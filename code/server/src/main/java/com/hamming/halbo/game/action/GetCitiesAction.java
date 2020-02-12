package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.factories.ContinentFactory;
import com.hamming.halbo.factories.DTOFactory;
import com.hamming.halbo.factories.WorldFactory;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.City;
import com.hamming.halbo.model.Continent;
import com.hamming.halbo.model.World;
import com.hamming.halbo.model.dto.CityDto;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.util.StringUtils;

public class GetCitiesAction implements Action {
    private GameController controller;
    private ClientConnection client;

    private String continentId;

    public GetCitiesAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        Continent c = ContinentFactory.getInstance().findContinentById(continentId);
        if ( c != null ) {
            for ( City city : c.getCities() ) {
                CityDto dto = DTOFactory.getInstance().getCityDto(city);
                client.send(Protocol.Command.GETCITIES.ordinal() + StringUtils.delimiter + dto.toNetData());
            }
        } else {
            client.send(Protocol.Command.GETCITIES.ordinal() + StringUtils.delimiter + Protocol.FAILED);
        }
    }

    @Override
    public void setValues(String... values) {
        if (values.length == 1 ) {
            continentId = values[0];
        } else {
            System.out.println(this.getClass().getName() + ":" + "Error at "+getClass().getName()+", size not ok of: "+values);
        }
    }
}
