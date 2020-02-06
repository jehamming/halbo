package com.hamming.halbo.model;

// This is a specific block : the rectangular block.
// it has a width and length. a squareblock is also an RectangleBlock
public class RectangleBlock extends Block {

    private int width;
    private int length;


    public RectangleBlock( HalboID id,  int width, int length) {
        super(id);
        this.width = width;
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "RectangleBlock{" +
                "width=" + width +
                ", length=" + length +
                super.toString() +
                '}';
    }
}
