package com.cui.study.web.spring.aop.aspectj;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 配置类，自动织入，使用注解的形式
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectConfigure2 {
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
