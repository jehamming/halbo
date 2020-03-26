package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.factories.DTOFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.UserLocation;
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
        UserLocation location = controller.handleTeleportRequest(userId, worldId, continentId, cityId);

        if ( location != null )  {
            UserLocationDto dto = DTOFactory.getInstance().getUserLocationDTO(location);
            client.send(Protocol.Command.TELEPORT.ordinal() + StringUtils.delimiter + Protocol.SUCCESS + StringUtils.delimiter + dto.toNetData());
        } else {
            client.send(Protocol.Command.TELEPORT.ordinal() + StringUtils.delimiter + Protocol.FAILED + StringUtils.delimiter + "Teleport Failed!");
        }

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
