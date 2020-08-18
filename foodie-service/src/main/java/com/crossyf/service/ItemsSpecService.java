package com.crossyf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crossyf.entity.ItemsSpec;

/**
 * 商品规格 每一件商品都有不同的规格，不同的规格又有不同的价格和优惠力度，规格表为此设计(ItemsSpec)表服务接口
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
public interface ItemsSpecService extends IService<ItemsSpec> {

}