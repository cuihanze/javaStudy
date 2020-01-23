package com.cui.study.base.io.test.nio;

public class Test {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                new BlockClass().test1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try {
                new BlockClass().test2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

class BlockClass {
    public void test1() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            System.out.println("test1" + i);
            Thread.sleep(10000);
        }
    }

    public void test2() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            System.out.println("test2" + i);
        }
    }
}
