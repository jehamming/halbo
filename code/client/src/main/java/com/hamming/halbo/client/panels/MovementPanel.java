package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.HALBOTestToollWindow;
import com.hamming.halbo.client.ToolsWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.UserLocationDto;
import com.hamming.halbo.net.CommandReceiver;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovementPanel extends JPanel implements CommandReceiver, Runnable {

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

        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
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
        toolsWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        forward = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        back = true;
                        break;
                    case KeyEvent.VK_LEFT:
                        left = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        right = true;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        forward = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        back = false;
                        break;
                    case KeyEvent.VK_LEFT:
                        left = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        right = false;
                        break;
                }
            }
        });
    }

    public void doMove(boolean forward, boolean back, boolean left, boolean right) {
        if ( client.isConnected() && client.getUserLocation() != null ) {
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
            String txt = "X:" + dto.getX() + ", Y:" + dto.getY() + ", Z:" + dto.getZ();
            String details = dto.getWorldId() +"," + dto.getContinentId()+","+dto.getCityId()+","+dto.getBaseplateId();
            if (dto.getUserId().equals(client.getUser().getId())) {
                client.setUserLocation(dto);
                lblLocationDetails.setText(details);
                lblCurrentLocation.setText(txt);
            } else {
                System.out.println("Got Location for user "+ dto.getUserId() + ": "+ txt);
            }
        }
    }


    public void empty() {
        lblCurrentLocation.setText("");
        lblLocationDetails.setText("");
    }


    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Thread.sleep(100);
                if ( forward || back || left || right) {
                    doMove(forward, back, left, right);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public void stop() {
        running = false;
    }
}
