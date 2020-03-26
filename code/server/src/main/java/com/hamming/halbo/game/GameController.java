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
            fireGameStateEvent(GameStateEvent.Type.USERTELEPORTED, loc);
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

            checkBaseplateBounds(location);

            location.setSequence(sequence);
            gameState.setLocation(u, location);
            fireGameStateEvent(GameStateEvent.Type.USERLOCATION, location);
        }
    }

    private void checkBaseplateBounds(UserLocation l) {
        Baseplate b = l.getBaseplate();
        if ( l.getX() > b.getSize()) {
            // Switch to baseplate to the right(EAST)
            Baseplate eastBaseplate = l.getCity().getCityGrid().getBaseplate(l.getBaseplate(), CityGrid.Direction.EAST);
            if (eastBaseplate != null ) {
                float x = l.getX() - l.getBaseplate().getSize();
                l.setBaseplate(eastBaseplate);
                l.setX(x);
            } else {
                l.setX(b.getSize());
            }
        }
        if ( l.getX() < 0 ) {
            // Switch to baseplate to the left(WEST)
            Baseplate westBaseplate = l.getCity().getCityGrid().getBaseplate(l.getBaseplate(), CityGrid.Direction.WEST);
            if (westBaseplate != null ) {
                float x = westBaseplate.getSize() + l.getX();
                l.setBaseplate(westBaseplate);
                l.setX(x);
            } else {
                l.setX(0);
            }
        }
        if ( l.getZ() > b.getSize()) {
            // Switch to baseplate to the top(NORTH)
            Baseplate northBaseplate = l.getCity().getCityGrid().getBaseplate(l.getBaseplate(), CityGrid.Direction.NORTH);
            if (northBaseplate != null ) {
                float z = l.getZ() - l.getBaseplate().getSize();
                l.setBaseplate(northBaseplate);
                l.setZ(z);
            } else {
                l.setZ(b.getSize());
            }
        }
        if ( l.getZ() < 0 ) {
            // Switch to baseplate to the bottom(SOUTH)
            Baseplate southBaseplate = l.getCity().getCityGrid().getBaseplate(l.getBaseplate(), CityGrid.Direction.SOUTH);
            if (southBaseplate != null ) {
                float z = southBaseplate.getSize() + l.getZ();
                l.setBaseplate(southBaseplate);
                l.setZ(z);
            } else {
                l.setZ(0);
            }

        }
    }

    private UserLocation calculateNewPosition(UserLocation location, float currentSpeed, float currentTurnSpeed) {
        // Calculate new position
        location.setYaw(location.getYaw() + currentTurnSpeed);
        float distance = currentSpeed;
        float dx = (float) (distance * Math.sin(Math.toRadians(location.getYaw() + currentTurnSpeed)));
        float dz = (float) (distance * Math.cos(Math.toRadians(location.getYaw() + currentTurnSpeed)));
        increasePosition(location, -dx, 0, dz);
        return location;
    }

    public void increasePosition(UserLocation l, float dx, float dy, float dz) {
        l.setX(l.getX() + dx );
        l.setY(l.getY() + dy );
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