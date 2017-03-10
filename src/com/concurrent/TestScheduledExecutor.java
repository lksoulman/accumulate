package com.concurrent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by chengyz on 2017-03-03.
 */

class BeepWorker implements Runnable {
    private String name;

    public BeepWorker(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        int count = 0;
        System.out.println(new Date() + name + (++count));
    }
}

class EndWorker implements Runnable {
    private List<ScheduledFuture> list;
    private ScheduledExecutorService scheduler;

    public EndWorker(ScheduledExecutorService scheduler, List<ScheduledFuture> list) {
        this.scheduler = scheduler;
        this.list = list;
    }

    @Override
    public void run() {
        for (int i = 0; i < list.size() ; i++) {
            list.get(i).cancel(true);
        }
        scheduler.shutdown();
    }
}

public class TestScheduledExecutor {

    public static void main(String[] args) {
        List<ScheduledFuture> list = new ArrayList<ScheduledFuture>();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        System.out.println(new Date() + " start ");
        list.add(scheduler.scheduleAtFixedRate(new BeepWorker(" scheduleAtFixedRate "), 1, 2, SECONDS));
        list.add(scheduler.scheduleWithFixedDelay(new BeepWorker(" scheduleWithFixedDelay "), 2, 5, SECONDS));
        scheduler.schedule(new EndWorker(scheduler, list), 30, SECONDS);
    }
}
