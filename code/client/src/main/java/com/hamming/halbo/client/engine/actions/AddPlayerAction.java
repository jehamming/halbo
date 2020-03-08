package com.hamming.halbo.client.engine.actions;

import com.hamming.halbo.client.engine.GLViewer;
import com.hamming.halbo.client.engine.entities.Player;

public class AddPlayerAction implements Action {

    private GLViewer viewer;
    private String userId, name;

    public AddPlayerAction(GLViewer viewer, String userId, String name) {
        this.viewer = viewer;
        this.userId = userId;
        this.name = name;
    }

    @Override
    public void execute() {
        Player player = new Player(viewer.getLoader(), userId, name);
        viewer.getPlayers().add(player);
    }

}
