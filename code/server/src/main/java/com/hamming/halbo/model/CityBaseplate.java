package com.hamming.halbo.model;

public class CityBaseplate {

    private final int row,col;
    private City city;
    private final Baseplate baseplate;

    public CityBaseplate(City city, Baseplate baseplate, int row, int col) {
        this.baseplate = baseplate;
        this.row = row;
        this.col = col;
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Baseplate getBaseplate() {
        return baseplate;
    }
}
