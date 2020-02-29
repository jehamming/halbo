package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.WorldListener;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.net.CommandReceiver;

import java.util.ArrayList;
import java.util.List;

public class WorldController implements CommandReceiver {

    private ProtocolHandler protocolHandler;
    private ConnectionController connectionController;
    private List<WorldListener> worldListeners;
    private WorldDto selectedWorld;

    public WorldController(ConnectionController connectionController) {
        this.connectionController = connectionController;
        protocolHandler = new ProtocolHandler();
        worldListeners = new ArrayList<WorldListener>();
        connectionController.registerReceiver(Protocol.Command.GETWORLDS, this);
    }


    private void handleGetWorlds(String[] data) {
        WorldDto dto = new WorldDto();
        dto.setValues(data);
        worldAdded(dto);
    }

    public void worldAdded(WorldDto dto) {
        for (WorldListener l: worldListeners) {
            l.worldAdded(dto);
        }
    }

    public void addWorldListener(WorldListener l) {
        worldListeners.add(l);
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        switch (cmd) {
            case GETWORLDS:
                handleGetWorlds(data);
                break;
        }
    }

    public void worldSelected(WorldDto world) {
        selectedWorld = world;
        connectionController.send(protocolHandler.getGetContinentsCommand(world.getId()));
    }

    public WorldDto getSelectedWorld() {
        return selectedWorld;
    }
}
