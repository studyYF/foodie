package com.crossyf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossyf.entity.Category;
import com.crossyf.entity.vo.CategoryVO;
import com.crossyf.entity.vo.NewItemsVO;

import java.util.List;
import java.util.Map;

/**
 * 商品分类 (Category)表数据库访问层
 *
 * @author crossyf
 * @since 2020-08-18 23:34:15
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 根据商品一级分类id 获取子分类信息
     * @param rootCatId 一级分类id
     * @return 结果
     */
    List<CategoryVO> querySubCatList(Integer rootCatId);

    /**
     * 查询每个一级分类下的最新6条商品数据
     * @param map 分类id
     * @return 结果
     */
    List<NewItemsVO> querySixNewItemsLazy(Map<String, Object> map);
}