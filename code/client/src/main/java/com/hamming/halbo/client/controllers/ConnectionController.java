package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.IConnectionListener;
import com.hamming.halbo.client.interfaces.ILoginCallback;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.net.CommandReceiver;
import com.hamming.halbo.net.NetClient;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConnectionController implements CommandReceiver {

    private NetClient client;
    private ProtocolHandler protocolHandler;
    private UserDto user;
    private ILoginCallback loginCallback;
    private List<IConnectionListener> connectionListeners;

    public ConnectionController() {
        protocolHandler = new ProtocolHandler();
        connectionListeners = new ArrayList<IConnectionListener>();
    }

    public void disconnect() {
        if (client != null ) {
            client.dispose();
        }
    }

    public void connect(String serverip, int port, ILoginCallback callback) throws Exception {
        if (client != null && !client.isConnected()) {
            client.dispose();
        }
        client = new NetClient();
        user = null;
        loginCallback = callback;
        String result = client.connect(serverip, port);
        if (result != null) throw new Exception("Error:" + result);
        registerReceiver(Protocol.Command.LOGIN,this);
    }

    public void sendLogin(String username, String password) {
        String s = protocolHandler.getLoginCommand(username, password);
        client.send(s);
    }

    public void registerReceiver(Protocol.Command cmd, CommandReceiver receiver) {
        if (client != null ) {
            client.registerReceiver(cmd, receiver);
        }
    }

    private void checkLoginOk( String[] data) {
        String status = data[0];
        String[] values = Arrays.copyOfRange(data, 1, data.length);
        if (Protocol.SUCCESS.equals(status)) {
            user = new UserDto();
            user.setValues(values);
            String newCommand = protocolHandler.getWorldsCommand();
            client.send(newCommand);
            fireConnectedEvent();
            loginCallback.loginResult(true, null);
        } else {
            user = null;
            String msg = Arrays.toString(values);
            loginCallback.loginResult(true, msg);
        }
    }

    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        if (cmd.equals(Protocol.Command.LOGIN)) {
            checkLoginOk(data);
        }
    }

    public void send(String s) {
        if (client != null ) {
            client.send(s);
        }
    }

    public void fireConnectedEvent() {
        for (IConnectionListener l: connectionListeners) {
            l.connected();
        }
    }

    public void addConnectionListener(IConnectionListener listener) {
        connectionListeners.add(listener);
    }

    public void removeConnectionListener(IConnectionListener l) {
        connectionListeners.remove(l);
    }


}
