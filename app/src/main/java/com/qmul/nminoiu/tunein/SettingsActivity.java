package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.onesignal.OneSignal;

import android.view.View.OnClickListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener {


    MaterialSearchView searchView;
    LinearLayout searchLayout;
    private TextView email;
    private FirebaseAuth firebaseAuth;
    private String username = "";
    private static final String TAG = "MainActivity";
    private ArrayList<String> songs = new ArrayList<>();
    private ArrayList<String> users = new ArrayList<>();
    private DatabaseReference db;
    private DatabaseReference db1;
    private DatabaseReference db2;
    private ArrayAdapter<String> sadapter;
    private ArrayAdapter<String> uadapter;
    private ArrayAdapter<String> fadapter;
    private ListView slistView;
    private ListView ulistView;
    private LinearLayout play_toolbar;
    public TextView track_title;
    public TextView sName;
    public static MediaPlayer mediaPlayer;
    private StorageReference storage;
    private boolean playPause;
    private boolean intialStage = true;
    private Button btn;
    private boolean play;
    private boolean pause;
    public static String url;
    private Handler mResultHandler;
    public static String song;
    private Handler mHandler;
    private FirebaseAuth firebaseAuth1;
    private FirebaseUser user;
    public static String loggedEmail;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabase2;
    private DatabaseReference mDatabase3;
    private DatabaseReference mDatabase4;
    private DatabaseReference mDatabase5;
    private DatabaseReference mDatabase6;
    private DatabaseReference mDatabase7;
    private DatabaseReference mDatabase8;
    private DatabaseReference mDatabase9;
    private DatabaseReference timeref;
    public User myuser;
    public File storagePath;
    LinearLayout nowPlayingLayout;
    private FirebaseStorage mStorage;

    private ImageButton syncButton;
    private ImageButton tuneOutBtn;
    private TextView ptextview;
    private TextView stextview;

    public String fullname;
    public boolean following;
    //public boolean nowPlaying;
    public TextView songText;
    public TextView nameText;
    public ImageButton fab;
    public ImageButton fab1;
    public ImageButton youtube;
    public ImageButton download;
    public ImageButton blackHeart;
    public ImageButton redHeart;


    private DatabaseReference fdb;
    private List<String> myFollowers;
    public String ID;
    public String me;
    public String text;
    public String addr;
    private String time;
    private Boolean isTunned = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setContentView(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        songText = (TextView) findViewById(R.id.songName);
        nameText = (TextView) findViewById(R.id.name);
        ptextview = (TextView) findViewById(R.id.ptextView);
        stextview = (TextView) findViewById(R.id.stextView);
        youtube = (ImageButton) findViewById(R.id.youtube);
        download = (ImageButton) findViewById(R.id.download);



        fab = (ImageButton) findViewById(R.id.fab);
        fab1 = (ImageButton) findViewById(R.id.fab1);
        syncButton = (ImageButton) findViewById(R.id.syncButton);
        fab.bringToFront();
        fab1.bringToFront();
        myFollowers = new ArrayList<>();
        myuser = new User();
        nowPlayingLayout = (LinearLayout) findViewById(R.id.playing);
        tuneOutBtn = (ImageButton) findViewById(R.id.tuneout_btn);
        blackHeart = (ImageButton) findViewById(R.id.blackHeart);
        redHeart = (ImageButton) findViewById(R.id.redHeart);

        firebaseAuth1 = FirebaseAuth.getInstance();
        user = firebaseAuth1.getCurrentUser();

        loggedEmail = user.getEmail();
        ID = firebaseAuth1.getCurrentUser().getUid();
        OneSignal.sendTag("User_ID", loggedEmail);
        mStorage = FirebaseStorage.getInstance();

        OneSignal.startInit(this).init();

        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String personNowPlaying = getPersonNowPlaying();
                sendTimeRequest(personNowPlaying);
                syncButton.setVisibility(View.GONE);
                tuneOutBtn.setVisibility(View.VISIBLE);
                }

            });

        tuneOutBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tuneOutBtn.setVisibility(View.GONE);
                syncButton.setVisibility(View.VISIBLE);
                mediaPlayer.stop();

                DatabaseReference reqdb = FirebaseDatabase.getInstance().getReference().child("TimeRequest").child(ID);
                reqdb.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                dataSnapshot.getRef().removeValue();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                            }
                        });

                return false;
            }
        });

        //setting notification tags for current user
        OneSignal.sendTag("User_ID", loggedEmail);
        NowPlayingItem item = new NowPlayingItem();
        //getTimeFromFirebase();

        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Following").child(ID);
