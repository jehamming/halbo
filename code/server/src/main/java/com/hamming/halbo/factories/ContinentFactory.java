package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.model.Continent;
import com.hamming.halbo.model.HalboID;
import com.hamming.halbo.model.User;

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

    public Continent createContinent(String name, User creator) {
        HalboID id = IDManager.getInstance().getNextID(HalboID.Prefix.CNT);
        Continent c = new Continent(id, name);
        c.setCreator(creator);
        c.setOwner(creator);
        continents.add(c);
        return c;
    }

    public Continent findContinentById(String strId) {
        Continent found = null;
        HalboID id = HalboID.valueOf(strId);
        for (Continent c: continents) {
            if ( c.getId().equals(id)) {
                found = c;
                break;
            }
        }
        return found;
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
            Long highestID = getHighestID(continents);
            IDManager.getInstance().setLastAddedID(HalboID.Prefix.CNT, highestID);
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
