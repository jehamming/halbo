package com.hamming.halbo.game;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.factories.*;
import com.hamming.halbo.game.action.Action;
import com.hamming.halbo.model.*;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

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
        gameState.getOnlineUsers().add(u);
        fireGameStateEvent(GameStateEvent.Type.USERCONNECTED, u);
    }

    public void userDisconnected(User u) {
        boolean contained = gameState.getOnlineUsers().remove(u);
        if (contained) {
            fireGameStateEvent(GameStateEvent.Type.USERDISCONNECTED, u);
        }
    }


    public void handleTeleportRequest(String userId, String worldId, String continentId, String cityId, String baseplateId) {
        User u = UserFactory.getInstance().findUserById(userId);
        World w = WorldFactory.getInstance().findWorldById(worldId);;
        Continent c = ContinentFactory.getInstance().findContinentById(continentId);
        City ct = CityFactory.getInstance().findCityByID(cityId);
        Baseplate b = BaseplateFactory.getInstance().findBaseplateByID(baseplateId);
        if (u != null && w != null && c != null && ct != null && b != null ) {
            UserLocation loc = gameState.getLocation(u);
            if (loc == null) {
                loc = new UserLocation(IDManager.getInstance().getNextID(HalboID.Prefix.LOC));
                loc.setUser(u);
            }
            loc.setWorld(w);
            loc.setContinent(c);
            loc.setCity(ct);
            loc.setBaseplate(b);
            loc.setX(b.getSpawnPointX());
            loc.setY(b.getSpawnPointY());
            loc.setZ(b.getSpawnPointZ());
            gameState.setLocation(u, loc);
            fireGameStateEvent(GameStateEvent.Type.USERLOCATION, loc);
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



    public void handleMoveRequest(User u, boolean forward, boolean back, boolean left, boolean right) {
        UserLocation location = gameState.getLocation(u);
        if (location != null ) {
            // TODO Check Baseplate bounds -Baseplate baseplate = location.getBaseplate();
            // First - lets try flat movement in the X/Y plane
            if (forward) location.setY(location.getY()+1);
            if (back) location.setY(location.getY()-1);
            if (left) location.setX(location.getX()-1);
            if (right) location.setX(location.getX()+1);
            gameState.setLocation(u, location);
            fireGameStateEvent(GameStateEvent.Type.USERLOCATION, location);
        }
    }


    private void fireGameStateEvent(GameStateEvent.Type type, BasicObject object) {
        for (GameStateListener l: listeners) {
            if ( l == null ) {
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

}