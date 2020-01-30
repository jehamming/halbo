package com.hamming.test.factories;

import com.hamming.halbo.datamodel.intern.City;
import com.hamming.halbo.factories.CityFactory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestCityFactory {

    @Test
    public void testStoreAndLoad() {
        deleteAllCities();
        City w1 = CityFactory.getInstance().addCity("City1", "USR1");
        City w2 = CityFactory.getInstance().addCity("City2", "USR2");
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
