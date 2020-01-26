package com.hamming.halbo.datamodel;

import java.util.ArrayList;
import java.util.List;

// This factory gives you all methods to get and deal with blocks
public class BlockFactory {

    private static BlockFactory instance;

    private BlockFactory() {
        initialize();
    };

    private void initialize() {
        // TODO Implement reading all the types from a source like XML?
    }

    public static BlockFactory getInstance() {
        if ( instance == null ) {
            instance = new BlockFactory();
        }
        return instance;
    }


    public List<Block> getAllBlocks() {
        ArrayList<Block> list = new ArrayList<Block>();
        // Initialize with some basic blocks:
        list.add( new RectangleBlock(1,1)); // 1x1 square
        list.add( new RectangleBlock(2,2)); // 2x2 square
        list.add( new RectangleBlock(1,2));
        list.add( new RectangleBlock(1,3));
        return list;
    }

}
