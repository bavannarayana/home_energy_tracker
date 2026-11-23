package com.energy_tracker.device_service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Slf4j
public class DeviceServiceAspect {
    @Pointcut("execution(* com.energy_tracker.user_service.service.*.*(..)")
    public void serviceMethods(){}

    @Before("serviceMethods()")
    public void beforeServiceMethod(JoinPoint joinPoint) throws Throwable {
        log.info("service method with {}, arugements {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void afterServiceMethod(ProceedingJoinPoint pjp, Object result) throws Throwable {
        log.info("service method with {}, arugements {}", pjp.getSignature().getName(), result);
    }
}
