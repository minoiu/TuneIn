package com.qmul.nminoiu.tunein;

import android.app.Activity;
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
import java.util.List;

public class PlaylistSongs extends AppCompatActivity {

    private DatabaseReference db;
    private ListView ulistView;
    private ArrayAdapter<String> uadapter;


    MaterialSearchView searchView;
    LinearLayout searchLayout;
    LinearLayout songsLayout;
    LinearLayout shareOwnership;


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
    private Button rename;
    private EditText playlistName;
    private DatabaseReference playlistRef;
    private DatabaseReference lovedPlaylistRef;
    private DatabaseReference sharedRef;
    public File storagePath;
    private FirebaseStorage mStorage;




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
        img = (ImageView) findViewById(R.id.icon);
        rowItems = new ArrayList<RowItem>();
        adapter = new SongsAdapter(this, rowItems);
        renameLayout = (LinearLayout) findViewById(R.id.renamePlaylist);
        cancel = (Button) findViewById(R.id.cancel);
        rename = (Button) findViewById(R.id.rename);
        cancelShare = (Button) findViewById(R.id.cancelShare);
        share = (Button) findViewById(R.id.share);


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

                    if (playlistName.equals(playlist)) {
                        UserDetails.privatePlaylist = true;
                    }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String playlistName = dataSnapshot.getValue(String.class);

