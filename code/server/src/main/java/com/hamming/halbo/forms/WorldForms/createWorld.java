package com.hamming.halbo.forms.WorldForms;

import com.hamming.halbo.datamodel.intern.User;
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
    private JTextField ownerUsernameField;

    private JButton createWorldButton;

    private JList selectionCreator;
    private JList selectionOwner;
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
        ownerUsernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectionOwner.setModel(fillJlist(ownerUsernameField));
                actionPerformedArray[1] = true;
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
        String ownerUsername = selectionOwner.getSelectedValue().toString();
        User owner = UserFactory.getInstance().findUserByUsername(ownerUsername);
        String worldName = worldNameTextfield.getText();
        WorldFactory.getInstance().addWorld(creator.getId(),owner.getId(),worldName);
    }


}
