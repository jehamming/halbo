package com.hamming.halbo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The Main World class. In this world users can work with each other
// A world consists of Continents, which contain cities.
//
public class World extends BasicObject {

    private List<Continent> continents;

    public World(HalboID id) {
        super(id);
        continents = new ArrayList<Continent>();
    }

    public boolean addContinent( Continent c ) {
        // TODO How to place a continent in the world ??
        continents.add(c);
        return true;
    }

    public List<Continent> getContinents() {
        return continents;
    }
}
