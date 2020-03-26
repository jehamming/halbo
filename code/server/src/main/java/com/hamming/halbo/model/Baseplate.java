package com.hamming.halbo.model;

import java.awt.*;

// A baseplate is like a lego baseplate. There are several types of plates, with grass or with road parts, etc.
public class Baseplate extends BasicObject   {

    private BaseplateType type ;
    private int size;
    private float spawnPointX;
    private float spawnPointY;
    private float spawnPointZ;

    public Baseplate(HalboID id) {
        super(id);
        spawnPointX = 0;
        spawnPointY = 0;
        spawnPointZ = 0;
        size = 80;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Baseplate{" +
                "type=" + type +
                ", size=" + size +
                ", spawnPointX=" + spawnPointX +
                ", spawnPointY=" + spawnPointY +
                ", spawnPointZ=" + spawnPointZ +
                '}';
    }
}
