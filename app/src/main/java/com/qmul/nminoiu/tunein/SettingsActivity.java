package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;

import com.google.firebase.storage.UploadTask;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
import com.squareup.picasso.Picasso;

import android.view.View.OnClickListener;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

public class SettingsActivity extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener {


    private MaterialSearchView searchView;
    private LinearLayout searchLayout;
    private TextView email;
    private FirebaseAuth firebaseAuth;
    private String username = "";
    private static final String TAG = "MainActivity";
    private ArrayList<String> songs = new ArrayList<>();
    private ArrayList<String> users = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> pictures = new ArrayList<>();

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
    private Firebase reference1;
    private Firebase reference2;
    private DatabaseReference reference3;
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
    private LinearLayout commentsLayout;
    private LinearLayout makePublic;
    private LinearLayout makePrivate;
    public TextView track_title;
    public TextView sName;
    private StorageReference storage;
    private boolean playPause;
    private boolean intialStage = true;
    private Button btn;
    private Button playOnToolbar;
    private Button pauseOnToolbar;
    private Button yesPublic;
    private Button cancelPublic;
    private Button yesPrivate;
    private Button cancelPrivate;
    private ImageView commentButton;
    private CoordinatorLayout mainL;


    private boolean play;
    private boolean pause;
    public static String url;
    private RelativeLayout picLayout;
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
    private Button uploadImg;
    private ImageView profilePic;
    private ImageView friendPic1;
    private ImageView friendPic2;
    private ImageView friendPic3;
    private ImageView friendPic4;
    private ImageView friendPic5;

    private ArrayList<String> recents;
    private ScrollView ScrollView01;
    public User myuser;
    public File storagePath;
    public LinearLayout np1;
    public LinearLayout np2;
    private EditText commentarea;
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
    public String pictureUrl;
    private String sender;

