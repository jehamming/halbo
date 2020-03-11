package com.hamming.halbo.client;

import com.hamming.halbo.model.dto.MovementDto;
import com.hamming.halbo.model.dto.UserLocationDto;
import org.lwjgl.util.vector.Vector3f;

public class CalcTools {

    public static UserLocationDto calculateNewPosition(MovementDto request, UserLocationDto currentPosition) {
        float speed = 0.12f;
        float x = currentPosition.getX();
        float y = currentPosition.getY();
        float z = currentPosition.getZ();
        if (request.isForward()) z = z - speed;
        if (request.isBack()) z = z  + speed;
        if (request.isLeft()) x = x  - speed;
        if (request.isRight()) x = x  + speed;
        // View Pitch / Yaw in degrees
        float newYaw = CalcTools.calculateNewYaw(request, currentPosition.getYaw());
        float newPitch = CalcTools.calculateNewPitch(request, currentPosition.getPitch());
        currentPosition.setX(x);
        currentPosition.setY(y);
        currentPosition.setZ(z);
        currentPosition.setPitch(newPitch);
        currentPosition.setYaw(newYaw);
        return currentPosition;
    }

    public static float calculateNewYaw(MovementDto request, float currentYaw) {
        float speed = 10.f;
        return normalize(currentYaw + (request.getYaw() * (1.f / speed)));
    }

    public static float calculateNewPitch(MovementDto request, float currentPitch) {
        float speed = 10.f;
        return normalize(currentPitch - (request.getPitch() * (1.f / speed)));
    }

    public static float normalize(float angle) {
        //Make angle between 0 and 360
        angle %= 360;
        //Make angle between -179 and 180
        if (angle > 180) angle -= 360;
        return angle;
    }

}
