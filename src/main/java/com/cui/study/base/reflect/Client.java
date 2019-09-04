package com.cui.study.base.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class Client {
    public static void main(String[] args) {
        /**
         * 创建对象的三种方法
         */
        Person person = null;
        // 1. 通过 new
        person = new Person(1L, "小崔1");
        System.out.println(person);
        // 2. 通过 Class 对象
        // Class clazz = Person.class;
        Class clazz = null;
        try {
            clazz = Class.forName("com.cui.study.base.reflect.Person");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            person = (Person) clazz.newInstance();
            System.out.println(person);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 通过 clone 方法，暂时不实现

        /**
         * 反射部分：
         * 对象由属性和方法组成，方法由分为构造方法和普通方法
         */
        // 1. 属性
        Class personClazz = Person.class;

        // 1.1 根据属性名获取有权限访问的属性，这里只能获取 Person 的 desc 属性
        try {
            Field field = personClazz.getField("desc");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // 1.2 获取所有有权限访问的属性，这里只能获取 Person 的 desc 属性
        Field[] fields = personClazz.getFields();

        // 1.3 根据属性名获取属性，这里可以获取所有属性
        try {
            Field field = personClazz.getDeclaredField("name");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // 1.4 获取所有属性，这里可以获取所有属性
        Field[] fields1 = personClazz.getDeclaredFields();

        // 1.5 Filed 对象使用
        Field field = fields1[0];
        String name = field.getName();// 属性名
        Class clazz1 = field.getDeclaringClass();// 属性定义类/接口
        Annotation[] annotations = field.getAnnotations();// 获取属性注解
        try {
            field.setAccessible(true);// 修改属性访问权限
            field.set(person, 2);// 设置属性值
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(person);

        try {
            long id = (long) field.get(person);// 获取属性值
            System.out.println(id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 2. 构造函数
        // 2.1 获取有权限的构造函数
        try {
            Constructor constructor = clazz.getConstructor();// 获取无参构造函数 Person()
            constructor = clazz.getConstructor(long.class, String.class);// 获取有参构造函数 Person(long id, String name)
            Constructor[] constructors = clazz.getConstructors();// 获取所有构造函数，Person() 和 Person(long id, String name)
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        // 2.2 获取构造函数
        try {
            Constructor constructor = clazz.getDeclaredConstructor();// 获取无参构造函数 Person()
            constructor = clazz.getDeclaredConstructor(long.class, String.class, String.class);// 获取有参构造函数 Person(long id, String name, String desc)
            Constructor[] constructors = clazz.getDeclaredConstructors();// // 获取所有构造函数，Person() 、 Person(long id, String name) 和 Person(long id, String name, String desc)
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 2.3 构造函数使用
        try {
            Constructor constructor = clazz.getDeclaredConstructor(long.class, String.class, String.class);
            Annotation[] annotations1 = constructor.getAnnotations();// 所有注解
            String name1 = constructor.getName();// 构造函数名称
            Class clazz2 = constructor.getDeclaringClass();// 对象的Class
            constructor.setAccessible(true);// 修改访问类型
            Person person1 = (Person) constructor.newInstance(3, "小崔", "程序猿");
            System.out.println(person1);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 3. 普通函数
        // 3.1 获取有权限的方法
        try {
            Method method = clazz.getMethod("getId");// 获取无参方法
            method = clazz.getMethod("setId", long.class);// 获取有参方法
            Method[] methods = clazz.getMethods();// 获取所有方法
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 3.2 获取所有方法
        try {
            Method method = clazz.getDeclaredMethod("getDesc");
            method = clazz.getDeclaredMethod("setDesc", String.class);
            Method[] methods = clazz.getMethods();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        // 3.3 method的使用
        try {
            Method method = clazz.getDeclaredMethod("setDesc", String.class);
            Class clazz2 = method.getDeclaringClass();// 定义类的class对象
            method.setAccessible(true);// 修改方法权限
            method.invoke(person, "描述");// 调用方法
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
