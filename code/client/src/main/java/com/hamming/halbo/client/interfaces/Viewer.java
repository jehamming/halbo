package com.hamming.halbo.client.interfaces;

public interface Viewer {

    public void setLocation(float x, float y, float z, float pitch, float yaw );
    public void setLocation(String userId, String name, float x, float y, float z, float pitch, float yaw );
    public void setBaseplate(String name, int width, int length);
    public void resetView();

    public void addPlayer(String userId, String name);
    public void removePlayer(String userId);

    //TODO Load LDRAW
    // public void drawConstruction(String ldrawXML, x, y, z)

}
