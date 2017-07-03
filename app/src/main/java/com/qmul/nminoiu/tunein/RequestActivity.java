package com.qmul.nminoiu.tunein;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by nicoledumitrascu on 02/07/2017.
 */

public class RequestActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private Firebase reference;
    private ArrayList<String> requests = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        String user = firebaseAuth.getCurrentUser().getUid();
        // reference = new Firebase("https://tunein-633e5.firebaseio.com/FriendRequests/" + user);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("FriendRequests").child(user);

        mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(UserDetails.username)){
                   Toast.makeText(RequestActivity.this, "Already sent a friend request", Toast.LENGTH_SHORT).show();
                }
                else{
                    final Map<String, Object> map = new HashMap<String, Object>();
                    map.put("Accepted", "false");
                    mDatabase.child(UserDetails.username).updateChildren(map);
                    Toast.makeText(RequestActivity.this, "Friend request sent", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