    private String time;
    private Boolean isTunned = false;
    private int i;
    int PICK_IMAGE_REQUEST;
    private Uri filePath;
    private ProgressDialog pd;
    private StorageReference picRef;
    private CoordinatorLayout.LayoutParams paramsFab1;
    private CoordinatorLayout.LayoutParams paramsFab;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setContentView(savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //initialising needed variables
        commentsLayout = (LinearLayout) findViewById(R.id.commentLayout);
        commentarea = (EditText) findViewById(R.id.commentArea);
        commentButton = (ImageView) findViewById(R.id.commentButton);


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

        makePrivate = (LinearLayout) findViewById(R.id.makePrivate);
        makePublic = (LinearLayout) findViewById(R.id.makePublic);
        mainL = (CoordinatorLayout) findViewById(R.id.cLayout);


        //picLayout = (RelativeLayout) findViewById(R.id.picLayout);
        uploadImg = (Button) findViewById(R.id.uploadImg);
        yesPublic = (Button) findViewById(R.id.yesPublic);
        cancelPublic = (Button) findViewById(R.id.cancelPublic);
        yesPrivate = (Button) findViewById(R.id.yesPrivate);
        cancelPrivate = (Button) findViewById(R.id.cancelPrivate);

        // profilePic = (ImageView) findViewById(R.id.profilePic);
       // profilePic.setClickable(true);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        picRef = storage.getReferenceFromUrl("gs://tunein-633e5.appspot.com/ProfilePictures");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Now Playing");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));


        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        Menu nav_Menu = navigationView.getMenu();
        //
        ScrollView01 = (ScrollView) findViewById(R.id.ScrollView01);
        checkBox = (CheckBox) nav_Menu.findItem(R.id.nav_private).getActionView();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        makeAccountPrivate();
                    } else {
                        makeAccountPublic();
                    }
             }

        });


        View hView = navigationView.getHeaderView(0);

        profilePic = (ImageView) hView.findViewById(R.id.profilePic);
        TextView nav_user = (TextView) hView.findViewById(R.id.emailProfile);
        //email = (TextView) findViewById(R.id.emailProfile);
        Intent i = getIntent();
        if(i.hasExtra("Email")){
            nav_user.setText(getIntent().getExtras().getString("Email"));
        }
        toolbar.setTitle("Now Playing");


        ll = new LinearLayout[5];
        ll[0] = np1;
        ll[1] = np2;
        ll[2] = np3;
        ll[3] = np4;
        ll[4] = np5;
        PICK_IMAGE_REQUEST = 111;
        tuneOutBtn = (ImageButton) findViewById(R.id.tuneout_btn);
        blackHeart = (ImageButton) findViewById(R.id.blackHeart);
        redHeart = (ImageButton) findViewById(R.id.redHeart);
        firebaseAuth1 = FirebaseAuth.getInstance();
        user = firebaseAuth1.getCurrentUser();
        loggedEmail = user.getEmail();
        ID = firebaseAuth1.getCurrentUser().getUid();
        sender = firebaseAuth1.getCurrentUser().getEmail();
        OneSignal.sendTag("User_ID", loggedEmail);
        mStorage = FirebaseStorage.getInstance();
        btn = (Button) findViewById(R.id.button);
        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        track_title = (TextView) findViewById(R.id.track_title);
        recents = new ArrayList<>();

        paramsFab1 = (CoordinatorLayout.LayoutParams) fab1.getLayoutParams();
        paramsFab = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();

        //initialising OneSignal for notifications
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .init();

        //getting intent and extras from other activities
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

        //now playing layout - initialising youtube button
        View v1 = findViewById(R.id.np1);
        ImageButton youtube1 = (ImageButton) v1.findViewById(R.id.youtube);
        View v2 = findViewById(R.id.np2);
        ImageButton youtube2 = (ImageButton) v2.findViewById(R.id.youtube);
        View v3 = findViewById(R.id.np3);
        ImageButton youtube3 = (ImageButton) v3.findViewById(R.id.youtube);
        View v4 = findViewById(R.id.np4);
        ImageButton youtube4 = (ImageButton) v4.findViewById(R.id.youtube);
        View v5 = findViewById(R.id.np5);
        ImageButton youtube5 = (ImageButton) v5.findViewById(R.id.youtube);

        //now playing layout - initialising download button
        View a1 = findViewById(R.id.np1);
        download1 = (ImageButton) a1.findViewById(R.id.download);
        View a2 = findViewById(R.id.np2);
        download2 = (ImageButton) a2.findViewById(R.id.download);
        View a3 = findViewById(R.id.np3);
        download3 = (ImageButton) a3.findViewById(R.id.download);
        View a4 = findViewById(R.id.np4);
        download4 = (ImageButton) a4.findViewById(R.id.download);
        View a5 = findViewById(R.id.np5);
        download5 = (ImageButton) a5.findViewById(R.id.download);

        //now playing layout - initialising comment button
        View c1 = findViewById(R.id.np1);
        comment1 = (ImageButton) c1.findViewById(R.id.comment);
        View c2 = findViewById(R.id.np2);
        comment2 = (ImageButton) c2.findViewById(R.id.comment);
        View c3 = findViewById(R.id.np3);
        comment3 = (ImageButton) c3.findViewById(R.id.comment);
        View c4 = findViewById(R.id.np4);
        comment4 = (ImageButton) c4.findViewById(R.id.comment);
        View c5 = findViewById(R.id.np5);
        comment5 = (ImageButton) c5.findViewById(R.id.comment);

        //now playing layout - initialising add to playlist button
        View ad1 = findViewById(R.id.np1);
        add1 = (ImageButton) ad1.findViewById(R.id.add);
        View ad2 = findViewById(R.id.np2);
        add2 = (ImageButton) ad2.findViewById(R.id.add);
        View ad3 = findViewById(R.id.np3);
        add3 = (ImageButton) ad3.findViewById(R.id.add);
        View ad4 = findViewById(R.id.np4);
        add4 = (ImageButton) ad4.findViewById(R.id.add);
        View ad5 = findViewById(R.id.np5);
        add5 = (ImageButton) ad5.findViewById(R.id.add);

        //now playing layout - initialising like button
        View b1 = findViewById(R.id.np1);
        bheart1 = (ImageButton) b1.findViewById(R.id.blackHeart);
        View b2 = findViewById(R.id.np2);
        bheart2 = (ImageButton) b2.findViewById(R.id.blackHeart);
        View b3 = findViewById(R.id.np3);
        bheart3 = (ImageButton) b3.findViewById(R.id.blackHeart);
        View b4 = findViewById(R.id.np4);
        bheart4 = (ImageButton) b4.findViewById(R.id.blackHeart);
        View b5 = findViewById(R.id.np5);
        bheart5 = (ImageButton) b5.findViewById(R.id.blackHeart);

        //now playing layout - initialising dislike button
        View r1 = findViewById(R.id.np1);
        rheart1 = (ImageButton) r1.findViewById(R.id.redHeart);
        View r2 = findViewById(R.id.np2);
        rheart2 = (ImageButton) r2.findViewById(R.id.redHeart);
        View r3 = findViewById(R.id.np3);
        rheart3 = (ImageButton) r3.findViewById(R.id.redHeart);
        View r4 = findViewById(R.id.np4);
        rheart4 = (ImageButton) r4.findViewById(R.id.redHeart);
        View r5 = findViewById(R.id.np5);
        rheart5 = (ImageButton) r5.findViewById(R.id.redHeart);

        //now playing layout - initialising synchronisation button
        View ti1 = findViewById(R.id.np1);
        tunein1 = (ImageButton) ti1.findViewById(R.id.syncButton);
        View ti2 = findViewById(R.id.np2);
        tunein2 = (ImageButton) ti2.findViewById(R.id.syncButton);
        View ti3 = findViewById(R.id.np3);
        tunein3 = (ImageButton) ti3.findViewById(R.id.syncButton);
        View ti4 = findViewById(R.id.np4);
        tunein4 = (ImageButton) ti4.findViewById(R.id.syncButton);
        View ti5 = findViewById(R.id.np5);
        tunein5 = (ImageButton) ti5.findViewById(R.id.syncButton);

        //now playing layout - initialising tune out button
        View to1 = findViewById(R.id.np1);
        tuneout1 = (ImageButton) to1.findViewById(R.id.tuneout_btn);
        View to2 = findViewById(R.id.np2);
        tuneout2 = (ImageButton) to2.findViewById(R.id.tuneout_btn);
        View to3 = findViewById(R.id.np3);
        tuneout3 = (ImageButton) to3.findViewById(R.id.tuneout_btn);
        View to4 = findViewById(R.id.np4);
        tuneout4 = (ImageButton) to4.findViewById(R.id.tuneout_btn);
        View to5 = findViewById(R.id.np5);
        tuneout5 = (ImageButton) to5.findViewById(R.id.tuneout_btn);

        //now playing layout - initialising textView for friend name
        View n1 = findViewById(R.id.np1);
        name1 = (TextView) n1.findViewById(R.id.name);
        View n2 = findViewById(R.id.np2);
        name2 = (TextView) n2.findViewById(R.id.name);
        View n3 = findViewById(R.id.np3);
        name3 = (TextView) n3.findViewById(R.id.name);
        View n4 = findViewById(R.id.np4);
        name4 = (TextView) n4.findViewById(R.id.name);
        View n5 = findViewById(R.id.np5);
        name5 = (TextView) n5.findViewById(R.id.name);

        //now playing layout - initialising textView for song name
        View t1 = findViewById(R.id.np1);
        title1 = (TextView) t1.findViewById(R.id.songName);
        View t2 = findViewById(R.id.np2);
        title2 = (TextView) t2.findViewById(R.id.songName);
        View t3 = findViewById(R.id.np3);
        title3 = (TextView) t3.findViewById(R.id.songName);
        View t4 = findViewById(R.id.np4);
        title4 = (TextView) t4.findViewById(R.id.songName);
        View t5 = findViewById(R.id.np5);
        title5 = (TextView) t5.findViewById(R.id.songName);

        View i1 = findViewById(R.id.np1);
        friendPic1 = (ImageView) i1.findViewById(R.id.friendPic);
        View i2 = findViewById(R.id.np2);
        friendPic2 = (ImageView) i2.findViewById(R.id.friendPic);
        View i3 = findViewById(R.id.np3);
        friendPic3 = (ImageView) i3.findViewById(R.id.friendPic);
        View i4 = findViewById(R.id.np4);
        friendPic4 = (ImageView) i4.findViewById(R.id.friendPic);
        View i5 = findViewById(R.id.np5);
        friendPic5 = (ImageView) i5.findViewById(R.id.friendPic);

        //storing buttons in ButtonArrays
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


        //sliding menu settings
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        DatabaseReference profileref = FirebaseDatabase.getInstance().getReference().child("PublicProfiles");
        profileref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(ID)) {
                        UserDetails.publicProfie = true;

                    Menu nav_Menu = navigationView.getMenu();
                    nav_Menu.findItem(R.id.nav_private).setChecked(false);
                    checkBox.setChecked(false);
                } else{
                    UserDetails.publicProfie = false;
                    Menu nav_Menu = navigationView.getMenu();
                    nav_Menu.findItem(R.id.nav_private).setChecked(true);
                    checkBox.setChecked(true);

                }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cancelPublic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                makePublic.setVisibility(View.GONE);
            }
        });

        cancelPrivate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                makePrivate.setVisibility(View.GONE);
            }
        });

        yesPublic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

                dr.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserDetails.fullname = dataSnapshot.getValue().toString();
                        String myname = dataSnapshot.getValue().toString();

                        Firebase ref1 = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase userRef1 = ref1.child("PublicProfiles");
                        Map<String,Object> uinfo1 = new HashMap<String, Object>();
                        uinfo1.put(ID,myname);
                        userRef1.updateChildren(uinfo1);

                        Menu nav_Menu = navigationView.getMenu();

                        nav_Menu.findItem(R.id.nav_private).setVisible(false);
                        //Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

                makePublic.setVisibility(View.GONE);
            }
        });

        yesPrivate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

                dr.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserDetails.fullname = dataSnapshot.getValue().toString();
                        String myname = dataSnapshot.getValue().toString();

                        Firebase ref1 = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase userRef1 = ref1.child("PrivateProfiles");
                        Map<String,Object> uinfo1 = new HashMap<String, Object>();
                        uinfo1.put(ID,myname);
                        userRef1.updateChildren(uinfo1);
                        //Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

                DatabaseReference profileref = FirebaseDatabase.getInstance().getReference().child("PublicProfiles");
                profileref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
                            dataSnapshot.child(ID).getRef().removeValue();
                            UserDetails.publicProfie = false;

                            Menu nav_Menu = navigationView.getMenu();
                            nav_Menu.findItem(R.id.nav_private).setVisible(true);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                makePrivate.setVisibility(View.GONE);
            }
        });

        ScrollView01.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });

        nowPlayingLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });

        mainL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });



        //synchronisation buttons for Now Playing layout
        tunein1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTimeRequest(name1.getText().toString(), title1.getText().toString());
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });

        tunein2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });

        tunein3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTimeRequest(name3.getText().toString(), title3.getText().toString());
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });

        tunein4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });

        tunein5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });

