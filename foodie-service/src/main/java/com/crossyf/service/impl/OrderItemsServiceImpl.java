package com.crossyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.mapper.OrderItemsMapper;
import com.crossyf.entity.OrderItems;
import com.crossyf.service.OrderItemsService;
import org.springframework.stereotype.Service;

/**
 * 订单商品关联表 (OrderItems)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
@Service("orderItemsService")
public class OrderItemsServiceImpl extends ServiceImpl<OrderItemsMapper, OrderItems> implements OrderItemsService {

}