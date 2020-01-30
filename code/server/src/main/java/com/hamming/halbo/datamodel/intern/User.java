package com.hamming.halbo.datamodel.intern;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private String fullName;
    private String username;
    private String password;
    private HalboID id;

    public User( HalboID id) {
        this.id = id;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public HalboID getId() {
        return id;
    }

    public static User valueOf( HalboID id, String fullName, String username, String password ) {
        final User u = new User(id);
        u.setPassword(password);
        u.setFullName(fullName);
        u.setUsername(username);
        return u;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}
