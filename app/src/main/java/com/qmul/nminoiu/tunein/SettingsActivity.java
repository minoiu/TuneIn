package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import android.view.View.OnClickListener;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;


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
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private LinearLayout[] ll;
    private ImageButton[] syncButtonArray;
    private ImageButton[] tuneoutButtonArray;
    private ImageButton[] youtubeArray;
    private ImageButton[] dwnArray;
    private ImageButton[] commentArray;
    private ImageButton[] addArray;
    private ImageButton[] blackHeartArray;
    private ImageButton[] redHeartArray;
    private TextView[] namesArray;
    private TextView[] titlesArray;
    Firebase reference1;
    Firebase reference2;
    DatabaseReference reference3;
    private String openURL;


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
   // public static MediaPlayer mediaPlayer;
    private StorageReference storage;
    private boolean playPause;
    private boolean intialStage = true;
    private Button btn;
    private Button playOnToolbar;
    private Button pauseOnToolbar;


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
    public LinearLayout np1;
    public LinearLayout np2;
    public LinearLayout np3;
    public LinearLayout np4;
    public LinearLayout np5;
    public LinearLayout nowPlayingLayout;
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
    public FloatingActionButton fab;
    public ImageButton fab1;
    public ImageButton youtube1;
    public ImageButton youtube2;
    public ImageButton youtube3;
    public ImageButton youtube4;
    public ImageButton youtube5;
    public ImageButton tunein1;
    public ImageButton tunein2;
    public ImageButton tunein3;
    public ImageButton tunein4;
    public ImageButton tunein5;
    public ImageButton tuneout1;
    public ImageButton tuneout2;
    public ImageButton tuneout3;
    public ImageButton tuneout4;
    public ImageButton tuneout5;
    public TextView title1;
    public TextView title2;
    public TextView title3;
    public TextView title4;
    public TextView title5;
    public ImageButton bheart1;
    public ImageButton bheart2;
    public ImageButton bheart3;
    public ImageButton bheart4;
    public ImageButton bheart5;
    public ImageButton rheart1;
    public ImageButton rheart2;
    public ImageButton rheart3;
    public ImageButton rheart4;
    public ImageButton rheart5;
    public ImageButton add1;
    public ImageButton add2;
    public ImageButton add3;
    public ImageButton add4;
    public ImageButton add5;
    public ImageButton comment1;
    public ImageButton comment2;
    public ImageButton comment3;
    public ImageButton comment4;
    public ImageButton comment5;
    public ImageButton download1;
    public ImageButton download2;
    public ImageButton download3;
    public ImageButton download4;
    public ImageButton download5;
    private int newtime;


    public TextView name1;
    public TextView name2;
    public TextView name3;
    public TextView name4;
    public TextView name5;

    public ImageButton youtubecoloured;
    public ImageButton comment;
    public ImageButton commentblue;
    public ImageButton add;
    public ImageButton addgreen;
    public ImageButton download;
    public ImageButton downloadgreen;
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
    private int i;
   // makeprivate Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setContentView(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //setSupportActionBar(toolbar);

        songText = (TextView) findViewById(R.id.songName);
        nameText = (TextView) findViewById(R.id.name);
        ptextview = (TextView) findViewById(R.id.ptextView);
        stextview = (TextView) findViewById(R.id.stextView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (ImageButton) findViewById(R.id.fab1);
        syncButton = (ImageButton) findViewById(R.id.syncButton);
        fab.bringToFront();
        fab1.bringToFront();
        myFollowers = new ArrayList<>();
        myuser = new User();
        nowPlayingLayout = (LinearLayout) findViewById(R.id.playing);
        np1 = (LinearLayout) findViewById(R.id.np1);
        np2 = (LinearLayout) findViewById(R.id.np2);
        np3 = (LinearLayout) findViewById(R.id.np3);
        np4 = (LinearLayout) findViewById(R.id.np4);
        np5 = (LinearLayout) findViewById(R.id.np5);
        ll = new LinearLayout[5];
        ll[0] = np1;
        ll[1] = np2;
        ll[2] = np3;
        ll[3] = np4;
        ll[4] = np5;

        tuneOutBtn = (ImageButton) findViewById(R.id.tuneout_btn);
        blackHeart = (ImageButton) findViewById(R.id.blackHeart);
        redHeart = (ImageButton) findViewById(R.id.redHeart);

        firebaseAuth1 = FirebaseAuth.getInstance();
        user = firebaseAuth1.getCurrentUser();

        loggedEmail = user.getEmail();
        ID = firebaseAuth1.getCurrentUser().getUid();
        OneSignal.sendTag("User_ID", loggedEmail);
        mStorage = FirebaseStorage.getInstance();
        btn = (Button) findViewById(R.id.button);
        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        track_title = (TextView) findViewById(R.id.track_title);

        CoordinatorLayout.LayoutParams paramsFab1 = (CoordinatorLayout.LayoutParams) fab1.getLayoutParams();
        CoordinatorLayout.LayoutParams paramsFab = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();

//        if(mediaPlayer.isPlaying()){
//            paramsFab1.setMargins(0, 0, 43, 150);
//            paramsFab.setMargins(53, 0, 0, 160);
//            fab1.setLayoutParams(paramsFab1);
//            fab.setLayoutParams(paramsFab);
//            play_toolbar.setVisibility(View.VISIBLE);
//
//        } else {
//            play_toolbar.setVisibility(View.GONE);
//        }

        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .init();

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("ID")) {
                String uniqid = intent.getStringExtra("ID");
                if (uniqid.equals("FromLibrary")) {
                    if (mediaPlayer.isPlaying()) {
                        String songFromLib = intent.getStringExtra("Song");
                        play_toolbar.setVisibility(View.VISIBLE);
                        track_title.setText(songFromLib);
                        btn.setBackgroundResource(R.drawable.ic_media_pause);
                        paramsFab1.setMargins(0, 0, 43, 150); //bottom margin is 25 here (change it as u wish)
                        fab1.setLayoutParams(paramsFab1);
                        paramsFab.setMargins(53, 0, 0, 160); //bottom margin is 25 here (change it as u wish)
                        fab.setLayoutParams(paramsFab);
                    } else {
                        play_toolbar.setVisibility(View.GONE);
                    }
                } else if (uniqid.equals("FromSearch")) {
                    if (mediaPlayer.isPlaying()) {
                        String songFromLib = intent.getStringExtra("Song");
                        fab1.bringToFront();
                        play_toolbar.setVisibility(View.VISIBLE);
                        track_title.setText(songFromLib);
                        btn.setBackgroundResource(R.drawable.ic_media_pause);
                        paramsFab1.setMargins(0, 0, 43, 150); //bottom margin is 25 here (change it as u wish)
                        fab1.setLayoutParams(paramsFab1);
                        paramsFab.setMargins(53, 0, 0, 160); //bottom margin is 25 here (change it as u wish)
                        fab.setLayoutParams(paramsFab);
                    } else {
                        play_toolbar.setVisibility(View.GONE);
                    }
                } else if (uniqid.equals("FromPlayer")) {
                        if (mediaPlayer.isPlaying()) {
                            String songFromLib = intent.getStringExtra("Song");
                            fab1.bringToFront();
                            play_toolbar.setVisibility(View.VISIBLE);
                            track_title.setText(songFromLib);
                            btn.setBackgroundResource(R.drawable.ic_media_pause);
                            paramsFab1.setMargins(0, 0, 43, 150); //bottom margin is 25 here (change it as u wish)
                            fab1.setLayoutParams(paramsFab1);
                            paramsFab.setMargins(53, 0, 0, 160); //bottom margin is 25 here (change it as u wish)
                            fab.setLayoutParams(paramsFab);
                        } else {
                            play_toolbar.setVisibility(View.GONE);
                        }
                    }
                }
            }


