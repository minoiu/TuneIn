package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import static android.os.Build.ID;
import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;
import static com.qmul.nminoiu.tunein.UserDetails.friend;


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
        //backgroundLayout = (LinearLayout)findViewById(R.id.backgroundLayout);
        final Context context = this;

//        if(mediaPlayer.isPlaying()){
//            play_toolbar.setVisibility(View.VISIBLE);
//            track_title.setText(UserDetails.playingSongName);
//        } else play_toolbar.setVisibility(View.GONE);
//

        Intent i = getIntent();

        if (i.hasExtra("Uniqid")) {
            String uniqid = i.getStringExtra("Uniqid");
            if (uniqid.equals("FromFollowersShare")) {
                String song = i.getStringExtra("Song");
                String friend = i.getStringExtra("Name");
                getSupportActionBar().setTitle(friend);
                String text = "Here is a song for you:\n" + song;
                messageArea.setText(text);
                track_title.setText(UserDetails.playingSongName);
                if(mediaPlayer.isPlaying()){
                    play_toolbar.setVisibility(View.VISIBLE);
                } else play_toolbar.setVisibility(View.GONE);
                addToFbShareWith(song,friend);

            } if (uniqid.equals("FromFollowers")){
                String song = i.getStringExtra("Song");
                String text = "Here is a song for you:\n" + song;
                String friend = i.getStringExtra("FriendName");
                UserDetails.chatWith=friend;
                getSupportActionBar().setTitle(friend);
                messageArea.setText(text);
                track_title.setText(UserDetails.playingSongName);
                if(mediaPlayer.isPlaying()){
                    play_toolbar.setVisibility(View.VISIBLE);
                } else play_toolbar.setVisibility(View.GONE);
            } if(uniqid.equals("FromFollowersListeWith")){
                String song = i.getStringExtra("Song");
                String friend = i.getStringExtra("FriendName");
                UserDetails.chatWith=friend;
                if(mediaPlayer.isPlaying()){
                    track_title.setText(UserDetails.playingSongName);
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
                getSupportActionBar().setTitle(friend);
                btn.setBackgroundResource(R.drawable.ic_media_pause);
                play_toolbar.setVisibility(View.VISIBLE);

                play_toolbar.bringToFront();
                track_title.setText(UserDetails.playingSongName);
            } if(uniqid.equals("NotificationShareWith")) {
                String friend = i.getStringExtra("Friend");
                getFullname();
                UserDetails.chatWith = friend;
                String song = i.getStringExtra("Song");
                getSupportActionBar().setTitle(friend);
                btn.setBackgroundResource(R.drawable.ic_media_pause);
                play_toolbar.setVisibility(View.VISIBLE);
                play_toolbar.bringToFront();
                track_title.setText(UserDetails.playingSongName);
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
                String song = UserDetails.playingSongName;
                getFullname();
                UserDetails.chatWith = friend;
                if(mediaPlayer.isPlaying()){
                    track_title.setText(UserDetails.playingSongName);
                    play_toolbar.setVisibility(View.VISIBLE);
                } else play_toolbar.setVisibility(View.GONE);
                getSupportActionBar().setTitle(friend);
            }
        }
        if(i.hasExtra("Song")){
            String song = i.getStringExtra("Song");
            track_title.setText(song);
        }

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String curUser = currentuser.getUid().toString();


        ID = firebaseAuth.getCurrentUser().getUid();
        sender = firebaseAuth.getCurrentUser().getEmail();

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

//        Picasso.with(this).load("https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/ProfilePictures%2FcrLOSAGbIAMk0urympRLWWEXftD3.jpg?alt=media&token=39fba14e-f780-4c0f-bc70-cc00c3b915cd").into(new Target(){
//
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                    layout.setBackground(new BitmapDrawable(context.getResources(), bitmap));
//                }
//            }
//
//            @Override
//            public void onBitmapFailed(final Drawable errorDrawable) {
//                Log.d("TAG", "FAILED");
//            }
//
//            @Override
//            public void onPrepareLoad(final Drawable placeHolderDrawable) {
//                Log.d("TAG", "Prepare Load");
//            }
//        });

//        Picasso.with(this).load("https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/ProfilePictures%2F3Plkwd7VsHhsKs7jDdLElpCyIpy1.jpg?alt=media&token=ee860cd6-f217-4fd0-a76c-b3620dc72354").into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                layout.setBackground(new BitmapDrawable(bitmap));
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });
//        Picasso.with(Chat.this)
//                .load("\"https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/ProfilePictures%2FcrLOSAGbIAMk0urympRLWWEXftD3.jpg?alt=media&token=39fba14e-f780-4c0f-bc70-cc00c3b915cd")
////                .resize(350, 240)
////                .centerInside()
//
//                .fit()
//                //.centerCrop()
//                .into((Target) layout);
//
//

