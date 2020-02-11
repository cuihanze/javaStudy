package com.cui.java.study.web.spring.aop.springaop;

// 目标类
public class SomeImpl implements ISome,IOther {
    @Override
    public void doSome() {
        System.out.println("do something");
    }

    @Override
    public void doSome2() {
        System.out.println("do something2");
    }

    @Override
    public void doOther() {
        System.out.println("do other");
    }
}
