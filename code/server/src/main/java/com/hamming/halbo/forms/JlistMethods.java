package com.hamming.halbo.forms;

import com.hamming.halbo.model.Continent;
import com.hamming.halbo.model.User;
import com.hamming.halbo.factories.ContinentFactory;
import com.hamming.halbo.factories.UserFactory;

import javax.swing.*;
import java.util.List;

public class JlistMethods {

    public static ListModel fillJlist(JTextField userfield) {
        DefaultListModel<String> model = new DefaultListModel<>();
        List<User> userList = UserFactory.getInstance().getUsers();

        for (User u : userList){
            if(u.getUsername().equalsIgnoreCase(userfield.getText())) {
                model.addElement(u.getUsername());
            }
        }

        return model;
    }

    public static ListModel fillJlistContinents(){
        DefaultListModel<String> model = new DefaultListModel<>();
        List<Continent> continents = ContinentFactory.getInstance().getContinents();

        for(Continent c: continents){
            model.addElement(c.getName());
        }

        return model;
    }

    public static ListModel fillJlistUsers(){
        DefaultListModel<String> model = new DefaultListModel<>();
        List<User> users = UserFactory.getInstance().getUsers();

        for(User u: users){
            model.addElement(u.getUsername());
        }

        return model;
    }
}
