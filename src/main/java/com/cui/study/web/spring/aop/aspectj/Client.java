package com.cui.study.web.spring.aop.aspectj;

import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 客户端测试类
 * 参考：https://juejin.im/post/5bf8a505f265da61682b0918
 */
public class Client {
    public static void main(String[] args) {
        // 生成JDK动态代理类，存储在类似$Proxy0.class文件中
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        // 生成CGLIB动态生成的代理类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "com/sun/CGLIBProxy");

        //manualWeaver();

        //autoWeaver();

        autoWeaverByAnnotation();
    }

    /**
     * 手动织入
     * 输出为：
     * +++++++++@annotation++++++++++
     * method1
     * cost time 25
     * method2
     * cost time 0
     */
    public static void manualWeaver() {
        // 创建织入类
        AspectJProxyFactory weaver = new AspectJProxyFactory();
        weaver.setProxyTargetClass(true);

        // 设置目标类
        weaver.setTarget(new Foo());

        // 设置切面
        weaver.addAspect(new PerformanceTranceAspect());

        // 获取代理类
        Foo foo = weaver.getProxy();

        // 执行代理方法
        foo.method1();
        foo.method2();
    }

    /**
     * 自动织入
     * 输出为：
     * +++++++++@annotation++++++++++
     * method1
     * cost time 25
     * method2
     * cost time 0
     */
    public static void autoWeaver() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AspectConfigure1.class);
        Foo foo = context.getBean(Foo.class);

        foo.method1();
        foo.method2();
    }

    /**
     * 使用注解，自动织入
     * 输出为：
     * +++++++++@annotation++++++++++
     * method1
     * cost time 21
     * method2
     * cost time 0
     */
    public static void autoWeaverByAnnotation() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AspectConfigure2.class);
        Foo foo = context.getBean(Foo.class);

        foo.method1();
        foo.method2();
    }
}

