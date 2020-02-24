package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.BaseWindow;
import com.hamming.halbo.client.controllers.DataController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeleportPanel extends JPanel {

    private JButton btnTeleport;

    private DataController dataController;

    public TeleportPanel(DataController dataController) {
        this.dataController = dataController;
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
        //TODO
    }

}
