package com.hamming.halbo.model;

import java.awt.geom.Point2D;

public class CityGrid {
    public class GridPosition {
        public int row, col;
    }

    public enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    private int size = 0;
    private Baseplate[][] baseplates;

    public CityGrid(int size, Baseplate startBaseplate, int startRow, int startCol) {
        this.size = size;
        this.baseplates = new Baseplate[size][size];
        baseplates[startRow][startCol] = startBaseplate;
    }

    public void addBasePlate(Baseplate baseplate, Baseplate refBaseplate, Direction dir) throws CityGridException {
        GridPosition pos = findBaseplatePosition(refBaseplate);
        if (pos == null) throw new CityGridException("Referenced baseplate not found");
        switch (dir) {
            case NORTH:
                pos.row -= 1;
                if (pos.row <= 0) throw new CityGridException("Row out of bounds < 0 ");
                break;
            case SOUTH:
                pos.row += 1;
                if (pos.row >= size) throw new CityGridException("Row out of bounds > " + size);
                break;
            case EAST:
                pos.col += 1;
                if (pos.col >= size) throw new CityGridException("Col out of bounds > " + size);
                break;
            case WEST:
                pos.col -= 1;
                if (pos.col <= 0) throw new CityGridException("Col out of bounds < 0 ");
                break;
        }
        addBasePlate(baseplate, pos.row, pos.col);
    }

    public void addBasePlate(Baseplate baseplate, int row, int col) throws CityGridException {
        if (row > size || col > size) {
            String msg = "(" + row + ", " + col + ") is out of bounds, Gridsize=" + size;
            throw new CityGridException(msg);
        }
        if (baseplates[row][col] != null) {
            String msg = "(" + row + ", " + col + ") is occupied";
            throw new CityGridException(msg);
        }
        boolean connected = false;
        connected = connected || hasConnection(row, col, Direction.NORTH);
        connected = connected || hasConnection(row, col, Direction.SOUTH);
        connected = connected || hasConnection(row, col, Direction.EAST);
        connected = connected || hasConnection(row, col, Direction.WEST);
        if (!connected) throw new CityGridException("Baseplate needs to be connected to another baseplate");
        baseplates[row][col] = baseplate;
    }

    private boolean hasConnection(int row, int col, Direction dir) {
        boolean hasConnection = false;
        switch (dir) {
            case NORTH:
                if (row > 0 && baseplates[row - 1][col] != null) hasConnection = true;
                break;
            case SOUTH:
                if (row < size && baseplates[row + 1][col] != null) hasConnection = true;
                break;
            case EAST:
                if (col < size && baseplates[row][col + 1] != null) hasConnection = true;
                break;
            case WEST:
                if (col > 0 && baseplates[row][col - 1] != null) hasConnection = true;
                break;
        }
        return hasConnection;
    }

    public Baseplate getBaseplate(Baseplate refBaseplate, Direction dir) throws CityGridException {
        GridPosition position = findBaseplatePosition(refBaseplate);
        if (position == null) throw new CityGridException("Referenced baseplate not found");
        return getBaseplate(position.row, position.col, dir);
    }

    private GridPosition findBaseplatePosition(Baseplate refBaseplate) {
        GridPosition pos = null;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Baseplate b = baseplates[row][col];
                if (b != null && b.getId().equals(refBaseplate.getId())) {
                    pos = new GridPosition();
                    pos.row = row;
                    pos.col = col;
                    break;
                }
            }
        }
        return pos;
    }


    public Baseplate getBaseplate(int row, int col, Direction dir) throws CityGridException {
        Baseplate baseplate = null;
        if (row > size || col > size) {
            String msg = "(" + row + ", " + col + ") is out of bounds, Gridsize=" + size;
            throw new CityGridException(msg);
        }
        if (dir == null) {
            baseplate = baseplates[row][col];
        } else {
            switch (dir) {
                case NORTH:
                    if (row == 0) throw new CityGridException("Row out of bounds < 0 ");
                    baseplate = baseplates[row - 1][col];
                    break;
                case SOUTH:
                    if (row == size) throw new CityGridException("Row out of bounds > " + size);
                    baseplate = baseplates[row + 1][col];
                    break;
                case EAST:
                    if (col == size) throw new CityGridException("Col out of bounds > " + size);
                    baseplate = baseplates[row][col + 1];
                    break;
                case WEST:
                    if (col == 0) throw new CityGridException("Col out of bounds < 0 ");
                    baseplate = baseplates[row][col - 1];
                    break;
            }
        }
        return baseplate;
    }


    @Override
    public String toString() {
        String str = "CityGrid :, size = " + size + "\n";
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (baseplates[row][col] != null) {
                    str = str.concat(" o ");
                } else {
                    str = str.concat(" . ");
                }
            }
            str = str.concat("\n");
        }
        return str;
    }
}
