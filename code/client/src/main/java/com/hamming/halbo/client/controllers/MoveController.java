package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.IMovementListener;
import com.hamming.halbo.client.interfaces.IUserListener;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.CityDto;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.net.CommandReceiver;

import java.util.ArrayList;
import java.util.List;

public class MoveController implements CommandReceiver {

    private ProtocolHandler protocolHandler;
    private ConnectionController connectionController;
    private List<IMovementListener> movementListeners;
    private UserController userController;
    private WorldController worldController;
    private ContinentController continentController;
    private CityController cityController;

    public MoveController(ConnectionController connectionController, UserController userController, WorldController worldController, ContinentController continentController, CityController cityController) {
        this.connectionController = connectionController;
        this.userController = userController;
        this.worldController = worldController;
        this.continentController = continentController;
        this.cityController = cityController;
        protocolHandler = new ProtocolHandler();
        movementListeners = new ArrayList<IMovementListener>();
        connectionController.registerReceiver(Protocol.Command.TELEPORT, this);
        connectionController.registerReceiver(Protocol.Command.LOCATION, this);
        connectionController.registerReceiver(Protocol.Command.MOVE, this);
    }

    public void addMovementListener(IMovementListener l) {
        movementListeners.add(l);
    }

    public String teleportRequest() {
        String message = null;
        WorldDto world = worldController.getSelectedWorld();
        ContinentDto continent = continentController.getSelectedContinent();
        CityDto city = cityController.getSelectedCity();
        UserDto user = userController.getCurrentUser();
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
                System.out.println(getClass().getName() + cmd + ": NOT IMPLEMENTED YET");
                break;
            case TELEPORT:
                System.out.println(getClass().getName() + cmd + ": NOT IMPLEMENTED YET");
                break;
            case LOCATION:
                System.out.println(getClass().getName() + cmd + ": NOT IMPLEMENTED YET");
                break;
        }
    }

}
