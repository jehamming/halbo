package com.hamming.test;

import com.hamming.halbo.ServerConfig;
import com.hamming.halbo.factories.*;
import com.hamming.halbo.model.*;

public class CreateMinimalWorld {

    public void createMinimalWorld() {
        // World of Luuk
        User u1 = UserFactory.getInstance().addUser("Luuk Hamming", "lhhamming", "lhhamming", "luuk.hamming@gmail.com");
        World w1 = WorldFactory.getInstance().createWorld(u1,"World001");
        Continent c = ContinentFactory.getInstance().createContinent("Continent001", u1);
        w1.addContinent(c);
        City c1 = CityFactory.getInstance().addCity("City001", u1);
        c.addCity(c1);
        Baseplate b = BaseplateFactory.getInstance().createBaseplate("Baseplate01", u1);
        c1.addBaseplate(b,0,0);
        Baseplate c1b2 = BaseplateFactory.getInstance().createBaseplate("Baseplate02", u1);
        c1.addBaseplate(c1b2,0,0);
        // World of Jan
        User u2 = UserFactory.getInstance().addUser("Jan-Egbert Hamming", "jehamming", "jehamming", "janneman@hotmail.com");
        World w2 = WorldFactory.getInstance().createWorld(u1,"World002");
        Continent c2 = ContinentFactory.getInstance().createContinent("Continent002", u2);
        w2.addContinent(c2);
        City c3 = CityFactory.getInstance().addCity("City001", u2);
        c2.addCity(c3);
        Baseplate b2 = BaseplateFactory.getInstance().createBaseplate("Baseplate03", u2);
        c3.addBaseplate(b2,0,0);
        Baseplate c3b2 = BaseplateFactory.getInstance().createBaseplate("Baseplate04", u2);
        c3.addBaseplate(c3b2,0,0);

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
