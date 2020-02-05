package com.hamming.halbo.client;


import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.net.DataReceiver;
import com.hamming.halbo.net.NetClient;

import javax.swing.*;

public class HALBOClientWindow extends JFrame implements DataReceiver {

    private LoginPanel loginPanel;
    private ProtocolHandler handler;
    private NetClient client;
    private WorldsPanel worldsPanel;

    public HALBOClientWindow() {
        init();
        handler = new ProtocolHandler();
    }

    private void init() {
        //Create and set up the window.
        setTitle("HALBO Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Create and set up the content pane.
        setContentPane(getMainPanel());
        //Display the window.
        pack();
        setVisible(true);
        setLocation(50,50);
    }


    private JPanel getMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        loginPanel = new LoginPanel(this);
        mainPanel.add(loginPanel);
        worldsPanel = new WorldsPanel(this);
        mainPanel.add(worldsPanel);
        mainPanel.add(new CitiesPanel());
        return mainPanel;
    }

    public void connect(String serverip, int port, String username, String password) {
        client = new NetClient(this);
        client.connect(serverip, port);
        String s = handler.getLoginCommand(username,password);
        client.send(s);
    }

    public void getWorlds() {
        String s = handler.getWorldsCommand();
        System.out.println("getWorlds-send:" + s);
        client.send(s);
    }


    @Override
    public void receive(String s) {
        System.out.println("Received:" + s);
        Protocol.Command cmd = handler.parseCommandString(s);
        if (cmd == Protocol.Command.LOGIN) {
            loginPanel.receive(s.substring(2));
        } else if (cmd == Protocol.Command.GETWORLDS) {
            worldsPanel.receive(s.substring(2));
        }

    }





    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        WorldOpenGLWindow openGLWindow = new WorldOpenGLWindow();
        HALBOClientWindow clientWindow = new HALBOClientWindow();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }



}
