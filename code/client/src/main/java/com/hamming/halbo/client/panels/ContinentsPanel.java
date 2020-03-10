package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.controllers.HALBOClientController;
import com.hamming.halbo.model.dto.ContinentDto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

public class ContinentsPanel extends JPanel {

    private JList<ContinentDto> listOfContinents;
    private DefaultListModel listModel;
    private HALBOClientController controller;

    public ContinentsPanel(HALBOClientController controller) {
        this.controller = controller;
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
                        controller.continentSelected(c);
                    }
                }
            }
        });
        listOfContinents.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfContinents);
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

    public void addContinent(ContinentDto continent) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.addElement(continent);
            }
        });
    }

    public void addContinents(List<ContinentDto> continents) {
        for (ContinentDto c : continents) {
            addContinent(c);
        }
    }

    public void removeContinent(ContinentDto continent) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.removeElement(continent);
            }
        });
    }





}
