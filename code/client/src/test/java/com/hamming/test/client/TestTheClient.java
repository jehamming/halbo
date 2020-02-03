package com.hamming.test.client;


import com.hamming.halbo.client.ProtocolHandler;
import com.hamming.halbo.net.DataReceiver;
import com.hamming.halbo.net.NetClient;

public class TestTheClient implements DataReceiver {


    private static boolean keeprunning = true;

    public void testConnection() {
        NetClient client = new NetClient(this);
        client.connect("127.0.0.1", 3333);
        ProtocolHandler handler = new ProtocolHandler();
        String s = handler.getLoginCommand("jehamming", "jehamming");
        System.out.println("Send:" + s);
        client.send(s);
    }

    public static void main(String[] args) {
        TestTheClient t = new TestTheClient();
        t.testConnection();
        while (keeprunning) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void receive(String s) {
        System.out.println("Received:" + s);
        keeprunning = false;
    }
}
