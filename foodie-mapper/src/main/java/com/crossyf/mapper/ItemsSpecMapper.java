package com.crossyf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crossyf.entity.ItemsSpec;

/**
 * 商品规格 每一件商品都有不同的规格，不同的规格又有不同的价格和优惠力度，规格表为此设计(ItemsSpec)表数据库访问层
 *
 * @author crossyf
 * @since 2020-08-18 23:34:15
 */
public interface ItemsSpecMapper extends BaseMapper<ItemsSpec> {

}