package com.hamming.halbo.game.cmd;

import com.hamming.halbo.HALBOClient;
import com.hamming.halbo.datamodel.intern.User;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.util.StringUtils;

public class LoginCommand implements ProtocolCommand {
    private GameController controller;
    private HALBOClient client;

    private String username;
    private String password;

    public LoginCommand(GameController controller, HALBOClient client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        User u = UserFactory.getInstance().validateUser(username,password);
        if ( u != null ) {
            client.send(Protocol.Command.LOGIN.ordinal() + StringUtils.delimiter + "SUCCESS");
        } else {
            client.send(Protocol.Command.LOGIN.ordinal() + StringUtils.delimiter + "FAILED");
        }
    }

    @Override
    public void setValues(String... values) {
        if (values.length == 2 ) {
            username = values[0];
            password = values[1];
        } else {
            System.out.println("Error at LoginCommand, size not ok of: "+values);
        }
    }
}
