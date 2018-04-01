package com.qmul.nminoiu.tunein;

/**
 * Created by nicoleta on 14/11/2017.
 */
public class User {

    private String fullname;
    private String email;
    private int time;

    /**
     * Gets fullname.
     *
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Sets fullname.
     *
     * @param fullname the fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(int time) { this.time = time;}

    /**
     * Get time int.
     *
     * @return the int
     */
    public int getTime(){ return time; }
}