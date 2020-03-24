package com.hamming.halbo;


import com.hamming.halbo.factories.ContinentFactory;
import com.hamming.halbo.factories.DTOFactory;
import com.hamming.halbo.factories.WorldFactory;
import com.hamming.halbo.game.*;
import com.hamming.halbo.game.action.GetUserAction;
import com.hamming.halbo.game.action.UserConnectedAction;
import com.hamming.halbo.game.action.UserDisconnectedAction;
import com.hamming.halbo.model.*;
import com.hamming.halbo.game.action.Action;
import com.hamming.halbo.model.dto.*;
import com.hamming.halbo.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection implements Runnable, GameStateListener {

    private User user;
    private UserLocation userLocation;
    private Socket socket;
    private BufferedReader in;
    private boolean running = true;
    private GameController gameController;
    private ProtocolHandler protocolHandler;
    private ClientSender clientSender;

    public ClientConnection(Socket s, BufferedReader in, PrintWriter out, GameController controller) {
        this.socket = s;
        this.in = in;
        this.gameController = controller;
        this.protocolHandler = new ProtocolHandler(controller, this);
        clientSender = new ClientSender(out);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(in);
        while (running && scanner.hasNextLine()) {
            try {
                String s = scanner.nextLine();
                if ( s != null ) {
                    handleInput(s);
                }
            } catch (Exception e) {
                running = false;
                System.out.println(this.getClass().getName() + ":" + "Error:" + e.getMessage());
            }
        }
        gameController.removeListener(this);
        if (user != null ) {
            gameController.userDisconnected(user);
        }
        try { socket.close(); } catch (IOException e) {}
        System.out.println(this.getClass().getName() + ":" + "Client Socket closed");
    }

    private void handleInput(String s) {
        Action cmd = protocolHandler.parseCommandString(s);
        if (cmd != null ) {
            gameController.addCommand(cmd);
        }
    }

    public void send(String s) {
        clientSender.enQueue(s);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if ( user != null ) {
            gameController.userConnected(user);
        }
    }

    public void sendFullGameState() {
        if (isLoggedIn()) {
            // World data
            sendWorldData();
            // Logged in Users;
            for ( User u: gameController.getGameState().getOnlineUsers()) {
                if (!u.getId().equals(user.getId())) {
                    sendUserDetails(u);
                    handleUserConnected(u);
                }
            }
            // UserLocations
            for (UserLocation loc : gameController.getGameState().getUserLocations().values()) {
                handleUserLocation(loc);
            }
        }
    }

    private void sendWorldData() {
        // Worlds
        for (World world: WorldFactory.getInstance().getWorlds()) {
            WorldDto worldDto = DTOFactory.getInstance().getWorldDto(world);
            send(Protocol.Command.GETWORLDS.ordinal() + StringUtils.delimiter + worldDto.toNetData());
            // Continents
            for (Continent continent : world.getContinents()) {
                ContinentDto continentDto = DTOFactory.getInstance().getContinentDto(world.getId().toString(), continent);
                send(Protocol.Command.GETCONTINENTS.ordinal() + StringUtils.delimiter + continentDto.toNetData());
                // Cities
                for (City city : continent.getCities()) {
                    CityDto cityDto = DTOFactory.getInstance().getCityDto(continent.getId().toString(), city);
                    send(Protocol.Command.GETCITIES.ordinal() + StringUtils.delimiter + cityDto.toNetData());
                }
            }
        }
    }

    public boolean isLoggedIn() {
        return user != null;
    }


    @Override
    public void newGameState(GameStateEvent event) {
        switch (event.getType()) {
            case USERCONNECTED:
                handleUserConnected((User) event.getObject());
                break;
            case USERDISCONNECTED:
                handleUserDisconnected((User) event.getObject());
                break;
            case USERLOCATION:
                handleUserLocation((UserLocation) event.getObject());
        }
    }

    private void handleUserLocation(UserLocation loc) {
        if (loc.getUser().equals(user)) {
            if ( userLocation == null || !userLocation.getCity().getId().equals(loc.getCity().getId())) {
                // New city! Send all the details
                sendCityDetails(loc.getCity());
            }
            // Location of this user!
            userLocation = loc;
        }
        if (userLocation != null && loc.getCity().equals(userLocation.getCity())) {
            UserLocationDto dto = DTOFactory.getInstance().getUserLocationDTO(loc);
            send(Protocol.Command.LOCATION.ordinal() + StringUtils.delimiter + dto.toNetData());
        }
    }

    public void sendCityDetails(City city) {
        city.getCityGrid().getAllBaseplates().forEach( cbp -> {
            CityBaseplateDto dto = DTOFactory.getInstance().getCityBaseplateDto(city.getId().toString(),cbp);
            send(Protocol.Command.CITYBASEPLATE.ordinal() + StringUtils.delimiter + dto.toNetData());
            // Send also the Baseplate
            BaseplateDto baseplateDto = DTOFactory.getInstance().getBaseplateDto(cbp.getBaseplate());
            send(Protocol.Command.GETBASEPLATE.ordinal() + StringUtils.delimiter + baseplateDto.toNetData());
        });
    }

    private void sendUserDetails(User u) {
        if (user != null && !user.equals(u)) {
            GetUserAction action = new GetUserAction(gameController, this);
            action.setUserId(u.getId().toString());
            gameController.addCommand( action );
        }
    }

    private void handleUserConnected(User u) {
        if (user != null && !user.equals(u)) {
            UserConnectedAction action = new UserConnectedAction(gameController, this);
            action.setUserId(u.getId().toString());
            gameController.addCommand( action );
        }
    }

    private void handleUserDisconnected(User u) {
        if (user != null && !user.equals(u)) {
            UserDisconnectedAction action = new UserDisconnectedAction(gameController, this);
            action.setUserId(u.getId().toString());
            gameController.addCommand( action );
        }
    }

    public void sendUserLocation() {
        userLocation = gameController.getGameState().getLocation(user);
        if (userLocation != null ) {
            UserLocationDto dto = DTOFactory.getInstance().getUserLocationDTO(userLocation);
            send(Protocol.Command.LOCATION.ordinal() + StringUtils.delimiter + dto.toNetData());
        }
    }

    public ProtocolHandler getProtocolHandler() {
        return protocolHandler;
    }
}
