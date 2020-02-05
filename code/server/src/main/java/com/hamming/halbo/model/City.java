package com.hamming.halbo.model.dto.intern;

import java.util.List;

// A City is a collection of connected baseplates
//
// "The world is flat"
//
public class City extends BasicObject {

    private String mayorID;
    private List<String> baseplates;

    public City(String id, String name) {
        super(id);
        setName(name);
    }

    public boolean addBaseplate(String baseplateID, long x, long y) {
        baseplates.add(baseplateID);
        return true;
    }

    public long getSizeX() {
        //TODO Implement
        return 0;
    }

    public long getSizeY() {
        //TODO Implement
        return 0;
    }

    public String getMayorID() {
        return mayorID;
    }

    public List<String> getBaseplates() {
        return baseplates;
    }

    @Override
    public String toString() {
        return "City{" +
                "mayor=" + mayorID +
                super.toString() +
                '}';
    }
}
