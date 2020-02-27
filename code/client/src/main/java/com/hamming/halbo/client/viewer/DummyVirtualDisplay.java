package com.hamming.halbo.client.viewer;

import com.hamming.halbo.client.controllers.MoveController;
import com.hamming.halbo.client.interfaces.IVirtualDisplay;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.model.dto.UserLocationDto;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DummyVirtualDisplay extends JFrame implements IVirtualDisplay, Runnable  {

    private MoveController moveController;
    private JLabel lblMessage;
    private JLabel lblLocation;
    private JLabel lblDetails;
    private JTextArea taOther;
    private boolean left = false;
    private boolean right = false;
    private boolean forward = false;
    private boolean back = false;
    private boolean running = false;

    public DummyVirtualDisplay() {
        init();
        Thread t = new Thread(this);
        t.start();
    }

    public void init() {        //Create and set up the window.
        setTitle("DummyVirtualWindow");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Display the window.

        lblMessage = new JLabel();
        lblLocation = new JLabel();
        lblDetails = new JLabel();
        taOther = new JTextArea();
        taOther.setFocusable(false);
        taOther.setRows(5);
        taOther.setEditable(false);

        JPanel p = new JPanel();
        p.setLayout( new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(lblMessage);
        p.add(lblLocation);
        p.add(lblDetails);
        p.add(taOther);

        getContentPane().add(p);

        pack();
        setVisible(true);
        setSize(600, 500);
        setLocation(350, 50);

        addKeylistener();

    }

    private void addKeylistener() {
        this.addKeyListener(new KeyListener() {
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
        moveController.moveRequest(forward, back, left, right);
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

    @Override
    public void setMovementController(MoveController controller) {
        this.moveController = controller;
        moveController.addMovementListener(this);
    }

    @Override
    public void userMoved(UserDto user, UserLocationDto l) {
        if (user.equals(moveController.getCurrentUser())) {
            updateLocationInTextField("MOVED", l);
        } else {
            addOtherUserText(l.toString());
        }
    }

    private void addOtherUserText(String str) {
        String txt = taOther.getText();
        txt = txt.concat(str + "\n");
        taOther.setText(txt);
    }

    @Override
    public void teleported(UserLocationDto location) {
        updateLocationInTextField("TELEPORTED" , location);
    }



    private void updateLocationInTextField(String msg, UserLocationDto l) {
        lblMessage.setText(msg);
        String location = "World: " + l.getWorldId() +
                ", Continent:" + l.getContinentId() +
                ", City:" + l.getCityId() +
                ", Baseplate:" + l.getBaseplateId();
        lblLocation.setText(location);
        String details = "X:" + l.getX() +
                ", Y:" + l.getY() +
                ", Z:" + l.getZ();
        lblDetails.setText(details);
    }

    @Override
    public void connected() {
        lblDetails.setText("");
        lblLocation.setText("");
        lblMessage.setText("");
        taOther.setText("");
    }

    @Override
    public void disconnected() {
        lblDetails.setText("");
        lblLocation.setText("");
        lblMessage.setText("");
        taOther.setText("");
    }
}
