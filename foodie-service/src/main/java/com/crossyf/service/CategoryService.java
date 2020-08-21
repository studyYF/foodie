package com.crossyf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crossyf.entity.Category;
import com.crossyf.entity.vo.CategoryVO;
import com.crossyf.entity.vo.NewItemsVO;

import java.util.List;

/**
 * 商品分类 (Category)表服务接口
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
public interface CategoryService extends IService<Category> {


    /**
     * 获取所有一级分类
     * @return 结果
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 根据商品一级分类id 获取子分类信息
     * @param rootCatId 一级分类id
     * @return 结果
     */
    List<CategoryVO> querySubCatList(Integer rootCatId);

    List<NewItemsVO> querySixNewItemsLazy(Integer rootCatId);
}