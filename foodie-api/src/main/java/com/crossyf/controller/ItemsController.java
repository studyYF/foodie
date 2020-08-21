package com.crossyf.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.crossyf.entity.Items;
import com.crossyf.entity.ItemsImg;
import com.crossyf.entity.ItemsParam;
import com.crossyf.entity.ItemsSpec;
import com.crossyf.entity.vo.CommentLevelCountsVO;
import com.crossyf.entity.vo.ItemInfoVO;
import com.crossyf.service.ItemsService;
import com.crossyf.utils.JsonResponse;
import com.crossyf.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品表 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表(Items)表控制层
 *
 * @author crossyf
 * @since 2020-08-18 23:37:18
 */
@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ItemsService itemsService;

    /**
     * 获取商品详情
     *
     * @param itemId itemId
     * @return 结果
     */
    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JsonResponse info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId) {

        if (StrUtil.isBlank(itemId)) {
            return JsonResponse.errorMsg("商品id不能为空");
        }
        Items item = itemsService.queryItemById(itemId);
        List<ItemsImg> itemImgList = itemsService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemsService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemsService.queryItemParam(itemId);
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);
        return JsonResponse.ok(itemInfoVO);
    }

    /**
     * 查询商品评价各等级条数
     *
     * @param itemId 商品id
     * @return 结果
     */
    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JsonResponse commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId) {
        if (StrUtil.isBlank(itemId)) {
            return JsonResponse.errorMsg(null);
        }
        CommentLevelCountsVO countsVO = itemsService.queryCommentCounts(itemId);
        return JsonResponse.ok(countsVO);
    }

    /**
     * 根据等级查询商品评价
     *
     * @param itemId   商品id
     * @param level    评价登记
     * @param page     页数
     * @param pageSize 每页条数
     * @return 结果
     */
    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public JsonResponse comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(required = false, defaultValue = "20") Integer pageSize) {

        if (StrUtil.isBlank(itemId)) {
            return JsonResponse.errorMsg(null);
        }
        PagedGridResult grid = itemsService.queryPagedComments(itemId, level, page, pageSize);
        return JsonResponse.ok(grid);
    }

    /**
     * 搜索商品
     *
     * @param keywords 关键字
     * @param sort     排序方式
     * @param page     页数
     * @param pageSize 每页条数
     * @return 结果
     */
    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public JsonResponse search(
            @ApiParam(name = "keywords", value = "关键字", required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(required = false, defaultValue = "20") Integer pageSize) {

        if (StrUtil.isBlank(keywords)) {
            return JsonResponse.errorMsg(null);
        }
        PagedGridResult grid = itemsService.queryItems(keywords, sort, page, pageSize);
        return JsonResponse.ok(grid);
    }

    /**
     * 根据三级分类搜素商品
     *
     * @param catId    分类id
     * @param sort     排序方式
     * @param page     页数
     * @param pageSize 每页个数
     * @return 结果
     */
    @ApiOperation(value = "通过分类id搜索商品列表", notes = "通过分类id搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public JsonResponse catItems(
            @ApiParam(name = "catId", value = "三级分类id", required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(required = false, defaultValue = "20") Integer pageSize) {

        if (catId == null) {
            return JsonResponse.errorMsg(null);
        }
        PagedGridResult grid = itemsService.queryItems(catId, sort, page, pageSize);
        return JsonResponse.ok(grid);
    }


}