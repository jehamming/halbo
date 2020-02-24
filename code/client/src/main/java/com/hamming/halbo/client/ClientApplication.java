package com.hamming.halbo.client;

import com.hamming.halbo.client.controllers.ConnectionController;
import com.hamming.halbo.client.controllers.DataController;
import com.hamming.halbo.game.Protocol;

public class ClientApplication {

    private ToolsWindow toolsWindow;
    private BaseWindow clientWindow;

    //Controllers
    private ConnectionController connectionController;
    private DataController dataController;

    public void initControllers() {
        connectionController = new ConnectionController();
        dataController = new DataController(connectionController);
    }

    private void createAndShowGUI() {
        initControllers();
       clientWindow = new BaseWindow(connectionController, dataController);
       //toolsWindow = new ToolsWindow();
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




    //TODO
/*
            client.registerReceiver(Protocol.Command.LOGIN, loginPanel);
        client.registerReceiver(Protocol.Command.GETWORLDS, worldsPanel);
        client.registerReceiver(Protocol.Command.GETCONTINENTS, continentsPanel);
        client.registerReceiver(Protocol.Command.GETCITIES, citiesPanel);
        client.registerReceiver(Protocol.Command.GETBASEPLATES, baseplatesPanel);
        client.registerReceiver(Protocol.Command.GETBASEPLATE, this);
    registerReceiver(Protocol.Command.USERCONNECTED, usersPanel);
        clientWindow.getClient().registerReceiver(Protocol.Command.USERDISCONNECTED, usersPanel);
        clientWindow.getClient().registerReceiver(Protocol.Command.MOVE, movementPanel);
        clientWindow.getClient().registerReceiver(Protocol.Command.LOCATION, movementPanel);
        clientWindow.getClient().registerReceiver(Protocol.Command.MOVE, movementPanel);
        clientWindow.getClient().registerReceiver(Protocol.Command.TELEPORT, movementPanel);



    public void teleportRequest() {
        WorldDto world = worldsPanel.getSelectedWorld();
        ContinentDto continent = continentsPanel.getSelectedContinent();
        CityDto city = citiesPanel.getSelectedCity();
        BaseplateDto baseplate = baseplatesPanel.getSelectedBaseplate();
        if (user != null && world != null && continent != null && city != null && baseplate != null) {
            String cmd = protocolHandler.getTeleportCommand(user.getId(), world, continent, city, baseplate);
            send(cmd);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a World, Continent, City and Baseplate to teleport to!");
        }
    }

        public void setUserLocation(UserLocationDto userLocation) {
        BaseplateDto bp = findBaseplateById(userLocation.getBaseplateId());
        if (  bp == null ) {
            String s = protocolHandler.getGetBaseplateCommand(userLocation.getBaseplateId());
            client.send(s);
        }
        this.userLocation = userLocation;
    //    viewController.setLocation(userLocation);
    }

    private BaseplateDto findBaseplateById(String baseplateId) {
        BaseplateDto found = null;
        for (BaseplateDto bp: baseplates) {
            if (bp.getId().equals( baseplateId)) {
                found = bp;
                break;
            }
        }
        return found;
    }





*/


}