//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.reset();

//        mediaPlayer.reset();
        //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        View v1 = findViewById(R.id.np1);
        ImageButton youtube1 = (ImageButton) v1.findViewById(R.id.youtube);
       // youtube1.setOnClickListener(this);
        View v2 = findViewById(R.id.np2);
        ImageButton youtube2 = (ImageButton) v2.findViewById(R.id.youtube);
       // youtube2.setOnClickListener(this);
        View v3 = findViewById(R.id.np3);
        ImageButton youtube3 = (ImageButton) v3.findViewById(R.id.youtube);
       // youtube3.setOnClickListener(this);
        View v4 = findViewById(R.id.np4);
        ImageButton youtube4 = (ImageButton) v4.findViewById(R.id.youtube);
       // youtube4.setOnClickListener(this);
        View v5 = findViewById(R.id.np5);
        ImageButton youtube5 = (ImageButton) v5.findViewById(R.id.youtube);
       // youtube5.setOnClickListener(this);


        View a1 = findViewById(R.id.np1);
         download1 = (ImageButton) a1.findViewById(R.id.download);
       // download1.setOnClickListener(this);
        View a2 = findViewById(R.id.np2);
         download2 = (ImageButton) a2.findViewById(R.id.download);
       // download2.setOnClickListener(this);
        View a3 = findViewById(R.id.np3);
         download3 = (ImageButton) a3.findViewById(R.id.download);
       // download3.setOnClickListener(this);
        View a4 = findViewById(R.id.np4);
         download4 = (ImageButton) a4.findViewById(R.id.download);
     //   download4.setOnClickListener(this);
        View a5 = findViewById(R.id.np5);
         download5 = (ImageButton) a5.findViewById(R.id.download);
       // download5.setOnClickListener(this);


        View c1 = findViewById(R.id.np1);
         comment1 = (ImageButton) c1.findViewById(R.id.comment);
       // comment1.setOnClickListener(this);
        View c2 = findViewById(R.id.np2);
         comment2 = (ImageButton) c2.findViewById(R.id.comment);
       // comment2.setOnClickListener(this);
        View c3 = findViewById(R.id.np3);
         comment3 = (ImageButton) c3.findViewById(R.id.comment);
      //  comment3.setOnClickListener(this);
        View c4 = findViewById(R.id.np4);
         comment4 = (ImageButton) c4.findViewById(R.id.comment);
      //  comment4.setOnClickListener(this);
        View c5 = findViewById(R.id.np5);
         comment5 = (ImageButton) c5.findViewById(R.id.comment);
      //  comment5.setOnClickListener(this);


        View ad1 = findViewById(R.id.np1);
         add1 = (ImageButton) ad1.findViewById(R.id.add);
      //  add1.setOnClickListener(this);
        View ad2 = findViewById(R.id.np2);
         add2 = (ImageButton) ad2.findViewById(R.id.add);
       // add2.setOnClickListener(this);
        View ad3 = findViewById(R.id.np3);
         add3 = (ImageButton) ad3.findViewById(R.id.add);
      //  add3.setOnClickListener(this);
        View ad4 = findViewById(R.id.np4);
         add4 = (ImageButton) ad4.findViewById(R.id.add);
      //  add4.setOnClickListener(this);
        View ad5 = findViewById(R.id.np5);
         add5 = (ImageButton) ad5.findViewById(R.id.add);
       // add5.setOnClickListener(this);

        View b1 = findViewById(R.id.np1);
         bheart1 = (ImageButton) b1.findViewById(R.id.blackHeart);
      //  bheart1.setOnClickListener(this);
        View b2 = findViewById(R.id.np2);
         bheart2 = (ImageButton) b2.findViewById(R.id.blackHeart);
      //  bheart2.setOnClickListener(this);
        View b3 = findViewById(R.id.np3);
         bheart3 = (ImageButton) b3.findViewById(R.id.blackHeart);
      //  bheart3.setOnClickListener(this);
        View b4 = findViewById(R.id.np4);
         bheart4 = (ImageButton) b4.findViewById(R.id.blackHeart);
     //   bheart4.setOnClickListener(this);
        View b5 = findViewById(R.id.np5);
         bheart5 = (ImageButton) b5.findViewById(R.id.blackHeart);
       // bheart5.setOnClickListener(this);

        View r1 = findViewById(R.id.np1);
         rheart1 = (ImageButton) r1.findViewById(R.id.redHeart);
      //  rheart1.setOnClickListener(this);
        View r2 = findViewById(R.id.np2);
         rheart2 = (ImageButton) r2.findViewById(R.id.redHeart);
      //  rheart2.setOnClickListener(this);
        View r3 = findViewById(R.id.np3);
         rheart3 = (ImageButton) r3.findViewById(R.id.redHeart);
      //  rheart3.setOnClickListener(this);
        View r4 = findViewById(R.id.np4);
         rheart4 = (ImageButton) r4.findViewById(R.id.redHeart);
      //  rheart4.setOnClickListener(this);
        View r5 = findViewById(R.id.np5);
         rheart5 = (ImageButton) r5.findViewById(R.id.redHeart);
      //  rheart5.setOnClickListener(this);

        View ti1 = findViewById(R.id.np1);
         tunein1 = (ImageButton) ti1.findViewById(R.id.syncButton);
      //  tunein1.setOnClickListener(this);
        View ti2 = findViewById(R.id.np2);
         tunein2 = (ImageButton) ti2.findViewById(R.id.syncButton);
      //  tunein2.setOnClickListener(this);
        View ti3 = findViewById(R.id.np3);
         tunein3 = (ImageButton) ti3.findViewById(R.id.syncButton);
       // tunein3.setOnClickListener(this);
        View ti4 = findViewById(R.id.np4);
         tunein4 = (ImageButton) ti4.findViewById(R.id.syncButton);
      //  tunein4.setOnClickListener(this);
        View ti5 = findViewById(R.id.np5);
         tunein5 = (ImageButton) ti5.findViewById(R.id.syncButton);
      //  tunein5.setOnClickListener(this);


        View to1 = findViewById(R.id.np1);
         tuneout1 = (ImageButton) to1.findViewById(R.id.tuneout_btn);
      //  tuneout1.setOnClickListener(this);
        View to2 = findViewById(R.id.np2);
         tuneout2 = (ImageButton) to2.findViewById(R.id.tuneout_btn);
       // tuneout2.setOnClickListener(this);
        View to3 = findViewById(R.id.np3);
         tuneout3 = (ImageButton) to3.findViewById(R.id.tuneout_btn);
       // tuneout3.setOnClickListener(this);
        View to4 = findViewById(R.id.np4);
         tuneout4 = (ImageButton) to4.findViewById(R.id.tuneout_btn);
       // tuneout4.setOnClickListener(this);
        View to5 = findViewById(R.id.np5);
         tuneout5 = (ImageButton) to5.findViewById(R.id.tuneout_btn);

        View n1 = findViewById(R.id.np1);
         name1 = (TextView) n1.findViewById(R.id.name);
        //  tuneout1.setOnClickListener(this);
        View n2 = findViewById(R.id.np2);
         name2 = (TextView) n2.findViewById(R.id.name);
        View n3 = findViewById(R.id.np3);
         name3 = (TextView) n3.findViewById(R.id.name);
        View n4 = findViewById(R.id.np4);
         name4 = (TextView) n4.findViewById(R.id.name);
        View n5 = findViewById(R.id.np5);
         name5 = (TextView) n5.findViewById(R.id.name);

        View t1 = findViewById(R.id.np1);
         title1 = (TextView) t1.findViewById(R.id.songName);
        //  tuneout1.setOnClickListener(this);
        View t2 = findViewById(R.id.np2);
         title2 = (TextView) t2.findViewById(R.id.songName);
        View t3 = findViewById(R.id.np3);
         title3 = (TextView) t3.findViewById(R.id.songName);
        View t4 = findViewById(R.id.np4);
         title4 = (TextView) t4.findViewById(R.id.songName);
        View t5 = findViewById(R.id.np5);
         title5 = (TextView) t5.findViewById(R.id.songName);



       // tuneout5.setOnClickListener(this);
        tuneoutButtonArray = new ImageButton[5];
        tuneoutButtonArray[0] = tuneout1;
        tuneoutButtonArray[1] = tuneout2;
        tuneoutButtonArray[2] = tuneout3;
        tuneoutButtonArray[3] = tuneout4;
        tuneoutButtonArray[4] = tuneout5;

        syncButtonArray = new ImageButton[5];
        syncButtonArray[0] = tunein1;
        syncButtonArray[1] = tunein2;
        syncButtonArray[2] = tunein3;
        syncButtonArray[3] = tunein4;
        syncButtonArray[4] = tunein5;

        youtubeArray = new ImageButton[5];
        youtubeArray[0] = youtube1;
        youtubeArray[1] = youtube2;
        youtubeArray[2] = youtube3;
        youtubeArray[3] = youtube4;
        youtubeArray[4] = youtube5;

        titlesArray = new TextView[5];
        titlesArray[0] = title1;
        titlesArray[1] = title2;
        titlesArray[2] = title3;
        titlesArray[3] = title4;
        titlesArray[4] = title5;

        namesArray = new TextView[5];
        namesArray[0] = name1;
        namesArray[1] = name2;
        namesArray[2] = name3;
        namesArray[3] = name4;
        namesArray[4] = name5;

        tunein1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mediaPlayer.pause();
                sendTimeRequest(name1.getText().toString(),title1.getText().toString() );
                tunein1.setVisibility(View.GONE);
                tuneout1.setVisibility(View.VISIBLE);
                tuneout2.setVisibility(View.GONE);
                tunein2.setVisibility(View.VISIBLE);
                tuneout3.setVisibility(View.GONE);
                tunein3.setVisibility(View.VISIBLE);
                tuneout4.setVisibility(View.GONE);
                tunein4.setVisibility(View.VISIBLE);
                tuneout5.setVisibility(View.GONE);
                tunein5.setVisibility(View.VISIBLE);

            }
        });

        tunein2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mediaPlayer.pause();
                sendTimeRequest(name2.getText().toString(), title2.getText().toString());
                tunein2.setVisibility(View.GONE);
                tuneout2.setVisibility(View.VISIBLE);
                tuneout1.setVisibility(View.GONE);
                tunein1.setVisibility(View.VISIBLE);
                tuneout3.setVisibility(View.GONE);
                tunein3.setVisibility(View.VISIBLE);
                tuneout4.setVisibility(View.GONE);
                tunein4.setVisibility(View.VISIBLE);
                tuneout5.setVisibility(View.GONE);
                tunein5.setVisibility(View.VISIBLE);
            }
        });

        tunein3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mediaPlayer.pause();
                sendTimeRequest(name3.getText().toString(),title3.getText().toString());
                tunein3.setVisibility(View.GONE);
                tuneout3.setVisibility(View.VISIBLE);
                tuneout1.setVisibility(View.GONE);
                tunein1.setVisibility(View.VISIBLE);
                tuneout2.setVisibility(View.GONE);
                tunein2.setVisibility(View.VISIBLE);
                tuneout4.setVisibility(View.GONE);
                tunein4.setVisibility(View.VISIBLE);
                tuneout5.setVisibility(View.GONE);
                tunein5.setVisibility(View.VISIBLE);
            }
        });

        tunein4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mediaPlayer.pause();
                sendTimeRequest(name4.getText().toString(), title4.getText().toString());
                tunein4.setVisibility(View.GONE);
                tuneout4.setVisibility(View.VISIBLE);
                tuneout1.setVisibility(View.GONE);
                tunein1.setVisibility(View.VISIBLE);
                tuneout2.setVisibility(View.GONE);
                tunein2.setVisibility(View.VISIBLE);
                tuneout3.setVisibility(View.GONE);
                tunein3.setVisibility(View.VISIBLE);
                tuneout5.setVisibility(View.GONE);
                tunein5.setVisibility(View.VISIBLE);
            }
        });

        tunein5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mediaPlayer.pause();
                sendTimeRequest(name5.getText().toString(), title5.getText().toString());
                tunein5.setVisibility(View.GONE);
                tuneout5.setVisibility(View.VISIBLE);
                tuneout1.setVisibility(View.GONE);
                tunein1.setVisibility(View.VISIBLE);
                tuneout2.setVisibility(View.GONE);
                tunein2.setVisibility(View.VISIBLE);
                tuneout3.setVisibility(View.GONE);
                tunein3.setVisibility(View.VISIBLE);
                tuneout4.setVisibility(View.GONE);
                tunein4.setVisibility(View.VISIBLE);
            }
        });

        tuneout1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            tuneout1.setVisibility(View.GONE);
                                            tunein1.setVisibility(View.VISIBLE);
                                            mediaPlayer.stop();
                                            mediaPlayer.release();

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
                                        }
                                    });

