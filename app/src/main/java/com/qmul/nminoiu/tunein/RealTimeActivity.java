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
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;


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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

public class RealTimeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MaterialSearchView searchView;
    private LinearLayout searchLayout;
    private TextView emailTextView;
    private FirebaseAuth firebaseAuth;
    private String username = "";
    private static final String TAG = "MainActivity";
    private ArrayList<String> songs = new ArrayList<>();
    private ArrayList<String> users = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> times1 = new ArrayList<>();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> pictures = new ArrayList<>();
    private ArrayList<String> names1 = new ArrayList<>();
    private ArrayList<String> titles1 = new ArrayList<>();
    private ArrayList<String> pictures1 = new ArrayList<>();
    private LinearLayout[] ll;
    private LinearLayout[] ll1;
    private ImageButton[] syncButtonArray;
    private ImageButton[] tuneoutButtonArray;
    private ImageButton[] tuneinButtonArray;
    private ImageButton[] youtubeArray;
    private ImageButton[] youtubeArray1;
    private ImageButton[] dwnArray;
    private ImageButton[] commentArray;
    private ImageButton[] addArray;
    private ImageButton[] blackHeartArray;
    private ImageButton[] redHeartArray;
    private TextView[] namesArray;
    private TextView[] namesArray1;
    private TextView[] timesArray;
    private TextView[] timesArray1;
    private TextView[] titlesArray;
    private TextView[] titlesArray1;
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
    private DatabaseReference factref;
    private DatabaseReference timeref;
    private Button uploadImg;
    private ImageView profilePic;
    private ImageView friendPic1;
    private ImageView friendPic2;
    private ImageView friendPic3;
    private ImageView friendPic4;
    private ImageView friendPic5;
    private ImageView friendPic6;
    private ImageView friendPic7;
    private ImageView friendPic8;
    private ImageView friendPic9;
    private ImageView friendPic10;
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
    public LinearLayout np6;
    public LinearLayout np7;
    public LinearLayout np8;
    public LinearLayout np9;
    public LinearLayout np10;
    public LinearLayout nowPlayingLayout;
    public LinearLayout recPlayedLayout;
    private ProgressDialog progressDialog;
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
    public TextView title6;
    public TextView title7;
    public TextView title8;
    public TextView title9;
    public TextView title10;
    public ImageButton bheart1;
    public ImageButton bheart2;
    public ImageButton bheart3;
    public ImageButton bheart4;
    public ImageButton bheart5;
    public ImageButton bheart6;
    public ImageButton bheart7;
    public ImageButton bheart8;
    public ImageButton bheart9;
    public ImageButton bheart10;
    public ImageButton rheart1;
    public ImageButton rheart2;
    public ImageButton rheart3;
    public ImageButton rheart4;
    public ImageButton rheart5;
    public ImageButton rheart6;
    public ImageButton rheart7;
    public ImageButton rheart8;
    public ImageButton rheart9;
    public ImageButton rheart10;
    public ImageButton add1;
    public ImageButton add2;
    public ImageButton add3;
    public ImageButton add4;
    public ImageButton add5;
    public ImageButton add6;
    public ImageButton add7;
    public ImageButton add8;
    public ImageButton add9;
    public ImageButton add10;
    public ImageButton comment1;
    public ImageButton comment2;
    public ImageButton comment3;
    public ImageButton comment4;
    public ImageButton comment5;
    public ImageButton comment6;
    public ImageButton comment7;
    public ImageButton comment8;
    public ImageButton comment9;
    public ImageButton comment10;
    public ImageButton download1;
    public ImageButton download2;
    public ImageButton download3;
    public ImageButton download4;
    public ImageButton download5;
    public ImageButton download6;
    public ImageButton download7;
    public ImageButton download8;
    public ImageButton download9;
    public ImageButton download10;
    private int newtime;
    public TextView name1;
    public TextView name2;
    public TextView name3;
    public TextView name4;
    public TextView name5;
    public TextView name6;
    public TextView name7;
    public TextView name8;
    public TextView name9;
    public TextView name10;
    public TextView time1;
    public TextView time2;
    public TextView time3;
    public TextView time4;
    public TextView time5;
    public TextView time6;
    public TextView time7;
    public TextView time8;
    public TextView time9;
    public TextView time10;
    public LinearLayout sl1;
    public LinearLayout sl2;
    public LinearLayout sl3;
    public LinearLayout sl4;
    public LinearLayout sl5;
    public LinearLayout sl6;
    public LinearLayout sl7;
    public LinearLayout sl8;
    public LinearLayout sl9;
    public LinearLayout sl10;
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
    public static CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);

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
        np6 = (LinearLayout) findViewById(R.id.np6);
        np7 = (LinearLayout) findViewById(R.id.np7);
        np8 = (LinearLayout) findViewById(R.id.np8);
        np9 = (LinearLayout) findViewById(R.id.np9);
        np10 = (LinearLayout) findViewById(R.id.np10);
        makePrivate = (LinearLayout) findViewById(R.id.makePrivate);
        makePublic = (LinearLayout) findViewById(R.id.makePublic);
        mainL = (CoordinatorLayout) findViewById(R.id.cLayout);
        uploadImg = (Button) findViewById(R.id.uploadImg);
        yesPublic = (Button) findViewById(R.id.yesPublic);
        cancelPublic = (Button) findViewById(R.id.cancelPublic);
        yesPrivate = (Button) findViewById(R.id.yesPrivate);
        cancelPrivate = (Button) findViewById(R.id.cancelPrivate);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        picRef = storage.getReferenceFromUrl("gs://tunein-633e5.appspot.com/ProfilePictures");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TuneIn");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        Menu nav_Menu = navigationView.getMenu();
        ll = new LinearLayout[5];
        ll[0] = np1;
        ll[1] = np2;
        ll[2] = np3;
        ll[3] = np4;
        ll[4] = np5;
        ll1 = new LinearLayout[5];
        ll1[0] = np6;
        ll1[1] = np7;
        ll1[2] = np8;
        ll1[3] = np9;
        ll1[4] = np10;
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
        Toast.makeText(this, loggedEmail, Toast.LENGTH_SHORT).show();
        mStorage = FirebaseStorage.getInstance();
        btn = (Button) findViewById(R.id.button);
        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        track_title = (TextView) findViewById(R.id.track_title);
        recents = new ArrayList<>();
        progressDialog = new ProgressDialog(this);

        //handle menu box checked/unchecked
        ScrollView01 = (ScrollView) findViewById(R.id.ScrollView01);
        checkBox = (CheckBox) nav_Menu.findItem(R.id.nav_private).getActionView();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    makeAccountPrivate();
                } else {
                    makeAccountPublic();
                }
            }

        });

        View hView = navigationView.getHeaderView(0);
        profilePic = (ImageView) hView.findViewById(R.id.profilePic);

        //set email text as user email
        TextView nav_user = (TextView) hView.findViewById(R.id.emailProfile);
        nav_user.setText(loggedEmail);

        //get extra from intent
        Intent i = getIntent();
        if (i.hasExtra("Email")) {
            nav_user.setText(getIntent().getExtras().getString("Email"));
        }
        toolbar.setTitle("Friend Activity");

        //set margins for floating buttons
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
                if (uniqid.equals("FromLibrary") || uniqid.equals("FromConversations")) {
                    if (mediaPlayer.isPlaying()) {
                        String songFromLib = intent.getStringExtra("Song");
                        play_toolbar.setVisibility(View.VISIBLE);
                        track_title.setText(songFromLib);
                        btn.setBackgroundResource(R.drawable.ic_media_pause);
                        paramsFab1.setMargins(0, 0, 43, 150);
                        fab1.setLayoutParams(paramsFab1);
                        paramsFab.setMargins(53, 0, 0, 160);
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
                        paramsFab1.setMargins(0, 0, 43, 150);
                        fab1.setLayoutParams(paramsFab1);
                        paramsFab.setMargins(53, 0, 0, 160);
                        fab.setLayoutParams(paramsFab);
                    } else {
                        play_toolbar.setVisibility(View.GONE);
                    }
                } else if (uniqid.equals("FromPlayer")) {
                    if (mediaPlayer.isPlaying()) {
                        String songFromLib = intent.getStringExtra("Song");
                        fab1.setVisibility(View.VISIBLE);
                        fab.setVisibility(View.VISIBLE);
                        fab1.bringToFront();
                        play_toolbar.setVisibility(View.VISIBLE);
                        track_title.setText(songFromLib);
                        btn.setBackgroundResource(R.drawable.ic_media_pause);
                        paramsFab1.setMargins(0, 0, 43, 150);
                        fab1.setLayoutParams(paramsFab1);
                        paramsFab.setMargins(53, 0, 0, 160);
                        fab.setLayoutParams(paramsFab);
                    } else {
                        play_toolbar.setVisibility(View.GONE);
                    }
                }
            } else if (intent.hasExtra("Song")) {
                String songFromLib = intent.getStringExtra("Song");
                fab1.bringToFront();
                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(songFromLib);
                btn.setBackgroundResource(R.drawable.ic_media_pause);
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
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
        View v6 = findViewById(R.id.np6);
        ImageButton youtube6 = (ImageButton) v6.findViewById(R.id.youtube);
        View v7 = findViewById(R.id.np7);
        ImageButton youtube7 = (ImageButton) v7.findViewById(R.id.youtube);
        View v8 = findViewById(R.id.np8);
        ImageButton youtube8 = (ImageButton) v8.findViewById(R.id.youtube);
        View v9 = findViewById(R.id.np9);
        ImageButton youtube9 = (ImageButton) v9.findViewById(R.id.youtube);
        View v10 = findViewById(R.id.np10);
        ImageButton youtube10 = (ImageButton) v10.findViewById(R.id.youtube);

        //now playing layout - initialising sublayouts
        View sp1 = findViewById(R.id.np1);
        sl1 = (LinearLayout) sp1.findViewById(R.id.nowPlaying3);
        View sp2 = findViewById(R.id.np2);
        sl2 = (LinearLayout) sp2.findViewById(R.id.nowPlaying3);
        View sp3 = findViewById(R.id.np3);
        sl3 = (LinearLayout) sp3.findViewById(R.id.nowPlaying3);
        View sp4 = findViewById(R.id.np4);
        sl4 = (LinearLayout) sp4.findViewById(R.id.nowPlaying3);
        View sp5 = findViewById(R.id.np5);
        sl5 = (LinearLayout) sp5.findViewById(R.id.nowPlaying3);
        View sp6 = findViewById(R.id.np6);
        sl6 = (LinearLayout) sp6.findViewById(R.id.nowPlaying3);
        View sp7 = findViewById(R.id.np7);
        sl7 = (LinearLayout) sp7.findViewById(R.id.nowPlaying3);
        View sp8 = findViewById(R.id.np8);
        sl8 = (LinearLayout) sp8.findViewById(R.id.nowPlaying3);
        View sp9 = findViewById(R.id.np9);
        sl9 = (LinearLayout) sp9.findViewById(R.id.nowPlaying3);
        View sp10 = findViewById(R.id.np10);
        sl10 = (LinearLayout) sp10.findViewById(R.id.nowPlaying3);

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
        View a6 = findViewById(R.id.np6);
        download6 = (ImageButton) a6.findViewById(R.id.download);
        View a7 = findViewById(R.id.np7);
        download7 = (ImageButton) a7.findViewById(R.id.download);
        View a8 = findViewById(R.id.np8);
        download8 = (ImageButton) a8.findViewById(R.id.download);
        View a9 = findViewById(R.id.np9);
        download9 = (ImageButton) a9.findViewById(R.id.download);
        View a10 = findViewById(R.id.np10);
        download10 = (ImageButton) a10.findViewById(R.id.download);

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
        View c6 = findViewById(R.id.np6);
        comment6 = (ImageButton) c6.findViewById(R.id.comment);
        View c7 = findViewById(R.id.np7);
        comment7 = (ImageButton) c7.findViewById(R.id.comment);
        View c8 = findViewById(R.id.np8);
        comment8 = (ImageButton) c8.findViewById(R.id.comment);
        View c9 = findViewById(R.id.np9);
        comment9 = (ImageButton) c9.findViewById(R.id.comment);
        View c10 = findViewById(R.id.np10);
        comment10 = (ImageButton) c10.findViewById(R.id.comment);

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
        View ad6 = findViewById(R.id.np6);
        add6 = (ImageButton) ad6.findViewById(R.id.add);
        View ad7 = findViewById(R.id.np7);
        add7 = (ImageButton) ad7.findViewById(R.id.add);
        View ad8 = findViewById(R.id.np8);
        add8 = (ImageButton) ad8.findViewById(R.id.add);
        View ad9 = findViewById(R.id.np9);
        add9 = (ImageButton) ad9.findViewById(R.id.add);
        View ad10 = findViewById(R.id.np10);
        add10 = (ImageButton) ad10.findViewById(R.id.add);

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
        View b6 = findViewById(R.id.np6);
        bheart6 = (ImageButton) b6.findViewById(R.id.blackHeart);
        View b7 = findViewById(R.id.np7);
        bheart7 = (ImageButton) b7.findViewById(R.id.blackHeart);
        View b8 = findViewById(R.id.np8);
        bheart8 = (ImageButton) b8.findViewById(R.id.blackHeart);
        View b9 = findViewById(R.id.np9);
        bheart9 = (ImageButton) b9.findViewById(R.id.blackHeart);
        View b10 = findViewById(R.id.np10);
        bheart10 = (ImageButton) b10.findViewById(R.id.blackHeart);

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
        View r6 = findViewById(R.id.np6);
        rheart6 = (ImageButton) r6.findViewById(R.id.redHeart);
        View r7 = findViewById(R.id.np7);
        rheart7 = (ImageButton) r7.findViewById(R.id.redHeart);
        View r8 = findViewById(R.id.np8);
        rheart8 = (ImageButton) r8.findViewById(R.id.redHeart);
        View r9 = findViewById(R.id.np9);
        rheart9 = (ImageButton) r9.findViewById(R.id.redHeart);
        View r10 = findViewById(R.id.np10);
        rheart10 = (ImageButton) r10.findViewById(R.id.redHeart);

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
        View n6 = findViewById(R.id.np6);
        name6 = (TextView) n6.findViewById(R.id.name);
        View n7 = findViewById(R.id.np7);
        name7 = (TextView) n7.findViewById(R.id.name);
        View n8 = findViewById(R.id.np8);
        name8 = (TextView) n8.findViewById(R.id.name);
        View n9 = findViewById(R.id.np9);
        name9 = (TextView) n9.findViewById(R.id.name);
        View n10 = findViewById(R.id.np10);
        name10 = (TextView) n10.findViewById(R.id.name);

        //now playing layout - initialising time textviews
        View tim1 = findViewById(R.id.np1);
        time1 = (TextView) tim1.findViewById(R.id.name);
        View tim2 = findViewById(R.id.np2);
        time2 = (TextView) tim2.findViewById(R.id.name);
        View tim3 = findViewById(R.id.np3);
        time3 = (TextView) tim3.findViewById(R.id.name);
        View tim4 = findViewById(R.id.np4);
        time4 = (TextView) tim4.findViewById(R.id.name);
        View tim5 = findViewById(R.id.np5);
        time5 = (TextView) tim5.findViewById(R.id.name);
        View tim6 = findViewById(R.id.np6);
        time6 = (TextView) tim6.findViewById(R.id.name);
        View tim7 = findViewById(R.id.np7);
        time7 = (TextView) tim7.findViewById(R.id.name);
        View tim8 = findViewById(R.id.np8);
        time8 = (TextView) tim8.findViewById(R.id.name);
        View tim9 = findViewById(R.id.np9);
        time9 = (TextView) tim9.findViewById(R.id.name);
        View tim10 = findViewById(R.id.np10);
        time10 = (TextView) tim10.findViewById(R.id.name);

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
        View t6 = findViewById(R.id.np6);
        title6 = (TextView) t6.findViewById(R.id.songName);
        View t7 = findViewById(R.id.np7);
        title7 = (TextView) t7.findViewById(R.id.songName);
        View t8 = findViewById(R.id.np8);
        title8 = (TextView) t8.findViewById(R.id.songName);
        View t9 = findViewById(R.id.np9);
        title9 = (TextView) t9.findViewById(R.id.songName);
        View t10 = findViewById(R.id.np10);
        title10 = (TextView) t10.findViewById(R.id.songName);

        //now playing layout - initialising picture image view
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
        View i6 = findViewById(R.id.np6);
        friendPic6 = (ImageView) i6.findViewById(R.id.friendPic);
        View i7 = findViewById(R.id.np7);
        friendPic7 = (ImageView) i7.findViewById(R.id.friendPic);
        View i8 = findViewById(R.id.np8);
        friendPic8 = (ImageView) i8.findViewById(R.id.friendPic);
        View i9 = findViewById(R.id.np9);
        friendPic9 = (ImageView) i9.findViewById(R.id.friendPic);
        View i10 = findViewById(R.id.np10);
        friendPic10 = (ImageView) i10.findViewById(R.id.friendPic);
        Firebase.setAndroidContext(this);

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
        youtubeArray1 = new ImageButton[5];
        youtubeArray1[0] = youtube6;
        youtubeArray1[1] = youtube7;
        youtubeArray1[2] = youtube8;
        youtubeArray1[3] = youtube9;
        youtubeArray1[4] = youtube10;

        titlesArray = new TextView[5];
        titlesArray[0] = title1;
        titlesArray[1] = title2;
        titlesArray[2] = title3;
        titlesArray[3] = title4;
        titlesArray[4] = title5;
        titlesArray1 = new TextView[5];
        titlesArray1[0] = title6;
        titlesArray1[1] = title7;
        titlesArray1[2] = title8;
        titlesArray1[3] = title9;
        titlesArray1[4] = title10;

        namesArray = new TextView[5];
        namesArray[0] = name1;
        namesArray[1] = name2;
        namesArray[2] = name3;
        namesArray[3] = name4;
        namesArray[4] = name5;
        namesArray1 = new TextView[5];
        namesArray1[0] = name6;
        namesArray1[1] = name7;
        namesArray1[2] = name8;
        namesArray1[3] = name9;
        namesArray1[4] = name10;

        timesArray = new TextView[5];
        timesArray[0] = time1;
        timesArray[1] = time2;
        timesArray[2] = time3;
        timesArray[3] = time4;
        timesArray[4] = time5;
        timesArray1 = new TextView[5];
        timesArray[0] = time6;
        timesArray[1] = time7;
        timesArray[2] = time8;
        timesArray[3] = time9;
        timesArray[4] = time10;
        MusicPlayerActivity.songs.clear();
        MusicPlayerActivity.urls.clear();

        //retrieve recent songs
        DatabaseReference rec = FirebaseDatabase.getInstance().getReference().child("RecentlyPlayed").child(ID);
        rec.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String recentSongs = dataSnapshot.getValue(String.class);
                getUrl(recentSongs);
                MusicPlayerActivity.songs.add(0, recentSongs);
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

        //retrieve current song
        DatabaseReference songTitleRef = FirebaseDatabase.getInstance().getReference().child("CurrentSong");
        songTitleRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(ID).exists()) {
                    String song = dataSnapshot.child(ID).child("Song").getValue().toString();
                    track_title.setText(song);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //handling click on song name
        title1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title1.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);

                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);
                hideSoftKeyboard(RealTimeActivity.this);
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

        title2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title2.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);
                hideSoftKeyboard(RealTimeActivity.this);
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

        title3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title3.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);
                hideSoftKeyboard(RealTimeActivity.this);
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


        title4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title4.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
