package com.energy_tracker.user_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {
    @Pointcut("execution(* com.energy_tracker.user_service.controller.*.*(..))")
    public void controllerMethods(){}

    @Around("controllerMethods()")
    public Object aroundControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        try{
            return pjp.proceed();
        }
        finally{
            long end = System.currentTimeMillis();
            long time = end - start;
            String signature = pjp.getSignature().getName();
            log.info("Execution time for '{}' is {}ms.", signature, time);
        }
    }
}
