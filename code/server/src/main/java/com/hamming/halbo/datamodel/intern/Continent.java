package com.hamming.halbo.datamodel.intern;

// A Continent exists of a set of cities
// TODO How to position or connect cities in an continent?
public class Continent extends BasicObject{

    private User senator;

    public Continent(HalboID id) {
        super(id);
    }

    public boolean addCity( City c) {
        //TODO Implement
        return true;
    }


    public User getSenator() {
        return senator;
    }

    public void setSenator(User senator) {
        this.senator = senator;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "senator=" + senator +
                super.toString() +
                '}';
    }
}
