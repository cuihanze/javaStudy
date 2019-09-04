package com.cui.study.base.reflect;

public class Person {
    private long id;
    private String name;
    public String desc;

    private String getDesc() {
        return desc;
    }

    private void setDesc(String desc) {
        this.desc = desc;
    }

    public Person() {
    }

    public Person(long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Person(long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
