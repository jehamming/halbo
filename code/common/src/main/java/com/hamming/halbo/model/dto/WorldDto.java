package com.hamming.halbo.model.dto;

import com.hamming.halbo.util.StringUtils;

public class WorldDto implements DTO {

    private String id;
    private String name;
    private String creatorID;
    private String ownerID;

    public WorldDto(){

    }

    public WorldDto(String id, String name, String creatorID, String ownerID){
        this.id = id;
        this.name = name;
        this.creatorID = creatorID;
        this.ownerID = ownerID;
    }

    @Override
    public String toNetData() {
        String data = id + StringUtils.delimiter
                + name + StringUtils.delimiter
                + creatorID + StringUtils.delimiter
                + ownerID ;
        return data;
    }

    @Override
    public void setValues(String... values) {
        if ( values.length == 4) {
            id = values[0];
            name = values[1];
            creatorID = values[2];
            ownerID = values[3];
        }
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

    @Override
    public String toString() {
        return name;
    }

}
