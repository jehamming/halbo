package com.hamming.halbo.model;

import java.io.Serializable;
import java.util.Objects;

public class User extends BasicObject implements Serializable {

    private String username;
    private String password;
    private String email = "";


    public User( HalboID id) {
        super(id);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail(){return this.email;}

    public void setEmail(String email) {this.email = email;}

    public static User valueOf(String id, String name, String username, String password, String email ) {
        final User u = new User(HalboID.valueOf(id));
        u.setPassword(password);
        u.setName(name);
        u.setUsername(username);
        u.setEmail(email);
        return u;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", " + super.toString() +
                '}';
    }
}
