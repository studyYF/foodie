package com.crossyf.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crossyf.entity.Users;
import com.crossyf.service.UsersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 用户表 (Users)表控制层
 *
 * @author crossyf
 * @since 2020-08-18 23:37:18
 */
@RestController
@RequestMapping("users")
public class UsersController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private UsersService usersService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param users 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Users> page, Users users) {
        return success(this.usersService.page(page, new QueryWrapper<>(users)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.usersService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param users 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Users users) {
        return success(this.usersService.save(users));
    }

    /**
     * 修改数据
     *
     * @param users 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Users users) {
        return success(this.usersService.updateById(users));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.usersService.removeByIds(idList));
    }
}