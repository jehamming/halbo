package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.Controllers;
import com.hamming.halbo.client.interfaces.ConnectionListener;
import com.hamming.halbo.client.interfaces.MovementListener;
import com.hamming.halbo.client.interfaces.Viewer;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.model.dto.MovementDto;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.model.dto.UserLocationDto;
import org.lwjgl.input.Mouse;

import java.util.List;

public class ViewController implements MovementListener, ConnectionListener {

    private ProtocolHandler protocolHandler;
    private ConnectionController connectionController;
    private UserController userController;
    private WorldController worldController;
    private ContinentController continentController;
    private CityController cityController;
    private MoveController moveController;
    private MovementSender movementSender;
    private Viewer viewer;
    private long sequenceNumber;

    public ViewController(Viewer viewer, Controllers controllers) {
        this.connectionController = controllers.getConnectionController();
        this.userController = controllers.getUserController();
        this.worldController = controllers.getWorldController();
        this.continentController = controllers.getContinentController();
        this.cityController = controllers.getCityController();
        this.moveController = controllers.getMoveController();
        this.viewer = viewer;
        protocolHandler = new ProtocolHandler();
        moveController.addMovementListener(this);
        connectionController.addConnectionListener(this);
        movementSender = new MovementSender(this, connectionController);
        sequenceNumber = 0;
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

    public MovementDto getCurrentMoveCommand() {
        MovementDto dto = null;
        boolean forward = viewer.getForward();
        boolean back = viewer.getBack();
        boolean left = viewer.getLeft();
        boolean right = viewer.getRight();
        float pitch = viewer.getPitch();
        float yaw = viewer.getYaw();
        if (forward || back || left || right || pitch != 0 || yaw != 0) {
            dto = new MovementDto(getNextSequenceNumber(), forward, back, left, right, pitch, yaw);
        }
        return  dto;
    }

    private long getNextSequenceNumber() {
        return sequenceNumber++;
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
