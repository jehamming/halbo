package com.hamming.halbo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CityGrid implements Serializable  {

    public class GridPosition {
        public int x, y;
    }

    public enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    private int size = 0;
    private City city;
    private Baseplate[][] baseplates;

    public CityGrid(City city, int size, Baseplate startBaseplate, int startX, int startY) {
        this.city = city;
        this.size = size;
        this.baseplates = new Baseplate[size][size];
        baseplates[startX][startY] = startBaseplate;
    }

    public void addBasePlate(Baseplate baseplate, Baseplate refBaseplate, Direction dir) throws CityGridException {
        GridPosition pos = findBaseplatePosition(refBaseplate);
        if (pos == null) throw new CityGridException("Referenced baseplate not found");
        switch (dir) {
            case NORTH:
                pos.y += 1;
                if (pos.y >= size) throw new CityGridException("Row out of bounds");
                break;
            case SOUTH:
                pos.y -= 1;
                if (pos.y < 0) throw new CityGridException("Row out of bounds");
                break;
            case EAST:
                pos.x += 1;
                if (pos.x >= size) throw new CityGridException("Col out of bounds");
                break;
            case WEST:
                pos.x -= 1;
                if (pos.x < 0) throw new CityGridException("Col out of bounds");
                break;
        }
        addBasePlate(baseplate, pos.x, pos.y);
    }

    public void addBasePlate(Baseplate baseplate, int x, int y) throws CityGridException {
        if (x > size || y > size) {
            String msg = "(" + x + ", " + y + ") is out of bounds, Gridsize=" + size;
            throw new CityGridException(msg);
        }
        if (baseplates[x][y] != null) {
            String msg = "(" + x + ", " + y + ") is occupied";
            throw new CityGridException(msg);
        }
        boolean connected = false;
        connected = connected || hasConnection(x, y, Direction.NORTH);
        connected = connected || hasConnection(x, y, Direction.SOUTH);
        connected = connected || hasConnection(x, y, Direction.EAST);
        connected = connected || hasConnection(x, y, Direction.WEST);
        if (!connected) throw new CityGridException("Baseplate needs to be connected to another baseplate");
        baseplates[x][y] = baseplate;
    }

    private boolean hasConnection(int x, int y, Direction dir) throws CityGridException {
        boolean hasConnection = false;
        try {
            switch (dir) {
                case NORTH:
                    if (x > 0 && baseplates[x - 1][y] != null) hasConnection = true;
                    break;
                case SOUTH:
                    if (x < size && baseplates[x + 1][y] != null) hasConnection = true;
                    break;
                case EAST:
                    if (y < size && baseplates[x][y + 1] != null) hasConnection = true;
                    break;
                case WEST:
                    if (y > 0 && baseplates[x][y - 1] != null) hasConnection = true;
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            String msg = "(" + x + ", " + y + ") is out of bounds, Gridsize=" + size;
            throw new CityGridException("hasConnection: " + msg);
        }
        return hasConnection;
    }

    public Baseplate getBaseplate(Baseplate refBaseplate, Direction dir) {
        Baseplate baseplate = null ;
        GridPosition position = findBaseplatePosition(refBaseplate);
        if (position != null) {
            try {
                baseplate = getBaseplate(position.x, position.y, dir);
            } catch (CityGridException e) {
                e.printStackTrace();
            }
        }
        return baseplate;
    }

    private GridPosition findBaseplatePosition(Baseplate refBaseplate) {
        GridPosition pos = null;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Baseplate b = baseplates[x][y];
                if (b != null && b.getId().equals(refBaseplate.getId())) {
                    pos = new GridPosition();
                    pos.x = x;
                    pos.y = y;
                    break;
                }
            }
        }
        return pos;
    }


    public Baseplate getBaseplate(int x, int y, Direction dir) throws CityGridException {
        Baseplate baseplate = null;
        if (x > size || y > size) {
            String msg = "(" + x + ", " + y + ") is out of bounds, Gridsize=" + size;
            throw new CityGridException(msg);
        }
        if (dir == null) {
            baseplate = baseplates[x][y];
        } else {
            switch (dir) {
                case NORTH:
                    if (y == size) throw new CityGridException("Row out of bounds");
                    baseplate = baseplates[x][y+1];
                    break;
                case SOUTH:
                    if (y <= 0) throw new CityGridException("Row out of bounds");
                    baseplate = baseplates[x][y-1];
                    break;
                case EAST:
                    if (x == size) throw new CityGridException("Col out of bounds");
                    baseplate = baseplates[x+1][y];
                    break;
                case WEST:
                    if (x <= 0) throw new CityGridException("Col out of bounds");
                    baseplate = baseplates[x-1][y];
                    break;
            }
        }
        return baseplate;
    }

    public List<CityBaseplate> getAllBaseplates() {
        List<CityBaseplate> cityBaseplates = new ArrayList<CityBaseplate>();
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (baseplates[x][y] != null) {
                    CityBaseplate cbp = new CityBaseplate(city, baseplates[x][y], x, y);
                    cityBaseplates.add(cbp);
                }
            }
        }
        return cityBaseplates;
    }




    public CityBaseplate getCitBaseplate(Baseplate baseplate) {
        CityBaseplate cityBaseplate = null;
        GridPosition position = findBaseplatePosition(baseplate);
        if ( position != null ) {
            cityBaseplate = new CityBaseplate(city, baseplate, position.x, position.y);
        }
        return cityBaseplate;
    }


    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        String str = "CityGrid :, size = " + size + "\n";
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (baseplates[x][y] != null) {
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
