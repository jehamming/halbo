package com.hamming.halbo.model.dto.intern;

import java.io.Serializable;
import java.util.Objects;

// The class to contain and ID for something.
// An HalboID is a combination of a Prefix and a number, for example :
// W1, w2 (World IDs)
// C1,C2 (City IDs)
// SYS1, SYS2, etc (System IDs)
// Etc.
public class HalboID implements Serializable  {

    public enum Prefix {
        WLD, // World
        CTY, // City
        BPL, // Baseplate
        BLK, // Block
        CTN, // Construction
        CNT, // Continent
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

    public static HalboID valueOf(Prefix prefix, long id) {
        final HalboID hid = new HalboID(prefix,id);
        return hid;
    }

    public static HalboID valueOf(String aString) {
        String strPrefix = aString.substring(0,3);
        Prefix prefix = Prefix.valueOf(strPrefix);
        String strID = aString.substring(3);
        Long id = Long.valueOf(strID);
        final HalboID hid = new HalboID(prefix,id);
        return hid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HalboID halboID = (HalboID) o;
        return id == halboID.id &&
                prefix == halboID.prefix;
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, id);
    }

    @Override
    public String toString() {
        return "" + prefix +  id;
    }
}
