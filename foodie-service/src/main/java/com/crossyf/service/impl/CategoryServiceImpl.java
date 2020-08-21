package com.crossyf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.entity.vo.CategoryVO;
import com.crossyf.entity.vo.NewItemsVO;
import com.crossyf.mapper.CategoryMapper;
import com.crossyf.entity.Category;
import com.crossyf.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品分类 (Category)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> queryAllRootLevelCat() {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", 1);
        return categoryMapper.selectList(queryWrapper);
    }

    @Override
    public List<CategoryVO> querySubCatList(Integer rootCatId) {

        return categoryMapper.querySubCatList(rootCatId);
    }

    @Override
    public List<NewItemsVO> querySixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);
        return categoryMapper.querySixNewItemsLazy(map);
    }




}