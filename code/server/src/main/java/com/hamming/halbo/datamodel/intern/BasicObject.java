package com.hamming.halbo.datamodel.intern;

import java.io.Serializable;

// Everything extends a BasicObject
public class BasicObject implements Serializable {

    private String id;
    private String creatorID;
    private String ownerID;
    private String name;

    public BasicObject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  ", id=" + id +
                ", creator=" + creatorID +
                ", owner=" + ownerID +
                ", name='" + name;
    }
}
