package com.hamming.test;

import com.hamming.halbo.ServerConfig;
import com.hamming.halbo.factories.*;
import com.hamming.halbo.model.*;

public class CreateMinimalWorld {

    public void createMinimalWorld() {
        // World of Luuk

        User u1 = UserFactory.getInstance().addUser("Luuk Hamming", "lhhamming", "lhhamming", "luuk.hamming@gmail.com");
        World w1 = WorldFactory.getInstance().createWorld(u1,"World001");
        Continent cn = ContinentFactory.getInstance().createContinent("Continent001", u1);
        w1.addContinent(cn);
        Baseplate bc1 = BaseplateFactory.getInstance().createBaseplate("Baseplate001", u1);
        City c1 = CityFactory.getInstance().createCity("City001", u1, bc1);
        cn.addCity(c1);

        // World of Jan
        User u2 = UserFactory.getInstance().addUser("Jan-Egbert Hamming", "jehamming", "jehamming", "janneman@hotmail.com");
        World w2 = WorldFactory.getInstance().createWorld(u1,"World002");
        Continent cn2 = ContinentFactory.getInstance().createContinent("Continent002", u2);
        w2.addContinent(cn2);
        Baseplate bc2 = BaseplateFactory.getInstance().createBaseplate("Baseplate002", u2);
        City c2 = CityFactory.getInstance().createCity("City002", u2, bc2);
        cn2.addCity(c2);

        storeEverything();
    }


    public void storeEverything() {
        ServerConfig config = ServerConfig.getInstance();
        UserFactory.getInstance().storeUsersInFile(config.getUsersDataFile());
        WorldFactory.getInstance().storeWorldsInFile(config.getWorldsDataFile());
        CityFactory.getInstance().storeCitiesInFile(config.getCitiesDataFile());
        BaseplateFactory.getInstance().storeBaseplatesInFile(config.getBaseplatesDataFile());
        ContinentFactory.getInstance().storeContinentsInFile(config.getContinentsDataFile());
        System.out.println(this.getClass().getName() + ":" + "Stored Users, Worlds, Cities, Continents, Baseplates");
    }

    public static void main(String[] args) {
        CreateMinimalWorld w = new CreateMinimalWorld();
        w.createMinimalWorld();
    }
}
