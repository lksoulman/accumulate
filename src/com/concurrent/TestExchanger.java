package com.concurrent;

import java.util.ArrayList;
import java.util.concurrent.Exchanger;


class AWorker extends Thread {
    private ArrayList<Integer> list;
    private Exchanger<ArrayList<Integer>> exchanger;

    AWorker(Exchanger<ArrayList<Integer>> exchanger, ArrayList<Integer> list) {
        this.exchanger = exchanger;
        this.list = list;
    }

    @Override
    public void run() {
        ArrayList<Integer> exchangelist = list;
        try {
            while (true) {
                if (exchangelist.size() >= 10) {
                    exchangelist = exchanger.exchange(exchangelist);
                    System.out.println("exchanger listA");
                    exchangelist.clear();
                }
                exchangelist.add((int)(Math.random()*100));
                Thread.sleep((long)(Math.random()*1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class BWorker extends Thread {
    private ArrayList<Integer> list;
    private Exchanger<ArrayList<Integer>> exchanger;

    BWorker(Exchanger<ArrayList<Integer>> exchanger, ArrayList<Integer> list) {
        this.exchanger = exchanger;
        this.list = list;
    }

    @Override
    public void run() {
        ArrayList<Integer> exchangelist = list;
        while(true){
            try {
                for(Integer i: exchangelist){
                    System.out.println(i);
                }
                Thread.sleep(1000);
                exchangelist = exchanger.exchange(exchangelist);
                System.out.println("exchange listB");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class TestExchanger {

    public static void main(String[] args) {
        final Exchanger<ArrayList<Integer>> exchanger = new Exchanger<ArrayList<Integer>>();
        final ArrayList<Integer> listA = new ArrayList<Integer>();
        final ArrayList<Integer> listB = new ArrayList<Integer>();
        AWorker aworker = new AWorker(exchanger, listA);
        BWorker bWorker = new BWorker(exchanger, listB);

        aworker.start();
        bWorker.start();
    }
}
