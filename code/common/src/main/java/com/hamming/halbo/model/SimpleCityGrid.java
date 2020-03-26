package com.hamming.halbo.model;

import java.util.ArrayList;
import java.util.List;

public class SimpleCityGrid {

    public class GridPosition {
        public int x, y;
    }

    private int size = 0;
    private String[][] baseplateIds;

    public SimpleCityGrid(int size) {
        this.size = size;
        this.baseplateIds = new String[size][size];
    }

    public void setBaseplate(String baseplateId, int x, int y) {
        baseplateIds[x][y] = baseplateId;
    }

    public GridPosition getPosition(String baseplateId) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (baseplateIds[x][y] != null && baseplateIds[x][y].toString().equals(baseplateId)) {
                    GridPosition pos = new GridPosition();
                    pos.x = x;
                    pos.y = y;
                    return pos;
                }
            }
        }
        return null;
    }

    public List<String> getBaseplateIds() {
        List<String> retval = new ArrayList<String>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (baseplateIds[x][y] != null) {
                    retval.add(baseplateIds[x][y]);
                }
            }
        }
        return retval;
    }

    public int getSize() {
        return size;
    }
}
