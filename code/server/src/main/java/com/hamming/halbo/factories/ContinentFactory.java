package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.datamodel.intern.City;
import com.hamming.halbo.datamodel.intern.Continent;
import com.hamming.halbo.datamodel.intern.HalboID;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ContinentFactory extends AbstractFactory{

    private List<Continent> continents;
    private static ContinentFactory instance;

    private ContinentFactory() {
        initialize();
    };

    private void initialize() {
        continents = new ArrayList<Continent>();
    }

    public static ContinentFactory getInstance() {
        if ( instance == null ) {
            instance = new ContinentFactory();
        }
        return instance;
    }

    public Continent createContinent(String name, String creatorID) {
        HalboID id = IDManager.getInstance().getNextID(HalboID.Prefix.CNT);
        Continent c = new Continent(id.toString(), name);
        c.setCreatorID(creatorID);
        c.setOwnerID(creatorID);
        continents.add(c);
        return c;
    }

    public List<Continent> getContinents() {
        return continents;
    }

    public String getContinentsAsString() {
        StringBuilder sb = new StringBuilder();
        for (Continent c : continents) {
            sb.append(c + "\n");
        }
        return sb.toString();
    }

    public boolean loadContinentsFromFile(String filename) {
        File file = new File(filename);
        return loadContinentsFromFile(file);
    }

    public boolean loadContinentsFromFile(File file) {
        boolean retval = true;
        List<Continent> loadContinents = (ArrayList<Continent>) loadFromFile(file);
        if (loadContinents != null) {
            continents = loadContinents;
        }
        return retval;
    }

    public boolean storeContinentsInFile(String filename) {
        return storeInFile(continents, filename);
    }

    public boolean storeContinentsInFile(File file) {
        return storeInFile(continents, file);
    }

}
