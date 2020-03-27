package com.hamming.halbo.client;

import com.hamming.halbo.client.controllers.*;
import com.hamming.halbo.client.engine.GLViewer;

public class HALBOClientApplication {

    private ToolsWindow toolsWindow;
    private BaseWindow baseWindow;
    private ViewController viewController;
    private GLViewer viewer;

    //Controllers
    private Controllers controllers;

    public void initControllers() {
        controllers = new Controllers();
        controllers.setConnectionController(new ConnectionController());
        controllers.setUserController( new UserController(controllers.getConnectionController()));
        controllers.setContinentController( new ContinentController(controllers.getConnectionController()));
        controllers.setCityController(new CityController(controllers.getConnectionController()));
        controllers.setMoveController(new MoveController(controllers));
        controllers.setHalboClientController(new HALBOClientController(this, controllers));
        controllers.setWorldController( new WorldController(controllers.getHalboClientController(), controllers.getConnectionController()));
    }

    private void createAndShowGUI() {
        initControllers();
        baseWindow = new BaseWindow(controllers);
        toolsWindow = new ToolsWindow(controllers);
        viewer = new GLViewer(controllers.getMoveController());
        viewController = new ViewController(viewer, controllers);

    }

    public static void main(String[] args) {
        final HALBOClientApplication application = new HALBOClientApplication();
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                application.createAndShowGUI();
            }
        });
    }


    public BaseWindow getBaseWindow() {
        return baseWindow;
    }

    public ToolsWindow getToolsWindow() {
        return toolsWindow;
    }
}
