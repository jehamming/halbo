package com.hamming.halbo.forms.WorldForms;

import com.hamming.halbo.model.User;
import com.hamming.halbo.factories.UserFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchWorld {
    public JPanel mainpanel;
    private JTextField worldNameField;
    private JList worldList;
    private JButton closeMenu;
    private JFrame thisFrame;


    public SearchWorld(JFrame frame) {
        this.thisFrame = frame;

        worldNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               worldList.setModel(getWorld());
            }
        });
        closeMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
            }
        });
    }

    private ListModel getWorld() {
        DefaultListModel<String> model = new DefaultListModel<>();
        List<User> userList = UserFactory.getInstance().getUsers();

        for (User u : userList){
            if(u.getUsername().equalsIgnoreCase(worldNameField.getText())) {
                model.addElement(u.getUsername());
            }
        }

        return model;
    }
}
