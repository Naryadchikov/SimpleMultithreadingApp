package com.simple.multithreading.app;

public class Storage {

    private volatile int activeConsumers;
    private volatile boolean isProducerWaiting;
    private String lastMessage;

    public Storage() {
        lastMessage = "EMPTY";
        activeConsumers = 0;
        isProducerWaiting = false;
    }

    public String read(int consumerID) {
        synchronized(this) {
            if (isProducerWaiting) {
                System.out.println("Consumer number " + consumerID + " is waiting.");
                while (isProducerWaiting) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            activeConsumers++;
            System.out.println("Consumer number " + consumerID + " is reading.");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized(this) {
            activeConsumers--;
            System.out.println("Consumer number " + consumerID + " has just stopped reading.");
        }

        try {
            return lastMessage;
        } finally {
            synchronized (this) {
                if (activeConsumers == 0) {
                    notifyAll();
                }
            }
        }
    }

    public void write(String message) {
        isProducerWaiting = true;
        System.out.println("Producer is waiting.");

        synchronized (this) {
            while (activeConsumers != 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lastMessage = message;
        System.out.println("Producer has just stopped writing this: " + lastMessage);
        isProducerWaiting = false;

        synchronized (this) {
            notifyAll();
        }
    }
}