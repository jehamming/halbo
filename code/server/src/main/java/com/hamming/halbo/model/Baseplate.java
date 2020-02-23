package com.hamming.halbo.model;

import java.awt.*;

// A baseplate is like a lego baseplate. There are several types of plates, with grass or with road parts, etc.
public class Baseplate extends BasicObject   {

    private BaseplateType type ;
    private int width;
    private int length;
    private double spawnPointX;
    private double spawnPointY;
    private double spawnPointZ;

    public Baseplate(HalboID id) {
        super(id);
        spawnPointX = 0;
        spawnPointY = 2.5;
        spawnPointX = 0;
        width = 100;
        length = 100;
    }

    public double getSpawnPointX() {
        return spawnPointX;
    }

    public void setSpawnPointX(double spawnPointX) {
        this.spawnPointX = spawnPointX;
    }

    public double getSpawnPointY() {
        return spawnPointY;
    }

    public void setSpawnPointY(double spawnPointY) {
        this.spawnPointY = spawnPointY;
    }

    public double getSpawnPointZ() {
        return spawnPointZ;
    }

    public void setSpawnPointZ(double spawnPointZ) {
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
