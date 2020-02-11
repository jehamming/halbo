package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.factories.DTOFactory;
import com.hamming.halbo.model.User;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.model.dto.UserLocationDto;
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
            client.setUser(u);
            UserDto dto = DTOFactory.getInstance().getUserDTO(u);
            client.send(Protocol.Command.LOGIN.ordinal() + StringUtils.delimiter + Protocol.SUCCESS + StringUtils.delimiter + dto.toNetData());
        } else {
            client.setUser(null);
            client.send(Protocol.Command.LOGIN.ordinal() + StringUtils.delimiter + Protocol.FAILED + StringUtils.delimiter + "Not a valid username/password combo");
        }
        client.sendUserLocation();
        client.sendFullGameState();
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
