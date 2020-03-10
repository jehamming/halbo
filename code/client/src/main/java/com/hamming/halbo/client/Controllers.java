package com.hamming.halbo.client;

import com.hamming.halbo.client.controllers.*;

public class Controllers {

    private CityController cityController;
    private ConnectionController connectionController;
    private ContinentController continentController;
    private MoveController moveController;
    private UserController userController;
    private WorldController worldController;
    private HALBOClientController halboClientController;
    private ViewController viewController;


    public CityController getCityController() {
        return cityController;
    }

    public void setCityController(CityController cityController) {
        this.cityController = cityController;
    }

    public ConnectionController getConnectionController() {
        return connectionController;
    }

    public void setConnectionController(ConnectionController connectionController) {
        this.connectionController = connectionController;
    }

    public ContinentController getContinentController() {
        return continentController;
    }

    public void setContinentController(ContinentController continentController) {
        this.continentController = continentController;
    }

    public MoveController getMoveController() {
        return moveController;
    }

    public void setMoveController(MoveController moveController) {
        this.moveController = moveController;
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public WorldController getWorldController() {
        return worldController;
    }

    public void setWorldController(WorldController worldController) {
        this.worldController = worldController;
    }

    public HALBOClientController getHalboClientController() {
        return halboClientController;
    }

    public void setHalboClientController(HALBOClientController halboClientController) {
        this.halboClientController = halboClientController;
    }

    public ViewController getViewController() {
        return viewController;
    }

    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }
}
