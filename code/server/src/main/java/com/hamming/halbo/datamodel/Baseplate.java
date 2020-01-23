package com.hamming.halbo.datamodel;

import java.util.List;

// A baseplate is like a lego baseplate. There are several types of plates, with grass or with road parts, etc.
public class Baseplate extends BasicObject {

    private BaseplateType type ;


    public boolean addConstruction(Construction c) {
        // TODO Implement - How to position Contructions ?
        return true;
    }

    public List<Construction> getAllContructions() {
        // TODO Implement
        return null;
    }
}
