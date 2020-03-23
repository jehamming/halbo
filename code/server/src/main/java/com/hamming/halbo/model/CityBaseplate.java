package com.hamming.halbo.model;

public class CityBaseplate {

    private final int row,col;
    private final Baseplate baseplate;

    public CityBaseplate(Baseplate baseplate, int row, int col) {
        this.baseplate = baseplate;
        this.row = row;
        this.col = col;
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
