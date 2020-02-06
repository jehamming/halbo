package com.hamming.halbo.forms.WorldForms;

import com.hamming.halbo.model.User;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.factories.WorldFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class createWorld {
    public JPanel mainpanel;
    private JTextField worldNameTextfield;
    private JTextField creatorUsernameField;

    private JButton createWorldButton;

    private JList selectionCreator;
    private JButton closeButton;

    private JFrame thisFrame;

    boolean[] actionPerformedArray = {false,false,false};

    public createWorld(JFrame frame) {
        this.thisFrame = frame;

        creatorUsernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectionCreator.setModel(fillJlist(creatorUsernameField));
                actionPerformedArray[0] = true;
            }
        });
        createWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionCreateWorld();
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
            }
        });
    }

    private DefaultListModel<String> fillJlist(JTextField usernameField) {
        DefaultListModel<String> model = new DefaultListModel<>();
        List<User> userList = UserFactory.getInstance().getUsers();

        for (User u : userList){
            if(u.getUsername().equalsIgnoreCase(usernameField.getText())) {
                model.addElement(u.getUsername());
            }
        }

        return model;
    }

    private void actionCreateWorld() {
        String creatorUsername = selectionCreator.getSelectedValue().toString();
        User creator = UserFactory.getInstance().findUserByUsername(creatorUsername);
        String worldName = worldNameTextfield.getText();
        WorldFactory.getInstance().createWorld(creator,worldName);
    }


}
