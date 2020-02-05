package com.hamming.halbo.forms.userForms;

import com.hamming.halbo.datamodel.intern.User;
import com.hamming.halbo.factories.UserFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchUserForm {

    JFrame thisFrame;
    public JPanel mainpanel;
    private JTextField userName;
    private JList toShowUserList;
    private JButton closeButton;

    public SearchUserForm(JFrame frame) {
        thisFrame = frame;

        userName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Searching users.");
                loadUserInAList();
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
            }
        });
    }

    private void loadUserInAList() {
        toShowUserList.setModel(getModel());
    }

    private ListModel getModel() {
        DefaultListModel<String> model = new DefaultListModel<>();
        List<User> userList = UserFactory.getInstance().getUsers();

        for (User u : userList){
            if(u.getUsername().equalsIgnoreCase(userName.getText())) {
                model.addElement(u.getUsername());
            }
        }

        return model;
    }
}
