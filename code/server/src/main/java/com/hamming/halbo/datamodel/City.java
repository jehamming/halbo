package com.hamming.halbo.datamodel;

import java.util.List;

// A City is a collection of connected baseplates
//
// "The world is flat"
//
public class City extends BasicObject {

    private User mayor;

    public boolean addBaseplate(Baseplate b, long x, long y) {
        //TODO Implement
        return true;
    }

    public List<Baseplate> getAllBaseplates() {
        //TODO Implement
        return null;
    }

    public long getSizeX() {
        //TODO Implement
        return 0;
    }

    public long getSizeY() {
        //TODO Implement
        return 0;
    }


    //
    // STANDARD GETTERS AND SETTERS
    //



    public User getMayor() {
        return mayor;
    }

    public void setMayor(User mayor) {
        this.mayor = mayor;
    }
}
