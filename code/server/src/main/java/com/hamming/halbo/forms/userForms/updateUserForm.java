package com.hamming.halbo.forms.userForms;

import com.hamming.halbo.model.User;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.util.StringUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class updateUserForm {

    public JPanel mainpanel;
    private JTextField userName;
    private JList toShowUserList;
    private JTextField changeUsernameField;
    private JTextField changePasswordField;
    private JButton modifyButton;
    private JButton closeButton;

    private JFrame thisFrame;

    public updateUserForm(JFrame frame) {
        thisFrame = frame;
        userName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Searching users.");
                loadUserInAList();
            }
        });
        toShowUserList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                changeUsernameField.setText(toShowUserList.getSelectedValue().toString());
                changePasswordField.setText(getPassword(toShowUserList.getSelectedValue().toString()));
            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyUser();
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
            }
        });
    }

    private void modifyUser() {
        String newUsername = changeUsernameField.getText();
        String newPassword = changePasswordField.getText();
        User toChangeUser = searchUser();
        if(!newUsername.equals("")){
            //New username has some text in it
            toChangeUser.setUsername(newUsername);
        }
        if(!newPassword.equals("")){
            toChangeUser.setPassword(StringUtils.hashPassword(newPassword));
        }
        JOptionPane.showMessageDialog(null,"Changed cridentials for: " + toChangeUser.getUsername());
    }

    private User searchUser() {
        List<User> userList = UserFactory.getInstance().getUsers();
        for (User u : userList){
            if(u.getUsername().equals(toShowUserList.getSelectedValue().toString())){
                return u;
            }
        }
        return null;
    }

    private String getPassword(String username) {
        List<User> userList = UserFactory.getInstance().getUsers();
        for (User u : userList){
            if(u.getUsername().equalsIgnoreCase(username)){
                return u.getPassword();
            }
        }
        return null;
    }

    private void loadUserInAList() {
        toShowUserList.setModel(getModel());
    }

    private ListModel getModel() {
        DefaultListModel<String> model = new DefaultListModel<>();
        List<User> userList = UserFactory.getInstance().getUsers();

        for (User u : userList){
            if(u.getUsername().equalsIgnoreCase(userName.getText())) {
                model.addElement(u.getName());
            }
        }

        return model;
    }
}
