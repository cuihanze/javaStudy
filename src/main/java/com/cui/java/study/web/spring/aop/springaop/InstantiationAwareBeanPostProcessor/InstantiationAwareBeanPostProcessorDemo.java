package com.cui.java.study.web.spring.aop.springaop.InstantiationAwareBeanPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.beans.PropertyDescriptor;

public class InstantiationAwareBeanPostProcessorDemo implements InstantiationAwareBeanPostProcessor {
    /**
     * 实例化前调用：即创建bean，doCreateBean(beanName, mbdToUse, args); 之前执行，
     * @param beanClass
     * @param beanName
     * @return  这里如果返回的为非null，则不会调用doCreateBean(beanName, mbdToUse, args)方法创建对象，但会调用InstantiationAwareBeanPostProcessor#postProcessAfterInitialization进行初始化后的处理。
     *          返回null，会正常初始化
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        // 所有的bean创建过程中都会调用，但这里限制只修改指定beanName的属性
        if (!beanName.equalsIgnoreCase("chenglong")) {
            return null;
        }
        System.out.println("大哥：即将初始化");
        return null;
    }

    /**
     *实例化后调用：即设置属性时执行，在applyPropertyValues(beanName, mbd, bw, pvs)方法调用之前。也在本类postProcessPropertyValues方法之前执行
     * @param bean
     * @param beanName
     * @return  返回true，后续的属性继续设置；返回false，则调用完postProcessAfterInstantiation，直接返回，执行初始化操作
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        // 所有的bean创建过程中都会调用，但这里限制只修改指定beanName的属性
        if (!beanName.equalsIgnoreCase("chenglong")) {
            return true;
        }
        System.out.println("大哥：已经初始化完成");
        return true;
    }

    /**
     * 实例化后，设置属性前，修改属性值
     * @param pvs
     * @param pds
     * @param bean
     * @param beanName
     * @return  返回修改后的PropertyValues
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        // 所有的bean创建过程中都会调用，但这里限制只修改指定beanName的属性
        if (!beanName.equalsIgnoreCase("chenglong")) {
            return pvs;
        }

        System.out.println("大哥：修改属性值");

        PropertyValue[] propertyValues = pvs.getPropertyValues();
        if (propertyValues == null || propertyValues.length == 0) {
            return pvs;
        }

        for (PropertyValue propertyValue : propertyValues) {
            // 修改所有修改用户名
            if (propertyValue.getName().equalsIgnoreCase("name")) {
                propertyValue.setConvertedValue("大哥666");
            }
        }
        return pvs;
    }

    /**
     * 初始化前调用：即初始化之前执行，invokeInitMethods(beanName, wrappedBean, mbd);
     * @param bean
     * @param beanName
     * @return  这里除非特殊情况，返回给容器原始bean即可
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 所有的bean创建过程中都会调用，但这里限制只修改指定beanName的属性
        if (!beanName.equalsIgnoreCase("chenglong")) {
            return bean;
        }
        System.out.println("大哥：即将执行初始化操作");
        return bean;
    }

    /**
     * 初始化后调用：即初始化invokeInitMethods(beanName, wrappedBean, mbd)之后执行
     * 这里就是AbstractAutoProxyCreator生成动态代理类的地方！！
     * @param bean
     * @param beanName
     * @return  这里除非特殊情况，返回给容器原始bean即可
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 所有的bean创建过程中都会调用，但这里限制只修改指定beanName的属性
        if (!beanName.equalsIgnoreCase("chenglong")) {
            return bean;
        }
        System.out.println("大哥：初始化方法调用完成");
        return bean;
    }
}
