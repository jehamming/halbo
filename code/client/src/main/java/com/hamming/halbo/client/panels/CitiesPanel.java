package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.controllers.CityController;
import com.hamming.halbo.client.interfaces.CityListener;
import com.hamming.halbo.model.dto.CityDto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class CitiesPanel extends JPanel implements CityListener {

    private DefaultListModel listModel;
    private JList<CityDto> listOfCities;
    private CityController cityController;

    public CitiesPanel(CityController cityController) {
        this.cityController = cityController;
        cityController.addCityListener(this);
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
                        cityController.citySelected(dto);
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
    public void cityAdded(CityDto city) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.addElement(city);
            }
        });
    }

    @Override
    public void cityDeleted(CityDto city) {

    }
}