//        DatabaseReference mDatabase5 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
//
//        mDatabase5.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
//            @Override
//            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
//                UserDetails.fullname = dataSnapshot.getValue().toString();
//                //Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
//                //getFollowers(UserDetails.fullname);
//                //Toast.makeText(SettingsActivity.this, myFollowers.size() + " followers", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });

        Toast.makeText(Chat.this, "me "+ UserDetails.username, Toast.LENGTH_SHORT).show();
        Toast.makeText(Chat.this, "friend "+ UserDetails.chatWith, Toast.LENGTH_SHORT).show();


        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://tunein-633e5.firebaseio.com/Messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://tunein-633e5.firebaseio.com/Messages/" + UserDetails.chatWith + "_" + UserDetails.username);
        refFriend = new Firebase("https://tunein-633e5.firebaseio.com/RecentMessages/" + UserDetails.chatWith +"/"+ UserDetails.chatWith + "_" + UserDetails.username);
        refMe = new Firebase("https://tunein-633e5.firebaseio.com/RecentMessages/" + UserDetails.username+"/"+ UserDetails.username + "_" + UserDetails.chatWith);



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

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
                    myMap.put("user", UserDetails.chatWith);
                    myMap.put("time", String.valueOf(System.currentTimeMillis()));

                    reference1.push().setValue(map);
                    reference2.push().setValue(map);

//                    checkPreviousMessagesInFriend(UserDetails.chatWith, UserDetails.username);
//                    checkPreviousMessages(UserDetails.chatWith, UserDetails.username);



//                    refFriend.addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                            Map map = dataSnapshot.getValue(Map.class);
//                            String message = map.get("message").toString();
//                            String userName = map.get("user").toString();
//                            String time =map.get("time").toString();
//
//                            if (userName.contains(UserDetails.username)) {
//                                dataSnapshot.child(dataSnapshot.getKey().toString()).getRef().removeValue();
//                            }
//                        }
//
//                        @Override
//                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//
//                        }
//                    });
//
                    refFriend.updateChildren(friendMap);

