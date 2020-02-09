package com.hamming.halbo;


import com.hamming.halbo.game.GameStateEvent;
import com.hamming.halbo.game.GameStateListener;
import com.hamming.halbo.game.action.UserConnectedAction;
import com.hamming.halbo.game.action.UserDisconnectedAction;
import com.hamming.halbo.model.User;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.game.action.Action;
import com.hamming.halbo.model.UserLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection implements Runnable, GameStateListener {

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
        Scanner scanner = new Scanner(in);
        while (running && scanner.hasNextLine()) {
            try {
                String s = scanner.nextLine();
                if ( s != null ) {
                    handleInput(s);
                }
            } catch (Exception e) {
                running = false;
                System.out.println("Error:" + e.getMessage());
            }
        }
        gameController.removeGameStateListener(this);
        try { socket.close(); } catch (IOException e) {}
        System.out.println("Client Socket closed");
    }

    private void handleInput(String s) {
        Action cmd = protocolHandler.parseCommandString(s);
        if (cmd != null ) {
            gameController.addCommand(cmd);
        }
    }

    public void send(String s) {
        System.out.println("Client send:" + s);
        out.println(s);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if ( user != null ) {
            gameController.userConnected(user);
        }
    }


    @Override
    public void newGameState(GameStateEvent event) {
        switch (event.getType()) {
            case USERCONNECTED:
                handleUserConnected((User) event.getObject());
                break;
            case USERDISCONNECTED:
                handleUserDisconnected((User) event.getObject());
                break;
        }
    }

    private void handleUserConnected(User u) {
        if (user != null && !user.equals(u)) {
            UserConnectedAction action = new UserConnectedAction(gameController, this);
            action.setUserId(u.getId().toString());
            gameController.addCommand( action );
        }
    }

    private void handleUserDisconnected(User u) {
        if (user != null && !user.equals(u)) {
            UserDisconnectedAction action = new UserDisconnectedAction(gameController, this);
            action.setUserId(u.getId().toString());
            gameController.addCommand( action );
        }
    }

}
