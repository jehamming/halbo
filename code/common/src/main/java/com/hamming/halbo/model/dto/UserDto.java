package com.hamming.halbo.model.dto;

import com.hamming.halbo.util.StringUtils;

public class UserDto implements DTO {

    private String id;
    private String name;
    private String email;

    public UserDto(){

    }

    public UserDto(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toNetData() {
        String data = id + StringUtils.delimiter
                + name + StringUtils.delimiter
                + email;
        return data;
    }

    @Override
    public void setValues(String... values) {
        if ( values.length == 3) {
            id = values[0];
            name = values[1];
            email = values[2];
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return name;
    }
}
