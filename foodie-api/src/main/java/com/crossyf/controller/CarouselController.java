package com.crossyf.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crossyf.entity.Carousel;
import com.crossyf.service.CarouselService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * spring事务的传播机制
 *  REQUIRED： 默认，父方法有事务，子方法跟随父方法的事务，父方法没有事务，子方法新建一个事务
 *  SUPPORTS:  子方法跟随父方法
 *  MANDATORY（强制性）: 必须存在事务，如果没有，则抛出异常
 *  REQUIRED_NEW: 父方法有事务，挂起，子方法新建一个事务
 *  NOT_SUPPORT: 父方法有事务，挂起事务，子方法不包含事务
 *  NEVER: 父方法不允许有事务，如果有事务，则抛出异常
 *  NESTED: 嵌套事务，父方法有事务，子方法也新开启事务，他们同时提交（区别于REQUIRED）如果父事务提交，子事务也一起提交，
 *          如果子事务回滚，父事务可以不会滚。
 *
 */

/**
 * 轮播图 (Carousel)表控制层
 *
 * @author crossyf
 * @since 2020-08-18 23:37:18
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private CarouselService carouselService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param carousel 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Carousel> page, Carousel carousel) {
        return success(this.carouselService.page(page, new QueryWrapper<>(carousel)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.carouselService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param carousel 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Carousel carousel) {
        return success(this.carouselService.save(carousel));
    }

    /**
     * 修改数据
     *
     * @param carousel 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Carousel carousel) {
        return success(this.carouselService.updateById(carousel));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.carouselService.removeByIds(idList));
    }
}