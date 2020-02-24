package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.BaseWindow;
import com.hamming.halbo.client.controllers.DataController;
import com.hamming.halbo.client.interfaces.ICityWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.CityDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class CitiesPanel extends JPanel implements ICityWindow {

    private DefaultListModel listModel;
    private JList<CityDto> listOfCities;
    private DataController dataController;

    public CitiesPanel(DataController dataController) {
        this.dataController = dataController;
        dataController.setCityWindow(this);
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
                        dataController.citySelected(dto);
                    }
                }
            }
        });
        listOfCities.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfCities);
        add(scrollPane);
    }


    public void empty() {
        listModel.removeAllElements();
    }


    @Override
    public void addCity(CityDto dto) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.addElement(dto);
            }
        });
    }

    public CityDto getSelectedCity() {
        return listOfCities.getSelectedValue();
    }
}
