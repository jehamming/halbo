package com.hamming.halbo.game;

import com.hamming.halbo.game.cmd.ProtocolCommand;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class GameController implements Runnable {
    private Deque<ProtocolCommand> commandsQueue;
    private boolean running = true;

    @Override
    public void run() {
        commandsQueue = new LinkedList<ProtocolCommand>();
        while (running) {
            if (commandsQueue.isEmpty()) {
                try {
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println("Exception : method wait was interrupted!");
                }
            }
            while (!commandsQueue.isEmpty()) {
                ProtocolCommand cmd = commandsQueue.removeFirst();
                cmd.execute();
            }
        }
    }


    public void addCommand(ProtocolCommand cmd) {
        commandsQueue.addLast(cmd);
        synchronized (this) {
            this.notify();
        }
    }

}