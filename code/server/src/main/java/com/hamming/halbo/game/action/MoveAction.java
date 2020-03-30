package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.model.User;

public class MoveAction implements Action {
    private GameController controller;
    private ClientConnection client;
    private long sequence;
    private boolean forward = false;
    private boolean back = false;
    private boolean left = false;
    private boolean right = false;
    private boolean buildMode = false;


    public MoveAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        User u = client.getUser();
        controller.handleMoveRequest(sequence, u, forward, back, left, right, buildMode);
    }

    @Override
    public void setValues(String... values) {
        if (values.length == 6 ) {
            sequence = Long.valueOf(values[0]);
            forward = Boolean.valueOf(values[1]);
            back = Boolean.valueOf(values[2]);
            left = Boolean.valueOf(values[3]);
            right = Boolean.valueOf(values[4]);
            buildMode = Boolean.valueOf(values[5]);
        } else {
            System.out.println(this.getClass().getName() + ":" + "Error at "+getClass().getName()+", size not ok of: "+values);
        }
    }
}
