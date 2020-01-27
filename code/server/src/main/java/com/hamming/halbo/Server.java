package com.hamming.halbo;

// This is the main server class
public abstract class Server {

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
