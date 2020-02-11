package com.cui.java.study.web.spring.aop.springaop.proxyfactory;

import com.cui.java.study.web.spring.aop.springaop.IOther;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;

// Introduction织入切面逻辑
public class TestIntroductionInterceptor implements IntroductionInterceptor, IOther {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!implementsInterface(invocation.getMethod().getDeclaringClass())) {
            // 非新增类的原有方法，直接执行
            return invocation.proceed();
        }
        // 新增接口的方法，执行新增方法
        return invocation.getMethod().invoke(this, invocation.getArguments());
    }
    @Override
    public boolean implementsInterface(Class<?> intf) {
        // 是否 IOther 实现类
        return intf.isAssignableFrom(IOther.class);
    }

    // 新增方法
    @Override
    public void doOther() {
        System.out.println("add doOther method");
    }
}
