package com.crossyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.mapper.CategoryMapper;
import com.crossyf.entity.Category;
import com.crossyf.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * 商品分类 (Category)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}