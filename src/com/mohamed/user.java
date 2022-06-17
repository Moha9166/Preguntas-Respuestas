package com.mohamed;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class is used to create the object {@code user}
 * @author Mohamed Yoube Bahmed
 * @see java.io.Serializable
 */
public class user implements Serializable {
    @Serial
    private static final long serialVersionUID = -3888984241462075534L;
    private String username;
    private String name;
    private String surname;
    private String password;
    private boolean isAdmin = false;
    /**
     * This is one of the constructors to create the user object but with the option to create it with admin privileges.
     * @param username {@code String} The username of the user.
     * @param name {@code String}The name of the user.
     * @param surname {@code String}The surname of the user.
     * @param password {@code String}The password of the user.
     * @param isAdmin {@code boolean}The option that makes it admin or not.
     */
    public user(String username, String name, String surname, String password, boolean isAdmin) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.isAdmin = isAdmin;
    }
    /**
     * This constructor creates a {@code  user} without the option to give to it admin options.
     * @param username {@code String} The username of the user.
     * @param name {@code String}The name of the user.
     * @param surname {@code String}The surname of the user.
     * @param password {@code String}The password of the user.
     */
    public user(String username, String name, String surname, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }
    /**
     * A username getter.
     * @return {@code String} the username of the user
     */
    public String getUsername() {
        return username;
    }
    /**
     * A getter for the user`s name.
     * @return {@code String} the name of the user.
     */
    public String getName() {
        return name;
    }
    /**
     * A getter for the user`s surname.
     * @return {@code String} the surname of the user.
     */
    public String getSurname(){ return surname;}
    /**
     * A getter for the user`s password.
     * @return {@code String} the password of the user.
     */
    public String getPassword() {
        return password;
    }
    /**
     * A getter to tell if the user is admin.
     * @return {@code boolean} {@code true} if the user is admin, {@code false} otherwise.
     */
    public Boolean isAdmin(){ return isAdmin;}
    /**
     * Updates the value user´s variables, with a given ones.
     * @param user {@code String} the new userName for the user.
     * @param name {@code String} the new name for the user.
     * @param surname {@code String} the new surname for the user.
     * @param password {@code String} the new password for the user.
     */
    public void updateUser(String user, String name, String surname, String password){
        this.username = user;
        this.name= name;
        this.surname= surname;
        this.password= password;
    }
    /**
     * This method updates a user variable {@code isAdmin} to true,
     * only if the {@code adminUser} is an actual admin.
     * @param adminUser the user with admin privileges.
     * @param userNewAdmin the admin that we want to grant the user privileges.
     */
    public void makeUserAdmin(user adminUser, user userNewAdmin){
        if (adminUser.isAdmin()){
            userNewAdmin.isAdmin = true;
        }
    }
    /**
    * Returns a string representation of the object.
     * @return {@code String} representation of the object.
     */
    @Override
    public String toString() {
        return "user{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