//                                            DatabaseReference answer = FirebaseDatabase.getInstance().getReference().child("TimeAnswer");
//
//                                            answer.addChildEventListener(new ChildEventListener() {
//
//                                                String ID = firebaseAuth1.getCurrentUser().getUid();
//
//                                                @Override
//                                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                                                            if (dataSnapshot.child("IDReq").getValue().toString().equals(ID)) {
//                                                                String time = dataSnapshot.child("Time").getValue().toString();
//                                                                String song = dataSnapshot.child("Song").getValue().toString();
//                                                                final int newtime = Integer.parseInt(time);
//                                                                Toast.makeText(SettingsActivity.this, time + " in child added ", Toast.LENGTH_SHORT).show();
//
//                                                                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
//                                                                Firebase songRef = ref.child("URL").child(song);
//
//                                        }
//                                    }
//                                                    }
//
//
//                                                @Override
//                                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                                                }
//
//                                                @Override
//                                                public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                                                }
//
//                                                @Override
//                                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                        }
//                                    });

        tuneout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuneout2.setVisibility(View.GONE);
                tunein2.setVisibility(View.VISIBLE);
                mediaPlayer.stop();
                mediaPlayer.release();


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
            }
        });

        tuneout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuneout3.setVisibility(View.GONE);
                tunein3.setVisibility(View.VISIBLE);
                mediaPlayer.stop();
                mediaPlayer.release();


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
            }
        });

        tuneout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuneout4.setVisibility(View.GONE);
                tunein4.setVisibility(View.VISIBLE);
                mediaPlayer.stop();
                mediaPlayer.release();

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
            }
        });

        tuneout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuneout5.setVisibility(View.GONE);
                tunein5.setVisibility(View.VISIBLE);
                mediaPlayer.stop();
                mediaPlayer.release();

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
            }
        });






