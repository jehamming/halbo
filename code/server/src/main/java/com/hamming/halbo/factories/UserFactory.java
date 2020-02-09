package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.model.HalboID;
import com.hamming.halbo.model.User;
import com.hamming.halbo.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFactory extends AbstractFactory {
    private static UserFactory instance;
    private List<User> users;

    private UserFactory() {
        initialize();
    };

    private void initialize() {
        HalboID id = IDManager.getInstance().getNextID(HalboID.Prefix.SYS);
        users = new ArrayList<User>();
    }

    public static UserFactory getInstance() {
        if ( instance == null ) {
            instance = new UserFactory();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    //This add user has an email within the constructor.
    public User addUser(String fullName, String username, String password, String email) {
        User u = addUser(fullName, username, password);
        u.setEmail(email);
        return u;
    }

    public User addUser(String name, String username, String password) {
        HalboID id = IDManager.getInstance().getNextID(HalboID.Prefix.USR);
        User u = new User(id);
        u.setName(name);
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
        if (u != null && !u.getPassword().equals(hashedPassword) ) {
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

    public User findUserById( String strId ) {
        User returnValue = null;
        HalboID id = HalboID.valueOf(strId);
        for (User u : users ) {
            if (u.getId().equals(id)) {
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
            long id = u.getId().getId();
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
