package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOTestToollWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ContinentsPanel extends JPanel implements CommandReceiver {

    private HALBOTestToollWindow client;
    private JList<ContinentDto> listOfContinents;
    private DefaultListModel listModel;
    private ProtocolHandler protocolHandler;

    public ContinentsPanel(HALBOTestToollWindow client) {
        this.client = client;
        protocolHandler = new ProtocolHandler();
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Continents"));
        listModel = new DefaultListModel();
        listOfContinents = new JList<ContinentDto>(listModel);
        listOfContinents.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting() ) { //Else this is called twice!
                    ContinentDto c = listOfContinents.getSelectedValue();
                    if (c != null) {
                        continentSelected(c);
                    }
                }
            }
        });
        listOfContinents.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfContinents);
        add(scrollPane);
    }
    public void continentSelected(ContinentDto continent) {
        client.getCitiesPanel().empty();
        client.getBaseplatesPanel().empty();
        String s = protocolHandler.getGetCitiesCommand(continent.getId());
        client.send(s);
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        if (cmd.equals(Protocol.Command.GETCONTINENTS)) {
            ContinentDto c = new ContinentDto();
            c.setValues(data);
            listModel.addElement(c);
        }
    }


    public void empty() {
        listModel.removeAllElements();
    }

    public ContinentDto getSelectedContinent() {
        return listOfContinents.getSelectedValue();
    }
}
