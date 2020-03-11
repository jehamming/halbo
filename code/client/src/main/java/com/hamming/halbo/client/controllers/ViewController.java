package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.CalcTools;
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
import org.lwjgl.util.vector.Vector3f;

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
    private Viewer viewer;
    private long sequenceNumber;
    private List<MovementDto> movementRequests;
    private UserLocationDto lastreceivedLocation;

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
        movementRequests = new ArrayList<MovementDto>();
    }

    @Override
    public void userMoved(UserDto user, UserLocationDto l) {
        if (user.equals(moveController.getCurrentUser())) {
            lastreceivedLocation = l;
            moveCurrentUser(l);
        } else {
            viewer.setLocation(user.getId(), user.getName(), l.getX(), l.getY(), l.getZ(), l.getPitch(), l.getYaw());
        }
    }

    // The method uses client side prediction to counter lag.
    private void moveCurrentUser(UserLocationDto l) {
        // First : Set the location based on the server respons (server = authoritive
        viewer.setLocation(l.getX(), l.getY(), l.getZ(), l.getPitch(), l.getYaw());
        // Remove all the request before this sequence (if any)
        deleteRequestsUpTO(l.getSequence());
        // Apply all the requests that server has not processed yet.
        applyMoveRequests(l);
    }

    private void deleteRequestsUpTO(long sequence) {
        movementRequests.removeIf(request -> request.getSequence() <= sequence);
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
        viewer.setLocation(newLocation.getX(), newLocation.getY(), newLocation.getZ(), newLocation.getPitch(), newLocation.getYaw());
        System.out.println(this.getClass().getName() + "-Scheduled-"+dto.getSequence()+"-"+newLocation.getX()+","+newLocation.getY()+","+newLocation.getZ()+","+newLocation.getPitch()+"," +newLocation.getYaw());
        return newLocation;
    }

    @Override
    public void teleported(UserLocationDto location) {
        lastreceivedLocation = location;
        BaseplateDto baseplate = cityController.getBaseplate(location.getBaseplateId());
        viewer.setBaseplate(baseplate.getName(), baseplate.getLength(), baseplate.getWidth());
    }

    public MovementDto getCurrentMoveRequest() {
        MovementDto dto = null;
        boolean forward = viewer.getForward();
        boolean back = viewer.getBack();
        boolean left = viewer.getLeft();
        boolean right = viewer.getRight();
        float pitch = viewer.getPitch();
        float yaw = viewer.getYaw();
        if (forward || back || left || right || pitch != 0 || yaw != 0) {
            dto = new MovementDto(getNextSequenceNumber(), forward, back, left, right, pitch, yaw);
            synchronized (movementRequests) {
                movementRequests.add(dto);
            }
         applyMoveRequest(dto, lastreceivedLocation);
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
