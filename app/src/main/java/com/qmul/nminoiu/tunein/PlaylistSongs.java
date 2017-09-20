package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.firebase.client.FirebaseError;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaylistSongs extends AppCompatActivity {

    private DatabaseReference db;
    private ListView ulistView;
    private ArrayAdapter<String> uadapter;


    MaterialSearchView searchView;
    LinearLayout searchLayout;
    LinearLayout songsLayout;
    LinearLayout shareOwnership;
    LinearLayout deletePlaylist;



    private ListView songs;
    private TextView name;

    public List<String> songsList;
    private ArrayList<String> users = new ArrayList<>();
    private ArrayAdapter<String> songssadapter;
    private DatabaseReference songsRef;
    private DatabaseReference shareRef;
    private DatabaseReference dwnRef;
    private String ID;
    private FirebaseAuth firebaseAuth;
    private List<RowItem> rowItems;
    private SongsAdapter adapter;
    private ImageView img;
    private LinearLayout renameLayout;
    private Button cancel;
    private Button cancelShare;
    private Button share;
    private Button delete;
    private Button cancelDelete;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlistsongs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlaylistSongs.this, Users.class);
                startActivity(i);
            }
        });

        mStorage = FirebaseStorage.getInstance();

        db = FirebaseDatabase.getInstance().getReference().child("Users");


        songs = (ListView) findViewById(R.id.songsList);
        songs.bringToFront();
        img = (ImageView) findViewById(R.id.icon);
        rowItems = new ArrayList<RowItem>();
        adapter = new SongsAdapter(this, rowItems);
        renameLayout = (LinearLayout) findViewById(R.id.renamePlaylist);
        deletePlaylist = (LinearLayout) findViewById(R.id.deletePlaylist);
        delete = (Button) findViewById(R.id.delete);
        cancelDelete = (Button) findViewById(R.id.cancelDelete);

        cancel = (Button) findViewById(R.id.cancel);
        rename = (Button) findViewById(R.id.rename);
        cancelShare = (Button) findViewById(R.id.cancelShare);
        share = (Button) findViewById(R.id.share);
        newName = (EditText) findViewById(R.id.editText);



        playlistName = (EditText) findViewById(R.id.editText);
        renameLayout.bringToFront();

        ulistView = (ListView) findViewById(R.id.friendsList);
        uadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        ulistView.setAdapter(uadapter);


        Intent intent = getIntent();
        playlist = intent.getStringExtra("Name");

        getSupportActionBar().setTitle(playlist);

        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();

        songsList = new ArrayList<>();
        songssadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songsList);

        songsRef = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs").child(ID).child(playlist);
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
//                String song = dataSnapshot.getValue(String.class);
//                songsList.remove(song);
//                rowItems.remove(new RowItem(R.drawable.options,song));
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        songs.setAdapter(adapter);

        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        songsLayout = (LinearLayout) findViewById(R.id.songsLayout);
        shareOwnership = (LinearLayout) findViewById(R.id.shareOwnership);
        name = (TextView) findViewById(R.id.textName);


        searchView = (MaterialSearchView) findViewById(R.id.search_view);

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

        ulistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String friend = ((TextView) view).getText().toString();
                shareOwnership.setVisibility(View.VISIBLE);
                shareOwnership.bringToFront();
                name.setText(friend + "?");

            }
        });


        shareRef = FirebaseDatabase.getInstance().getReference().child("PrivatePlaylists").child(ID);
        shareRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName = dataSnapshot.getValue(String.class);

                if (playlistName.equals(getSupportActionBar().getTitle().toString())) {
                    UserDetails.privatePlaylist = true;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String playlistName = dataSnapshot.getValue(String.class);

                if (playlistName.equals(getSupportActionBar().getTitle().toString())) {
                    UserDetails.privatePlaylist = false;
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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



        //share popup window
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String friend = name.getText().toString();
                String friendName = friend.substring(0, friend.length() - 1);

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


                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ID").child(friendName).child("Id");

                mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserDetails.fullname = dataSnapshot.getValue().toString();

                        Firebase shareRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("PlaylistsInvites").child(UserDetails.fullname).child(UserDetails.myname);
                        shareRef.push().setValue(getSupportActionBar().getTitle().toString());

                        Firebase splaylists = new Firebase("https://tunein-633e5.firebaseio.com/").child("SharedPlaylists").child(ID);
                        splaylists.push().setValue(getSupportActionBar().getTitle().toString());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                shareOwnership.setVisibility(View.GONE);
                Toast.makeText(PlaylistSongs.this, friendName + " can now add songs to your playlist", Toast.LENGTH_SHORT).show();


            }
        });

        cancelShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareOwnership.setVisibility(View.GONE);
            }
        });

        cancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePlaylist.setVisibility(View.GONE);
            }
        });


