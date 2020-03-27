package com.hamming.halbo.client.engine.actions;

import com.hamming.halbo.client.engine.GLViewer;
import com.wijlen.ter.halbo.lwjgl.entities.Player;

public class RemovePlayerAction implements Action {

    private GLViewer viewer;
    private String userId;

    public RemovePlayerAction(GLViewer viewer, String userId) {
        this.viewer = viewer;
        this.userId = userId;
    }

    @Override
    public void execute() {
        Player player = viewer.getPlayer(userId);
        viewer.removePlayer(player);
    }

}
