package com.hamming.halbo.datamodel.intern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The Main World class. In this world users can work with each other
// A world consists of Continents, which contain cities.
//
public class World extends BasicObject {

    private Map<String, Continent> continents;

    public World(String id) {
        super(id);
        continents = new HashMap<String, Continent>();
    }

    public boolean addContinent( Continent c ) {
        // TODO How to place a continent in the world ??
        continents.put(c.getName(), c);
        return true;
    }




}
