package com.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



class BarrierWorker implements Runnable {

    @Override
    public void run() {
        System.out.println("线程哥都到了，走，一起 SPA... 一起 Happy...");
    }
}

class HappyWorker implements Runnable {
    private Random random;
    private CyclicBarrier cyclicBarrier;

    public HappyWorker(Random random, CyclicBarrier cyclicBarrier) {
        this.random = random;
        this.cyclicBarrier = cyclicBarrier;
    };

    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "到了，其他哥们呢");
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}

public class TestCyclicBarrier {
    private static int COUNT = 10;

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Random random = new Random();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(COUNT, new BarrierWorker());

        for (int i = 0; i < COUNT; i++) {
            service.execute(new HappyWorker(random, cyclicBarrier));
        }
        service.shutdown();
    }
}
