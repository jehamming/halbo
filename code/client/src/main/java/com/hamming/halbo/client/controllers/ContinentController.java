package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.ContinentListener;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.net.CommandReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContinentController implements CommandReceiver {

    private ConnectionController connectionController;
    private Map<String, List<ContinentDto>> continents;

    public ContinentController(ConnectionController connectionController) {
        this.connectionController = connectionController;
        this.continents = new HashMap<>();
        connectionController.registerReceiver(Protocol.Command.GETCONTINENTS, this);
    }

    private void handleGetContinents(String[] data) {
        ContinentDto dto = new ContinentDto();
        dto.setValues(data);
        addContinent(dto);
    }

    public void addContinent(ContinentDto dto) {
        List<ContinentDto> continentsForWorld = continents.get(dto.getWorldID());
        if (continentsForWorld == null ) {
            continentsForWorld = new ArrayList<ContinentDto>();
        }
        continentsForWorld.add(dto);
        continents.put(dto.getWorldID(), continentsForWorld);
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        switch (cmd) {
            case GETCONTINENTS:
                handleGetContinents(data);
                break;
        }
    }


    public List<ContinentDto> getContinents(WorldDto world) {
        return continents.get(world.getId());
    }

    public void reset() {
        this.continents = new HashMap<>();
    }


}
