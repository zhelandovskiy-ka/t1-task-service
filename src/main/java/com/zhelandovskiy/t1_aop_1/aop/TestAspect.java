package com.zhelandovskiy.t1_aop_1.aop;

import com.zhelandovskiy.t1_aop_1.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
public class TestAspect {

    @Around("@within(com.zhelandovskiy.t1_aop_1.aop.annotation.TimeMetric)")
    public Object calculateTime(ProceedingJoinPoint joinPoint) {

        Object proceed;

        try {
            long start = System.currentTimeMillis();

            proceed = joinPoint.proceed();

            long end = System.currentTimeMillis();

            log.info("Time {}: {} ms", joinPoint.toShortString(), end - start);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return proceed;
    }

    @Before("within(com.zhelandovskiy.t1_aop_1.service.impl.TaskServiceImpl)")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Start method {}", joinPoint.toShortString());
    }

    @After("within(com.zhelandovskiy.t1_aop_1.service.impl.TaskServiceImpl)")
    public void logAfter(JoinPoint joinPoint) {
        log.info("End method {}", joinPoint.toShortString());
    }

    @AfterReturning(
            value = "@annotation(com.zhelandovskiy.t1_aop_1.aop.annotation.CalculateRecordsLog)",
            returning = "result")
    public void calculateRecord(List<?> result) {
        log.info("Records received: {}", result.size());
    }

    @AfterThrowing(
            value = "within(com.zhelandovskiy.t1_aop_1.service.impl.TaskServiceImpl)",
            throwing = "exception")
    public void exceptionsAdvice(TaskNotFoundException exception) {
        log.error("Getting exception: {}", exception.toString());
    }

}
