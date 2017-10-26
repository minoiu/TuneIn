package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicoleta on 26/10/2017.
 */

public class FollowingActivity extends AppCompatActivity {
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    // ProgressDialog pd;
    ListView followingList;
    ArrayList<String> users = new ArrayList<>();
    private AdapterFollowing uadapter;
    private DatabaseReference db;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabase2;
    private DatabaseReference mDatabase3;
    private LinearLayout play_toolbar;
    private LinearLayout searchLayout;
    MaterialSearchView searchView;
    private List<RowItem> rowItems;



    private DatabaseReference db1;
    private String value;
    private String sender;
    private String ID;


    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        followingList = (ListView) findViewById(R.id.followingList);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String curUser = currentuser.getUid().toString();

        Intent i = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Following");
        ID = firebaseAuth.getCurrentUser().getUid();
        sender = firebaseAuth.getCurrentUser().getEmail();
        rowItems = new ArrayList<RowItem>();


        DatabaseReference fdb = FirebaseDatabase.getInstance().getReference().child("Following").child(ID);
        fdb.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    String value = dataSnapshot.getValue(String.class);
                    users.add(value);
                    RowItem item = new RowItem(R.drawable.options, value);

                    rowItems.add(item);
                    uadapter.notifyDataSetChanged();
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                users.remove(dataSnapshot.getValue().toString());
                uadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        uadapter = new AdapterFollowing(this, rowItems);
        followingList.setAdapter(uadapter);

    }


        }





