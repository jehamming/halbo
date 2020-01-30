package com.hamming.halbo;

import com.hamming.halbo.datamodel.intern.City;
import com.hamming.halbo.datamodel.intern.World;
import com.hamming.halbo.factories.CityFactory;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.factories.WorldFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// This is the main Server for HALBO.
// CLients connect to this server and login
public class HALBOServer extends Server {

    private List<CityServer> cityServers;
    private List<WorldServer> worldServers;
    private int port = 3131;

    public void initialize() {
        // Load Config
        ServerConfig config = ServerConfig.getInstance();
        port = config.getServerPort();
        // Load Data
        UserFactory.getInstance().loadUsersFromFile(config.getUsersDataFile());
        WorldFactory.getInstance().loadWorldsFromFile(config.getWorldsDataFile());
        CityFactory.getInstance().loadCitiesFromFile(config.getCitiesDataFile());

        cityServers = new ArrayList<CityServer>();
        worldServers = new ArrayList<WorldServer>();

        startWorldServers();
        startCityServers();


    }

    private void startCityServers() {
        for (City city : CityFactory.getInstance().getCities()) {
            // Start a new Server thread
            CityServer cityServer = new CityServer(city);
            Thread clientThread = new Thread(cityServer);
            clientThread.setDaemon(true);
            clientThread.setName("CityServer-"+city.getName());
            clientThread.start();
            System.out.println("Started CityServer-"+city.getName());
            // Register the thread
            cityServers.add(cityServer);
        }
    }

    private void startWorldServers() {
        for (World world: WorldFactory.getInstance().getWorlds()) {
            // Start a new Server thread
            WorldServer worldServer = new WorldServer(world);
            Thread clientThread = new Thread(worldServer);
            clientThread.setDaemon(true);
            clientThread.setName("WorldServer-"+world.getName());
            clientThread.start();
            System.out.println("Started WorldServer-"+world.getName());
            // Register the thread
            worldServers.add(worldServer);
        }
    }

    public void startServer() {
        startServer(port);
    }



    @Override
    protected ClientInstance clientConnected(Socket s, BufferedReader in, PrintWriter out) {
        return null;
    }


    public static void main(String[] args) {

        HALBOServer server = new HALBOServer();
        server.initialize();
        server.startServer();

    }



}
