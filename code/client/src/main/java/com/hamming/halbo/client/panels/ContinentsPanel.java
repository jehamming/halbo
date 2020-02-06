package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOClientWindow;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.util.StringUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ContinentsPanel extends JPanel {

    private HALBOClientWindow client;
    private JList<ContinentDto> listOfContinents;
    private DefaultListModel listModel;

    public ContinentsPanel(HALBOClientWindow client) {
        this.client = client;
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Worlds"));
        listModel = new DefaultListModel();
        listOfContinents = new JList<ContinentDto>(listModel);
        listOfContinents.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ContinentDto c = listOfContinents.getSelectedValue();
                if (c != null ) {
                    client.continentSelected(c);
                }
            }
        });
        listOfContinents.setPreferredSize(new Dimension(200,60));
        JScrollPane scrollPane = new JScrollPane(listOfContinents);
        add(scrollPane);
    }

    public void receive( String s) {
        String[] arr = s.split(StringUtils.delimiter);
        ContinentDto c = new ContinentDto();
        c.setValues(arr);
        listModel.addElement(c);
    }



    public void empty() {
        listModel.removeAllElements();
    }
}
