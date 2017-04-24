package com.simple.multithreading.app;

public class Launcher {

    public static void main(String[] args) {
        Storage storage = new Storage();

        new Thread(new Producer(storage)).start();

        for (int i = 0; i < 5; i++) {
            new Thread(new Consumer(storage, i)).start();
        }
    }
}