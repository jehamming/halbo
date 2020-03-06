package com.hamming.halbo.client.engine.actions;

import com.hamming.halbo.client.engine.GLViewer;
import com.hamming.halbo.client.engine.entities.Player;
import org.lwjgl.util.vector.Vector3f;

public class SetUserLocationAction implements Action {

    private GLViewer viewer;
    private float x,y,z,pitch,yaw;
    private String userId, name;

    public SetUserLocationAction(GLViewer viewer, String userId, String name,  float x, float y, float z, float pitch, float yaw) {
        this.viewer = viewer;
        this.userId = userId;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    @Override
    public void execute() {
        Player p = findOrCreatePlayer();
        p.setPosition(x,y,z);
        p.setPitch(pitch);
        p.setYaw(yaw);
    }

    private Player findOrCreatePlayer() {
        Player player = null;
        for ( Player p: viewer.getPlayers()) {
            if ( p.getUserId().equals(userId)) {
                player = p;
                break;
            }
        }
        if (player == null) {
            player = new Player(viewer.getLoader(), userId, name);
            viewer.getPlayers().add(player);
        }
        return player;
    }


}
