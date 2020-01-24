package com.hamming.halbo.datamodel;

// This is a specific block : the rectangular block.
// it has a width and length. a squareblock is also an RectangleBlock
public class RectangleBlock extends Block {

    private int width;
    private int length;


    public RectangleBlock( int width, int length) {
        this.width = width;
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
}
