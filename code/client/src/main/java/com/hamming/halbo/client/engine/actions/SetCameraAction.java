package com.hamming.halbo.client.engine.actions;

import com.hamming.halbo.client.engine.GLViewer;
import com.hamming.halbo.client.interfaces.Viewer;
import org.lwjgl.util.vector.Vector3f;

public class SetCameraAction implements Action {

    private Viewer viewer;
    private float x,y,z,pitch,yaw;

    public SetCameraAction(Viewer viewer, float x, float y, float z, float pitch, float yaw) {
        this.viewer = viewer;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    @Override
    public void execute() {
        viewer.getCamera().setPosition(new Vector3f(x,y,z));
        viewer.getCamera().setPitch(pitch);
        viewer.getCamera().setYaw(yaw);
    }
}
