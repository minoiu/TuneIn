package com.qmul.nminoiu.tunein;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.LinearLayout;
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

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

/**
 * Created by nicoleta on 11/10/2017.
 */
public class FollowersActivity extends AppCompatActivity {
    ArrayList<String> al = new ArrayList<>();
    ListView usersList;
    ArrayList<String> users = new ArrayList<>();
    private AdapterFollowers uadapter;
    private DatabaseReference mDatabase2;
    private DatabaseReference mDatabase3;
    private List<RowItem> rowItems;
    private LinearLayout searchLayout;
    private DatabaseReference db1;
    private String value;
    private String sender;
    private String ID;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        rowItems = new ArrayList<RowItem>();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String curUser = currentuser.getUid().toString();
        Intent i = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Followers");
        ID = firebaseAuth.getCurrentUser().getUid();
        sender = firebaseAuth.getCurrentUser().getEmail();

        //retrieve followers from Firebase
        DatabaseReference mDb = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
        mDb.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.myname = dataSnapshot.getValue().toString();
                DatabaseReference fdb = FirebaseDatabase.getInstance().getReference().child("FollowersNames").child(ID);
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
            }

                @Override
                public void onCancelled (DatabaseError databaseError){

                }
            });

        //retrieve fullname from Firebase
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
        mDatabase2.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //retrieve email from Firebase
        mDatabase3 = FirebaseDatabase.getInstance().getReference().child("Users").child(curUser);
        mDatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                UserDetails.username = value;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        usersList = (ListView) findViewById(R.id.ulistView);
        uadapter = new AdapterFollowers(this, rowItems);
        usersList.setAdapter(uadapter);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
    }

    // Handle actifon bar item clicks. The action bar will
    // automatically handle clicks on the Home/Up button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    //handle click on back button and start previous activity
    @Override
    public void onBackPressed() {
        Intent i = getIntent();
        if (i != null) {
            if (UserDetails.oldIntent.equals("Followers")) {
                Intent backMainTest = new Intent(this, PlaylistSongs.class);
                if (mediaPlayer.isPlaying()) {
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            } else if (UserDetails.oldIntent.equals("MySongs")) {
                Intent backMainTest = new Intent(this, Songs.class);
                if (mediaPlayer.isPlaying()) {
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            } else if (UserDetails.oldIntent.equals("Downloads")) {
                Intent backMainTest = new Intent(this, Downloads.class);
                if (mediaPlayer.isPlaying()) {
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            } else if (UserDetails.oldIntent.equals("Favourites")) {
                Intent backMainTest = new Intent(this, Favourites.class);
                if (mediaPlayer.isPlaying()) {
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            } else if (UserDetails.oldIntent.equals("Recents")) {
                Intent backMainTest = new Intent(this, LibraryActivity.class);
                if (mediaPlayer.isPlaying()) {
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            } else if (UserDetails.oldIntent.equals("FromSettingsMenu")) {
                Intent backMainTest = new Intent(this, RealTimeActivity.class);
                if (mediaPlayer.isPlaying()) {
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            } else if (UserDetails.oldIntent.equals("FromConversations")) {
                Intent backMainTest = new Intent(this, Conversations.class);
                startActivity(backMainTest);
                finish();
            }
        }
    }

    //handle menu open
    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {
        if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){
                }
                catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
}







