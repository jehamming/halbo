package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.factories.CityFactory;
import com.hamming.halbo.factories.DTOFactory;
import com.hamming.halbo.factories.WorldFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.Baseplate;
import com.hamming.halbo.model.City;
import com.hamming.halbo.model.Continent;
import com.hamming.halbo.model.World;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.util.StringUtils;

import java.util.Arrays;

public class GetBaseplatesAction implements Action {
    private GameController controller;
    private ClientConnection client;

    private String cityId;

    public GetBaseplatesAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        City c = CityFactory.getInstance().findCityByID(cityId);
        if ( c != null ) {
            //TODO IMPLEMENT
/*            for ( Baseplate b : c.getBaseplates() ) {
                BaseplateDto dto = DTOFactory.getInstance().getBaseplateDto(b);
                client.send(Protocol.Command.GETBASEPLATES.ordinal() + StringUtils.delimiter + dto.toNetData());
            }*/
            client.send(Protocol.Command.GETBASEPLATES.ordinal() + StringUtils.delimiter + Protocol.FAILED);
        } else {
            client.send(Protocol.Command.GETBASEPLATES.ordinal() + StringUtils.delimiter + Protocol.FAILED);
        }
    }

    @Override
    public void setValues(String... values) {
        if (values.length == 1 ) {
            cityId = values[0];
        } else {
            System.out.println(this.getClass().getName() + ":" + "Error at "+getClass().getName()+", size not ok of: "+ Arrays.toString(values));
        }
    }
}
