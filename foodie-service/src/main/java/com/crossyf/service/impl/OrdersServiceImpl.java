package com.crossyf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.entity.*;
import com.crossyf.entity.bo.SubmitOrderBO;
import com.crossyf.entity.enums.OrderStatusEnum;
import com.crossyf.entity.enums.YesOrNo;
import com.crossyf.entity.vo.MerchantOrdersVO;
import com.crossyf.entity.vo.OrderVO;
import com.crossyf.mapper.*;
import com.crossyf.service.OrdersService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 订单表 (Orders)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
@Service("ordersService")
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private OrderStatusMapper orderStatusMapper;


    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        Orders newOrder = assembleOrder(submitOrderBO);
        saveOrderItems(submitOrderBO.getItemSpecIds(), newOrder.getId());
        saveOrderStatus(newOrder.getId());
        MerchantOrdersVO merchantOrdersVO = assembleMerchantOrder(newOrder);
        OrderVO orderVO = new OrderVO();
        orderVO.setMerchantOrdersVO(merchantOrdersVO);
        orderVO.setOrderId(newOrder.getId());
        return orderVO;
    }

    @Override
    public void updateOrderStatus(String merchantOrderId, Integer type) {
        OrderStatus orderStatus = orderStatusMapper.selectById(merchantOrderId);
        orderStatus.setOrderStatus(type);
        orderStatus.setPayTime(new Date());
        orderStatusMapper.updateById(orderStatus);
    }

    /**
     * 创建订单
     *
     * @param submitOrderBO 传入订单信息
     * @return 订单order
     */
    private Orders assembleOrder(SubmitOrderBO submitOrderBO) {
        Orders newOrder = new Orders();
        newOrder.setId(sid.nextShort());
        newOrder.setUserId(submitOrderBO.getUserId());
        UserAddress address = userAddressMapper.selectById(submitOrderBO.getAddressId());
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince() + " "
                + address.getCity() + " "
                + address.getDistrict() + " "
                + address.getDetail());
        newOrder.setPostAmount(submitOrderBO.getPayMethod());
        newOrder.setPayMethod(submitOrderBO.getPayMethod());
        newOrder.setLeftMsg(submitOrderBO.getLeftMsg());
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());
        newOrder.setTotalAmount(calculateTotalAmount(submitOrderBO.getItemSpecIds()));
        newOrder.setRealPayAmount(calculateRealPayAmount(submitOrderBO.getItemSpecIds()));
        ordersMapper.insert(newOrder);
        return newOrder;
    }

    private void saveOrderItems(String itemSpecIds, String orderId) {
        String[] itemSpecIdArr = itemSpecIds.split(",");
        for (String itemSpecId : itemSpecIdArr) {
            // 2.1 根据规格id，查询规格的具体信息，主要获取价格
            ItemsSpec itemSpec = itemsSpecMapper.selectById(itemSpecId);
            // 2.2 根据商品id，获得商品信息以及商品图片
            String itemId = itemSpec.getItemId();
            Items item = itemsMapper.selectById(itemId);
            QueryWrapper<ItemsImg> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("item_id", itemId);
            queryWrapper.eq("is_main", YesOrNo.YES.type);
            ItemsImg itemsImg = itemsImgMapper.selectOne(queryWrapper);
            // 2.3 循环保存子订单数据到数据库
            String subOrderId = sid.nextShort();
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(itemsImg.getUrl());
            // TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
            int buyCounts = 1;
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemSpec.getName());
            subOrderItem.setPrice(itemSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);
            // 2.4 在用户提交订单以后，规格表中需要扣除库存
            itemsMapper.decreaseItemSpecStock(itemSpecId, buyCounts);
        }
    }

    /**
     * 保存订单状态
     */
    private void saveOrderStatus(String orderId) {
        // 3. 保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);
    }

    /**
     * 创建商户订单，传给支付中心
     *
     * @param orders 订单
     * @return 商户vo
     */
    private MerchantOrdersVO assembleMerchantOrder(Orders orders) {
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orders.getId());
        merchantOrdersVO.setMerchantUserId(orders.getUserId());
        merchantOrdersVO.setAmount(orders.getRealPayAmount());
        merchantOrdersVO.setPayMethod(orders.getPayMethod());
        return merchantOrdersVO;
    }

    /**
     * 计算总价
     *
     * @param itemSpecIds 订单商品id
     * @return 价格
     */
    private Integer calculateTotalAmount(String itemSpecIds) {
        int result = 0;
        // TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
        int buyCounts = 1;
        for (String itemSpecId : itemSpecIds.split(",")) {
            ItemsSpec itemSpec = itemsSpecMapper.selectById(itemSpecId);
            result += itemSpec.getPriceNormal() * buyCounts;
        }
        return result;
    }

    /**
     * 计算折扣价格
     *
     * @param itemSpecIds 订单商品id
     * @return 价格
     */
    private Integer calculateRealPayAmount(String itemSpecIds) {
        int result = 0;
        // TODO 整合redis后，商品购买的数量重新从redis的购物车中获取
        int buyCounts = 1;
        for (String itemSpecId : itemSpecIds.split(",")) {
            ItemsSpec itemSpec = itemsSpecMapper.selectById(itemSpecId);
            result += itemSpec.getPriceDiscount() * buyCounts;
        }
        return result;
    }


}