package com.crossyf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossyf.entity.OrderStatus;
import com.crossyf.entity.Orders;
import com.crossyf.entity.vo.MyOrdersVO;

import java.util.List;
import java.util.Map;

/**
 * 订单表 (Orders)表数据库访问层
 *
 * @author crossyf
 * @since 2020-08-18 23:34:15
 */
public interface OrdersMapper extends BaseMapper<Orders> {

    /**
     * 根据条件查询订单
     *
     * @param map 条件
     * @return 结果
     */
    List<MyOrdersVO> queryMyOrders(Map<String, Object> map);

    /**
     * 计算不同状态订单个数
     * @param map 参数
     * @return 结果
     */
    int queryMyOrderStatusCounts(Map<String, Object> map);

    /**
     * 查询订单
     * @param map 参数
     * @return 结果
     */
    List<OrderStatus> queryMyOrderTrend(Map<String, Object> map);
}