//        for(i=0; i < tuneoutButtonArray.length; i++) {
//
//            tuneoutButtonArray[i].setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    tuneoutButtonArray[i].setVisibility(View.GONE);
//                    syncButtonArray[i].setVisibility(View.VISIBLE);
//                    mediaPlayer.stop();
//
//                    DatabaseReference reqdb = FirebaseDatabase.getInstance().getReference().child("TimeRequest").child(ID);
//                    reqdb.addListenerForSingleValueEvent(
//                            new ValueEventListener() {
//                                @Override
//                                public void onDataChange(DataSnapshot dataSnapshot) {
//                                    dataSnapshot.getRef().removeValue();
//                                }
//
//                                @Override
//                                public void onCancelled(DatabaseError databaseError) {
//                                    Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
//                                }
//                            });
//
//                    return false;
//                }
//            });
//        }


        youtube1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title1.getText().toString();
               // Toast.makeText(SettingsActivity.this, "songname and view" + songToView + i, Toast.LENGTH_SHORT).show();

                //youtube.setVisibility(View.GONE);
                //youtubecoloured.setVisibility(View.VISIBLE);

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

        youtube2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title2.getText().toString();
               // Toast.makeText(SettingsActivity.this, "songname and view" + songToView + i, Toast.LENGTH_SHORT).show();

                //youtube.setVisibility(View.GONE);
                //youtubecoloured.setVisibility(View.VISIBLE);

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

        youtube3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title3.getText().toString();
             //   Toast.makeText(SettingsActivity.this, "songname and view" + songToView + i, Toast.LENGTH_SHORT).show();

                //youtube.setVisibility(View.GONE);
                //youtubecoloured.setVisibility(View.VISIBLE);

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


        youtube4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title4.getText().toString();
              //  Toast.makeText(SettingsActivity.this, "songname and view" + songToView + i, Toast.LENGTH_SHORT).show();

                //youtube.setVisibility(View.GONE);
                //youtubecoloured.setVisibility(View.VISIBLE);

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

        youtube5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title5.getText().toString();
             //   Toast.makeText(SettingsActivity.this, "songname and view" + songToView + i, Toast.LENGTH_SHORT).show();

                //youtube.setVisibility(View.GONE);
                //youtubecoloured.setVisibility(View.VISIBLE);

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


        download1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String songToDown = title1.getText().toString();

                StorageReference storageReference = mStorage.getReferenceFromUrl("gs://tunein-633e5.appspot.com/bad boi muzik");
                StorageReference down = storageReference.child(songToDown + ".mp3");

                storagePath = new File(view.getContext().getFilesDir(), "My_music");
                File localFile = new File(storagePath, songToDown);
                try {
                    localFile = File.createTempFile(songToDown, ".mp3");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final File finalLocalFile = new File(storagePath, songToDown);
                down.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        finalLocalFile.getAbsolutePath();
                        Toast.makeText(getApplicationContext(), "Done downloading" , Toast.LENGTH_SHORT).show();
                            Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                            Firebase playRef = ref.child("DownloadedSongs").child(ID);
                            playRef.push().setValue(songToDown);

                        UserDetails.song = finalLocalFile.getAbsolutePath();
                        //download.setVisibility(View.GONE);
                        //downloadgreen.setVisibility(View.VISIBLE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
            }
        });

        download2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String songToDown = title2.getText().toString();

                StorageReference storageReference = mStorage.getReferenceFromUrl("gs://tunein-633e5.appspot.com/bad boi muzik");
                StorageReference down = storageReference.child(songToDown + ".mp3");

                storagePath = new File(view.getContext().getFilesDir(), "My_music");
                File localFile = new File(storagePath, songToDown);
                try {
                    localFile = File.createTempFile(songToDown, ".mp3");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final File finalLocalFile = new File(storagePath, songToDown);
                down.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        finalLocalFile.getAbsolutePath();
                        Toast.makeText(getApplicationContext(), "Done downloading" , Toast.LENGTH_SHORT).show();
                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase playRef = ref.child("DownloadedSongs").child(ID);
                        playRef.push().setValue(songToDown);

                        //download.setVisibility(View.GONE);
                        //downloadgreen.setVisibility(View.VISIBLE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
            }
        });

        download3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String songToDown = title3.getText().toString();

                StorageReference storageReference = mStorage.getReferenceFromUrl("gs://tunein-633e5.appspot.com/bad boi muzik");
                StorageReference down = storageReference.child(songToDown + ".mp3");

                storagePath = new File(view.getContext().getFilesDir(), "My_music");
                File localFile = new File(storagePath, songToDown);
                try {
                    localFile = File.createTempFile(songToDown, ".mp3");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final File finalLocalFile = new File(storagePath, songToDown);
                down.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        finalLocalFile.getAbsolutePath();
                        Toast.makeText(getApplicationContext(), "Done downloading" , Toast.LENGTH_SHORT).show();
                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase playRef = ref.child("DownloadedSongs").child(ID);
                        playRef.push().setValue(songToDown);

                        //download.setVisibility(View.GONE);
                        //downloadgreen.setVisibility(View.VISIBLE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
            }
        });

        download4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String songToDown = title4.getText().toString();

                StorageReference storageReference = mStorage.getReferenceFromUrl("gs://tunein-633e5.appspot.com/bad boi muzik");
                StorageReference down = storageReference.child(songToDown + ".mp3");

                storagePath = new File(view.getContext().getFilesDir(), "My_music");
                File localFile = new File(storagePath, songToDown);
                try {
                    localFile = File.createTempFile(songToDown, ".mp3");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final File finalLocalFile = new File(storagePath, songToDown);
                down.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        finalLocalFile.getAbsolutePath();
                        Toast.makeText(getApplicationContext(), "Done downloading" , Toast.LENGTH_SHORT).show();
                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase playRef = ref.child("DownloadedSongs").child(ID);
                        playRef.push().setValue(songToDown);

                        //download.setVisibility(View.GONE);
                        //downloadgreen.setVisibility(View.VISIBLE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
            }
        });

        download5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String songToDown = title5.getText().toString();

                StorageReference storageReference = mStorage.getReferenceFromUrl("gs://tunein-633e5.appspot.com/bad boi muzik");
                StorageReference down = storageReference.child(songToDown + ".mp3");

                storagePath = new File(view.getContext().getFilesDir(), "My_music");
                File localFile = new File(storagePath, songToDown);
                try {
                    localFile = File.createTempFile(songToDown, ".mp3");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final File finalLocalFile = new File(storagePath, songToDown);
                down.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        finalLocalFile.getAbsolutePath();
                        Toast.makeText(getApplicationContext(), "Done downloading" , Toast.LENGTH_SHORT).show();
                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase playRef = ref.child("DownloadedSongs").child(ID);
                        playRef.push().setValue(songToDown);
                        //download.setVisibility(View.GONE);
                        //downloadgreen.setVisibility(View.VISIBLE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
            }
        });

        comment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //comment.setVisibility(View.GONE);
                //commentblue.setVisibility(View.VISIBLE);
            }
        });

        comment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //comment.setVisibility(View.GONE);
                //commentblue.setVisibility(View.VISIBLE);
            }
        });

        comment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //comment.setVisibility(View.GONE);
                //commentblue.setVisibility(View.VISIBLE);
            }
        });

        comment4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //comment.setVisibility(View.GONE);
                //commentblue.setVisibility(View.VISIBLE);
            }
        });

        comment5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //comment.setVisibility(View.GONE);
                //commentblue.setVisibility(View.VISIBLE);
            }
        });

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, PlaylistsActivity.class);
                final String song = title1.getText().toString();
                i.putExtra("Uniqid", "FromNowPlayling");
                i.putExtra("Song", song);
                startActivity(i);
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, PlaylistsActivity.class);
                final String song = title2.getText().toString();
                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(ID)){
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!dataSnapshot.child(key).getValue().equals(song)) {
                                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                                    Firebase playRef = ref.child("MySongs").child(ID);
                                    playRef.push().setValue(song);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                i.putExtra("Song", song);
                startActivity(i);
            }
        });

        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, PlaylistsActivity.class);
                final String song = title3.getText().toString();
                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(ID)){
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!dataSnapshot.child(key).getValue().equals(song)) {
                                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                                    Firebase playRef = ref.child("MySongs").child(ID);
                                    playRef.push().setValue(song);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                i.putExtra("Song", song);
                startActivity(i);
            }
        });

        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, PlaylistsActivity.class);
                final String song = title4.getText().toString();
                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(ID)){
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!dataSnapshot.child(key).getValue().equals(song)) {
                                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                                    Firebase playRef = ref.child("MySongs").child(ID);
                                    playRef.push().setValue(song);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                i.putExtra("Song", song);
                startActivity(i);
            }
        });

        add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, PlaylistsActivity.class);
                final String song = title5.getText().toString();

                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(ID)){
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!dataSnapshot.child(key).getValue().equals(song)) {
                                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                                    Firebase playRef = ref.child("MySongs").child(ID);
                                    playRef.push().setValue(song);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                i.putExtra("Song", song);
                startActivity(i);
            }
        });

        bheart1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String songToLike = title1.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart1.setVisibility(View.GONE);
                rheart1.setVisibility(View.VISIBLE);

            }
        });

        bheart2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String songToLike = title2.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart2.setVisibility(View.GONE);
                rheart2.setVisibility(View.VISIBLE);

            }
        });

        bheart3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String songToLike = title3.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart3.setVisibility(View.GONE);
                rheart3.setVisibility(View.VISIBLE);

            }
        });

        bheart4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String songToLike = title4.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart4.setVisibility(View.GONE);
                rheart4.setVisibility(View.VISIBLE);

            }
        });

        bheart5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String songToLike = title5.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart5.setVisibility(View.GONE);
                rheart5.setVisibility(View.VISIBLE);

            }
        });

        rheart1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                final String songLiked = title1.getText().toString();

                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);

                mDatabase1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();

                                    if (dataSnapshot.child(key).getValue().equals(songLiked)) {

                                        dataSnapshot.child(key).getRef().removeValue();
                                        rheart1.setVisibility(View.INVISIBLE);
                                        bheart1.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });

        rheart2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                final String songLiked = title2.getText().toString();

                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);

                mDatabase1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();

                                    if (dataSnapshot.child(key).getValue().equals(songLiked)) {

                                        dataSnapshot.child(key).getRef().removeValue();
                                        rheart2.setVisibility(View.INVISIBLE);
                                        bheart2.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });

        rheart3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                final String songLiked = title3.getText().toString();

                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);

                mDatabase1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();

                                    if (dataSnapshot.child(key).getValue().equals(songLiked)) {

                                        dataSnapshot.child(key).getRef().removeValue();
                                        rheart3.setVisibility(View.INVISIBLE);
                                        bheart3.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });

        rheart4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                final String songLiked = title4.getText().toString();

                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);

                mDatabase1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();

                                    if (dataSnapshot.child(key).getValue().equals(songLiked)) {
                                        dataSnapshot.child(key).getRef().removeValue();
                                        rheart4.setVisibility(View.INVISIBLE);
                                        bheart4.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });

        rheart5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                final String songLiked = title5.getText().toString();

                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);

                mDatabase1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();

                                    if (dataSnapshot.child(key).getValue().equals(songLiked)) {

                                        dataSnapshot.child(key).getRef().removeValue();
                                        rheart5.setVisibility(View.INVISIBLE);
                                        bheart5.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });



