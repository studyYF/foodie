package com.crossyf.service.impl.center;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crossyf.entity.OrderItems;
import com.crossyf.entity.OrderStatus;
import com.crossyf.entity.Orders;
import com.crossyf.entity.bo.center.OrderItemsCommentBO;
import com.crossyf.entity.enums.YesOrNo;
import com.crossyf.entity.vo.MyCommentVO;
import com.crossyf.mapper.ItemsCommentsMapper;
import com.crossyf.mapper.OrderItemsMapper;
import com.crossyf.mapper.OrderStatusMapper;
import com.crossyf.mapper.OrdersMapper;
import com.crossyf.service.center.MyCommentsService;
import com.crossyf.utils.PagedGridResult;
import com.github.pagehelper.PageHelper;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.crossyf.utils.PagedGridResult.setterPagedGrid;


/**
 * @author Created by YangFan.
 * @date 2020/8/19
 * 功能:
 */

@Service
public class MyCommentsServiceImpl implements MyCommentsService {

    @Autowired
    public OrderItemsMapper orderItemsMapper;

    @Autowired
    public OrdersMapper ordersMapper;

    @Autowired
    public OrderStatusMapper orderStatusMapper;

    @Autowired
    public ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private Sid sid;

    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        QueryWrapper<OrderItems> query = new QueryWrapper<>();
        query.eq("order_id", orderId);
        return orderItemsMapper.selectList(query);
    }

    @Override
    public void saveComments(String orderId, String userId,
                             List<OrderItemsCommentBO> commentList) {
        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapper.saveComments(map);
        // 2. 修改订单表改已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateById(order);
        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateById(orderStatus);
    }

    @Override
    public PagedGridResult queryMyComments(String userId,
                                           Integer page,
                                           Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsMapper.queryMyComments(map);
        return setterPagedGrid(list, page);
    }
}
