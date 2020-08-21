package com.crossyf.aspect;

import cn.hutool.core.date.StopWatch;
import com.crossyf.utils.JsonUtils;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Created by YangFan.
 * @date 2020/8/20
 * 功能: service切面，记录调用的方法，以及消耗的时间
 */

@Slf4j
@Component
@Aspect
public class ServiceLogAspect {

    /**
     * 记录调用每个service方法的时间，
     *
     * @param joinPoint 切面
     * @return 结果
     */
    @Around("execution(* com.crossyf.service.impl..*.*(..))")
    public Object methodTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("====== 开始调用{}.{}方法, 参数为{} ======", joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName(),
                JsonUtils.objectToJson(Arrays.asList(joinPoint.getArgs())));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        if (stopWatch.getTotalTimeMillis() > 3000) {
            log.error("====== 结束调用，共耗时{}毫秒 ====== ", stopWatch.getTotalTimeMillis());
        } else if (stopWatch.getTotalTimeMillis() > 2000) {
            log.warn("====== 结束调用，共耗时{}毫秒 ====== ", stopWatch.getTotalTimeMillis());
        } else {
            log.info("====== 结束调用，共耗时{}毫秒 ====== ", stopWatch.getTotalTimeMillis());
        }
        return proceed;
    }
}
