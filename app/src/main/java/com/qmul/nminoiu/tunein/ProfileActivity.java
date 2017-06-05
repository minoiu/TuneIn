package com.qmul.nminoiu.tunein;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    private TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = (TextView)findViewById(R.id.textEmailProfile);
        email.setText(getIntent().getExtras().getString("Email"));
    }
}
