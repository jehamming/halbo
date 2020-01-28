package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.datamodel.HalboID;
import com.hamming.halbo.datamodel.User;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
        User u = new User(id);
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


    public boolean loadUsersFromFile(String filename) {
        File file = new File(filename);
        return loadUsersFromFile(file);
    }

    public boolean loadUsersFromFile(File file) {
        boolean retval = true;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (ArrayList<User>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            retval = false;
        }
        return retval;
    }

    public boolean storeUsersInFile(String filename) {
        File file = new File(filename);
        return storeUsersInFile(file);
    }

    public boolean storeUsersInFile(File file) {
        boolean retval = true;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            retval = false;
        }
        return retval;
    }



}
