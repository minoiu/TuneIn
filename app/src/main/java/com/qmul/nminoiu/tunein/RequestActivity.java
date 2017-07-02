package com.qmul.nminoiu.tunein;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nicoledumitrascu on 02/07/2017.
 */

class RequestActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private Firebase reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        String user = firebaseAuth.getCurrentUser().getUid();
        reference = new Firebase("https://tunein-633e5.firebaseio.com/FriendRequests/" + user);

        Map<String, String> map = new HashMap<String, String>();
        map.put("Accpted", "false");
        map.put("Name", UserDetails.chatWith);
        map.put("UserID", //todo)
        reference.push().setValue(map);
    }
}