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
    private ProtocolHandler protocolHandler;
    private CommandReceiver receiver;

    public NetClient(CommandReceiver receiver) {
        this.receiver = receiver;
        protocolHandler = new ProtocolHandler();
    }

    public String connect(String ip, int port) {
        String retval = null;
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Do Protocol handshake
            doProtocolHandshake();

            Thread clientThread = new Thread(this);
            clientThread.setName("Client Connection");
            clientThread.setDaemon(true);
            clientThread.start();
            open = true;
        } catch (IOException e) {
            System.out.println(this.getClass().getName() + ":" + "ERROR:" + e.getMessage());
            retval = e.getMessage();
            //e.printStackTrace();
        }
        return retval;
    }

    private void doProtocolHandshake() throws IOException {
        send(protocolHandler.getVersionCommand() + StringUtils.delimiter + Protocol.version);
        String response = in.readLine();
        if (response != null) {
            Protocol.Command cmd = protocolHandler.parseCommandString(response);
            if (cmd != null && cmd.equals(Protocol.Command.VERSION)) {
                String[] splitted = response.split(StringUtils.delimiter);
                String[] data = Arrays.copyOfRange(splitted, 1, splitted.length);
                String status = Arrays.toString(data);
                if (Protocol.FAILED.equals(status)) {
                    String failure = data[1];
                    throw new IOException(failure);
                }
            }
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(in);
        while (open && scanner.hasNextLine()) {
            try {
                String s = scanner.nextLine();
                if ( s != null ) {
                    received(s);
                }
            } catch (Exception e) {
                open = false;
                System.out.println(this.getClass().getName() + ":" + "Error:" + e.getMessage());
                e.printStackTrace();
            }
        }
        if ( socket != null ) {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        System.out.println(this.getClass().getName() + ":" + "NetClient finished");
    }

    public void send(String s) {
        System.out.println(this.getClass().getName() + ":" + "Send:" +s );
        out.println(s);
    }

    public void received(String s) {
        Protocol.Command cmd = protocolHandler.parseCommandString(s);
        System.out.println(this.getClass().getName() + ":" + "Received:(" +cmd +")-" + s);
        String[] splitted = s.split(StringUtils.delimiter);
        String[] data = Arrays.copyOfRange(splitted, 1, splitted.length);
        receiver.receiveCommand(cmd, data);
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
