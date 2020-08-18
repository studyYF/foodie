package com.crossyf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crossyf.mapper.CarouselMapper;
import com.crossyf.entity.Carousel;
import com.crossyf.service.CarouselService;
import org.springframework.stereotype.Service;

/**
 * 轮播图 (Carousel)表服务实现类
 *
 * @author crossyf
 * @since 2020-08-18 23:36:20
 */
@Service("carouselService")
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselService {

}