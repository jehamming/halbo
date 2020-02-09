package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOClientWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Enumeration;

public class UsersPanel extends JPanel implements CommandReceiver {

    private HALBOClientWindow client;
    private DefaultListModel listModel;
    private ProtocolHandler protocolHandler;

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


    public UsersPanel(HALBOClientWindow clientWindow) {
        this.client = clientWindow;
        protocolHandler = new ProtocolHandler();
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Online Users"));
        listModel = new DefaultListModel();
        JList<UserDto> listOfUsers = new JList<UserDto>(listModel);
        listOfUsers.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting() ) { //Else this is called twice!
                    UserDto dto = listOfUsers.getSelectedValue();
                    if (dto != null) {
                        userSelected(dto);
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

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        UserDto user = new UserDto();
        user.setValues(data);
        switch (cmd) {
            case USERCONNECTED:
                addUser(user);
                break;
            case USERDISCONNECTED:
                removeUser(user);
                break;

        }
    }

    private void addUser(UserDto user) {
        if (!contains(user)) {
            listModel.addElement(user);
        }
    }

    private void removeUser(UserDto user) {
        if (contains(user)) {
            listModel.removeElement(user);
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


    public void empty() {
        listModel.removeAllElements();
    }


}
