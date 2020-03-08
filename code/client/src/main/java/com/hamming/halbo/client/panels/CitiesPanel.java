package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.controllers.HALBOClientController;
import com.hamming.halbo.model.dto.CityDto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

public class CitiesPanel extends JPanel {

    private DefaultListModel listModel;
    private HALBOClientController controller;
    private JList<CityDto> listOfCities;

    public CitiesPanel(HALBOClientController controller) {
        this.controller = controller;
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
                        controller.citySelected(dto);
                    }
                }
            }
        });
        listOfCities.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfCities);
        add(scrollPane);
    }


    public void empty() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.removeAllElements();
            }
        });
    }

    public void addCity(CityDto city) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.addElement(city);
            }
        });
    }

    public void deleteCity(CityDto city) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.removeElement(city);
            }
        });
    }

    public void addCities(List<CityDto> cities) {
        for (CityDto dto: cities) {
            addCity(dto);
        }

    }
}
