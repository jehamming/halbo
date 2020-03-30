package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.IDManager;
import com.hamming.halbo.factories.*;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.GameStateEvent;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.*;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.model.dto.UserLocationDto;
import com.hamming.halbo.util.StringUtils;

public class TeleportAction implements Action {
    private GameController controller;
    private ClientConnection client;

    private String userId;
    private String worldId;
    private String continentId;
    private String cityId;
    private String baseplateId;


    public TeleportAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        UserLocation location = handleTeleportRequest(userId, worldId, continentId, cityId);

        if ( location != null )  {
            UserLocationDto dto = DTOFactory.getInstance().getUserLocationDTO(location);
            client.send(Protocol.Command.TELEPORT,Protocol.SUCCESS + StringUtils.delimiter + dto.toNetData());
        } else {
            client.send(Protocol.Command.TELEPORT,Protocol.FAILED + StringUtils.delimiter + "Teleport Failed!");
        }

    }

    public UserLocation handleTeleportRequest(String userId, String worldId, String continentId, String cityId) {
        User u = UserFactory.getInstance().findUserById(userId);
        World w = WorldFactory.getInstance().findWorldById(worldId);
        ;
        Continent c = ContinentFactory.getInstance().findContinentById(continentId);
        City ct = CityFactory.getInstance().findCityByID(cityId);
        Baseplate b = ct.getTeleportBaseplate();
        UserLocation loc = null;
        if (u != null && w != null && c != null && ct != null && b != null) {
            loc = controller.getGameState().getLocation(u);
            if (loc == null) {
                loc = new UserLocation(IDManager.getInstance().getNextID(HalboID.Prefix.LOC));
                loc.setUser(u);
            }
            loc.setWorld(w);
            loc.setContinent(c);
            loc.setCity(ct);
            loc.setBaseplate(b);
            loc.setX(b.getSpawnPointX());
            loc.setY(b.getSpawnPointY());
            loc.setZ(b.getSpawnPointZ());
            loc.setPitch(0);
            loc.setYaw(0);
            controller.userTeleported(u, loc);
        }
        return loc;
    }



    @Override
    public void setValues(String... values) {
        if (values.length == 4 ) {
            userId = values[0];
            worldId = values[1];
            continentId = values[2];
            cityId = values[3];
        } else {
            System.out.println(this.getClass().getName() + ":" + "Error at "+getClass().getName()+", size not ok of: "+values);
            Exception e = new Exception();
            e.printStackTrace();
        }
    }
}
