package com.mohamed;

import java.io.Serializable;

public class user implements Serializable {
    private String user;
    private String name;
    private String surname;
    private String password;
    private boolean isAdmin = false;


    public user(String user, String name, String surname, String password, boolean isAdmin) {
        this.user = user;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.isAdmin = isAdmin;
    }
    public user(String user, String name, String surname, String password) {
        this.user = user;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public String getUser() {
        return user;
    }
    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public String getSurname(){ return surname;}



    @Override
    public String toString() {
        return "user{" +
                "user='" + user + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
