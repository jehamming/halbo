package com.hamming.halbo.factories;

import com.hamming.halbo.model.User;

import java.io.*;
import java.util.ArrayList;

public class AbstractFactory {

    public boolean storeInFile(Object o, String filename) {
        File file = new File(filename);
        return storeInFile(o, file);
    }

    public boolean storeInFile(Object o, File file) {
        boolean retval = true;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            retval = false;
        }
        return retval;
    }

    public Object loadFromFile(String filename) {
        File file = new File(filename);
        return loadFromFile(file);
    }

    public Object loadFromFile(File file) {
        Object readObject = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            readObject = (ArrayList<User>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(this.getClass().getName() + ":" + "ERROR:" + e.getMessage());
            //e.printStackTrace();
        }
        return readObject;
    }


}
