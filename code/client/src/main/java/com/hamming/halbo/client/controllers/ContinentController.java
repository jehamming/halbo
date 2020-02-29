package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.ContinentListener;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.net.CommandReceiver;

import java.util.ArrayList;
import java.util.List;

public class ContinentController implements CommandReceiver {

    private ProtocolHandler protocolHandler;
    private ConnectionController connectionController;
    private List<ContinentListener> continentListeners;
    private ContinentDto selectedContinent;

    public ContinentController(ConnectionController connectionController) {
        this.connectionController = connectionController;
        protocolHandler = new ProtocolHandler();
        continentListeners = new ArrayList<ContinentListener>();
        connectionController.registerReceiver(Protocol.Command.GETCONTINENTS, this);
    }

    private void handleGetContinents(String[] data) {
        ContinentDto dto = new ContinentDto();
        dto.setValues(data);
        continentAdded(dto);
    }

    public void continentAdded(ContinentDto dto) {
        for (ContinentListener l : continentListeners) {
            l.continentAdded(dto);
        }
    }

    public void addContinentListener(ContinentListener l) {
        continentListeners.add(l);
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        switch (cmd) {
            case GETCONTINENTS:
                handleGetContinents(data);
                break;
        }
    }

    public void continentSelected(ContinentDto c) {
        selectedContinent = c;
        connectionController.send(protocolHandler.getGetCitiesCommand(c.getId()));
    }

    public ContinentDto getSelectedContinent() {
        return selectedContinent;
    }
}
