package com.cui.java.study.base.test;

public class StudentImpl implements IStudent {
    private int identity;
    private static String countNum;
    private static final String ss = "a";

    @Override public int getId() {
        return 0;
    }

    public String getName() {
        String result = identity + countNum;
        return result;
    }

}
