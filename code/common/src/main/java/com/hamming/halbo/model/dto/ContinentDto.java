package com.hamming.halbo.model.dto;

import com.hamming.halbo.util.StringUtils;

public class ContinentDto implements DTO {

    private String id;
    private String name;
    private String creatorID;
    private String ownerID;

    private String senatorID;

    public ContinentDto(){

    }

    public ContinentDto(String id, String name, String creatorID, String ownerID, String senatorID){
        this.id = id;
        this.name = name;
        this.creatorID = creatorID;
        this.ownerID = ownerID;
        this.senatorID = senatorID;
    }

    @Override
    public String toNetData() {
        String data = id + StringUtils.delimiter
                + name + StringUtils.delimiter
                + creatorID + StringUtils.delimiter
                + ownerID + StringUtils.delimiter
                + senatorID;
        return data;
    }

    @Override
    public void setValues(String... values) {
        if ( values.length == 5) {
            id = values[0];
            name = values[1];
            creatorID = values[2];
            ownerID = values[3];
            senatorID = values[4];
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

    @Override
    public String toString() {
        return name;
    }
}