                if (playlistName.equals(playlist)) {
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

                if (playlistName.equals(playlist)) {
                    UserDetails.dwnPlaylist = true;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String playlistName = dataSnapshot.getValue(String.class);

                if (playlistName.equals(playlist)) {
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

                Toast.makeText(PlaylistSongs.this, "playlists name " + getSupportActionBar().getTitle().toString(), Toast.LENGTH_SHORT).show();

                if (playlistName.equals(getSupportActionBar().getTitle().toString())) {
                    UserDetails.lovedPlaylist = true;
                    Toast.makeText(PlaylistSongs.this, "lovedPlaylists is " + UserDetails.lovedPlaylist, Toast.LENGTH_SHORT).show();
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
                String friendName = friend.substring(0, friend.length()-1);

                DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

                mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserDetails.myname = dataSnapshot.getValue().toString();
                        Toast.makeText(PlaylistSongs.this, UserDetails.myname + " is my name", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(PlaylistSongs.this, UserDetails.fullname + " is friend id", Toast.LENGTH_SHORT).show();

                        Firebase shareRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("PlaylistsInvites").child(UserDetails.fullname).child(UserDetails.myname);
                        shareRef.push().setValue(playlist);
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


        if(UserDetails.lovedPlaylist) {
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
    public boolean onMenuOpened(int featureId, Menu menu)
    {
        if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){
                }
                catch(Exception e){
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

        } else if(id == R.id.menu_remdwn){

            playlistRef = FirebaseDatabase.getInstance().getReference().child("DownloadedPlaylists").child(ID);
            playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();
                        Toast.makeText(getApplicationContext(), "the key: " + key, Toast.LENGTH_SHORT).show();


                        if (dataSnapshot.child(key).getValue().equals(playlist)) {

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
            fab.setVisibility(View.GONE);
            rename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String newname = playlistName.getText().toString().trim();

                    if (newname.equals("")) {
                        Toast.makeText(PlaylistSongs.this, "Please enter a name", Toast.LENGTH_LONG).show();
                    } else {
                        playlistRef = FirebaseDatabase.getInstance().getReference().child("Playlists").child(ID);
                        playlistRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                String playlistName = dataSnapshot.getValue(String.class);
                                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                                if (playlistName.equals(playlist)) {
                                    playlistRef.child(dataSnapshot.getKey().toString()).setValue(newname);
                                    Toast.makeText(PlaylistSongs.this, "Your playlist was renamed", Toast.LENGTH_SHORT).show();
                                    getSupportActionBar().setTitle(newname);
                                    renameLayout.setVisibility(View.GONE);
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

                        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("LovedPlaylists").child(ID);
                        ref.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                String playlistName1 = dataSnapshot.getValue(String.class);
                                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                                if (playlistName1.equals(playlist)) {
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

                        final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("PrivatePlaylists").child(ID);
                        ref1.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                String playlistName2 = dataSnapshot.getValue(String.class);
                                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                                if (playlistName2.equals(playlist)) {
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

                        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("DownloadedPlaylists").child(ID);
                        ref2.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                String playlistName3 = dataSnapshot.getValue(String.class);
                                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                                if (playlistName3.equals(playlist)) {
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

                        final DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs").child(ID);
                        ref3.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                String playlistName4 = dataSnapshot.getValue(String.class);
                                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                                if (playlistName4.equals(playlist)) {
                                    ref3.child(dataSnapshot.getKey().toString()).setValue(newname);
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
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    renameLayout.setVisibility(View.GONE);
                }
            });

        } else if (id == R.id.menu_share) {

            searchLayout.setVisibility(View.VISIBLE);
            Toast.makeText(PlaylistSongs.this, "Clicked on share ", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(getApplicationContext(), "the key: " + key, Toast.LENGTH_SHORT).show();


                        if (dataSnapshot.child(key).getValue().equals(playlist)) {

                            dataSnapshot.child(key).getRef().removeValue();
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
//

        } else if (id == R.id.menu_like) {

            addToLikedPlaylists();

        } else if ( id == R.id.menu_dislike){

            playlistRef = FirebaseDatabase.getInstance().getReference().child("LovedPlaylists").child(ID);
            playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();
                        Toast.makeText(getApplicationContext(), "the key: " + key, Toast.LENGTH_SHORT).show();


                        if (dataSnapshot.child(key).getValue().equals(playlist)) {

                            dataSnapshot.child(key).getRef().removeValue();
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        } else if (id == R.id.menu_delete) {

            playlistRef = FirebaseDatabase.getInstance().getReference().child("Playlists").child(ID);
            playlistRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        String playlistName = dataSnapshot.getValue(String.class);
                        //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                        if (playlistName.equals(playlist)) {
                            playlistRef.child(dataSnapshot.getKey().toString()).removeValue();
                            Toast.makeText(PlaylistSongs.this, "Your playlist was deleted", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(PlaylistSongs.this, MyPlaylists.class);
                            startActivity(i);
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

            } else onBackPressed();


        return super.onOptionsItemSelected(item);
        }

    private void download() {

        for (int i = 0; i <= songsList.size()-1; i++) {
            String songToDwn = songsList.get(i);
            Toast.makeText(PlaylistSongs.this, "Your songs:" + songToDwn, Toast.LENGTH_SHORT).show();


            StorageReference storageReference = mStorage.getReferenceFromUrl("gs://tunein-633e5.appspot.com/bad boi muzik");
            StorageReference down = storageReference.child(songToDwn + ".mp3");

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
                    Toast.makeText(getApplicationContext(), "Downloded at location: " + finalLocalFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
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

//        Toast.makeText(getApplicationContext(), "Downloded at location: " + UserDetails.song , Toast.LENGTH_SHORT).show();

        }


    private void addToDownloads() {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("DownloadedPlaylists").child(ID);
        playRef.push().setValue(playlist);
    }

    private void deleteFromPrivate() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Playlists").child(ID);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlistName = dataSnapshot.getValue(String.class);
                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                if (playlistName.equals(playlist)) {
                    playlistRef.child(dataSnapshot.getKey().toString()).removeValue();
                    Toast.makeText(PlaylistSongs.this, "Your playlist was deleted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PlaylistSongs.this, MyPlaylists.class);
                    startActivity(i);
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
        playRef.push().setValue(playlist);
    }

    private void addToLikedPlaylists() {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("LovedPlaylists").child(ID);
        playRef.push().setValue(playlist);
    }


    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, MyPlaylists.class);
        startActivity(backMainTest);
        finish();
    }

    public void showMenu(ImageView img){
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

        if(UserDetails.privatePlaylist) {
            privateOption.setVisible(false);
            publicOption.setVisible(true);
        } else {
            privateOption.setVisible(true);
            publicOption.setVisible(false);
        }
        if(UserDetails.dwnPlaylist){
            dwnOption.setVisible(false);
            remDwnOption.setVisible(true);
        } else{
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
                Toast.makeText(PlaylistSongs.this, UserDetails.fullname + " is my fullname", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}


