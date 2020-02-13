package com.cui.java.study.web.spring.aop.springaop.InstantiationAwareBeanPostProcessor;

public class Student {
    private int id;
    private String name;

    public Student() {
        System.out.println("调用实例化构造函数");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
