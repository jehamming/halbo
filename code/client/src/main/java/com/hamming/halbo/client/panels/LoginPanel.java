package com.hamming.halbo.client;

import com.hamming.halbo.game.Protocol;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private JTextField txtServer;
    JTextField txtPort;
    JTextField txtUsername;
    JTextField txtPassword;
    JLabel lblStatus;
    HALBOClientWindow client;

    public LoginPanel(HALBOClientWindow client) {
        createPanel();
        this.client = client;
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
        txtPassword = new JTextField();
        add(txtPassword);

        lblStatus = new JLabel();
        add(lblStatus);
        JButton b = new JButton("Connect");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        add(b);
    }

    public void login() {
        String server = txtServer.getText().trim();
        String strPort = txtPort.getText().trim();
        Integer port = Integer.valueOf(strPort);
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        client.connect(server,port,username,password);
    }

    public void receive( String s) {
        if (Protocol.SUCCESS.equals(s)) {
            client.getWorlds();
        } else {
            JOptionPane.showMessageDialog(this, "Login failed..");
            txtUsername.setText("");
            txtPassword.setText("");
        }
    }
}