//            checkNowPlaying();
//            checkFollowing();
//            displayOnHomepage();

        //media player
        btn = (Button) findViewById(R.id.button);
        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        mediaPlayer = new MediaPlayer();

        //getting users and songs from database
        db = FirebaseDatabase.getInstance().getReference().child("Users");
        db1 = FirebaseDatabase.getInstance().getReference().child("Songs");
        db2 = FirebaseDatabase.getInstance().getReference().child("Test");

        String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //ID = user.getUid();
        mDatabase5 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

        mDatabase5.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                //Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
                //getFollowers(UserDetails.fullname);
                //Toast.makeText(SettingsActivity.this, myFollowers.size() + " followers", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ulistView = (ListView) findViewById(R.id.plistView);
        uadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        ulistView.setAdapter(uadapter);

        slistView = (ListView) findViewById(R.id.slistView);
        sadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs);
        slistView.setAdapter(sadapter);

        ulistView.setClickable(true);
        slistView.setClickable(true);
        play_toolbar.setClickable(true);

        mDatabase9 = FirebaseDatabase.getInstance().getReference().child("TimeRequest");

        mDatabase9.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String otherUser;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    otherUser = dataSnapshot.getKey();
                    //Toast.makeText(SettingsActivity.this, "in for" + snapshot.getValue(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(SettingsActivity.this, "in for my fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();

                    if (snapshot.getValue().equals(UserDetails.fullname)) {
                        addTimeToFirebase(otherUser);
                        //getCurrentPlayingTime();
                        Toast.makeText(SettingsActivity.this, "has child" + UserDetails.fullname, Toast.LENGTH_SHORT).show();

                    }
                }
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


        mDatabase8 = FirebaseDatabase.getInstance().getReference().child("Homepage").child(ID);

        mDatabase8.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    text = dataSnapshot.getKey();
                    NowPlayingItem item = new NowPlayingItem(text);
                    nowPlayingLayout.setVisibility(View.VISIBLE);
                    item.setSong(text);
                    nameText.setText(text);

                    Toast.makeText(SettingsActivity.this, text + " is first friend name", Toast.LENGTH_SHORT).show();

                    String mysong = dataSnapshot.child("Song").getValue().toString();
                    songText.setText(mysong);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    text = dataSnapshot.getKey();
                    NowPlayingItem item = new NowPlayingItem(text);
                    nowPlayingLayout.setVisibility(View.VISIBLE);
                    item.setSong(text);
                    nameText.setText(text);

                    Toast.makeText(SettingsActivity.this, text + " is first friend name", Toast.LENGTH_SHORT).show();

                    String mysong = dataSnapshot.child("Song").getValue().toString();
                    songText.setText(mysong);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                nowPlayingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ///try
        timeref = FirebaseDatabase.getInstance().getReference().child("TimeAnswer");

        timeref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String x = dataSnapshot.getKey();
                    Toast.makeText(SettingsActivity.this, "id is " + x , Toast.LENGTH_SHORT).show();
                    Toast.makeText(SettingsActivity.this, "child is " + snapshot.getValue().toString() , Toast.LENGTH_SHORT).show();

                    //Toast.makeText(SettingsActivity.this, "in for my fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();

                    if (!x.equals(ID)) {
                        String time = snapshot.getValue().toString();
                        final int newtime = Integer.parseInt(time);
                        Toast.makeText(SettingsActivity.this, time + " in child added ", Toast.LENGTH_SHORT).show();
                        String songtosync = getSongName();

                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase songRef = ref.child("URL").child(songtosync);

                        songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                            @Override
                            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    url = String.valueOf(dsp.getValue());
                                    UserDetails.song = url;
                                    Toast.makeText(SettingsActivity.this, UserDetails.song + " is the url", Toast.LENGTH_SHORT).show();
                                    playMusic(newtime);
                                    //getTimeFromFirebase();
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });


                        //getUrl(songtosync);
                       // playMusic(newtime);
                    }
                }
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String x = dataSnapshot.getKey();
                    Toast.makeText(SettingsActivity.this, "id is " + x , Toast.LENGTH_SHORT).show();
                    Toast.makeText(SettingsActivity.this, "child is " + snapshot.getValue().toString() , Toast.LENGTH_SHORT).show();

                    //Toast.makeText(SettingsActivity.this, "in for my fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();

                    if (!x.equals(ID)) {
                        String time = snapshot.getValue().toString();
                        final int newtime = Integer.parseInt(time);
                        Toast.makeText(SettingsActivity.this, time + " in child added ", Toast.LENGTH_SHORT).show();
                        String songtosync = getSongName();

                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase songRef = ref.child("URL").child(songtosync);

                        songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                            @Override
                            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    url = String.valueOf(dsp.getValue());
                                    UserDetails.song = url;
                                    Toast.makeText(SettingsActivity.this, UserDetails.song + " is the url", Toast.LENGTH_SHORT).show();
                                    playMusic(newtime);
                                    //getTimeFromFirebase();
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });


                        //getUrl(songtosync);
                        // playMusic(newtime);
                    }
                }
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



        //event listener for users
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

        //event listener for songs
        db1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                songs.add(value);
                sadapter.notifyDataSetChanged();
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

        db.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        db1.child("Songs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    // Toast.makeText(SettingsActivity.this, dataSnapshot.toString().trim(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //on create


        //homepage toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Now Playing");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));


        //loading users in plistView
        ulistView = (ListView) findViewById(R.id.plistView);
        uadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        ulistView.setAdapter(uadapter);

        //loading songs in slistView
        slistView = (ListView) findViewById(R.id.slistView);
        sadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, songs);

        //add click listenerr for users in ulistView
        ulistView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String user = ((TextView) view).getText().toString();
                play_toolbar.setVisibility(View.GONE);
                //nowPlayingLayout.setVisibility(View.GONE);
                play_toolbar.requestLayout();
                hideSoftKeyboard(SettingsActivity.this);

                UserDetails.username = user;

                Intent i = new Intent(SettingsActivity.this, ScrollingActivity.class);
                startActivity(i);

            }
        });

        //add click listener for play bar
        play_toolbar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, AndroidBuildingMusicPlayerActivity.class);
                startActivity(i);
            }
        });

        //add click listeners for songs listview
        //showing clicked song in play toolbar.
        slistView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                song = ((TextView) view).getText().toString();
                play_toolbar.setVisibility(View.VISIBLE);
                play_toolbar.requestLayout();
                play_toolbar.bringToFront();
                track_title = (TextView) findViewById(R.id.track_title);
                track_title.setText(song);
                hideSoftKeyboard(SettingsActivity.this);

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase songRef = ref.child("URL").child(song);

                songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                        for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                            btn.setBackgroundResource(R.drawable.ic_media_pause);
                            play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
                            play_toolbar.bringToFront();
                            play_toolbar.setVisibility(View.VISIBLE);
                            play_toolbar.requestLayout();
                            play_toolbar.invalidate();
                            url = String.valueOf(dsp.getValue());
                            startMusic(url);
                            Toast.makeText(SettingsActivity.this, "Fullname is this: " + UserDetails.fullname, Toast.LENGTH_SHORT).show();

                            nowPlaying(song);

                            getFollowers(UserDetails.fullname, song);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        slistView.setAdapter(sadapter);

        // TODO: 21/06/2017 chat button and function
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, Users.class);
                startActivity(i);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = getSongName();

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");

                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String link = dataSnapshot.getValue().toString();
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(link));
                        startActivity(viewIntent);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToDown = getSongName();

                StorageReference storageReference = mStorage.getReferenceFromUrl("gs://tunein-633e5.appspot.com/bad boi muzik");
                StorageReference down = storageReference.child(songToDown+".mp3");

                storagePath = new File(view.getContext().getFilesDir(), "My_music");
                File localFile = new File(storagePath,"track1.mp3");
                try {
                    localFile = File.createTempFile("Audio", "mp3");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final File finalLocalFile = new File(storagePath,"track1.mp3");
                down.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        finalLocalFile.getAbsolutePath();
                        Toast.makeText(getApplicationContext(), "Downloded at location: " + finalLocalFile.getAbsolutePath(),Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
            }
        });

        blackHeart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String songToLike = getSongName();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                blackHeart.setVisibility(View.GONE);
                redHeart.setVisibility(View.VISIBLE);

            }
        });

        redHeart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                final String songLiked = getSongName();

                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);

                mDatabase1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();

                                    Toast.makeText(getApplicationContext(),"the key: "+ key, Toast.LENGTH_SHORT).show();



                                    if (dataSnapshot.child(key).getValue().equals(songLiked)) {
                                        Toast.makeText(getApplicationContext(),"in if: "+ songLiked, Toast.LENGTH_SHORT).show();

                                        dataSnapshot.child(key).getRef().removeValue();
                                        redHeart.setVisibility(View.GONE);
                                        blackHeart.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });



        //sliding menu settings
        //// TODO: 21/06/2017 add extra settings
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //showing user's email on sliding drawer
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.emailProfile);
        //nav_user.setText(getIntent().getExtras().getString("Email"));
        toolbar.setTitle("Now Playing");


        //search layout and functions
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

            @Override
            public void onSearchViewShown() {
                //nowPlayingLayout.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);
                fab.setVisibility(View.INVISIBLE);
                fab1.setVisibility(View.INVISIBLE);
                play_toolbar.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                searchLayout.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                play_toolbar.setVisibility(View.GONE);


            }
        });

        //search method
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.toLowerCase() != null && !newText.toLowerCase().isEmpty()) {
                    List<String> ulistFound = new ArrayList<String>();
                    for (String item : users) {
                        if (item.toLowerCase().contains(newText.toLowerCase()))
                            ulistFound.add(item);
                    }

                    ArrayAdapter uadapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_list_item_1, ulistFound);
                    ulistView.setAdapter(uadapter);

                    List<String> slistFound = new ArrayList<String>();
                    for (String item : songs) {
                        if (item.toLowerCase().contains(newText.toLowerCase()))
                            slistFound.add(item);
                    }

                    ArrayAdapter sadapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_list_item_1, slistFound);
                    slistView.setAdapter(sadapter);
                } else {
                    //if search text is null
                    ArrayAdapter uadapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_list_item_1, users);
                    ulistView.setAdapter(uadapter);
                    ArrayAdapter sadapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_list_item_1, songs);
                    slistView.setAdapter(sadapter);
                }
                return true;
            }
        });
    }

    private void getSongs(String id) {

        mDatabase8 = FirebaseDatabase.getInstance().getReference().child("Homepage").child(id);

        mDatabase8.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String value = snapshot.getKey();
                    NowPlayingItem item = new NowPlayingItem(value);
                    item.setSong(value);
                    songText.setText(value);
                    Toast.makeText(SettingsActivity.this, value + " is first friend name", Toast.LENGTH_SHORT).show();
                }
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

    private void getFollowers(String fullname, final String mysong) {

        fadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myFollowers);
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
                Toast.makeText(SettingsActivity.this, UserDetails.myFollowers.size() + " is the size", Toast.LENGTH_LONG).show();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String value = snapshot.getKey();
