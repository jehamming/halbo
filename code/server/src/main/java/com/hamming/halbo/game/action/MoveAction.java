package com.hamming.halbo.game.action;

import com.hamming.halbo.ClientConnection;
import com.hamming.halbo.game.GameController;
import com.hamming.halbo.model.User;

public class MoveAction implements Action {
    private GameController controller;
    private ClientConnection client;

    private boolean forward = false;
    private boolean back = false;
    private boolean left = false;
    private boolean right = false;
    private double viewAngle = 0.0;


    public MoveAction(GameController controller, ClientConnection client) {
        this.controller = controller;
        this.client = client;
    }

    @Override
    public void execute() {
        User u = client.getUser();
        controller.handleMoveRequest(u, forward, back, left, right, viewAngle);
    }

    @Override
    public void setValues(String... values) {
        if (values.length == 5 ) {
            forward = Boolean.valueOf(values[0]);
            back = Boolean.valueOf(values[1]);
            left = Boolean.valueOf(values[2]);
            right = Boolean.valueOf(values[3]);
            viewAngle = Double.valueOf(values[4]);
        } else {
            System.out.println(this.getClass().getName() + ":" + "Error at "+getClass().getName()+", size not ok of: "+values);
        }
    }
}
