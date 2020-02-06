package com.hamming.halbo.model;

import java.util.List;

// A City is a collection of connected baseplates
//
// "The world is flat"
//
public class City extends BasicObject {

    private User mayor;
    private List<Baseplate> baseplates;

    public City(HalboID id, String name) {
        super(id);
        setName(name);
    }

    public boolean addBaseplate(Baseplate baseplate, long x, long y) {
        baseplates.add(baseplate);
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

    public List<Baseplate> getBaseplates() {
        return baseplates;
    }


    public User getMayor() {
        return mayor;
    }

    public void setMayor(User mayor) {
        this.mayor = mayor;
    }


    @Override
    public String toString() {
        return "City{" +
                "mayor=" + mayor +
                super.toString() +
                '}';
    }

}
