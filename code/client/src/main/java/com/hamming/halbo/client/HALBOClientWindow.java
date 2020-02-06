package com.hamming.halbo.client;


import com.hamming.halbo.client.panels.*;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.net.CommandReceiver;
import com.hamming.halbo.net.NetClient;

import javax.swing.*;

public class HALBOClientWindow extends JFrame {

    private LoginPanel loginPanel;
    private NetClient client;
    private WorldsPanel worldsPanel;
    private CitiesPanel citiesPanel;
    private ContinentsPanel continentsPanel;
    private BaseplatesPanel baseplatesPanel;

    public HALBOClientWindow() {
        init();
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
        setLocation(50, 50);

        /*Some piece of code*/
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (client != null && client.isConnected()) {
                    client.dispose();
                }
                System.exit(0);
            }
        });

    }

    private void registerCommandReceivers() {
        client.registerReceiver(Protocol.Command.LOGIN, loginPanel);
        client.registerReceiver(Protocol.Command.GETWORLDS, worldsPanel);
        client.registerReceiver(Protocol.Command.GETCONTINENTS, continentsPanel);
        client.registerReceiver(Protocol.Command.GETCITIES, citiesPanel);
        client.registerReceiver(Protocol.Command.GETBASEPLATES, baseplatesPanel);
    }


    private JPanel getMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        loginPanel = new LoginPanel(this);
        mainPanel.add(loginPanel);

        worldsPanel = new WorldsPanel(this);
        mainPanel.add(worldsPanel);

        continentsPanel = new ContinentsPanel(this);
        mainPanel.add(continentsPanel);

        citiesPanel = new CitiesPanel(this);
        mainPanel.add(citiesPanel);

        baseplatesPanel = new BaseplatesPanel(this);
        mainPanel.add(baseplatesPanel);
        return mainPanel;
    }

    public void emptyPanels() {
        worldsPanel.empty();
        continentsPanel.empty();
        citiesPanel.empty();
    }

    public boolean connect(String serverip, int port) {
        boolean success = true;
        emptyPanels();
        if (client != null && !client.isConnected() ) {
            client.dispose();
        }
        client = new NetClient();
        // Register Data Receivers
        registerCommandReceivers();
        String result = client.connect(serverip, port);
        if (result != null) {
            System.out.println("Something went wrong connecting to the server");
            JOptionPane.showMessageDialog(loginPanel, result);
            success = false;
        }
        return success;
    }

    public void send(String s) {
        client.send(s);
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


    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public WorldsPanel getWorldsPanel() {
        return worldsPanel;
    }

    public CitiesPanel getCitiesPanel() {
        return citiesPanel;
    }

    public ContinentsPanel getContinentsPanel() {
        return continentsPanel;
    }

    public BaseplatesPanel getBaseplatesPanel() {
        return baseplatesPanel;
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

