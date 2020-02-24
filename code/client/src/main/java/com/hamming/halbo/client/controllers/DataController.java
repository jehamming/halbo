package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.ICityWindow;
import com.hamming.halbo.client.interfaces.IConnectionListener;
import com.hamming.halbo.client.interfaces.IContinentWindow;
import com.hamming.halbo.client.interfaces.IWorldWindow;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.CityDto;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.net.CommandReceiver;

import java.util.ArrayList;
import java.util.List;

public class DataController implements IConnectionListener, CommandReceiver {

    private IWorldWindow worldWindow;
    private IContinentWindow continentWindow;
    private ICityWindow cityWindow;
    private ProtocolHandler protocolHandler;
    private ConnectionController connectionController;
    private List<WorldDto> worlds;
    private List<ContinentDto> continents;
    private List<CityDto> cities;

    public DataController(ConnectionController connectionController) {
        this.connectionController = connectionController;
        connectionController.addConnectionListener(this);
        protocolHandler = new ProtocolHandler();
        worlds = new ArrayList<WorldDto>();
        continents = new ArrayList<ContinentDto>();
        cities = new ArrayList<CityDto>();
    }

    public void worldSelected(WorldDto world) {
        String s = protocolHandler.getGetContinentsCommand(world.getId());
        connectionController.send(s);
    }

    public void continentSelected(ContinentDto continent) {
        String s = protocolHandler.getGetCitiesCommand(continent.getId());
        connectionController.send(s);
    }

    @Override
    public void connected() {
        connectionController.registerReceiver(Protocol.Command.GETWORLDS, this);
        connectionController.registerReceiver(Protocol.Command.GETCONTINENTS, this);
        connectionController.registerReceiver(Protocol.Command.GETCITIES, this);
    }

    public void citySelected(CityDto dto) {
        String s = protocolHandler.getGetBaseplatesCommand(dto.getId());
        connectionController.send(s);

    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        switch (cmd) {
            case GETWORLDS:
                handleGetWorlds(data);
                break;
            case GETCONTINENTS:
                handleGetContinents(data);
                break;
            case GETCITIES:
                handleGetCities(data);
                break;
        }
    }

    private void handleGetCities(String[] data) {
        CityDto dto = new CityDto();
        dto.setValues(data);
        cities.add(dto);
        cityWindow.addCity(dto);
    }

    private void handleGetContinents(String[] data) {
        ContinentDto dto = new ContinentDto();
        dto.setValues(data);
        continents.add(dto);
        continentWindow.addContinent(dto);
    }

    private void handleGetWorlds(String[] data) {
        WorldDto dto = new WorldDto();
        dto.setValues(data);
        worlds.add(dto);
        worldWindow.addWorld(dto);
    }


    public IWorldWindow getWorldWindow() {
        return worldWindow;
    }

    public void setWorldWindow(IWorldWindow worldWindow) {
        this.worldWindow = worldWindow;
    }

    public IContinentWindow getContinentWindow() {
        return continentWindow;
    }

    public void setContinentWindow(IContinentWindow continentWindow) {
        this.continentWindow = continentWindow;
    }

    public ICityWindow getCityWindow() {
        return cityWindow;
    }

    public void setCityWindow(ICityWindow cityWindow) {
        this.cityWindow = cityWindow;
    }
}
