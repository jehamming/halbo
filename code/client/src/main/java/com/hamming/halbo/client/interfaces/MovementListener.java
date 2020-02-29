package com.hamming.halbo.client.interfaces;

import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.model.dto.UserLocationDto;

public interface MovementListener {

    public void userMoved(UserDto user, UserLocationDto newUserLocation);

    public void teleported(UserLocationDto location);

}
