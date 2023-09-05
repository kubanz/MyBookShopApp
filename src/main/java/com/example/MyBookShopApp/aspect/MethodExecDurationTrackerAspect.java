package com.example.MyBookShopApp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Aspect
@Component
public class MethodExecDurationTrackerAspect {

   Logger log = Logger.getLogger(this.getClass().getName());

    private Long durationMillis;

    @Before(value = "execution(public String mainPage())")
    public void beforeDurationTrackingAdvice() {
        durationMillis = new Date().getTime();
        log.info("Duration tracking begins");
    }

    @After(value = "execution(public String mainPage())")
    public void afterDurationTrackingAdvice() {
        durationMillis = new Date().getTime() - durationMillis;
        log.info("Duration tracking ends in " + durationMillis + " ms");
    }

    @After(value = "within(com.example.MyBookShopApp.data.BookService)")
    public void beforeDurationTrackingAdvice(JoinPoint joinPoint) {
        log.info("Author: {}" + joinPoint.getArgs());
    }

    @Pointcut(value = "execution(* book*())")
    public void allBookInfoMethods() {
    }

    @After("allBookInfoMethods()")
    public void afterAllBookInfoMethodsAdvice() {
        log.info("Advice to all book info methods...");
    }

    @Pointcut(value = "within(com.example.MyBookShopApp.service.*)")
    public void allServicesMethods() {
    }

    @After("allServicesMethods()")
    public void afterAllServicesMethodsAdvice(JoinPoint joinPoint) {
        log.info("Advice to all services methods...  {}" + joinPoint.getTarget().getClass().getName() + " " + joinPoint.getSignature().getName());
    }
}
