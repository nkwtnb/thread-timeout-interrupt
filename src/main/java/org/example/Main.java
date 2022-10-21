package org.example;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ThreadFactory daemon = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                // when main thread exit, also daemon thread exit
                t.setDaemon(true);
                return t;
            }
        };
        ExecutorService es = Executors.newSingleThreadExecutor(daemon);
        try {
            Future<String> future = es.submit(new MyThread());
            try {
                String result = future.get(6, TimeUnit.SECONDS);
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException te) {
                System.out.println("timeout!");
            }
        } finally {
            es.shutdown();
        }
        System.out.println("end");
    }
}