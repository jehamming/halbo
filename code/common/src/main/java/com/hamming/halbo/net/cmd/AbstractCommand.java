package com.hamming.halbo.net.cmd;

import com.hamming.halbo.util.StringUtils;

public abstract class AbstractCommand<T> {
    private String shortName;
    private String delimiter = ":";

    public AbstractCommand(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDelimiter() {
        return delimiter;
    }

    abstract public String toNETCode();

}
