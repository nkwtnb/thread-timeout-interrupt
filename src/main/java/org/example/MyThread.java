package org.example;

import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class MyThread implements Callable<String> {
    public String call () {
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
        return "from thread";
    }
}
