package com.crossyf.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.crossyf.service.OrdersService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单表 (Orders)表控制层
 *
 * @author crossyf
 * @since 2020-08-18 23:37:18
 */
@RestController
@RequestMapping("orders")
@Api(value = "订单相关接口", tags = {"订单相关的api接口"})
@Slf4j
public class OrdersController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private OrdersService ordersService;




}