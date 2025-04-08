package com.asmadiya.student.aspect;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.asmadiya.student..*(..)) && !within(org.springframework.web.filter.GenericFilterBean+)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("Method Called: {}", methodName);
        logger.info("Arguments: {}", Arrays.toString(args));

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        logger.info("Method Result: {}", result);
        logger.info("Execution Time: {} ms", (endTime - startTime));

        return result;
    }
}
