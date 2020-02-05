package com.hamming.halbo.game.cmd;

public interface Action {

    public void execute();

    public void setValues(String... values);
}
