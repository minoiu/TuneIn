package com.qmul.nminoiu.tunein;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AudioPlayer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        Button play = (Button) findViewById(R.id.button_play);
        Button pause = (Button) findViewById(R.id.button_pause);
        Button stop = (Button) findViewById(R.id.button_stop);

        final MediaPlayer mp = MediaPlayer.create(AudioPlayer.this, R.raw.adele);

        play.setOnClickListener(new View.OnClickListener() {
             @Override
              public void onClick(View v) {
                 mp.start();
             }
         });

                pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mp.pause();
            }
        });


        pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mp.stop();
            }
        });


    }


}
