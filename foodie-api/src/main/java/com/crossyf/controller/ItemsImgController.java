package com.crossyf.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crossyf.entity.ItemsImg;
import com.crossyf.service.ItemsImgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 商品图片 (ItemsImg)表控制层
 *
 * @author crossyf
 * @since 2020-08-18 23:37:18
 */
@RestController
@RequestMapping("itemsImg")
public class ItemsImgController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private ItemsImgService itemsImgService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param itemsImg 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<ItemsImg> page, ItemsImg itemsImg) {
        return success(this.itemsImgService.page(page, new QueryWrapper<>(itemsImg)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.itemsImgService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param itemsImg 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody ItemsImg itemsImg) {
        return success(this.itemsImgService.save(itemsImg));
    }

    /**
     * 修改数据
     *
     * @param itemsImg 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody ItemsImg itemsImg) {
        return success(this.itemsImgService.updateById(itemsImg));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.itemsImgService.removeByIds(idList));
    }
}