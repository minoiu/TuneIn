package com.qmul.nminoiu.tunein;

import android.app.Application;

/**
 * Created by nicoleta on 24/01/2018.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        initializeSingletons();
    }

    private void initializeSingletons(){
    }
}
