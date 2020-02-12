package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOTestToollWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class LoginPanel extends JPanel implements CommandReceiver {

    private JTextField txtServer;
    JTextField txtPort;
    JTextField txtUsername;
    JPasswordField txtPassword;
    JLabel lblStatus;
    HALBOTestToollWindow client;
    JButton btnConnect;
    JButton btnDisconnect;
    private ProtocolHandler protocolHandler;

    public LoginPanel(HALBOTestToollWindow client) {
        createPanel();
        this.client = client;
        this.protocolHandler = new ProtocolHandler();
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
                    login();
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
                login();
            }
        });
        add(btnConnect);
    }

    private void disconnect() {
        client.disConnect();
        client.emptyPanels();
        btnDisconnect.setEnabled(false);
        btnConnect.setEnabled(true);
    }

    public void login() {
        String server = txtServer.getText().trim();
        String strPort = txtPort.getText().trim();
        Integer port = Integer.valueOf(strPort);
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword());
        boolean ok = client.connect(server,port);
        if (ok) {
            String s = protocolHandler.getLoginCommand(username, password);
            client.send(s);
            btnDisconnect.setEnabled(true);
            btnConnect.setEnabled(false);
        }
    }

    public void checkLoginOk( String[] data) {
        String status = data[0];
        String[] values = Arrays.copyOfRange(data, 1, data.length);
        if (Protocol.SUCCESS.equals(status)) {
            UserDto user = new UserDto();
            user.setValues(values);
            client.setUser(user);
            String newCommand = protocolHandler.getWorldsCommand();
            client.send(newCommand);
        } else {
            String msg = Arrays.toString(values);
            JOptionPane.showMessageDialog(this, "Login failed : " + msg);
            txtUsername.setText("");
            txtPassword.setText("");
            client.setUser(null);
            btnDisconnect.setEnabled(false);
            btnConnect.setEnabled(true);
        }
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        if (cmd.equals(Protocol.Command.LOGIN)) {
            checkLoginOk(data);
        }
    }
}
