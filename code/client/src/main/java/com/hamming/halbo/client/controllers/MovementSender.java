package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.Viewer;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.MovementDto;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Queue;

public class MovementSender implements Runnable {

    private ConnectionController connectionController;
    private ProtocolHandler protocolHandler;
    private ViewController viewController;
    boolean running = false;
    private static int INTERVAL = 100; // Milliseconds

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
            MovementDto data = viewController.getCurrentMoveCommand();
            if (data != null) {
                connectionController.send(protocolHandler.getMoveCommand(data));
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
