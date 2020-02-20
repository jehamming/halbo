package com.hamming.halbo.model;

import java.awt.*;

// A baseplate is like a lego baseplate. There are several types of plates, with grass or with road parts, etc.
public class Baseplate extends BasicObject   {

    private BaseplateType type ;
    private int width;
    private int length;
    private int spawnPointX;
    private int spawnPointY;
    private int spawnPointZ;

    public Baseplate(HalboID id) {
        super(id);
        spawnPointX = 0;
        spawnPointY = 0;
        spawnPointX = 0;
        width = 100;
        length = 100;
    }

    public int getSpawnPointX() {
        return spawnPointX;
    }

    public void setSpawnPointX(int spawnPointX) {
        this.spawnPointX = spawnPointX;
    }

    public int getSpawnPointY() {
        return spawnPointY;
    }

    public void setSpawnPointY(int spawnPointY) {
        this.spawnPointY = spawnPointY;
    }

    public int getSpawnPointZ() {
        return spawnPointZ;
    }

    public void setSpawnPointZ(int spawnPointZ) {
        this.spawnPointZ = spawnPointZ;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Baseplate{" +
                "type=" + type +
                ", width=" + width +
                ", length=" + length +
                ", spawnPointX=" + spawnPointX +
                ", spawnPointY=" + spawnPointY +
                ", spawnPointZ=" + spawnPointZ +
                '}';
    }
}
