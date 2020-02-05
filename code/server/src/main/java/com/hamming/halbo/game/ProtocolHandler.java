package com.hamming.halbo.game;

import com.hamming.halbo.HALBOClient;
import com.hamming.halbo.game.cmd.GetWorldsAction;
import com.hamming.halbo.game.cmd.LoginAction;
import com.hamming.halbo.game.cmd.Action;
import com.hamming.halbo.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProtocolHandler implements Protocol {

    private GameController controller;
    private HALBOClient client;
    private Map<Command, Action> commands;

    public ProtocolHandler(GameController controller, HALBOClient client) {
        this.controller = controller;
        this.client = client;
        registerCommands();
    }

    private void registerCommands() {
        commands = new HashMap<Command, Action>();
        commands.put(Command.LOGIN, new LoginAction(controller, client));
        commands.put(Command.GETWORLDS, new GetWorldsAction(controller, client));
    }

    public Action parseCommandString(String s) {
        System.out.println("ParseCommand:" + s);
        String[] sArr = s.split(StringUtils.delimiter);
        String strId = sArr[0];
        Command cmd = Command.values()[Integer.valueOf(strId)];
        Action pCMD = commands.get(cmd);
        if ( pCMD != null ) {
            pCMD.setValues(Arrays.copyOfRange(sArr, 1, sArr.length));
        }
        return pCMD;
    }


}
