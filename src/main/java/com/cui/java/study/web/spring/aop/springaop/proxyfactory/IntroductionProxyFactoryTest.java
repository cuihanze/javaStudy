package com.cui.java.study.web.spring.aop.springaop.proxyfactory;

import com.cui.java.study.web.spring.aop.springaop.IOther;
import com.cui.java.study.web.spring.aop.springaop.ISome;
import com.cui.java.study.web.spring.aop.springaop.SomeImpl;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.cglib.core.DebuggingClassWriter;

// Introduction织入
public class IntroductionProxyFactoryTest {
    public static void main(String[] args) {
        // 生成JDK动态代理类，存储在类似$Proxy0.class文件中
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 生成CGLIB动态生成的代理类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "com/sun/CGLIBProxy");

        // 目标类
        ISome some = new SomeImpl();

        // ProxyFactory类，并设置目标类
        ProxyFactory proxyFactory = new ProxyFactory(some);

        // 切面拦截规则+拦截逻辑
        DefaultIntroductionAdvisor advisor = new DefaultIntroductionAdvisor(new TestIntroductionInterceptor(), IOther.class);

        // 设置切面
        proxyFactory.addAdvisors(advisor);

        // 执行织入，生成代理类
        Object proxyObject = proxyFactory.getProxy();

        // 原有方法
        ((ISome) proxyObject).doSome();
        // 新增方法
        ((IOther) proxyObject).doOther();
    }
}
/*
原有方法
add doOther method
 */
