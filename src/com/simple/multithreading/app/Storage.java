package com.simple.multithreading.app;

public class Storage {

    private int consumers;
    private String lastMessage;

    public Storage() {
        lastMessage = "EMPTY";
        consumers = 0;
    }

    public String read(int consumerID) {
        synchronized(this) {
            consumers++;
            System.out.println("Consumer number " + consumerID + " is reading.");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized(this) {
            consumers--;
            System.out.println("Consumer number " + consumerID + " has just stopped reading.");
            if (consumers == 0) {
                notifyAll();
            }

            return lastMessage;
        }
    }

    public synchronized void write(String message) {
        while (consumers != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Producer is waiting.");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lastMessage = message;
        System.out.println("Producer has just stopped writing this: " + lastMessage);
        notifyAll();
    }
}