package com.hamming.halbo.datamodel;

public class UserFactory {
    private static UserFactory instance;
    private User systemUser;

    private UserFactory() {
        initialize();
    };

    private void initialize() {
        systemUser = new User();
        systemUser.setFullName("SYSTEM");
        // TODO Check a connection with a Database or something?
    }

    public static UserFactory getInstance() {
        if ( instance == null ) {
            instance = new UserFactory();
        }
        return instance;
    }

    public User getSystemUser() {
        return systemUser;
    }
}
