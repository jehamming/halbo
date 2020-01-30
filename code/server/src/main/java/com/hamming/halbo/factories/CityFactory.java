package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.datamodel.intern.City;
import com.hamming.halbo.datamodel.intern.HalboID;
import com.hamming.halbo.datamodel.intern.User;
import com.hamming.halbo.datamodel.intern.World;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CityFactory extends AbstractFactory {
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

    public List<City> getCities() {
        return cities;
    }

    public City findCityByID(String id) {
        City found = null;
        for (City c: cities) {
            if ( c.getId().equals(id) ) {
                found = c;
                break;
            }
        }
        return found;
    }

    public City findCityByName(String name) {
        City found = null;
        for (City c: cities) {
            if ( c.getName().equals(name) ) {
                found = c;
                break;
            }
        }
        return found;
    }


    public City addCity(String name, String creatorID) {
        HalboID id = IDManager.getInstance().getNextID(HalboID.Prefix.CTY);
        City city = new City(id.toString(), name);
        city.setCreatorID(creatorID);
        city.setOwnerID(creatorID);
        cities.add(city);
        return city;
    }



    public void deleteCity(City toDeleteCity) {
        cities.remove(toDeleteCity);
    }


    public String getCitiesAsString() {
        StringBuilder sb = new StringBuilder();
        for (City c : cities){
            sb.append(c + "\n");
        }
        return sb.toString();
    }


    public boolean loadCitiesFromFile(String filename) {
        File file = new File(filename);
        return loadCitiesFromFile(file);
    }

    public boolean loadCitiesFromFile(File file) {
        boolean retval = true;
        List<City> loadCities = (ArrayList<City>) loadFromFile(file);
        if (loadCities != null) {
            cities = loadCities;
        }
        return retval;
    }

    public boolean storeCitiesInFile(String filename) {
        return storeInFile(cities, filename);
    }

    public boolean storeCitiesInFile(File file) {
        return storeInFile(cities, file);
    }


}
