package com.hamming.halbo.model;

public class UserLocation extends BasicObject{

    private User user;
    private World world;
    private Continent continent;
    private City city;
    private Baseplate baseplate;
    private int x;
    private int y;
    private int z;

    public UserLocation(HalboID id) {
        super(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Baseplate getBaseplate() {
        return baseplate;
    }

    public void setBaseplate(Baseplate baseplate) {
        this.baseplate = baseplate;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
