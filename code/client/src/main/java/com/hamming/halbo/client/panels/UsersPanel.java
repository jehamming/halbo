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

public class UsersPanel extends JPanel implements CommandReceiver {

    private HALBOClientWindow client;
    private DefaultListModel listModel;
    private ProtocolHandler protocolHandler;


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
        listOfUsers.setPreferredSize(new Dimension(200,50));
        JScrollPane scrollPane = new JScrollPane(listOfUsers);
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
                listModel.addElement(user);
                break;
            case USERDISCONNECTED:
                listModel.removeElement(user);
                break;

        }
    }


    public void empty() {
        listModel.removeAllElements();
    }


}