//                    Toast.makeText(SettingsActivity.this, value, Toast.LENGTH_LONG).show();
//                    myFollowers.add(value);
//                    addToFirebaseHome(value, mysong);
//                    UserDetails.myFollowers.add(value);
//
//                    fadapter.notifyDataSetChanged();
//                    Toast.makeText(SettingsActivity.this, UserDetails.myFollowers.size() + "  followers", Toast.LENGTH_LONG).show();
//
//                }
//            }
//
    //            mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    UserDetails.fullname = dataSnapshot.getValue().toString();
//                    Toast.makeText(SettingsActivity.this, UserDetails.fullname + " follower fullname", Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//
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
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//       // Toast.makeText(SettingsActivity.this, UserDetails.myFollowers.size() + "  followers", Toast.LENGTH_LONG).show();


    private void nowPlaying(String songName) {
        //getFulname();

        ID = firebaseAuth1.getCurrentUser().getUid();
        Firebase ref4 = new Firebase("https://tunein-633e5.firebaseio.com/").child("NowListening").child(ID);
        Map<String, Object> uinfo4 = new HashMap<String, Object>();
        uinfo4.put("Song", songName);
        ref4.updateChildren(uinfo4);
    }

    //showing searching layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    //when goign back from search layout
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            email = (TextView) findViewById(R.id.emailProfile);
            email.setText(getIntent().getExtras().getString("Email"));
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //posible place to get the ids for the clicked song
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle actifon bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    //sliding menu functions
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_friends) {
            // Handle the camera action
        } else if (id == R.id.nav_Playlists) {
            Intent nextActivity = new Intent(this, AudioPlayer.class);
            startActivity(nextActivity);
        } else if (id == R.id.nav_delete) {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            // Get auth credentials from the user for re-authentication. The example below shows
            // email and password credentials but there are multiple possible providers,
            // such as GoogleAuthProvider or FacebookAuthProvider.
            AuthCredential credential = EmailAuthProvider
                    .getCredential("user@example.com", "password1234");

            // Prompt the user to re-provide their sign-in credentials
            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User account deleted.");
                                            }
                                        }
                                    });
                        }
                    });
            Intent nextActivity = new Intent(this, RegisterActivity.class);
            startActivity(nextActivity);
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent nextActivity = new Intent(this, LoginActivity.class);
            startActivity(nextActivity);
        } else if (id == R.id.nav_Visibility) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // TODO: 21/06/2017 posible hide keyboard method
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void startMusic(String link) {
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
    }

    public void stopMusic(Integer length) {
        play_toolbar.setVisibility(View.VISIBLE);
        play_toolbar.bringToFront();
        length = mediaPlayer.getCurrentPosition();
        Button btn = (Button) this.findViewById(R.id.button);
        btn.setBackgroundResource(R.drawable.ic_media_play);
        mediaPlayer.pause();
    }

    //play song method with signle button background handling
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

    private void eraseFromFirebase() {
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Homepage");
        mDatabase1.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String v;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            v = snapshot.getKey();
                            Toast.makeText(SettingsActivity.this, "in erase" + dataSnapshot.child(snapshot.child(v).getKey().toString()).getKey().toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(SettingsActivity.this, "in for my fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
                            //getFulname();
                            if (dataSnapshot.child(v).hasChild(getMyFullname(ID))) {
                                Toast.makeText(SettingsActivity.this, "in if" + snapshot.getValue(), Toast.LENGTH_SHORT).show();

                                dataSnapshot.child(v).getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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

    public void openPlayerPage(View v) {
        Intent i = new Intent(SettingsActivity.this, AndroidBuildingMusicPlayerActivity.class);
        startActivity(i);
    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            Utilities utils = new Utilities();
            long totalDuration = SettingsActivity.mediaPlayer.getDuration();
            long currentDuration = SettingsActivity.mediaPlayer.getCurrentPosition();

            // Displaying Total Duration time
            AndroidBuildingMusicPlayerActivity.songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            AndroidBuildingMusicPlayerActivity.songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            AndroidBuildingMusicPlayerActivity.songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };


    public void getFulname() {

        ID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

        mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public String getMyFullname(String id){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(id).child("Name");

        mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return UserDetails.fullname;
    }

    public void checkFollowing() {

        mDatabase2.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(fullname)) {
                    //Toast.makeText(RequestActivity.this, "Already following " + UserDetails.username, Toast.LENGTH_SHORT).show();
                    UserDetails.following = true;
                } else {
                    UserDetails.following = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public boolean checkNowPlaying(String userId) {
        getFulname();
        mDatabase3 = FirebaseDatabase.getInstance().getReference().child("NowListening");
        mDatabase3.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(UserDetails.fullname)) {
                    //Toast.makeText(RequestActivity.this, "Already following " + UserDetails.username, Toast.LENGTH_SHORT).show();
                    UserDetails.nowPlaying = true;
                } else {
                    UserDetails.nowPlaying = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return false;
    }

    public void displayOnHomepage() {
        Toast.makeText(SettingsActivity.this, "display called" + UserDetails.username, Toast.LENGTH_SHORT).show();

        //nowPlayingLayout.setVisibility(View.VISIBLE);

//            getFulname();
//            checkFollowing();
//            checkNowPlaying();

        Toast.makeText(SettingsActivity.this, UserDetails.following + " " + UserDetails.nowPlaying, Toast.LENGTH_SHORT).show();


        mDatabase4 = FirebaseDatabase.getInstance().getReference().child("NowListening").child(UserDetails.fullname).child("Song");

        if (UserDetails.following && UserDetails.nowPlaying) {


            mDatabase4.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Toast.makeText(SettingsActivity.this, "following and playing" + UserDetails.username, Toast.LENGTH_SHORT).show();

                    //TextView tv = (TextView) findViewById(R.id.songName);
                    songText.setText("song name here soon");


                    //View test1View = findViewById(R.id.test1);
                    //sName = (TextView) test1View.findViewById(R.id.songName);
                    //String song = dataSnapshot.getValue().toString();
                    // sName.setText("blah");
                    //Toast.makeText(SettingsActivity.this, fullname, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            //add song to text view
        }
    }


    private void addToFirebaseHome(String myvalue, final String mysong) {

        String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");

        mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.myname = dataSnapshot.getValue().toString();
                Toast.makeText(SettingsActivity.this, UserDetails.myname + " is finally my fullname", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase6 = FirebaseDatabase.getInstance().getReference().child("Homepage").child(myvalue);
        mDatabase6.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("Song", mysong);
                Toast.makeText(SettingsActivity.this, UserDetails.myname + " id to get fullname for", Toast.LENGTH_SHORT).show();
                mDatabase6.child(UserDetails.myname).updateChildren(map);

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


    private void addToHome(List<String> myvalue, final String mysong) {

        String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");

        mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.myname = dataSnapshot.getValue().toString();
                me = dataSnapshot.getValue().toString();
                Toast.makeText(SettingsActivity.this, UserDetails.myname + " is finally my fullname", Toast.LENGTH_SHORT).show();

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

    private void getFriendFullname(String id) {

        mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(id).child("Name");

        mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                Toast.makeText(SettingsActivity.this, UserDetails.fullname + " follower fullname", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //trying to sync song

    //getting the name of the song
    public String getSongName() {
        String songToPlay = songText.getText().toString();
        Toast.makeText(SettingsActivity.this, songToPlay, Toast.LENGTH_SHORT).show();
        return songToPlay;
    }

    public String getPersonNowPlaying() {
        return nameText.getText().toString();
    }

    public void getUrl(String song) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase songRef = ref.child("URL").child(song);

        songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                    url = String.valueOf(dsp.getValue());
                    UserDetails.song = url;
                    Toast.makeText(SettingsActivity.this, UserDetails.song + " is the url", Toast.LENGTH_SHORT).show();
                    //getTimeFromFirebase();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void sendTimeRequest(String listenerFullname) {

        String me = firebaseAuth1.getCurrentUser().getUid();

        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/TimeRequest/" + me);
        Map<String, Object> uinfo = new HashMap<>();
        uinfo.put("Name", listenerFullname);
        ref.updateChildren(uinfo);

        //getSongName();
    }

    public void addTimeToFirebase(String otherUser) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/TimeAnswer/" + ID);
        Map<String, Object> uinfo = new HashMap<>();
        uinfo.put("Time", getCurrentPlayingTime());
        ref.updateChildren(uinfo);
    }

    public int getCurrentPlayingTime() {
        if (mediaPlayer.isPlaying())
            Toast.makeText(SettingsActivity.this, mediaPlayer.getCurrentPosition() + "it shouldnt be called", Toast.LENGTH_SHORT).show();
        return mediaPlayer.getCurrentPosition();
    }

    public void getTimeFromFirebase() {

        String myID = firebaseAuth1.getCurrentUser().getUid();

        DatabaseReference timeref = FirebaseDatabase.getInstance().getReference().child("TimeRequest").child(myID).child("Time");
        timeref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String data = dataSnapshot.getValue().toString();
                int timeFb = Integer.parseInt(data);
                //Toast.makeText(SettingsActivity.this, timeFb + " time in ", Toast.LENGTH_SHORT).show();
                playMusic(timeFb);
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

    public void setUrlValue(String address) {
        addr = address;
    }

    public String getUrlValue() {

        return addr;
    }

    public void playMusic(int time) {


        Toast.makeText(SettingsActivity.this, UserDetails.song + " the link ", Toast.LENGTH_SHORT).show();
        Toast.makeText(SettingsActivity.this, time + " the time ", Toast.LENGTH_SHORT).show();

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(UserDetails.song);
            mediaPlayer.prepare();
            mediaPlayer.seekTo(time+2);
            mediaPlayer.start();
        } catch (
                IllegalArgumentException e)

        {
            e.printStackTrace();
        } catch (
                IllegalStateException e)

        {
            e.printStackTrace();
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }
    }

    public void viewOnYoutube(){

        String songToView = getSongName();

        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");

        videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    String link = dataSnapshot.getValue().toString();
                    Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(link));
                startActivity(viewIntent);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}


