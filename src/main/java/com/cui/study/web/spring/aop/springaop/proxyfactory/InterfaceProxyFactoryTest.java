package com.cui.study.web.spring.aop.springaop.proxyfactory;

import com.cui.study.web.spring.aop.springaop.ISome;
import com.cui.study.web.spring.aop.springaop.SomeImpl;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.cglib.core.DebuggingClassWriter;

// 基于接口的织入 - JDK动态代理
public class InterfaceProxyFactoryTest {
    public static void main(String[] args) {
        // 生成JDK动态代理类，存储在类似$Proxy0.class文件中
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 生成CGLIB动态生成的代理类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "com/sun/CGLIBProxy");

        // 1. 创建目标类
        ISome some = new SomeImpl();

        // 2. 实例化ProxyFactory，并设置目标类
        ProxyFactory proxyFactory = new ProxyFactory(some);


        // 3. 指定要织入的接口
        proxyFactory.setInterfaces(ISome.class);// 显式设置代理接口，使用JDK动态代理

        // 4. 设置ProxyFactory属性
//        proxyFactory.setOptimize(true); // 设置为true，则无论是否实现接口，都会走CGLIB代理，此参数是因为老的jdk版本，JDK动态代理比CGLIB慢，不过现在JDK动态代理比CGLIB快，所以此参数不太实用
//        proxyFactory.setProxyTargetClass(true); // 设置为true，则会走CGLIB代理

        // 5. 定义切面
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        // 切面拦截规则
        advisor.setMappedName("doSome");
        // 指定切面逻辑
        advisor.setAdvice(new ProxyMethdInterceptor());

        // 6. 将切面添加到 ProxyFactory 实例中
        proxyFactory.addAdvisor(advisor);

        // 7. 执行织入，返回织入后的代理实例
        ISome some1 = (ISome) proxyFactory.getProxy();

        // 8. 调用接口，此时的执行逻辑为织入的切面逻辑
        some1.doSome();
        System.out.println(some);
        System.out.println(some1);

        some1.doSome2();
    }
}
/*
执行前
原有方法
执行后
do other
com.dianping.baby.activity.zebraStudy.SomeImpl@a8c1f44
com.dianping.baby.activity.zebraStudy.SomeImpl@a8c1f44
 */