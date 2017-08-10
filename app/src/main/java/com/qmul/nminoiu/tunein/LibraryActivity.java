package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.qmul.nminoiu.tunein.SettingsActivity.mediaPlayer;


public class LibraryActivity extends AppCompatActivity {

    private ListView recentSongs;
    private ArrayList<String> recents = new ArrayList<>();
    private DatabaseReference recentsRef;
    public String ID;
    private FirebaseAuth firebaseAuth;
    private ArrayAdapter<String> recentsadapter;
    private String song;
    private TextView track_title;
    private LinearLayout play_toolbar;
    private Button btn;
    private String url;
    private Handler mHandler;
    private List myFollowers;
    private String me;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LibraryActivity.this, Users.class);
                startActivity(i);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();
        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        play_toolbar.setClickable(true);
        btn = (Button) findViewById(R.id.button);
        myFollowers = new ArrayList<>();


        recentsRef = FirebaseDatabase.getInstance().getReference().child("RecentlyPlayed").child(ID);
        recentsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String recentSongs = dataSnapshot.getValue(String.class);
                Toast.makeText(LibraryActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                recents.add(recentSongs);
                recentsadapter.notifyDataSetChanged();
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

        recentSongs = (ListView) findViewById(R.id.recentSongs);
        recentsadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recents);
        recentSongs.setAdapter(recentsadapter);
        recentSongs.setClickable(true);

        recentSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                song = ((TextView) view).getText().toString();
                play_toolbar.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
                play_toolbar.requestLayout();
                play_toolbar.bringToFront();
                track_title = (TextView) findViewById(R.id.track_title);
                track_title.setText(song);
                hideSoftKeyboard(LibraryActivity.this);

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase songRef = ref.child("URL").child(song);

                songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                        for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                            btn.setBackgroundResource(R.drawable.ic_media_pause);
                            play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
                            play_toolbar.bringToFront();
                            play_toolbar.requestLayout();
                            play_toolbar.invalidate();
                            url = String.valueOf(dsp.getValue());
                            startMusic(url, song);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        play_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LibraryActivity.this, AndroidBuildingMusicPlayerActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, SettingsActivity.class);
        startActivity(backMainTest);
        finish();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void startMusic(String link, String song) {
        play_toolbar.setVisibility(View.VISIBLE);
        play_toolbar.bringToFront();

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

        getFullname();
        getFollowers(UserDetails.fullname, song);

    }

    public void updateProgressBar() {
        SettingsActivity sa = new SettingsActivity();
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
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //myFollowers.clear();
                    String value = snapshot.getKey();
                    myFollowers.add(value);
                    UserDetails.myFollowers.add(value);
                    //addToFirebaseHome(value, mysong);
                    fadapter.notifyDataSetChanged();
                }

                addToHome(UserDetails.myFollowers, mysong);
                //Toast.makeText(SettingsActivity.this, UserDetails.myFollowers.size() + " is the size", Toast.LENGTH_LONG).show();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getFullname() {

        FirebaseAuth fb;
        fb = FirebaseAuth.getInstance();

        String ID;
        ID = fb.getCurrentUser().getUid();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

        mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                //Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void addToHome(List<String> myvalue, final String mysong) {

        String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");

        mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.myname = dataSnapshot.getValue().toString();
                me = dataSnapshot.getValue().toString();
                //Toast.makeText(SettingsActivity.this, UserDetails.myname + " is finally my fullname", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        for (int i = 0; i <= myvalue.size() - 1; i++) {

            Firebase ref4 = new Firebase("https://tunein-633e5.firebaseio.com/Homepage/" + myvalue.get(i));
            Map<String, Object> uinfo = new HashMap<>();
            uinfo.put("Song", mysong);
            ref4.child(UserDetails.fullname).updateChildren(uinfo);


        }
    }

    public void playFromPause(Integer time, String link) {
        play_toolbar.setVisibility(View.VISIBLE);
        play_toolbar.bringToFront();
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(link);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
            //  updateProgressBar();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.seekTo(time);
        mediaPlayer.start();
        TextView title = (TextView) findViewById(R.id.track_title);
        String songtitle = title.getText().toString();
        //song = ((TextView) view).getText().toString();
        getFollowers(UserDetails.fullname, songtitle);

        Button btn = (Button) this.findViewById(R.id.button);
        btn.setBackgroundResource(R.drawable.ic_media_pause);
        //nowPlaying(song);
    }

    public void playPauseMusic(View v) {

        play_toolbar.setVisibility(View.VISIBLE);
        play_toolbar.bringToFront();

        Integer length = mediaPlayer.getCurrentPosition();
        if (mediaPlayer.isPlaying()) {
            length = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            Button btn = (Button) this.findViewById(R.id.button);
            btn.setBackgroundResource(R.drawable.ic_media_play);
            eraseFromFirebase();

        } else {
            mediaPlayer.seekTo(length);
            playFromPause(length, url);
            Button btn = (Button) this.findViewById(R.id.button);
            btn.setBackgroundResource(R.drawable.ic_media_pause);
        }

        mediaPlayer.getCurrentPosition();
        //AndroidBuildingMusicPlayerActivity.songProgressBar.setProgress(0);
        //AndroidBuildingMusicPlayerActivity.songProgressBar.setMax(100);
//            new AndroidBuildingMusicPlayerActivity().updateProgressBar();
    }

    public void eraseFromFirebase() {
        final SettingsActivity sa =  new SettingsActivity();
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Homepage");
        mDatabase1.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String v;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            v = snapshot.getKey();
                            //Toast.makeText(SettingsActivity.this, "in erase" + dataSnapshot.child(snapshot.child(v).getKey().toString()).getKey().toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(SettingsActivity.this, "v" + v, Toast.LENGTH_SHORT).show();
                            //getFulname();
                            if (dataSnapshot.child(v).hasChild(sa.getMyFullname(ID))) {
                               // Toast.makeText(SettingsActivity.this, "in if" + snapshot.getValue(), Toast.LENGTH_SHORT).show();

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
}
