package com.hamming.test.factories;

import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.model.City;
import com.hamming.halbo.factories.CityFactory;
import com.hamming.halbo.model.User;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestCityFactory {

    @Test
    public void testStoreAndLoad() {
        deleteAllCities();
        User u1 = UserFactory.getInstance().addUser("Luuk Hamming", "lhhamming", "lhhamming", "luuk.hamming@gmail.com");
        User u2 = UserFactory.getInstance().addUser("Jan-Egbert Hamming", "jehamming", "jehamming", "janneman@hotmail.com");
        City w1 = CityFactory.getInstance().addCity("City1", u1);
        City w2 = CityFactory.getInstance().addCity("City2", u2);
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile("HALBO","TST");
            CityFactory.getInstance().storeCitiesInFile(tmpFile);
            CityFactory.getInstance().deleteCity(w1);
            CityFactory.getInstance().deleteCity(w2);
            assert  CityFactory.getInstance().getCities().size() == 0;
            CityFactory.getInstance().loadCitiesFromFile(tmpFile);
            assert  CityFactory.getInstance().getCities().size() == 2;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteAllCities() {
        CityFactory.getInstance().getCities().removeAll(CityFactory.getInstance().getCities());

    }
}
