package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOClientWindow;
import com.hamming.halbo.client.ToolsWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.model.dto.UserLocationDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovementPanel extends JPanel implements CommandReceiver {

    private HALBOClientWindow client;
    private ToolsWindow toolsWindow;
    private DefaultListModel listModel;
    private ProtocolHandler protocolHandler;


    private JTextArea txtCurrentLocation;
    private JLabel lblRequest;


    public MovementPanel(HALBOClientWindow clientWindow, ToolsWindow toolsWindow) {
        this.client = clientWindow;
        this.toolsWindow = toolsWindow;
        protocolHandler = new ProtocolHandler();
        createPanel();
    }

    private void createPanel() {
        setBorder(new TitledBorder("Position"));
        add(new JLabel("User Arrow Keys to Move"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        txtCurrentLocation = new JTextArea();
        txtCurrentLocation.setLineWrap(true);
        txtCurrentLocation.setEditable(false);
        add(txtCurrentLocation);
        toolsWindow.setFocusable(true);
        toolsWindow.requestFocus();
        toolsWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        forwardPressed();
                        break;
                    case KeyEvent.VK_DOWN:
                        backPressed();
                        break;
                    case KeyEvent.VK_LEFT:
                        leftPressed();
                        break;
                    case KeyEvent.VK_RIGHT:
                        rightPressed();
                        break;
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void rightPressed() {
        doMove(false, false, false, true);
    }

    private void leftPressed() {
        doMove(false, false, true, false);
    }

    private void backPressed() {
        doMove(false, true, false, false);
    }

    private void forwardPressed() {
        doMove(true, false, false, false);
    }


    public void doMove(boolean forward, boolean back, boolean left, boolean right) {
        System.out.println("doMove()");
        if ( client.isConnected() ) {
            String cmd = protocolHandler.getMoveCommand(forward, back, left, right);
            client.send(cmd);
        }
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        if (cmd.equals(Protocol.Command.MOVE)) {
            System.out.println("MOVE Command;" + data);
        } else if (cmd.equals(Protocol.Command.LOCATION)) {
            UserLocationDto dto = new UserLocationDto();
            dto.setValues(data);
            txtCurrentLocation.setText(dto.toString());
        }
    }


    public void empty() {
        //TODO Implement
    }


}
