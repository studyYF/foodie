package com.crossyf.constant;

/**
 * @author Created by YangFan.
 * @date 2020/8/21
 * 功能: 全局常量类
 */
public class Constant {

    /**
     * 默认评论也条数
     */
    public static final Integer COMMON_PAGE_SIZE = 20;

    /**
     * 订单回调url
     */
    public static final String PAY_RETURN_URL = "http://localhost:8088/orders/notifyMerchantOrderPaid";

    /**
     * 支付订单url
     */
    public static final String PAYMENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

}
