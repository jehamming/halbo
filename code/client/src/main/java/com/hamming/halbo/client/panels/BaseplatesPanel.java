package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOClientWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class BaseplatesPanel extends JPanel implements CommandReceiver {

    private HALBOClientWindow client;
    private JList<BaseplateDto> listOfBaseplates;
    private DefaultListModel listModel;
    private ProtocolHandler protocolHandler;

    public BaseplatesPanel(HALBOClientWindow client) {
        this.client = client;
        protocolHandler = new ProtocolHandler();
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Baseplates"));
        listModel = new DefaultListModel();
        listOfBaseplates = new JList<BaseplateDto>(listModel);
        listOfBaseplates.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting() ) { //Else this is called twice!
                    BaseplateDto bp = listOfBaseplates.getSelectedValue();
                    if (bp != null) {
                        basePlateSelected(bp);
                    }
                }
            }
        });
        listOfBaseplates.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfBaseplates);
        add(scrollPane);
    }


    public void empty() {
        listModel.removeAllElements();
    }

    public void basePlateSelected(BaseplateDto world) {
        //ToDO Implement
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        if (cmd.equals(Protocol.Command.GETBASEPLATES)) {
            BaseplateDto dto = new BaseplateDto();
            dto.setValues(data);
            listModel.addElement(dto);
        }
    }

}
