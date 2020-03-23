package com.hamming.halbo.model.dto;

import com.hamming.halbo.util.StringUtils;

public class CityBaseplateDto implements DTO {

    private String cityId;
    private String baseplateId;
    private int row, col;

    public CityBaseplateDto(){

    }

    public CityBaseplateDto(String cityId, String baseplateId, int row, int col){
        this.baseplateId = baseplateId;
        this.cityId = cityId;
        this.row = row;
        this.col = col;
    }

    @Override
    public String toNetData() {
        String data = cityId + StringUtils.delimiter
                + baseplateId + StringUtils.delimiter
                + row + StringUtils.delimiter
                + col + StringUtils.delimiter;
        return data;
    }

    @Override
    public void setValues(String... values) {
        if ( values.length == 4) {
            cityId = values[0];
            baseplateId = values[1];
            row = Integer.valueOf(values[2]);
            col = Integer.valueOf(values[3]);
        }
    }

    public String getCityId() {
        return cityId;
    }

    public String getBaseplateId() {
        return baseplateId;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
