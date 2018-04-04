package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

/**
 * created by nicoleta on 19/09/2017
 */
public class LibraryActivity extends AppCompatActivity{

    private ListView recentSongs;
    private ArrayList<String> recents = new ArrayList<>();
    private DatabaseReference recentsRef;
    public String ID;
    private FirebaseAuth firebaseAuth;
    private ArrayAdapter<String> recentsadapter;
    private AdapterRecentlyplayed adapterRecentlyplayed;
    private String song;
    public static TextView track_title;
    private LinearLayout play_toolbar;
    private Button btn;
    private String url;
    private Handler mHandler;
    private List myFollowers;
    private String me;
    private FloatingActionButton fab;
    private RelativeLayout playlists;
    private RelativeLayout downloads;
    private RelativeLayout favourites;
    private RelativeLayout songs;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    private SimpleGestureFilter detector;
    private DatabaseReference mDatabase;
    private List<RowItem> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();
        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        play_toolbar.setClickable(true);
        btn = (Button) findViewById(R.id.button);
        playlists = (RelativeLayout) findViewById(R.id.playlists);
        favourites = (RelativeLayout) findViewById(R.id.favourites);
        downloads = (RelativeLayout) findViewById(R.id.downloads);
        songs = (RelativeLayout) findViewById(R.id.songs);
        track_title = (TextView) findViewById(R.id.track_title);
        myFollowers = new ArrayList<>();
        rowItems = new ArrayList<RowItem>();
        MusicPlayerActivity.songs.clear();
        MusicPlayerActivity.urls.clear();
        adapterRecentlyplayed = new AdapterRecentlyplayed(this, rowItems);

        //retrieve current song from Firebase
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

        //set play bar visible is music is playing
        if(mediaPlayer.isPlaying()){
            play_toolbar.setVisibility(View.VISIBLE);
        } else play_toolbar.setVisibility(View.GONE);

        //retrieve recent songs from Firebase
        recentsRef = FirebaseDatabase.getInstance().getReference().child("RecentlyPlayed").child(ID);
        recentsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String recentSongs = dataSnapshot.getValue(String.class);
                getUrl(recentSongs);
                MusicPlayerActivity.songs.add(recentSongs);
                recents.add(recentSongs);
                RowItem item = new RowItem(R.drawable.options, recentSongs);
                rowItems.add(item);
                adapterRecentlyplayed.notifyDataSetChanged();
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

