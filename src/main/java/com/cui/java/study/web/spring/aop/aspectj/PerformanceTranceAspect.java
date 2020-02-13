package com.cui.java.study.web.spring.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 切面
 */
@Aspect
public class PerformanceTranceAspect {
    // 正则匹配切点
    @Pointcut("execution(* *..*method1()) || execution(* *..*method2())")
    public void pointcutName() {

    }

    // 匹配注解切点
    @Pointcut("@annotation(com.cui.java.study.web.spring.aop.aspectj.PerformaceAnnotation)")
    public void matchPointcut() {

    }

    // before 通知
    @Before("matchPointcut()")
    public void before(JoinPoint joinPoint) {
        // System.out.println(joinPoint.getSignature().getName());
        System.out.println("+++++++++@annotation++++++++++");
    }

    // around 通知
    @Around("pointcutName()")
    public Object performanceTrance(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            System.out.println(String.format("cost time %s", System.currentTimeMillis() - start));
        }
    }
}
