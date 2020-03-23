package com.hamming.halbo.client.controllers;

import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.model.dto.CityBaseplateDto;
import com.hamming.halbo.model.dto.CityDto;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.net.CommandReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityController implements CommandReceiver {

    private ProtocolHandler protocolHandler;
    private List<BaseplateDto> baseplates;
    private Map<String, List<CityDto>> cities;

    public CityController(ConnectionController connectionController) {
        protocolHandler = new ProtocolHandler();
        baseplates = new ArrayList<BaseplateDto>();
        cities = new HashMap<>();
        connectionController.registerReceiver(Protocol.Command.GETCITIES, this);
        connectionController.registerReceiver(Protocol.Command.GETCITYDETAILS, this);
        connectionController.registerReceiver(Protocol.Command.CITYBASEPLATE, this);
        connectionController.registerReceiver(Protocol.Command.GETBASEPLATE, this);
    }

    private void handleGetCities(String[] data) {
        CityDto dto = new CityDto();
        dto.setValues(data);
        addCity(dto);
    }

    public void addCity(CityDto city) {
        List<CityDto> citiesForContinent = cities.get(city.getContinentID());
        if (citiesForContinent == null ) {
            citiesForContinent = new ArrayList<CityDto>();
        }
        citiesForContinent.add(city);
        cities.put(city.getContinentID(), citiesForContinent);
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
            case GETBASEPLATE:
                handleGetBaseplates(data);
                break;
            case CITYBASEPLATE:
                handleCityBaseplate(data);
                break;
        }
    }

    private void handleCityBaseplate(String[] data) {
        CityBaseplateDto dto = new CityBaseplateDto();
        dto.setValues(data);
        CityDto city = getCity(dto.getCityId());
        if ( city != null ) {
            city.getCityGrid().setBaseplate(dto.getBaseplateId(), dto.getRow(), dto.getCol());
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

    public CityDto getCity( String cityId ) {
        CityDto found = null;
        for (List<CityDto> citiesForContinent : cities.values()) {
            for (CityDto city: citiesForContinent) {
                if ( city.getId().toString().equals(cityId)) {
                    found = city;
                    break;
                }
            }
        }
        return found;
    }

    public List<CityDto> getCities(ContinentDto c) {
        return cities.get(c.getId());
    }

    public void reset() {
        cities = new HashMap<>();
    }

}
