package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.factories.DTOFactory;
import com.hamming.halbo.factories.UserFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.User;
import com.hamming.halbo.model.dto.UserDto;
import com.hamming.halbo.util.StringUtils;

public class ProtocolVersionAction implements Action {
    private GameController controller;
    private ClientConnection client;

    private String clientProtocol;

    public ProtocolVersionAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        if (Protocol.version.equals(clientProtocol)) {
            client.send(Protocol.Command.VERSION.ordinal() + StringUtils.delimiter + Protocol.SUCCESS);
            client.getProtocolHandler().protocolVersionCompatible();
        } else {
            String msg = "Incompatible protocol : " + clientProtocol + ", server=" + Protocol.version;
            client.send(Protocol.Command.VERSION.ordinal() + StringUtils.delimiter + Protocol.FAILED + StringUtils.delimiter + msg);
        }
    }

    @Override
    public void setValues(String... values) {
        if (values.length == 1 ) {
            clientProtocol = values[0];
        } else {
            System.out.println(this.getClass().getName() + ":" + "Error, size not ok of: "+values);
        }
    }
}