//        Glide.with(this /* context */)
//                .load("https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/ProfilePictures%2FiFisFj7zKLeuzqsg1vBMxf6gWst2.jpg?alt=media&token=4b473216-6251-4d2b-af36-13f9e495d21d")
//                .into(profilePic);

        Firebase picture = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase picture1 = picture.child("ProfilePictures");

        picture1.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(ID)){
                    String link = dataSnapshot.child(ID).child("Url").getValue().toString();
                    UserDetails.picturelink = link;

                        Picasso.with(SettingsActivity.this)
                                .load(link)
//                .resize(350, 240)
//                .centerInside()

                                .fit()
                                //.centerCrop()
                                .into(profilePic);

            }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

//        Picasso.with(this)
//                .load(UserDetails.picturelink)//                .resize(350, 240)
////                .centerInside()
//
//                .fit()
//                //.centerCrop()
//                .into(profilePic);

        //tune out buttons for Now Playing layout
        tuneout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuneout1.setVisibility(View.GONE);
                tunein1.setVisibility(View.VISIBLE);
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
               // mediaPlayer.release();

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

                DatabaseReference reqdb1 = FirebaseDatabase.getInstance().getReference().child("TimeAnswer");
                reqdb1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    String reqId = snapshot.getKey().toString();
                                    if(dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)){
                                        dataSnapshot.child(reqId).getRef().removeValue();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                            }
                        });

            }
        });

        tuneout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuneout2.setVisibility(View.GONE);
                tunein2.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                //mediaPlayer.release();

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
                DatabaseReference reqdb1 = FirebaseDatabase.getInstance().getReference().child("TimeAnswer");
                reqdb1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    String reqId = snapshot.getKey().toString();
                                    if(dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)){
                                        dataSnapshot.child(reqId).getRef().removeValue();
                                    }
                                }
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
                mediaPlayer.pause();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                //mediaPlayer.release();

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
                DatabaseReference reqdb1 = FirebaseDatabase.getInstance().getReference().child("TimeAnswer");
                reqdb1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    String reqId = snapshot.getKey().toString();
                                    if(dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)){
                                        dataSnapshot.child(reqId).getRef().removeValue();
                                    }
                                }
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
                mediaPlayer.pause();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                //mediaPlayer.release();

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
                DatabaseReference reqdb1 = FirebaseDatabase.getInstance().getReference().child("TimeAnswer");
                reqdb1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    String reqId = snapshot.getKey().toString();
                                    if(dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)){
                                        dataSnapshot.child(reqId).getRef().removeValue();
                                    }
                                }
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
                mediaPlayer.pause();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                //mediaPlayer.release();

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
                DatabaseReference reqdb1 = FirebaseDatabase.getInstance().getReference().child("TimeAnswer");
                reqdb1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    String reqId = snapshot.getKey().toString();
                                    if(dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)){
                                        dataSnapshot.child(reqId).getRef().removeValue();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                            }
                        });
            }
        });

        //listeners for YouTube buttons - NowPlaying Layout
        youtube1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title1.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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


