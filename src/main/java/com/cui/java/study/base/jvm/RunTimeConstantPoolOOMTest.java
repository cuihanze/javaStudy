package com.cui.java.study.base.jvm;

import java.util.ArrayList;
import java.util.List;

public class RunTimeConstantPoolOOMTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
