package com.hamming.halbo.forms.userForms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserOptions {

    public JPanel mainpanel;
    private JButton createUserButton;
    private JButton updateAUserButton;
    private JButton searchByName;
    private JButton showUserList;
    private JButton closeButton;

    private JFrame thisFrame;

    public UserOptions(JFrame frame) {
        thisFrame = frame;
        createUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateUser();
            }
        });
        searchByName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSearchUser();
            }
        });
        updateAUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUserOptions();
            }
        });
        showUserList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUserListForm();
            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
            }
        });
    }

    private void showUserListForm() {
        JFrame frame = new JFrame("User list");
        frame.setContentPane(new UserList(frame).mainpanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void showUserOptions() {
        JFrame frame = new JFrame("Update user");
        frame.setContentPane(new updateUserForm(frame).mainpanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void showSearchUser() {
        JFrame frame = new JFrame("Search user");
        frame.setContentPane(new SearchUserForm(frame).mainpanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void showCreateUser() {
        JFrame frame = new JFrame("Create user");
        frame.setContentPane(new CreateUser(frame).mainpanel);
        frame.pack();
        frame.setVisible(true);
    }


}
