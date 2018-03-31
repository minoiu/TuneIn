package com.qmul.nminoiu.tunein;

/**
 * Created by nicoledumitrascu on 18/06/2017.
 */
public class Track {

    private String name;
    private String URL;

    /**
     * Instantiates a new Track.
     *
     * @param URL the url
     */
    public Track(String URL) {
        this.URL = URL;

    }

    /**
     * Instantiates a new Track.
     */
    public Track(){
        //this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getURL() {
        return URL;
    }

    /**
     * Sets url.
     *
     * @param URL the url
     */
    public void setURL(String URL) {
        this.URL = URL;
    }
}
