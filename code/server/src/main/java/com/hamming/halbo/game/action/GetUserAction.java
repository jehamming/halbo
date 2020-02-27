package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.factories.BaseplateFactory;
import com.hamming.halbo.factories.DTOFactory;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.Baseplate;
import com.hamming.halbo.model.User;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.util.StringUtils;

import java.util.Arrays;

public class GetUserAction implements Action {
    private GameController controller;
    private ClientConnection client;

    private String userId;

    public GetUserAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        User user = UserFactory.getInstance().findUserById(userId);
        if ( user != null ) {
            UserDto dto = DTOFactory.getInstance().getUserDTO(user);
            client.send(Protocol.Command.GETUSER.ordinal() + StringUtils.delimiter + Protocol.SUCCESS + StringUtils.delimiter + dto.toNetData());
        } else {
            client.send(Protocol.Command.GETUSER.ordinal() + StringUtils.delimiter + Protocol.FAILED + StringUtils.delimiter + "User not found!");
        }
    }

    @Override
    public void setValues(String... values) {
        if (values.length == 1 ) {
            userId = values[0];
        } else {
            System.out.println(this.getClass().getName() + ":" + "Error at "+getClass().getName()+", size not ok of: "+ Arrays.toString(values));
        }
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}