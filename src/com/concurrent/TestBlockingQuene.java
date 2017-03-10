package com.concurrent;

import java.util.concurrent.*;


class QueneWorker implements Runnable {
    public static BlockingQueue<String> queue = new LinkedBlockingDeque<>(3);
    private int index;

    public QueneWorker(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        try {
            queue.put(String.valueOf(this.index));
            System.out.println("{" + this.index + "} in queue!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class TakeQueneWorker implements Runnable {

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep((int) (Math.random() * 1000));
                if(QueneWorker.queue.isEmpty())
                    break;
                String str = QueneWorker.queue.take();
                System.out.println(str + " has take!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class TestBlockingQuene {
    private static final int TASKCOUNT = 10;
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 1; i <= TASKCOUNT; i++) {
            service.submit(new QueneWorker(i));
        }
        service.submit(new TakeQueneWorker());
        service.shutdown();
    }
}
