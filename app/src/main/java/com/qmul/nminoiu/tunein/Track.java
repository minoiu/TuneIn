package com.qmul.nminoiu.tunein;

/**
 * Created by nicoledumitrascu on 18/06/2017.
 */

public class Track {

    private String name;
    private String URL;

    public Track() {
    }

    public Track(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
