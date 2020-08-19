package com.crossyf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Created by YangFan.
 * @date 2020/8/18
 * 功能: 启动类
 */

@SpringBootApplication
@MapperScan(basePackages = "com.crossyf.mapper")
@ComponentScan(basePackages = {"com.crossyf","org.n3r.idworker"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
