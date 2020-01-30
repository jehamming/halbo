package com.hamming.test;

import com.hamming.halbo.ServerConfig;
import com.hamming.halbo.datamodel.intern.City;
import com.hamming.halbo.datamodel.intern.Continent;
import com.hamming.halbo.datamodel.intern.User;
import com.hamming.halbo.datamodel.intern.World;
import com.hamming.halbo.factories.CityFactory;
import com.hamming.halbo.factories.ContinentFactory;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.factories.WorldFactory;

public class CreateMinimalWorld {

    public void createMinimalWorld() {
        User u1 = UserFactory.getInstance().addUser("Luuk Hamming", "lhhamming", "lhhamming");
        User u2 = UserFactory.getInstance().addUser("Jan-Egbert Hamming", "jehamming", "jehamming");
        World w1 = WorldFactory.getInstance().addWorld(u1.getId().toString(), u1.getId().toString(),"World001");
        Continent c = ContinentFactory.getInstance().createContinent("Continent001", u2.toString());
        w1.addContinent(c);
        City c1 = CityFactory.getInstance().addCity("City001", u1.toString());
        c.addCity(c1);
        storeEverything();
    }


    public void storeEverything() {
        ServerConfig config = ServerConfig.getInstance();
        UserFactory.getInstance().storeUsersInFile(config.getUsersDataFile());
        WorldFactory.getInstance().storeWorldsInFile(config.getWorldsDataFile());
        CityFactory.getInstance().storeCitiesInFile(config.getCitiesDataFile());
        System.out.println("Stored Users, Worlds, Cities");
    }

    public static void main(String[] args) {
        CreateMinimalWorld w = new CreateMinimalWorld();
        w.createMinimalWorld();
    }
}
