package com.hamming.halbo.datamodel;

import java.io.Serializable;
import java.util.Map;

public class World implements Serializable {
    private String id;
    private Map<String, String> continents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getContinents() {
        return continents;
    }

    public void setContinents(Map<String, String> continents) {
        this.continents = continents;
    }
}
