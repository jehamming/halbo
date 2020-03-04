package com.hamming.halbo.client;

import com.hamming.halbo.client.controllers.*;
import com.hamming.halbo.client.engine.GLViewer;
import com.hamming.halbo.client.interfaces.Viewer;
import com.hamming.halbo.client.viewer.DummyVirtualDisplay;

public class ClientApplication {

    private ToolsWindow toolsWindow;
    private BaseWindow baseWindow;

    //Controllers
    private ConnectionController connectionController;
    private UserController userController;
    private WorldController worldController;
    private ContinentController continentController;
    private CityController cityController;
    private MoveController moveController;
    private ViewController viewController;

    public void initControllers() {
        connectionController = new ConnectionController();
        userController = new UserController(connectionController);
        worldController = new WorldController(connectionController);
        continentController = new ContinentController(connectionController);
        cityController = new CityController(connectionController);
        moveController = new MoveController(connectionController,userController, worldController, continentController, cityController);
    }

    private void createAndShowGUI() {
        initControllers();
        baseWindow = new BaseWindow(connectionController,
                userController,
                worldController,
                continentController,
                cityController,
                moveController);
        toolsWindow = new ToolsWindow(userController, moveController);

        // TODO Replace with OpenGL Window
        Viewer display = new GLViewer();
        viewController = new ViewController(display, connectionController,  userController,  worldController, continentController,  cityController,  moveController);
    }

    public static void main(String[] args) {
        final ClientApplication application = new ClientApplication();
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                application.createAndShowGUI();
            }
        });
    }


}
