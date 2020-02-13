package com.cui.java.study.base.serializable;

import java.io.*;

/**
 * 序列化测试类
 */
public class SerializableTest {
    public static void main(String[] args) {
        Student student = new Student(1, "xiaocui", "三年级");

        // 使用 Java try-with-resource 语法糖 简化代码
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("file/student"))) {
            // 使用 ObjectOutputStream 序列化
            System.out.println("序列化的数据：" + student);
            objectOutputStream.writeObject(student);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("file/student"))) {
            // 使用 ObjectInputStream 反序列化
            Student object = (Student) objectInputStream.readObject();
            System.out.println("反序列化的数据：" + object);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
/*
序列化的数据：Student{id=1, name='xiaocui', grade='三年级'}
反序列化的数据：Student{id=1, name='xiaocui', grade='null'}
 */
