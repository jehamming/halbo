package com.hamming.halbo.client.engine.actions;

import com.hamming.halbo.client.engine.GLViewer;
import com.wijlen.ter.halbo.lwjgl.entities.Player;
import org.lwjgl.util.vector.Vector3f;

public class SetUserLocationAction implements Action {

    private GLViewer viewer;
    private float x,y,z,pitch,yaw;
    private String userId;

    public SetUserLocationAction(GLViewer viewer, String userId, float x, float y, float z, float pitch, float yaw) {
        this.viewer = viewer;
        this.userId = userId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    @Override
    public void execute() {
        Player p = findOrCreatePlayer();
        Vector3f position = new Vector3f(x,y,z);
        p.setPosition(position);
        if (p.getUserId().equals(viewer.getFollowedUserId())) {
            viewer.followPlayer(p);
        }
        //TODO Pitch an Yaw?
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
            player = new Player(userId, viewer.getBasicPlayerTexture());
            viewer.getPlayers().add(player);
        }
        return player;
    }


}
