package com.hamming.halbo.model;

import java.util.ArrayList;
import java.util.List;

public class SimpleCityGrid {

    public class GridPosition {
        public int row, col;
    }

    private int size = 0;
    private String[][] baseplateIds;

    public SimpleCityGrid(int size) {
        this.size = size;
        this.baseplateIds = new String[size][size];
    }

    public void setBaseplate(String baseplateId, int row, int col) {
        baseplateIds[row][col] = baseplateId;
    }

    public GridPosition getPosition(String baseplateId) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (baseplateIds[row][col] != null && baseplateIds[row][col].toString().equals(baseplateId)) {
                    GridPosition pos = new GridPosition();
                    pos.row = row;
                    pos.col = col;
                    return pos;
                }
            }
        }
        return null;
    }

    public List<String> getBaseplateIds() {
        List<String> retval = new ArrayList<String>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (baseplateIds[row][col] != null) {
                    retval.add(baseplateIds[row][col]);
                }
            }
        }
        return retval;
    }

}
