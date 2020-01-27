package com.hamming.halbo.datamodel;

// The class to contain and ID for something.
// An HalboID is a combination of a Prefix and a number, for example :
// W1, w2 (World IDs)
// C1,C2 (City IDs)
// SYS1, SYS2, etc (System IDs)
// Etc.
public class HalboID {

    public enum Prefix {
        WLD, // World
        CTY, // City
        BPL, // Baseplate
        BLK, // Block
        CTN, // Construction
        USR, // User
        SYS  // System
    }

    private Prefix prefix;
    private final long id;

    public HalboID( Prefix prefix, long id) {
        this.prefix = prefix;
        this.id = id;
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "" + prefix +  id;
    }
}
