package com.crossyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.mapper.ItemsSpecMapper;
import com.crossyf.entity.ItemsSpec;
import com.crossyf.service.ItemsSpecService;
import org.springframework.stereotype.Service;

/**
 * 商品规格 每一件商品都有不同的规格，不同的规格又有不同的价格和优惠力度，规格表为此设计(ItemsSpec)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
@Service("itemsSpecService")
public class ItemsSpecServiceImpl extends ServiceImpl<ItemsSpecMapper, ItemsSpec> implements ItemsSpecService {

}