package com.qmul.nminoiu.tunein;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

/**
 * Created by nicoleta on 12/12/2017.
 */
public class Conversations extends AppCompatActivity {
    ListView convList;
    ArrayList<String> conversationsArList = new ArrayList<>();
    private ArrayAdapter<String> cadapter;
    private DatabaseReference db;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private DatabaseReference ref;
    Firebase recentsRef;
    private ConversationsAdapter adapter;
    private List<String> fromList;
    private List<String> timeList;
    private List<String> messageList;
    public static TextView track_title;
    private LinearLayout play_toolbar;
    private Button btn;
    private String url;
    private Handler mHandler;
    private String me;
    private DatabaseReference db1;
    private String value;
    private String sender;
    private Toolbar toolbar;
    List<DoubleRow> drowItem;
    private String ID;
    private ArrayAdapter<String> convadapter;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        cadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, conversationsArList);
        drowItem = new ArrayList<DoubleRow>();
        adapter = new ConversationsAdapter(this, drowItem);
        fromList = new ArrayList<>();
        timeList = new ArrayList<>();
        messageList = new ArrayList<>();
        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        play_toolbar.setClickable(true);
        track_title = (TextView) findViewById(R.id.track_title);
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String curUser = currentuser.getUid().toString();
        UserDetails.oldIntent="Conversations";
        db = FirebaseDatabase.getInstance().getReference().child("Users");
        db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(curUser);
        ID = firebaseAuth.getCurrentUser().getUid();
        sender = firebaseAuth.getCurrentUser().getEmail();
        convList = (ListView) findViewById(R.id.convList);
        convList.setAdapter(adapter);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFullname();
        recentsRef = new Firebase("https://tunein-633e5.firebaseio.com/RecentMessages/" + UserDetails.fullname);
        UserDetails.username = UserDetails.fullname;

        Intent i = getIntent();
        if(i.hasExtra("Song")){
            String title = i.getStringExtra("Song");
            track_title.setText(title);
        }

        //checking if media player is playing and setting playing bar visible
        if(mediaPlayer.isPlaying()){
            play_toolbar.setVisibility(View.VISIBLE);
            play_toolbar.bringToFront();
        } else play_toolbar.setVisibility(View.GONE);

        //getting currently playing song from Firebase
        DatabaseReference songTitleRef = FirebaseDatabase.getInstance().getReference().child("CurrentSong");
        songTitleRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(ID).exists()){
                    String song = dataSnapshot.child(ID).child("Song").getValue().toString();
                    track_title.setText(song);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //retrieving recent messages from Firebase
        ref = FirebaseDatabase.getInstance().getReference().child("RecentMessages").child(UserDetails.fullname);
        ref.orderByChild("time").addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String from = map.get("user").toString();
                String time =map.get("time").toString();
                String message = map.get("message").toString();
                if(message.length()>=60){
                    String newmess = message.substring(0,60);
                    String finmess = newmess+"...";
                    messageList.add(finmess);
                    fromList.add(from);
                    timeList.add(time);
                    DoubleRow item = new DoubleRow(R.drawable.ic_nextblack, from, getTime(time), finmess);
                    drowItem.add(0, item);
                    adapter.notifyDataSetChanged();
                } else {
                    messageList.add(message);
                    fromList.add(from);
                    timeList.add(time);
                    DoubleRow item = new DoubleRow(R.drawable.ic_nextblack, from, getTime(time), message);
                    drowItem.add(0, item);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //handling click on playbar
        play_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(Conversations.this, MusicPlayerActivity.class);
                intent_info.putExtra("Uniqid", "FromConversations");
                if (mediaPlayer.isPlaying()) {
                    intent_info.putExtra("Song", track_title.getText().toString());
                }
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            }
        });
    }

    //get current time
    private String getTime(String time) {
        Calendar calendar = Calendar.getInstance();
        Long longTime = Long.valueOf(time);
        Date today = new Date(System.currentTimeMillis());
        Date otherDate = new Date(longTime);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat hfor = new SimpleDateFormat("HH:mm");
        String todayD = formatter.format(today);
        String otherDateD = formatter.format(otherDate);

        if(todayD.equals(otherDateD)){
            return hfor.format(longTime);
        } else return otherDateD;
    }

    //pressing back arrow and starting previous intent
    @Override
    public void onBackPressed() {
        Intent i = new Intent(Conversations.this, RealTimeActivity.class);
        i.putExtra("ID", "FromConversations");
        i.putExtra("Song", track_title.getText().toString());
        startActivity(i);
        finish();
    }

    //creating new message menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_message, menu);
        return true;
    }

    // Handle action bar item clicks. The action bar will
    // automatically handle clicks on the Home/Up
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.newmessage) {
            Intent i = new Intent(this, FollowersActivity.class);
            i.putExtra("Uniqid", "FromConversations");
            UserDetails.oldIntent="FromConversations";
            i.putExtra("Myname", UserDetails.username);
            if(mediaPlayer.isPlaying()){
                i.putExtra("Song", track_title.getText().toString());
            }
            startActivity(i);
        }
        else onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Gets fullname.
     */
    public void getFullname() {
        FirebaseAuth fb;
        fb = FirebaseAuth.getInstance();
        String ID;
        ID = fb.getCurrentUser().getUid();
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
    }

    /**
     * Update progress bar.
     */
    public void updateProgressBar() {
        RealTimeActivity sa = new RealTimeActivity();
        mHandler.postDelayed(sa.mUpdateTimeTask, 100);
    }

    /**
     * Gets followers.
     *
     * @param fullname the fullname
     * @param mysong   the mysong
     */
    public void getFollowers(String fullname, final String mysong) {

        final ArrayAdapter<String> fadapter;
        final List<String> myFollowers = new ArrayList<>();
        fadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myFollowers);
        DatabaseReference fdb;
        fdb = FirebaseDatabase.getInstance().getReference().child("Followers").child(fullname);
        fdb.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String value = snapshot.getKey();
                    myFollowers.add(value);
                    UserDetails.myFollowers.add(value);
                    fadapter.notifyDataSetChanged();
                }

                addToHome(UserDetails.myFollowers, mysong);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Open player page.
     *
     * @param v the v
     */
    public void openPlayerPage(View v) {
        Intent i = new Intent(Conversations.this, MusicPlayerActivity.class);
        startActivity(i);
    }

    /**
     * Add to home.
     *
     * @param myvalue the myvalue
     * @param mysong  the mysong
     */
    public void addToHome(List<String> myvalue, final String mysong) {

        String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");
        mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserDetails.myname = dataSnapshot.getValue().toString();
                me = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        for (int i = 0; i <= myvalue.size() - 1; i++) {

            Firebase ref4 = new Firebase("https://tunein-633e5.firebaseio.com/Homepage/" + myvalue.get(i));
            Map<String, Object> uinfo = new HashMap<>();
            uinfo.put("Song", mysong);
            uinfo.put("Picture", UserDetails.picturelink);
            ref4.child(UserDetails.fullname).updateChildren(uinfo);
        }
    }

    /**
     * Play from pause.
     *
     * @param time the time
     */
    public void playFromPause(Integer time) {

        mediaPlayer.seekTo(time);
        mediaPlayer.start();
        TextView title = (TextView) findViewById(R.id.track_title);
        String songtitle = title.getText().toString();
        getFollowers(UserDetails.fullname, songtitle);
        Button btn = (Button) this.findViewById(R.id.button);
        btn.setBackgroundResource(R.drawable.ic_media_pause);
    }

    /**
     * Play pause music.
     *
     * @param v the v
     */
    public void playPauseMusic(View v) {

        Integer length = mediaPlayer.getCurrentPosition();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Button btn = (Button) this.findViewById(R.id.button);
            btn.setBackgroundResource(R.drawable.ic_media_play);
            eraseFromFirebase();

        } else {

            playFromPause(length);
            Button btn = (Button) this.findViewById(R.id.button);
            btn.setBackgroundResource(R.drawable.ic_media_pause);
        }

        mediaPlayer.getCurrentPosition();
    }

    /**
     * Erase from firebase.
     */
    public void eraseFromFirebase() {
        final RealTimeActivity sa = new RealTimeActivity();
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Homepage");
        mDatabase1.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        String v;
                        for (com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            v = snapshot.getKey();
                            if (dataSnapshot.child(v).hasChild(sa.getMyFullname(ID))) {
                                dataSnapshot.child(v).child(sa.getMyFullname(ID)).getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    /**
     * Get bar title string.
     *
     * @return the string
     */
    public String getBarTitle(){
        return getSupportActionBar().getTitle().toString();
    }

}
