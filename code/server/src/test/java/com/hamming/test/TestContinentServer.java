package com.hamming.test;

import com.hamming.halbo.ContinentServer;
import com.hamming.halbo.datamodel.City;
import com.hamming.halbo.datamodel.User;
import com.hamming.halbo.factories.UserFactory;

public class TestContinentServer {

    public void testServer () {

        User user = UserFactory.getInstance().addUser("Longname", "username", "password");
        System.out.println("User : " + user);

        ContinentServer cs = new ContinentServer();
        City city = cs.addCity("City01", user);
        System.out.println("City : " + city);

    }

    public static void main(String[] args) {
        TestContinentServer server = new TestContinentServer();
        server.testServer();
    }



}
