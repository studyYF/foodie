package com.crossyf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by YangFan.
 * @date 2020/8/18
 * 功能: 测试控制器
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Object hello() {
        return "hello";
    }
}
