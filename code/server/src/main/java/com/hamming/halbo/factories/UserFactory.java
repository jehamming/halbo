package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.datamodel.intern.HalboID;
import com.hamming.halbo.datamodel.intern.User;
import com.hamming.halbo.util.StringUtils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserFactory extends AbstractFactory {
    private static UserFactory instance;
    private User systemUser;
    private List<User> users;

    private UserFactory() {
        initialize();
    };

    private void initialize() {
        HalboID id = IDManager.getInstance().getNextID(HalboID.Prefix.SYS);
        systemUser = new User(id.toString());
        systemUser.setFullName("SYSTEM");
        users = new ArrayList<User>();
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

    public List<User> getUsers() {
        return users;
    }

    public User addUser(String fullName, String username, String password) {
        HalboID id = IDManager.getInstance().getNextID(HalboID.Prefix.USR);
        User u = new User(id.toString());
        u.setFullName(fullName);
        u.setUsername(username);
        u.setPassword(StringUtils.hashPassword(password));
        users.add(u);
        return u;
    }

    public boolean deleteUser(User user) {
        return (users.remove(user));
    }

    // NOTE this methods expect a HASHED password!
    public User validateUser(String username, String hashedPassword) {
        User u = findUserByUsername(username);
        if (!u.getPassword().equals(hashedPassword) ) {
            // Wrong password!
            u = null;
        }
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

    public String getUsersAsAString() {
        StringBuilder sb = new StringBuilder();
        for (User u : users){
            sb.append(u + "\n");
        }
        return sb.toString();
    }

    private Long getHighestID() {
        Long highest = 0L;
        for (User u : users ) {
            long id = HalboID.valueOf(u.getId()).getId();
            if (id > highest) {
                highest = id;
            }
        }
        return highest;
    }

    public boolean storeUsersInFile(String filename) {
        return storeInFile(users, filename);
    }

    public boolean storeUsersInFile(File file) {
        return storeInFile(users, file);
    }


    public boolean loadUsersFromFile(String filename) {
        File file = new File(filename);
        return loadUsersFromFile(file);
    }

    public boolean loadUsersFromFile(File file) {
        boolean retval = true;
        List<User> loadUsers = (ArrayList<User>) loadFromFile(file);
        if ( loadUsers != null ) {
            users = loadUsers;
            Long highestID = getHighestID();
            IDManager.getInstance().setLastAddedID(HalboID.Prefix.USR, highestID);
        }
        return retval;
    }


}
