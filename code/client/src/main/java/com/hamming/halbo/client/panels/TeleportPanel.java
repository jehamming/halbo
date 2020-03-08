package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.controllers.HALBOClientController;
import com.hamming.halbo.client.controllers.MoveController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeleportPanel extends JPanel {

    private JButton btnTeleport;
    private HALBOClientController controller;


    public TeleportPanel(HALBOClientController controller) {
        this.controller = controller;
        createPanel();
    }

    private void createPanel() {
        btnTeleport = new JButton("Teleport");
        btnTeleport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teleport();
            }
        });
        add(btnTeleport);
    }

    private void teleport() {
        controller.teleport();
    }

}
