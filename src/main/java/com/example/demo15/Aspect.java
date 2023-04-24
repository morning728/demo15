package com.example.demo15;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@org.aspectj.lang.annotation.Aspect
public class Aspect {
    @Before("allServiceMethods()")
    public void logParameters(JoinPoint joinPoint) {
        log.info("Parameters: {}", joinPoint.getArgs());
    }
    @Pointcut("within(com.example.demo15.controllers.*)")
    public void allServiceMethods() {}


    @Pointcut("within(com.example.demo15.controllers.*)")
    public void executionServiceMethod() {}

    @Around("executionServiceMethod()")
    public Object timeExecutionServiceMethod(ProceedingJoinPoint jp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object object = jp.proceed();
        long end = System.currentTimeMillis();
        log.info(jp.getSignature().toShortString() + " completed in " + (end - begin) + " ms");
        return object;
    }

}
