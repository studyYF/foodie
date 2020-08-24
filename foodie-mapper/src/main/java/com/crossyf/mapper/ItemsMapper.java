package com.crossyf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossyf.entity.Items;
import com.crossyf.entity.vo.ItemCommentVO;
import com.crossyf.entity.vo.SearchItemsVO;
import com.crossyf.entity.vo.ShoppingCartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表(Items)表数据库访问层
 *
 * @author crossyf
 * @since 2020-08-18 23:34:15
 */
public interface ItemsMapper extends BaseMapper<Items> {

    /**
     * 根据商品id和评价等级查询商品评论
     *
     * @param map 参数
     * @return 结果
     */
    List<ItemCommentVO> queryItemComments(Map<String, Object> map);

    /**
     * 根据商品分类查询商品
     *
     * @param map 参数
     * @return 结果
     */
    List<SearchItemsVO> queryItemsByThirdCat(Map<String, Object> map);

    /**
     * 根据关键字查询商品
     *
     * @param map 参数
     * @return 结果
     */
    List<SearchItemsVO> queryItems(Map<String, Object> map);

    /**
     * 用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格），类似京东淘宝
     *
     * @param specIdsList 商品id
     * @return 结果
     */
    List<ShoppingCartVO> queryItemsBySpecIds(@Param(value = "paramsList") List<String> specIdsList);
}