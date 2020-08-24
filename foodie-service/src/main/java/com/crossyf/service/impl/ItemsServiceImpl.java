package com.crossyf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.entity.*;
import com.crossyf.entity.enums.CommentLevel;
import com.crossyf.entity.vo.CommentLevelCountsVO;
import com.crossyf.entity.vo.ItemCommentVO;
import com.crossyf.entity.vo.SearchItemsVO;
import com.crossyf.entity.vo.ShoppingCartVO;
import com.crossyf.mapper.*;
import com.crossyf.service.ItemsService;
import com.crossyf.utils.DesensitizationUtil;
import com.crossyf.utils.PagedGridResult;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.crossyf.utils.PagedGridResult.setterPagedGrid;

/**
 * 商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表(Items)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:20
 */
@Service("itemsService")
public class ItemsServiceImpl extends ServiceImpl<ItemsMapper, Items> implements ItemsService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;


    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectById(itemId);
    }

    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        QueryWrapper<ItemsImg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id", itemId);
        return itemsImgMapper.selectList(queryWrapper);
    }

    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        QueryWrapper<ItemsSpec> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id", itemId);
        return itemsSpecMapper.selectList(queryWrapper);
    }

    @Override
    public ItemsParam queryItemParam(String itemId) {
        QueryWrapper<ItemsParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id", itemId);
        return itemsParamMapper.selectOne(queryWrapper);
    }

    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        CommentLevelCountsVO countsVO = new CommentLevelCountsVO();
        countsVO.setTotalCounts(totalCounts);
        countsVO.setGoodCounts(goodCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setBadCounts(badCounts);
        return countsVO;
    }

    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page,
                                              Integer pageSize) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("itemId", itemId);
        map.put("level", level);
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> list = itemsMapper.queryItemComments(map);
        for (ItemCommentVO vo : list) {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }
        return setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult queryItems(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("catId", catId);
        map.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapper.queryItemsByThirdCat(map);
        return setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult queryItems(String keywords, String sort, Integer page,
                                      Integer pageSize) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("keywords", keywords);
        map.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapper.queryItems(map);
        return setterPagedGrid(list, page);
    }

    /**
     * 查询商品各评价等级的数量
     *
     * @param itemId 商品id
     * @param level  等级
     * @return 结果
     */
    private Integer getCommentCounts(String itemId, Integer level) {
        QueryWrapper<ItemsComments> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_id", itemId);
        if (level != null) {
            queryWrapper.eq("comment_level", level);
        }
        return itemsCommentsMapper.selectCount(queryWrapper);
    }

    @Override
    public List<ShoppingCartVO> queryItemsBySpecIds(String itemSpecIds) {
        String[] ids = itemSpecIds.split(",");
        List<String> specIdsList = new ArrayList<>();
        Collections.addAll(specIdsList, ids);
        return itemsMapper.queryItemsBySpecIds(specIdsList);
    }
}