//                    refFriend.push().setValue(map1);
                    refMe.updateChildren(myMap);

                    getReceiver(UserDetails.chatWith, UserDetails.username, messageText);
                    messageArea.getText().clear();
                }
            }
        });

        play_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(Chat.this, AndroidBuildingMusicPlayerActivity.class);
                intent_info.putExtra("Uniqid", "FromChat");
                if (mediaPlayer.isPlaying()) {
                    intent_info.putExtra("Song", track_title.getText().toString());
                    UserDetails.playingSongName = track_title.getText().toString();
                }
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            }
        });


        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                //String time =map.get("time").toString();
                ///

                if (message.contains("Here is a song for you:")) {

                    String link = message.substring(24);
                    String text = "Here is a song for you:\n";

                    String linkMessage = "<a href='www.link.com'>" + link + "</a>";
                    String text1 = text + "<font color=#ff6f00>" + link + "</font>";


                    if (userName.equals(UserDetails.username)) {
                        addMessageBox("You:\n" + Html.fromHtml(text1), 1);
                    } else {
                        addMessageBox(UserDetails.chatWith + ":\n" + Html.fromHtml(text1), 2);
                    }

                } else {
                    if (userName.equals(UserDetails.username)) {
                        addMessageBox("You:\n" + message, 1);
                    } else {
                        addMessageBox(UserDetails.chatWith + ":\n" + message, 2);
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

    private void checkPreviousMessages(String chatWith, String username) {
    }

    private void checkPreviousMessagesInFriend(final String chatWith, String username) {

        DatabaseReference reffriend = FirebaseDatabase.getInstance().getReference().child("RecentMessages");
        reffriend.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

                for (com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(dataSnapshot.hasChild(chatWith)){
                        Toast.makeText(Chat.this, "Has child "+ chatWith +" snap is "+snapshot, Toast.LENGTH_SHORT).show();

//                        for (com.google.firebase.database.DataSnapshot snap : dataSnapshot.child(UserDetails.chatWith)) {
//                            String key = snap.getKey();
//                            if(dataSnapshot.child(ID).child(friend).child(key).getValue().toString().equals(playlist)){
//                                friend = friend;
//                                Toast.makeText(MyPlaylists.this, "friend who shared is " + friend, Toast.LENGTH_LONG).show();
//
//
//                            }
                        }
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



    }

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
                //Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
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

    private void getReceiver(final String friendName,final String myName, final String message) {
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(friendName).child("Email");
        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserDetails.receiver = dataSnapshot.getValue().toString();
                sendNotification(UserDetails.receiver, myName, message);
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


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

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(Chat.this);

        if (message.contains("Here is a song for you:")) {

            final String song = message.split(": ")[1];
            String part2 = message.split(": ")[0];
            String firstPart = part2+": ";
//            Toast.makeText(Chat.this, "song is" + song, Toast.LENGTH_SHORT).show();
//            Toast.makeText(Chat.this, "part is" + part2, Toast.LENGTH_SHORT).show();
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

    private void sendNotification(final String username, final String sender, final String message) {

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
                        if (SettingsActivity.loggedEmail.equals(username)) {
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

//                        String strJsonBody = "{'contents': {'en': 'The notification message or body'}," +
//                                "'app_id': ['99ce9cc9-d20d-4e6b-ba9b-de2e95d3ec00']'}" ;
                            //"'headings': {'en': 'Notification Title'}, " +
                            //"'big_picture': 'http://i.imgur.com/DKw1J2F.gif'}";

                            String strJsonBody = "{"
                                    + "\"app_id\": \"99ce9cc9-d20d-4e6b-ba9b-de2e95d3ec00\","

                                    + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                    + "\"data\": {\"foo\": \"bar\"},"
                                    + "\"contents\": {\"en\": \"" + sender + " said: '" + message + "'.\"},"
                                    + "\"buttons\":[{\"id\": \"reply\", \"text\": \"Reply\"}]"
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
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                UserDetails.username = dataSnapshot.getValue().toString();
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
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserDetails.myname = dataSnapshot.getValue().toString();
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
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        String v;
                        for (com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.getChildren()) {
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
        // Handle actifon bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

       // Toast.makeText(Chat.this, "back to " + UserDetails.oldIntent, Toast.LENGTH_SHORT).show();

        if(UserDetails.oldIntent.equals("Followers")){
            Intent backMainTest = new Intent(this, PlaylistSongs.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
                UserDetails.playingSongName = track_title.getText().toString();
            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }else if(UserDetails.oldIntent.equals("MySongs")){
            Intent backMainTest = new Intent(this, Songs.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
                UserDetails.playingSongName = track_title.getText().toString();

            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("Downloads")){
            Intent backMainTest = new Intent(this, Downloads.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
                UserDetails.playingSongName = track_title.getText().toString();

            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("Favourites")){
            Intent backMainTest = new Intent(this, Favourites.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
                UserDetails.playingSongName = track_title.getText().toString();

            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("Recents")){
            Intent backMainTest = new Intent(this, LibraryActivity.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
                UserDetails.playingSongName = track_title.getText().toString();

            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("FromUsers")){
            Intent backMainTest = new Intent(this, Conversations.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
                UserDetails.playingSongName = track_title.getText().toString();

            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("Conversations")){
            Intent backMainTest = new Intent(this, Conversations.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
                UserDetails.playingSongName = track_title.getText().toString();

            }
//            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }






    }

    public void openPlayerPage(View v) {
        Intent i = new Intent(Chat.this, AndroidBuildingMusicPlayerActivity.class);
        startActivity(i);
    }


}