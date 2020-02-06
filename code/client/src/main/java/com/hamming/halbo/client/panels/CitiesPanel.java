package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOClientWindow;
import com.hamming.halbo.model.dto.CityDto;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.util.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CitiesPanel extends JPanel {

    private HALBOClientWindow client;
    private DefaultListModel listModel;


    public CitiesPanel(HALBOClientWindow clientWindow) {
        this.client = clientWindow;
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Cities"));
        listModel = new DefaultListModel();
        JList<String> listOfCities = new JList<String>(listModel);
        listOfCities.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfCities);
        add(scrollPane);
    }

    public void receive( String s) {
        String[] arr = s.split(StringUtils.delimiter);
        CityDto city = new CityDto();
        city.setValues(arr);
        listModel.addElement(city.getName());
    }


    public void empty() {
        listModel.removeAllElements();
    }


}
