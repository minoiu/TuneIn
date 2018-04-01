package com.qmul.nminoiu.tunein;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.qmul.nminoiu.tunein.UserDetails.friendID;
import static com.qmul.nminoiu.tunein.UserDetails.username;

/**
 * Created by nicoleta on 30/09/2017.
 */
public class ProfileActivity extends AppCompatActivity {

    public static String fullname;
    private DatabaseReference mDatabase;
    private DatabaseReference followersdb;
    private DatabaseReference followersdbN;
    private DatabaseReference mDatabase1;
    private String sender;
    private FirebaseAuth firebaseAuth;
    private String ID;
    private ImageView friendPic;
    private Toolbar toolbar;
    private TextView followersNo;
    private TextView followingNo;
    private TextView publicTitle;
    private ListView publicPlaylists;
    private List<RowItem> rowItems;
    private AdapterProfile adapterProfile;
    private ArrayList<String> publicPList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final String username = getIntent().getExtras().getString("FriendName");
        publicPlaylists = (ListView) findViewById(R.id.publicPlaylists);
        firebaseAuth = FirebaseAuth.getInstance();
        final String user = firebaseAuth.getCurrentUser().getUid();
        sender = firebaseAuth.getCurrentUser().getEmail();
        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();
        followersNo = (TextView) findViewById(R.id.nofollowers);
        followingNo = (TextView) findViewById(R.id.nofollowing);
        publicTitle = (TextView) findViewById(R.id.tvNumber2);
        rowItems = new ArrayList<RowItem>();
        adapterProfile = new AdapterProfile(this, rowItems);
        fullname = UserDetails.chatWith;
        friendPic = (ImageView) findViewById(R.id.friendPicture);
        final String friendName = getIntent().getStringExtra("FriendName");
        UserDetails.friend = friendName;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("FriendName"));
        FirebaseStorage storage = FirebaseStorage.getInstance();

        //get extra from intent
        Intent i = getIntent();
        if (i.hasExtra("FriendId")){
            final String friendID = getIntent().getStringExtra("FriendId");
            UserDetails.friendID = friendID;
        } else{
            DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(username).child("Id");

            dbF.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserDetails.friendID = dataSnapshot.getValue().toString();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        //load profile picture from Firebase with Picasso library
        Firebase picture = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase picture1 = picture.child("ProfilePictures");
        picture1.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(friendID)){
                    String link = dataSnapshot.child(friendID).child("Url").getValue().toString();
                    Picasso.with(ProfileActivity.this)
                            .load(link)
                            .fit()
                            .noFade()
                            .into(friendPic);
                }
                else {
                    String link = "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/ProfilePictures%2Fno-profile-photo1-300x200.jpg?alt=media&token=9ea688b0-e1b8-4935-afa1-8f0b3b41a83a";
                    Picasso.with(ProfileActivity.this)
                            .load(link)
                            .fit()
                            .noFade()
                            .into(friendPic);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //retrieve friend name
        DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(friendName).child("Id");
        dbF.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String friendID = dataSnapshot.getValue().toString();
                friendID = friendID;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //retrieve public playlists
        DatabaseReference pp = FirebaseDatabase.getInstance().getReference().child("PublicPlaylists");
        pp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(UserDetails.friendID)) {
                    for (com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.child(UserDetails.friendID).getChildren()) {
                        String publicP = snapshot.getValue().toString();
                        publicTitle.setText("Public Playlists");
                        publicPList.add(publicP);
                        RowItem item = new RowItem(R.drawable.playlist, publicP);
                        rowItems.add(item);
                        adapterProfile.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        publicPlaylists.setAdapter(adapterProfile);
        publicPlaylists.setClickable(true);

        //handle click on public playlist
        publicPlaylists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RowItem rowItem = (RowItem) parent.getItemAtPosition(position);
                final String playlist = rowItem.getTitle();
                Intent i = new Intent(ProfileActivity.this, SharedPlaylistSongs.class);
                i.putExtra("Uniqid", "FromProfile");
                i.putExtra("Playlist", playlist);
                i.putExtra("FriendId", friendID);
                i.putExtra("FriendName", friendName);
                UserDetails.friend = friendName;
                startActivity(i);
            }
        });

        //retrieve followers name
        DatabaseReference followersdb = FirebaseDatabase.getInstance().getReference().child("FollowersNames").child(friendID);
        followersdb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long size = dataSnapshot.getChildrenCount();
                        String fno = Long.toString(size);
                        followersNo.setText(fno);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //retrieve following users
        DatabaseReference followingdb = FirebaseDatabase.getInstance().getReference().child("Following").child(friendID);
        followingdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long size = dataSnapshot.getChildrenCount()/2;
                String fno = Long.toString(size);
                followingNo.setText(fno);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //handle click on follow button
        FloatingActionButton follow = (FloatingActionButton) findViewById(R.id.fab);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, "fullname: " + fullname, Toast.LENGTH_SHORT).show();
                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(toolbar.getTitle().toString()).child("Email");
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Following").child(user);
                mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserDetails.receiver = dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(username)){
                            Toast.makeText(ProfileActivity.this, "Already following " + username, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            final Map<String, Object> map = new HashMap<String, Object>();
                            map.put("Date", getDate());
                            mDatabase.child(username).updateChildren(map);
                            Toast.makeText(ProfileActivity.this, "You are now following " + username, Toast.LENGTH_SHORT).show();
                            addToFollowers(username);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //add to followers list
    private void addToFollowers(final String username) {
        followersdb = FirebaseDatabase.getInstance().getReference().child("Followers").child(username);
        followersdb.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String me = FirebaseAuth.getInstance().getCurrentUser().getUid();
                final Map<String, Object> map = new HashMap<String, Object>();
                map.put("Date", getDate());
                followersdb.child(me).updateChildren(map);
                Toast.makeText(ProfileActivity.this, "You are now a follower for " + username, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        followersdbN = FirebaseDatabase.getInstance().getReference().child("FollowersNames");
        followersdbN.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ID").child(username).child("Id");
                mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserDetails.fullname = dataSnapshot.getValue().toString();
                        Toast.makeText(ProfileActivity.this, "Friend ID" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

                DatabaseReference mfullname = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
                mfullname.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String myFullname = dataSnapshot.getValue().toString();
                        UserDetails.myname = myFullname;
                        sendNotification(myFullname);
                        followersdbN.child(UserDetails.fullname).push().setValue(myFullname);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    //handle menu item click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    //start new activity on click on back
    @Override
    public void onBackPressed(){
        Intent backMainTest = new Intent(this,RealTimeActivity.class);
        startActivity(backMainTest);
        finish();
    }

    //send new follower notification
    private void sendNotification(final String myname)
    {
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(username).child("Email");
        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.receiver = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;
                    if (RealTimeActivity.loggedEmail.equals(sender)) {
                        send_email = UserDetails.receiver;
                    } else {
                        send_email = sender;
                    }

                    try {
                        String jsonResponse;
                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic NmMxZDRiNjAtMzY5Ni00NDRhLThhZGEtODRkNmIzZTEzOWVm");
                        con.setRequestMethod("POST");
                        String strJsonBody = "{"
                                + "\"app_id\": \"99ce9cc9-d20d-4e6b-ba9b-de2e95d3ec00\","
                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"
                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"" + myname +" is now following you\"}"
                                + "}";

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);
                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);
                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Get date string.
     *
     * @return the string
     */
    public String getDate(){
        Date date = new Date();
        Date newDate = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String stringdate = dt.format(newDate);
        return stringdate;
    }
}