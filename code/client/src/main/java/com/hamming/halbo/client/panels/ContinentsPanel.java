package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.BaseWindow;
import com.hamming.halbo.client.controllers.DataController;
import com.hamming.halbo.client.interfaces.IContinentWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ContinentsPanel extends JPanel implements IContinentWindow {

    private JList<ContinentDto> listOfContinents;
    private DefaultListModel listModel;
    private DataController dataController;

    public ContinentsPanel(DataController dataController) {
        this.dataController =  dataController;
        dataController.setContinentWindow(this);
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
                        dataController.continentSelected(c);
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
    public void addContinent(ContinentDto dto) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                listModel.addElement(dto);
            }
        });
    }

    public ContinentDto getSelectedContinent() {
        return listOfContinents.getSelectedValue();
    }
}
