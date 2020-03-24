package com.hamming.halbo.model;

public class SimpleCityGrid {

    public class GridPosition {
        public int row, col;
    }

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

    public GridPosition getPosition(String baseplateId) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (baseplates[row][col] != null && baseplates[row][col].toString().equals(baseplateId)) {
                    GridPosition pos = new GridPosition();
                    pos.row = row;
                    pos.col = col;
                    return pos;
                }
            }
        }
        return null;
    }

}
