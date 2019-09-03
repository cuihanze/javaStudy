package com.cui.study.web.spring.aop.aspectj;

public class Foo {
    @PerformaceAnnotation
    public void method1() {
        System.out.println("method1");
    }

    public void method2() {
        System.out.println("method2");
    }
}
