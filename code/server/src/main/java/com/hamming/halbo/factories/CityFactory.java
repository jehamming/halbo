package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.datamodel.City;
import com.hamming.halbo.datamodel.HalboID;
import com.hamming.halbo.datamodel.User;

import java.util.ArrayList;
import java.util.List;

public class CityFactory {
    private static CityFactory instance;

    private CityFactory() {
        initialize();
    };
    private List<City> cities;

    private void initialize() {
        cities = new ArrayList<City>();
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
