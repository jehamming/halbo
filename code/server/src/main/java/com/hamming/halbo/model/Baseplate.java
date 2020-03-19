package com.hamming.halbo.model;

import java.awt.*;

// A baseplate is like a lego baseplate. There are several types of plates, with grass or with road parts, etc.
public class Baseplate extends BasicObject   {

    private BaseplateType type ;
    private int width;
    private int length;
    private float spawnPointX;
    private float spawnPointY;
    private float spawnPointZ;

    public Baseplate(HalboID id) {
        super(id);
        spawnPointX = 25;
        spawnPointY = 1;
        spawnPointZ = -25;
        width = 50;
        length = 50;
    }

    public float getSpawnPointX() {
        return spawnPointX;
    }

    public void setSpawnPointX(float spawnPointX) {
        this.spawnPointX = spawnPointX;
    }

    public float getSpawnPointY() {
        return spawnPointY;
    }

    public void setSpawnPointY(float spawnPointY) {
        this.spawnPointY = spawnPointY;
    }

    public float getSpawnPointZ() {
        return spawnPointZ;
    }

    public void setSpawnPointZ(float spawnPointZ) {
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
