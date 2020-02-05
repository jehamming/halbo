package com.hamming.halbo.forms.ContinentsForms;

import com.hamming.halbo.datamodel.intern.Continent;
import com.hamming.halbo.datamodel.intern.User;
import com.hamming.halbo.factories.ContinentFactory;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.forms.JlistMethods;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CreateContinent {
    public JPanel mainpanel;
    private JTextField continentNameField;
    private JTextField creatorUsername;
    private JList creatorList;
    private JButton createContinentButton;
    private JButton closeButton;

    private JFrame thisFrame;

    public CreateContinent(JFrame frame) {
        thisFrame = frame;

        creatorUsername.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creatorList.setModel(JlistMethods.fillJlist(creatorUsername));
            }
        });
        creatorList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                createContinentButton.setEnabled(true);
            }
        });
        createContinentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createContinent();
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
            }
        });
    }

    private void createContinent() {
        String creatorUsernameString = creatorUsername.getText();
        User creator = UserFactory.getInstance().findUserByUsername(creatorUsernameString);
        String continentName = continentNameField.getText();
        ContinentFactory.getInstance().createContinent(continentName,creator.getId());
        JOptionPane.showMessageDialog(null,"Created the continent!");
    }
}
