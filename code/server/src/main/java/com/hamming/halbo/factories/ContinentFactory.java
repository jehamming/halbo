package com.hamming.halbo.factories;

import com.hamming.halbo.IDManager;
import com.hamming.halbo.datamodel.intern.City;
import com.hamming.halbo.datamodel.intern.Continent;
import com.hamming.halbo.datamodel.intern.HalboID;
import com.hamming.halbo.datamodel.intern.User;

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





}
