package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOTestToollWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseplatesPanel extends JPanel implements CommandReceiver {

    private HALBOTestToollWindow client;
    private JList<ListItem> listOfBaseplates;
    private DefaultListModel listModel;
    private ProtocolHandler protocolHandler;
    private JButton btnTeleport;

    private class ListItem {
        private BaseplateDto baseplate;
        public ListItem(BaseplateDto b) {
            this.baseplate = b;
        }

        @Override
        public String toString() {
            return baseplate.getName();
        }

        public BaseplateDto getBaseplate() {
            return baseplate;
        }
    }


    public BaseplatesPanel(HALBOTestToollWindow client) {
        this.client = client;
        protocolHandler = new ProtocolHandler();
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Baseplates"));
        listModel = new DefaultListModel();
        listOfBaseplates = new JList<ListItem>(listModel);
        JScrollPane scrollPane = new JScrollPane(listOfBaseplates);
        scrollPane.setPreferredSize(new Dimension(150,60));
        add(scrollPane);

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
        client.teleport();
    }


    public void empty() {
        listModel.removeAllElements();
    }

    public BaseplateDto getSelectedBaseplate() {
        BaseplateDto dto = null;
        ListItem item = listOfBaseplates.getSelectedValue();
        if (item != null) {
            dto =  item.getBaseplate();
        }
        return dto;
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        if (cmd.equals(Protocol.Command.GETBASEPLATES)) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    BaseplateDto dto = new BaseplateDto();
                    dto.setValues(data);
                    ListItem item = new ListItem(dto);
                    listModel.addElement(item);
                    System.out.println("Added item " + item);
                }
            });
        }
    }

}
