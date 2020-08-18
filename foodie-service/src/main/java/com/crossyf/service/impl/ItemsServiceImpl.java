package com.crossyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.mapper.ItemsMapper;
import com.crossyf.entity.Items;
import com.crossyf.service.ItemsService;
import org.springframework.stereotype.Service;

/**
 * 商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表(Items)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:20
 */
@Service("itemsService")
public class ItemsServiceImpl extends ServiceImpl<ItemsMapper, Items> implements ItemsService {

}