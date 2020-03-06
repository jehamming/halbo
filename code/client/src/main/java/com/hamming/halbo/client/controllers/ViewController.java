package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.ConnectionListener;
import com.hamming.halbo.client.interfaces.MovementListener;
import com.hamming.halbo.client.interfaces.Viewer;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.model.dto.UserLocationDto;

public class ViewController implements MovementListener, ConnectionListener {

    private ProtocolHandler protocolHandler;
    private ConnectionController connectionController;
    private UserController userController;
    private WorldController worldController;
    private ContinentController continentController;
    private CityController cityController;
    private MoveController moveController;
    private Viewer viewer;

    public ViewController(Viewer viewer, ConnectionController connectionController, UserController userController, WorldController worldController, ContinentController continentController, CityController cityController, MoveController moveController) {
        this.connectionController = connectionController;
        this.userController = userController;
        this.worldController = worldController;
        this.continentController = continentController;
        this.cityController = cityController;
        this.moveController = moveController;
        this.viewer = viewer;
        protocolHandler = new ProtocolHandler();
        moveController.addMovementListener(this);
        connectionController.addConnectionListener(this);
    }

    @Override
    public void userMoved(UserDto user, UserLocationDto l) {
        if (user.equals(moveController.getCurrentUser())) {
            viewer.setLocation(l.getX(), l.getY(), l.getZ(), l.getPitch(), l.getYaw());
        } else {
            viewer.setLocation(user.getId(), user.getName(), l.getX(), l.getY(), l.getZ(), l.getPitch(), l.getYaw());
        }
    }


    @Override
    public void teleported(UserLocationDto location) {
        BaseplateDto baseplate = cityController.getBaseplate(location.getBaseplateId());
        viewer.setBaseplate(baseplate.getName(), baseplate.getLength(), baseplate.getWidth());
    }


    @Override
    public void connected() {
        viewer.resetView();
    }

    @Override
    public void disconnected() {
        viewer.resetView();
    }



}
