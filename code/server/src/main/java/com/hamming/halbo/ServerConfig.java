package com.hamming.halbo;

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
    private final static String CONTINENTSFILE  = "continentsfile";
    private final static String BASEPLATESFILE  = "baseplatesfile";

    // Properties file location
    private final static String propertiesFile = "server.properties";
    private Properties properties;

    // Defaults
    private String dataDirectory = "data";
    private int serverPort = 3131;
    private String worldsDataFile = "Worlds.dat";
    private String citiesDataFile = "Cities.dat";
    private String usersDataFile = "Users.dat";
    private String continentsDataFile = "Continents.dat";
    private String baseplatesDataFile = "Baseplates.dat";


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
        properties = new Properties();
        try {
            File configFile = new File(propertiesFile);
            FileInputStream fis = new FileInputStream(configFile);
            properties.load(fis);
            setDataDirectory(loadProperty(DATADIR));
            setServerPort(Integer.valueOf(loadProperty(SERVERPORT)));
            setWorldsDataFile(loadProperty(WORLDSFILE));
            setCitiesDataFile(loadProperty(CITIESFILE));
            setUsersDataFile(loadProperty(USERSFILE));
            setContinentsDataFile(loadProperty(CONTINENTSFILE));
            setBaseplatesDataFile(loadProperty(BASEPLATESFILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadProperty( String name ) {
        String value = properties.getProperty(name);
        if (value == null) {
            System.out.println(this.getClass().getName() + ":" + "Error loading property '" + name +"', no value found!");
        }
        return value;
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

    public void setContinentsDataFile(String continentsDataFile) {
        this.continentsDataFile = continentsDataFile;
    }

    public String getContinentsDataFile() {
        return continentsDataFile;
    }

    public String getBaseplatesDataFile() {
        return baseplatesDataFile;
    }

    public void setBaseplatesDataFile(String baseplatesDataFile) {
        this.baseplatesDataFile = baseplatesDataFile;
    }
}
