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

    public static SongSingleton getInstance() {
        if (mInstance == null) {
            mInstance = new SongSingleton();
        }
        return mInstance;
    }

    public String getSongName(){return this.songName;}
    public void setSongName(String s){songName = s;}

}
