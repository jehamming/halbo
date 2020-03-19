package com.hamming.halbo.client;

import com.hamming.halbo.model.dto.MovementDto;
import com.hamming.halbo.model.dto.UserLocationDto;
import org.lwjgl.util.vector.Vector3f;

public class CalcTools {

    private static final float RUN_SPEED = 1;
    private static final float TURN_SPEED = 7;


    public static UserLocationDto calculateNewPosition(MovementDto request, UserLocationDto location) {
        float currentSpeed = 0;
        if (request.isForward()) {
            currentSpeed = RUN_SPEED;
        } else if (request.isBack()) {
            currentSpeed = -RUN_SPEED;
        }
        float currentTurnSpeed = 0;
        if (request.isRight()) {
            currentTurnSpeed = -TURN_SPEED;
        } else if (request.isLeft()) {
            currentTurnSpeed = TURN_SPEED;
        }

        location = calculateNewPosition(location, currentSpeed, currentTurnSpeed);

        return location;
    }

    public static UserLocationDto calculateNewPosition(UserLocationDto location, float currentSpeed, float currentTurnSpeed) {
        // Calculate new position
        location.setYaw(location.getYaw() + currentTurnSpeed);
        float distance = currentSpeed;
        float dx = (float) (distance * Math.sin(Math.toRadians(location.getYaw() + currentTurnSpeed)));
        float dz = (float) (distance * Math.cos(Math.toRadians(location.getYaw() + currentTurnSpeed)));
        increasePosition(location, dx, 0, dz);
        return location;
    }

    public static void increasePosition(UserLocationDto l, float dx, float dy, float dz) {
        l.setX( l.getX() + dx );
        l.setY(l.getY() +  dy );
        l.setZ(l.getZ() + dz );
    }



}