//        for(i=0; i <= 4; i++) {
//
//            youtubeArray[i].setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String songToView = titlesArray[i].getText().toString();
//                    Toast.makeText(SettingsActivity.this, "songname and view" + songToView + i, Toast.LENGTH_SHORT).show();
//
//                    //youtube.setVisibility(View.GONE);
//                    //youtubecoloured.setVisibility(View.VISIBLE);
//
//                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
//                    Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");
//
//                    videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
//                        @Override
//                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                            String link = dataSnapshot.getValue().toString();
//                            Intent viewIntent =
//                                    new Intent("android.intent.action.VIEW",
//                                            Uri.parse(link));
//                            startActivity(viewIntent);
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    });
//                }
//            });
//        }

        //setting notification tags for current user

        NowPlayingItem item = new NowPlayingItem();
        //getTimeFromFirebase();

        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Following").child(ID);
//            checkNowPlaying();
//            checkFollowing();
//            displayOnHomepage();

        //media player

      //  mediaPlayer.release();


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

                    if (dataSnapshot.child("Name").getValue().toString().equals(UserDetails.fullname)) {
                        String song = dataSnapshot.child("Song").getValue().toString();
                        addTimeToFirebase(otherUser, song);
                        //getCurrentPlayingTime();

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
                    //NowPlayingItem item = new NowPlayingItem(text);

                    //item.setSong(text);
                    // nameText.setText(text);
                    names.add(text);
                    // Toast.makeText(SettingsActivity.this, text + " is first friend name", Toast.LENGTH_SHORT).show();

                    String mysong = dataSnapshot.child("Song").getValue().toString();
                    //songText.setText(mysong);
                    title.add(mysong);
                }

                    for (int i = 0; i <= names.size() - 1; i++) {
                        ll[i].setVisibility(View.VISIBLE);
                        TextView name = (TextView) ll[i].findViewById(R.id.name);
                        name.setText(names.get(i));
                        TextView song = (TextView) ll[i].findViewById(R.id.songName);
                        song.setText(title.get(i));
                    }
                }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    text = dataSnapshot.getKey();
                    String mysong = dataSnapshot.child("Song").getValue().toString();

                    for(int i = 0; i <= names.size()-1; i++){
                        if(text.equals(names.get(i))){
                            names.set(i, text);
                            title.set(i, mysong);
                            //ll[i].setVisibility(View.VISIBLE);
                            TextView name = (TextView) ll[i].findViewById(R.id.name);
                            name.setText(text);
                            TextView song = (TextView) ll[i].findViewById(R.id.songName);
                            song.setText(mysong);
                        }

//                        else {
//                            names.add(text);
//                            title.add(mysong);
//                        }
                    }

                    //item.setSong(text);
                    // nameText.setText(text);
                    //names.add(text);
                    //Toast.makeText(SettingsActivity.this, text + " is first friend name", Toast.LENGTH_SHORT).show();


                    //songText.setText(mysong);
                    //title.add(mysong);
                }

                for(int i=0; i <= names.size()-1; i++){
                    ll[i].setVisibility(View.VISIBLE);
                    TextView name = (TextView) ll[i].findViewById(R.id.name);
                    name.setText(names.get(i));
                    TextView song = (TextView) ll[i].findViewById(R.id.songName);
                    song.setText(title.get(i));
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    text = dataSnapshot.getKey();
                    String mysong = dataSnapshot.child("Song").getValue().toString();


                    for (int j = 0; j <= names.size() - 1; j++) {
                        if (text.equals(names.get(j))) {
                            names.remove(text);
                            title.remove(mysong);
                            ll[j].setVisibility(View.GONE);
                            if(mediaPlayer.isPlaying()){
                                play_toolbar.setVisibility(View.VISIBLE);
                                track_title = (TextView) findViewById(R.id.track_title);
                                track_title.setText(mysong);

                            }
                        }


                    }
                }
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

           String ID = firebaseAuth1.getCurrentUser().getUid();

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

               // for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//                    Toast.makeText(SettingsActivity.this, "id is " + x, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(SettingsActivity.this, "child is " + snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

                    //Toast.makeText(SettingsActivity.this, "in for my fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();


