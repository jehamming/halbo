package com.hamming.halbo;

import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Queue;

public class ClientSender implements Runnable {


    private PrintWriter out;
    boolean running = false;
    private Queue<String> itemsToSend;
    private static int INTERVAL = 50; // Milliseconds, 20Hz

    public ClientSender(PrintWriter out) {
        this.out = out;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        itemsToSend = new ArrayDeque<String>();
        running = true;
        while (running) {
            long start = System.currentTimeMillis();
            while (!itemsToSend.isEmpty()) {
                String data = itemsToSend.remove();
                out.println(data);
            }
            long stop = System.currentTimeMillis();
            long timeSpent = start - stop;
            if (timeSpent < INTERVAL ) {
                try {
                    Thread.sleep(INTERVAL - timeSpent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        running = false;
    }

    public void enQueue(String s) {
        synchronized (itemsToSend) {
            itemsToSend.add(s);
        }
    }
}
