package com.hamming.halbo.factories;

import com.hamming.halbo.datamodel.City;
import com.hamming.halbo.datamodel.User;

import java.util.ArrayList;
import java.util.List;

public class ContinentFactory {

    private List<City> cities;
    private static ContinentFactory instance;

    private ContinentFactory() {
        initialize();
    };

    private void initialize() {
        cities = new ArrayList<City>();
    }

    public static ContinentFactory getInstance() {
        if ( instance == null ) {
            instance = new ContinentFactory();
        }
        return instance;
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


    public void removeCity(City city) {
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
