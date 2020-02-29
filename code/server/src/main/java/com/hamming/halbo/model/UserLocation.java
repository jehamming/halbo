package com.hamming.halbo.model;

public class UserLocation extends BasicObject{

    private User user;
    private World world;
    private Continent continent;
    private City city;
    private Baseplate baseplate;
    private double x;
    private double y;
    private double z;
    private double viewAngle;

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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getViewAngle() {
        return viewAngle;
    }

    public void setViewAngle(double viewAngle) {
        this.viewAngle = viewAngle;
    }
}
