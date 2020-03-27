package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.CalcTools;
import com.hamming.halbo.client.Controllers;
import com.hamming.halbo.client.engine.GLViewer;
import com.hamming.halbo.client.interfaces.ConnectionListener;
import com.hamming.halbo.client.interfaces.MovementListener;
import com.hamming.halbo.client.interfaces.UserListener;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.SimpleCityGrid;
import com.hamming.halbo.model.dto.*;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ViewController implements MovementListener, ConnectionListener, UserListener {

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
        userController.addUserListener(this);
        movementSender = new MovementSender(this, connectionController);
        sequenceNumber = 0;
        movementRequests = new ArrayList<MovementDto>();
    }

    @Override
    public void userMoved(UserDto user, UserLocationDto l) {
        if (user.equals(userController.getCurrentUser())) {
            if (lastreceivedLocation == null || !lastreceivedLocation.getCityId().equals(l.getCityId())) {
                // New City, resetViewer and send the grid to the viewer
                CityDto city = cityController.getCity(l.getCityId());
                setCityGrid(city.getCityGrid());
            }
            lastreceivedLocation = l;
            moveCurrentUser(l);
        } else {
            if ( viewer.getPlayer(user.getId()) == null) {
                viewer.addPlayer(user.getId(), user.getName());
            }
            Vector3f viewerLocation = getViewerLocation(l);
            viewer.setLocation(user.getId(), viewerLocation, l.getPitch(), l.getYaw());
        }
    }

    private void setCityGrid(SimpleCityGrid cityGrid) {
        viewer.resetTerrains();
        List<String> baseplateIds = cityGrid.getBaseplateIds();
        baseplateIds.forEach(baseplateId -> {
            BaseplateDto baseplate = cityController.getBaseplate(baseplateId);
            SimpleCityGrid.GridPosition pos = cityGrid.getPosition(baseplateId);
            viewer.addBaseplate(baseplateId, baseplate.getName(), baseplate.getSize(), cityGrid.getSize(), pos.x, pos.y );
        });
    }

    // The method uses client side prediction to counter lag.
    private void moveCurrentUser(UserLocationDto l) {
        viewer.followPlayer(l.getUserId());
        // First : Set the location based on the server respons (server = authoritive
        Vector3f viewerLocation = getViewerLocation(l);
        viewer.setLocation(l.getUserId(), viewerLocation, l.getPitch(), l.getYaw());
        // Remove all the request before this sequence (if any)
        deleteRequestsUpTO(l.getSequence());
        // Apply all the requests that server has not processed yet.
        applyMoveRequests(l);
    }

    private Vector3f getViewerLocation(UserLocationDto l) {
        CityDto city = cityController.getCity(l.getCityId());
        BaseplateDto baseplate = cityController.getBaseplate(l.getBaseplateId());
        SimpleCityGrid.GridPosition pos = city.getCityGrid().getPosition(l.getBaseplateId());
        float viewerX = ((city.getCityGrid().getSize()+1 - pos.x) * baseplate.getSize()) - l.getX();
        float viewerZ = (pos.y * baseplate.getSize()) + l.getZ();
        Vector3f newLocation = new Vector3f( viewerX , 0, viewerZ);
        System.out.println(getClass().getName() + ": New viewer location: Baseplate "+baseplate.getName()+":" +newLocation.x + ","  +newLocation.y+","+newLocation.z);
        return newLocation;
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
        Vector3f viewerLocation = getViewerLocation(newLocation);
        viewer.setLocation(newLocation.getUserId(), viewerLocation, newLocation.getPitch(), newLocation.getYaw());
        System.out.println(this.getClass().getName() + "-Scheduled-"+dto.getSequence()+"-"+newLocation.getX()+","+newLocation.getY()+","+newLocation.getZ()+","+newLocation.getPitch()+"," +newLocation.getYaw());
        return newLocation;
    }

    @Override
    public void teleported(UserLocationDto location) {
        UserDto currentUser = userController.getCurrentUser();
        UserLocationDto currentUserLocation = userController.getUserLocation(currentUser.getId());
        UserDto user = userController.getUser(location.getUserId());
        if ( location.getUserId().equals(currentUser)) {
            viewer.resetView();
            CityDto city = cityController.getCity(location.getCityId());
            setCityGrid(city.getCityGrid());
        }
        if ( location.getCityId().equals(currentUserLocation.getCityId())) {
            userMoved(user, location);
        } else {
            // A user teleported to another city
            // RemovePlayer (if present)
            viewer.removePlayer(user.getId());
        }
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
        lastreceivedLocation = null;
        sequenceNumber = 0;
    }

    @Override
    public void userConnected(UserDto user) {
        // No actions in here
    }

    @Override
    public void userDisconnected(UserDto user) {
        viewer.removePlayer(user.getId());
    }

    @Override
    public void loginResult(boolean success, String message) {
        if (success) {
            UserDto user = userController.getCurrentUser();
            viewer.addPlayer(user.getId(), user.getName());
        }
    }
}
