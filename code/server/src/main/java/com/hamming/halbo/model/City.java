package com.hamming.halbo.model;

import java.util.Objects;

// A City is a collection of connected baseplates
//
// "The world is flat"
//
public class City extends BasicObject {

    public static final int CITYSIZE = 50;

    private User mayor;
    private Baseplate teleportBaseplate;
    private CityGrid cityGrid;

    public City(HalboID id, String name, Baseplate teleportBaseplate) {
        super(id);
        setName(name);
        this.teleportBaseplate = teleportBaseplate;
        this.cityGrid = new CityGrid(CITYSIZE, teleportBaseplate,Math.round(CITYSIZE/2), Math.round(CITYSIZE/2));
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

    public CityGrid getCityGrid() {
        return cityGrid;
    }

    @Override
    public String toString() {
        return "City{" +
                "mayor=" + mayor +
                ", CityGrid=\n" + cityGrid + "\n" +
                super.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(getId(), city.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(mayor);
    }
}
