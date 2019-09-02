package base.serializable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Teacher implements Externalizable {
    // 显式设置 serialVersionUID 的值，防止反序列化失败
    private static final long serialVersionUID = 4067976636446149653L;

    private int id;
    private String name;
    private transient String grade;// 使用transient修饰，使用ObjectOutput的writeObject方法可强制序列化

    public Teacher() {
    }

    public Teacher(int id, String name, String grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
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
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
        out.writeObject(name);
        out.writeObject(grade);// 强制序列化
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readInt();
        Object object = in.readObject();
        name = object == null ? null : (String) object;
        Object gradeObj = in.readObject();
        grade = gradeObj == null ? null : (String) gradeObj;// 反序列化
    }
}

