package com.hamming.halbo;

import com.hamming.halbo.factories.*;
import com.hamming.halbo.game.GameController;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

// This is the main Server for HALBO.
// CLients connect to this server and login
public class HALBOServer extends Server {

    private int port = 3131;
    private GameController controller;
    private int clients = 0;

    public HALBOServer() {
        super("HALBO");
    }

    public void initialize() {
        // Load Config
        ServerConfig config = ServerConfig.getInstance();
        port = config.getServerPort();
        // Load Data
        UserFactory.getInstance().loadUsersFromFile(config.getUsersDataFile());
        WorldFactory.getInstance().loadWorldsFromFile(config.getWorldsDataFile());
        ContinentFactory.getInstance().loadContinentsFromFile(config.getContinentsDataFile());
        CityFactory.getInstance().loadCitiesFromFile(config.getCitiesDataFile());
        BaseplateFactory.getInstance().loadBaseplatesFromFile(config.getBaseplatesDataFile());
        System.out.println(this.getClass().getName() + ":" + "Data loaded");

        // Start GameController
        controller = new GameController();
        Thread controllerThread = new Thread(controller);
        controllerThread.setDaemon(true);
        controllerThread.setName("GameController");
        controllerThread.start();
        System.out.println(this.getClass().getName() + ":" + "GameController started");
    }


    public void startServer() {
        startServer(port);
        System.out.println(this.getClass().getName() + ":" + "Started main HALBO Server, port:"+port);
    }


    @Override
    protected void clientConnected(Socket s, BufferedReader in, PrintWriter out) {
        try {
            clients++;
            ClientConnection client = new ClientConnection("Client-"+clients, s, in, out, controller);
            Thread clientThread = new Thread(client);
            controller.addListener(client);
            clientThread.setDaemon(true);
            clientThread.setName("Client " + s.getInetAddress().toString());
            clientThread.start();
            System.out.println(this.getClass().getName() + ":" + "Client " + s.getInetAddress().toString() + ", ClientThread started");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HALBOServer server = new HALBOServer();
        server.initialize();
        server.startServer();
    }


}