//                        Toast.makeText(SettingsActivity.this, x + " first if", Toast.LENGTH_SHORT).show();

                        if (dataSnapshot.child("IDReq").getValue().toString().equals(ID)) {
                            String time = dataSnapshot.child("Time").getValue().toString();
                            String song = dataSnapshot.child("Song").getValue().toString();
                            newtime = Integer.parseInt(time);

//                            Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("RecentlyPlayed").child(ID);
//                            likedRef.push().setValue(song);

                            Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                            Firebase songRef = ref.child("URL").child(song);

                            songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                                @Override
                                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                                    for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                                        url = String.valueOf(dsp.getValue());
                                        UserDetails.song = url;
                                        playMusic(newtime);
                                        //getTimeFromFirebase();
                                    }
                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });



//                            songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
//                                @Override
//                                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                                    for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
//                                        url = dataSnapshot.child("URL").getValue().toString();
//                                        UserDetails.song = url;
////                                        Toast.makeText(SettingsActivity.this, UserDetails.song + " is the url", Toast.LENGTH_SHORT).show();
//                                        playMusic(newtime);
//                                        //getTimeFromFirebase();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(FirebaseError firebaseError) {
//
//                                }
//                            });


                            //getUrl(songtosync);
                            // playMusic(newtime);

                    }
                }
          //  }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.child("IDReq").getValue().toString().equals(ID)) {
                    String time = dataSnapshot.child("Time").getValue().toString();
                    String song = dataSnapshot.child("Song").getValue().toString();
                    newtime = Integer.parseInt(time);
