package com.hamming.halbo.client.interfaces;


import com.wijlen.ter.halbo.lwjgl.entities.Camera;

public interface Viewer {

    public void setLocation(String userId, float x, float y, float z, float pitch, float yaw );
    public void followPlayer(String userId);
    public void setBaseplate(String baseplateId, String name, int width, int length);
    public String getCurrentBaseplateId();
    public void resetView();

    public void addPlayer(String userId, String name);
    public void removePlayer(String userId);

    public boolean getForward();
    public boolean getBack();
    public boolean getLeft();
    public boolean getRight();
}
