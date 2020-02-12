package com.hamming.test;

import com.hamming.halbo.model.User;
import com.hamming.halbo.factories.UserFactory;

public class TestContinentServer {

    public void testServer () {

        User user = UserFactory.getInstance().addUser("Longname", "username", "password");
        System.out.println(this.getClass().getName() + ":" + "User : " + user);

        //CityServer cs = new CityServer();
        //City city = cs.addCity("City01", user);
        //System.out.println(this.getClass().getName() + ":" + "City : " + city);

    }

    public static void main(String[] args) {
        TestContinentServer server = new TestContinentServer();
        server.testServer();
    }



}
