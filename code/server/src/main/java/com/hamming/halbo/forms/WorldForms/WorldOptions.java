package com.hamming.halbo.forms.WorldForms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorldOptions {
    public JPanel mainpanel;
    private JButton createWorldButton;
    private JButton searchWorldButton;
    private JButton deleteWorldButton;

    public WorldOptions() {
        createWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateWorld();
            }
        });
        searchWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchWorld();
            }
        });
        deleteWorldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteWorld();
            }
        });
    }

    private void showCreateWorld() {
        JFrame frame = new JFrame("Create world");
        frame.setContentPane(new createWorld(frame).mainpanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void showSearchWorld() {
        JFrame frame = new JFrame("Search world");
        frame.setContentPane(new SearchWorld(frame).mainpanel);
        frame.pack();
        frame.setVisible(true);
    }
    private void showDeleteWorld() {
        JFrame frame = new JFrame("Delete world");
        frame.setContentPane(new DeleteWorld(frame).mainpanel);
        frame.pack();
        frame.setVisible(true);
    }
}
