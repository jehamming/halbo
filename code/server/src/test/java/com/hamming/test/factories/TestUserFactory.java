package com.hamming.test.factories;

import com.hamming.halbo.model.User;
import com.hamming.halbo.factories.UserFactory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestUserFactory {


    @Test
    public void testCreateUser() {
        deleteAllUsers();
        User u1 = UserFactory.getInstance().addUser("Fullname", "username", "password");
        assert  UserFactory.getInstance().getUsers().size() == 1;
        User u2 = UserFactory.getInstance().addUser("Fullname2", "username2", "password2");
        assert  UserFactory.getInstance().getUsers().size() == 2;
    }

    @Test
    public void testDeleteUser() {
        deleteAllUsers();
        User u1 = UserFactory.getInstance().addUser("Fullname", "username", "password");
        User u2 = UserFactory.getInstance().addUser("Fullname2", "username2", "password2");
        User foundUser = UserFactory.getInstance().findUserByUsername("username");
        UserFactory.getInstance().deleteUser(foundUser);
        assert  UserFactory.getInstance().getUsers().size() == 1;
    }


    @Test
    public void testStoreAndLoad() {
        deleteAllUsers();
        User u1 = UserFactory.getInstance().addUser("Fullname", "username", "password");
        User u2 = UserFactory.getInstance().addUser("Fullname2", "username2", "password2");
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile("HALBO","TST");
            UserFactory.getInstance().storeUsersInFile(tmpFile);
            UserFactory.getInstance().deleteUser(u1);
            UserFactory.getInstance().deleteUser(u2);
            assert  UserFactory.getInstance().getUsers().size() == 0;
            UserFactory.getInstance().loadUsersFromFile(tmpFile);
            assert  UserFactory.getInstance().getUsers().size() == 2;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteAllUsers() {
        UserFactory.getInstance().getUsers().removeAll(UserFactory.getInstance().getUsers());

    }


}