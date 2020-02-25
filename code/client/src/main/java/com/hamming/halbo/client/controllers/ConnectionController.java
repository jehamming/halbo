package com.hamming.halbo.client.controllers;

import com.hamming.halbo.client.interfaces.IConnectionListener;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.net.CommandReceiver;
import com.hamming.halbo.net.NetClient;

import java.util.*;

public class ConnectionController implements CommandReceiver {

    private NetClient client;
    private ProtocolHandler protocolHandler;
    private UserDto user;
    private List<IConnectionListener> connectionListeners;

    private Map<Protocol.Command, List<CommandReceiver>> commandReceivers;


    public ConnectionController() {
        protocolHandler = new ProtocolHandler();
        connectionListeners = new ArrayList<IConnectionListener>();
        commandReceivers = new HashMap<Protocol.Command, List<CommandReceiver>>();
    }

    public void disconnect() {
        if (client != null ) {
            client.dispose();
        }
    }

    public void connect(String serverip, int port) throws Exception {
        if (client != null && !client.isConnected()) {
            client.dispose();
        }
        client = new NetClient(this);
        user = null;
        String result = client.connect(serverip, port);
        if (result != null) throw new Exception("Error:" + result);
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


    @Override
    public void receiveCommand(Protocol.Command cmd, String[] data) {
        List<CommandReceiver> listReceivers = commandReceivers.get(cmd);
        boolean handled = false;
        if (listReceivers != null) {
            for (CommandReceiver c : listReceivers) {
                c.receiveCommand(cmd, data);
                handled = true;
            }
        }
        if (!handled) {
            System.out.println(this.getClass().getName() + ":" + "Command " + cmd.toString() + " NOT handled");
        }

    }



    public void registerReceiver(Protocol.Command cmd, CommandReceiver receiver) {
        List<CommandReceiver> listReceivers = commandReceivers.get(cmd);
        if (listReceivers == null) {
            listReceivers = new ArrayList<CommandReceiver>();
        }
        listReceivers.add(receiver);
        commandReceivers.put(cmd, listReceivers);
    }

}
