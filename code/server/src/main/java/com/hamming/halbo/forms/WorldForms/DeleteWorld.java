package com.hamming.halbo.forms.WorldForms;

import com.hamming.halbo.datamodel.intern.World;
import com.hamming.halbo.datamodel.intern.User;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.factories.WorldFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DeleteWorld {
    public JPanel mainpanel;
    private JTextField worldNameField;
    private JList worldsList;
    private JButton deleteWorldButton;
    private JButton closeButton;
    private JFrame thisFrame;

    public DeleteWorld(JFrame frame) {
        this.thisFrame = frame;

        worldNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worldsList.setModel(fillJlistWithWorlds());
            }
        });
        worldsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                deleteWorldButton.setEnabled(true);
            }
        });
        deleteWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteWorld();
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
            }
        });
    }

    private void deleteWorld() {
        String worldName = worldNameField.getText();
        World toDelteWorld = WorldFactory.getInstance().getWorldByName(worldName);
        WorldFactory.getInstance().deleteWorld(toDelteWorld);
    }

    private ListModel fillJlistWithWorlds() {
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
