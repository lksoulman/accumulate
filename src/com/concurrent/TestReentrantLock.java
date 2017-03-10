package com.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

class ReentrantWorker implements Runnable {
    private int index;
    private ReentrantLock lock;

    public ReentrantWorker(int index, ReentrantLock lock) {
        this.index = index;
        this.lock = lock;
    }
    @Override
    public void run() {
        try{
            lock.lock();
            System.out.println(Thread.currentThread().getName() +"获得");
            Thread.sleep((int)(Math.random()*1000));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            System.out.println(Thread.currentThread().getName()+"释放");
            lock.unlock();
        }
    }
}

public class TestReentrantLock {
    private static final int TASKCOUNT = 10;

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < TASKCOUNT; i++) {
            service.execute(new ReentrantWorker(i, lock));
        }
        service.shutdown();
    }
}
