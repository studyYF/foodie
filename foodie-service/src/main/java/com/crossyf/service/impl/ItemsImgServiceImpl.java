package com.crossyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.mapper.ItemsImgMapper;
import com.crossyf.entity.ItemsImg;
import com.crossyf.service.ItemsImgService;
import org.springframework.stereotype.Service;

/**
 * 商品图片 (ItemsImg)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:20
 */
@Service("itemsImgService")
public class ItemsImgServiceImpl extends ServiceImpl<ItemsImgMapper, ItemsImg> implements ItemsImgService {

}