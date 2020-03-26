package com.hamming.halbo.game;

import com.hamming.halbo.model.BasicObject;

public class GameStateEvent {

    public enum Type {
        USERCONNECTED,
        USERDISCONNECTED,
        USERTELEPORTED,
        USERLOCATION
    }

    private BasicObject object;
    private Type type;

    public GameStateEvent(Type type, BasicObject object) {
        this.type = type;
        this.object = object;
    }


    public BasicObject getObject() {
        return object;
    }

    public void setObject(BasicObject object) {
        this.object = object;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
