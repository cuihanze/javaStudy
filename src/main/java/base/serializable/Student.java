package base.serializable;

import java.io.Serializable;

public class Student implements Serializable {
    // 显式设置 serialVersionUID 的值，防止反序列化失败
    private static final long serialVersionUID = 8872628391027409536L;

    private int id;
    private String name;
    private transient String grade;// 班级，使用 transient 修饰，不会被序列化

    public Student(int id, String name, String grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    public Student() {
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
