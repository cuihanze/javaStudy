package com.cui.study.web.spring.aop.springaop;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;

/**
 * IntroductionInterceptor是一种特殊的拦截器，用于新增方法
 */
public class OtherIntroduction implements IOther, IntroductionInterceptor {
    @Override
    public void doOther() {
        System.out.println("新增方法");
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (implementsInterface(invocation.getMethod().getDeclaringClass())) {
            // 判断通过，执行新增方法
            return invocation.getMethod().invoke(this, invocation.getArguments());
        } else {
            // 判断未通过，执行非新增方法
            return invocation.proceed();
        }
    }

    /**
     * 判断是否实现给定的接口
     * @param intf
     * @return
     */
    @Override
    public boolean implementsInterface(Class<?> intf) {
        return intf.isAssignableFrom(IOther.class);
    }
}
