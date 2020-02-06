package com.hamming.halbo;


import com.hamming.halbo.model.User;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.game.action.Action;
import com.hamming.halbo.model.UserLocation;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection implements Runnable {

    private User user;
    private UserLocation userLocation;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean running = true;
    private GameController gameController;
    private ProtocolHandler protocolHandler;

    public ClientConnection(Socket s, BufferedReader in, PrintWriter out, GameController controller) {
        this.socket = s;
        this.in = in;
        this.out = out;
        this.gameController = controller;
        this.protocolHandler = new ProtocolHandler(controller, this);
    }

    @Override
    public void run() {
        while (running) {
            try {
                String s = in.readLine();
                if ( s != null ) {
                    handleInput(s);
                }
            } catch (Exception e) {
                running = false;
                e.printStackTrace();
            }
        }
    }

    private void handleInput(String s) {
        Action cmd = protocolHandler.parseCommandString(s);
        if (cmd != null ) {
            gameController.addCommand(cmd);
        }
    }

    public void send(String s) {
        System.out.println("HALBOCLIENT-SEND:" + s);
        out.println(s);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
