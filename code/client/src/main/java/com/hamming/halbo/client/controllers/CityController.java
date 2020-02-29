package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.CityListener;
import com.hamming.halbo.client.interfaces.ConnectionListener;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.model.dto.CityDto;
import com.hamming.halbo.net.CommandReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CityController implements CommandReceiver, ConnectionListener {

    private ProtocolHandler protocolHandler;
    private ConnectionController connectionController;
    private List<CityListener> cityListeners;
    private CityDto selectedCity;
    private List<BaseplateDto> baseplates;
    private List<CityDto> cities;

    public CityController(ConnectionController connectionController) {
        this.connectionController = connectionController;
        protocolHandler = new ProtocolHandler();
        cityListeners = new ArrayList<CityListener>();
        baseplates = new ArrayList<BaseplateDto>();
        cities = new ArrayList<CityDto>();
        connectionController.addConnectionListener(this);
        connectionController.registerReceiver(Protocol.Command.GETCITIES, this);
        connectionController.registerReceiver(Protocol.Command.GETCITYDETAILS, this);
        connectionController.registerReceiver(Protocol.Command.GETBASEPLATES, this);
    }

    private void handleGetCities(String[] data) {
        CityDto dto = new CityDto();
        dto.setValues(data);
        addCity(dto);
    }

    public void addCity(CityDto city) {
        cities.add(city);
        for (CityListener l : cityListeners) {
            l.cityAdded(city);
        }
    }

    public void addCityListener(CityListener l) {
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
            case GETBASEPLATES:
                handleGetBaseplates(data);
                break;
        }
    }

    private void handleGetBaseplates(String[] data) {
        BaseplateDto dto = new BaseplateDto();
        dto.setValues(data);
        addBaseplate(dto);
    }

    private void addBaseplate(BaseplateDto dto) {
        baseplates.add(dto);
    }

    public void citySelected(CityDto dto) {
        selectedCity = dto;
    }

    public CityDto getSelectedCity() {
        return selectedCity;
    }

    public BaseplateDto getBaseplate(String baseplateId) {
        BaseplateDto found = null;
        for (BaseplateDto b : baseplates) {
            if (b.getId().equals(baseplateId)) {
                found = b;
                break;
            }
        }
        return found;
    }

    @Override
    public void connected() {
        baseplates = new ArrayList<BaseplateDto>();
        cities = new ArrayList<CityDto>();
    }

    @Override
    public void disconnected() {

    }
}
