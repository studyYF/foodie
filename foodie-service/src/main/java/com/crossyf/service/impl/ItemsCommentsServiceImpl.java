package com.crossyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.mapper.ItemsCommentsMapper;
import com.crossyf.entity.ItemsComments;
import com.crossyf.service.ItemsCommentsService;
import org.springframework.stereotype.Service;

/**
 * 商品评价表 (ItemsComments)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:19
 */
@Service("itemsCommentsService")
public class ItemsCommentsServiceImpl extends ServiceImpl<ItemsCommentsMapper, ItemsComments> implements ItemsCommentsService {

}