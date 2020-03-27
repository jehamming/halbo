package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.Controllers;
import com.hamming.halbo.client.HALBOClientApplication;
import com.hamming.halbo.client.interfaces.ConnectionListener;
import com.hamming.halbo.client.interfaces.UserListener;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.CityDto;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.model.dto.WorldDto;

import javax.swing.*;

public class HALBOClientController implements UserListener, ConnectionListener {
    private Controllers controllers;
    private HALBOClientApplication application;
    private ProtocolHandler protocolHandler;
    private WorldDto selectedWorld;
    private ContinentDto selectedContinent;
    private CityDto selectedCity;

    public HALBOClientController(HALBOClientApplication application, Controllers controllers) {
        this.controllers = controllers;
        this.application = application;
        this.protocolHandler = new ProtocolHandler();
        this.controllers.getUserController().addUserListener(this);
        this.controllers.getConnectionController().addConnectionListener(this);
    }

    public void worldSelected(WorldDto world) {
        selectedWorld = world;
        // Empty the panels
        application.getBaseWindow().getContinentsPanel().empty();
        application.getBaseWindow().getCitiesPanel().empty();
        // Add the continents
        application.getBaseWindow().getContinentsPanel().addContinents(controllers.getContinentController().getContinents(world));
    }


    public void continentSelected(ContinentDto c) {
        selectedContinent = c;
        // Empty the Cities panel
        application.getBaseWindow().getCitiesPanel().empty();
        // Add the cities
        application.getBaseWindow().getCitiesPanel().addCities(controllers.getCityController().getCities(c));
    }


    public void citySelected(CityDto dto) {
        selectedCity = dto;
    }


    public void teleport() {
        UserDto user = controllers.getUserController().getCurrentUser();
        String result = controllers.getMoveController().teleportRequest(user, selectedWorld, selectedContinent, selectedCity);
        if (result != null ) {
            JOptionPane.showMessageDialog(application.getBaseWindow(), result);
        }
    }




    public WorldDto getSelectedWorld() {
        return selectedWorld;
    }
    public ContinentDto getSelectedContinent() {
        return selectedContinent;
    }
    public CityDto getSelectedCity() {
        return selectedCity;
    }


    @Override
    public void userConnected(UserDto user) {
        //TODO not used
    }

    @Override
    public void userDisconnected(UserDto user) {
        //TODO Not used
    }

    @Override
    public void loginResult(boolean success, String message) {
        if (success) {
            application.getBaseWindow().getWorldsPanel().addWorlds(controllers.getWorldController().getWorlds());
        } else {
            JOptionPane.showMessageDialog(application.getBaseWindow(), message);
            application.getBaseWindow().getWorldsPanel().empty();
        }
    }

    @Override
    public void connected() {
    }

    @Override
    public void disconnected() {
        application.getBaseWindow().emptyPanels();
        controllers.getWorldController().reset();
        controllers.getContinentController().reset();
        controllers.getCityController().reset();
    }

    public void worldAdded(WorldDto world) {
        application.getBaseWindow().getWorldsPanel().addWorld(world);
    }
}
