package com.hamming.halbo.game;

import com.hamming.halbo.game.action.Action;
import com.hamming.halbo.model.User;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GameController implements Runnable {
    private Deque<Action> actionQueue;
    private boolean running = true;
    private GameState gameState;

    public GameController() {
        gameState = new GameState();
    }

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

    public void userConnected(User u) {
        gameState.userConnected(u);
    }

    public void userDisconnected(User u) {
        gameState.userDisconnected(u);
    }


    public void addGameStateListener(GameStateListener l ) {
        gameState.addListener(l);
    }

    public void removeGameStateListener(GameStateListener l) {
        gameState.removeListener(l);
    }


    public void addCommand(Action cmd) {
        actionQueue.addLast(cmd);
        synchronized (this) {
            this.notify();
        }
    }

}