package com.cui.java.study.web.spring.aop.springaop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// 中介类
public class JDKDynamicProxy implements InvocationHandler {
    private Object target;

    public JDKDynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object result = method.invoke(target, args);
        System.out.println("after");
        return result;
    }
}
