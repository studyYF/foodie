package com.crossyf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crossyf.entity.Items;
import com.crossyf.entity.ItemsImg;
import com.crossyf.entity.ItemsParam;
import com.crossyf.entity.ItemsSpec;
import com.crossyf.entity.vo.CommentLevelCountsVO;
import com.crossyf.entity.vo.ShoppingCartVO;
import com.crossyf.utils.PagedGridResult;

import java.util.List;

/**
 * 商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表(Items)表服务接口
 *
 * @author crossyf
 * @since 2020-08-18 23:36:20
 */
public interface ItemsService extends IService<Items> {

    /**
     * 获取商品详情
     *
     * @param itemId itemId
     * @return 结果
     */
    Items queryItemById(String itemId);

    /**
     * 获取商品图片
     *
     * @param itemId itemId
     * @return 结果
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 获取商品规格
     *
     * @param itemId itemId
     * @return 结果
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 获取商品参数
     *
     * @param itemId itemId
     * @return 结果
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 查询商品评价各等级条数
     *
     * @param itemId 商品id
     * @return 结果
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据等级查询商品评价
     *
     * @param itemId   商品id
     * @param level    评价登记
     * @param page     页数
     * @param pageSize 每页条数
     * @return 结果
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page,
                                       Integer pageSize);

    /**
     * 根据三级分类搜素商品
     *
     * @param catId    分类id
     * @param sort     排序方式
     * @param page     页数
     * @param pageSize 每页个数
     * @return 结果
     */

    PagedGridResult queryItems(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 搜索商品
     *
     * @param keywords 关键字
     * @param sort     排序方式
     * @param page     页数
     * @param pageSize 每页条数
     * @return 结果
     */
    PagedGridResult queryItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格），类似京东淘宝
     *
     * @param itemSpecIds 商品id，用逗号拼接
     * @return 结果
     */
    List<ShoppingCartVO> queryItemsBySpecIds(String itemSpecIds);
}