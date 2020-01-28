package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.datamodel.HalboID;
import com.hamming.halbo.datamodel.User;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {
    private static UserFactory instance;
    private User systemUser;
    private List<User> users;

    private UserFactory() {
        initialize();
    };

    private void initialize() {
        HalboID id = IDManager.getInstance().getNextID(HalboID.Prefix.SYS);
        systemUser = new User(id);
        systemUser.setFullName("SYSTEM");
        users = new ArrayList<User>();
        // TODO Check a connection with a Database or something?
    }

    public static UserFactory getInstance() {
        if ( instance == null ) {
            instance = new UserFactory();
        }
        return instance;
    }

    public User getSystemUser() {
        return systemUser;
    }

    public User addUser(String fullName, String username, String password) {
        HalboID id = IDManager.getInstance().getNextID(HalboID.Prefix.USR);
        User u = new User(id);
        u.setFullName(fullName);
        u.setUsername(username);
        u.setPassword(password);
        users.add(u);
        return u;
    }

    public User findUserByUsername( String username ) {
        User returnValue = null;
        for (User u : users ) {
            if (u.getUsername().equals(username)) {
                returnValue = u;
                break;
            }
        }
        return returnValue;
    }

    public String getUsers() {
        StringBuilder sb = new StringBuilder();
        for (User u : users){
            sb.append(u + "\n");
        }
        return sb.toString();
    }
}
