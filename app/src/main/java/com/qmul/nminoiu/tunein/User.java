package com.qmul.nminoiu.tunein;

/**
 * Created by nminoiu on 14/06/2017.
 */

public class User {

    private String fullname;
    private String email;
    private int time;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTime(int time) { this.time = time;}

    public int getTime(){ return time; }
}