//        rename.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                newname = playlistName.getText().toString().trim();
//
//                if (newname.equals("")) {
//                    Toast.makeText(PlaylistSongs.this, "Please enter a name", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(PlaylistSongs.this, "rename clicked", Toast.LENGTH_LONG).show();
//                    Toast.makeText(PlaylistSongs.this, "text is " + newname, Toast.LENGTH_LONG).show();
//
//
//                    changeInPlaylist();
//                    changeInLovedPlaylists();
//                    changeInPrivate();
//                    changeInDownloaded();
//                    changeInPlaylistSongs();
//                    renameLayout.setVisibility(View.GONE);
//
//                }
//            }
//
//        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameLayout.setVisibility(View.GONE);
                hideSoftKeyboard(PlaylistSongs.this);
            }
        });

        renameLayout.postInvalidate();

        invalidateOptionsMenu();
    }


    public void rename(View view){
        newname = playlistName.getText().toString().trim();
        String oldPlaylist = getSupportActionBar().getTitle().toString();


        if (newname.equals("")) {
            Toast.makeText(PlaylistSongs.this, "Please enter a name", Toast.LENGTH_LONG).show();
        } else {

            changeInPlaylist(oldPlaylist);
            changeInLovedPlaylists(oldPlaylist);
            changeInPrivate(oldPlaylist);
            changeInDownloaded(oldPlaylist);
            changeInPlaylistSongs(oldPlaylist);
            changeInInvites(oldPlaylist);
            changeInShared(oldPlaylist);
            renameLayout.setVisibility(View.GONE);
            hideSoftKeyboard(PlaylistSongs.this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_dwn) {

            addToDownloads();
            download();
            Toast.makeText(PlaylistSongs.this, "Downloading... ", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.menu_remdwn) {

            //Toast.makeText(PlaylistSongs.this, "Already downloaded ", Toast.LENGTH_SHORT).show();


            playlistRef = FirebaseDatabase.getInstance().getReference().child("DownloadedPlaylists").child(ID);
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
            Toast.makeText(PlaylistSongs.this, "Removing... ", Toast.LENGTH_SHORT).show();


        } else if (id == R.id.menu_rename) {
            renameLayout.setVisibility(View.VISIBLE);
            newName.setText("");
            fab.setVisibility(View.GONE);
            rename.setClickable(true);
            newName.requestFocus();
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        } else if (id == R.id.menu_share) {

            searchLayout.setVisibility(View.VISIBLE);

        } else if (id == R.id.menu_private) {

            Toast.makeText(PlaylistSongs.this, "Your playlist is now private", Toast.LENGTH_SHORT).show();
            addToPrivate();

        } else if (id == R.id.menu_public) {

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

            deletePlaylist.setVisibility(View.VISIBLE);
            deletePlaylist.bringToFront();
            fab.setVisibility(View.GONE);

        } else onBackPressed();


        return super.onOptionsItemSelected(item);
    }

    public void delete(View view){

        Toast.makeText(PlaylistSongs.this, "Deleting playlist...", Toast.LENGTH_SHORT).show();
        String oldPlaylist = getSupportActionBar().getTitle().toString();
        deleteFromPlaylist(oldPlaylist);
        deleteFromLovedPlaylists(oldPlaylist);
        deleteFPrivate(oldPlaylist);
        deleteFromDownloaded(oldPlaylist);
        deleteFromPlaylistSongs(oldPlaylist);
        deleteFromInvites(oldPlaylist);
        deleteFromShared(oldPlaylist);
        deletePlaylist.setVisibility(View.GONE);
        Intent i = new Intent(PlaylistSongs.this, MyPlaylists.class);
        startActivity(i);
    }


    private void deleteFromShared(final String oldPlaylist) {

        delSharedPlRef = FirebaseDatabase.getInstance().getReference().child("SharedPlaylists").child(ID);
        delSharedPlRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    if (dataSnapshot.child(key).getValue().equals(oldPlaylist)) {
                        dataSnapshot.child(key).getRef().removeValue();
                        Toast.makeText(PlaylistSongs.this, "Your playlist was deleted", Toast.LENGTH_SHORT).show();

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
    }

    private void addToLikedPlaylists() {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("LovedPlaylists").child(ID);
        playRef.push().setValue(getSupportActionBar().getTitle().toString());
    }


    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, MyPlaylists.class);
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

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem privateOption = menu.findItem(R.id.menu_private);
        MenuItem publicOption = menu.findItem(R.id.menu_public);
        MenuItem dwnOption = menu.findItem(R.id.menu_dwn);
        MenuItem remDwnOption = menu.findItem(R.id.menu_remdwn);

        if (UserDetails.privatePlaylist) {
            privateOption.setVisible(false);
            publicOption.setVisible(true);
        } else {
            privateOption.setVisible(true);
            publicOption.setVisible(false);
        }
        if (UserDetails.dwnPlaylist) {
            dwnOption.setVisible(false);
            remDwnOption.setVisible(true);
        } else {
            dwnOption.setVisible(true);
            remDwnOption.setVisible(false);
        }

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


}


