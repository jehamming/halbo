package com.hamming.halbo.datamodel;

import java.io.Serializable;

// Everything extends a BasicObject
public class BasicObject implements Serializable {

    private HalboID id;
    private User creator;
    private User owner;
    private String name;

    public BasicObject(HalboID id) {
        this.id = id;
    }

    public HalboID getId() {
        return id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
                ", creator=" + creator +
                ", owner=" + owner +
                ", name='" + name;
    }
}
