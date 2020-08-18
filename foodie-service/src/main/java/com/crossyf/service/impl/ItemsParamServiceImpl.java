package com.crossyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.mapper.ItemsParamMapper;
import com.crossyf.entity.ItemsParam;
import com.crossyf.service.ItemsParamService;
import org.springframework.stereotype.Service;

/**
 * 商品参数 (ItemsParam)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:20
 */
@Service("itemsParamService")
public class ItemsParamServiceImpl extends ServiceImpl<ItemsParamMapper, ItemsParam> implements ItemsParamService {

}