package com.hamming.halbo.client;

import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.util.StringUtils;

public class ProtocolHandler implements Protocol {

    public String getLoginCommand(String username, String password) {
        String hashedPassword = StringUtils.hashPassword(password);
        String cmd = Command.LOGIN.ordinal() + StringUtils.delimiter+ StringUtils.combineValuesToString(username, hashedPassword);
        return cmd;
    }


}
