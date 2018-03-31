package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

/**
 * Created by nicoleta on 19/10/2017.
 */

public class Chat extends AppCompatActivity {

    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2, reference3, refFriend, refMe;
    private LinearLayout play_toolbar;
    public TextView track_title;
    private Button btn;
    private FirebaseAuth firebaseAuth1;
    private Handler mHandler;
    private String url;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference db;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private DatabaseReference db1;
    private String value;
    private String sender;
    private String ID;
    static boolean active = false;
    private Toolbar toolbar;
    private LinearLayout backgroundLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        play_toolbar.setClickable(true);
        btn = (Button) findViewById(R.id.button);
        firebaseAuth1 = FirebaseAuth.getInstance();
        ID = firebaseAuth1.getCurrentUser().getUid();
        track_title = (TextView) findViewById(R.id.track_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Context context = this;
        sender = firebaseAuth1.getCurrentUser().getEmail();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String curUser = currentuser.getUid().toString();

        //get intent to identify previous acivity
        Intent i = getIntent();
        if (i.hasExtra("Uniqid")) {
            String uniqid = i.getStringExtra("Uniqid");
            if (uniqid.equals("FromFollowersShare")) {
                String song = i.getStringExtra("Song");
                String friend = i.getStringExtra("Friend");
                getSupportActionBar().setTitle(friend);
                String text = "Here is a song for you:\n" + song;
                messageArea.setText(text);
                track_title.setText(song);
                if(mediaPlayer.isPlaying()){
                    play_toolbar.setVisibility(View.VISIBLE);
                } else play_toolbar.setVisibility(View.GONE);
                addToFbShareWith(song,friend);

            } if (uniqid.equals("FromFollowers")){
                String song = i.getStringExtra("Song");
                String text = "Here is a song for you:\n" + song;
                String friend = i.getStringExtra("Friend");
                UserDetails.chatWith=friend;
                getSupportActionBar().setTitle(friend);
                messageArea.setText(text);
                track_title.setText(song);
                if(mediaPlayer.isPlaying()){
                    play_toolbar.setVisibility(View.VISIBLE);
                } else play_toolbar.setVisibility(View.GONE);

            } if(uniqid.equals("FromFollowersListeWith")){
                String song = i.getStringExtra("Song");
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);
                String friend = i.getStringExtra("Friend");
                UserDetails.chatWith=friend;
                if(mediaPlayer.isPlaying()){
                    track_title.setText(song);
                    play_toolbar.setVisibility(View.VISIBLE);
                } else play_toolbar.setVisibility(View.GONE);
                getSupportActionBar().setTitle(friend);
                getURL(song);
                addToFbListenWith(song,friend);

            } if(uniqid.equals("NotificationListenWith")){
                String friend = i.getStringExtra("Friend");
                getFullname();
                UserDetails.chatWith=friend;
                String song = i.getStringExtra("Song");
                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);
                getSupportActionBar().setTitle(friend);
                btn.setBackgroundResource(R.drawable.ic_media_pause);
                play_toolbar.setVisibility(View.VISIBLE);
                play_toolbar.bringToFront();
                track_title.setText(song);

            } if(uniqid.equals("NotificationShareWith")) {
                String friend = i.getStringExtra("Friend");
                getFullname();
                UserDetails.chatWith = friend;
                String song = i.getStringExtra("Song");
                getSupportActionBar().setTitle(friend);
                btn.setBackgroundResource(R.drawable.ic_media_pause);
                play_toolbar.setVisibility(View.VISIBLE);
                play_toolbar.bringToFront();
                track_title.setText(song);

            }if(uniqid.equals("NotificationReply")) {
                String friend = i.getStringExtra("Friend");
                getFullname();
                UserDetails.chatWith = friend;
                getSupportActionBar().setTitle(friend);

            } if(uniqid.equals("FromConversations")) {
                String friend = i.getStringExtra("Friend");
                String song = i.getStringExtra("Song");
                getFullname();
                UserDetails.chatWith = friend;
                if(mediaPlayer.isPlaying()){
                    track_title.setText(song);
                    play_toolbar.setVisibility(View.VISIBLE);
                } else play_toolbar.setVisibility(View.GONE);
                getSupportActionBar().setTitle(friend);

            } if(uniqid.equals("FromUsers")) {
                String friend = i.getStringExtra("Friend");
                String song = i.getStringExtra("Song");;
                getFullname();
                UserDetails.chatWith = friend;
                if(mediaPlayer.isPlaying()){
                    track_title.setText(song);
                    play_toolbar.setVisibility(View.VISIBLE);
                } else play_toolbar.setVisibility(View.GONE);
                getSupportActionBar().setTitle(friend);
            }
        }

        //retrieve current playing song from Firebase
        DatabaseReference songTitleRef = FirebaseDatabase.getInstance().getReference().child("CurrentSong");
        songTitleRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(ID).exists()){
                    String song = dataSnapshot.child(ID).child("Song").getValue().toString();
                    track_title.setText(song);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //retrieve fullname from Firebase
        db = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserDetails.username = dataSnapshot.getValue().toString();
                UserDetails.fullname = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //retrieve current user from Firebase
        DatabaseReference mDatabase3 = FirebaseDatabase.getInstance().getReference().child("Users").child(curUser);
        mDatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                UserDetails.username = value;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Firebase.setAndroidContext(this);

        //initialise references for chat
        reference1 = new Firebase("https://tunein-633e5.firebaseio.com/Messages/" + UserDetails.username + "_" + getIntent().getStringExtra("Friend"));
        reference2 = new Firebase("https://tunein-633e5.firebaseio.com/Messages/" + getIntent().getStringExtra("Friend") + "_" + UserDetails.username);
        refFriend = new Firebase("https://tunein-633e5.firebaseio.com/RecentMessages/" + getIntent().getStringExtra("Friend") +"/"+ getIntent().getStringExtra("Friend") + "_" + UserDetails.username);
        refMe = new Firebase("https://tunein-633e5.firebaseio.com/RecentMessages/" + UserDetails.username+"/"+ UserDetails.username + "_" + getIntent().getStringExtra("Friend"));

        Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/Intent/" + EncodeString(sender));
        Map<String, Object> uinfo = new HashMap<>();
        uinfo.put("Intent", "Chat");
        refsong.updateChildren(uinfo);

        //click on send message
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                getFullname();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    Map<String, String> map1 = new HashMap<String, String>();

                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    map.put("time", String.valueOf(System.currentTimeMillis()));

                    map1.put("message", messageText);
                    map1.put("user", UserDetails.username);
                    map1.put("time", String.valueOf(System.currentTimeMillis()));

                    Map<String, Object> friendMap = new HashMap<>();
                    friendMap.put("message", messageText);
                    friendMap.put("user", UserDetails.username);
                    friendMap.put("time", String.valueOf(System.currentTimeMillis()));

                    Map<String, Object> myMap = new HashMap<>();
                    myMap.put("message", messageText);
                    myMap.put("user", getIntent().getStringExtra("Friend"));
                    myMap.put("time", String.valueOf(System.currentTimeMillis()));

                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    refFriend.updateChildren(friendMap);
                    refMe.updateChildren(myMap);
                    getReceiver(getIntent().getStringExtra("Friend"), UserDetails.username, messageText);
                    messageArea.getText().clear();
                }
            }
        });

        //click on play toolbar
        play_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(Chat.this, MusicPlayerActivity.class);
                intent_info.putExtra("Uniqid", "FromChat");
                if (mediaPlayer.isPlaying()) {
                    intent_info.putExtra("Song", track_title.getText().toString());
                }
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            }
        });

        //retrieve messages for Firebase and create message boxes for each
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if (message.contains("Here is a song for you:")) {
                    String link = message.substring(24);
                    String text = "Here is a song for you:\n";
                    String linkMessage = "<a href='www.link.com'>" + link + "</a>";
                    String text1 = text + "<font color=#ff6f00>" + link + "</font>";
                    if (userName.equals(UserDetails.username)) {
                        addMessageBox("You:\n" + Html.fromHtml(text1), 1);
                    } else {
                        addMessageBox(getIntent().getStringExtra("Friend") + ":\n" + Html.fromHtml(text1), 2);
                    }

                } else {
                    if (userName.equals(UserDetails.username)) {
                        addMessageBox("You:\n" + message, 1);
                    } else {
                        addMessageBox(getIntent().getStringExtra("Friend") + ":\n" + message, 2);
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
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    /**
     * Encode string string.
     *
     * @param string the string
     * @return the string
     */
    public static String EncodeString(String string) {
        String nst = string.replace("@", "");
        return nst.replace(".", "");
    }

    /**
     * Decode string string.
     *
     * @param string the string
     * @return the string
     */
    public static String DecodeString(String string) {
        return string.replace("", "@");
    }

    //handling share song option via chat
    private void addToFbShareWith(String song,String friend) {
        FirebaseAuth fb;
        fb = FirebaseAuth.getInstance();
        String ID;
        ID = fb.getCurrentUser().getUid();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
        mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        reference3 = new Firebase("https://tunein-633e5.firebaseio.com/ShareWith/" + friend);
        Map<String, Object> map = new HashMap<>();
        map.put("Name", UserDetails.fullname);
        map.put("Song", song);
        reference3.updateChildren(map);
    }

    //add song chosen to share to Firebase
    private void addToFbListenWith(String song,String friend) {
        FirebaseAuth fb;
        fb = FirebaseAuth.getInstance();
        String ID;
        ID = fb.getCurrentUser().getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
        mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        reference3 = new Firebase("https://tunein-633e5.firebaseio.com/ListenWith/" + friend);
            Map<String, Object> map = new HashMap<>();
            map.put("Name", UserDetails.fullname);
            map.put("Song", song);
            reference3.updateChildren(map);
    }

    //get receiver
    private void getReceiver(final String friendName,final String myName, final String message) {
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(friendName).child("Email");
        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserDetails.receiver = dataSnapshot.getValue().toString();
                String receiver = dataSnapshot.getValue().toString();
                checkReceiverInIntent(receiver, myName, message);
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //get current intent from Firebase
    private void checkReceiverInIntent(final String receiver, final String myName, final String message) {
        DatabaseReference songTitleRef = FirebaseDatabase.getInstance().getReference().child("Intent");
        songTitleRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(EncodeString(receiver)).exists()){
                    String intent = dataSnapshot.child(EncodeString(receiver)).child("Intent").getValue().toString();
                    if(intent.equals("Chat")) {

                    } else{
                        sendNotification(receiver, myName, message);
                    }
                } else sendNotification(receiver, myName, message);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //get song URL
    private void getURL(final String song) {
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
                    startMusic(url, song);
                    getFollowers(UserDetails.fullname, song);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    /**
     * Add message box.
     *
     * @param message the message
     * @param type    the type
     */
    public void addMessageBox(String message, int type){
        TextView textView = new TextView(Chat.this);

        if (message.contains("Here is a song for you:")) {

            final String song = message.split(": ")[1];
            String part2 = message.split(": ")[0];
            String firstPart = part2+": ";
            String colouredSong = "<font color=#ff6f00>" + song + "</font>";
            textView.setText(Html.fromHtml(firstPart+colouredSong));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 0, 10);
            textView.setLayoutParams(lp);

            if (type == 1) {
                textView.setBackgroundResource(R.drawable.rounded_corner1);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                textView.setBackgroundResource(R.drawable.rounded_corner2);
                textView.setTextColor(Color.parseColor("#000000"));

            }

            layout.addView(textView);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyboard(Chat.this);
                    play_toolbar.setVisibility(View.VISIBLE);
                    play_toolbar.requestLayout();
                    play_toolbar.bringToFront();
                    track_title = (TextView) findViewById(R.id.track_title);
                    track_title.setText(song);
                    Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                    Map<String, Object> uinfo = new HashMap<>();
                    uinfo.put("Song", song);
                    refsong.updateChildren(uinfo);
                    getLink(song);
                }
            });
        }
            else {

            textView.setText(message);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if (type == 1) {
                textView.setBackgroundResource(R.drawable.rounded_corner1);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                textView.setGravity(Gravity.START);
                lp.gravity = Gravity.RIGHT;
                lp.setMargins(50, 10, 20, 20);
                textView.setLayoutParams(lp);
            } else {
                textView.setBackgroundResource(R.drawable.rounded_corner2);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setGravity(Gravity.START);
                lp.gravity = Gravity.LEFT;
                lp.setMargins(20, 10, 50, 20);
                textView.setLayoutParams(lp);
            }
            layout.addView(textView);
            String text = textView.getText().toString();
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    //get URL and start song
    private void getLink(final String song) {
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
                    startMusic(url, song);
                    getFollowers(UserDetails.fullname, song);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    //send notification
    private void sendNotification(final String username, final String sender, final String message) {

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    int SDK_INT = android.os.Build.VERSION.SDK_INT;
                    if (SDK_INT > 8) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        String send_email;
                        if (RealTimeActivity.loggedEmail.equals(username)) {
                            send_email = UserDetails.receiver;
                        } else {
                            send_email = username;
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
                                    + "\"contents\": {\"en\": \"" + sender + " said: '" + message + "'.\"},"
                                    + "\"buttons\":[{\"id\": \"reply\", \"text\": \"Reply\"}]"
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

    /**
     * Hide soft keyboard.
     *
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
     *
     * @param link the link
     * @param song the song
     */
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
     * Gets fullname.
     */
    public void getFullname() {

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
                //Toast.makeText(RealTimeActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /**
     * Add to home.
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
        //song = ((TextView) view).getText().toString();
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
            playFromPause(length);
            Button btn = (Button) this.findViewById(R.id.button);
            btn.setBackgroundResource(R.drawable.ic_media_pause);
        }

        mediaPlayer.getCurrentPosition();
    }

    /**
     * Erase from firebase when song is paused.
     */
    public void eraseFromFirebase() {
        final RealTimeActivity sa =  new RealTimeActivity();
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

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    //click on back arrow and get previous intent
    @Override
    public void onBackPressed() {

        DatabaseReference songTitleRef = FirebaseDatabase.getInstance().getReference().child("Intent");
        songTitleRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(EncodeString(sender)).exists()) {
                    dataSnapshot.child(EncodeString(sender)).getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(UserDetails.oldIntent.equals("Followers")){
            Intent backMainTest = new Intent(this, PlaylistSongs.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }else if(UserDetails.oldIntent.equals("MySongs")){
            Intent backMainTest = new Intent(this, Songs.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("Downloads")){
            Intent backMainTest = new Intent(this, Downloads.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("Favourites")){
            Intent backMainTest = new Intent(this, Favourites.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("Recents")){
            Intent backMainTest = new Intent(this, LibraryActivity.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("FromUsers")){
            Intent backMainTest = new Intent(this, Conversations.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("Conversations")){
            Intent backMainTest = new Intent(this, Conversations.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
            }
            startActivity(backMainTest);
            finish();
        } else {
            Intent backMainTest = new Intent(this, Conversations.class);
            startActivity(backMainTest);
            finish();
        }
    }

    /**
     * Open player page.
     *
     * @param v the v
     */
    public void openPlayerPage(View v) {
        Intent i = new Intent(Chat.this, MusicPlayerActivity.class);
        startActivity(i);
    }

}