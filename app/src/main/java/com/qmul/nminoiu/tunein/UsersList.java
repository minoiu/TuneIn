package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by nminoiu on 27/06/2017.
 */

public class UsersList extends AppCompatActivity {

    ListView usersList;
    ArrayList<String> users = new ArrayList<>();
    private ArrayAdapter<String> uadapter;
    private DatabaseReference db;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        db = FirebaseDatabase.getInstance().getReference().child("Users");

        usersList = (ListView) findViewById(R.id.usersList);
        uadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        usersList.setAdapter(uadapter);

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                users.add(value);
                uadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

