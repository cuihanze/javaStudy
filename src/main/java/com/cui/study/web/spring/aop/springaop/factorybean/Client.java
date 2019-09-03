package com.cui.study.web.spring.aop.springaop.factorybean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:beans-config.xml");
        Person person = (Person) applicationContext.getBean("personBeanFactory");
        System.out.println(person);
    }
}
