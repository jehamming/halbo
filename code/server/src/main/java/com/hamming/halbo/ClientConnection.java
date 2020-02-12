package com.hamming.halbo;


import com.hamming.halbo.factories.DTOFactory;
import com.hamming.halbo.game.*;
import com.hamming.halbo.game.action.UserConnectedAction;
import com.hamming.halbo.game.action.UserDisconnectedAction;
import com.hamming.halbo.model.User;
import com.hamming.halbo.game.action.Action;
import com.hamming.halbo.model.UserLocation;
import com.hamming.halbo.model.dto.UserLocationDto;
import com.hamming.halbo.util.StringUtils;

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
                System.out.println(this.getClass().getName() + ":" + "Error:" + e.getMessage());
            }
        }
        gameController.removeListener(this);
        if (user != null ) {
            gameController.userDisconnected(user);
        }
        try { socket.close(); } catch (IOException e) {}
        System.out.println(this.getClass().getName() + ":" + "Client Socket closed");
    }

    private void handleInput(String s) {
        Action cmd = protocolHandler.parseCommandString(s);
        if (cmd != null ) {
            gameController.addCommand(cmd);
        }
    }

    public void send(String s) {
        System.out.println(this.getClass().getName() + ":" + "Client send:" + s);
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

    public void sendFullGameState() {
        if (isLoggedIn()) {
            // Logged in Users;
            for ( User u: gameController.getGameState().getOnlineUsers()) {
                if (!u.getId().equals(user.getId())) {
                    handleUserConnected(u);
                }
            }
            // UserLocations
            for (UserLocation loc : gameController.getGameState().getUserLocations().values()) {
                handleUserLocation(loc);
            }
        }
    }

    public boolean isLoggedIn() {
        return user != null;
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
            case USERLOCATION:
                handleUserLocation((UserLocation) event.getObject());
        }
    }

    private void handleUserLocation(UserLocation loc) {
        if (loc.getUser().equals(user)) {
            // Location of this user!
            userLocation = loc;
        }
        if (userLocation != null && loc.getCity().equals(userLocation.getCity())) {
            UserLocationDto dto = DTOFactory.getInstance().getUserLocationDTO(loc);
            send(Protocol.Command.LOCATION.ordinal() + StringUtils.delimiter + dto.toNetData());
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

    public void sendUserLocation() {
        userLocation = gameController.getGameState().getLocation(user);
        if (userLocation != null ) {
            UserLocationDto dto = DTOFactory.getInstance().getUserLocationDTO(userLocation);
            send(Protocol.Command.LOCATION.ordinal() + StringUtils.delimiter + dto.toNetData());
        }
    }
}
