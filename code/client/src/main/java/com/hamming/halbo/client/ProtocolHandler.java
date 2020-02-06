package com.hamming.halbo.client;

import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.util.StringUtils;

import java.util.Arrays;

public class ProtocolHandler implements Protocol {

    public String getLoginCommand(String username, String password) {
        String hashedPassword = StringUtils.hashPassword(password);
        String cmd = Command.LOGIN.ordinal() + StringUtils.delimiter+ StringUtils.combineValuesToString(username, hashedPassword);
        return cmd;
    }

    public String getWorldsCommand() {
        String cmd = Command.GETWORLDS.ordinal() + "";
        return cmd;
    }

    public String getGetContinentsCommand(String worldId) {
        String cmd = Command.GETCONTINENTS.ordinal() + StringUtils.delimiter + worldId;
        return cmd;
    }

    public String getGetCitiesCommand(String continentId) {
        String cmd = Command.GETCITIES.ordinal() + StringUtils.delimiter + continentId;
        return cmd;
    }


    public Command parseCommandString(String s) {
        String[] sArr = s.split(StringUtils.delimiter);
        String strId = sArr[0];
        Command cmd = Command.values()[Integer.valueOf(strId)];
        return cmd;
    }



}
