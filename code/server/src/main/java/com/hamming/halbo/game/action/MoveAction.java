package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.game.GameConstants;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.GameState;
import com.hamming.halbo.game.GameStateEvent;
import com.hamming.halbo.model.Baseplate;
import com.hamming.halbo.model.CityGrid;
import com.hamming.halbo.model.User;
import com.hamming.halbo.model.UserLocation;

public class MoveAction implements Action {
    private GameController controller;
    private ClientConnection client;
    private long sequence;
    private boolean forward = false;
    private boolean back = false;
    private boolean left = false;
    private boolean right = false;
    private boolean buildMode = false;


    public MoveAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        User u = client.getUser();
        handleMoveRequest(sequence, u, forward, back, left, right, buildMode);
    }

    public void handleMoveRequest(Long sequence, User u, boolean forward, boolean back, boolean left, boolean right, boolean buildMode) {
        UserLocation location = controller.getGameState().getLocation(u);
        if (location != null) {
            float currentSpeed = 0;
            if (forward) {
                currentSpeed = GameConstants.RUN_SPEED;
            } else if (back) {
                currentSpeed = -GameConstants.RUN_SPEED;
            }
            float currentTurnSpeed = 0;
            if (right) {
                currentTurnSpeed = -GameConstants.TURN_SPEED;
            } else if (left) {
                currentTurnSpeed = GameConstants.TURN_SPEED;
            }

            location = calculateNewPosition(location, currentSpeed, currentTurnSpeed);

            checkBaseplateBounds(location, buildMode);

            location.setSequence(sequence);

            controller.setLocation(u,location);
        }
    }

    private void checkBaseplateBounds(UserLocation l, boolean buildMode) {
        Baseplate b = l.getBaseplate();
        if ( l.getX() > b.getSize()) {
            // Switch to baseplate to the right(EAST)
            Baseplate eastBaseplate = l.getCity().getCityGrid().getBaseplate(l.getBaseplate(), CityGrid.Direction.EAST);
            if (eastBaseplate != null ) {
                float x = l.getX() - l.getBaseplate().getSize();
                l.setBaseplate(eastBaseplate);
                l.setX(x);
            } else if (buildMode) {
                eastBaseplate = controller.createBaseplate("AUTOBUILD",l,CityGrid.Direction.EAST);
                float x = l.getX() - l.getBaseplate().getSize();
                l.setBaseplate(eastBaseplate);
                l.setX(x);
            } else {
                l.setX(b.getSize());
            }
        }
        if ( l.getX() < 0 ) {
            // Switch to baseplate to the left(WEST)
            Baseplate westBaseplate = l.getCity().getCityGrid().getBaseplate(l.getBaseplate(), CityGrid.Direction.WEST);
            if (westBaseplate != null ) {
                float x = westBaseplate.getSize() + l.getX();
                l.setBaseplate(westBaseplate);
                l.setX(x);
            } else if (buildMode) {
                westBaseplate = controller.createBaseplate("AUTOBUILD",l,CityGrid.Direction.WEST);
                float x = westBaseplate.getSize() + l.getX();
                l.setBaseplate(westBaseplate);
                l.setX(x);
            } else {
                l.setX(0);
            }
        }
        if ( l.getZ() > b.getSize()) {
            // Switch to baseplate to the top(NORTH)
            Baseplate northBaseplate = l.getCity().getCityGrid().getBaseplate(l.getBaseplate(), CityGrid.Direction.NORTH);
            if (northBaseplate != null ) {
                float z = l.getZ() - l.getBaseplate().getSize();
                l.setBaseplate(northBaseplate);
                l.setZ(z);
            } else if (buildMode) {
                northBaseplate = controller.createBaseplate("AUTOBUILD",l,CityGrid.Direction.NORTH);
                float z = l.getZ() - l.getBaseplate().getSize();
                l.setBaseplate(northBaseplate);
                l.setZ(z);
            } else {
                l.setZ(b.getSize());
            }
        }
        if ( l.getZ() < 0 ) {
            // Switch to baseplate to the bottom(SOUTH)
            Baseplate southBaseplate = l.getCity().getCityGrid().getBaseplate(l.getBaseplate(), CityGrid.Direction.SOUTH);
            if (southBaseplate != null ) {
                float z = southBaseplate.getSize() + l.getZ();
                l.setBaseplate(southBaseplate);
                l.setZ(z);
            } else if (buildMode) {
                southBaseplate = controller.createBaseplate("AUTOBUILD",l,CityGrid.Direction.SOUTH);
                float z = southBaseplate.getSize() + l.getZ();
                l.setBaseplate(southBaseplate);
                l.setZ(z);
            } else {
                l.setZ(0);
            }
        }
    }


    private UserLocation calculateNewPosition(UserLocation location, float currentSpeed, float currentTurnSpeed) {
        // Calculate new position
        location.setYaw(location.getYaw() + currentTurnSpeed);
        float distance = currentSpeed;
        float dx = (float) (distance * Math.sin(Math.toRadians(location.getYaw() + currentTurnSpeed)));
        float dz = (float) (distance * Math.cos(Math.toRadians(location.getYaw() + currentTurnSpeed)));
        increasePosition(location, -dx, 0, dz);
        return location;
    }

    public void increasePosition(UserLocation l, float dx, float dy, float dz) {
        l.setX(l.getX() + dx );
        l.setY(l.getY() + dy );
        l.setZ(l.getZ() + dz );
    }


    @Override
    public void setValues(String... values) {
        if (values.length == 6 ) {
            sequence = Long.valueOf(values[0]);
            forward = Boolean.valueOf(values[1]);
            back = Boolean.valueOf(values[2]);
            left = Boolean.valueOf(values[3]);
            right = Boolean.valueOf(values[4]);
            buildMode = Boolean.valueOf(values[5]);
        } else {
            System.out.println(this.getClass().getName() + ":" + "Error at "+getClass().getName()+", size not ok of: "+values);
        }
    }
}
