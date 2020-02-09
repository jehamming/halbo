package com.hamming.halbo.game;

import com.hamming.halbo.model.BasicObject;
import com.hamming.halbo.model.User;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private List<GameStateListener> listeners;
    private List<User> onlineUsers;

    public GameState() {
        listeners = new ArrayList<GameStateListener>();
        onlineUsers = new ArrayList<User>();
    }

    public void userConnected(User u) {
        onlineUsers.add(u);
        fireGameStateEvent(GameStateEvent.Type.USERCONNECTED, u);
    }

    public void userDisconnected(User u) {
        onlineUsers.remove(u);
        fireGameStateEvent(GameStateEvent.Type.USERDISCONNECTED, u);
    }



    private void fireGameStateEvent(GameStateEvent.Type type, BasicObject object) {
        for (GameStateListener l: listeners) {
            l.newGameState(new GameStateEvent(type, object));
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


    public List<User> getOnlineUsers() {
        return onlineUsers;
    }
}
