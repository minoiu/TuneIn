package com.qmul.nminoiu.tunein;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nicoledumitrascu on 30/06/2017.
 */

public class ScrollingActivity extends AppCompatActivity {

    public static String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        TextView name = (TextView) findViewById(R.id.name);
        name.setText(UserDetails.username);
        User user = new User();
        //user.setFullname(UserDetails.chatWith);

        fullname = UserDetails.chatWith;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScrollingActivity.this, RequestActivity.class);
                startActivity(i);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        Intent backMainTest = new Intent(this,SettingsActivity.class);
        startActivity(backMainTest);
        finish();
    }
}