package com.qmul.nminoiu.tunein;

/**
 * Created by nicoleta on 23/01/2018.
 */
public class Song {
    private String song;
    private String url;

    /**
     * Song.
     *
     * @param song the song
     * @param url  the url
     */
    public void Song(String song, String url){
        this.song = song;
        this.url = url;
    }

    /**
     * Song.
     *
     * @param song the song
     */
    public  void Song(String song){
        this.song = song;
    }

    /**
     * Get song name string.
     *
     * @return the string
     */
    public String getSongName(){
        return song;
    }

    /**
     * Get url string.
     *
     * @return the string
     */
    public String getURL(){
        return url;
    }

    /**
     * Set song name.
     *
     * @param song the song
     */
    public void setSongName(String song){
        this.song = song;
    }

    /**
     * Set url.
     *
     * @param url the url
     */
    public void setURL(String url){
        this.url = url;
    }
}