//        uploadImg.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (filePath != null) {
//                    pd.show();
//
//                    StorageReference childRef = picRef.child(ID + "_image.jpg");
//
//                    //uploading the image
//                    UploadTask uploadTask = childRef.putFile(filePath);
//
//                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            pd.dismiss();
//                            Toast.makeText(SettingsActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
//                            picLayout.setVisibility(View.GONE);
//                            if (mediaPlayer.isPlaying()) {
//                                paramsFab1.setMargins(0, 0, 43, 150); //bottom margin is 25 here (change it as u wish)
//                                fab1.setLayoutParams(paramsFab1);
//                                paramsFab.setMargins(53, 0, 0, 160); //bottom margin is 25 here (change it as u wish)
//                                fab.setLayoutParams(paramsFab);
//                                fab1.setVisibility(View.VISIBLE);
//                                fab.setVisibility(View.VISIBLE);
//                            } else {
//                                fab1.setVisibility(View.VISIBLE);
//                                fab.setVisibility(View.VISIBLE);
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            pd.dismiss();
//                            Toast.makeText(SettingsActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    Toast.makeText(SettingsActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        youtube3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title3.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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

        //listeners for download buttons - NowPlaying Layout
        download1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String songToDown = title1.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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
                        Toast.makeText(getApplicationContext(), "Done downloading", Toast.LENGTH_SHORT).show();
                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase playRef = ref.child("DownloadedSongs").child(ID);
                        playRef.push().setValue(songToDown);

                        UserDetails.song = finalLocalFile.getAbsolutePath();

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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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
                        Toast.makeText(getApplicationContext(), "Done downloading", Toast.LENGTH_SHORT).show();
                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase playRef = ref.child("DownloadedSongs").child(ID);
                        playRef.push().setValue(songToDown);

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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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
                        Toast.makeText(getApplicationContext(), "Done downloading", Toast.LENGTH_SHORT).show();
                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase playRef = ref.child("DownloadedSongs").child(ID);
                        playRef.push().setValue(songToDown);

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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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
                        Toast.makeText(getApplicationContext(), "Done downloading", Toast.LENGTH_SHORT).show();
                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase playRef = ref.child("DownloadedSongs").child(ID);
                        playRef.push().setValue(songToDown);

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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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
                        Toast.makeText(getApplicationContext(), "Done downloading", Toast.LENGTH_SHORT).show();
                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase playRef = ref.child("DownloadedSongs").child(ID);
                        playRef.push().setValue(songToDown);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
            }
        });

        //// TODO: 18/10/2017
        //listeners for comment buttons - NowPlaying Layout
        comment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name1.getText().toString();
                UserDetails.commentOn = title1.getText().toString();

            }
        });

        comment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name2.getText().toString();
                UserDetails.commentOn = title2.getText().toString();
            }
        });

        comment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name3.getText().toString();
                UserDetails.commentOn = title3.getText().toString();
            }
        });

        comment4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name4.getText().toString();
                UserDetails.commentOn = title4.getText().toString();
            }
        });

        comment5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name5.getText().toString();
                UserDetails.commentOn = title5.getText().toString();
            }
        });


        commentButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentarea.getText().toString();

                if(!comment.isEmpty()) {
                    getReceiver(UserDetails.commentOn, UserDetails.commentTo, comment);
                } else {
                    Toast.makeText(SettingsActivity.this, "Please write a comment", Toast.LENGTH_SHORT).show();
                }
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });

        //// TODO: 18/10/2017
        //listeners for add to playlist buttons - NowPlaying Layout
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, PlaylistsActivity.class);
                final String song = title1.getText().toString();
                i.putExtra("Uniqid", "FromNowPlayling");
                i.putExtra("Song", song);
                startActivity(i);
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(SettingsActivity.this, PlaylistsActivity.class);
                final String song = title2.getText().toString();
                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(SettingsActivity.this, PlaylistsActivity.class);
                final String song = title3.getText().toString();
                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(SettingsActivity.this, PlaylistsActivity.class);
                final String song = title4.getText().toString();
                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(SettingsActivity.this, PlaylistsActivity.class);
                final String song = title5.getText().toString();

                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
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

        final DatabaseReference recentsRef = FirebaseDatabase.getInstance().getReference().child("RecentlyPlayed").child(ID);
        recentsRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey().toString();
                    String recentsong = dataSnapshot.child(key).getValue().toString();
                    recents.add(recentsong);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //listeners for like buttons - NowPlaying Layout
        bheart1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                String songToLike = title5.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart5.setVisibility(View.GONE);
                rheart5.setVisibility(View.VISIBLE);

            }
        });

        //listeners for dislike buttons - NowPlaying Layout
        rheart1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
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
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
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

        //Firebase references to retrieve data
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Following").child(ID);
        db = FirebaseDatabase.getInstance().getReference().child("PublicProfiles");
        db1 = FirebaseDatabase.getInstance().getReference().child("Songs");
        db2 = FirebaseDatabase.getInstance().getReference().child("Test");

        //getting current user fullname
        mDatabase5 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
        mDatabase5.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //initialising list views and adapters for songs and friends
        ulistView = (ListView) findViewById(R.id.plistView);
        uadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        ulistView.setAdapter(uadapter);

        slistView = (ListView) findViewById(R.id.slistView);
        sadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs);
        slistView.setAdapter(sadapter);

        ulistView.setClickable(true);
        slistView.setClickable(true);
        play_toolbar.setClickable(true);

        //listener for time request Firebase child
        mDatabase9 = FirebaseDatabase.getInstance().getReference().child("TimeRequest");
        mDatabase9.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String otherUser;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    otherUser = dataSnapshot.getKey();
                    if (dataSnapshot.child("Name").getValue().toString().equals(UserDetails.fullname)) {
                        String song = dataSnapshot.child("Song").getValue().toString();
                        addTimeToFirebase(otherUser, song);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String otherUser;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    otherUser = dataSnapshot.getKey();
                    if (dataSnapshot.child("Name").getValue().toString().equals(UserDetails.fullname)) {
                        String song = dataSnapshot.child("Song").getValue().toString();
                        addTimeToFirebase(otherUser, song);
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

        //listener for Homepage Firebase child
        mDatabase8 = FirebaseDatabase.getInstance().getReference().child("Homepage").child(ID);
        mDatabase8.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               // for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    text = dataSnapshot.getKey();
                    names.add(text);
                    String mysong = dataSnapshot.child("Song").getValue().toString();
                    title.add(mysong);
                    String picture = dataSnapshot.child("Picture").getValue().toString();
                    pictures.add(picture);
               // }
                for (int i = 0; i <= names.size() - 1; i++) {
                    Toast.makeText(SettingsActivity.this, "Fullname and size " + names.get(i)+names.size(), Toast.LENGTH_SHORT).show();

                    ll[i].setVisibility(View.VISIBLE);
                    TextView name = (TextView) ll[i].findViewById(R.id.name);
                    name.setText(names.get(i));
                    TextView song = (TextView) ll[i].findViewById(R.id.songName);
                    song.setText(title.get(i));
                    ImageView pic = (ImageView) ll[i].findViewById(R.id.friendPic);
                    Picasso.with(SettingsActivity.this)
                            .load(pictures.get(i).toString())
//                .resize(350, 240)
//                .centerInside()

                            .fit()
                            .centerCrop()
                            .into(pic);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
             //   for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    text = dataSnapshot.getKey();
                    String mysong = dataSnapshot.child("Song").getValue().toString();
                    String fpic = dataSnapshot.child("Picture").getValue().toString();


                    for (int i = 0; i <= names.size() - 1; i++) {
                        if (text.equals(names.get(i))) {
                            names.set(i, text);
                            title.set(i, mysong);
                            pictures.set(i,fpic);
                            TextView name = (TextView) ll[i].findViewById(R.id.name);
                            name.setText(text);
                            TextView song = (TextView) ll[i].findViewById(R.id.songName);
                            song.setText(mysong);
                            ImageView pict = (ImageView) ll[i].findViewById(R.id.friendPic);
                            Picasso.with(SettingsActivity.this)
                                    .load(fpic)
//                .resize(350, 240)
//                .centerInside()

                                    .fit()
                                    .centerCrop()
                                    .into(pict);
                        }
                    }
               // }
//                for (int i = 0; i <= names.size() - 1; i++) {
//                    ll[i].setVisibility(View.VISIBLE);
//                    TextView name = (TextView) ll[i].findViewById(R.id.name);
//                    name.setText(names.get(i));
//                    TextView song = (TextView) ll[i].findViewById(R.id.songName);
//                    song.setText(title.get(i));
//                    ImageView pic = (ImageView) ll[i].findViewById(R.id.friendPic);
//                    Picasso.with(SettingsActivity.this)
//                            .load(pictures.get(i).toString())
////                .resize(350, 240)jūlo
////                .centerInside()
//
//                            .fit()
//                            //.centerCrop()
//                            .into(pic);
//                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
               // for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    text = dataSnapshot.getKey();
                    String mysong = dataSnapshot.child("Song").getValue().toString();

                    String picture = dataSnapshot.child("Picture").getValue().toString();
                    names.remove(text);
                    title.remove(mysong);
                    pictures.remove(picture);

                    for (int j = 0; j <= ll.length - 1; j++) {
                       // Toast.makeText(SettingsActivity.this, "key names " + text, Toast.LENGTH_SHORT).show();
                        TextView name = (TextView) ll[j].findViewById(R.id.name);
                        TextView song = (TextView) ll[j].findViewById(R.id.songName);
                        ImageView pict = (ImageView) ll[j].findViewById(R.id.friendPic);

                        if (name.getText().equals(text)) {
                            Toast.makeText(SettingsActivity.this, "key names " + text, Toast.LENGTH_SHORT).show();
                            ll[j].setVisibility(View.GONE);
                        }

                    }
                if (mediaPlayer.isPlaying()) {
                    play_toolbar.setVisibility(View.VISIBLE);
                    track_title = (TextView) findViewById(R.id.track_title);
                    track_title.setText(mysong);
                }

            }
           // }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //listener for TimeAnswer Firebase child
        timeref = FirebaseDatabase.getInstance().getReference().child("TimeAnswer");
        timeref.addChildEventListener(new ChildEventListener() {
            String ID = firebaseAuth1.getCurrentUser().getUid();

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.child("IDReq").getValue().toString().equals(ID)) {
                    String time = dataSnapshot.child("Time").getValue().toString();
                    String song = dataSnapshot.child("Song").getValue().toString();
                    newtime = Integer.parseInt(time);
                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                    Firebase songRef = ref.child("URL").child(song);

                    songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                            for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                                url = String.valueOf(dsp.getValue());
                                UserDetails.song = url;
                                playMusic(newtime);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.child("IDReq").getValue().toString().equals(ID)) {
                    String time = dataSnapshot.child("Time").getValue().toString();
                    String song = dataSnapshot.child("Song").getValue().toString();
                    newtime = Integer.parseInt(time);
                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                    Firebase songRef = ref.child("URL").child(song);

                    songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                            for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                                url = String.valueOf(dsp.getValue());
                                UserDetails.song = url;
                                playMusic(newtime);
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

        //adding users from Firebase to ArrayList
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

        //adding songs from Firebase to ArrayList
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

        //setting title for homepage toolbar

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
                final String user = ((TextView) view).getText().toString();
                play_toolbar.setVisibility(View.GONE);
                //nowPlayingLayout.setVisibility(View.GONE);
                play_toolbar.requestLayout();
                hideSoftKeyboard(SettingsActivity.this);
                UserDetails.username = user;

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");

                dbF.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
//                      Toast.makeText(PlaylistSongs.this, "my fullname " + UserDetails.fullname , Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(SettingsActivity.this, ProfileActivity.class);
                        i.putExtra("FriendId", friendID);
                        i.putExtra("FriendName", user);
                        startActivity(i);
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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

        //adding click listeners for songs listview and showing clicked song in play toolbar.
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
        slistView.setAdapter(sadapter);

        //hide keyboard when songs list is touched
        slistView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard(SettingsActivity.this);
                return false;
            }
        });

        //chat button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, Conversations.class);
                i.putExtra("Uniqid", "FromSettings");
                if (mediaPlayer.isPlaying()) {
                    i.putExtra("Song", track_title.getText().toString());
                }
                startActivity(i);
            }
        });

        //library button
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, LibraryActivity.class);
                i.putExtra("Uniqid", "FromSettings");
                if (mediaPlayer.isPlaying()) {
                    i.putExtra("Song", track_title.getText().toString());
                }
                startActivity(i);
            }
        });




        //showing user's email on sliding drawer

        //search layout and functions
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

            @Override
            public void onSearchViewShown() {
                if (mediaPlayer.isPlaying()) {
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
                Intent inte = getIntent();
                inte.putExtra("ID", "FromSearch");
                inte.putExtra("Song", song);
                startActivity(inte);
                overridePendingTransition(0, 0);
                searchLayout.setVisibility(View.GONE);
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

    //getting followers
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

    //setting icon for serach
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        Menu nav_Menu = navigationView.getMenu();

//        final CheckBox checkBox = (CheckBox) nav_Menu.findItem(R.id.nav_private).getActionView();
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    makeAccountPublic();
//                    checkBox.setChecked(false);
//                } else {
//                    makeAccountPrivate();
//                    checkBox.setChecked(true);
//                }
//            }
//
//        });

        return true;
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            MenuItem privateProfile = menu.findItem(R.id.nav_private);
//            //MenuItem dwnOption = menu.findItem(R.id.menu_dwn);
//            //MenuItem remDwnOption = menu.findItem(R.id.menu_remdwn);
//
//            if (UserDetails.publicProfie) {
//                checkBox.
//                privateProfile.setVisible(false);
//            } else {
//                privateProfile.setVisible(true);
//            }
//
//        }
//        return super.onPrepareOptionsMenu(menu);
//
//    }
//
//

    //back button - from search layout
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            email = (TextView) findViewById(R.id.emailProfile);
            email.setText(loggedEmail);
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //handling click on menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        Menu nav_Menu = navigationView.getMenu();
//        nav_Menu.findItem(R.id.nav_private).setVisible(true);
//        if(UserDetails.publicProfie) {
//            nav_Menu.findItem(R.id.nav_private).setChecked(false);
//        } else nav_Menu.findItem(R.id.nav_private).setChecked(true);
//



        return super.onOptionsItemSelected(item);
    }

    //sliding menu options
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_followers) {
            Intent i = new Intent(SettingsActivity.this, FollowersActivity.class);
            i.putExtra("Uniqid", "FromSettingsMenu");
            UserDetails.oldIntent = "FromSettingsMenu";
            startActivity(i);

        } else if (id == R.id.nav_following) {
            Intent i = new Intent(SettingsActivity.this, FollowingActivity.class);
            i.putExtra("Uniqid", "FromSettingsMenu");
            UserDetails.oldIntent = "FromSettingsMenu";
            startActivity(i);

        } else if (id == R.id.profile_pic) {
            {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");
                Intent chooserIntent = Intent.createChooser(intent, "Pick an image.");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

//                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
               // picLayout.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
            }

        } else if (id == R.id.nav_delete) {
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider
                    .getCredential("user@example.com", "password1234");

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
            Intent nextActivity = new Intent(this, LoginActivity.class);
            startActivity(nextActivity);
            eraseFromFirebase();
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent nextActivity = new Intent(this, LoginActivity.class);
            startActivity(nextActivity);
            eraseFromFirebase();
        } else if (id == R.id.nav_private) {

            final CheckBox checkBox= (CheckBox) item.getActionView();

//            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked) {
//                        // perform logic
//                        makeAccountPublic();
//                        item.setChecked(false);
//                        checkBox.setChecked(false);
//                    } else {
//                        makeAccountPrivate();
//                        item.setChecked(true);
//                        checkBox.setChecked(true);
//                    }
//                }
//            });

            if(item.isChecked()){
                makeAccountPublic();
                item.setChecked(false);
                checkBox.setChecked(false);
            } else {
                makeAccountPrivate();
                item.setChecked(true);
                checkBox.setChecked(true);
            }



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void makeAccountPublic() {
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

        dr.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                String myname = dataSnapshot.getValue().toString();

                Firebase ref1 = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase userRef1 = ref1.child("PublicProfiles");
                Map<String,Object> uinfo1 = new HashMap<String, Object>();
                uinfo1.put(ID,myname);
                userRef1.updateChildren(uinfo1);

                final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_private).setChecked(false);
                //Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        DatabaseReference profileref = FirebaseDatabase.getInstance().getReference().child("PrivateProfiles");
        profileref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(ID)) {
                    dataSnapshot.child(ID).getRef().removeValue();
                    UserDetails.publicProfie = true;

                    final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    Menu nav_Menu = navigationView.getMenu();
                    nav_Menu.findItem(R.id.nav_private).setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void makeAccountPrivate() {
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

        dr.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                String myname = dataSnapshot.getValue().toString();

                Firebase ref1 = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase userRef1 = ref1.child("PrivateProfiles");
                Map<String,Object> uinfo1 = new HashMap<String, Object>();
                uinfo1.put(ID,myname);
                userRef1.updateChildren(uinfo1);
                //Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        DatabaseReference profileref = FirebaseDatabase.getInstance().getReference().child("PublicProfiles");
        profileref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(ID)) {
                    dataSnapshot.child(ID).getRef().removeValue();
                    UserDetails.publicProfie = false;

                    final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    Menu nav_Menu = navigationView.getMenu();
                    nav_Menu.findItem(R.id.nav_private).setChecked(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //hide keyboard method
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    //play selected song
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
        if (!recents.contains(song)) {
            Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("RecentlyPlayed").child(ID);
            likedRef.push().setValue(song);
        }
    }

    //stopmusic
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
            playFromPause(length);
            Button btn = (Button) this.findViewById(R.id.button);
            btn.setBackgroundResource(R.drawable.ic_media_pause);
        }
        mediaPlayer.getCurrentPosition();
    }

    private void getReceiver(final String song, final String user, final String comment) {
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(user).child("Email");
        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.receiver = dataSnapshot.getValue().toString();
                sendCommentNotification(song, user, comment);
                Toast.makeText(SettingsActivity.this, "Comment to " + user + " was sent.", Toast.LENGTH_SHORT).show();

                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                //Toast.makeText(RequestActivity.this, receiver, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendCommentNotification(final String songToJoin, String user, final String comment) {

        DatabaseReference dbC = FirebaseDatabase.getInstance().getReference().child("Emails").child(user).child("Email");
        dbC.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.receiver = dataSnapshot.getValue().toString();
                //Toast.makeText(RequestActivity.this, receiver, Toast.LENGTH_SHORT).show();
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
                    //notificationBuilder.setSmallIcon(R.drawable.ic_aphla_logo);

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;

                    //This is a Simple Logic to Send Notification different Device Programmatically....
                    if (SettingsActivity.loggedEmail.equals(sender)) {
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

//                        String strJsonBody = "{'contents': {'en': 'The notification message or body'}," +
//                                "'app_id': ['99ce9cc9-d20d-4e6b-ba9b-de2e95d3ec00']'}" ;
                        //"'headings': {'en': 'Notification Title'}, " +
                        //"'big_picture': 'http://i.imgur.com/DKw1J2F.gif'}";

                        String strJsonBody = "{"
                                + "\"app_id\": \"99ce9cc9-d20d-4e6b-ba9b-de2e95d3ec00\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"" + UserDetails.fullname + " commented on '" + songToJoin + "': "+ comment +" \"},"
                                + "\"buttons\":[{\"id\": \"comment\", \"text\": \"\"}]"
                                //+ "\"small_picture\": {\"@android:drawable/buttonorg.png\"}"
                                + "}";

                        System.out.println("strJsonBody:\n" + strJsonBody);

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

    //erase from homepage when song stops
    public void eraseFromFirebase() {
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Homepage");
        mDatabase1.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String v;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            v = snapshot.getKey();
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
            if(!checkBox.isChecked()) {
                uinfo.put("Song", mysong);
                if (!UserDetails.picturelink.equals("")) {
                    uinfo.put("Picture", UserDetails.picturelink);
                } else {
                    uinfo.put("Picture", "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/ProfilePictures%2Fdefault-user.png?alt=media&token=98996406-225b-4572-a494-b6306ce9a288");
                }
                ref4.child(UserDetails.fullname).updateChildren(uinfo);

            }
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
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(UserDetails.song);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
            mediaPlayer.seekTo(time + 1300);
        } catch (IOException e) {
            e.printStackTrace();
            //   updateProgressBar();
        }
        Button btn = (Button) this.findViewById(R.id.button);
        btn.setBackgroundResource(R.drawable.ic_media_pause);
        mediaPlayer.start();

        ////

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

    public void addToPlaylist(View v) {
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
                                    sendTimeRequest(friend, song);
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

                } else if (result.action.actionID.equals("shareSong")) {
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

                } else if (result.action.actionID.equals("comment")) {
                    Log.i("OneSignalExample", "button id called: " + result.action.actionID);
                }
                else if (result.action.actionID.equals("reply")) {
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
                                    intent.putExtra("Uniqid", "NotificationReply");
                                    UserDetails.chatWith = friend;
                                    intent.putExtra("Friend", friend);
                                    //intent.putExtra("Song", song);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            String[] filepathColumm = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(filePath, filepathColumm, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filepathColumm[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//            Picasso.with(this)
//                    .load("gs://tunein-633e5.appspot.com/ProfilePictures/iFisFj7zKLeuzqsg1vBMxf6gWst2_image.jpg")
//                    .into(profilePic);


            if (filePath != null) {
                pd.show();

                final StorageReference childRef = picRef.child(ID + ".jpg");

                //uploading the image
                UploadTask uploadTask = childRef.putFile(filePath);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(SettingsActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();

                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        UserDetails.picturelink = downloadUrl.toString();
                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase picRef = ref.child("ProfilePictures").child(ID);
                        Map<String,Object> info = new HashMap<String, Object>();
                        info.put("Url", downloadUrl.toString());
                        picRef.updateChildren(info);

                        Picasso.with(SettingsActivity.this)
                                .load(downloadUrl.toString())
//                .resize(350, 240)
//                .centerInside()

                                .fit()
                                //.centerCrop()
                                .into(profilePic);



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(SettingsActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(SettingsActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
            }
        }
//          //  profilePic.setImageBitmap(bitmap);
            //pictureFlag = 1;
//
//
//
//            try {
//                //getting image from gallery
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//               // Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//
//                Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,  600 ,600, true);//this bitmap2 you can use only for display
//
//                //Setting image to ImageView
//                profilePic.setImageBitmap(bitmap2);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }







