package com.qmul.nminoiu.tunein;

import android.app.Application;

/**
 * Created by nicoleta on 23/01/2018.
 */
public class SongSingleton extends Application{
    private volatile static SongSingleton mInstance = null;
    private String songName;

    private SongSingleton() {
        songName = "";
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SongSingleton getInstance() {
        if (mInstance == null) {
            mInstance = new SongSingleton();
        }
        return mInstance;
    }

    /**
     * Get song name string.
     *
     * @return the string
     */
    public String getSongName(){return this.songName;}

    /**
     * Set song name.
     *
     * @param s the s
     */
    public void setSongName(String s){songName = s;}

}
