package com.hamming.halbo.model.dto;

import com.hamming.halbo.util.StringUtils;

public class UserLocationDto implements DTO {

    private String userId;
    private String worldId;
    private String continentId;
    private String cityId;
    private String baseplateId;
    private float x,y,z,pitch,yaw;

    public UserLocationDto(){

    }

    public UserLocationDto(String userId, String worldId, String continentId, String cityId, String baseplateId, float x, float y, float z, float pitch, float yaw){
        this.userId = userId;
        this.worldId = worldId;
        this.continentId = continentId;
        this.cityId = cityId;
        this.baseplateId = baseplateId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
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
                + z + StringUtils.delimiter
                + pitch + StringUtils.delimiter
                + yaw;
        return data;
    }

    @Override
    public void setValues(String... values) {
        if ( values.length == 10) {
            userId = values[0];
            worldId = values[1];
            continentId = values[2];
            cityId = values[3];
            baseplateId = values[4];
            x = Float.valueOf(values[5]);
            y = Float.valueOf(values[6]);
            z = Float.valueOf(values[7]);
            pitch = Float.valueOf(values[8]);
            yaw = Float.valueOf(values[9]);
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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
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
                ", pitch=" + pitch+
                ", yaw=" + yaw+
                '}';
    }
}
