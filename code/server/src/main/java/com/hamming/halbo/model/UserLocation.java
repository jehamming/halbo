package com.hamming.halbo.model;

public class UserLocation {
    private String worldId;
    private String continentId;
    private String cityId;
    private String baseplateId;
    private int x;
    private int y;


    public UserLocation(String worldId, String continentId, String cityId, String baseplateId, int x, int y){
        this.worldId = worldId;
        this.continentId = continentId;
        this.cityId = cityId;
        this.baseplateId = baseplateId;
        this.x = x;
        this.y = y;
    }

    public String getWorldId() {
        return worldId;
    }

    public void setWorldId(String worldId) {
        this.worldId = worldId;
    }

    public String getContinentId() {
        return continentId;
    }

    public void setContinentId(String continentId) {
        this.continentId = continentId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getBaseplateId() {
        return baseplateId;
    }

    public void setBaseplateId(String baseplateId) {
        this.baseplateId = baseplateId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
