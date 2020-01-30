package com.hamming.halbo;


import com.hamming.halbo.datamodel.intern.User;
import com.hamming.halbo.factories.UserFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;

public class HALBOClient extends ClientInstance {

    private User user;

    public HALBOClient(Socket s, BufferedReader in, PrintWriter out) {
        super(s, in, out);
    }


    private boolean login(String username, String password){
        boolean returnValue = true;
        user = UserFactory.getInstance().validateUser(username, password);
        if (user == null ) {
            returnValue = false;
        }
        return returnValue;
    }
}
