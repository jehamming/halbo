package com.hamming.halbo.model.dto;

import com.hamming.halbo.util.StringUtils;

public class CityDto implements DTO {

    private String id;
    private String name;
    private String creatorID;
    private String ownerID;
    private String mayorID;
    private String continentID;


    public CityDto(){

    }

    public CityDto(String continentID, String id, String name, String creatorID, String ownerID, String mayorID){
        this.id = id;
        this.name = name;
        this.creatorID = creatorID;
        this.ownerID = ownerID;
        this.mayorID = mayorID;
        this.continentID = continentID;
    }

    @Override
    public String toNetData() {
        String data = continentID + StringUtils.delimiter
                + id + StringUtils.delimiter
                + name + StringUtils.delimiter
                + creatorID + StringUtils.delimiter
                + ownerID + StringUtils.delimiter
                + mayorID;
        return data;
    }

    @Override
    public void setValues(String... values) {
        if ( values.length == 6) {
            continentID = values[0];
            id = values[1];
            name = values[2];
            creatorID = values[3];
            ownerID = values[4];
            mayorID = values[5];
        }
    }

    public String getMayorID() {
        return mayorID;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getContinentID() {
        return continentID;
    }

    @Override
    public String toString() {
        return name;
    }
}
