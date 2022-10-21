package org.example;

public class MyThread extends Thread{
    public void run () {
        System.out.println("Hello, from thread");
        try {
            System.out.println("1");
            sleep(1000);
            System.out.println("2");
            sleep(1000);
            System.out.println("3");
            sleep(1000);
            System.out.println("4");
            sleep(1000);
            System.out.println("5");
            sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
