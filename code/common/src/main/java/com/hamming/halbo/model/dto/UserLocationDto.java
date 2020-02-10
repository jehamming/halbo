package com.hamming.halbo.model.dto;

import com.hamming.halbo.util.StringUtils;

public class UserLocationDto implements DTO {

    private String userId;
    private String worldId;
    private String continentId;
    private String cityId;
    private String baseplateId;
    private int x;
    private int y;
    private int z;

    public UserLocationDto(){

    }

    public UserLocationDto(String userId, String worldId, String continentId, String cityId, String baseplateId, int x, int y, int z){
        this.userId = userId;
        this.worldId = worldId;
        this.continentId = continentId;
        this.cityId = cityId;
        this.baseplateId = baseplateId;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toNetData() {
        String data = userId + StringUtils.delimiter
                + worldId + StringUtils.delimiter
                + continentId + StringUtils.delimiter
                + cityId + StringUtils.delimiter
                + baseplateId + StringUtils.delimiter
                + x + StringUtils.delimiter
                + y + StringUtils.delimiter
                + z;
        return data;
    }

    @Override
    public void setValues(String... values) {
        if ( values.length == 8) {
            userId = values[0];
            worldId = values[1];
            continentId = values[2];
            cityId = values[3];
            baseplateId = values[4];
            x = Integer.valueOf(values[5]);
            y = Integer.valueOf(values[6]);
            z = Integer.valueOf(values[7]);
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getWorldId() {
        return worldId;
    }

    public String getContinentId() {
        return continentId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getBaseplateId() {
        return baseplateId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "UserLocationDto{" +
                "userId='" + userId + '\'' +
                ", worldId='" + worldId + '\'' +
                ", continentId='" + continentId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", baseplateId='" + baseplateId + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
