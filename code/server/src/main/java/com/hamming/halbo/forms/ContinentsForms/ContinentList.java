package com.hamming.halbo.forms.ContinentsForms;

import com.hamming.halbo.forms.JlistMethods;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContinentList {
    public JPanel mainpanel;
    private JList continentList;
    private JButton testButtonButton;

    private JFrame thisFrame;

    public ContinentList(JFrame frame) {
        thisFrame = frame;
        continentList.setModel(JlistMethods.fillJlistContinents());
    }
}
