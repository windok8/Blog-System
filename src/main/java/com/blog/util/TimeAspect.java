package com.blog.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeAspect {

    @Around("execution(* com.blog.controller.*.*(..))")
    public Object recordTime(ProceedingJoinPoint point) throws Throwable {
        long begin = System.currentTimeMillis();
        Object result = point.proceed();

        long end = System.currentTimeMillis();
        long time = end - begin;
        System.out.println("time = " + time);
        return result;
    }

}
