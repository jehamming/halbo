package com.hamming.halbo.game.cmd;

public interface ProtocolCommand {

    public void execute();

    public void setValues(String... values);
}
