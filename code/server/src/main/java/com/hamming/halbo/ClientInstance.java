package com.hamming.halbo;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ClientInstance {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientInstance(Socket s, BufferedReader in, PrintWriter out) {
        this.socket = s;
        this.in = in;
        this.out = out;
    }

    public Socket getSocket() {
        return socket;
    }
}
