package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOClientWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.CityDto;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.net.CommandReceiver;
import com.hamming.halbo.util.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class CitiesPanel extends JPanel implements CommandReceiver {

    private HALBOClientWindow client;
    private DefaultListModel listModel;
    private ProtocolHandler protocolHandler;
    private JList<CityDto> listOfCities;

    public CitiesPanel(HALBOClientWindow clientWindow) {
        this.client = clientWindow;
        protocolHandler = new ProtocolHandler();
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Cities"));
        listModel = new DefaultListModel();
        listOfCities = new JList<CityDto>(listModel);
        listOfCities.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting() ) { //Else this is called twice!
                    CityDto dto = listOfCities.getSelectedValue();
                    if (dto != null) {
                        citySelected(dto);
                    }
                }
            }
        });
        listOfCities.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfCities);
        add(scrollPane);
    }

    private void citySelected(CityDto city) {
        client.getBaseplatesPanel().empty();
        String s = protocolHandler.getGetBaseplatesCommand(city.getId());
        client.send(s);
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        if (cmd.equals(Protocol.Command.GETCITIES)) {
            CityDto city = new CityDto();
            city.setValues(data);
            listModel.addElement(city);
        }
    }


    public void empty() {
        listModel.removeAllElements();
    }


    public CityDto getSelectedCity() {
        return listOfCities.getSelectedValue();
    }
}
