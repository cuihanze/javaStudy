package com.cui.java.study.web.spring.aop.springaop.InstantiationAwareBeanPostProcessor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:beans-config.xml");
        Student dage = (Student) applicationContext.getBean("chenglong");
        System.out.println("chenglong: " + dage);

        System.out.println();
        System.out.println("假大哥：");
        Student jiadage = (Student) applicationContext.getBean("jiachenglong");
        System.out.println("jiachenglong: " + jiadage);
    }
}
/*
大哥：即将初始化
调用实例化构造函数
大哥：已经初始化完成
大哥：修改属性值
大哥：即将执行初始化操作
大哥：初始化方法调用完成
调用实例化构造函数
chenglong: Student{id=1, name='大哥666'}

假大哥：
jiachenglong: Student{id=1, name='大哥'}
 */
