package com.hamming.halbo.client.controllers;

import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.MovementDto;

public class MovementSender implements Runnable {

    private ConnectionController connectionController;
    private ProtocolHandler protocolHandler;
    private ViewController viewController;
    boolean running = false;
    private static int INTERVAL = 50; // Milliseconds 20hz

    public MovementSender(ViewController viewController, ConnectionController connectionController) {
        this.connectionController = connectionController;
        this.protocolHandler = new ProtocolHandler();
        this.viewController = viewController;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            if (connectionController.isConnected()) {
                MovementDto data = viewController.getCurrentMoveRequest();
                if (data != null) {
                    connectionController.send(protocolHandler.getMoveCommand(data));
                }
            }
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
    }
}
