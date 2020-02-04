package com.hamming.halbo.game.cmd;

import com.hamming.halbo.HALBOClient;
import com.hamming.halbo.datamodel.intern.User;
import com.hamming.halbo.datamodel.intern.World;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.factories.WorldFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.util.StringUtils;

public class GetWorldsAction implements Action {
    private GameController controller;
    private HALBOClient client;

    private String username;
    private String password;

    public GetWorldsAction(GameController controller, HALBOClient client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        for (World w : WorldFactory.getInstance().getWorlds() ) {
            String txtWorld = w.getId()
                    + StringUtils.delimiter + w.getId()
                    + StringUtils.delimiter + w.getName()
                    + StringUtils.delimiter + w.getCreatorID()
                    + StringUtils.delimiter + w.getOwnerID() ;
            client.send(Protocol.Command.GETWORLDS.ordinal() + StringUtils.delimiter + txtWorld);
        }
    }

    @Override
    public void setValues(String... values) {
    }
}
