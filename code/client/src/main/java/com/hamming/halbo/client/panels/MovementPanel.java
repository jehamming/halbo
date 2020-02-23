package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOTestToollWindow;
import com.hamming.halbo.client.ToolsWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.model.dto.UserLocationDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class MovementPanel extends JPanel implements CommandReceiver {

    private HALBOTestToollWindow client;
    private ToolsWindow toolsWindow;
    private DefaultListModel listModel;
    private ProtocolHandler protocolHandler;

    private boolean forward = false;
    private boolean back = false;
    private boolean left = false;
    private boolean right = false;

    private JLabel lblCurrentLocation;
    private JLabel lblLocationDetails;

    boolean running = true;


    public MovementPanel(HALBOTestToollWindow clientWindow, ToolsWindow toolsWindow) {
        this.client = clientWindow;
        this.toolsWindow = toolsWindow;
        protocolHandler = new ProtocolHandler();
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Position"));
        add(new JLabel("User Arrow Keys to Move"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        lblLocationDetails = new JLabel();
        add(lblLocationDetails);
        lblCurrentLocation = new JLabel();
        add(lblCurrentLocation);
        toolsWindow.setFocusable(true);
        toolsWindow.requestFocus();
    }



    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        switch (cmd) {
            case MOVE:
                System.out.println(this.getClass().getName() + ":" + "MOVE Command;" + data);
                break;
            case LOCATION:
                doLocation(data);
                break;
            case TELEPORT:
                checkTeleport(data);
                break;
        }
    }

    private UserLocationDto doLocation(String[] data) {
        UserLocationDto dto = new UserLocationDto();
        dto.setValues(data);
        String txt = "X:" + dto.getX() + ", Y:" + dto.getY() + ", Z:" + dto.getZ();
        String details = dto.getWorldId() +"," + dto.getContinentId()+","+dto.getCityId()+","+dto.getBaseplateId();
        if (dto.getUserId().equals(client.getUser().getId())) {
            client.setUserLocation(dto);
            lblLocationDetails.setText(details);
            lblCurrentLocation.setText(txt);
        } else {
            System.out.println(this.getClass().getName() + ":" + "Got Location for user "+ dto.getUserId() + ": "+ txt);
            dto = null;
        }
        return dto;
    }

    private void checkTeleport(String[] data) {
            String status = data[0];
            String[] values = Arrays.copyOfRange(data, 1, data.length);
            if (Protocol.SUCCESS.equals(status)) {
                UserLocationDto loc = doLocation(values);
                client.teleport(loc);
            } else {
                String msg = Arrays.toString(values);
                JOptionPane.showMessageDialog(this, "Teleport failed : " + msg);
            }
    }


    public void empty() {
        lblCurrentLocation.setText("");
        lblLocationDetails.setText("");
    }

}
