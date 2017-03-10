package com.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class TestWorker implements Runnable {
    private int index;

    public TestWorker(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        System.out.println("["+this.index+"] start....");
        try {
            Thread.sleep((int)(Math.random()*10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("["+this.index+"] end.");
    }
}

public class TestFixedThreadPool {
    private static final int THREADCOUNT = 4;
    private static final int TASKCOUNT = 8;

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(THREADCOUNT);
        for (int i = 1; i <= TASKCOUNT; i++) {
            service.execute(new TestWorker(i));
        }
        service.shutdown();
    }
}
