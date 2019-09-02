package base.serializable;

import java.io.*;

public class ExternalizableTest {
    public static void main(String[] args) {
        Teacher teacher = new Teacher(1, "xiaocui", "三年级");
        try (ObjectOutputStream objectOutput = new ObjectOutputStream(new FileOutputStream("file/teacher"))) {
            // 使用 ObjectOutputStream 序列化
            System.out.println("序列化数据：" + teacher);
            objectOutput.writeObject(teacher);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("file/teacher"))) {
            // 使用 ObjectInputStream 反序列化
            Teacher object = (Teacher) objectInputStream.readObject();
            System.out.println("反序列化数据：" + object);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
/*
序列化数据：Teacher{id=1, name='xiaocui', grade='三年级'}
反序列化数据：Teacher{id=1, name='xiaocui', grade='三年级'}
 */
