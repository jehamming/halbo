package com.hamming.test;

import com.hamming.halbo.ServerConfig;
import com.hamming.halbo.model.City;
import com.hamming.halbo.model.Continent;
import com.hamming.halbo.model.User;
import com.hamming.halbo.model.World;
import com.hamming.halbo.factories.CityFactory;
import com.hamming.halbo.factories.ContinentFactory;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.factories.WorldFactory;

public class CreateMinimalWorld {

    public void createMinimalWorld() {
        User u1 = UserFactory.getInstance().addUser("Luuk Hamming", "lhhamming", "lhhamming", "luuk.hamming@gmail.com");
        User u2 = UserFactory.getInstance().addUser("Jan-Egbert Hamming", "jehamming", "jehamming", "janneman@hotmail.com");
        World w1 = WorldFactory.getInstance().createWorld(u1,"World001");
        Continent c = ContinentFactory.getInstance().createContinent("Continent001", u2);
        w1.addContinent(c);
        City c1 = CityFactory.getInstance().addCity("City001", u1);
        c.addCity(c1);
        storeEverything();
    }


    public void storeEverything() {
        ServerConfig config = ServerConfig.getInstance();
        UserFactory.getInstance().storeUsersInFile(config.getUsersDataFile());
        WorldFactory.getInstance().storeWorldsInFile(config.getWorldsDataFile());
        CityFactory.getInstance().storeCitiesInFile(config.getCitiesDataFile());
        ContinentFactory.getInstance().storeContinentsInFile(config.getContinentsDataFile());
        System.out.println("Stored Users, Worlds, Cities, Continents");
    }

    public static void main(String[] args) {
        CreateMinimalWorld w = new CreateMinimalWorld();
        w.createMinimalWorld();
    }
}
