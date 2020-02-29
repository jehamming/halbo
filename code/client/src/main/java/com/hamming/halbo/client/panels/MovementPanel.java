package com.hamming.halbo.client.panels;

import com.hamming.halbo.client.BaseWindow;
import com.hamming.halbo.client.controllers.MoveController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MovementPanel extends JPanel implements Runnable{

    private boolean left = false;
    private boolean right = false;
    private boolean forward = false;
    private boolean back = false;
    private boolean running = false;
    private MoveController moveController;

    public MovementPanel(MoveController moveController) {
        this.moveController = moveController;
        createPanel();
        addKeylistener();

        Thread t = new Thread(this);
        t.start();

    }

    private void createPanel() {
        setBorder(new TitledBorder("Movement"));
        add(new JLabel("Use ArrowKeys to Move)"));
    }

    private void addKeylistener() {
        addKeyListener(new KeyListener() {
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



    public void stop() {
        running = false;
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






}
