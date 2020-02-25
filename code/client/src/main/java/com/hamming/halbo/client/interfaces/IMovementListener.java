package com.hamming.halbo.client.interfaces;

import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.model.dto.UserLocationDto;

public interface IMovementListener {

    public void userMoved(UserLocationDto newUserLocation);

}
