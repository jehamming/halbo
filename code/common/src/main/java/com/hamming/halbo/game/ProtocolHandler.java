package com.hamming.halbo.game;

import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.model.dto.*;
import com.hamming.halbo.util.StringUtils;

import java.util.Arrays;

public class ProtocolHandler implements Protocol {

    public String getLoginCommand(String username, String password) {
        String hashedPassword = StringUtils.hashPassword(password);
        String cmd = Command.LOGIN.ordinal() + StringUtils.delimiter+ StringUtils.combineValuesToString(username, hashedPassword);
        return cmd;
    }

    public String getWorldsCommand() {
        return Command.GETWORLDS.ordinal() + "";
    }

    public String getVersionCommand() {
        return Command.VERSION.ordinal() + "";
    }


    public String getGetContinentsCommand(String worldId) {
        return Command.GETCONTINENTS.ordinal() + StringUtils.delimiter + worldId;
    }

    public String getGetCitiesCommand(String continentId) {
        return Command.GETCITIES.ordinal() + StringUtils.delimiter + continentId;
    }

    public String getGetBaseplatesCommand(String cityId) {
        return Command.GETBASEPLATES.ordinal() + StringUtils.delimiter + cityId;
    }


    public String getMoveCommand(MovementDto dto) {
        String cmd = Command.MOVE.ordinal() + StringUtils.delimiter
                + dto.toNetData();
        return cmd;
    }


    public String getTeleportCommand(String userId, WorldDto world, ContinentDto continent, CityDto city) {
        String cmd = Command.TELEPORT.ordinal() + StringUtils.delimiter
                + userId + StringUtils.delimiter
                + world.getId().toString() + StringUtils.delimiter
                + continent.getId().toString() + StringUtils.delimiter
                + city.getId().toString() + StringUtils.delimiter;
         return cmd;
    }


    public Command parseCommandString(String s) {
        String[] sArr = s.split(StringUtils.delimiter);
        String strId = sArr[0];
        return Command.values()[Integer.valueOf(strId)];
    }


    public String getGetBaseplateCommand(String baseplateId) {
        return Command.GETBASEPLATE.ordinal() + StringUtils.delimiter + baseplateId;
    }

    public String getUserCommand(String userId) {
        return Command.GETUSER.ordinal() + StringUtils.delimiter + userId;
    }
}
