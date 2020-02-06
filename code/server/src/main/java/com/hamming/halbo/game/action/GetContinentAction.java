package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.factories.DTOFactory;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.factories.WorldFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.Continent;
import com.hamming.halbo.model.User;
import com.hamming.halbo.model.World;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.util.StringUtils;

public class GetContinentAction implements Action {
    private GameController controller;
    private ClientConnection client;

    private String worldId;

    public GetContinentAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        World w = WorldFactory.getInstance().findWorldById(worldId);
        if ( w != null ) {
            for ( Continent continent : w.getContinents() ) {
                ContinentDto dto = DTOFactory.getInstance().getContinentDto(continent);
                client.send(Protocol.Command.GETCONTINENTS.ordinal() + StringUtils.delimiter + dto.toNetData());
            }
        } else {
            client.send(Protocol.Command.GETCONTINENTS.ordinal() + StringUtils.delimiter + Protocol.FAILED);
        }
    }

    @Override
    public void setValues(String... values) {
        if (values.length == 1 ) {
            worldId = values[0];
        } else {
            System.out.println("Error at "+getClass().getName()+", size not ok of: "+values);
        }
    }
}