        //attach custom adapter to recent songs list
        recentSongs = (ListView) findViewById(R.id.recentSongs);
        recentsadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recents);
        recentSongs.setAdapter(adapterRecentlyplayed);
        recentSongs.setClickable(true);


        //handle click on playlists tab
        playlists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LibraryActivity.this, MyPlaylists.class);
                if(mediaPlayer.isPlaying()) {
                    i.putExtra("Song", track_title.getText().toString());
                }
                startActivity(i);
            }
        });

        //handle click on songs tab
        songs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LibraryActivity.this, Songs.class);
                if(mediaPlayer.isPlaying()){
                   String song = track_title.getText().toString();
                    i.putExtra("Song", song);
                }
                startActivity(i);
            }
        });

        //handle click on favourites tab
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LibraryActivity.this, Favourites.class);
                if(mediaPlayer.isPlaying()) {
                   i.putExtra("Song", track_title.getText().toString());
                }
                startActivity(i);
            }
        });

        //handle click on downloads tab
        downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LibraryActivity.this, Downloads.class);
                if(mediaPlayer.isPlaying()) {
                    i.putExtra("Song", track_title.getText().toString());

                }
                startActivity(i);
            }
        });

        //handle click on recent songs list
        recentSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RowItem rowItem = (RowItem) parent.getItemAtPosition(position);
                final String song = rowItem.getTitle();
                play_toolbar.setVisibility(View.VISIBLE);
                SongSingleton.getInstance().setSongName(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);
                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase songRef = ref.child("URL").child(song);
                songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                            url = String.valueOf(dsp.getValue());
                            startMusic(url, song);
                            btn.setBackgroundResource(R.drawable.ic_media_pause);
                            getFollowers(UserDetails.fullname, song);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        //handle clcick on playbar
        play_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(LibraryActivity.this, MusicPlayerActivity.class);
                intent_info.putExtra("Uniqid", "FromLibrary");
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            }
        });
    }

    //handle click on option menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    //handle click on back icon and open previous activity
    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, RealTimeActivity.class);
        backMainTest.putExtra("ID", "FromLibrary");
        backMainTest.putExtra("Song", track_title.getText().toString());
        startActivity(backMainTest);
        finish();
    }

    /**
     * Hide soft keyboard.
     * @param activity the activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Start music.
     * @param link the link
     * @param song the song
     */
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
            updateProgressBar();
        }
        Button btn = (Button) this.findViewById(R.id.button);
        btn.setBackgroundResource(R.drawable.ic_media_pause);
        mediaPlayer.start();
        if (!recents.contains(song)) {
            Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("RecentlyPlayed").child(ID);
            likedRef.push().setValue(song);
        }
    }

    /**
     * Update progress bar.
     */
    public void updateProgressBar() {
        RealTimeActivity sa = new RealTimeActivity();
        mHandler.postDelayed(sa.mUpdateTimeTask, 100);
    }

    /**
     * Gets url.
     *
     * @param song the song
     */
    public void getUrl(String song) {

            Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
            Firebase songRef = ref.child("URL").child(song);
            songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                        url = String.valueOf(dsp.getValue());
                        MusicPlayerActivity.urls.add(url);
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }

    /**
     * Gets followers.
     *
     * @param fullname the fullname
     * @param mysong   the mysong
     */
    public void getFollowers(String fullname, final String mysong) {
        final ArrayAdapter<String> fadapter;
        UserDetails.mysong = mysong;
        fadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myFollowers);
        DatabaseReference fdb;
        fdb = FirebaseDatabase.getInstance().getReference().child("Followers").child(fullname);
        fdb.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String value = snapshot.getKey();
                    myFollowers.add(value);
                    UserDetails.myFollowers.add(value);
                    fadapter.notifyDataSetChanged();
                }
                eraseFromRecents(mysong);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Erase from recents.
     *
     * @param mysong the mysong
     */
    public void eraseFromRecents(String mysong) {
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("FriendsActivity");
        mDatabase1.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String v;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            v = snapshot.getKey();
                            if (dataSnapshot.child(v).hasChild(getMyFullname(ID))) {
                                dataSnapshot.child(v).child(getMyFullname(ID)).getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        addToHome(UserDetails.myFollowers, mysong);
    }

    /**
     * Gets fullname.
     *
     * @param id the id
     * @return the my fullname
     */
    public String getMyFullname(String id) {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(id).child("Name");

        mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
        return UserDetails.fullname;
    }


    /**
     * Open player page.
     *
     * @param v the v
     */
    public void openPlayerPage(View v) {
        Intent i = new Intent(LibraryActivity.this, MusicPlayerActivity.class);
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
            public void onDataChange(DataSnapshot dataSnapshot) {
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
            if(!RealTimeActivity.checkBox.isChecked()) {
                uinfo.put("Song", mysong);
                if (!UserDetails.picturelink.equals("")) {
                    uinfo.put("Picture", UserDetails.picturelink);
                } else {
                    uinfo.put("Picture", "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/ProfilePictures%2Fdefault-user.png?alt=media&token=98996406-225b-4572-a494-b6306ce9a288");
                }
                ref4.child(UserDetails.fullname).updateChildren(uinfo);
            } else {
                eraseFromFirebase();
            }
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
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Homepage");
        mDatabase1.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String v;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            v = snapshot.getKey();
                            if (dataSnapshot.child(v).hasChild(getMyFullname(ID))) {
                                dataSnapshot.child(v).child(getMyFullname(ID)).getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        addToFriendActivity(UserDetails.myFollowers, UserDetails.mysong);
    }

    /**
     * Add to friend activity.
     *
     * @param myvalue the myvalue
     * @param mysong  the mysong
     */
    public void addToFriendActivity(List<String> myvalue, final String mysong) {

        String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");
        mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.myname = dataSnapshot.getValue().toString();
                me = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        for (int i = 0; i <= myvalue.size() - 1; i++) {
            Firebase refAct = new Firebase("https://tunein-633e5.firebaseio.com/FriendsActivity/" + myvalue.get(i));
            Map<String, Object> udet = new HashMap<>();
            if(!RealTimeActivity.checkBox.isChecked()) {
                udet.put("Song", mysong);
                udet.put("Time", System.currentTimeMillis());
                if (!UserDetails.picturelink.equals("")) {
                    udet.put("Picture", UserDetails.picturelink);
                } else {
                    udet.put("Picture", "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/ProfilePictures%2Fdefault-user.png?alt=media&token=98996406-225b-4572-a494-b6306ce9a288");
                }
                refAct.child(UserDetails.fullname).updateChildren(udet);
            }
        }
    }
}




