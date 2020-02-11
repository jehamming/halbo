package com.hamming.halbo.game;

public interface Protocol {

    public String version = "1.0";

    public String SUCCESS="SUCCESS";
    public String FAILED = "FAILED";

    public enum Command {
        VERSION,
        LOGIN,
        USERCONNECTED,
        USERDISCONNECTED,
        GETUSER,
        GETWORLDS,
        GETCONTINENTS,
        GETCITIES,
        GETCITYDETAILS,
        GETBASEPLATES,
        GETBASEPLATE,
        GETCONSTUCTION,
        GETLOCATION,
        LOCATION,
        TELEPORT,
        MOVE
    }
}
