package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.factories.DTOFactory;
import com.hamming.halbo.model.World;
import com.hamming.halbo.factories.WorldFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.dto.WorldDto;
import com.hamming.halbo.util.StringUtils;

public class GetWorldsAction implements Action {
    private GameController controller;
    private ClientConnection client;

    private String username;
    private String password;

    public GetWorldsAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        for (World w : WorldFactory.getInstance().getWorlds() ) {
            WorldDto dto = DTOFactory.getInstance().getWorldDto(w);
            client.send(Protocol.Command.GETWORLDS.ordinal() + StringUtils.delimiter + dto.toNetData());
        }
    }

    @Override
    public void setValues(String... values) {
    }
}
