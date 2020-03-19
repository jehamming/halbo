package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.ConnectionListener;
import com.hamming.halbo.client.interfaces.UserListener;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.net.CommandReceiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserController implements ConnectionListener, CommandReceiver {

    private ProtocolHandler protocolHandler;
    private ConnectionController connectionController;
    private List<UserListener> userListeners;
    private List<UserDto> users;
    private UserDto currentUser;

    public UserController(ConnectionController connectionController) {
        this.connectionController = connectionController;
        connectionController.addConnectionListener(this);
        protocolHandler = new ProtocolHandler();
        userListeners = new ArrayList<UserListener>();
        users = new ArrayList<UserDto>();
        connectionController.registerReceiver(Protocol.Command.LOGIN,this);
        connectionController.registerReceiver(Protocol.Command.USERCONNECTED,this);
        connectionController.registerReceiver(Protocol.Command.USERDISCONNECTED,this);
        connectionController.registerReceiver(Protocol.Command.GETUSER,this);
    }

    @Override
    public void connected() {
        connectionController.registerReceiver(Protocol.Command.LOGIN, this);
    }

    @Override
    public void disconnected() {
        connectionController.unregisterReceiver(Protocol.Command.LOGIN,this);
    }

    public void sendLogin(String username, String password) {
        String s = protocolHandler.getLoginCommand(username, password);
        connectionController.send(s);
    }

    private void checkLoginOk( String[] data) {
        String status = data[0];
        String[] values = Arrays.copyOfRange(data, 1, data.length);
        String msg = "";
        boolean success = false;
        if (Protocol.SUCCESS.equals(status)) {
            currentUser = new UserDto();
            currentUser.setValues(values);
            success = true;
        } else {
            currentUser = null;
            msg = Arrays.toString(values);
        }
        sendLoginResult(success, msg);
    }


    public void sendLoginResult(boolean success, String msg) {
        for (UserListener userListener: userListeners) {
            userListener.loginResult(success, msg);
        }
    }

    public void addUserListener(UserListener l) {
        userListeners.add(l);
    }

    public UserDto getCurrentUser() {
        return currentUser;
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        switch (cmd) {
            case LOGIN:
                checkLoginOk(data);
                break;
            case GETUSER:
                handleGetUser(data);
                break;
            case USERCONNECTED:
                userConnected(data);
                break;
            case USERDISCONNECTED:
                userDisconnected(data);
                break;
        }
    }

    private void handleGetUser(String[] data) {
        String status = data[0];
        String[] values = Arrays.copyOfRange(data, 1, data.length);
        String msg = "";
        if (Protocol.SUCCESS.equals(status)) {
            UserDto user = new UserDto();
            user.setValues(values);
            users.add(user);
        } else {
            msg = Arrays.toString(values);
            System.out.println("Get user failed: " + msg);
        }
    }

    private void userConnected(String[] data) {
        UserDto user = new UserDto();
        user.setValues(data);
        users.add(user);
        for (UserListener l: userListeners) {
            l.userConnected(user);
        }
    }

    private void userDisconnected(String[] data) {
        UserDto user = new UserDto();
        user.setValues(data);
        users.remove(user);
        for (UserListener l: userListeners) {
            l.userDisconnected(user);
        }
    }

    public UserDto getUser(String userId) {
        UserDto user = findUserById(userId);
        if (user == null ) {
            connectionController.send(protocolHandler.getUserCommand(userId));
            // TODO Wait for user details?
        }
        return user;
    }

    private UserDto findUserById(String userId) {
        UserDto found = null;
        for (UserDto user : users) {
            if (user.getId().equals(userId)) {
                found = user;
                break;
            }
        }
        return found;
    }


}
