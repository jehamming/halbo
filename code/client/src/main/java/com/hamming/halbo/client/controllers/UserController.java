package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.IConnectionListener;
import com.hamming.halbo.client.interfaces.IUserListener;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.net.CommandReceiver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserController implements IConnectionListener, CommandReceiver {

    private ProtocolHandler protocolHandler;
    private ConnectionController connectionController;
    private List<IUserListener> userListeners;
    private List<UserDto> users;
    private UserDto currentUser;

    public UserController(ConnectionController connectionController) {
        this.connectionController = connectionController;
        connectionController.addConnectionListener(this);
        protocolHandler = new ProtocolHandler();
        userListeners = new ArrayList<IUserListener>();
        connectionController.registerReceiver(Protocol.Command.LOGIN,this);
        connectionController.registerReceiver(Protocol.Command.USERCONNECTED,this);
        connectionController.registerReceiver(Protocol.Command.USERDISCONNECTED,this);
        connectionController.registerReceiver(Protocol.Command.GETUSER,this);
    }

    @Override
    public void connected() {
        connectionController.registerReceiver(Protocol.Command.LOGIN, this);
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
            String newCommand = protocolHandler.getWorldsCommand();
            connectionController.send(newCommand);
            success = true;
        } else {
            currentUser = null;
            msg = Arrays.toString(values);
        }
        sendLoginResult(success, msg);
    }


    public void sendLoginResult(boolean success, String msg) {
        for (IUserListener userListener: userListeners) {
            userListener.loginResult(success, msg);
        }
    }

    public void addUserListener(IUserListener l) {
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
                System.out.println(getClass().getName() + ":" + cmd + ": NOT IMPLEMENTED YET");
                break;
            case USERCONNECTED:
                userConnected(data);
                break;
            case USERDISCONNECTED:
                userDisconnected(data);
                break;
        }
    }

    private void userConnected(String[] data) {
        UserDto user = new UserDto();
        user.setValues(data);
        for (IUserListener l: userListeners) {
            l.userConnected(user);
        }
    }

    private void userDisconnected(String[] data) {
        UserDto user = new UserDto();
        user.setValues(data);
        for (IUserListener l: userListeners) {
            l.userDisconnected(user);
        }
    }


}
