package com.crossyf.service.center;

import com.crossyf.entity.Orders;
import com.crossyf.entity.vo.OrderStatusCountsVO;
import com.crossyf.utils.PagedGridResult;

/**
 * @author Created by YangFan.
 * @date 2020/8/19
 * 功能:
 */
public interface MyOrdersService {

    /**
     * 查询我的订单列表
     *
     * @param userId 用户id
     * @param orderStatus 订单状态
     * @param page 页数
     * @param pageSize 每页个数
     * @return 分页数据
     */
    PagedGridResult queryMyOrders(String userId,
                                         Integer orderStatus,
                                         Integer page,
                                         Integer pageSize);

    /**
     * @Description: 订单状态 --> 商家发货
     */
    void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     *
     * @param userId 用户ID
     * @param orderId 订单id
     * @return
     */
    Orders queryMyOrder(String userId, String orderId);

    /**
     * 更新订单状态 —> 确认收货
     *
     * @return 结果
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单（逻辑删除）
     * @param userId 用户ID
     * @param orderId 订单id
     * @return 结果
     */
    boolean deleteOrder(String userId, String orderId);

    /**
     * 查询用户订单数
     * @param userId 用户ID
     */
    OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 获得分页的订单动向
     * @param userId 用户ID
     * @param page 页数
     * @param pageSize 每页个数
     * @return 分页数据
     */
    PagedGridResult getOrdersTrend(String userId,
                                         Integer page,
                                         Integer pageSize);
}