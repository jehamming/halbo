package com.hamming.halbo.game;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.game.action.*;
import com.hamming.halbo.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProtocolHandler implements Protocol {

    private GameController controller;
    private ClientConnection client;
    private Map<Command, Action> commands;

    public ProtocolHandler(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
        registerCommands();
    }

    private void registerCommands() {
        commands = new HashMap<Command, Action>();
        commands.put(Command.LOGIN, new LoginAction(controller, client));
        commands.put(Command.GETWORLDS, new GetWorldsAction(controller, client));
        commands.put(Command.GETCONTINENTS, new GetContinentAction(controller,client));
        commands.put(Command.GETCITIES, new GetCitiesAction(controller,client));
        commands.put(Command.GETBASEPLATES, new GetBaseplatesAction(controller,client));
        commands.put(Command.MOVE, new GetBaseplatesAction(controller,client));
        commands.put(Command.TELEPORT, new TeleportAction(controller,client));
    }

    public Action parseCommandString(String s) {
        System.out.println("ParseIncomingCommand:" + s);
        String[] sArr = s.split(StringUtils.delimiter);
        String strId = sArr[0];
        Command cmd = Command.values()[Integer.valueOf(strId)];
        System.out.println("IncomingCommand=" + cmd);
        Action pCMD = commands.get(cmd);
        if ( pCMD != null ) {
            pCMD.setValues(Arrays.copyOfRange(sArr, 1, sArr.length));
        } else {
            System.out.println("Unhandled command: " + cmd);
        }
        return pCMD;
    }


}
