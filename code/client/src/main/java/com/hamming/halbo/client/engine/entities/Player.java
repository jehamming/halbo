package com.hamming.halbo.client.engine.entities;

import com.hamming.halbo.client.engine.models.RawModel;
import com.hamming.halbo.client.engine.models.TexturedModel;
import com.hamming.halbo.client.engine.renderengine.Loader;
import com.hamming.halbo.client.engine.renderengine.OBJLoader;
import com.hamming.halbo.client.engine.textures.ModelTexture;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity {
    private String userId, name;
    private float pitch, yaw;

    public Player(Loader loader, String userId, String name) {
        this.userId = userId;
        this.name = name;
        RawModel model = OBJLoader.loadObjModel("legopuppet", loader);
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("purple")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        setModel(staticModel);
        setScale(1f);
    }

    public void setPosition(float x, float y, float z) {
        Vector3f newPostion = new Vector3f(x, y, z);
        setPosition(newPostion);
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
    }
}
