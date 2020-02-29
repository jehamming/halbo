package com.hamming.halbo.model.dto;

import com.hamming.halbo.util.StringUtils;

public class UserLocationDto implements DTO {

    private String userId;
    private String worldId;
    private String continentId;
    private String cityId;
    private String baseplateId;
    private double x;
    private double y;
    private double z;
    private double viewingAngle;

    public UserLocationDto(){

    }

    public UserLocationDto(String userId, String worldId, String continentId, String cityId, String baseplateId, double x, double y, double z, double angle){
        this.userId = userId;
        this.worldId = worldId;
        this.continentId = continentId;
        this.cityId = cityId;
        this.baseplateId = baseplateId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.viewingAngle = angle;
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
                + viewingAngle;;
        return data;
    }

    @Override
    public void setValues(String... values) {
        if ( values.length == 9) {
            userId = values[0];
            worldId = values[1];
            continentId = values[2];
            cityId = values[3];
            baseplateId = values[4];
            x = Double.valueOf(values[5]);
            y = Double.valueOf(values[6]);
            z = Double.valueOf(values[7]);
            viewingAngle = Double.valueOf(values[8]);
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getViewingAngle() {
        return viewingAngle;
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
                ", viewingAngle=" + viewingAngle+
                '}';
    }
}
