package com.crossyf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crossyf.entity.Carousel;

import java.util.List;

/**
 * 轮播图 (Carousel)表服务接口
 *
 * @author crossyf
 * @since 2020-08-18 23:36:20
 */
public interface CarouselService extends IService<Carousel> {

    /**
     * 获取首页轮播图
     * @param type 类型
     * @return 结果
     */
    List<Carousel> queryAll(Integer type);
}