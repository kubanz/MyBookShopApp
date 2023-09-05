package com.example.MyBookShopApp.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class ExceptionAspect {

    Logger log = Logger.getLogger(this.getClass().getName());

    @Pointcut("within(com.example.MyBookShopApp.*)")

    public void catchAllExceptionsPointCut() {
    }

    @AfterThrowing(value = "catchAllExceptionsPointCut()", throwing = "ex")
    public void catchAllExceptionsAdvice(Throwable ex) {
        log.info(ex.toString());
    }

}
