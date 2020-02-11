package com.cui.java.study.web.spring.aop.springaop.proxyfactory;

import com.cui.java.study.web.spring.aop.springaop.IOther;
import com.cui.java.study.web.spring.aop.springaop.ISome;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanNameAutoProxyCreatorTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:beans-config.xml");
        Object proxy = applicationContext.getBean("some");
        ISome some = (ISome) proxy;
        some.doSome();
        IOther other = (IOther) proxy;
        other.doOther();
    }
}
/*
执行前
do something
执行后
执行前
do other
执行后
 */
