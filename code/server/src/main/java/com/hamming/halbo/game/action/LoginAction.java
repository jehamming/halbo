package com.hamming.halbo.game.cmd;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.model.User;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.util.StringUtils;

public class LoginAction implements Action {
    private GameController controller;
    private ClientConnection client;

    private String username;
    private String password;

    public LoginAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        User u = UserFactory.getInstance().validateUser(username,password);
        if ( u != null ) {
            client.send(Protocol.Command.LOGIN.ordinal() + StringUtils.delimiter + Protocol.SUCCESS);
            client.setUser(u);
        } else {
            client.setUser(null);
            client.send(Protocol.Command.LOGIN.ordinal() + StringUtils.delimiter + Protocol.FAILED);
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
