package com.company;

public class ManuWorker extends Thread {

    private String name;

    public ManuWorker(String name){
        this.name=name;
    }
    @Override
    public void run()  {
        for(int idx=0;idx<10;idx++){
            System.out.println("run "+name+"   "+idx);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
