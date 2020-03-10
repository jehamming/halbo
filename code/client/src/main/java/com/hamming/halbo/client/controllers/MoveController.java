package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.Controllers;
import com.hamming.halbo.client.interfaces.MovementListener;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.*;
import com.hamming.halbo.net.CommandReceiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveController implements CommandReceiver {

    private ProtocolHandler protocolHandler;
    private ConnectionController connectionController;
    private List<MovementListener> movementListeners;
    private UserController userController;
    private WorldController worldController;
    private ContinentController continentController;
    private CityController cityController;

    public MoveController(Controllers controllers) {
        this.connectionController = controllers.getConnectionController();
        this.userController = controllers.getUserController();
        this.worldController = controllers.getWorldController();
        this.continentController = controllers.getContinentController();
        this.cityController = controllers.getCityController();
        protocolHandler = new ProtocolHandler();
        movementListeners = new ArrayList<MovementListener>();
        connectionController.registerReceiver(Protocol.Command.TELEPORT, this);
        connectionController.registerReceiver(Protocol.Command.LOCATION, this);
        connectionController.registerReceiver(Protocol.Command.MOVE, this);
    }

    public void addMovementListener(MovementListener l) {
        movementListeners.add(l);
    }

    public String teleportRequest(UserDto user, WorldDto world, ContinentDto continent, CityDto city) {
        String message = null;
        if (user != null && world != null && continent != null && city != null) {
            String cmd = protocolHandler.getTeleportCommand(user.getId(), world, continent, city);
            connectionController.send(cmd);
        } else {
            message = "Please select a World, Continent, City and Baseplate to teleport to!";
        }
        return message;
    }


    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        switch (cmd) {
            case MOVE:
                System.out.println(getClass().getName() + ":" + cmd + ": NOT IMPLEMENTED YET");
                break;
            case TELEPORT:
                teleportResult(data);
                break;
            case LOCATION:
                handleLocation(data);
                break;
        }
    }

    private void handleLocation(String[] data) {
        UserLocationDto loc = new UserLocationDto();
        loc.setValues(data);
        if (userController.getCurrentUser().getId().equals( loc.getUserId())) {
            // Current logged in user!
            move(userController.getCurrentUser(), loc);
        } else  {
            // Movement of other user!
            UserDto user = userController.getUser(loc.getUserId());
            if (user != null ) {
                move(user, loc);
            }
        }
    }

    private void teleportResult(String[] data) {
        String status = data[0];
        String[] values = Arrays.copyOfRange(data, 1, data.length);
        String msg = "";
        if (Protocol.SUCCESS.equals(status)) {
            UserLocationDto loc = new UserLocationDto();
            loc.setValues(values);
            teleported(loc);
        } else {
            msg = Arrays.toString(values);
            System.out.println("Teleport failed: " + msg);
        }
    }

    private void teleported(UserLocationDto location) {
        for (MovementListener l: movementListeners) {
            l.teleported(location);
        }
    }

    private void move(UserDto user, UserLocationDto location) {
        for (MovementListener l: movementListeners) {
            l.userMoved(user,location);
        }
    }

    public UserDto getCurrentUser() {
        return userController.getCurrentUser();
    }

}
