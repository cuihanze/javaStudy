package com.cui.java.study.web.spring.aop.springaop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 参考：https://openhome.cc/Gossip/SpringGossip/IntroductionInterceptor.html
 */
public class Client {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:beans-config.xml");
        ISome some = (ISome) applicationContext.getBean("proxyFactoryBean");
        some.doSome();

        ((IOther) some).doOther();
    }
}
/*
原有方法
新增方法
 */
