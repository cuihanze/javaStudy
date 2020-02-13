package com.cui.java.study.web.spring.aop.springaop.proxyfactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

// 切面执行逻辑
public class ProxyMethdInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("执行前");
        try {
            return invocation.proceed();
        }finally {
            System.out.println("执行后");
        }

    }
}
