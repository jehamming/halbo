package com.hamming.halbo.model.dto;

import com.hamming.halbo.util.StringUtils;

public class ContinentDto implements DTO {

    private String id;
    private String name;
    private String creatorID;
    private String ownerID;
    private String senatorID;
    private String worldID;

    public ContinentDto(){

    }

    public ContinentDto(String worldID, String id, String name, String creatorID, String ownerID, String senatorID){
        this.id = id;
        this.name = name;
        this.creatorID = creatorID;
        this.ownerID = ownerID;
        this.senatorID = senatorID;
        this.worldID = worldID;
    }

    @Override
    public String toNetData() {
        String data = worldID + StringUtils.delimiter
                + id + StringUtils.delimiter
                + name + StringUtils.delimiter
                + creatorID + StringUtils.delimiter
                + ownerID + StringUtils.delimiter
                + senatorID;
        return data;
    }

    @Override
    public void setValues(String... values) {
        if ( values.length == 6) {
            worldID = values[0];
            id = values[1];
            name = values[2];
            creatorID = values[3];
            ownerID = values[4];
            senatorID = values[5];
        }
    }

    public String getSenatorID() {
        return senatorID;
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

    public String getWorldID() {
        return worldID;
    }

    @Override
    public String toString() {
        return name;
    }
}
