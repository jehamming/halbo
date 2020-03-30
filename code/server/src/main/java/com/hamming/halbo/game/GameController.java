package com.hamming.halbo.game;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.factories.*;
import com.hamming.halbo.game.action.Action;
import com.hamming.halbo.model.*;

import java.util.*;

public class GameController implements Runnable {
    private Deque<Action> actionQueue;
    private boolean running = true;
    private GameState gameState;
    private List<GameStateListener> listeners;

    public GameController() {
        gameState = new GameState();
        listeners = new ArrayList<GameStateListener>();
    }

    @Override
    public void run() {
        actionQueue = new ArrayDeque<Action>();
        while (running) {
            if (actionQueue.isEmpty()) {
                try {
                    synchronized (this) {
                        this.wait();
                    }
                } catch (InterruptedException e) {
                    System.out.println(this.getClass().getName() + ":" + "Exception : method wait was interrupted!");
                }
            }
            while (!actionQueue.isEmpty()) {
                Action cmd = actionQueue.removeFirst();
                cmd.execute();
            }
        }
    }

    public void userConnected(User u) {
        gameState.getOnlineUsers().add(u);
        fireGameStateEvent(GameStateEvent.Type.USERCONNECTED, u);
    }

    public void userDisconnected(User u) {
        boolean contained = gameState.getOnlineUsers().remove(u);
        if (contained) {
            fireGameStateEvent(GameStateEvent.Type.USERDISCONNECTED, u);
        }
    }


    public void addCommand(Action cmd) {
        actionQueue.addLast(cmd);
        synchronized (this) {
            this.notify();
        }
    }

    public GameState getGameState() {
        return gameState;
    }



    public Baseplate createBaseplate(String name, UserLocation l, CityGrid.Direction direction) {
        Baseplate newBaseplate = BaseplateFactory.getInstance().createBaseplate(name, l.getUser());
        try {
            l.getCity().getCityGrid().addBasePlate(newBaseplate, l.getBaseplate(), direction);
            UserLocation baseplateLocation = new UserLocation(null);
            baseplateLocation.setWorld(l.getWorld());
            baseplateLocation.setContinent(l.getContinent());
            baseplateLocation.setCity(l.getCity());
            baseplateLocation.setBaseplate(newBaseplate);
            fireGameStateEvent(GameStateEvent.Type.CITYBASEPLATEADDED, baseplateLocation);
        } catch (CityGridException e) {
            e.printStackTrace();
        }
        return newBaseplate;
    }


    private void fireGameStateEvent(GameStateEvent.Type type, BasicObject object) {
        for (GameStateListener l : listeners) {
            if (l == null) {
                // Should not happen, stale client connection..
                removeListener(l);
            } else {
                l.newGameState(new GameStateEvent(type, object));
            }
        }
    }

    public void addListener(GameStateListener l) {
        listeners.add(l);
    }

    public void removeListener(GameStateListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    public void setLocation(User u, UserLocation location) {
        gameState.setLocation(u, location);
        fireGameStateEvent(GameStateEvent.Type.USERLOCATION, location);
    }

    public void userTeleported(User u, UserLocation loc) {
        gameState.setLocation(u, loc);
        fireGameStateEvent(GameStateEvent.Type.USERTELEPORTED, loc);
    }
}