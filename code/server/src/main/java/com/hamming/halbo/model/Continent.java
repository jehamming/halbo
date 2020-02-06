package com.hamming.halbo.model;

import com.hamming.halbo.factories.CityFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// A Continent exists of a set of cities
// TODO How to position or connect cities in an continent?
public class Continent extends BasicObject {

    private User senator;
    private List<City> cities;

    public Continent(HalboID id, String name) {
        super(id);
        setName(name);
        cities = new ArrayList<City>();
    }

    public boolean addCity(City city) {
        cities.add(city);
        return true;
    }


    public void removeCity(City city) {
        cities.remove(city.getId());
    }

    public String getCitiesAsString() {
        StringBuilder sb = new StringBuilder();
        for (City city : cities){
            sb.append(city + "\n");
        }
        return sb.toString();
    }

    public User getSenator() {
        return senator;
    }

    public void setSenator(User senator) {
        this.senator = senator;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "senator=" + senator +
                super.toString() +
                '}';
    }
}
