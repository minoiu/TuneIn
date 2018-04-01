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
import android.support.v7.app.AlertDialog;
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

import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;


import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

/**
 * Created by Nicoleta on 16/01/2018
 */
public class PlaylistSongs extends AppCompatActivity {

    private DatabaseReference db;
    private RelativeLayout sharedOwnership;
    private RelativeLayout sharedList;
    private ListView ulistView;
    private ListView sharedWithListView;
    private AdapterShared adapterShared;
    private ImageView showSharedImg;
    private ArrayAdapter<String> uadapter;
    private DatabaseReference receiverRef;
    private DatabaseReference mDatabase1;
    MaterialSearchView searchView;
    LinearLayout searchLayout;
    LinearLayout songsLayout;
    LinearLayout shareOwnership;
    ArrayList<String> sharedFriends = new ArrayList<>();
    private ListView songs;
    private TextView name;
    private String sender;
    public List<String> songsList;
    private ArrayList<String> users = new ArrayList<>();
    private ArrayList<String> recents = new ArrayList<>();
    private ArrayAdapter<String> songssadapter;
    private DatabaseReference songsRef;
    private DatabaseReference shareRef;
    private DatabaseReference dwnRef;
    private String ID;
    private List<RowItem> rowItems;
    private List<RowItem> rowItems1;
    private SongsAdapter adapter;
    private ImageView img;
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
    private FloatingActionButton fab1;
    private Toolbar toolbar;
    private Menu menu;
    public static TextView track_title;
    private LinearLayout play_toolbar;
    private Button btn;
    private String me;
    private String url;
    private View bar;
    private List myFollowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlistsongs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MusicPlayerActivity.songs.clear();
        MusicPlayerActivity.urls.clear();
        myFollowers = new ArrayList<>();
        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        play_toolbar.setClickable(true);
        btn = (Button) findViewById(R.id.button);
        track_title = (TextView) findViewById(R.id.track_title);
        showSharedImg = (ImageView) findViewById(R.id.showShared);
        mStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String curUser = currentuser.getUid().toString();
        db = FirebaseDatabase.getInstance().getReference().child("Users");
        sender = firebaseAuth.getCurrentUser().getEmail();
        songs = (ListView) findViewById(R.id.songsList);
        sharedList = (RelativeLayout) findViewById(R.id.sharedList);
        songs.bringToFront();
        bar = (View) findViewById(R.id.bar);
        img = (ImageView) findViewById(R.id.icon);
        rowItems = new ArrayList<RowItem>();
        rowItems1 = new ArrayList<RowItem>();
        adapter = new SongsAdapter(this, rowItems);
        adapterShared = new AdapterShared(this, rowItems1);
        cancelShare = (Button) findViewById(R.id.cancelShare);
        share = (Button) findViewById(R.id.share);
        playlistName = (EditText) findViewById(R.id.editText);
        ulistView = (ListView) findViewById(R.id.friendsList);
        sharedWithListView = (ListView) findViewById(R.id.sharedFriends);
        sharedWithListView.setAdapter(adapterShared);
        uadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        ulistView.setAdapter(uadapter);
        sharedOwnership = (RelativeLayout) findViewById(R.id.collaborators);
        UserDetails.privatePlaylist = false;
        UserDetails.dwnPlaylist = false;
        UserDetails.lovedPlaylist = false;
        UserDetails.notIn = false;
        Intent i = getIntent();
        songsList = new ArrayList<>();
        songssadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songsList);
        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();

        //handle click on messages
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlaylistSongs.this, Conversations.class);
                if (mediaPlayer.isPlaying()) {
                    String song = track_title.getText().toString();
                    i.putExtra("Song", song);
                }
                startActivity(i);
            }
        });

        //handle click on home button
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlaylistSongs.this, RealTimeActivity.class);
                if (mediaPlayer.isPlaying()) {
                    String song = track_title.getText().toString();
                    i.putExtra("Song", song);
                }
                startActivity(i);
            }
        });

        //set new margins for floating buttons
        final CoordinatorLayout.LayoutParams paramsFab = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        final CoordinatorLayout.LayoutParams paramsFab1 = (CoordinatorLayout.LayoutParams) fab1.getLayoutParams();

        //identify previous intent
        if (i != null) {
            if (i.hasExtra("Uniqid")) {
                String uniqid = i.getStringExtra("Uniqid");
                if (uniqid.equals("FromPlaylistsActivity")) {
                    playlist = i.getStringExtra("Name");
                    getSupportActionBar().setTitle(playlist);

                } else if (uniqid.equals("FromPlayer")) {
                    playlist = i.getStringExtra("Name");
                    getSupportActionBar().setTitle(playlist);
                } else {
                    playlist = i.getExtras().getString("Name");
                    getSupportActionBar().setTitle(playlist);
                }
            } else {
                playlist = i.getExtras().getString("Name");
                getSupportActionBar().setTitle(playlist);
            }
        }

        //handle click on play bar
        play_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(PlaylistSongs.this, MusicPlayerActivity.class);
                intent_info.putExtra("Uniqid", "FromPlaylistSongs");
                if (mediaPlayer.isPlaying()) {
                    intent_info.putExtra("Song", track_title.getText().toString());
                    intent_info.putExtra("OldPlaylist", getSupportActionBar().getTitle().toString());
                }
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            }
        });

        //show playbar if media player is playing
        if (mediaPlayer.isPlaying()) {
            play_toolbar.setVisibility(View.VISIBLE);
            paramsFab.setMargins(53, 0, 0, 160);
            fab.setLayoutParams(paramsFab);
            paramsFab1.setMargins(0, 0, 53, 160);
            fab1.setLayoutParams(paramsFab1);
        } else play_toolbar.setVisibility(View.GONE);

        //retrieve playlist songs from Firebase
        songsRef = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs").child(ID).child(i.getStringExtra("Name"));
        songsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String song = dataSnapshot.getValue(String.class);
                songsList.add(song);
                getUrl(song);
                MusicPlayerActivity.songs.add(song);
                RowItem item = new RowItem(R.drawable.options, song);
                rowItems.add(item);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String song = dataSnapshot.getValue(String.class);
                songsList.remove(song);
                rowItems.remove(new RowItem(R.drawable.options, song));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //get current song from Firebase
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

        invalidateOptionsMenu();
        songs.setAdapter(adapter);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        songsLayout = (LinearLayout) findViewById(R.id.songsLayout);
        shareOwnership = (LinearLayout) findViewById(R.id.shareOwnership);
        name = (TextView) findViewById(R.id.textName);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        //handle click on search view
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchLayout.setVisibility(View.VISIBLE);
                songsLayout.setVisibility(View.GONE);
                searchLayout.bringToFront();
            }

            @Override
            public void onSearchViewClosed() {
                searchLayout.setVisibility(View.GONE);
                songsLayout.setVisibility(View.VISIBLE);
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

        //retrieve followers names
        DatabaseReference mDb = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
        mDb.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.myname = dataSnapshot.getValue().toString();
                DatabaseReference fdb = FirebaseDatabase.getInstance().getReference().child("FollowersNames").child(ID);
                fdb.addChildEventListener(new ChildEventListener() {

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //handle click on songs
        songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RowItem rowItem = (RowItem) parent.getItemAtPosition(position);
                final String song = rowItem.getTitle();
                play_toolbar.setVisibility(View.VISIBLE);
                paramsFab.setMargins(53, 0, 0, 160); //bottom margin is 25 here (change it as u wish)
                fab.setLayoutParams(paramsFab);
                paramsFab1.setMargins(0, 0, 53, 160);
                fab1.setLayoutParams(paramsFab1);
                track_title.setText(song);
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

                    ArrayAdapter uadapter = new ArrayAdapter(PlaylistSongs.this, android.R.layout.simple_list_item_1, ulistFound);
                    ulistView.setAdapter(uadapter);

                } else {
                    //if search text is null
                    ArrayAdapter uadapter = new ArrayAdapter(PlaylistSongs.this, android.R.layout.simple_list_item_1, users);
                    ulistView.setAdapter(uadapter);
                }
                return true;
            }
        });

        //handle click on users list
        ulistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String friend = ((TextView) view).getText().toString();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ID").child(friend).child("Id");
                mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        checkIfInInvites(friendID, UserDetails.myname, friend);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        //retrieve private playlists
        shareRef = FirebaseDatabase.getInstance().getReference().child("PrivatePlaylists").child(ID);
        shareRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String key = data.getKey().toString();
                    if (dataSnapshot.child(key).getValue().toString().equals(getSupportActionBar().getTitle().toString())) {
                        UserDetails.privatePlaylist = true;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //check if playlist is downloaded
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

        //retrieve shared playlist
        DatabaseReference shared = FirebaseDatabase.getInstance().getReference().child("SharedPlaylists").child(ID);
        shared.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String friend = snapshot.getKey();
                    for (DataSnapshot snap : dataSnapshot.child(friend).getChildren()) {
                        String key = snap.getKey();
                        if (dataSnapshot.child(friend).child(key).getValue().equals(toolbar.getTitle().toString())) {
                            sharedFriends.add(friend);
                            RowItem item = new RowItem(R.drawable.xclose, friend);
                            rowItems1.add(item);
                            adapterShared.notifyDataSetChanged();
                            sharedOwnership.setVisibility(View.VISIBLE);
                            showSharedImg.setBackgroundResource(R.drawable.blackdown);
                            bar.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        sharedWithListView.setAdapter(adapterShared);
        sharedOwnership.setClickable(true);

        //handle click on shared playlists
        sharedOwnership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedList.getVisibility() == View.GONE) {
                    sharedList.setVisibility(View.VISIBLE);
                    showSharedImg.setBackgroundResource(R.drawable.uparrowshared);
                } else if (sharedList.getVisibility() == View.VISIBLE) {
                    sharedList.setVisibility(View.GONE);
                    showSharedImg.setBackgroundResource(R.drawable.blackdown);
                }
            }

        });

        //retrieve liked playlists
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
        invalidateOptionsMenu();
    }

    //check if already invited and invite if not
    private void checkIfInInvites(final String friendID, final String myname, final String friend) {
        UserDetails.notIn = false;
        DatabaseReference checkInvitesRef = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites");
        checkInvitesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sharedWithFriend;
                if (dataSnapshot.hasChild(friendID) && dataSnapshot.child(friendID).hasChild(myname)) {
                    for (DataSnapshot snapshot : dataSnapshot.child(friendID).child(UserDetails.myname).getChildren()) {
                        sharedWithFriend = snapshot.getKey();
                        if (dataSnapshot.child(friendID).child(UserDetails.myname).child(sharedWithFriend).getValue().equals(playlist)) {
                            Toast.makeText(PlaylistSongs.this, "Already shared the playlist '" + playlist + "' with " + friend + ".", Toast.LENGTH_SHORT).show();
                            UserDetails.notIn = false;
                        } else UserDetails.notIn = true;
                    }
                    if (UserDetails.notIn) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PlaylistSongs.this);
                        View mView = getLayoutInflater().inflate(R.layout.shareplaylist, null);
                        Button cancel = (Button) mView.findViewById(R.id.cancel);
                        final Button share = (Button) mView.findViewById(R.id.share);
                        final TextView name = (TextView) mView.findViewById(R.id.textName);
                        name.setText(friend + "?");
                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();
                        fab.setVisibility(View.GONE);
                        fab1.setVisibility(View.GONE);
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                final String friend = name.getText().toString();
                                final String friendName = friend.substring(0, friend.length() - 1);
                                DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
                                mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        UserDetails.myname = dataSnapshot.getValue().toString();
                                        getReceiver(friendName, toolbar.getTitle().toString());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                receiverRef = FirebaseDatabase.getInstance().getReference().child("Emails").child(friendName).child("Email");
                                receiverRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        UserDetails.receiver = dataSnapshot.getValue().toString();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ID").child(friendName).child("Id");
                                mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        UserDetails.fullname = dataSnapshot.getValue().toString();
                                        Firebase shareRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("PlaylistsInvites").child(UserDetails.fullname).child(UserDetails.myname);
                                        shareRef.push().setValue(getSupportActionBar().getTitle().toString());
                                        Firebase splaylists = new Firebase("https://tunein-633e5.firebaseio.com/").child("SharedPlaylists").child(ID).child(friendName);
                                        splaylists.push().setValue(getSupportActionBar().getTitle().toString());
                                        finish();
                                        startActivity(getIntent().putExtra("Name", playlist));
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });

                                dialog.hide();
                                fab.setVisibility(View.VISIBLE);
                                Toast.makeText(PlaylistSongs.this, friendName + " can now add songs to your playlist", Toast.LENGTH_SHORT).show();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fab.setVisibility(View.VISIBLE);
                                dialog.hide();
                            }
                        });
                    }
                } else {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(PlaylistSongs.this);
                    View mView = getLayoutInflater().inflate(R.layout.shareplaylist, null);
                    Button cancel = (Button) mView.findViewById(R.id.cancel);
                    final Button share = (Button) mView.findViewById(R.id.share);
                    final TextView name = (TextView) mView.findViewById(R.id.textName);
                    name.setText(friend + "?");
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    fab.setVisibility(View.GONE);
                    fab1.setVisibility(View.GONE);
                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final String friend = name.getText().toString();
                            final String friendName = friend.substring(0, friend.length() - 1);
                            DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
                            mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    UserDetails.myname = dataSnapshot.getValue().toString();
                                    getReceiver(friendName, toolbar.getTitle().toString());
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            receiverRef = FirebaseDatabase.getInstance().getReference().child("Emails").child(friendName).child("Email");
                            receiverRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    UserDetails.receiver = dataSnapshot.getValue().toString();
                                    //Toast.makeText(PlaylistSongs.this, UserDetails.receiver, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ID").child(friendName).child("Id");
                            mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    UserDetails.fullname = dataSnapshot.getValue().toString();
                                    // Toast.makeText(PlaylistSongs.this, "my id or friend id " + UserDetails.fullname , Toast.LENGTH_SHORT).show();
                                    Firebase shareRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("PlaylistsInvites").child(UserDetails.fullname).child(UserDetails.myname);
                                    shareRef.push().setValue(getSupportActionBar().getTitle().toString());

                                    Firebase splaylists = new Firebase("https://tunein-633e5.firebaseio.com/").child("SharedPlaylists").child(ID).child(friendName);
                                    splaylists.push().setValue(getSupportActionBar().getTitle().toString());
                                    finish();
                                    startActivity(getIntent().putExtra("Name", playlist));
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            dialog.hide();
                            fab.setVisibility(View.VISIBLE);
                            Toast.makeText(PlaylistSongs.this, friendName + " can now add songs to your playlist", Toast.LENGTH_SHORT).show();
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fab.setVisibility(View.VISIBLE);
                            dialog.hide();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //send notification
    private void sendNotification(String user, String playlist) {
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
                                + "\"contents\": {\"en\": \"" + UserDetails.myname + " shared a playlist with you. \"},"
                                + "\"buttons\":[{\"id\": \"id2\", \"text\": \"View\"}]"
                                + "}";

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

    //get receiver
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

    /**
     * Rename.
     *
     * @param newPlaylistName the new playlist name
     */
    public void rename(String newPlaylistName) {
        String oldPlaylist = getSupportActionBar().getTitle().toString();
        if (newPlaylistName.equals("")) {
            Toast.makeText(PlaylistSongs.this, "Please enter a name", Toast.LENGTH_SHORT).show();
        } else {

            changeInPlaylist(oldPlaylist, newPlaylistName);
            changeInLovedPlaylists(oldPlaylist, newPlaylistName);
            changeInPrivate(oldPlaylist, newPlaylistName);
            changeInPublic(oldPlaylist, newPlaylistName);
            changeInDownloaded(oldPlaylist, newPlaylistName);
            changeInPlaylistSongs(oldPlaylist, newPlaylistName);
            changeInInvites(oldPlaylist, newPlaylistName);
            changeInShared(oldPlaylist, newPlaylistName);
            setTitle(newPlaylistName);
        }
    }

    //change playlists in public node
    private void changeInPublic(final String oldPlaylist, final String newname) {
        final DatabaseReference ref11 = FirebaseDatabase.getInstance().getReference().child("PublicPlaylists").child(ID);
        ref11.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String playlistName2 = dataSnapshot.getValue(String.class);
                if (playlistName2.equals(oldPlaylist)) {
                    ref11.child(dataSnapshot.getKey().toString()).setValue(newname);
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

    //change playlist in shared node
    private void changeInShared(final String oldname, final String newname) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("SharedPlaylists");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ID).exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                        String friend = snapshot.getKey();

                        for (DataSnapshot snap : dataSnapshot.child(ID).child(friend).getChildren()) {
                            String key = snap.getKey();

                            String playlistSh = dataSnapshot.child(ID).child(friend).child(key).getValue().toString();
                            if (playlistSh.equals(oldname)) {

                                dataSnapshot.child(ID).child(friend).child(key).getRef().setValue(newname);
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

    //change playlist in invites node
    private void changeInInvites(final String oldname, final String newname) {
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

    //set toolbar title
    private void setTitle(String name) {
        getSupportActionBar().setTitle(name);

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

    //change in playlist songs
    private void changeInPlaylistSongs(final String oldname, final String newname) {
        ref3 = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs").child(ID);
        ref3.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String v;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            v = snapshot.getKey();
                            if (v.toString().equals(oldname)) {
                                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                                Firebase sRef = ref.child("PlaylistSongs").child(ID);
                                Map<String, Object> sinfo = new HashMap<String, Object>();
                                sinfo.put(newname, dataSnapshot.child(v.toString()).getValue());
                                sRef.updateChildren(sinfo);
                                ref3.child(oldname).removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    //change in downloaded
    private void changeInDownloaded(final String oldname, final String newname) {
        ref2 = FirebaseDatabase.getInstance().getReference().child("DownloadedPlaylists").child(ID);
        ref2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String playlistName3 = dataSnapshot.getValue(String.class);
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

    //change in private
    private void changeInPrivate(final String oldname, final String newname) {
        ref1 = FirebaseDatabase.getInstance().getReference().child("PrivatePlaylists").child(ID);
        ref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String playlistName2 = dataSnapshot.getValue(String.class);
                String oldPlaylist = getSupportActionBar().getTitle().toString();
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

    //change in liked playlists
    private void changeInLovedPlaylists(final String oldname, final String newname) {
        ref = FirebaseDatabase.getInstance().getReference().child("LovedPlaylists").child(ID);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String playlistName1 = dataSnapshot.getValue(String.class);
                String oldPlaylist = getSupportActionBar().getTitle().toString();
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

    //change in playlists
    private void changeInPlaylist(final String oldname, final String newname) {
        playlistRef = FirebaseDatabase.getInstance().getReference().child("Playlists").child(ID);
        playlistRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String playlistName = dataSnapshot.getValue(String.class);
                if (playlistName.equals(oldname)) {
                    playlistRef.child(dataSnapshot.getKey().toString()).setValue(newname);
                    Toast.makeText(PlaylistSongs.this, "Your playlist was renamed", Toast.LENGTH_SHORT).show();
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

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.playlistoptions, menu);
        this.menu = menu;
        MenuItem whiteHeart = menu.findItem(R.id.menu_like);
        MenuItem redHeart = menu.findItem(R.id.menu_dislike);
        MenuItem item = menu.findItem(R.id.menu_share);
        searchView.setMenuItem(item);

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

    //handle on menu open
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

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.menu_rename) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PlaylistSongs.this);
            View mView = getLayoutInflater().inflate(R.layout.renameplaylist, null);
            final EditText newname = (EditText) mView.findViewById(R.id.editText);
            Button cancel = (Button) mView.findViewById(R.id.cancel);
            Button rename = (Button) mView.findViewById(R.id.rename);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            fab.setVisibility(View.GONE);
            fab1.setVisibility(View.GONE);
            rename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rename(newname.getText().toString());
                    fab.setVisibility(View.VISIBLE);
                    dialog.hide();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fab.setVisibility(View.VISIBLE);
                    dialog.hide();
                }
            });

        } else if (id == R.id.menu_share) {

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

            searchLayout.setVisibility(View.VISIBLE);

        } else if (id == R.id.menu_private) {
            Toast.makeText(PlaylistSongs.this, "Your playlist is now private", Toast.LENGTH_SHORT).show();
            addToPrivate();
            deleteFromShared(playlist);
            deleteFromInvites(playlist);
            sharedOwnership.setVisibility(View.GONE);
            sharedList.setVisibility(View.GONE);
            bar.setVisibility(View.GONE);

        } else if (id == R.id.menu_public) {
            Toast.makeText(PlaylistSongs.this, "Your playlist is now public", Toast.LENGTH_SHORT).show();
            addToPublic();
            playlistRef = FirebaseDatabase.getInstance().getReference().child("PrivatePlaylists").child(ID);
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
            UserDetails.privatePlaylist = false;

        } else if (id == R.id.menu_like) {
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
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PlaylistSongs.this);
            View mView = getLayoutInflater().inflate(R.layout.deleteplaylist, null);
            Button cancel = (Button) mView.findViewById(R.id.cancel);
            final Button delete = (Button) mView.findViewById(R.id.delete);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            fab.setVisibility(View.GONE);
            fab1.setVisibility(View.GONE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String oldPlaylist = getSupportActionBar().getTitle().toString();
                    delete(oldPlaylist);
                    fab.setVisibility(View.VISIBLE);
                    dialog.hide();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fab.setVisibility(View.VISIBLE);
                    dialog.hide();
                }
            });
        } else onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    //add to public node
    private void addToPublic() {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("PublicPlaylists").child(ID);
        playRef.push().setValue(getSupportActionBar().getTitle().toString());
        UserDetails.privatePlaylist = true;
    }

    /**
     * Delete playlist
     *
     * @param oldPlaylist the old playlist
     */
    public void delete(String oldPlaylist) {
        Toast.makeText(PlaylistSongs.this, "Deleting playlist...", Toast.LENGTH_SHORT).show();
        deleteFromPlaylist(oldPlaylist);
        deleteFromLovedPlaylists(oldPlaylist);
        deleteFPrivate(oldPlaylist);
        deleteFPublic(oldPlaylist);
        deleteFromDownloaded(oldPlaylist);
        deleteFromPlaylistSongs(oldPlaylist);
        deleteFromInvites(oldPlaylist);
        deleteFromShared(oldPlaylist);
        Intent i = new Intent(PlaylistSongs.this, MyPlaylists.class);
        startActivity(i);
    }

    //delete from shared node
    private void deleteFromShared(final String oldPlaylist) {
        delSharedPlRef = FirebaseDatabase.getInstance().getReference().child("SharedPlaylists").child(ID);
        delSharedPlRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String friend = snapshot.getKey();

                    for (DataSnapshot snap : dataSnapshot.child(friend).getChildren()) {
                        String key1 = snap.getKey();
                        if (dataSnapshot.child(friend).child(key1).getValue().equals(oldPlaylist)) {

                            dataSnapshot.child(friend).child(key1).getRef().removeValue();
                            Toast.makeText(PlaylistSongs.this, "Your playlist was deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //delete from invites
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

    //delete from playlists song
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

    //delete from downloaded
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

    //delete from playlists
    private void deleteFromPlaylist(final String oldname) {

        delPlRef = FirebaseDatabase.getInstance().getReference().child("Playlists").child(ID);
        delPlRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String playlistName = dataSnapshot.getValue(String.class);
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

    //delete from public
    private void deleteFPublic(final String name) {
        final DatabaseReference delPriPlRef1 = FirebaseDatabase.getInstance().getReference().child("PublicPlaylists").child(ID);
        delPriPlRef1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String playlistName = dataSnapshot.getValue(String.class);
                if (playlistName.equals(name)) {
                    delPriPlRef1.child(dataSnapshot.getKey().toString()).removeValue();
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

    //delete from private
    private void deleteFPrivate(final String name) {
        delPriPlRef = FirebaseDatabase.getInstance().getReference().child("PrivatePlaylists").child(ID);
        delPriPlRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName = dataSnapshot.getValue(String.class);
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

    //delete from liked playlists
    private void deleteFromLovedPlaylists(final String oldname) {
        delLovedPlRef = FirebaseDatabase.getInstance().getReference().child("LovedPlaylists").child(ID);
        delLovedPlRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String playlistName = dataSnapshot.getValue(String.class);
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

    //add to private
    private void addToPrivate() {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("PrivatePlaylists").child(ID);
        playRef.push().setValue(getSupportActionBar().getTitle().toString());
        UserDetails.privatePlaylist = true;

    }

    //add to likes
    private void addToLikedPlaylists() {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("LovedPlaylists").child(ID);
        playRef.push().setValue(getSupportActionBar().getTitle().toString());
    }

    //handle click on back arrow
    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra("Uniqid")) {
            if (getIntent().getStringExtra("Uniqid").equals("favplaylists")) {
                Intent backMainTest = new Intent(this, FavouritePlaylists.class);
                if (mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", track_title.getText().toString());
                }
                startActivity(backMainTest);
                finish();
            } else {
                Intent backMainTest = new Intent(this, MyPlaylists.class);
                if (mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", track_title.getText().toString());
                }
                startActivity(backMainTest);
                finish();
            }
        } else {
            Intent backMainTest = new Intent(this, MyPlaylists.class);
            if (mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", track_title.getText().toString());
            }
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
        Intent i = new Intent(PlaylistSongs.this, MusicPlayerActivity.class);
        i.putExtra("OldPlaylist", getSupportActionBar().getTitle().toString());
        startActivity(i);
    }

    /**
     * Hide soft keyboard.
     *
     * @param activity the activity
     */
    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);

    }

    //method called before opening menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem privateOption = menu.findItem(R.id.menu_private);
        MenuItem publicOption = menu.findItem(R.id.menu_public);
        if (UserDetails.privatePlaylist) {
            privateOption.setVisible(false);
            publicOption.setVisible(true);
        } else {
            privateOption.setVisible(true);
            publicOption.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Get bar title string.
     *
     * @return the string
     */
    public String getBarTitle() {
        return getSupportActionBar().getTitle().toString();
    }

    //start song
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
                    //myFollowers.clear();
                    String value = snapshot.getKey();
                    myFollowers.add(value);
                    UserDetails.myFollowers.add(value);
                    //addToFirebaseHome(value, mysong);
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
     * Gets my fullname.
     *
     * @param id the id
     * @return the my fullname
     */
    public String getMyFullname(String id) {
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
        return UserDetails.fullname;
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
            if (!RealTimeActivity.checkBox.isChecked()) {
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
            if (!RealTimeActivity.checkBox.isChecked()) {
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


