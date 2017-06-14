package com.qmul.nminoiu.tunein;

/**
 * Created by nminoiu on 14/06/2017.
 */

public class User {

    public String username;
    public String email;
    public String firstname;
    public String lastname;



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User(String email, String firstname, String lastname) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;

    }

}