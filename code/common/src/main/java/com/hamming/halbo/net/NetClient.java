package com.hamming.halbo.net;

import com.hamming.halbo.net.cmd.AbstractCommand;

import javax.management.relation.RoleUnresolved;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetClient implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean open = true;

    public boolean connect(String ip, int port) {
        boolean retval = true;
        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            Thread clientThread = new Thread(this);
            clientThread.setName("Client Connection");
            clientThread.setDaemon(true);
            clientThread.start();
        } catch (IOException e) {
            retval = false;
            System.out.println("ERROR:"+e.getMessage());
            //e.printStackTrace();
        }
        return retval;
    }

    @Override
    public void run() {
        while (open) {
            try {
                String s = in.readLine();
                if (s == null) {
                    open = false;
                    try {
                        if (socket != null) socket.close();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    try {
                        if (in != null) in.close();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    try {
                        if (out != null) out.close();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    return;
                }
            } catch (IOException exception) {
                open = false;
                try {
                    socket.close();
                } catch (Exception exception1) {
                    exception.printStackTrace();
                }
                try {
                    in.close();
                } catch (Exception exception1) {
                    exception.printStackTrace();
                }
                try {
                    out.close();
                } catch (Exception exception1) {
                    exception.printStackTrace();
                }
                return;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

    }


    public void dispose() {
        try {
            if (open) {
                open = false;
                socket.close();
                in.close();
                out.close();
            }
            socket = null;
            in = null;
            out = null;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void send(AbstractCommand command) {
        if (open) out.println(command.toNETCode());
    }

    public boolean isConnected() {
        return open;
    }

}
