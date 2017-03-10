package com.concurrent;

import java.util.concurrent.*;


class CompletionWorker implements Callable<String> {
    private int index;

    public CompletionWorker(int index) {
        this.index = index;
    }

    @Override
    public String call() throws Exception {
        Integer time=(int)(Math.random()*1000);
        try {
            System.out.println(this.index+" start");
            Thread.sleep(time);
            System.out.println(this.index+" end");
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.index+":"+time;
    }
}

public class TestCompletionService {
    private static final int TASKCOUNT = 10;

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        CompletionService<String> completionService = new ExecutorCompletionService<String>(service);
        for (int i = 0; i < TASKCOUNT; i++) {
            completionService.submit(new CompletionWorker(i));
        }
        for(int i = 0; i < TASKCOUNT; i++){
            try {
                System.out.println(completionService.take().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        service.shutdown();
    }
}
