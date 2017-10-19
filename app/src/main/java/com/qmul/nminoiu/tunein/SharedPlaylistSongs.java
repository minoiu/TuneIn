package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static android.os.Build.ID;
import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

public class SharedPlaylistSongs extends AppCompatActivity {

    private DatabaseReference db;
    private RelativeLayout sharedOwnership;
    private RelativeLayout sharedList;

    private ListView ulistView;
    private ListView sharedWithListView;
    private AdapterSharedWithMe adapterShared;
    private ImageView showSharedImg;


    private ArrayAdapter<String> uadapter;
    private DatabaseReference receiverRef;
    private DatabaseReference mDatabase1;
    MaterialSearchView searchView;
    LinearLayout searchLayout;
    LinearLayout songsLayout;
    ArrayList<String> sharedFriends = new ArrayList<>();
    LinearLayout deletePlaylist;
    private ListView songs;
    private TextView name;
    private String sender;
    public List<String> songsList;
    private ArrayList<String> users = new ArrayList<>();
    private ArrayAdapter<String> songssadapter;
    private DatabaseReference songsRef;
    private DatabaseReference shareRef;
    private DatabaseReference dwnRef;
    private String ID;
    private List<RowItem> rowItems;
    private List<RowItem> rowItems1;

    private AdapterSharedWithMe adapter;
    private ImageView img;
    private LinearLayout renameLayout;
    private Button cancel;
    private Button cancelShare;
    private Button share;
    private Button delete;
    private Button cancelDelete;
    private FirebaseAuth firebaseAuth;
    private Button rename;
    private EditText playlistName;
    private DatabaseReference playlistRef;
    private DatabaseReference lovedPlaylistRef;
    private DatabaseReference sharedRef;
    public File storagePath;
    private FirebaseStorage mStorage;
    private EditText newName;
    private String newname;
    private DatabaseReference ref;
    private DatabaseReference ref1;
    private DatabaseReference ref2;
    private DatabaseReference ref3;
    private DatabaseReference invitesref;
    private DatabaseReference sharedref;
    private DatabaseReference delLovedPlRef;
    private DatabaseReference delPlSongsRef;
    private DatabaseReference delPlRef;
    private DatabaseReference delPlInvRef;
    private DatabaseReference delPriPlRef;
    private DatabaseReference delSharedPlRef;
    private DatabaseReference delDwnPlRef;
    private String playlist;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private Menu menu;
    private TextView track_title;
    private LinearLayout play_toolbar;
    private Button btn;
    private String me;
    private String url;
    private View bar;
    private TextView byTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_playlist_songs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SharedPlaylistSongs.this, Users.class);
                startActivity(i);
            }
        });
        final CoordinatorLayout.LayoutParams paramsFab = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();


        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        play_toolbar.setClickable(true);
        btn = (Button) findViewById(R.id.button);
        track_title = (TextView) findViewById(R.id.track_title);
        showSharedImg = (ImageView) findViewById(R.id.showShared);
        songsLayout = (LinearLayout) findViewById(R.id.songsLayout);


        mStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String curUser = currentuser.getUid().toString();
        db = FirebaseDatabase.getInstance().getReference().child("Users");
        sender = firebaseAuth.getCurrentUser().getEmail();
        songs = (ListView) findViewById(R.id.songsList);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);


        songs.bringToFront();
        bar = (View) findViewById(R.id.bar);
        img = (ImageView) findViewById(R.id.icon);
        rowItems = new ArrayList<RowItem>();
        rowItems1 = new ArrayList<RowItem>();

        adapter = new AdapterSharedWithMe(this, rowItems);
        adapterShared = new AdapterSharedWithMe(this, rowItems1);

        renameLayout = (LinearLayout) findViewById(R.id.renamePlaylist);
        deletePlaylist = (LinearLayout) findViewById(R.id.deletePlaylist);
        byTextView = (TextView) findViewById(R.id.collabTextView);
        delete = (Button) findViewById(R.id.delete);
        cancelDelete = (Button) findViewById(R.id.cancelDelete);
        cancel = (Button) findViewById(R.id.cancel);
        rename = (Button) findViewById(R.id.rename);
        newName = (EditText) findViewById(R.id.editText);
        playlistName = (EditText) findViewById(R.id.editText);
        renameLayout.bringToFront();
        ulistView = (ListView) findViewById(R.id.friendsList);
        UserDetails.privatePlaylist = false;
        UserDetails.dwnPlaylist = false;
        UserDetails.lovedPlaylist = false;

        Intent i = getIntent();

        if (i != null) {
            if (i.hasExtra("Uniqid")) {
                String uniqid = i.getStringExtra("Uniqid");
                if (uniqid.equals("FromPlaylistsActivity")) {
                    playlist = i.getStringExtra("Name");
                    getSupportActionBar().setTitle(playlist);

                } else {
                    playlist = i.getExtras().getString("Name");
                    getSupportActionBar().setTitle(playlist);
                }
            }
            else {
                playlist = i.getExtras().getString("Name");
                getSupportActionBar().setTitle(playlist);
            }

        }

        if(i.hasExtra("Friend")){
            String friend = i.getStringExtra("Friend");
            UserDetails.friend = friend;
            byTextView.setText("Shared by "+friend);
        }

        if(i.hasExtra("Song")){
            String title = i.getStringExtra("Song");
            track_title.setText(title);
        }
        if (mediaPlayer.isPlaying()) {
            play_toolbar.setVisibility(View.VISIBLE);
            paramsFab.setMargins(53, 0, 0, 160); //bottom margin is 25 here (change it as u wish)
            fab.setLayoutParams(paramsFab);
            track_title.setText(i.getStringExtra("Song"));

        } else play_toolbar.setVisibility(View.GONE);



        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();

        songsList = new ArrayList<>();
        songssadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songsList);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites").child(ID);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String friend = snapshot.getKey();

                    for (DataSnapshot snap : dataSnapshot.child(ID).child(friend).getChildren()) {
                        String key = snap.getKey();
                        if(dataSnapshot.child(ID).child(friend).child(key).getValue().toString().equals(playlist)){
                            UserDetails.friend = friend;
                           // Toast.makeText(SharedPlaylistSongs.this, "friend who shared is " + UserDetails.friend, Toast.LENGTH_LONG).show();
                            byTextView.setText("Shared by "+friend);


                        }
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


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ID").child(UserDetails.friend).child("Id");

        mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserDetails.username = dataSnapshot.getValue().toString();
                songsRef = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs").child(UserDetails.username).child(toolbar.getTitle().toString());
                songsRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        String song = dataSnapshot.getValue(String.class);

                        songsList.add(song);
                        RowItem item = new RowItem(R.drawable.options, song);

                        rowItems.add(item);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                String song = dataSnapshot.getValue(String.class);
//                for(int i = 0; i<=songsList.size()-2;i++){
//                    if(songsList.get(i).equals(song))
//                }
//                songsList.remove(song);
//                rowItems.remove(new RowItem(R.drawable.options,song));
//                adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        String song = dataSnapshot.getValue(String.class);
                        songsList.remove(song);
                        rowItems.remove(new RowItem(R.drawable.options,song));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                songs.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        name = (TextView) findViewById(R.id.textName);

        songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RowItem rowItem = (RowItem) parent.getItemAtPosition(position);
                final String song = rowItem.getTitle();
                play_toolbar.setVisibility(View.VISIBLE);
                paramsFab.setMargins(53, 0, 0, 160); //bottom margin is 25 here (change it as u wish)
                fab.setLayoutParams(paramsFab);
                track_title.setText(song);

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("URL").child(song).child("URL");
                mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        url = dataSnapshot.getValue().toString();
                        startMusic(url, song);
                        btn.setBackgroundResource(R.drawable.ic_media_pause);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

        });

        // Toast.makeText(PlaylistSongs.this, "does it run? ", Toast.LENGTH_SHORT).show();
        shareRef = FirebaseDatabase.getInstance().getReference().child("PrivatePlaylists").child(ID);
        shareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String key = data.getKey().toString();
                    //Toast.makeText(PlaylistSongs.this, "keys are " + key, Toast.LENGTH_SHORT).show();

                    if (dataSnapshot.child(key).getValue().toString().equals(getSupportActionBar().getTitle().toString())) {
                        UserDetails.privatePlaylist = true;
                        // Toast.makeText(PlaylistSongs.this, "title is  " + getSupportActionBar().getTitle().toString(), Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(PlaylistSongs.this, "title is  " + dataSnapshot.child(key).getValue().toString(), Toast.LENGTH_SHORT).show();
                    }
                    // Toast.makeText(PlaylistSongs.this, "playlist is in private " + UserDetails.privatePlaylist, Toast.LENGTH_SHORT).show();

                }
                //Toast.makeText(PlaylistSongs.this, "playlist is in private " + UserDetails.privatePlaylist, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        } );
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//
//                String playlistName = dataSnapshot.getValue(String.class);
//
//                if (playlistName.equals(getSupportActionBar().getTitle().toString())) {
//                    UserDetails.privatePlaylist = true;
//                    Toast.makeText(PlaylistSongs.this, "playlist is in private " + UserDetails.privatePlaylist, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                String playlistName = dataSnapshot.getValue(String.class);
//
//                if (playlistName.equals(getSupportActionBar().getTitle().toString())) {
//                    UserDetails.privatePlaylist = false;
//                }
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

        dwnRef = FirebaseDatabase.getInstance().getReference().child("DownloadedPlaylists").child(ID);
        dwnRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName = dataSnapshot.getValue(String.class);

                if (playlistName.equals(getSupportActionBar().getTitle().toString())) {
                    UserDetails.dwnPlaylist = true;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String playlistName = dataSnapshot.getValue(String.class);

                if (playlistName.equals(getSupportActionBar().getTitle().toString())) {
                    UserDetails.dwnPlaylist = false;
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lovedPlaylistRef = FirebaseDatabase.getInstance().getReference().child("LovedPlaylists").child(ID);
        lovedPlaylistRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName = dataSnapshot.getValue(String.class);

                if (playlistName.equals(getSupportActionBar().getTitle().toString())) {
                    UserDetails.lovedPlaylist = true;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String playlistName = dataSnapshot.getValue(String.class);

                if (playlistName.equals(getSupportActionBar().getTitle().toString())) {
                    UserDetails.lovedPlaylist = false;
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameLayout.setVisibility(View.GONE);
                hideSoftKeyboard(SharedPlaylistSongs.this);
            }
        });

        invalidateOptionsMenu();
    }

