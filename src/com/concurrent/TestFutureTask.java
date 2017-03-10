package com.concurrent;

import java.util.concurrent.*;


class CallWorker implements Callable<String> {

    @Override
    public String call() throws Exception {
        return Thread.currentThread().getName();
    }
}

public class TestFutureTask {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        FutureTask<String> task = new FutureTask<String>(new CallWorker());
        service.execute(task);
        try {
            String result = task.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
