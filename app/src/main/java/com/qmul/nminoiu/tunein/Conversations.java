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

public class Conversations extends AppCompatActivity {
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    // ProgressDialog pd;
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

    private String song;
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
        btn = (Button) findViewById(R.id.button);
        track_title = (TextView) findViewById(R.id.track_title);
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String curUser = currentuser.getUid().toString();

        if(mediaPlayer.isPlaying()) {
            String song = track_title.getText().toString();
        }
        UserDetails.oldIntent="Conversations";

        Intent i = getIntent();
        if(i.hasExtra("Song")){
            String title = i.getStringExtra("Song");
            track_title.setText(title);
        }
        if(mediaPlayer.isPlaying()){
            play_toolbar.setVisibility(View.VISIBLE);
            play_toolbar.bringToFront();
            //track_title.setText(UserDetails.playingSongName);
        } else play_toolbar.setVisibility(View.GONE);

        db = FirebaseDatabase.getInstance().getReference().child("Users");
        db1 = FirebaseDatabase.getInstance().getReference().child("Users").child(curUser);
        ID = firebaseAuth.getCurrentUser().getUid();
        sender = firebaseAuth.getCurrentUser().getEmail();

        convList = (ListView) findViewById(R.id.convList);
        convList.setAdapter(adapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recentsRef = new Firebase("https://tunein-633e5.firebaseio.com/RecentMessages/" + UserDetails.fullname);
        UserDetails.username = UserDetails.fullname;
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference songTitleRef = FirebaseDatabase.getInstance().getReference().child("CurrentSong");
        songTitleRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(ID).exists()){
                    String song = dataSnapshot.child(ID).child("Song").getValue().toString();
                    track_title.setText(song);
//                    Toast.makeText(LibraryActivity.this, "song is " + song, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ref = FirebaseDatabase.getInstance().getReference().child("RecentMessages").child(UserDetails.fullname);
        ref.orderByChild("time").addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String message = map.get("message").toString();
                String from = map.get("user").toString();
                String time =map.get("time").toString();
//                Toast.makeText(Conversations.this, from, Toast.LENGTH_SHORT).show();
                fromList.add(from);
                timeList.add(time);
                messageList.add(message);
                DoubleRow item = new DoubleRow(R.drawable.ic_nextblack, from, getTime(time), message);
                drowItem.add(0, item);
                adapter.notifyDataSetChanged();



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
//        recentsRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Map map = dataSnapshot.getValue(Map.class);
//                String message = map.get("message").toString();
//                String from = map.get("user").toString();
//                String time =map.get("time").toString();
////                //Toast.makeText(Conversations.this, "from "+ from, Toast.LENGTH_LONG).show();
//
//                if(timeList.size() == 1 && Long.valueOf(timeList.get(0)) >= Long.valueOf(time)) {
//                    timeList.add(0, time);
//                    fromList.add(0, from);
//                    messageList.add(0, message);
//                } else {
//                    for(int i = 0; i < timeList.size()-1; i++) {
//                        if(Long.valueOf(time) >= Long.valueOf(timeList.get(i)) && Long.valueOf(time) <= Long.valueOf(timeList.get(i+1))) {
//                            timeList.add(i, time);
//                            fromList.add(i, from);
//                            messageList.add(i, message);
//
//                        }
//                    }
//                    // number is the largest seen; add it to the end.
//                     timeList.add(time);
//                     fromList.add(from);
//                     messageList.add(message);
//
//                 }
//                for(int i=0; i<=timeList.size()-1;i++){
//                    DoubleRow item = new DoubleRow(R.drawable.ic_nextblack, fromList.get(i), timeList.get(i), messageList.get(i));
//                    drowItem.add(item);
//                    adapter.notifyDataSetChanged();
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });

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

//            String hm = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(longTime),
//                    TimeUnit.MILLISECONDS.toMinutes(longTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(longTime)));

            return hfor.format(longTime);
        } else return otherDateD;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Conversations.this, RealTimeActivity.class);
        i.putExtra("ID", "FromConversations");
        i.putExtra("Song", track_title.getText().toString());
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

    public void startMusic(String link, String song) {
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            //   updateProgressBar();
        }
        Button btn = (Button) this.findViewById(R.id.button);
        btn.setBackgroundResource(R.drawable.ic_media_pause);
        mediaPlayer.start();

//        if (!recents.contains(song)) {
//            Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("RecentlyPlayed").child(ID);
//            likedRef.push().setValue(song);
//        }
    }

    public void updateProgressBar() {
        RealTimeActivity sa = new RealTimeActivity();
        mHandler.postDelayed(sa.mUpdateTimeTask, 100);
    }

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
                    //myFollowers.clear();
                    String value = snapshot.getKey();
                    myFollowers.add(value);
                    UserDetails.myFollowers.add(value);
                    //addToFirebaseHome(value, mysong);
                    fadapter.notifyDataSetChanged();
                }

                addToHome(UserDetails.myFollowers, mysong);
                //Toast.makeText(RealTimeActivity.this, UserDetails.myFollowers.size() + " is the size", Toast.LENGTH_LONG).show();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void openPlayerPage(View v) {
        Intent i = new Intent(Conversations.this, MusicPlayerActivity.class);
        startActivity(i);
    }

    public void addToHome(List<String> myvalue, final String mysong) {

        String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");

        mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserDetails.myname = dataSnapshot.getValue().toString();
                me = dataSnapshot.getValue().toString();
                //Toast.makeText(RealTimeActivity.this, UserDetails.myname + " is finally my fullname", Toast.LENGTH_SHORT).show();
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

    public void playFromPause(Integer time) {

        mediaPlayer.seekTo(time);
        mediaPlayer.start();
        TextView title = (TextView) findViewById(R.id.track_title);
        //UserDetails.playingSongName = track_title.getText().toString();

        String songtitle = title.getText().toString();
        //song = ((TextView) view).getText().toString();
        getFollowers(UserDetails.fullname, songtitle);

        Button btn = (Button) this.findViewById(R.id.button);
        btn.setBackgroundResource(R.drawable.ic_media_pause);
    }

    public void playPauseMusic(View v) {

        Integer length = mediaPlayer.getCurrentPosition();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Button btn = (Button) this.findViewById(R.id.button);
            btn.setBackgroundResource(R.drawable.ic_media_play);
            eraseFromFirebase();

        } else {

            //mediaPlayer.seekTo(length);
            playFromPause(length);
            Button btn = (Button) this.findViewById(R.id.button);
            btn.setBackgroundResource(R.drawable.ic_media_pause);
        }

        mediaPlayer.getCurrentPosition();

        //MusicPlayerActivity.songProgressBar.setProgress(0);
        //MusicPlayerActivity.songProgressBar.setMax(100);
//            new MusicPlayerActivity().updateProgressBar();
    }

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
                            //Toast.makeText(RealTimeActivity.this, "in erase" + dataSnapshot.child(snapshot.child(v).getKey().toString()).getKey().toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(RealTimeActivity.this, "v" + v, Toast.LENGTH_SHORT).show();
                            //getFulname();
                            if (dataSnapshot.child(v).hasChild(sa.getMyFullname(ID))) {
                                // Toast.makeText(RealTimeActivity.this, "in if" + snapshot.getValue(), Toast.LENGTH_SHORT).show();

                                // dataSnapshot.child(v).getRef().removeValue();
                                dataSnapshot.child(v).child(sa.getMyFullname(ID)).getRef().removeValue();

                                //names.remove(getMyFullname(ID));
                                // title.remove()
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public String getBarTitle(){
        return getSupportActionBar().getTitle().toString();
    }



}