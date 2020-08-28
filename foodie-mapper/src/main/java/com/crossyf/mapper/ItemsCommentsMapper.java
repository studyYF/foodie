package com.crossyf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossyf.entity.ItemsComments;
import com.crossyf.entity.vo.MyCommentVO;

import java.util.List;
import java.util.Map;

/**
 * 商品评价表 (ItemsComments)表数据库访问层
 *
 * @author crossyf
 * @since 2020-08-18 23:34:15
 */
public interface ItemsCommentsMapper extends BaseMapper<ItemsComments> {

    /**
     * 保存评论
     * @param map 参数
     */
    void saveComments(Map<String, Object> map);

    /**
     * 根据条件查询评论
     * @param map 参数
     * @return 结果
     */
    List<MyCommentVO> queryMyComments(Map<String, Object> map);
}