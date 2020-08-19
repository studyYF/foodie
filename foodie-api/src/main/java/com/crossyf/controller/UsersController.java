package com.crossyf.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.crossyf.entity.Users;
import com.crossyf.entity.bo.UserBO;
import com.crossyf.service.UsersService;
import com.crossyf.utils.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户表 (Users)表控制层
 *
 * @author crossyf
 * @since 2020-08-18 23:37:18
 */
@RestController
@RequestMapping("passport")
@Api(value = "用户接口", tags = {"用户登录注册接口"})
public class UsersController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UsersService usersService;


    /**
     * 判断用户名是否存在
     * @param username  用户名
     * @return 结果
     */
    @GetMapping("usernameIsExist")
    @ApiOperation(value = "判断用户名是否存在", httpMethod = "GET")
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
     * @param userBO 用户信息
     * @return 结果
     */
    @PostMapping("regist")
    @ApiOperation(value = "用户注册", tags = {"用户注册"}, httpMethod = "POST")
    public JsonResponse register(@RequestBody UserBO userBO) {
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
        return usersService.saveUser(userBO);
    }

    /**
     * 用户登录
     * @param userBO 用户信息
     * @return 用户信息
     */
    @PostMapping("login")
    @ApiOperation(value = "用户登录", tags = "用户登录")
    public JsonResponse login(@RequestBody UserBO userBO) {
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
        return JsonResponse.ok(users);
    }




}