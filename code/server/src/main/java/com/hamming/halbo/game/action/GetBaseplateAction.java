package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.factories.BaseplateFactory;
import com.hamming.halbo.factories.CityFactory;
import com.hamming.halbo.factories.DTOFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.Baseplate;
import com.hamming.halbo.model.City;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.util.StringUtils;

import java.util.Arrays;

public class GetBaseplateAction implements Action {
    private GameController controller;
    private ClientConnection client;

    private String baseplateId;

    public GetBaseplateAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        Baseplate bp = BaseplateFactory.getInstance().findBaseplateByID(baseplateId);
        if ( bp != null ) {
            BaseplateDto dto = DTOFactory.getInstance().getBaseplateDto(bp);
            client.send(Protocol.Command.GETBASEPLATE.ordinal() + StringUtils.delimiter + dto.toNetData());
        } else {
            client.send(Protocol.Command.GETBASEPLATE.ordinal() + StringUtils.delimiter + Protocol.FAILED + StringUtils.delimiter + "Baseplate not found!");
        }
    }

    @Override
    public void setValues(String... values) {
        if (values.length == 1 ) {
            baseplateId = values[0];
        } else {
            System.out.println(this.getClass().getName() + ":" + "Error at "+getClass().getName()+", size not ok of: "+ Arrays.toString(values));
        }
    }
}
