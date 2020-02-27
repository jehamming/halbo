package com.hamming.halbo.model;

// A City is a collection of connected baseplates
//
// "The world is flat"
//
public class City extends BasicObject {

    private User mayor;
    private Baseplate teleportBaseplate;

    public City(HalboID id, String name, Baseplate teleportBaseplate) {
        super(id);
        setName(name);
        this.teleportBaseplate = teleportBaseplate;
    }


    public long getSizeX() {
        //TODO Implement
        return 0;
    }

    public long getSizeY() {
        //TODO Implement
        return 0;
    }

    public User getMayor() {
        return mayor;
    }

    public void setMayor(User mayor) {
        this.mayor = mayor;
    }

    public Baseplate getTeleportBaseplate() {
        return teleportBaseplate;
    }

    @Override
    public String toString() {
        return "City{" +
                "mayor=" + mayor +
                super.toString() +
                '}';
    }

}
