package com.hamming.halbo.client;


import com.hamming.halbo.client.panels.*;

import javax.swing.*;

public class BaseWindow extends JFrame {

    private LoginPanel loginPanel;
    private WorldsPanel worldsPanel;
    private CitiesPanel citiesPanel;
    private ContinentsPanel continentsPanel;
    private TeleportPanel teleportPanel;
    private Controllers controllers;

    public BaseWindow(Controllers controllers) {
        this.controllers = controllers;
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

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }


    private JPanel getMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        loginPanel = new LoginPanel(controllers);
        mainPanel.add(loginPanel);

        worldsPanel = new WorldsPanel(controllers.getHalboClientController());
        mainPanel.add(worldsPanel);

        continentsPanel = new ContinentsPanel(controllers.getHalboClientController());
        mainPanel.add(continentsPanel);

        citiesPanel = new CitiesPanel(controllers.getHalboClientController());
        mainPanel.add(citiesPanel);

        teleportPanel = new TeleportPanel(controllers.getHalboClientController());
        mainPanel.add(teleportPanel);

        return mainPanel;
    }

    public void emptyPanels() {
        worldsPanel.empty();
        continentsPanel.empty();
        citiesPanel.empty();
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

    public TeleportPanel getTeleportPanel() {
        return teleportPanel;
    }


}

