package com.crossyf.controller;

import cn.hutool.core.util.StrUtil;
import com.crossyf.entity.bo.ShoppingCartBO;
import com.crossyf.utils.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Created by YangFan.
 * @date 2020/8/24
 * 功能: 购物车控制器
 */

@Api(value = "购物车接口controller", tags = {"购物车接口相关的api"})
@RequestMapping("shoppingCart")
@RestController
public class ShoppingCartController {


    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public JsonResponse add(
            @RequestParam String userId,
            @RequestBody ShoppingCartBO ShoppingCartBO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (StrUtil.isBlank(userId)) {
            return JsonResponse.errorMsg("");
        }
        System.out.println(ShoppingCartBO);
        // TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        return JsonResponse.ok();
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public JsonResponse del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (StrUtil.isBlank(userId) || StrUtil.isBlank(itemSpecId)) {
            return JsonResponse.errorMsg("参数不能为空");
        }
        // TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品
        return JsonResponse.ok();
    }
}
