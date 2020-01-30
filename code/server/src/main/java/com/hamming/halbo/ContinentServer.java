package com.hamming.halbo;

import com.hamming.halbo.datamodel.intern.City;
import com.hamming.halbo.datamodel.intern.User;
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

    public City findCityByName(String name) {
        City retVal = null;
        for (City city : cities) {
            if (city.getName().equals(name)) {
                retVal = city;
                break;
            }
        }
        return retVal;
    }


    public void removeCityByCity(City city) {
        cities.remove(city);
    }

    public String getCitiesAsString() {
        StringBuilder sb = new StringBuilder();
        for (City c : cities){
            sb.append(c + "\n");
        }
        return sb.toString();
    }
}
