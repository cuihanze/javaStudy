package com.cui.java.study.web.spring.aop.springaop.proxy;

import com.cui.java.study.web.spring.aop.springaop.ISome;
import com.cui.java.study.web.spring.aop.springaop.SomeImpl;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

public class Client {
    public static void main(String[] args) {
        /*
        JDK 动态代理
         */
        // 生成类似$Proxy0.class文件，存储动态生成的动态类
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        ISome some = new SomeImpl();
        // 创建代理类
        ISome proxy = (ISome) Proxy.newProxyInstance(some.getClass().getClassLoader(), some.getClass().getInterfaces(), new JDKDynamicProxy(some));
        // 执行代理类
        proxy.doSome();

        /*
        CGLIB动态代理
         */
        // 生成CGLIB动态生成的代理类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "com/sun/CGLIBProxy");
        SomeImpl some1 = new SomeImpl();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(some1.getClass());
        enhancer.setCallback(new CGLIBDynamicProxy());
        SomeImpl proxy1 = (SomeImpl) enhancer.create();
        proxy1.doSome();
    }
}
/*
before
原有方法
after
代理前输出
原有方法
代理后输出
 */
