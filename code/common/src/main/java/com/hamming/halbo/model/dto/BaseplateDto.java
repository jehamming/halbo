package com.hamming.halbo.model.dto;

import com.hamming.halbo.util.StringUtils;

public class BaseplateDto implements DTO {

    private String id;
    private String name;
    private String creatorID;
    private String ownerID;
    private int width;
    private int length;

    public BaseplateDto(){

    }

    public BaseplateDto(String id, String name, String creatorID, String ownerID, int width, int length){
        this.id = id;
        this.name = name;
        this.creatorID = creatorID;
        this.ownerID = ownerID;
        this.width = width;
        this.length = length;
    }

    @Override
    public String toNetData() {
        String data = id + StringUtils.delimiter
                + name + StringUtils.delimiter
                + creatorID + StringUtils.delimiter
                + ownerID + StringUtils.delimiter
                + width + StringUtils.delimiter
                + length;
        return data;
    }

    @Override
    public void setValues(String... values) {
        if ( values.length == 6) {
            id = values[0];
            name = values[1];
            creatorID = values[2];
            ownerID = values[3];
            width = Integer.valueOf(values[4]);
            length = Integer.valueOf(values[5]);
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

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return name;
    }
}
