package com.cui.study.web.spring.aop.springaop.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

// 实现cglib的方法拦截器接口
public class CGLIBDynamicProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("代理前输出");
        try{
            return methodProxy.invokeSuper(o, objects);
        }finally {
            System.out.println("代理后输出");
        }
    }
}
