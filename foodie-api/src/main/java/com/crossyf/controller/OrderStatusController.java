package com.crossyf.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crossyf.entity.OrderStatus;
import com.crossyf.service.OrderStatusService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 订单状态表 订单的每个状态更改都需要进行记录
10：待付款  20：已付款，待发货  30：已发货，待收货（7天自动确认）  40：交易成功（此时可以评价）50：交易关闭（待付款时，用户取消 或 长时间未付款，系统识别后自动关闭）
退货/退货，此分支流程不做，所以不加入(OrderStatus)表控制层
 *
 * @author crossyf
 * @since 2020-08-18 23:37:18
 */
@RestController
@RequestMapping("orderStatus")
public class OrderStatusController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private OrderStatusService orderStatusService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param orderStatus 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<OrderStatus> page, OrderStatus orderStatus) {
        return success(this.orderStatusService.page(page, new QueryWrapper<>(orderStatus)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.orderStatusService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param orderStatus 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody OrderStatus orderStatus) {
        return success(this.orderStatusService.save(orderStatus));
    }

    /**
     * 修改数据
     *
     * @param orderStatus 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody OrderStatus orderStatus) {
        return success(this.orderStatusService.updateById(orderStatus));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.orderStatusService.removeByIds(idList));
    }
}