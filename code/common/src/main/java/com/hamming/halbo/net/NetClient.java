package com.hamming.halbo.net;

import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.game.ProtocolHandler;
import com.hamming.halbo.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class NetClient implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean open = true;
    private Map<Protocol.Command, List<CommandReceiver>> receivers;
    private ProtocolHandler protocolHandler;

    public NetClient() {
        receivers = new HashMap<Protocol.Command, List<CommandReceiver>>();
        protocolHandler = new ProtocolHandler();
    }

    public String connect(String ip, int port) {
        String retval = null;
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            Thread clientThread = new Thread(this);
            clientThread.setName("Client Connection");
            clientThread.setDaemon(true);
            clientThread.start();
            open = true;
        } catch (IOException e) {
            System.out.println("ERROR:" + e.getMessage());
            retval = e.getMessage();
            //e.printStackTrace();
        }
        return retval;
    }

    @Override
    public void run() {
        while (open) {
            try {
                String s = in.readLine();
                if (s != null) {
                    received(s);
                }
            } catch (Exception e) {
                open = false;
                System.out.println("Error:" + e.getMessage());
            }
        }
        if ( socket != null ) {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        System.out.println("NetClient finished");
    }

    public void send(String s) {
        out.println(s);
    }

    public void received(String s) {
        System.out.println("Received:" + s);
        Protocol.Command cmd = protocolHandler.parseCommandString(s);
        String[] splitted = s.split(StringUtils.delimiter);
        String[] data = Arrays.copyOfRange(splitted, 1, splitted.length);
        List<CommandReceiver> listReceivers = receivers.get(cmd);
        boolean handled = false;
        if (listReceivers != null) {
            for (CommandReceiver c : listReceivers) {
                c.receiveCommand(cmd, data);
                handled = true;
            }
        }

        if (!handled) {
            System.out.println("Command " + cmd.toString() + " NOT handled");
        }

    }

    public void registerReceiver(Protocol.Command cmd, CommandReceiver receiver) {
        List<CommandReceiver> listReceivers = receivers.get(cmd);
        if (listReceivers == null) {
            listReceivers = new ArrayList<CommandReceiver>();
        }
        listReceivers.add(receiver);
        receivers.put(cmd, listReceivers);
    }


    public void dispose() {
        try {
            if (open) {
                open = false;
                closeConnection();
            }
            socket = null;
            in = null;
            out = null;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void closeConnection() throws IOException {
        try {
            out.close();
        } finally {
            try {
                in.close();
            } finally {
                socket.close();
            }
        }

    }


    public boolean isConnected() {
        return open;
    }

}
