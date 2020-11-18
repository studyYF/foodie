package com.crossyf.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.crossyf.constant.Constant;
import com.crossyf.entity.Users;
import com.crossyf.entity.bo.UserBO;
import com.crossyf.service.UsersService;
import com.crossyf.utils.CookieUtils;
import com.crossyf.utils.JsonResponse;
import com.crossyf.utils.JsonUtils;
import com.crossyf.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 用户表 (Users)表控制层
 *
 * @author crossyf
 * @since 2020-08-18 23:37:18
 */
@RestController
@RequestMapping("passport")
@Api(value = "用户接口", tags = {"用户登录注册接口---"})
public class UsersController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UsersService usersService;

    @Autowired
    private RedisOperator redisOperator;


    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 结果
     */
    @GetMapping("usernameIsExist")
    @ApiOperation(value = "判断用户名是否存在--", notes = "判断用户名是否存在", httpMethod = "GET")
    public JsonResponse usernameExist(String username) {
        if (StrUtil.isBlank(username)) {
            return JsonResponse.errorMsg("用户名不能为空");
        }
        QueryWrapper<Users> usersWrapper = new QueryWrapper<>();
        usersWrapper.eq("username", username);
        Users users = usersService.getOne(usersWrapper);
        if (users != null) {
            return JsonResponse.errorMsg("用户名已存在");
        }
        return JsonResponse.ok();
    }

    /**
     * 注册用户
     *
     * @param userBO   用户信息
     * @param request  request
     * @param response response
     * @return 结果
     */
    @PostMapping("/regist")
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    public JsonResponse register(@RequestBody UserBO userBO, HttpServletRequest request,
                                 HttpServletResponse response) {
        //1.校验参数
        if (StrUtil.isBlank(userBO.getUsername()) ||
                StrUtil.isBlank(userBO.getPassword())) {
            return JsonResponse.errorMsg("用户名或密码不能为空");
        }
        //2.判断用户名是否存在
        if (!usernameExist(userBO.getUsername()).isOK()) {
            return JsonResponse.errorMsg("当前用户名已存在");
        }

        //3.判断密码是否符合要求 （大于6位）
        if (userBO.getPassword().length() < 6) {
            return JsonResponse.errorMsg("密码不能少于6位");
        }
        //4.判断两次密码是否相同
        if (!userBO.getPassword().equals(userBO.getConfirmPassword())) {
            return JsonResponse.errorMsg("两次输入的密码不同");
        }
        //5.注册
        JsonResponse jsonResponse = usersService.saveUser(userBO);
        Users usersResult = setNullProperty((Users) jsonResponse.getData());
        //实现用户的redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(Constant.REDIS_USER_TOKEN + ":" + usersResult.getId(), uniqueToken);

        //修复一个线上bug
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersResult), true);
        return jsonResponse;
    }

    /**
     * 用户登录
     *
     * @param userBO   用户信息
     * @param request  request
     * @param response response
     * @return 用户信息
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    public JsonResponse login(@RequestBody UserBO userBO, HttpServletRequest request,
                              HttpServletResponse response) {
        //1.校验参数
        if (StrUtil.isBlank(userBO.getUsername()) ||
                StrUtil.isBlank(userBO.getPassword())) {
            return JsonResponse.errorMsg("用户名或密码不能为空");
        }
        //2.查找用户
        QueryWrapper<Users> usersWrapper = new QueryWrapper<>();
        usersWrapper.eq("username", userBO.getUsername());
        usersWrapper.eq("password", SecureUtil.md5(userBO.getPassword()));
        Users users = usersService.getOne(usersWrapper);
        if (users == null) {
            return JsonResponse.errorMsg("用户名或密码错误");
        }
        JsonResponse jsonResponse = JsonResponse.ok(users);
        Users usersResult = setNullProperty((Users) jsonResponse.getData());
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersResult), true);
        return jsonResponse;
    }


    /**
     * 退出登录
     *
     * @param userId   用户id
     * @param request  request
     * @param response response
     * @return 结果
     */
    @ApiOperation(value = "退出登录", notes = "退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public JsonResponse logout(@RequestParam String userId, HttpServletRequest request,
                               HttpServletResponse response) {
        if (StrUtil.isBlank(userId)) {
            return JsonResponse.errorMsg("用户id不能为空");
        }
        //清除cookie信息
        CookieUtils.deleteCookie(request, response, "user");
        return JsonResponse.ok();
    }


    /**
     * 将隐私敏感信息清除
     * @param userResult user
     * @return 结果
     */
    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

}