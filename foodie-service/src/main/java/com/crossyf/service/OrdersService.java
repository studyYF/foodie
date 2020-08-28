package com.crossyf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crossyf.entity.Orders;
import com.crossyf.entity.bo.SubmitOrderBO;
import com.crossyf.entity.vo.OrderVO;

/**
 * 订单表 (Orders)表服务接口
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 创建订单
     *
     * @param submitOrderBO
     * @return
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 更新订单状态
     *
     * @param merchantOrderId 商户订单id
     * @param type            状态
     */
    void updateOrderStatus(String merchantOrderId, Integer type);
}