package com.cui.java.study.web.spring.aop.springaop.proxyfactory;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.cglib.core.DebuggingClassWriter;

// 基于类的织入 - CGLIB动态代理
public class ClassProxyFactoryTest {
    public static void main(String[] args) {
        // 生成JDK动态代理类，存储在类似$Proxy0.class文件中
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 生成CGLIB动态生成的代理类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "com/sun/CGLIBProxy");

        // 1. 创建目标类
        Test test = new Test();

        // 2. 创建ProxyFactory
        ProxyFactory proxyFactory = new ProxyFactory(test);

        // 3. 创建切面
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.setMappedName("test");
        advisor.setAdvice(new ProxyMethdInterceptor());

        // 4. ProxyFactory设置切面
        proxyFactory.addAdvisor(advisor);

        // 5. 执行织入，返回代理类
        Test proxyTest = (Test) proxyFactory.getProxy();

        // 6. 执行代理类的方法
        proxyTest.test();
    }
}
/*
CGLIB debugging enabled, writing to 'com/sun/CGLIBProxy'
执行前
do something
执行后
 */
