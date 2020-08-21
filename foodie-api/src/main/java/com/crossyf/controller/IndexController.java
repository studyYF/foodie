package com.crossyf.controller;

import com.crossyf.entity.Carousel;
import com.crossyf.entity.Category;
import com.crossyf.entity.enums.YesOrNo;
import com.crossyf.entity.vo.CategoryVO;
import com.crossyf.entity.vo.NewItemsVO;
import com.crossyf.service.CarouselService;
import com.crossyf.service.CategoryService;
import com.crossyf.utils.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by YangFan.
 * @date 2020/8/21
 * 功能: 首页控制器
 */
@RequestMapping("/index")
@Api(value = "首页接口", tags = {"首页接口"})
@RestController
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CarouselService carouselService;


    /**
     * 获取首页轮播图
     *
     * @return 结果
     */
    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public JsonResponse carousel() {
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);
        return JsonResponse.ok(list);
    }

    /**
     * 获取所有一级分类
     *
     * @return 结果
     */
    @ApiOperation(value = "获取一级分类", notes = "获取所有的一级分类")
    @GetMapping("cats")
    public JsonResponse cats() {
        List<Category> list = categoryService.queryAllRootLevelCat();
        return JsonResponse.ok(list);
    }


    /**
     * 根据商品一级分类id 获取子分类信息
     *
     * @param rootCatId 一级分类id
     * @return 结果
     */
    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JsonResponse subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return JsonResponse.errorMsg("分类不存在");
        }
        List<CategoryVO> list = categoryService.querySubCatList(rootCatId);
        return JsonResponse.ok(list);
    }

    /**
     * 查询每个一级分类下的最新6条商品数据
     * @param rootCatId 分类id
     * @return 结果
     */
    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JsonResponse sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return JsonResponse.errorMsg("分类不存在");
        }
        List<NewItemsVO> list = categoryService.querySixNewItemsLazy(rootCatId);
        return JsonResponse.ok(list);
    }


}
