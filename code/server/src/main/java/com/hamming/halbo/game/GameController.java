package com.hamming.halbo.game;

import com.hamming.halbo.game.action.Action;

import java.util.Deque;
import java.util.LinkedList;

public class GameController implements Runnable {
    private Deque<Action> actionQueue;
    private boolean running = true;

    @Override
    public void run() {
        actionQueue = new LinkedList<Action>();
        while (running) {
            if (actionQueue.isEmpty()) {
                try {
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println("Exception : method wait was interrupted!");
                }
            }
            while (!actionQueue.isEmpty()) {
                Action cmd = actionQueue.removeFirst();
                cmd.execute();
            }
        }
    }


    public void addCommand(Action cmd) {
        actionQueue.addLast(cmd);
        synchronized (this) {
            this.notify();
        }
    }

}