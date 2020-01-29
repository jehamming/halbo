package com.hamming.halbo.datamodel;

import java.awt.*;

public class Block extends BasicObject {

    private Color color = Color.WHITE;

    public Block(HalboID id) {
        super(id);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Block{" +
                "color=" + color +
                super.toString() +
                '}';
    }
}
