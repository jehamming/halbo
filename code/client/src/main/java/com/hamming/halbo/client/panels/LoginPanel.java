package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.BaseWindow;
import com.hamming.halbo.client.Controllers;
import com.hamming.halbo.client.interfaces.UserListener;
import com.hamming.halbo.model.dto.UserDto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginPanel extends JPanel {

    private JTextField txtServer;
    JTextField txtPort;
    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton btnConnect;
    JButton btnDisconnect;
    private Controllers controllers;


    public LoginPanel(Controllers controllers) {
        createPanel();
        this.controllers = controllers;
    }

    private void createPanel() {
        setBorder(new TitledBorder("Login"));
        setLayout(new GridLayout(5,2,5,5));

        add(new JLabel("Server ip:"));
        txtServer = new JTextField("127.0.0.1");
        add(txtServer);

        add(new JLabel("Port:"));
        txtPort = new JTextField("3333");
        add(txtPort);

        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        txtPassword.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    doLogin();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        add(txtPassword);


        btnDisconnect = new JButton("Disconnect");
        btnDisconnect.setEnabled(false);
        btnDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disconnect();
            }
        });
        add(btnDisconnect);


        btnConnect = new JButton("Connect");
        btnConnect.setEnabled(true);
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });
        add(btnConnect);
    }

    private void disconnect() {
        controllers.getConnectionController().disconnect();
        btnDisconnect.setEnabled(false);
        btnConnect.setEnabled(true);
    }

    public void doLogin() {
        String server = txtServer.getText().trim();
        String strPort = txtPort.getText().trim();
        Integer port = Integer.valueOf(strPort);
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword());
        try {
            controllers.getConnectionController().connect(server,port);
            controllers.getUserController().sendLogin(username, password);
            btnDisconnect.setEnabled(true);
            btnConnect.setEnabled(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
