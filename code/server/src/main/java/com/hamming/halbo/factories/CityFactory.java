package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.datamodel.City;
import com.hamming.halbo.datamodel.HalboID;
import com.hamming.halbo.datamodel.User;

public class CityFactory {
    private static CityFactory instance;

    private CityFactory() {
        initialize();
    };

    private void initialize() {
        // TODO Implement reading all the types from a source like XML?
    }

    public static CityFactory getInstance() {
        if ( instance == null ) {
            instance = new CityFactory();
        }
        return instance;
    }


    public City createCity(String name, User creator) {
        HalboID id = IDManager.getInstance().getNextID(HalboID.Prefix.CTY);
        City city = new City(id);
        city.setName(name);
        city.setCreator(creator);
        city.setOwner(creator);
        return city;
    }
}
