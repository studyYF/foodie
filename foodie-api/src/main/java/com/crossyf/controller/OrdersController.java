package com.crossyf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crossyf.constant.Constant;
import com.crossyf.entity.OrderStatus;
import com.crossyf.entity.bo.SubmitOrderBO;
import com.crossyf.entity.enums.OrderStatusEnum;
import com.crossyf.entity.enums.PayMethod;
import com.crossyf.entity.vo.MerchantOrdersVO;
import com.crossyf.entity.vo.OrderVO;
import com.crossyf.service.OrderStatusService;
import com.crossyf.service.OrdersService;
import com.crossyf.utils.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
public class OrdersController {

    @Autowired
    private RestTemplate restTemplate;
    /**
     * 服务对象
     */
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderStatusService orderStatusService;


    /**
     * 用户提交订单
     *
     * @param submitOrderBO 订单bo
     * @return 结果
     */
    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("create")
    public JsonResponse create(@RequestBody SubmitOrderBO submitOrderBO) {
        if (!submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type)
                && !submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type)) {
            return JsonResponse.errorMsg("支付方式不支持！");
        }
        OrderVO orderVO = ordersService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(Constant.PAY_RETURN_URL);
        // 为了方便测试购买，所以所有的支付金额都统一改为1分钱
        merchantOrdersVO.setAmount(1);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");

        HttpEntity<MerchantOrdersVO> entity =
                new HttpEntity<>(merchantOrdersVO, headers);
        ResponseEntity<JsonResponse> responseEntity =
                restTemplate.postForEntity(Constant.PAYMENT_URL, entity, JsonResponse.class);
        JsonResponse paymentResult = responseEntity.getBody();
        if (paymentResult != null && paymentResult.getStatus() != 200) {
            log.error("发送错误：{}", paymentResult.getMsg());
            return JsonResponse.errorMsg("支付中心订单创建失败，请联系管理员！");
        }
        return JsonResponse.ok(orderId);
    }


    /**
     * 订单支付回调
     *
     * @param merchantOrderId 商户订单id
     * @return 结果
     */
    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        ordersService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    /**
     * 查询当前订单状态
     *
     * @param orderId 订单id
     * @return 结果
     */
    @PostMapping("getPaidOrderInfo")
    public JsonResponse getPaidOrderInfo(String orderId) {
        QueryWrapper<OrderStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        OrderStatus orderStatus = orderStatusService.getOne(queryWrapper);
        return JsonResponse.ok(orderStatus);
    }

}