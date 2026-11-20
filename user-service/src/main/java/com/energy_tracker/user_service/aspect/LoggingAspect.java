package com.energy_tracker.user_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("execution(* com.energy_tracker.user_service.service.*.*(..))")
    public void serviceMethods(){}

    @Before("serviceMethods()")
    public void beforeServiceMethods(JoinPoint joinPoint){
        log.info("service method {}, with arguments {}"
                ,joinPoint.getSignature().getName(),joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "serviceMethods()",returning = "result")
    public void afterServiceMethods(JoinPoint joinPoint,Object result){
        log.info("service method {}, with result {}"
                ,joinPoint.getSignature().getName(),result);
    }
}
