package com.hamming.halbo;

import com.hamming.halbo.factories.LDrawFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ServerConfig {

    private final static String DATADIR     = "datadir";
    private final static String SERVERPORT  = "serverport";
    private final static String USERSFILE   = "usersfile";
    private final static String WORLDSFILE  = "worldsfile";
    private final static String CITIESFILE  = "citiesfile";

    // Properties file location
    private final static String propertiesFile = "server.properties";

    // Defaults
    private String dataDirectory = "data";
    private int serverPort = 3131;
    private String worldsDataFile = "Worlds.dat";
    private String citiesDataFile = "Cities.dat";
    private String usersDataFile = "Users.dat";


    private static ServerConfig instance;

    private ServerConfig() {
        loadProperties();
    };

    public static ServerConfig getInstance() {
        if ( instance == null ) {
            instance = new ServerConfig();
        }
        return instance;
    }

    public void loadProperties() {
        // Load properties file
        Properties p = new Properties();
        try {
            File configFile = new File(propertiesFile);
            FileInputStream fis = new FileInputStream(configFile);
            p.load(fis);
            setDataDirectory(p.getProperty(DATADIR));
            setServerPort(Integer.valueOf(p.getProperty(SERVERPORT)));
            setWorldsDataFile(p.getProperty(WORLDSFILE));
            setCitiesDataFile(p.getProperty(CITIESFILE));
            setUsersDataFile(p.getProperty(USERSFILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getWorldsDataFile() {
        return worldsDataFile;
    }

    public void setWorldsDataFile(String worldsDataFile) {
        this.worldsDataFile = worldsDataFile;
    }

    public String getCitiesDataFile() {
        return citiesDataFile;
    }

    public void setCitiesDataFile(String citiesDataFile) {
        this.citiesDataFile = citiesDataFile;
    }

    public String getUsersDataFile() {
        return usersDataFile;
    }

    public void setUsersDataFile(String usersDataFile) {
        this.usersDataFile = usersDataFile;
    }

    public String getDataDirectory() {
        return dataDirectory;
    }

    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
