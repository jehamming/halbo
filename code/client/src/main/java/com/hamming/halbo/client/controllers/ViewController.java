package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.CalcTools;
import com.hamming.halbo.client.Controllers;
import com.hamming.halbo.client.engine.GLViewer;
import com.hamming.halbo.client.interfaces.ConnectionListener;
import com.hamming.halbo.client.interfaces.MovementListener;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.model.dto.MovementDto;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.model.dto.UserLocationDto;

import java.util.ArrayList;
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
    private GLViewer viewer;
    private long sequenceNumber;
    private List<MovementDto> movementRequests;
    private UserLocationDto lastreceivedLocation;

    public ViewController(GLViewer viewer, Controllers controllers) {
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
        movementRequests = new ArrayList<MovementDto>();
    }

    @Override
    public void userMoved(UserDto user, UserLocationDto l) {
        if (user.equals(moveController.getCurrentUser())) {
            if (!l.getBaseplateId().equals(viewer.getCurrentBaseplateId())) {
                BaseplateDto baseplate = cityController.getBaseplate(l.getBaseplateId());
                viewer.addBaseplate((baseplate.getId(), baseplate.getName(), baseplate.getLength(), baseplate.getWidth());
            }
            lastreceivedLocation = l;
            moveCurrentUser(l);
        } else {
            viewer.setLocation(user.getId(), l.getX(), l.getY(), l.getZ(), l.getPitch(), l.getYaw());
        }
    }

    // The method uses client side prediction to counter lag.
    private void moveCurrentUser(UserLocationDto l) {
        viewer.followPlayer(l.getUserId());
        // First : Set the location based on the server respons (server = authoritive
        viewer.setLocation(l.getUserId(), l.getX(), l.getY(), l.getZ(), l.getPitch(), l.getYaw());
        // Remove all the request before this sequence (if any)
        deleteRequestsUpTO(l.getSequence());
        // Apply all the requests that server has not processed yet.

        // FIXME Remove comment
       // applyMoveRequests(l);
    }

    private void deleteRequestsUpTO(long sequence) {
        synchronized (movementRequests) {
            movementRequests.removeIf(request -> request.getSequence() <= sequence);
        }
    }

    public void applyMoveRequests(UserLocationDto loc) {
        UserLocationDto locationToCalculateOn = loc;
        synchronized (movementRequests) {
            for (MovementDto dto : movementRequests) {
                locationToCalculateOn = applyMoveRequest(dto, locationToCalculateOn);
            }
        }
    }

    public UserLocationDto applyMoveRequest(MovementDto dto, UserLocationDto loc) {
        UserLocationDto newLocation = CalcTools.calculateNewPosition(dto, loc);
        viewer.setLocation(newLocation.getUserId(), newLocation.getX(), newLocation.getY(), newLocation.getZ(), newLocation.getPitch(), newLocation.getYaw());
        System.out.println(this.getClass().getName() + "-Scheduled-"+dto.getSequence()+"-"+newLocation.getX()+","+newLocation.getY()+","+newLocation.getZ()+","+newLocation.getPitch()+"," +newLocation.getYaw());
        return newLocation;
    }

    @Override
    public void teleported(UserLocationDto location) {
        lastreceivedLocation = location;
        BaseplateDto baseplate = cityController.getBaseplate(location.getBaseplateId());
        viewer.setBaseplate(baseplate.getId(), baseplate.getName(), baseplate.getLength(), baseplate.getWidth());
    }

    public MovementDto getCurrentMoveRequest() {
        MovementDto dto = null;
        boolean forward = viewer.getForward();
        boolean back = viewer.getBack();
        boolean left = viewer.getLeft();
        boolean right = viewer.getRight();
        if (forward || back || left || right) {
            dto = new MovementDto(getNextSequenceNumber(), forward, back, left, right);
            synchronized (movementRequests) {
                movementRequests.add(dto);
            }
            // FIXME Remove comment
//            applyMoveRequest(dto, lastreceivedLocation);
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
