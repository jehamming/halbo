package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.game.GameController;

public class TeleportAction implements Action {
    private GameController controller;
    private ClientConnection client;

    private String userId;
    private String worldId;
    private String continentId;
    private String cityId;
    private String baseplateId;


    public TeleportAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        controller.handleTeleportRequest(userId, worldId, continentId, cityId, baseplateId);
    }

    @Override
    public void setValues(String... values) {
        if (values.length == 5 ) {
            userId = values[0];
            worldId = values[1];
            continentId = values[2];
            cityId = values[3];
            baseplateId = values[4];
        } else {
            System.out.println("Error at "+getClass().getName()+", size not ok of: "+values);
        }
    }
}
