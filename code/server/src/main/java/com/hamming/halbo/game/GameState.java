package com.hamming.halbo.game;

import com.hamming.halbo.model.BasicObject;
import com.hamming.halbo.model.User;
import com.hamming.halbo.model.UserLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {

    private List<User> onlineUsers;
    private Map<User, UserLocation> userLocations;

    public GameState() {
        onlineUsers = new ArrayList<User>();
        userLocations = new HashMap<User,UserLocation>();
    }

    public List<User> getOnlineUsers() {
        return onlineUsers;
    }
    public Map<User, UserLocation> getUserLocations() {
        return userLocations;
    }

    public UserLocation getLocation(User u) {
        UserLocation loc = userLocations.get(u);
        return loc;
    }

    public void setLocation(User u, UserLocation l) {
        userLocations.put(u,l);
    }
}
