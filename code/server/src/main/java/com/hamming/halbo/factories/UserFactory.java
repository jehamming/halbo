package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.datamodel.intern.HalboID;
import com.hamming.halbo.datamodel.intern.User;

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
        u.setPassword(hashPassword(password));
        users.add(u);
        return u;
    }

    public boolean deleteUser(User user) {
        return (users.remove(user));
    }

    public User validateUser(String username, String password) {
        User u = findUserByUsername(username);
        if (!u.getPassword().equals(hashPassword(password))) {
            // Wrong password!
            u = null;
        }
        return u;
    }

    private String hashPassword(String password) {
        // Create MessageDigest instance for MD5
        MessageDigest md = null;
        String hashedPassword = null;
        try {
            md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
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
