package com.qmul.nminoiu.tunein;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by nicoledumitrascu on 18/06/2017.
 */

class Player extends AsyncTask<String, Void, Boolean> {
    private ProgressDialog progress;
    private MediaPlayer mediaPlayer;
    private boolean intialStage = true;
    private boolean playPause;
    private Button btn;

    @Override
    protected Boolean doInBackground(String... params) {
        // TODO Auto-generated method stub
        Boolean prepared;
        try {

            mediaPlayer.setDataSource(params[0]);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    intialStage = true;
                    playPause=false;
                    btn.setBackgroundResource(R.drawable.ic_media_play);
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });
            mediaPlayer.prepare();
            prepared = true;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            Log.d("IllegarArgument", e.getMessage());
            prepared = false;
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            prepared = false;
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            prepared = false;
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            prepared = false;
            e.printStackTrace();
        }
        return prepared;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (progress.isShowing()) {
            progress.cancel();
        }
        Log.d("Prepared", "//" + result);
        mediaPlayer.start();

        intialStage = false;
    }

//    public Player() {
//        progress = new ProgressDialog(Player.this);
//    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        this.progress.setMessage("Buffering...");
        this.progress.show();

    }

   // @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        //super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
