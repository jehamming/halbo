package com.hamming.halbo.model;

public class SimpleCityGrid {

    private int size = 0;
    private String[][] baseplates;

    public SimpleCityGrid(int size) {
        this.size = size;
        this.baseplates = new String[size][size];
    }

    public void setBaseplate(String baseplateId, int row, int col) {
        baseplates[row][col] = baseplateId;
    }

    public String getBaseplate(int row, int col) {
        return baseplates[row][col];
    }

}
