package com.cui.study.web.spring.aop.springaop.proxyfactory;

// 目标类
public class Test {
    public void test() {
        System.out.println("do something");
    }

    public void test(String param) {
        System.out.println("do something with param");
    }
}
