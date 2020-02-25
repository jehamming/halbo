package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.ICityListener;
import com.hamming.halbo.client.interfaces.IContinentListener;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.CityDto;
import com.hamming.halbo.net.CommandReceiver;

import java.util.ArrayList;
import java.util.List;

public class CityController implements CommandReceiver {

    private ProtocolHandler protocolHandler;
    private ConnectionController connectionController;
    private List<ICityListener> cityListeners;
    private CityDto selectedCity;

    public CityController(ConnectionController connectionController) {
        this.connectionController = connectionController;
        protocolHandler = new ProtocolHandler();
        cityListeners = new ArrayList<ICityListener>();
        connectionController.registerReceiver(Protocol.Command.GETCITIES, this);
    }

    private void handleGetCities(String[] data) {
        CityDto dto = new CityDto();
        dto.setValues(data);
        cityAdded(dto);
    }

    public void cityAdded(CityDto city) {
        for (ICityListener l : cityListeners) {
            l.cityAdded(city);
        }
    }

    public void addCityListener(ICityListener l) {
        cityListeners.add(l);
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        switch (cmd) {
            case GETCITIES:
                handleGetCities(data);
                break;
            case GETCITYDETAILS:
                System.out.println(getClass().getName() + cmd + ": NOT IMPLEMENTED YET");
                break;
        }
    }

    public void citySelected(CityDto dto) {
        selectedCity = dto;
    }

    public CityDto getSelectedCity() {
        return selectedCity;
    }
}
