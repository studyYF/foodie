package com.crossyf.service.impl.center;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crossyf.entity.OrderStatus;
import com.crossyf.entity.Orders;
import com.crossyf.entity.enums.OrderStatusEnum;
import com.crossyf.entity.enums.YesOrNo;
import com.crossyf.entity.vo.MyOrdersVO;
import com.crossyf.entity.vo.OrderStatusCountsVO;
import com.crossyf.mapper.OrderStatusMapper;
import com.crossyf.mapper.OrdersMapper;
import com.crossyf.service.center.MyOrdersService;
import com.crossyf.utils.PagedGridResult;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Created by YangFan.
 * @date 2020/8/19
 * 功能:
 */
@Service
public class MyOrdersServiceImpl implements MyOrdersService {

    @Autowired
    public OrdersMapper ordersMapper;

    @Autowired
    public OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId,
                                         Integer orderStatus,
                                         Integer page,
                                         Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> list = ordersMapper.queryMyOrders(map);
        return PagedGridResult.setterPagedGrid(list, page);
    }

    @Override
    public void updateDeliverOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderId(orderId);
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setDeliverTime(new Date());
        orderStatusMapper.updateById(updateOrder);
    }

    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("order_id", orderId);
        queryWrapper.eq("is_delete", YesOrNo.NO.type);
        return ordersMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        updateOrder.setSuccessTime(new Date());
        updateOrder.setOrderId(orderId);
        int result = orderStatusMapper.updateById(updateOrder);
        return result == 1;
    }

    @Override
    public boolean deleteOrder(String userId, String orderId) {
        Orders updateOrder = new Orders();
        updateOrder.setIsDelete(YesOrNo.YES.type);
        updateOrder.setUpdatedTime(new Date());
        updateOrder.setId(orderId);
        updateOrder.setUserId(userId);
        int result = ordersMapper.updateById(updateOrder);
        return result == 1;
    }

    @Override
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersMapper.queryMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersMapper.queryMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersMapper.queryMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.type);
        int waitCommentCounts = ordersMapper.queryMyOrderStatusCounts(map);

        return new OrderStatusCountsVO(waitPayCounts,
                waitDeliverCounts,
                waitReceiveCounts,
                waitCommentCounts);
    }

    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<OrderStatus> list = ordersMapper.queryMyOrderTrend(map);
        return PagedGridResult.setterPagedGrid(list, page);
    }
}
