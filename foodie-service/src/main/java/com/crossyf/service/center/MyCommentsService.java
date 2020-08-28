package com.crossyf.service.center;

import com.crossyf.entity.OrderItems;
import com.crossyf.entity.bo.center.OrderItemsCommentBO;
import com.crossyf.utils.PagedGridResult;

import java.util.List;

/**
 * @author Created by YangFan.
 * @date 2020/8/19
 * 功能:
 */

public interface MyCommentsService {

    /**
     * 根据订单id查询关联的商品
     *
     * @param orderId 订单id
     * @return 数据
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     *
     * @param orderId     订单id
     * @param userId      用户id
     * @param commentList 评论链表
     */
    void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);


    /**
     * 我的评价查询 分页
     *
     * @param userId   用户id
     * @param page     页数
     * @param pageSize 每页个数
     * @return
     */
    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