//
                    //// TODO: 28/09/2017 add in action buton - tunein add to recentlyplayed 
//                    Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("RecentlyPlayed").child(ID);
//                    likedRef.push().setValue(song);

                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                    Firebase songRef = ref.child("URL").child(song);

                    songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                            for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                                url = String.valueOf(dsp.getValue());
                                UserDetails.song = url;
                                playMusic(newtime);
                                //getTimeFromFirebase();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
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
                i.putExtra("User", user);
                startActivity(i);

            }
        });

        //add click listener for play bar
        play_toolbar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(SettingsActivity.this, AndroidBuildingMusicPlayerActivity.class);
                intent_info.putExtra("Uniqid", "FromSettings");
                if (mediaPlayer.isPlaying()) {
                    intent_info.putExtra("Song", track_title.getText().toString());
                }
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            }
        });

        //add click listeners for songs listview
        //showing clicked song in play toolbar.
        slistView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                song = ((TextView) view).getText().toString();
                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                hideSoftKeyboard(SettingsActivity.this);

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

        slistView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                hideSoftKeyboard(SettingsActivity.this);

                return false;
            }
        });

        slistView.setAdapter(sadapter);

        // TODO: 21/06/2017 chat button and function
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, Users.class);
                i.putExtra("Uniqid","FromSettings");
                if(mediaPlayer.isPlaying()){
                    i.putExtra("Song", track_title.getText().toString());
                }
                startActivity(i);
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, LibraryActivity.class);
                i.putExtra("Uniqid","FromSettings");
                if(mediaPlayer.isPlaying()){
                    i.putExtra("Song", track_title.getText().toString());
                }
                startActivity(i);
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
        //searchView.bringToFront();//
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

            @Override
            public void onSearchViewShown() {
                if(mediaPlayer.isPlaying()) {
                    play_toolbar.setVisibility(View.VISIBLE);
                } else play_toolbar.setVisibility(View.GONE);
                nowPlayingLayout.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);
                //searchView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.INVISIBLE);
                fab1.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onSearchViewClosed() {
                finish();
                //overridePendingTransition( 0, 0);
                Intent inte = getIntent();
                inte.putExtra("ID", "FromSearch");
                inte.putExtra("Song", song);
                startActivity(inte);
                overridePendingTransition( 0, 0);
                searchLayout.setVisibility(View.GONE);

//                fab.setVisibility(View.VISIBLE);
//                fab1.setVisibility(View.VISIBLE);
//                if(!mediaPlayer.isPlaying()) {
//                    play_toolbar.setVisibility(View.GONE);
//                } else play_toolbar.setVisibility(View.VISIBLE);
//                nowPlayingLayout.setVisibility(View.VISIBLE);


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

    public void getFollowers(String fullname, final String mysong) {

        final ArrayAdapter<String> fadapter;

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

    public void startMusic(String link, String song) {
        //play_toolbar.setVisibility(View.VISIBLE);
        //play_toolbar.bringToFront();

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

        Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("RecentlyPlayed").child(ID);
        likedRef.push().setValue(song);
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
    }

    public void eraseFromFirebase() {
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Homepage");
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
                            if (dataSnapshot.child(v).hasChild(getMyFullname(ID))) {

                               // dataSnapshot.child(v).getRef().removeValue();
                                dataSnapshot.child(v).child(getMyFullname(ID)).getRef().removeValue();

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

    public void playFromPause(Integer time) {

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
    public Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            Utilities utils = new Utilities();
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

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

        FirebaseAuth fb;
        fb = FirebaseAuth.getInstance();

        String ID;
        ID = fb.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

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

    public String getMyFullname(String id) {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(id).child("Name");

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


    public void addToHome(List<String> myvalue, final String mysong) {

        String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");

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
    public String getSongName(int layoutno) {
        String songToPlay = titlesArray[layoutno].getText().toString();
        Toast.makeText(SettingsActivity.this, songToPlay, Toast.LENGTH_SHORT).show();
        return songToPlay;
    }

    public String getPersonNowPlaying(int layoutno) {
        return namesArray[layoutno].getText().toString();
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

    public void sendTimeRequest(String listenerFullname, String url) {

        String me = firebaseAuth1.getCurrentUser().getUid();

        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/TimeRequest/" + me);
        Map<String, Object> uinfo = new HashMap<>();
        uinfo.put("Name", listenerFullname);
        uinfo.put("Song", url);
        ref.updateChildren(uinfo);

        //getSongName();
    }

    public void addTimeToFirebase(String otherUser, String song) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/TimeAnswer/" + ID);
        Map<String, Object> uinfo = new HashMap<>();
        uinfo.put("Time", getCurrentPlayingTime());
        uinfo.put("IDReq", otherUser);

        //777
        //getUrl(song);
        uinfo.put("Song", song);
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

        try {
            mediaPlayer.setDataSource(UserDetails.song);
            mediaPlayer.prepare();
            mediaPlayer.seekTo(time+1300);
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

//    public void viewOnYoutube(View v) {
//
//        youtube.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String songToView = getSongName();
//
//                //youtube.setVisibility(View.GONE);
//                //youtubecoloured.setVisibility(View.VISIBLE);
//
//                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
//                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");
//
//                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
//                    @Override
//                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                        String link = dataSnapshot.getValue().toString();
//                        Intent viewIntent =
//                                new Intent("android.intent.action.VIEW",
//                                        Uri.parse(link));
//                        startActivity(viewIntent);
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
//            }
//        });
//    }

//    public void viewOnYoutubeAgain(View v) {
//        youtubecoloured.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String songToView = getSongName();
//
//                youtubecoloured.setVisibility(View.VISIBLE);
//
//                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
//                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");
//
//                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
//                    @Override
//                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
//                        String link = dataSnapshot.getValue().toString();
//                        Intent viewIntent =
//                                new Intent("android.intent.action.VIEW",
//                                        Uri.parse(link));
//                        startActivity(viewIntent);
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
//            }
//        });
//    }

//    public void comment(View v) {
//        comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //comment.setVisibility(View.GONE);
//                //commentblue.setVisibility(View.VISIBLE);
//            }
//        });
//    }

    public void addToPlaylist(View v){
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add.setVisibility(View.GONE);
                //addgreen.setVisibility(View.VISIBLE);
            }
        });
    }

    public void addToPlaylistAgain(View v) {
        addgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Already added to your playlist", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void download(View v){
//        download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String songToDown = getSongName();
//
//                StorageReference storageReference = mStorage.getReferenceFromUrl("gs://tunein-633e5.appspot.com/bad boi muzik");
//                StorageReference down = storageReference.child(songToDown + ".mp3");
//
//                storagePath = new File(view.getContext().getFilesDir(), "My_music");
//                File localFile = new File(storagePath, "track1.mp3");
//                try {
//                    localFile = File.createTempFile("Audio", "mp3");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                final File finalLocalFile = new File(storagePath, "track1.mp3");
//                down.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        finalLocalFile.getAbsolutePath();
//                        Toast.makeText(getApplicationContext(), "Downloded at location: " + finalLocalFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                        UserDetails.song = finalLocalFile.getAbsolutePath();
//                        //download.setVisibility(View.GONE);
//                        //downloadgreen.setVisibility(View.VISIBLE);
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//
//                    }
//                });
//            }
//        });
//    }
//
//    public void downloadAgain(View v){
//        downloadgreen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Already downloaded at location: " + UserDetails.song, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    public void likeSong(View v){
//        blackHeart.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                String songToLike = getSongName();
//                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
//                likedRef.push().setValue(songToLike);
//
//                blackHeart.setVisibility(View.GONE);
//                redHeart.setVisibility(View.VISIBLE);
//
//            }
//        });
//    }
//
//    public void dislikeSong(View v){
//        redHeart.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(final View v) {
//
//                final String songLiked = getSongName();
//
//                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);
//
//                mDatabase1.addListenerForSingleValueEvent(
//                        new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                    String key = snapshot.getKey();
//
//                                    Toast.makeText(getApplicationContext(), "the key: " + key, Toast.LENGTH_SHORT).show();
//
//
//                                    if (dataSnapshot.child(key).getValue().equals(songLiked)) {
//                                        Toast.makeText(getApplicationContext(), "in if: " + songLiked, Toast.LENGTH_SHORT).show();
//
//                                        dataSnapshot.child(key).getRef().removeValue();
//                                        redHeart.setVisibility(View.INVISIBLE);
//                                        blackHeart.setVisibility(View.VISIBLE);
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//            }
//        });
//    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//
//            case R.id.oneButton:
//                // do your code
//                break;
//
//            case R.id.twoButton:
//                // do your code
//                break;
//
//            case R.id.threeButton:
//                // do your code
//                break;
//
//            default:
//                break;
//        }
//    }



    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.


        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            String launchUrl = result.notification.payload.launchURL; // update docs launchUrl

            String customKey;
            openURL = null;
            Object activityToLaunch = SettingsActivity.class;

            if (data != null) {
                customKey = data.optString("customkey", null);
                openURL = data.optString("openURL", null);

                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey);

                if (openURL != null)
                    Log.i("OneSignalExample", "openURL to webview with URL value: " + openURL);
            }

            if (actionType == OSNotificationAction.ActionType.ActionTaken) {
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);
                if (result.action.actionID.equals("listenwith")) {
                    //getting invitation details from firebase

                    FirebaseAuth fb;
                    fb = FirebaseAuth.getInstance();

                    String ID;
                    ID = fb.getCurrentUser().getUid();

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

                    mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            UserDetails.fullname = dataSnapshot.getValue().toString();
                            UserDetails.username = dataSnapshot.getValue().toString();

                            reference3 = FirebaseDatabase.getInstance().getReference().child("ListenWith").child(UserDetails.fullname);
                            reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String friend = dataSnapshot.child("Name").getValue().toString();
                                    String song = dataSnapshot.child("Song").getValue().toString();

                                    Intent intent = new Intent(getApplicationContext(), Chat.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("openURL", openURL);
                                    intent.putExtra("Uniqid", "NotificationListenWith");
                                    UserDetails.chatWith = friend;
                                    intent.putExtra("Friend", friend);
                                    intent.putExtra("Song", song);
                                    sendTimeRequest(friend,song);
                                    startActivity(intent);

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

                } else if (result.action.actionID.equals("shareSong")){
                    FirebaseAuth fb;
                    fb = FirebaseAuth.getInstance();

                    String ID;
                    ID = fb.getCurrentUser().getUid();

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

                    mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            UserDetails.fullname = dataSnapshot.getValue().toString();
                            UserDetails.username = dataSnapshot.getValue().toString();

                            reference3 = FirebaseDatabase.getInstance().getReference().child("ShareWith").child(UserDetails.fullname);
                            reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String friend = dataSnapshot.child("Name").getValue().toString();
                                    String song = dataSnapshot.child("Song").getValue().toString();

                                    Intent intent = new Intent(getApplicationContext(), Chat.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra("openURL", openURL);
                                    intent.putExtra("Uniqid", "NotificationShareWith");
                                    UserDetails.chatWith = friend;
                                    intent.putExtra("Friend", friend);
                                    intent.putExtra("Song", song);
                                    startActivity(intent);

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



                    ///
                    Intent intent = new Intent(getApplicationContext(), Chat.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("openURL", openURL);
                    intent.putExtra("Uniqid", "NotificationShareWith");
                    startActivity(intent);

                }
                else {
                    Log.i("OneSignalExample", "button id called: " + result.action.actionID);
                }

            }
            // The following can be used to open an Activity of your choice.
            // Replace - getApplicationContext() - with any Android Context.
            // Intent intent = new Intent(getApplicationContext(), YourActivity.class);


            // Add the following to your AndroidManifest.xml to prevent the launching of your main Activity
            //   if you are calling startActivity above.
     /*
        <application ...>
          <meta-data android:name="com.onesignal.NotificationOpened.DEFAULT" android:value="DISABLE" />
        </application>
     */


        }
    }
}




