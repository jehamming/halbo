package com.hamming.halbo;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

// This is the main server class
public abstract class Server {
    private int port;
    private boolean open = true;
    private ServerSocket ss;
    private List<ClientInstance> clients = new ArrayList<>();

    public void startServer(int port) {
        try {
            ss = new ServerSocket(port);
            if (this.port == 0) this.port = ss.getLocalPort();
            else this.port = port;
            Thread serverThread = new Thread(new Runnable() {
                public void run() {
                    while (open) {
                        try {
                            final Socket s = ss.accept();
                            Thread clientThread = new Thread(new Runnable() {
                                public void run() {
                                    try {
                                        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                                        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                                        clients.add(clientConnected(s, in, out));
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            });
                            clientThread.setDaemon(true);
                            clientThread.setName("Client " + s.getInetAddress().toString());
                            clientThread.start();
                        } catch (SocketException e) {  //Do nothing
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            serverThread.setDaemon(true);
            serverThread.setName("Server");
            serverThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void dispose() {
        open = false;
        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (ClientInstance client : clients) {
            try {
                client.getSocket().close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        clients.clear();
        clients = null;
        ss = null;
    }

    public String getIp() {
        try {
            ss.getInetAddress();
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("resource")
    public void kickClient(ClientInstance client) {
        try {
            client.getSocket().shutdownOutput();
            client.getSocket().close();
            clients.remove(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected abstract ClientInstance clientConnected(Socket s, BufferedReader in, PrintWriter out);

}