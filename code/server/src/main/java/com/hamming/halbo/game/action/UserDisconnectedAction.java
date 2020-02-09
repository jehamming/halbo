package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.factories.DTOFactory;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.User;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.util.StringUtils;

public class UserDisconnectedAction implements Action {
    private GameController controller;
    private ClientConnection client;

    private String userId;

    public UserDisconnectedAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        User u = UserFactory.getInstance().findUserById(userId);;
        UserDto dto = DTOFactory.getInstance().getUserDTO(u);
        client.send(Protocol.Command.USERDISCONNECTED.ordinal() + StringUtils.delimiter + dto.toNetData());
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void setValues(String... values) {
        if (values.length == 1 ) {
            userId = values[0];
        } else {
            System.out.println("Error at "+getClass().getName() +", size not ok of: "+values);
        }
    }
}