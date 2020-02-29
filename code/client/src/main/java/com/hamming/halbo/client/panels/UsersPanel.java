package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.controllers.UserController;
import com.hamming.halbo.client.interfaces.UserListener;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.UserDto;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Enumeration;

public class UsersPanel extends JPanel implements UserListener {

    private DefaultListModel listModel;
    private ProtocolHandler protocolHandler;
    private UserController userController;


    private class ListItem {
        private UserDto user;
        public ListItem(UserDto usr) {
            this.user = usr;
        }

        @Override
        public String toString() {
            return user.getName();
        }

        public UserDto getUser() {
            return user;
        }
    }


    public UsersPanel(UserController userController) {
        this.userController = userController;
        userController.addUserListener(this);
        protocolHandler = new ProtocolHandler();
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Online Users"));
        listModel = new DefaultListModel();
        JList<ListItem> listOfUsers = new JList<ListItem>(listModel);
        listOfUsers.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting() ) { //Else this is called twice!
                    ListItem item = listOfUsers.getSelectedValue();
                    if (item != null && item.getUser()!= null) {
                        userSelected(item.getUser());
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(listOfUsers);
        scrollPane.setPreferredSize(new Dimension(220,90));
        add(scrollPane);
    }

    private void userSelected(UserDto user) {
        // Not implemented yet
    }

    private void addUser(UserDto user) {
        if (!contains(user)) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ListItem item = new ListItem(user);
                    listModel.addElement(item);
                }
            });
        }
    }

    private void removeUser(UserDto user) {
        if (contains(user)) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ListItem item = findListItemFor(user);
                    listModel.removeElement(item);
                }
            });
        }
    }

    public boolean contains(UserDto dto) {
        return findUser(dto) != null;
    }

    public UserDto findUser(UserDto dto) {
        UserDto found=null;
        Enumeration<ListItem> enumerator = listModel.elements();
        while (enumerator.hasMoreElements()) {
            ListItem item = enumerator.nextElement();
            if (item.getUser().getId().equals(dto.getId())) {
                found = item.getUser();
            }
        }
        return found;
    }

    public ListItem findListItemFor(UserDto dto) {
        ListItem found=null;
        Enumeration<ListItem> enumerator = listModel.elements();
        while (enumerator.hasMoreElements()) {
            ListItem item = enumerator.nextElement();
            if (item.getUser().getId().equals(dto.getId())) {
                found = item;
            }
        }
        return found;
    }


    public void empty() {
        listModel.removeAllElements();
    }



    @Override
    public void userConnected(UserDto user) {
        addUser(user);
    }

    @Override
    public void userDisconnected(UserDto user) {
        removeUser(user);
    }

    @Override
    public void loginResult(boolean success, String message) {

    }



}
