package com.hamming.halbo.client;


import javax.swing.*;
import java.awt.*;

public class WorldOpenGLWindow extends JFrame {

    public WorldOpenGLWindow() {
        initWindow();
    }

    public void initWindow() {
        setTitle("3D View");
        setPreferredSize(new Dimension(700,600));
        setLocation(500,50);
        pack();
        setVisible(true);
    }
}
