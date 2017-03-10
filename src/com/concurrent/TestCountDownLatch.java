package com.concurrent;


import java.util.concurrent.CountDownLatch;

/**
 * Created by chengyz on 2017-03-02.
 */
public class TestCountDownLatch {
    private static final int COUNT = 10;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch doneSignal = new CountDownLatch(COUNT);
        CountDownLatch startSignal = new CountDownLatch(1);

        for (int i = 1; i <= COUNT; i++) {
            new Thread(new Worker(i, doneSignal, startSignal)).start();//线程启动了
        }

        System.out.println("begin------------");
        startSignal.countDown();//开始执行啦
        try {
            doneSignal.await();//等待所有的线程执行完毕
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ok");
    }

    static class Worker implements Runnable {
        private final CountDownLatch doneSignal;
        private final CountDownLatch startSignal;
        private int beginIndex;

        public Worker(int beginIndex, CountDownLatch doneSignal,
                      CountDownLatch startSignal) {
            this.startSignal = startSignal;
            this.beginIndex = beginIndex;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                startSignal.await(); //等待开始执行信号的发布
                beginIndex = (beginIndex - 1) * 10 + 1;
                for (int i = beginIndex; i < beginIndex + 10; i++) {
                    System.out.println(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                doneSignal.countDown();
            }
        }
    }
}
