package com.qmul.nminoiu.tunein;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by nicoledumitrascu on 12/07/2017.
 */

public class SyncActivity extends AppCompatActivity {
    private String myId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Toast.makeText(SyncActivity.this, "My id is: " + myId, Toast.LENGTH_SHORT).show();

    }
}
