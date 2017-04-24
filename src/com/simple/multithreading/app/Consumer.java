package com.simple.multithreading.app;

public class Consumer implements Runnable {

    private final Storage storage;
    private int consumerID;

    public Consumer(Storage storage, int consumerID) {
        this.storage = storage;
        this.consumerID = consumerID;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; ++i) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Consumer number " + consumerID + "has just read this: " + storage.read(consumerID));
        }
    }
}