//                SongSingleton.getInstance().setSongName(song);

                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                hideSoftKeyboard(RealTimeActivity.this);
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


        title5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title5.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);
                hideSoftKeyboard(RealTimeActivity.this);
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


        title6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title6.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);

                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                hideSoftKeyboard(RealTimeActivity.this);
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


        title7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title7.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);

                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                hideSoftKeyboard(RealTimeActivity.this);
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


        title8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title8.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);

                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                hideSoftKeyboard(RealTimeActivity.this);
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


        title9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title9.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);

                hideSoftKeyboard(RealTimeActivity.this);
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


        title10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title10.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);
                hideSoftKeyboard(RealTimeActivity.this);
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

        //handle click on sublayouts on homepage
        sl1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title1.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);
                hideSoftKeyboard(RealTimeActivity.this);
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

        sl2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title2.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);
                hideSoftKeyboard(RealTimeActivity.this);
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

        sl3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title3.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                hideSoftKeyboard(RealTimeActivity.this);
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

        sl4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title4.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                hideSoftKeyboard(RealTimeActivity.this);
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

        sl5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title5.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                hideSoftKeyboard(RealTimeActivity.this);
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

        sl6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title6.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                hideSoftKeyboard(RealTimeActivity.this);
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

        sl7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title7.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                hideSoftKeyboard(RealTimeActivity.this);
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

        sl8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title8.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                hideSoftKeyboard(RealTimeActivity.this);
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

        sl9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title9.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                hideSoftKeyboard(RealTimeActivity.this);
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

        sl10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String song = title10.getText().toString();
                paramsFab1.setMargins(0, 0, 43, 150);
                fab1.setLayoutParams(paramsFab1);
                paramsFab.setMargins(53, 0, 0, 160);
                fab.setLayoutParams(paramsFab);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);

                hideSoftKeyboard(RealTimeActivity.this);
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

        //handle click on friend names and go to their profile page
        name1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name1.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        name2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name2.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        name3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name3.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        name4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name4.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        name5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name5.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        name6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name6.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        name7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name7.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        name8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name8.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        name9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name9.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        name10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name10.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        //handling click on friend profile picture
        friendPic1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name1.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
                deleteRequest();
            }
        });

        friendPic2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name2.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
                deleteRequest();

            }
        });

        friendPic3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name3.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
                deleteRequest();

            }
        });

        friendPic4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name4.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
                deleteRequest();

            }
        });

        friendPic5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name5.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
                deleteRequest();

            }
        });

        friendPic6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name6.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
                deleteRequest();

            }
        });

        friendPic7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name7.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
                deleteRequest();

            }
        });

        friendPic8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name8.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
                deleteRequest();

            }
        });

        friendPic9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name9.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
                deleteRequest();

            }
        });

        friendPic10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = name10.getText().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(RealTimeActivity.this, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                startActivity(intent);
                deleteRequest();

            }
        });


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
                } else {
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

        //handle click on public
        yesPublic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserDetails.fullname = dataSnapshot.getValue().toString();
                        String myname = dataSnapshot.getValue().toString();
                        Firebase ref1 = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase userRef1 = ref1.child("PublicProfiles");
                        Map<String, Object> uinfo1 = new HashMap<String, Object>();
                        uinfo1.put(ID, myname);
                        userRef1.updateChildren(uinfo1);
                        Menu nav_Menu = navigationView.getMenu();
                        nav_Menu.findItem(R.id.nav_private).setVisible(false);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

                makePublic.setVisibility(View.GONE);
            }
        });

        //handle click on private profile
        yesPrivate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserDetails.fullname = dataSnapshot.getValue().toString();
                        String myname = dataSnapshot.getValue().toString();

                        Firebase ref1 = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase userRef1 = ref1.child("PrivateProfiles");
                        Map<String, Object> uinfo1 = new HashMap<String, Object>();
                        uinfo1.put(ID, myname);
                        userRef1.updateChildren(uinfo1);
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

        //handle clikc on scroll view to hide comments layout
        ScrollView01.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });

        //handle click on now playing layout to hide comments layout
        nowPlayingLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });

        //handle click on the main layout
        mainL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
            }
        });


        //handle click on the synchronisation buttons for the Now Playing layout
        tunein1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RealTimeActivity.this, "Synchronising...", Toast.LENGTH_SHORT).show();
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
                play_toolbar.setVisibility(View.GONE);

                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", title1.getText().toString());
                refsong.updateChildren(uinfo);
            }
        });

        tunein2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RealTimeActivity.this, "Synchronising...", Toast.LENGTH_SHORT).show();

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
                play_toolbar.setVisibility(View.GONE);

            }
        });

        tunein3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RealTimeActivity.this, "Synchronising...", Toast.LENGTH_SHORT).show();

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
                play_toolbar.setVisibility(View.GONE);

            }
        });

        tunein4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RealTimeActivity.this, "Synchronising...", Toast.LENGTH_SHORT).show();

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
                play_toolbar.setVisibility(View.GONE);

            }
        });

        tunein5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RealTimeActivity.this, "Synchronising...", Toast.LENGTH_SHORT).show();

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
                play_toolbar.setVisibility(View.GONE);

            }
        });

        //load rpofile picture
        Firebase picture = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase picture1 = picture.child("ProfilePictures");
        picture1.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(ID)) {
                    String link = dataSnapshot.child(ID).child("Url").getValue().toString();
                    UserDetails.picturelink = link;

                    Picasso.with(RealTimeActivity.this)
                            .load(link)
                            .fit()
                            .into(profilePic);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //tune out buttons for Now Playing layout
        tuneout1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tuneout1.setVisibility(View.GONE);
                tunein1.setVisibility(View.VISIBLE);
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                mediaPlayer.pause();

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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        tuneout2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tuneout2.setVisibility(View.GONE);
                tunein2.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        tuneout3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tuneout3.setVisibility(View.GONE);
                tunein3.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        tuneout4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tuneout4.setVisibility(View.GONE);
                tunein4.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        tuneout5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tuneout5.setVisibility(View.GONE);
                tunein5.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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
        youtube1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title1.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");

                if (mediaPlayer.isPlaying()) {
                    pause();
                }

                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String link = dataSnapshot.getValue().toString();
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(link));
                        startActivity(viewIntent);
                        deleteRequest();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        youtube2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title2.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");

                if (mediaPlayer.isPlaying()) {
                    pause();
                }

                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String link = dataSnapshot.getValue().toString();
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(link));
                        startActivity(viewIntent);
                        deleteRequest();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        youtube3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title3.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");

                if (mediaPlayer.isPlaying()) {
                    pause();
                }

                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String link = dataSnapshot.getValue().toString();
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(link));
                        startActivity(viewIntent);
                        deleteRequest();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });


        youtube4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title4.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");

                if (mediaPlayer.isPlaying()) {
                    pause();
                }

                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String link = dataSnapshot.getValue().toString();
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(link));
                        startActivity(viewIntent);
                        deleteRequest();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        youtube5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title5.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");

                if (mediaPlayer.isPlaying()) {
                    pause();
                }

                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String link = dataSnapshot.getValue().toString();
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(link));
                        startActivity(viewIntent);
                        deleteRequest();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        youtube6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title6.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");

                if (mediaPlayer.isPlaying()) {
                    pause();
                }

                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String link = dataSnapshot.getValue().toString();
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(link));
                        startActivity(viewIntent);
                        deleteRequest();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        youtube7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title7.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");

                if (mediaPlayer.isPlaying()) {
                    pause();
                }

                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String link = dataSnapshot.getValue().toString();
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(link));
                        startActivity(viewIntent);
                        deleteRequest();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        youtube8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title8.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");

                if (mediaPlayer.isPlaying()) {
                    pause();
                }

                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String link = dataSnapshot.getValue().toString();
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(link));
                        startActivity(viewIntent);
                        deleteRequest();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        youtube9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title9.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");

                if (mediaPlayer.isPlaying()) {
                    pause();
                }

                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String link = dataSnapshot.getValue().toString();
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(link));
                        startActivity(viewIntent);
                        deleteRequest();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });

        youtube10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String songToView = title10.getText().toString();
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase videoRef = ref.child("Youtube").child(songToView).child("Link");

                if (mediaPlayer.isPlaying()) {
                    pause();
                }

                videoRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                    @Override
                    public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                        String link = dataSnapshot.getValue().toString();
                        Intent viewIntent =
                                new Intent("android.intent.action.VIEW",
                                        Uri.parse(link));
                        startActivity(viewIntent);
                        deleteRequest();

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });


        //listeners for download buttons - NowPlaying Layout
        download1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();

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

        download2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();

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

        download3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();

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

        download4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();

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

        download5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();

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

        download6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();

                final String songToDown = title6.getText().toString();
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

        download7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();

                final String songToDown = title7.getText().toString();
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

        download8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();

                final String songToDown = title8.getText().toString();
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

        download9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();

                final String songToDown = title9.getText().toString();
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

        download10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();

                final String songToDown = title10.getText().toString();
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

        //listeners for comment buttons - NowPlaying Layout
        comment1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                play_toolbar.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name1.getText().toString();
                UserDetails.commentOn = title1.getText().toString();

            }
        });

        comment2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                play_toolbar.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name2.getText().toString();
                UserDetails.commentOn = title2.getText().toString();
            }
        });

        comment3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                play_toolbar.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name3.getText().toString();
                UserDetails.commentOn = title3.getText().toString();
            }
        });

        comment4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                play_toolbar.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name4.getText().toString();
                UserDetails.commentOn = title4.getText().toString();
            }
        });

        comment5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                play_toolbar.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name5.getText().toString();
                UserDetails.commentOn = title5.getText().toString();
            }
        });

        comment6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                play_toolbar.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name6.getText().toString();
                UserDetails.commentOn = title6.getText().toString();
            }
        });

        comment7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                play_toolbar.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name7.getText().toString();
                UserDetails.commentOn = title7.getText().toString();
            }
        });

        comment8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                play_toolbar.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name8.getText().toString();
                UserDetails.commentOn = title8.getText().toString();
            }
        });

        comment9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                play_toolbar.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name9.getText().toString();
                UserDetails.commentOn = title9.getText().toString();
            }
        });

        comment10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                play_toolbar.setVisibility(View.GONE);
                commentarea.setText("");
                commentsLayout.setVisibility(View.VISIBLE);
                UserDetails.commentTo = name10.getText().toString();
                UserDetails.commentOn = title10.getText().toString();
            }
        });

        //handle click on send comment
        commentButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentarea.getText().toString();

                if (!comment.isEmpty()) {
                    getReceiver(UserDetails.commentOn, UserDetails.commentTo, comment);
                } else {
                    Toast.makeText(RealTimeActivity.this, "Please write a comment", Toast.LENGTH_SHORT).show();
                }
                if (mediaPlayer.isPlaying()) {
                    play_toolbar.setVisibility(View.VISIBLE);
                    btn.setBackgroundResource(R.drawable.ic_media_pause);
                    paramsFab1.setMargins(0, 0, 43, 150);
                    fab1.setLayoutParams(paramsFab1);
                    paramsFab.setMargins(53, 0, 0, 160);
                    fab.setLayoutParams(paramsFab);
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                commentsLayout.setVisibility(View.GONE);
                hideSoftKeyboard(RealTimeActivity.this);


            }
        });

        //listeners for add to playlist buttons - NowPlaying Layout
        add1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(RealTimeActivity.this, PlaylistsActivity.class);
                final String song = title1.getText().toString();

                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                if (!snapshot.getValue().equals(song)) {
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
                deleteRequest();

            }
        });


        add2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(RealTimeActivity.this, PlaylistsActivity.class);
                final String song = title2.getText().toString();
                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!snapshot.getValue().equals(song)) {
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
                deleteRequest();

            }
        });

        add3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(RealTimeActivity.this, PlaylistsActivity.class);
                final String song = title3.getText().toString();
                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!snapshot.getValue().equals(song)) {
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
                deleteRequest();

            }
        });

        add4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(RealTimeActivity.this, PlaylistsActivity.class);
                final String song = title4.getText().toString();
                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!snapshot.getValue().equals(song)) {
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
                deleteRequest();

            }
        });

        add5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(RealTimeActivity.this, PlaylistsActivity.class);
                final String song = title5.getText().toString();

                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!snapshot.getValue().equals(song)) {
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
                deleteRequest();

            }
        });

        add6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(RealTimeActivity.this, PlaylistsActivity.class);
                final String song = title6.getText().toString();

                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!snapshot.getValue().equals(song)) {
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
                deleteRequest();

            }
        });

        add7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(RealTimeActivity.this, PlaylistsActivity.class);
                final String song = title7.getText().toString();

                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!snapshot.getValue().equals(song)) {
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
                deleteRequest();

            }
        });

        add8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(RealTimeActivity.this, PlaylistsActivity.class);
                final String song = title8.getText().toString();

                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!snapshot.getValue().equals(song)) {
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
                deleteRequest();

            }
        });

        add9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(RealTimeActivity.this, PlaylistsActivity.class);
                final String song = title9.getText().toString();

                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!snapshot.getValue().equals(song)) {
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
                deleteRequest();

            }
        });

        add10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                commentsLayout.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.VISIBLE);
                Intent i = new Intent(RealTimeActivity.this, PlaylistsActivity.class);
                final String song = title10.getText().toString();

                DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
                songref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ID)) {
                            for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                                String key = snapshot.getKey();

                                if (!snapshot.getValue().equals(song)) {
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
                deleteRequest();

            }
        });

        //retrieve recent songs from Firebase
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
        bheart1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                String songToLike = title1.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart1.setVisibility(View.GONE);
                rheart1.setVisibility(View.VISIBLE);

            }
        });

        bheart2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                String songToLike = title2.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart2.setVisibility(View.GONE);
                rheart2.setVisibility(View.VISIBLE);

            }
        });

        bheart3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                String songToLike = title3.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart3.setVisibility(View.GONE);
                rheart3.setVisibility(View.VISIBLE);

            }
        });

        bheart4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                String songToLike = title4.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart4.setVisibility(View.GONE);
                rheart4.setVisibility(View.VISIBLE);

            }
        });

        bheart5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                String songToLike = title5.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart5.setVisibility(View.GONE);
                rheart5.setVisibility(View.VISIBLE);

            }
        });

        bheart6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                String songToLike = title6.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart6.setVisibility(View.GONE);
                rheart6.setVisibility(View.VISIBLE);

            }
        });

        bheart7.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                String songToLike = title7.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart7.setVisibility(View.GONE);
                rheart7.setVisibility(View.VISIBLE);
            }
        });

        bheart8.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                String songToLike = title8.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart8.setVisibility(View.GONE);
                rheart8.setVisibility(View.VISIBLE);

            }
        });

        bheart9.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                String songToLike = title9.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);

                bheart9.setVisibility(View.GONE);
                rheart9.setVisibility(View.VISIBLE);

            }
        });

        bheart10.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                String songToLike = title10.getText().toString();
                Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("LovedSongs").child(ID);
                likedRef.push().setValue(songToLike);
                bheart5.setVisibility(View.GONE);
                rheart5.setVisibility(View.VISIBLE);
            }
        });

        //listeners for dislike buttons - NowPlaying Layout
        rheart1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
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

        rheart2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
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

        rheart3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
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

        rheart4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
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

        rheart5.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
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

        rheart6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                final String songLiked = title6.getText().toString();
                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);
                mDatabase1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();
                                    if (dataSnapshot.child(key).getValue().equals(songLiked)) {
                                        dataSnapshot.child(key).getRef().removeValue();
                                        rheart6.setVisibility(View.INVISIBLE);
                                        bheart6.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });

        rheart7.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                final String songLiked = title7.getText().toString();
                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);
                mDatabase1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();
                                    if (dataSnapshot.child(key).getValue().equals(songLiked)) {
                                        dataSnapshot.child(key).getRef().removeValue();
                                        rheart7.setVisibility(View.INVISIBLE);
                                        bheart7.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });

        rheart8.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                final String songLiked = title8.getText().toString();
                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);
                mDatabase1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();
                                    if (dataSnapshot.child(key).getValue().equals(songLiked)) {
                                        dataSnapshot.child(key).getRef().removeValue();
                                        rheart8.setVisibility(View.INVISIBLE);
                                        bheart8.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });

        rheart9.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                final String songLiked = title9.getText().toString();
                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);
                mDatabase1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();
                                    if (dataSnapshot.child(key).getValue().equals(songLiked)) {
                                        dataSnapshot.child(key).getRef().removeValue();
                                        rheart9.setVisibility(View.INVISIBLE);
                                        bheart9.setVisibility(View.VISIBLE);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });

        rheart10.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View v) {
                commentsLayout.setVisibility(View.GONE);
                if (!mediaPlayer.isPlaying()) {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                }
                final String songLiked = title10.getText().toString();
                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);
                mDatabase1.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();
                                    if (dataSnapshot.child(key).getValue().equals(songLiked)) {
                                        dataSnapshot.child(key).getRef().removeValue();
                                        rheart10.setVisibility(View.INVISIBLE);
                                        bheart10.setVisibility(View.VISIBLE);
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
        mDatabase5.addListenerForSingleValueEvent(new ValueEventListener() {
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
                        if (mediaPlayer.isPlaying()) {
                            addTimeToFirebase(otherUser, song);
                        }
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
                        if (mediaPlayer.isPlaying()) {
                            addTimeToFirebase(otherUser, song);
                        }
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
                text = dataSnapshot.getKey();
                names.add(0, text);
                String mysong = dataSnapshot.child("Song").getValue().toString();
                title.add(0, mysong);
                String picture = dataSnapshot.child("Picture").getValue().toString();
                pictures.add(0, picture);

                for (int i = 0; i <= names.size() - 1; i++) {
                    ll[i].setVisibility(View.VISIBLE);
                    TextView name = (TextView) ll[i].findViewById(R.id.name);
                    name.setText(names.get(i));
                    TextView song = (TextView) ll[i].findViewById(R.id.songName);
                    song.setText(title.get(i));
                    ImageView pic = (ImageView) ll[i].findViewById(R.id.friendPic);
                    Picasso.with(RealTimeActivity.this)
                            .load(pictures.get(i).toString())
                            .fit()
                            .centerCrop()
                            .into(pic);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                text = dataSnapshot.getKey();
                String mysong = dataSnapshot.child("Song").getValue().toString();
                String fpic = dataSnapshot.child("Picture").getValue().toString();


                for (int i = 0; i <= names.size() - 1; i++) {
                    if (text.equals(names.get(i))) {
                        names.set(i, text);
                        title.set(i, mysong);
                        pictures.set(i, fpic);
                        tuneoutButtonArray[i].setVisibility(View.GONE);
                        syncButtonArray[i].setVisibility(View.VISIBLE);
                        TextView name = (TextView) ll[i].findViewById(R.id.name);
                        name.setText(text);
                        TextView song = (TextView) ll[i].findViewById(R.id.songName);
                        song.setText(mysong);
                        ImageView pict = (ImageView) ll[i].findViewById(R.id.friendPic);
                        Picasso.with(RealTimeActivity.this)
                                .load(fpic)
                                .fit()
                                .centerCrop()
                                .into(pict);
                    }
                }

                if (mediaPlayer.isPlaying()) {
                    Toast.makeText(RealTimeActivity.this, text + " changed songs", Toast.LENGTH_SHORT).show();
                    play_toolbar.setVisibility(View.VISIBLE);
                    btn.setBackgroundResource(R.drawable.ic_media_pause);
                    paramsFab1.setMargins(0, 0, 43, 150);
                    fab1.setLayoutParams(paramsFab1);
                    paramsFab.setMargins(53, 0, 0, 160);
                    fab.setLayoutParams(paramsFab);
                }
            }


            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                text = dataSnapshot.getKey();
                String mysong = dataSnapshot.child("Song").getValue().toString();
                String picture = dataSnapshot.child("Picture").getValue().toString();
                names.remove(text);
                title.remove(mysong);
                pictures.remove(picture);

                for (int j = 0; j <= ll.length - 1; j++) {
                    TextView name = (TextView) ll[j].findViewById(R.id.name);
                    TextView song = (TextView) ll[j].findViewById(R.id.songName);
                    ImageView pict = (ImageView) ll[j].findViewById(R.id.friendPic);

                    if (name.getText().equals(text)) {
                        ll[j].setVisibility(View.GONE);
                        tuneoutButtonArray[j].setVisibility(View.GONE);
                        syncButtonArray[j].setVisibility(View.VISIBLE);
                    }
                }

                if (mediaPlayer.isPlaying()) {
                    Toast.makeText(RealTimeActivity.this, text + " stopped listening", Toast.LENGTH_SHORT).show();
                    play_toolbar.setVisibility(View.VISIBLE);
                    btn.setBackgroundResource(R.drawable.ic_media_pause);
                    paramsFab1.setMargins(0, 0, 43, 150);
                    fab1.setLayoutParams(paramsFab1);
                    paramsFab.setMargins(53, 0, 0, 160);
                    fab.setLayoutParams(paramsFab);
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //listener for Friends Activity Firebase child
        factref = FirebaseDatabase.getInstance().getReference().child("FriendsActivity").child(ID);
        factref.orderByChild("Time").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String text = dataSnapshot.getKey();
                names1.add(0, text);
                String mysong = dataSnapshot.child("Song").getValue().toString();
                titles1.add(0, mysong);
                String picture = dataSnapshot.child("Picture").getValue().toString();
                pictures1.add(0, picture);
                String time = dataSnapshot.child("Time").getValue().toString();
                times1.add(0, getTime(time));


                for (int i = 0; i <= names1.size() - 1; i++) {
                    ll1[i].setVisibility(View.VISIBLE);
                    TextView name = (TextView) ll1[i].findViewById(R.id.name);
                    name.setText(names1.get(i));
                    TextView song = (TextView) ll1[i].findViewById(R.id.songName);
                    song.setText(titles1.get(i));
                    TextView timer = (TextView) ll1[i].findViewById(R.id.time);
                    timer.setText(times1.get(i));
                    ImageView pic = (ImageView) ll1[i].findViewById(R.id.friendPic);
                    Picasso.with(RealTimeActivity.this)
                            .load(pictures1.get(i).toString())
                            .fit()
                            .centerCrop()
                            .into(pic);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String text = dataSnapshot.getKey();
                String mysong = dataSnapshot.child("Song").getValue().toString();
                String fpic = dataSnapshot.child("Picture").getValue().toString();
                String ctime = dataSnapshot.child("Time").getValue().toString();


                for (int i = 0; i <= names1.size() - 1; i++) {
                    if (text.equals(names1.get(i))) {
                        names1.set(i, text);
                        titles1.set(i, mysong);
                        pictures1.set(i, fpic);
                        times1.set(i, getTime(ctime));
                        TextView name = (TextView) ll1[i].findViewById(R.id.name);
                        name.setText(text);
                        TextView song = (TextView) ll1[i].findViewById(R.id.songName);
                        song.setText(mysong);
                        TextView time = (TextView) ll1[i].findViewById(R.id.time);
                        time.setText(getTime(ctime));
                        ImageView pict = (ImageView) ll1[i].findViewById(R.id.friendPic);
                        Picasso.with(RealTimeActivity.this)
                                .load(fpic)
                                .fit()
                                .centerCrop()
                                .into(pict);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getKey();
                String mysong = dataSnapshot.child("Song").getValue().toString();
                String time = dataSnapshot.child("Time").getValue().toString();
                String picture = dataSnapshot.child("Picture").getValue().toString();
                names1.remove(text);
                titles1.remove(mysong);
                pictures1.remove(picture);
                times1.remove(getTime(time));


                for (int j = 0; j <= ll1.length - 1; j++) {
                    TextView name = (TextView) ll1[j].findViewById(R.id.name);
                    TextView timer = (TextView) ll1[j].findViewById(R.id.time);
                    TextView song = (TextView) ll1[j].findViewById(R.id.songName);
                    ImageView pict = (ImageView) ll1[j].findViewById(R.id.friendPic);

                    if (name.getText().equals(text)) {
                        ll1[j].setVisibility(View.GONE);
                    }

                }
                if (mediaPlayer.isPlaying()) {
                    play_toolbar.setVisibility(View.VISIBLE);
                    track_title.setText(mysong);
                }
            }

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
                                playMusic(newtime, url);
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
                                playMusic(newtime, url);
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
                play_toolbar.requestLayout();
                hideSoftKeyboard(RealTimeActivity.this);
                UserDetails.username = user;

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
//                      Toast.makeText(PlaylistSongs.this, "my fullname " + UserDetails.fullname , Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(RealTimeActivity.this, ProfileActivity.class);
                        i.putExtra("FriendId", friendID);
                        i.putExtra("FriendName", user);
                        startActivity(i);
                        deleteRequest();
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
                Intent intent_info = new Intent(RealTimeActivity.this, MusicPlayerActivity.class);
                intent_info.putExtra("Uniqid", "FromSettings");
                if (mediaPlayer.isPlaying()) {
                    SongSingleton.getInstance().setSongName(song);
                }
                startActivity(intent_info);
                deleteRequest();
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            }
        });

        //adding click listeners for songs listview and showing clicked song in play toolbar.
        slistView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                song = ((TextView) view).getText().toString();
                String song1 = ((TextView) view).getText().toString();
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song1);
                refsong.updateChildren(uinfo);
                play_toolbar.setVisibility(View.VISIBLE);
                track_title.setText(song);
                hideSoftKeyboard(RealTimeActivity.this);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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
        slistView.setAdapter(sadapter);

        //hide keyboard when songs list is touched
        slistView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard(RealTimeActivity.this);
                return false;
            }
        });

        //chat button
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                getFulname();
                Intent i = new Intent(RealTimeActivity.this, Conversations.class);
                i.putExtra("Uniqid", "FromSettings");
                if (mediaPlayer.isPlaying()) {
                    SongSingleton.getInstance().setSongName(song);
                }
                startActivity(i);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

        //library button
        fab1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RealTimeActivity.this, LibraryActivity.class);
                i.putExtra("Uniqid", "FromSettings");
                if (mediaPlayer.isPlaying()) {
                    SongSingleton.getInstance().setSongName(song);
                }
                startActivity(i);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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
                fab.setVisibility(View.INVISIBLE);
                fab1.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSearchViewClosed() {
                nowPlayingLayout.setVisibility(View.VISIBLE);
                if (mediaPlayer.isPlaying()) {
                    paramsFab1.setMargins(0, 0, 43, 150);
                    fab1.setLayoutParams(paramsFab1);
                    paramsFab.setMargins(53, 0, 0, 160);
                    fab.setLayoutParams(paramsFab);
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                    fab1.setVisibility(View.VISIBLE);
                    paramsFab1.setMargins(0, 0, 43, 43);
                    fab1.setLayoutParams(paramsFab1);
                    paramsFab.setMargins(53, 0, 0, 53);
                    fab.setLayoutParams(paramsFab);
                }

                searchLayout.setVisibility(View.GONE);
            }
        });

        //search method
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                hideSoftKeyboard(RealTimeActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.toLowerCase() != null && !newText.toLowerCase().isEmpty()) {
                    List<String> ulistFound = new ArrayList<String>();
                    for (String item : users) {
                        if (item.toLowerCase().contains(newText.toLowerCase())) {
                            ulistFound.add(item);
                        }
                    }

                    ArrayAdapter uadapter = new ArrayAdapter(RealTimeActivity.this, android.R.layout.simple_list_item_1, ulistFound);
                    ulistView.setAdapter(uadapter);

                    List<String> slistFound = new ArrayList<String>();
                    for (String item : songs) {
                        if (item.toLowerCase().contains(newText.toLowerCase())) {
                            slistFound.add(item);
                        }
                    }

                    if (ulistFound.isEmpty()) {
                        ulistFound.add("No users found");
                    } else {
                        ptextview.setVisibility(View.VISIBLE);
                    }
                    if (slistFound.isEmpty()) {
                        slistFound.add("No songs found");
                    } else {
                        stextview.setVisibility(View.VISIBLE);
                    }

                    ArrayAdapter sadapter = new ArrayAdapter(RealTimeActivity.this, android.R.layout.simple_list_item_1, slistFound);
                    slistView.setAdapter(sadapter);
                } else {
                    //if search text is null
                    ArrayAdapter uadapter = new ArrayAdapter(RealTimeActivity.this, android.R.layout.simple_list_item_1, users);
                    ulistView.setAdapter(uadapter);
                    ArrayAdapter sadapter = new ArrayAdapter(RealTimeActivity.this, android.R.layout.simple_list_item_1, songs);
                    slistView.setAdapter(sadapter);
                }
                return true;
            }
        });
    }

    //delete time request
    private void deleteRequest() {
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
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String reqId = snapshot.getKey().toString();
                            if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

    //getting followers
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

    //setting icon for serach
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        return true;
    }

    //back button - from search layout
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            TextView nav_user = (TextView) drawer.findViewById(R.id.emailProfile);
            nav_user.setText(sender);
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //handling click on menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        TextView nav_user = (TextView) drawer.findViewById(R.id.emailProfile);
        nav_user.setText(sender);
        return super.onOptionsItemSelected(item);
    }

    //get time
    private String getTime(String time) {
        Calendar calendar = Calendar.getInstance();
        Long longTime = Long.valueOf(time);

        Date today = new Date(System.currentTimeMillis());
        Date otherDate = new Date(longTime);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat hfor = new SimpleDateFormat("HH:mm");

        String todayD = formatter.format(today);
        String otherDateD = formatter.format(otherDate);

        if (todayD.equals(otherDateD)) {
            return hfor.format(longTime);
        } else return otherDateD;
    }

    //sliding menu options
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_followers) {
            Intent i = new Intent(RealTimeActivity.this, FollowersActivity.class);
            i.putExtra("Uniqid", "FromSettingsMenu");
            UserDetails.oldIntent = "FromSettingsMenu";
            startActivity(i);
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
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String reqId = snapshot.getKey().toString();
                                if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
                                    dataSnapshot.child(reqId).getRef().removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                        }
                    });


        } else if (id == R.id.nav_following) {
            Intent i = new Intent(RealTimeActivity.this, FollowingActivity.class);
            i.putExtra("Uniqid", "FromSettingsMenu");
            UserDetails.oldIntent = "FromSettingsMenu";
            startActivity(i);
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
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String reqId = snapshot.getKey().toString();
                                if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
                                    dataSnapshot.child(reqId).getRef().removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                        }
                    });


        } else if (id == R.id.profile_pic) {
            {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");
                Intent chooserIntent = Intent.createChooser(intent, "Pick an image.");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
                startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
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
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String reqId = snapshot.getKey().toString();
                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String reqId = snapshot.getKey().toString();
                                if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
                                    dataSnapshot.child(reqId).getRef().removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                        }
                    });

            eraseFromFirebase();
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent nextActivity = new Intent(this, LoginActivity.class);
            startActivity(nextActivity);
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
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String reqId = snapshot.getKey().toString();
                                if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
                                    dataSnapshot.child(reqId).getRef().removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                        }
                    });

            eraseFromFirebase();
        } else if (id == R.id.nav_private) {
            final CheckBox checkBox = (CheckBox) item.getActionView();
            if (item.isChecked()) {
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

    /**
     * Update progress bar.
     */
    public void updateProgressBar() {
        RealTimeActivity sa = new RealTimeActivity();
        mHandler.postDelayed(sa.mUpdateTimeTask, 100);
    }

    //make account public
    private void makeAccountPublic() {
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

        dr.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                String myname = dataSnapshot.getValue().toString();

                Firebase ref1 = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase userRef1 = ref1.child("PublicProfiles");
                Map<String, Object> uinfo1 = new HashMap<String, Object>();
                uinfo1.put(ID, myname);
                userRef1.updateChildren(uinfo1);

                final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_private).setChecked(false);
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

    //set account to private
    private void makeAccountPrivate() {
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

        dr.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                String myname = dataSnapshot.getValue().toString();

                Firebase ref1 = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase userRef1 = ref1.child("PrivateProfiles");
                Map<String, Object> uinfo1 = new HashMap<String, Object>();
                uinfo1.put(ID, myname);
                userRef1.updateChildren(uinfo1);
                //Toast.makeText(RealTimeActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
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
        }
        Button btn = (Button) this.findViewById(R.id.button);
        btn.setBackgroundResource(R.drawable.ic_media_pause);
        mediaPlayer.start();
        if (!recents.contains(song)) {
            Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("RecentlyPlayed").child(ID);
            likedRef.push().setValue(song);
        }
    }

    //pause song
    public void pause() {
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

    //get receiver
    private void getReceiver(final String song, final String user, final String comment) {
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(user).child("Email");
        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.receiver = dataSnapshot.getValue().toString();
                sendCommentNotification(song, user, comment);
                Toast.makeText(RealTimeActivity.this, "Comment to " + user + " was sent.", Toast.LENGTH_SHORT).show();

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

    //send notification when commenting
    private void sendCommentNotification(final String songToJoin, String user, final String comment) {
        DatabaseReference dbC = FirebaseDatabase.getInstance().getReference().child("Emails").child(user).child("Email");
        dbC.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
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
                                + "\"contents\": {\"en\": \"" + UserDetails.fullname + " commented on '" + songToJoin + "': " + comment + " \"},"
                                + "\"buttons\":[{\"id\": \"comment\", \"text\": \"\"}]"
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
                            if (dataSnapshot.child(v).hasChild(getMyFullname(ID))) {
                                dataSnapshot.child(v).child(getMyFullname(ID)).getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        addToFriendActivity(UserDetails.myFollowers, UserDetails.mysong, ID);
    }

    //erase song from recents
    public void eraseFromRecents(String mysong) {
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("FriendsActivity");
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

    //play song from pause
    public void playFromPause(Integer time) {
        mediaPlayer.seekTo(time);
        mediaPlayer.start();
        TextView title = (TextView) findViewById(R.id.track_title);
        String songtitle = title.getText().toString();
        getFollowers(UserDetails.fullname, songtitle);
        Button btn = (Button) this.findViewById(R.id.button);
        btn.setBackgroundResource(R.drawable.ic_media_pause);
    }

    //slide up player page
    public void openPlayerPage(View v) {
        Intent i = new Intent(RealTimeActivity.this, MusicPlayerActivity.class);
        startActivity(i);
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
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String reqId = snapshot.getKey().toString();
                            if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

    public Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            Utilities utils = new Utilities();
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            // Displaying Total Duration time
            MusicPlayerActivity.songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            MusicPlayerActivity.songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            MusicPlayerActivity.songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    //get fullname
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return UserDetails.fullname;
    }

    //add to homepage Firebase node
    private void addToFirebaseHome(String myvalue, final String mysong) {

        String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");

        mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.myname = dataSnapshot.getValue().toString();
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

    //add to homepage
    public void addToHome(List<String> myvalue, final String mysong) {
        String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");

        mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
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

            if (!checkBox.isChecked()) {
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

    //add to friend activity
    public void addToFriendActivity(List<String> myvalue, final String mysong, final String myid) {

        mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(myid).child("Name");

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

            if (!checkBox.isChecked()) {
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

    //get friend fullname
    private void getFriendFullname(String id) {

        mDatabase7 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(id).child("Name");

        mDatabase7.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //get song URL
    public void getUrl(String song) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase songRef = ref.child("URL").child(song);

        songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                    url = String.valueOf(dsp.getValue());
                    UserDetails.song = url;
                    MusicPlayerActivity.urls.add(0, url);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    //send time request for synchronisation
    public void sendTimeRequest(String listenerFullname, String url) {

        String me = firebaseAuth1.getCurrentUser().getUid();
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/TimeRequest/" + me);
        Map<String, Object> uinfo = new HashMap<>();
        uinfo.put("Name", listenerFullname);
        uinfo.put("Song", url);
        ref.updateChildren(uinfo);
    }

    //add time to Firebase to synchronise song
    public void addTimeToFirebase(String otherUser, String song) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/TimeAnswer/" + ID);
        Map<String, Object> uinfo = new HashMap<>();
        uinfo.put("Time", getCurrentPlayingTime());
        uinfo.put("IDReq", otherUser);
        uinfo.put("Song", song);
        ref.updateChildren(uinfo);
    }

    //get current playing time
    public int getCurrentPlayingTime() {
        if (!mediaPlayer.isPlaying()) {
//            Toast.makeText(this, "Music cannot be shared", Toast.LENGTH_SHORT).show();
        }
        return mediaPlayer.getCurrentPosition();
    }

    //get time from Firebase
    public void getTimeFromFirebase() {

        String myID = firebaseAuth1.getCurrentUser().getUid();
        DatabaseReference timeref = FirebaseDatabase.getInstance().getReference().child("TimeRequest").child(myID).child("Time");
        timeref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String data = dataSnapshot.getValue().toString();
                int timeFb = Integer.parseInt(data);
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

    //get URL
    public String getUrlValue() {
        return addr;
    }

    //play music
    public void playMusic(final int time, String url) {
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
            mediaPlayer.seekTo(time + 3100);


        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        Button btn = (Button) this.findViewById(R.id.button);
        btn.setBackgroundResource(R.drawable.ic_media_pause);
    }

    //add to playlist
    public void addToPlaylist(View v) {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add.setVisibility(View.GONE);
                //addgreen.setVisibility(View.VISIBLE);
            }
        });
    }

    //send notification
    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            String launchUrl = result.notification.payload.launchURL; // update docs launchUrl
            String customKey;
            openURL = null;
            Object activityToLaunch = RealTimeActivity.class;

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

                    final String ID;
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
                                    checkIfStillListening(friend, song);
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

                    final String ID;
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
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        String reqId = snapshot.getKey().toString();
                                                        if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                    Intent intent = new Intent(getApplicationContext(), Chat.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("openURL", openURL);
                    intent.putExtra("Uniqid", "NotificationShareWith");
                    startActivity(intent);
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
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        String reqId = snapshot.getKey().toString();
                                        if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
                                            dataSnapshot.child(reqId).getRef().removeValue();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                                }
                            });


                } else if (result.action.actionID.equals("comment")) {
                    Log.i("OneSignalExample", "button id called: " + result.action.actionID);
                } else if (result.action.actionID.equals("reply")) {
                    FirebaseAuth fb;
                    fb = FirebaseAuth.getInstance();

                    final String ID;
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
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        String reqId = snapshot.getKey().toString();
                                                        if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
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
        }
    }

    //check if friend is still listening
    private void checkIfStillListening(final String friend, final String song) {
        DatabaseReference home = FirebaseDatabase.getInstance().getReference().child("Homepage").child(ID);
        home.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Toast.makeText(RealTimeActivity.this, friend + " " + song, Toast.LENGTH_SHORT).show();
                        if (dataSnapshot.hasChild(friend)) {
                            if (dataSnapshot.child(friend).child("Song").getValue().equals(song)) {
                                Intent intent = new Intent(getApplicationContext(), Chat.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("openURL", openURL);
                                intent.putExtra("Uniqid", "NotificationListenWith");
                                UserDetails.chatWith = friend;
                                intent.putExtra("Friend", friend);
                                intent.putExtra("Song", song);
                                sendTimeRequest(friend, song);
                                startActivity(intent);
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
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    String reqId = snapshot.getKey().toString();
                                                    if (dataSnapshot.child(reqId).child("IDReq").getValue().toString().equals(ID)) {
                                                        dataSnapshot.child(reqId).getRef().removeValue();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                                            }
                                        });
                            } else {
                                Toast.makeText(RealTimeActivity.this, friend + " is no longer listening", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RealTimeActivity.this, friend + " is no longer listening", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("TodoApp", "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    //handling on activity result
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

            if (filePath != null) {
                pd.show();

                final StorageReference childRef = picRef.child(ID + ".jpg");

                //uploading the image
                UploadTask uploadTask = childRef.putFile(filePath);

                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(RealTimeActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();

                        @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        UserDetails.picturelink = downloadUrl.toString();
                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase picRef = ref.child("ProfilePictures").child(ID);
                        Map<String, Object> info = new HashMap<String, Object>();
                        info.put("Url", downloadUrl.toString());
                        picRef.updateChildren(info);

                        Picasso.with(RealTimeActivity.this)
                                .load(downloadUrl.toString())
                                .fit()
                                .into(profilePic);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(RealTimeActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(RealTimeActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}







