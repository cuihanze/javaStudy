package com.cui.study.web.spring.aop.aspectj;

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，自动织入
 */
@Configuration
public class AspectConfigure1 {
    /**
     * 自动织入器
     * @return
     */
    @Bean
    public AnnotationAwareAspectJAutoProxyCreator proxyCreator() {
        AnnotationAwareAspectJAutoProxyCreator proxyCreator = new AnnotationAwareAspectJAutoProxyCreator();
        // 默认为false，如果目标对象未实现接口的话，其代理对象也是通过cglib生成
        proxyCreator.setProxyTargetClass(false);
        return proxyCreator;
    }

    /**
     * 切面
     * @return
     */
    @Bean
    public PerformanceTranceAspect performanceTranceAspect() {
        return new PerformanceTranceAspect();
    }

    /**
     * 目标类
     * @return
     */
    @Bean
    public Foo foo() {
        return new Foo();
    }
}
