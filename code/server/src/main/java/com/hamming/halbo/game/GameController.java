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
    private static final float RUN_SPEED = 1;
    private static final float TURN_SPEED = 7;

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


    public UserLocation handleTeleportRequest(String userId, String worldId, String continentId, String cityId) {
        User u = UserFactory.getInstance().findUserById(userId);
        World w = WorldFactory.getInstance().findWorldById(worldId);
        ;
        Continent c = ContinentFactory.getInstance().findContinentById(continentId);
        City ct = CityFactory.getInstance().findCityByID(cityId);
        Baseplate b = ct.getTeleportBaseplate();
        UserLocation loc = null;
        if (u != null && w != null && c != null && ct != null && b != null) {
            loc = gameState.getLocation(u);
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
            loc.setPitch(0);
            loc.setYaw(0);
            gameState.setLocation(u, loc);
            fireGameStateEvent(GameStateEvent.Type.USERLOCATION, loc);
        }
        return loc;
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


    public void handleMoveRequest(Long sequence, User u, boolean forward, boolean back, boolean left, boolean right) {
        UserLocation location = gameState.getLocation(u);
        if (location != null) {
            float currentSpeed = 0;
            if (forward) {
                currentSpeed = RUN_SPEED;
            } else if (back) {
                currentSpeed = -RUN_SPEED;
            }
            float currentTurnSpeed = 0;
            if (right) {
                currentTurnSpeed = -TURN_SPEED;
            } else if (left) {
                currentTurnSpeed = TURN_SPEED;
            }

            location = calculateNewPosition(location, currentSpeed, currentTurnSpeed);

            location.setSequence(sequence);
            gameState.setLocation(u, location);
            fireGameStateEvent(GameStateEvent.Type.USERLOCATION, location);
        }
    }

    private UserLocation calculateNewPosition(UserLocation location, float currentSpeed, float currentTurnSpeed) {
        // Calculate new position
        location.setYaw(location.getYaw() + currentTurnSpeed);
        float distance = currentSpeed;
        float dx = (float) (distance * Math.sin(Math.toRadians(location.getYaw() + currentTurnSpeed)));
        float dz = (float) (distance * Math.cos(Math.toRadians(location.getYaw() + currentTurnSpeed)));
        increasePosition(location, dx, 0, dz);
        return location;
    }

    public void increasePosition(UserLocation l, float dx, float dy, float dz) {
        l.setX( l.getX() + dx );
        l.setY(l.getY() +  dy );
        l.setZ(l.getZ() + dz );
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

}