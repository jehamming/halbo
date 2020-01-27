package com.hamming.halbo;

import com.hamming.halbo.datamodel.City;
import com.hamming.halbo.datamodel.HalboID;
import com.hamming.halbo.datamodel.User;
import com.hamming.halbo.factories.CityFactory;

import java.util.ArrayList;
import java.util.List;

// This Class is the server that serves a City, meaning a collection of Baseplates with Constructions on them
public class ContinentServer extends Server{

    private List<City> cities;

    public ContinentServer() {
        cities = new ArrayList<City>();
    }


    public City addCity(String name, User creator) {
        City city = CityFactory.getInstance().createCity(name, creator);
        cities.add(city);
        return city;
    }

    public City addCity(City city) {
        cities.add(city);
        return city;
    }

    public City removeCityByName(String name) {
        City city = findCityByName(name);
        if ( city == null ) {
            cities.remove(city);
        }
        return city;
    }

    private City findCityByName(String name) {
        City retVal = null;
        for (City city : cities) {
            if ( city.getName().equals(name)) {
                retVal = city;
                break;
            }
        }
        return retVal;
    }


}
