package com.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by chengyz on 2017-03-03.
 */

class SemWorker implements Runnable {
    private int index;
    private Semaphore semaphore;

    public SemWorker(int index, Semaphore semaphore) {
        this.index = index;
        this.semaphore = semaphore;
    }
    @Override
    public void run() {
        try{
            if(semaphore.availablePermits()>0){
                System.out.println("顾客["+this.index+"]进入厕所，有空位");
            }
            else{
                System.out.println("顾客["+this.index+"]进入厕所，没空位，排队");
            }
            semaphore.acquire();
            System.out.println("顾客["+this.index+"]获得坑位");
            Thread.sleep((int)(Math.random()*1000));
            System.out.println("顾客["+this.index+"]使用完毕");
            semaphore.release();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

public class TestSemaphore {
    private static final int TASKCOUNT = 10;

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < TASKCOUNT; i++) {
            service.execute(new SemWorker(i, semaphore));
        }

        service.shutdown();
    }
}
