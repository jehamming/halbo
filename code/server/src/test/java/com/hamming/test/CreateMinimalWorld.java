package com.hamming.test;

import com.hamming.halbo.ServerConfig;
import com.hamming.halbo.factories.*;
import com.hamming.halbo.model.*;

public class CreateMinimalWorld {

    public void createMinimalWorld() throws CityGridException {
        // World of Luuk
        User u1 = UserFactory.getInstance().addUser("Luuk Hamming", "lhhamming", "lhhamming", "luuk.hamming@gmail.com");
        World w1 = WorldFactory.getInstance().createWorld(u1,"World001");
        Continent cn = ContinentFactory.getInstance().createContinent("Continent001", u1);
        w1.addContinent(cn);
        Baseplate teleportLuukBaseplate = BaseplateFactory.getInstance().createBaseplate("Baseplate001", u1);
        City c1 = CityFactory.getInstance().createCity("City001", u1, teleportLuukBaseplate);
        Baseplate baseplateLuuk1 = BaseplateFactory.getInstance().createBaseplate("BaseplateLuuk001", u1);
        c1.getCityGrid().addBasePlate(baseplateLuuk1, teleportLuukBaseplate, CityGrid.Direction.NORTH);
        Baseplate baseplateLuuk2 = BaseplateFactory.getInstance().createBaseplate("BaseplateLuuk002", u1);
        c1.getCityGrid().addBasePlate(baseplateLuuk2, teleportLuukBaseplate, CityGrid.Direction.SOUTH);
        cn.addCity(c1);

        // World of Jan
        User u2 = UserFactory.getInstance().addUser("Jan-Egbert Hamming", "jehamming", "jehamming", "janneman@hotmail.com");
        World w2 = WorldFactory.getInstance().createWorld(u1,"World002");
        Continent cn2 = ContinentFactory.getInstance().createContinent("Continent002", u2);
        w2.addContinent(cn2);
        Baseplate teleportJanBaseplate = BaseplateFactory.getInstance().createBaseplate("Baseplate002", u2);
        City c2 = CityFactory.getInstance().createCity("City002", u2, teleportJanBaseplate);
        Baseplate baseplateJan1 = BaseplateFactory.getInstance().createBaseplate("BaseplateJan001", u1);
        c2.getCityGrid().addBasePlate(baseplateJan1, teleportJanBaseplate, CityGrid.Direction.EAST);
        Baseplate baseplateJan2 = BaseplateFactory.getInstance().createBaseplate("BaseplateJan002", u1);
        c2.getCityGrid().addBasePlate(baseplateJan2, teleportJanBaseplate, CityGrid.Direction.WEST);
        cn2.addCity(c2);

        // 3rd World
        User u3 = UserFactory.getInstance().addUser("User1", "user1", "user1", "user1@hotmail.com");
        World w3 = WorldFactory.getInstance().createWorld(u1,"World003");
        Continent cn3 = ContinentFactory.getInstance().createContinent("Continent003", u3);
        w3.addContinent(cn3);
        Baseplate teleportBaseplate3 = BaseplateFactory.getInstance().createBaseplate("Baseplate033", u3);
        City c3 = CityFactory.getInstance().createCity("City003", u2, teleportBaseplate3);
        Baseplate baseplate31 = BaseplateFactory.getInstance().createBaseplate("EAST", u3);
        c3.getCityGrid().addBasePlate(baseplate31, teleportBaseplate3, CityGrid.Direction.EAST);
        Baseplate baseplate32 = BaseplateFactory.getInstance().createBaseplate("WEST", u3);
        c3.getCityGrid().addBasePlate(baseplate32, teleportBaseplate3, CityGrid.Direction.WEST);
        Baseplate baseplate33 = BaseplateFactory.getInstance().createBaseplate("NORTH", u3);
        c3.getCityGrid().addBasePlate(baseplate33, teleportBaseplate3, CityGrid.Direction.NORTH);
        Baseplate baseplate34 = BaseplateFactory.getInstance().createBaseplate("SOUTH", u3);
        c3.getCityGrid().addBasePlate(baseplate34, teleportBaseplate3, CityGrid.Direction.SOUTH);
        cn3.addCity(c3);

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

    public static void main(String[] args) throws CityGridException {
        CreateMinimalWorld w = new CreateMinimalWorld();
        w.createMinimalWorld();
    }
}
