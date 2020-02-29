package com.hamming.halbo.client.interfaces;

import com.hamming.halbo.model.dto.UserDto;

public interface UserListener {

    public void userConnected(UserDto user);
    public void userDisconnected(UserDto user);

    public void loginResult(boolean success, String message);

}
