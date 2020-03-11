package com.hamming.halbo.model.dto;

import com.hamming.halbo.util.StringUtils;

public class UserLocationDto implements DTO {

    private String userId;
    private String worldId;
    private String continentId;
    private String cityId;
    private String baseplateId;
    private long sequence;
    private float x,y,z,pitch,yaw;

    public UserLocationDto(){

    }

    public UserLocationDto(long sequence, String userId, String worldId, String continentId, String cityId, String baseplateId, float x, float y, float z, float pitch, float yaw){
        this.sequence = sequence;
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
        String data = sequence + StringUtils.delimiter
                + userId + StringUtils.delimiter
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
        if ( values.length == 11) {
            sequence = Long.valueOf(values[0]);
            userId = values[1];
            worldId = values[2];
            continentId = values[3];
            cityId = values[4];
            baseplateId = values[5];
            x = Float.valueOf(values[6]);
            y = Float.valueOf(values[7]);
            z = Float.valueOf(values[8]);
            pitch = Float.valueOf(values[9]);
            yaw = Float.valueOf(values[10]);
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public long getSequence() {
        return sequence;
    }

    @Override
    public String toString() {
        return "UserLocationDto{" +
                "sequence ='" + sequence + '\'' +
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
