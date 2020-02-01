package com.hamming.test.client;


import com.hamming.halbo.net.NetClient;
import com.hamming.halbo.net.cmd.LoginCommand;
import com.hamming.halbo.util.StringUtils;

public class TestTheClient {

    public void testConnection() {
        NetClient client = new NetClient();
        client.connect("127.0.0.1", 3333);
        client.send(new LoginCommand("jehamming", StringUtils.hashPassword("jehamming")));
    }

    public static void main(String[] args) {
        TestTheClient t = new TestTheClient();
        t.testConnection();
    }
}
