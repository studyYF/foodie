package com.crossyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.mapper.OrderStatusMapper;
import com.crossyf.entity.OrderStatus;
import com.crossyf.service.OrderStatusService;
import org.springframework.stereotype.Service;

/**
 * 订单状态表 订单的每个状态更改都需要进行记录
10：待付款  20：已付款，待发货  30：已发货，待收货（7天自动确认）  40：交易成功（此时可以评价）50：交易关闭（待付款时，用户取消 或 长时间未付款，系统识别后自动关闭）
退货/退货，此分支流程不做，所以不加入(OrderStatus)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
@Service("orderStatusService")
public class OrderStatusServiceImpl extends ServiceImpl<OrderStatusMapper, OrderStatus> implements OrderStatusService {

}