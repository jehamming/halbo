package com.hamming.halbo.forms.ContinentsForms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContinentOptions {
    public JPanel mainpanel;
    private JButton createContinentButton;
    private JButton showAListOfContinentsButton;
    private JButton closeButton;

    public ContinentOptions() {
        createContinentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateContinent();
            }
        });
        showAListOfContinentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showContinentList();
            }
        });
    }

    private void showContinentList() {
        JFrame frame = new JFrame("Continent List");
        frame.setContentPane(new ContinentList(frame).mainpanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void showCreateContinent() {
        JFrame frame = new JFrame("Create continent");
        frame.setContentPane(new CreateContinent(frame).mainpanel);
        frame.pack();
        frame.setVisible(true);
    }
}
