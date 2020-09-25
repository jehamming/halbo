package com.hamming.halbo.client.controllers;

import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.net.CommandReceiver;

import java.util.*;

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
        if (continentsForWorld == null) {
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


    public List<ContinentDto> getContinents(String worldId) {
        return continents.get(worldId);
    }

    public void reset() {
        this.continents = new HashMap<>();
    }


    public ContinentDto getContinent(String continentId) {
        ContinentDto found = null;
        Set<String> worldIs = continents.keySet();
        for (String worldId : worldIs) {
            List<ContinentDto> contentsForWorld = getContinents(worldId);
            for (ContinentDto continent : contentsForWorld) {
                if (continent.getId().equals(continentId)) {
                    found = continent;
                    break;
                }
            }
            if (found != null ) break;
        }
        return found;
    }
}
