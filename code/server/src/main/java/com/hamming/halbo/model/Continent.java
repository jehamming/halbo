package com.hamming.halbo.model.dto.intern;

import com.hamming.halbo.factories.CityFactory;

import java.util.HashMap;
import java.util.Map;

// A Continent exists of a set of cities
// TODO How to position or connect cities in an continent?
public class Continent extends BasicObject {

    private String senatorID;
    private Map<String, String> cities;

    public Continent(String id, String name) {
        super(id);
        setName(name);
        cities = new HashMap<String, String>();
    }

    public boolean addCity(City city) {
        cities.put(city.getId(), city.getName());
        return true;
    }

    public String getSenatorID() {
        return senatorID;
    }

    public void setSenatorID(String senatorID) {
        this.senatorID = senatorID;
    }

    public Map<String, String> getCities() {
        return cities;
    }

    public City addCity(String name, User creator) {
        City city = CityFactory.getInstance().addCity(name, creator.toString());
        cities.put(city.getId(), name);
        return city;
    }



    public void removeCity(City city) {
        cities.remove(city.getId());
    }

    public String getCitiesAsString() {
        StringBuilder sb = new StringBuilder();
        for (String cityId : cities.keySet()){
            City city = CityFactory.getInstance().findCityByID(cityId);
            sb.append(city + "\n");
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        return "Continent{" +
                "senator=" + senatorID +
                super.toString() +
                '}';
    }
}
