package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.BaseWindow;
import com.hamming.halbo.client.controllers.ContinentController;
import com.hamming.halbo.client.interfaces.ContinentListener;
import com.hamming.halbo.model.dto.ContinentDto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ContinentsPanel extends JPanel implements ContinentListener {

    private JList<ContinentDto> listOfContinents;
    private DefaultListModel listModel;
    private ContinentController continentController;
    private BaseWindow baseWindow;

    public ContinentsPanel(BaseWindow window, ContinentController continentController) {
        this.continentController = continentController;
        this.baseWindow = window;
        continentController.addContinentListener(this);
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
                        continentController.continentSelected(c);
                        baseWindow.getCitiesPanel().empty();
                    }
                }
            }
        });
        listOfContinents.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfContinents);
        add(scrollPane);
    }

    public void empty() {
        listModel.removeAllElements();
    }

    @Override
    public void continentAdded(ContinentDto continent) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.addElement(continent);
            }
        });
    }

    @Override
    public void continentDeleted(ContinentDto continent) {

    }
}
