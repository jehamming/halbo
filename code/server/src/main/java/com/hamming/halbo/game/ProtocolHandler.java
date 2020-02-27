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
        commands = new HashMap<Command, Action>();
        commands.put(Command.VERSION, new ProtocolVersionAction(controller, client));
    }

    public void protocolVersionCompatible() {
        emptyCommands();
        commands.put(Command.LOGIN, new LoginAction(controller, client));
    }

    public void LoggedIn() {
        commands.put(Command.GETWORLDS, new GetWorldsAction(controller, client));
        commands.put(Command.GETCONTINENTS, new GetContinentAction(controller,client));
        commands.put(Command.GETCITIES, new GetCitiesAction(controller,client));
        commands.put(Command.GETBASEPLATES, new GetBaseplatesAction(controller,client));
        commands.put(Command.MOVE, new MoveAction(controller,client));
        commands.put(Command.TELEPORT, new TeleportAction(controller,client));
        commands.put(Command.GETBASEPLATE, new GetBaseplateAction(controller,client));
        commands.put(Command.GETUSER, new GetUserAction(controller, client));
    }


    private void emptyCommands() {
        for (Command c : commands.keySet() ) {
            commands.remove(c);
        }
    }

    public void reset() {
        emptyCommands();
        commands.put(Command.VERSION, new ProtocolVersionAction(controller, client));
    }



    public Action parseCommandString(String s) {
        System.out.println(this.getClass().getName() + ":" + "ParseIncomingCommand:" + s);
        String[] sArr = s.split(StringUtils.delimiter);
        String strId = sArr[0];
        Command cmd = Command.values()[Integer.valueOf(strId)];
        System.out.println(this.getClass().getName() + ":" + "IncomingCommand=" + cmd);
        Action pCMD = commands.get(cmd);
        if ( pCMD != null ) {
            pCMD.setValues(Arrays.copyOfRange(sArr, 1, sArr.length));
        } else {
            System.out.println(this.getClass().getName() + ":" + "Unhandled command: " + cmd);
        }
        return pCMD;
    }


}
