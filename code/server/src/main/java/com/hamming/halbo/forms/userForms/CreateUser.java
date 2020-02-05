package com.hamming.halbo.forms.userForms;

import com.hamming.halbo.factories.UserFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CreateUser {
    public JPanel mainpanel;
    private JTextField userFullNameTextfield;
    private JTextField userUsernameTextfield;
    private JPasswordField passwordField;
    private JTextField userEmailField;

    private JFrame thisFrame;


    public CreateUser(JFrame frame) {
        thisFrame = frame;
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUserAccount();
            }
        });
    }

    private void createUserAccount() {
        String userFullname = userFullNameTextfield.getText();
        String userUsername = userUsernameTextfield.getText();
        String userPassword = passwordField.getText();
        String userEmail = userEmailField.getText();

        UserFactory.getInstance().addUser(userFullname,userUsername,userPassword, userEmail);
        JOptionPane.showMessageDialog(null,"User created!");
        thisFrame.setVisible(false);

    }
}
