package com.wijlen.ter.halbo.lwjgl.entities;

import com.wijlen.ter.halbo.lwjgl.models.TexturedModel;
import com.wijlen.ter.halbo.lwjgl.terrains.HeightTerrain;
import com.wijlen.ter.halbo.lwjgl.terrains.Terrain;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import com.wijlen.ter.halbo.lwjgl.renderEngine.DisplayManager;

public class Player extends Entity {

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;

    private String userId;
    private String name;

    private float pitch, yaw;

    public Player(String userId, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.userId = userId;
        this.name = name;
    }

    public Player(String userId, TexturedModel model) {
        this(userId, model, new Vector3f(100, 0, -50), 0, 0, 0, 0.5f);
    }

    public void move(Terrain terrain) {
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        if (super.getPosition().y < terrainHeight) {
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = terrainHeight;
        }
    }

    private void jump() {
        if (!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    private void checkInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.currentSpeed = RUN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.currentSpeed = -RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jump();
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        setRotY(yaw);
    }
}
