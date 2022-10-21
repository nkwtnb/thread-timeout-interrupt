package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        MyThread t = new MyThread();
        // when main thread exit, also daemon thread exit
        t.setDaemon(true);
        t.start();
        long start = System.currentTimeMillis();
        while(true) {
            long time = System.currentTimeMillis() - start;
            if (time > 3000) {
                System.out.println("time out");
                t.interrupt();
                break;
            }
            if (!t.isAlive()) {
                break;
            }
        }
        System.out.println("end");
    }
}