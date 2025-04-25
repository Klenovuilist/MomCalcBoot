package com.ev.momcalcboot.service.internal;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@Aspect
@Slf4j
public class AspectClass {

    @Pointcut("@annotation(com.ev.momcalcboot.service.internal.SoutAOP)")
    public void soutText(){}

    @Before("soutText()")
    public void addText(){
//      log.info("Aspect_123_226_ready");
        System.out.println("Вызов метода");
    }

}
