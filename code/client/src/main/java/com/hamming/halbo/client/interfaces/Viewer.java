package com.hamming.halbo.client.interfaces;

public interface Viewer {

    public void setLocation(double x, double y, double z, double viewDirection );
    public void setLocation(String userId, String name, double x, double y, double z, double viewDirection );
    public void setBaseplate(String name, int width, int length);
    public void resetView();

    //TODO Load LDRAW
    // public void drawConstruction(String ldrawXML, x, y, z)

}