//    private void checkIfInInvites(final String friendID, final String myname, final String friend) {
//        UserDetails.notIn = false;
//        DatabaseReference checkInvitesRef = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites");
//        checkInvitesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String sharedWithFriend;
//                if (dataSnapshot.hasChild(friendID) && dataSnapshot.child(friendID).hasChild(myname)) {
//                    for (DataSnapshot snapshot : dataSnapshot.child(friendID).child(UserDetails.myname).getChildren()) {
//                        sharedWithFriend = snapshot.getKey();
////                        if (dataSnapshot.child(friendID).child(UserDetails.myname).child(sharedWithFriend).getChildren().iterator()..getValue().equals(playlist)) {
//
//                        if (dataSnapshot.child(friendID).child(UserDetails.myname).child(sharedWithFriend).getValue().equals(playlist)) {
//                            Toast.makeText(SharedPlaylistSongs.this, "Already shared the playlist '" + playlist + "' with " + friend + ".", Toast.LENGTH_SHORT).show();
//                            UserDetails.notIn = false;
//                        } else UserDetails.notIn = true;
//                    }
//                    if(UserDetails.notIn){
//                        shareOwnership.setVisibility(View.VISIBLE);
//                        shareOwnership.bringToFront();
//                        name.setText(friend + "?");
//                    }
//                } else {
//                    shareOwnership.setVisibility(View.VISIBLE);
//                    shareOwnership.bringToFront();
//                    name.setText(friend + "?");
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


    private void sendNotification(String user, String playlist) {
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
                        //Toast.makeText(PlaylistSongs.this, sender+ " the sender", Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(PlaylistSongs.this, UserDetails.receiver+" the receiver", Toast.LENGTH_SHORT).show();


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
                                + "\"contents\": {\"en\": \"" + UserDetails.myname + " shared a playlist with you. \"},"
                                + "\"buttons\":[{\"id\": \"id2\", \"text\": \"View\"}]"
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

    private void getReceiver(final String user, final String playlist) {
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(user).child("Email");
        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.receiver = dataSnapshot.getValue().toString();
                sendNotification(user, playlist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void rename(View view){
        newname = playlistName.getText().toString().trim();
        String oldPlaylist = getSupportActionBar().getTitle().toString();


        if (newname.equals("")) {
            Toast.makeText(SharedPlaylistSongs.this, "Please enter a name", Toast.LENGTH_LONG).show();
        } else {

            changeInPlaylist(oldPlaylist);
            changeInLovedPlaylists(oldPlaylist);
            changeInPrivate(oldPlaylist);
            changeInDownloaded(oldPlaylist);
            changeInPlaylistSongs(oldPlaylist);
            changeInInvites(oldPlaylist);
            changeInShared(oldPlaylist);
            renameLayout.setVisibility(View.GONE);
            hideSoftKeyboard(SharedPlaylistSongs.this);

            setTitle(newname);
//            new java.util.Timer().schedule(
//                    new java.util.TimerTask() {
//                        @Override
//                        public void run() {
//                            try {
//                                synchronized (this) {
//                                    wait(3000);
//
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            setTitle(newname);
//
//                                        }
//                                    });
//
//                                }
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    },
//                    3000
//            );
//
        }
    }

    private void changeInShared(final String oldname) {
        sharedref = FirebaseDatabase.getInstance().getReference().child("SharedPlaylists").child(ID);
        sharedref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName2 = dataSnapshot.getValue(String.class);

                if (playlistName2.equals(oldname)) {
                    sharedref.child(dataSnapshot.getKey().toString()).setValue(newname);
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

    private void changeInInvites(final String oldname) {
        invitesref = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites");

        invitesref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String v;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            v = snapshot.getKey();
                            DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

                            mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    UserDetails.myname = dataSnapshot.getValue().toString();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            if (dataSnapshot.child(v).hasChild(UserDetails.myname)) {
                                for (DataSnapshot snap : dataSnapshot.child(v).child(UserDetails.myname).getChildren()) {
                                    String key = snap.getKey().toString();
                                    if (dataSnapshot.child(v).child(UserDetails.myname).child(key).getValue().toString().equals(oldname)) {
                                        invitesref.child(v).child(UserDetails.myname).child(key).setValue(newname);
                                    }
                                }
                            }
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    private void setTitle(String name) {

        getSupportActionBar().setTitle(name);

    }

    private void changeInPlaylistSongs(final String oldname) {
        ref3 = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs").child(ID);

        ref3.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String v;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            v = snapshot.getKey();

                            //Toast.makeText(SettingsActivity.this, "in erase" + dataSnapshot.child(snapshot.child(v).getKey().toString()).getKey().toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(SettingsActivity.this, "v" + v, Toast.LENGTH_SHORT).show();
                            //getFulname();
                            if (v.toString().equals(oldname)) {

                                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                                Firebase sRef = ref.child("PlaylistSongs").child(ID);
                                Map<String,Object> sinfo = new HashMap<String, Object>();
                                sinfo.put(newname,dataSnapshot.child(v.toString()).getValue());
                                sRef.updateChildren(sinfo);

                                ref3.child(oldname).removeValue();




                                // dataSnapshot.child(v).getRef().removeValue();

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

    private void changeInDownloaded(final String oldname) {
        ref2 = FirebaseDatabase.getInstance().getReference().child("DownloadedPlaylists").child(ID);
        ref2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName3 = dataSnapshot.getValue(String.class);
                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                if (playlistName3.equals(oldname)) {
                    ref2.child(dataSnapshot.getKey().toString()).setValue(newname);
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

    private void changeInPrivate(final String oldname) {
        ref1 = FirebaseDatabase.getInstance().getReference().child("PrivatePlaylists").child(ID);
        ref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName2 = dataSnapshot.getValue(String.class);
                String oldPlaylist = getSupportActionBar().getTitle().toString();

                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                if (playlistName2.equals(oldname)) {
                    ref1.child(dataSnapshot.getKey().toString()).setValue(newname);
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

    private void changeInLovedPlaylists(final String oldname) {
        ref = FirebaseDatabase.getInstance().getReference().child("LovedPlaylists").child(ID);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName1 = dataSnapshot.getValue(String.class);
                String oldPlaylist = getSupportActionBar().getTitle().toString();
                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                if (playlistName1.equals(oldname)) {
                    ref.child(dataSnapshot.getKey().toString()).setValue(newname);
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

    private void changeInPlaylist(final String oldname) {
        playlistRef = FirebaseDatabase.getInstance().getReference().child("Playlists").child(ID);
        playlistRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName = dataSnapshot.getValue(String.class);
                String oldPlaylist = getSupportActionBar().getTitle().toString();

                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                if (playlistName.equals(oldname)) {
                    playlistRef.child(dataSnapshot.getKey().toString()).setValue(newname);
                    Toast.makeText(SharedPlaylistSongs.this, "Your playlist was renamed", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sharedplaylistoptions, menu);
        this.menu = menu;


        MenuItem whiteHeart = menu.findItem(R.id.menu_like);
        MenuItem redHeart = menu.findItem(R.id.menu_dislike);
//        MenuItem item = menu.findItem(R.id.menu_share);
//        searchView.setMenuItem(item);


        if (UserDetails.lovedPlaylist) {
            redHeart.setVisible(true);
            whiteHeart.setVisible(false);
        } else {
            redHeart.setVisible(false);
            whiteHeart.setVisible(true);
        }

        invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

         if (id == R.id.menu_like) {
            addToLikedPlaylists();

        } else if (id == R.id.menu_dislike) {

            playlistRef = FirebaseDatabase.getInstance().getReference().child("LovedPlaylists").child(ID);
            playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();

                        if (dataSnapshot.child(key).getValue().equals(getSupportActionBar().getTitle().toString())) {

                            dataSnapshot.child(key).getRef().removeValue();
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else if (id == R.id.menu_delete) {

            deletePlaylist.setVisibility(View.VISIBLE);
            deletePlaylist.bringToFront();
            fab.setVisibility(View.GONE);

        } else onBackPressed();


        return super.onOptionsItemSelected(item);
    }

    public void delete(View view){

        getFullname(ID);
        Toast.makeText(SharedPlaylistSongs.this, "Deleting playlist...", Toast.LENGTH_SHORT).show();
        String oldPlaylist = getSupportActionBar().getTitle().toString();
        deleteFromInvitations(oldPlaylist);
        deleteFromHisShared(getSupportActionBar().getTitle().toString());
        deletePlaylist.setVisibility(View.GONE);

    }

    private void deleteFromInvitations(final String oldPlaylist) {
        Intent i = getIntent();
        final String friend = i.getStringExtra("Friend");
      //  Toast.makeText(SharedPlaylistSongs.this, "friendd is "+ friend, Toast.LENGTH_SHORT).show();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ID).exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.child(ID).child(friend).getChildren()) {
                        String key = snapshot.getKey();

                            String playlistSh = dataSnapshot.child(ID).child(friend).child(key).getValue().toString();
                            if (playlistSh.equals(oldPlaylist)) {
                                dataSnapshot.child(ID).child(friend).child(key).getRef().removeValue();
                                Intent i = new Intent(SharedPlaylistSongs.this, MyPlaylists.class);
                                i.putExtra("Uniqid", "FromPlaylistDelete");
                                i.putExtra("Playlist", oldPlaylist);
                                startActivity(i);
                            }
                        }

                    }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //// TODO: 17/10/2017
    private void deleteFromHisShared(final String oldPlaylist) {
        delSharedPlRef = FirebaseDatabase.getInstance().getReference().child("SharedPlaylists");
        delSharedPlRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String friend = snapshot.getKey();


                    for (DataSnapshot snap : dataSnapshot.child(friend).getChildren()) {
                        String key = snap.getKey();
                        if(key.equals(UserDetails.fullname)){
                            for(DataSnapshot s : dataSnapshot.child(friend).child(key).getChildren()){
                                String playlist = s.getValue().toString();
                               // Toast.makeText(SharedPlaylistSongs.this, "playlistt is "+ playlist, Toast.LENGTH_SHORT).show();
                                if(playlist.equals(oldPlaylist)){
                                    dataSnapshot.child(friend).child(key).child(s.getKey()).getRef().removeValue();
                                }
                            }

                        }
//
// String playlistSh = dataSnapshot.child(ID).child(friend).child(key).getValue().toString();
//                        if (playlistSh.equals(oldPlaylist)) {
//                            dataSnapshot.child(ID).child(friend).child(key).getRef().removeValue();
//
//                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void deleteFromShared(final String oldPlaylist) {
        delSharedPlRef = FirebaseDatabase.getInstance().getReference().child("SharedPlaylists").child(ID);
        delSharedPlRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ID).exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                        String friend = snapshot.getKey();

                        for (DataSnapshot snap : dataSnapshot.child(ID).child(friend).getChildren()) {
                            String key = snap.getKey();

                            String playlistSh = dataSnapshot.child(ID).child(friend).child(key).getValue().toString();
                            if (playlistSh.equals(oldPlaylist)) {
                                dataSnapshot.child(ID).child(friend).child(key).getRef().removeValue();
                            }
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //

        delSharedPlRef = FirebaseDatabase.getInstance().getReference().child("SharedPlaylists").child(ID);
        delSharedPlRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    if (dataSnapshot.child(key).getValue().equals(oldPlaylist)) {
                        dataSnapshot.child(key).getRef().removeValue();
                        //Toast.makeText(PlaylistSongs.this, "Your playlist was deleted", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void deleteFromInvites(final String oldPlaylist) {
        delPlInvRef = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites");
        delPlInvRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String v;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    v = snapshot.getKey();
                    DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

                    mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserDetails.myname = dataSnapshot.getValue().toString();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    if (dataSnapshot.child(v).hasChild(UserDetails.myname)) {
                        for (DataSnapshot snap : dataSnapshot.child(v).child(UserDetails.myname).getChildren()) {
                            String key = snap.getKey().toString();
                            if (dataSnapshot.child(v).child(UserDetails.myname).child(key).getValue().toString().equals(oldPlaylist)) {
                                delPlInvRef.child(v).child(UserDetails.myname).child(key).getRef().removeValue();

                            }
                        }
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void deleteFromPlaylistSongs(final String name) {

        delPlSongsRef = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs").child(ID);
        delPlSongsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();

                    if (key.equals(name)) {

                        delPlSongsRef.child(key).getRef().setValue(null);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void deleteFromDownloaded(final String oldPlaylist) {
        delDwnPlRef = FirebaseDatabase.getInstance().getReference().child("DownloadedPlaylists").child(ID);
        delDwnPlRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    if (dataSnapshot.child(key).getValue().equals(oldPlaylist)) {

                        dataSnapshot.child(key).getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void download() {

        for (int i = 0; i <= songsList.size() - 1; i++) {
            String songToDwn = songsList.get(i);

            StorageReference storageReference = mStorage.getReferenceFromUrl("gs://tunein-633e5.appspot.com/bad boi muzik");
            StorageReference down = storageReference.child(songToDwn + ".mp3");
            storagePath = new File(getApplicationContext().getFilesDir(), "My_music");

            File localFile = new File(storagePath, songToDwn);
            try {
                localFile = File.createTempFile("Audio", "mp3");
            } catch (IOException e) {
                e.printStackTrace();
            }

            final File finalLocalFile = new File(storagePath, songToDwn);
            down.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    finalLocalFile.getAbsolutePath();
                    Toast.makeText(getApplicationContext(), "Done dowloading ", Toast.LENGTH_SHORT).show();
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

    }
//        Toast.makeText(getApplicationContext(), "Downloded at location: " + UserDetails.song , Toast.LENGTH_SHORT).show();



    private void addToDownloads() {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("DownloadedPlaylists").child(ID);
        playRef.push().setValue(getSupportActionBar().getTitle().toString());
    }

    private void deleteFromPlaylist(final String oldname){

        delPlRef = FirebaseDatabase.getInstance().getReference().child("Playlists").child(ID);
        delPlRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName = dataSnapshot.getValue(String.class);
                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                if (playlistName.equals(oldname)) {
                    delPlRef.child(dataSnapshot.getKey().toString()).removeValue();
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

    private void deleteFPrivate(final String name) {

        delPriPlRef = FirebaseDatabase.getInstance().getReference().child("PrivatePlaylists").child(ID);
        delPriPlRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName = dataSnapshot.getValue(String.class);
                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                if (playlistName.equals(name)) {
                    delPriPlRef.child(dataSnapshot.getKey().toString()).removeValue();
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

    private void deleteFromLovedPlaylists(final String oldname){
        delLovedPlRef = FirebaseDatabase.getInstance().getReference().child("LovedPlaylists").child(ID);
        delLovedPlRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName = dataSnapshot.getValue(String.class);
                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                if (playlistName.equals(oldname)) {
                    delLovedPlRef.child(dataSnapshot.getKey().toString()).removeValue();
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

    private void deleteFromPrivate() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Playlists").child(ID);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName = dataSnapshot.getValue(String.class);
                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                if (playlistName.equals(getSupportActionBar().getTitle().toString())) {
                    playlistRef.child(dataSnapshot.getKey().toString()).removeValue();

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

    private void addToPrivate() {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("PrivatePlaylists").child(ID);
        playRef.push().setValue(getSupportActionBar().getTitle().toString());
        UserDetails.privatePlaylist = true;

    }

    private void addToLikedPlaylists() {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("LovedPlaylists").child(ID);
        playRef.push().setValue(getSupportActionBar().getTitle().toString());
    }


    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, MyPlaylists.class);
        if(mediaPlayer.isPlaying()) {
            backMainTest.putExtra("Song", track_title.getText().toString());
        }
        startActivity(backMainTest);
        finish();
    }

    public void showMenu(ImageView img) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // buttons.setVisibility(View.VISIBLE);
            }
        });
    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem privateOption = menu.findItem(R.id.menu_private);
        MenuItem publicOption = menu.findItem(R.id.menu_public);
        //MenuItem dwnOption = menu.findItem(R.id.menu_dwn);
        //MenuItem remDwnOption = menu.findItem(R.id.menu_remdwn);

//        if (UserDetails.dwnPlaylist) {
//            dwnOption.setVisible(false);
//            remDwnOption.setVisible(true);
//        } else {
//            dwnOption.setVisible(true);
//            remDwnOption.setVisible(false);
//        }

        return super.onPrepareOptionsMenu(menu);
    }

    private void getFullname(String id) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(id).child("Name");

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

    private void getMyName(){
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.myname = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getBarTitle(){
        return getSupportActionBar().getTitle().toString();
    }

    private void startMusic(String link, String song) {
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

        //AndroidBuildingMusicPlayerActivity.songProgressBar.setProgress(0);
        //AndroidBuildingMusicPlayerActivity.songProgressBar.setMax(100);
//            new AndroidBuildingMusicPlayerActivity().updateProgressBar();
    }

    public void eraseFromFirebase() {
        final SettingsActivity sa = new SettingsActivity();
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





}

