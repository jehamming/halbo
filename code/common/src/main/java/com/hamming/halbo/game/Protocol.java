package com.hamming.halbo.game;

public interface Protocol {

    public String version = "1.0";

    public enum Command {
        VERSION,
        LOGIN,
        GETWORLDS,
        GETCONTINENTS,
        GETCITIES,
        GETCITYDETAILS,
        GETBASEPLATES,
        GETBASEPLATE,
        GETCONSTUCTION
    }
}
