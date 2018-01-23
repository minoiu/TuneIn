package com.qmul.nminoiu.tunein;

/**
 * Created by nicoleta on 23/01/2018.
 */

public class Song {
    private String song;
    private String url;

    public void Song(String song, String url){
        this.song = song;
        this.url = url;
    }

    public  void Song(String song){
        this.song = song;
    }

    public String getSongName(){
        return song;
    }

    public String getURL(){
        return url;
    }

    public void setSongName(String song){
        this.song = song;
    }

    public void setURL(String url){
        this.url = url;
    }
}
