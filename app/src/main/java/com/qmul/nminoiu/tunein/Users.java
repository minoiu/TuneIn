package com.qmul.nminoiu.tunein;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * created by Nicoleta on 10/10/2017
 */
public class Users extends AppCompatActivity {
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ListView usersList;
    ArrayList<String> users = new ArrayList<>();
    private ArrayAdapter<String> uadapter;
    private DatabaseReference db;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private DatabaseReference db1;
    private String value;
    private String sender;
    private Toolbar toolbar;
    private String ID;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String curUser = currentuser.getUid().toString();
        db = FirebaseDatabase.getInstance().getReference().child("Users");
        db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(curUser);
        ID = firebaseAuth.getCurrentUser().getUid();
        sender = firebaseAuth.getCurrentUser().getEmail();
        usersList = (ListView) findViewById(R.id.usersList);
        uadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        usersList.setAdapter(uadapter);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //retrieve users from Firebase
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

        //retrieve emails
        db1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                UserDetails.username = value;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //retrieve names
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
        mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //handle click on users
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String user = ((TextView) view).getText().toString();
                UserDetails.chatWith = user;
                Intent i = getIntent();
                if (i != null) {
                    if (i.hasExtra("Uniqid")) {
                        String uniqid = i.getStringExtra("Uniqid");
                        if (uniqid.equals("FromSettings")) {
                            UserDetails.oldIntent = "FromUsers";
                            Toast.makeText(Users.this, "from settings ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Users.this, Chat.class);
                            intent.putExtra("Name", user);
                            startActivity(intent);
                        } else if (uniqid.equals("FromSongAdapter")) {
                            Toast.makeText(Users.this, "from songsadapter ", Toast.LENGTH_SHORT).show();
                            String song = i.getStringExtra("Song");
                            Intent intent = new Intent(Users.this, Chat.class);
                            intent.putExtra("Uniqid", "FromUsers");
                            intent.putExtra("Name", user);
                            intent.putExtra("Song", song);
                            startActivity(intent);
                        } else if (uniqid.equals("FSAdapter")) {
                            String songToJoin = i.getStringExtra("Song");
                            String playlist = i.getStringExtra("Name");
                            //getReceiver(songToJoin, user, playlist);
                            Toast.makeText(Users.this, "from sadapterfor notification " + songToJoin + user, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Intent intent = new Intent(Users.this, Chat.class);
                        intent.putExtra("Uniqid", "FromUsers");
                        UserDetails.oldIntent = "FromUsers";
                        intent.putExtra("Name", user);
                        startActivity(intent);
                    }
                }
            }
        });
    }


    // Handle actifon bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    //handle click on back
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Users.this, RealTimeActivity.class);
        startActivity(i);
        finish();
    }
}





