package com.hamming.halbo.forms.userForms;

import com.hamming.halbo.forms.JlistMethods;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserList {
    public JPanel mainpanel;
    private JList userList;
    private JButton closeButton;

    private JFrame thisFrame;

    public UserList(JFrame frame){
        thisFrame = frame;
        userList.setModel(JlistMethods.fillJlistUsers());
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
            }
        });
    }

}
