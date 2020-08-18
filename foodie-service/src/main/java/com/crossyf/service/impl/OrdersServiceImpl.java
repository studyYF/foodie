package com.crossyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.mapper.OrdersMapper;
import com.crossyf.entity.Orders;
import com.crossyf.service.OrdersService;
import org.springframework.stereotype.Service;

/**
 * 订单表 (Orders)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
@Service("ordersService")
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}