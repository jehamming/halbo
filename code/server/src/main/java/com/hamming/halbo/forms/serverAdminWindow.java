package com.hamming.halbo.forms;

import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.forms.ContinentsForms.ContinentOptions;
import com.hamming.halbo.forms.WorldForms.WorldOptions;
import com.hamming.halbo.forms.userForms.UserOptions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class serverAdminWindow {
    private JPanel mainpanel;
    private JButton userOptions;
    private JButton showWorldOptionsButton;
    private JButton showContinetsOptionsButton;

    JFrame frame = new JFrame("File");

    public serverAdminWindow() {
        userOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUserOptions();
            }
        });
        showWorldOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showWorldOptions();
            }
        });
        showContinetsOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showContinentOptions();
            }
        });
    }


    public void run() {
        frame.setContentPane(new serverAdminWindow().mainpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void showUserOptions(){
        frame.getContentPane().removeAll();
        frame.setContentPane(new UserOptions(frame).mainpanel);
        frame.pack();
        frame.setVisible(true);
    }

    public void showWorldOptions(){
        frame.getContentPane().removeAll();
        frame.setContentPane(new WorldOptions().mainpanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void showContinentOptions() {
        frame.getContentPane().removeAll();
        frame.setContentPane(new ContinentOptions().mainpanel);
        frame.pack();
        frame.setVisible(true);
    }

}
