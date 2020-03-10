package com.hamming.halbo.client.controllers;

import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.net.CommandReceiver;

import java.util.ArrayList;
import java.util.List;

;

public class WorldController implements CommandReceiver {

    private ProtocolHandler protocolHandler;
    private ConnectionController connectionController;
    private HALBOClientController halboClientController;
    private List<WorldDto> worlds;

    public WorldController(HALBOClientController halboClientController, ConnectionController connectionController) {
        this.connectionController = connectionController;
        this.halboClientController = halboClientController;
        protocolHandler = new ProtocolHandler();
        worlds = new ArrayList<WorldDto>();
        connectionController.registerReceiver(Protocol.Command.GETWORLDS, this);
    }


    private void handleGetWorlds(String[] data) {
        WorldDto dto = new WorldDto();
        dto.setValues(data);
        addWorld(dto);
    }

    public void addWorld(WorldDto world) {
        worlds.add(world);
        halboClientController.worldAdded(world);
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        switch (cmd) {
            case GETWORLDS:
                handleGetWorlds(data);
                break;
        }
    }


    public List<WorldDto> getWorlds() {
        return worlds;
    }

    public void reset() {
        worlds = new ArrayList<WorldDto>();
    }
}
