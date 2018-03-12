package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

public class PlaylistsActivity extends AppCompatActivity {

    private String song;
    private String songToAdd;
    private ListView playlists;
    private List<String> playlistsList;
    private List<String> mysongs;
    private List<String> mysongs1;

    private List<String> sharedlist;
    private List<String> sharedlist1;



    private ArrayAdapter<String> playlistsadapter;
    private ListView sharedPlaylists;
    private List<String> sharedPlaylistsList;
    private ArrayAdapter<String> sharedPlaylistsadapter;
    private List<String> friends;
    private List<String> friendsIDs;
    private List<String> songsInPlaylist;
    private List<String> sharedsongsInPlaylist;
    private String ID;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference playlistsRef;
    private String playlist;
    private LinearLayout newPlaylist;
    private RelativeLayout sharedWithMeLayout;
    private EditText playlistName;
    private Button create;
    private Button cancel;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

//        newPlaylist = (LinearLayout) findViewById(R.id.createPlaylist);
        sharedWithMeLayout = (RelativeLayout) findViewById(R.id.sharedWithMeLayout);


//        .bringToFront();
        playlistName = (EditText) findViewById(R.id.editText);
        create = (Button) findViewById(R.id.create);
        cancel = (Button) findViewById(R.id.cancel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();
        UserDetails.hasSong = false;
        UserDetails.hasSharedSong = false;

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlaylistsActivity.this, Users.class);
                startActivity(i);
            }
        });

        song = getIntent().getExtras().getString("Song");


        sharedPlaylists = (ListView) findViewById(R.id.listSharedWithMe);
        sharedPlaylistsList = new ArrayList<>();
        mysongs = new ArrayList<>();
        mysongs1 = new ArrayList<>();


        songsInPlaylist = new ArrayList<>();
        sharedlist = new ArrayList<>();
        sharedlist1 = new ArrayList<>();
        sharedsongsInPlaylist = new ArrayList<>();



        sharedPlaylistsadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sharedPlaylistsList);

        playlists = (ListView) findViewById(R.id.playlistsList);
        playlistsList = new ArrayList<>();
        playlistsadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlistsList);

        friends = new ArrayList<>();
        friendsIDs = new ArrayList<>();

        playlistsRef = FirebaseDatabase.getInstance().getReference().child("Playlists").child(ID);
        playlistsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlist = dataSnapshot.getValue(String.class);
                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                playlistsList.add(playlist);
                playlistsadapter.notifyDataSetChanged();
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
        playlists.setAdapter(playlistsadapter);


        DatabaseReference sharedWithMe = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites");

        sharedWithMe.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ID).exists()) {
                    sharedWithMeLayout.setVisibility(View.VISIBLE);
                    //Toast.makeText(MyPlaylists.this, "i'm in", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                        String friend = snapshot.getKey();
                        friends.add(friend);
                        getIDForFriends(friends);
                        for (DataSnapshot snap : dataSnapshot.child(ID).child(friend).getChildren()) {
                            String key = snap.getKey();
                            String playlist = dataSnapshot.child(ID).child(friend).child(key).getValue().toString();
        //
                            //Toast.makeText(PlaylistsActivity.this, "playlists " + playlist, Toast.LENGTH_SHORT).show();
                            sharedPlaylistsList.add(playlist);
                            sharedPlaylistsadapter.notifyDataSetChanged();
                                                                            }
                                                                        }
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });
        sharedPlaylists.setAdapter(sharedPlaylistsadapter);


//        DatabaseReference playlistsSharedRef = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites").child(ID);
//        playlistsSharedRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    String friend = dataSnapshot.getKey().toString();
//                    for(DataSnapshot snapshot1 : dataSnapshot.child(friend).getChildren()){
//                        String key = snapshot1.getKey().toString();
//                        String sharedPlaylists = dataSnapshot.child(friend).child(key).getValue().toString();
//                       //add all shared playlists in an array list
//                        Toast.makeText(PlaylistsActivity.this, " shared playlists to add "+sharedPlaylists, Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//                //String playlist = dataSnapshot.getValue(String.class);
//                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();
//
////                playlistsList.add(playlist);
////                playlistsadapter.notifyDataSetChanged();
//            }
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


        playlists.setClickable(true);
        playlists.setAdapter(playlistsadapter);
        sharedPlaylists.setClickable(true);
        sharedPlaylists.setAdapter(sharedPlaylistsadapter);

        playlists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String playlistClicked = ((TextView) view).getText().toString();

                Intent i = getIntent();
                if (i != null) {
                    if (i.hasExtra("Uniqid")) {
                        String uniqid = i.getStringExtra("Uniqid");
                        if (uniqid.equals("FSAdapter")) {
                            String song = i.getStringExtra("Song");
                            String oldPlaylist = i.getStringExtra("OldPlaylist");
                            UserDetails.playlist = oldPlaylist;
                            addSharedSongsToList(song, playlistClicked, oldPlaylist);
                        } else if(uniqid.equals("FromNowPlayling")){
                            String song = i.getStringExtra("Song");
                            addSharedSongsToList(song, playlistClicked, "");
                        } else if(uniqid.equals("FromRecents")){
                            Toast.makeText(PlaylistsActivity.this, "normal pl", Toast.LENGTH_SHORT).show();
                            String song = i.getStringExtra("Song");
                            addSharedSongsToList(song, playlistClicked, "");
                        } else if(uniqid.equals("AdapterAllSongs")) {
                            String song = i.getStringExtra("Song");
                            addSharedSongsToList(song, playlistClicked, "");
                        }
                    } else{
                        String song = i.getStringExtra("Song");
                        addSharedSongsToList(song, playlistClicked, "");
                    }
                }
            }
        });

        sharedPlaylists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String playlistClicked = ((TextView) view).getText().toString();

                Intent i = getIntent();
                if (i != null) {
                    if (i.hasExtra("Uniqid")) {
                        String uniqid = i.getStringExtra("Uniqid");
                        if (uniqid.equals("FSAdapter")) {
                            String song = i.getStringExtra("Song");
                            String oldPlaylist = i.getStringExtra("OldPlaylist");
                            addSongsToSharedList(song,playlistClicked,oldPlaylist);
                        } else if(uniqid.equals("FromNowPlayling")){
                            String song = i.getStringExtra("Song");
                            addSongsToSharedList(song,playlistClicked,"");
                        } else if(uniqid.equals("FromRecents")){
                            Toast.makeText(PlaylistsActivity.this, "shared pl", Toast.LENGTH_SHORT).show();

                            String song = i.getStringExtra("Song");
                            addSongsToSharedList(song,playlistClicked,"");
                        } else if(uniqid.equals("AdapterAllSongs")) {
                            String song = i.getStringExtra("Song");
                            addSongsToSharedList(song, playlistClicked, "");
                        }
                    } else {
                        String song = i.getStringExtra("Song");
                        addSongsToSharedList(song, playlistClicked, "");
                    }
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlaylistsActivity.this, Users.class);
                i.putExtra("Uniqid","FromPlaylistActivity");
                startActivity(i);
            }
        });
    }

    private void addSongsToSharedList(final String song, final String playlist, final String oldPlaylist) {
        sharedlist.clear();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (int i = 0; i <= friendsIDs.size() - 1; i++) {
                    if (dataSnapshot.hasChild(friendsIDs.get(i)) && dataSnapshot.child(friendsIDs.get(i)).hasChild(playlist)) {
                        for (DataSnapshot snapshot : dataSnapshot.child(friendsIDs.get(i)).child(playlist).getChildren()) {
                            String key = snapshot.getKey().toString();
                            String song = dataSnapshot.child(friendsIDs.get(i)).child(playlist).child(key).getValue().toString();
                            sharedlist.add(song);
                            UserDetails.friendID = friendsIDs.get(i);

                            //from here
//                            if (dataSnapshot.child(friendsIDs.get(i)).child(playlist).child(key).getValue().toString().equals(song)) {
//                                Toast.makeText(PlaylistsActivity.this, song + " is already in this playlist", Toast.LENGTH_SHORT).show();
//                                UserDetails.hasSharedSong = true;
//                            } else {
//                                UserDetails.hasSharedSong = false;
//                            }
//                        }
//                        if (!UserDetails.hasSharedSong) {
//                            addSongToShared(playlist, song, friendsIDs.get(i), oldPlaylist);
//
//                        }
//                    } else addSongToShared(playlist, song, friendsIDs.get(i), oldPlaylist);
                        }
                    }
                }
                checkexistence(song, playlist, UserDetails.friendID, oldPlaylist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ////
//        sharedlist.clear();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs");
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot snap : dataSnapshot.child(ID).child(playlist).getChildren()){
//                    String key = snap.getKey().toString();
//                    sharedlist.add(dataSnapshot.child(ID).child(playlist).child(key).getValue().toString());
//                }
//                checkexistence(song, playlist, oldPlaylist);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    private void checkexistence(String song, String playlist, String friendID, String oldPlaylist) {
        if(sharedlist.contains(song)){
            Toast.makeText(PlaylistsActivity.this, song + " is already in this playlist", Toast.LENGTH_SHORT).show();
        } else addSongToFriendPlaylist(playlist, song, friendID, oldPlaylist);
    }

    private void addSongToFriendPlaylist(String playlist, String song, String friendID, String oldPlaylist) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase songRef = ref.child("PlaylistSongs").child(friendID).child(playlist);
        songRef.push().setValue(song);
        addMySongsToList(song, playlist, oldPlaylist);
        Toast.makeText(PlaylistsActivity.this, song + " was added to your playlist", Toast.LENGTH_SHORT).show();
    }

    private void addMySongsToList(final String song, final String playlist, final String oldPlaylist) {
        mysongs1.clear();
        DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
        songref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(ID)) {
                    for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                        String key = snapshot.getKey();
                        String songInMySongs = dataSnapshot.child(ID).child(key).getValue().toString();
                        mysongs1.add(songInMySongs);
                    }
                }
                checkSongInMySongs1(song, playlist, oldPlaylist);
            }


//                        if (!dataSnapshot.child(ID).child(key).getValue().equals(song)) {
//                            Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
//                            Firebase playRef = ref.child("MySongs").child(ID);
//                            playRef.push().setValue(song);
//                        }
//                    }
//                } else{
//                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
//                    Firebase playRef = ref.child("MySongs").child(ID);
//                    playRef.push().setValue(song);                }
//            }
//
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }

    private void checkSongInMySongs1(String song, String playlist, String oldPlaylist) {
        if(!mysongs1.contains(song)){
            Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
            Firebase playRef = ref.child("MySongs").child(ID);
            playRef.push().setValue(song);

            if(UserDetails.oldIntent.equals("Followers")){
                Intent backMainTest = new Intent(this, PlaylistSongs.class);
                if(mediaPlayer.isPlaying()) {
                    getIntent().getStringExtra("Song");
                    backMainTest.putExtra("Song", PlaylistSongs.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }else if(UserDetails.oldIntent.equals("MySongs")){
                Intent backMainTest = new Intent(this, Songs.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", Songs.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }
            else if(UserDetails.oldIntent.equals("Downloads")){
                Intent backMainTest = new Intent(this, Downloads.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", Downloads.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }
            else if(UserDetails.oldIntent.equals("Favourites")){
                Intent backMainTest = new Intent(this, Favourites.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", Favourites.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }
            else if(UserDetails.oldIntent.equals("Recents")){
                Intent backMainTest = new Intent(this, LibraryActivity.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", LibraryActivity.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }
        } else {
            if(UserDetails.oldIntent.equals("Followers")){
                Intent backMainTest = new Intent(this, PlaylistSongs.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", PlaylistSongs.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }else if(UserDetails.oldIntent.equals("MySongs")){
                Intent backMainTest = new Intent(this, Songs.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", Songs.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }
            else if(UserDetails.oldIntent.equals("Downloads")){
                Intent backMainTest = new Intent(this, Downloads.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", Downloads.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }
            else if(UserDetails.oldIntent.equals("Favourites")){
                Intent backMainTest = new Intent(this, Favourites.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", Favourites.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }
            else if(UserDetails.oldIntent.equals("Recents")){
                Intent backMainTest = new Intent(this, LibraryActivity.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", LibraryActivity.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }
        }
    }


    private void addSharedSongsToList(final String song, final String playlistClicked, final String oldPlaylist) {
        sharedsongsInPlaylist.clear();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.child(ID).child(playlistClicked).getChildren()){
                    String key = snap.getKey().toString();
                    sharedsongsInPlaylist.add(dataSnapshot.child(ID).child(playlistClicked).child(key).getValue().toString());
                }
                chckifharedsongisin(song, playlistClicked, oldPlaylist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void chckifharedsongisin(String song, String playlistClicked, String oldPlaylist) {
        if(sharedsongsInPlaylist.contains(song)){
            Toast.makeText(PlaylistsActivity.this, song + " is already in this playlist", Toast.LENGTH_SHORT).show();
        } else addToPlaylist(playlistClicked, song, oldPlaylist);
    }

    private void addSongstoList(final String song, final String playlistClicked) {
        songsInPlaylist.clear();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.child(ID).child(playlistClicked).getChildren()){
                    String key = snap.getKey().toString();
                    songsInPlaylist.add(dataSnapshot.child(ID).child(playlistClicked).child(key).getValue().toString());
                }
                checkifsongisin(song, playlistClicked);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void checkSharedSongIn(final String song, final String playlist) {

        UserDetails.hasSharedSong = false;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (int i = 0; i <= friendsIDs.size() - 1; i++) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey().toString();
                        if (dataSnapshot.child(friendsIDs.get(i)).child(playlist).child(key).getValue().toString().equals(song)) {
                            Toast.makeText(PlaylistsActivity.this, song + " is already in this playlist", Toast.LENGTH_SHORT).show();
                            UserDetails.hasSharedSong = true;
                        } else {
                            UserDetails.hasSharedSong = false;
                        }
                    }
                    if (!UserDetails.hasSharedSong) {
                        addSong(playlist, song);

                    } else addSong(playlist, song);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getIDForFriends(List<String> friends) {
        for (int i = 0; i <= friends.size() - 1; i++) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ID").child(friends.get(i)).child("Id");

            mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final String friendID = dataSnapshot.getValue().toString();
                        friendsIDs.add(friendID);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void checkSharedHasSong(final String song, final String playlist, final String oldPlaylist) {

        UserDetails.hasSharedSong = false;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (int i = 0; i <= friendsIDs.size() - 1; i++) {
                    if (dataSnapshot.hasChild(friendsIDs.get(i)) && dataSnapshot.child(friendsIDs.get(i)).hasChild(playlist)) {
                        for (DataSnapshot snapshot : dataSnapshot.child(friendsIDs.get(i)).child(playlist).getChildren()) {
                            String key = snapshot.getKey().toString();
                            String song = dataSnapshot.child(friendsIDs.get(i)).child(playlist).child(key).getValue().toString();
                            mysongs1.add(song);
                            //from here
                            if (dataSnapshot.child(friendsIDs.get(i)).child(playlist).child(key).getValue().toString().equals(song)) {
                                Toast.makeText(PlaylistsActivity.this, song + " is already in this playlist", Toast.LENGTH_SHORT).show();
                                UserDetails.hasSharedSong = true;
                            } else {
                                UserDetails.hasSharedSong = false;
                            }
                        }
                        if (!UserDetails.hasSharedSong) {
                            addSongToShared(playlist, song, friendsIDs.get(i), oldPlaylist);

                        }
                    } else addSongToShared(playlist, song, friendsIDs.get(i), oldPlaylist);
                }
            }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    private void addSongToShared(String playlist, final String song, String s, String oldPlaylist) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase songRef = ref.child("PlaylistSongs").child(s).child(playlist);
        songRef.push().setValue(song);

        DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
        songref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(ID)){
                    for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                        String key = snapshot.getKey();

                        if (!dataSnapshot.child(ID).child(key).getValue().equals(song)) {
                            Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                            Firebase playRef = ref.child("MySongs").child(ID);
                            playRef.push().setValue(song);
                        }
                    }
                } else{
                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                    Firebase playRef = ref.child("MySongs").child(ID);
                    playRef.push().setValue(song);                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toast.makeText(PlaylistsActivity.this, song + " was added to your playlist", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PlaylistsActivity.this, PlaylistSongs.class);
        intent.putExtra("Uniqid", "FromPlaylistsActivity");
        intent.putExtra("Name", oldPlaylist);
        startActivity(intent);
    }

    private void checkifsongisin(final String song, final String playlist) {
        if(songsInPlaylist.contains(song)){
            Toast.makeText(PlaylistsActivity.this, song + " is already in this playlist", Toast.LENGTH_SHORT).show();
        } else addSong(playlist, song);
    }

    private void lastChecking(String playlist, String song) {
        if(!UserDetails.hasSong){
            addSong(playlist, song);
        }
    }

    private void checkSongIn(final String song, final String playlist) {

        UserDetails.hasSong = false;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(ID) && dataSnapshot.child(ID).hasChild(playlist)) {
                    for (DataSnapshot snapshot : dataSnapshot.child(ID).child(playlist).getChildren()) {
                        String key = snapshot.getKey().toString();
                        if (dataSnapshot.child(ID).child(playlist).child(key).getValue().toString().equals(song)) {
                            Toast.makeText(PlaylistsActivity.this, song + " is already in this playlist", Toast.LENGTH_SHORT).show();
                            UserDetails.hasSong = true;
                        } else {
                            UserDetails.hasSong = false;
                        }
                    }
                    if (!UserDetails.hasSong) {
                        addSong(playlist, song);

                    } else addSong(playlist, song);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    private void addtosharedPlaylist(){
//        mysongs.clear();
//        UserDetails.hasSharedSong = false;
//
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs");
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for (int i = 0; i <= friendsIDs.size() - 1; i++) {
//                    if (dataSnapshot.hasChild(friendsIDs.get(i)) && dataSnapshot.child(friendsIDs.get(i)).hasChild(playlist)) {
//                        for (DataSnapshot snapshot : dataSnapshot.child(friendsIDs.get(i)).child(playlist).getChildren()) {
//                            String key = snapshot.getKey().toString();
//                            if (dataSnapshot.child(friendsIDs.get(i)).child(playlist).child(key).getValue().toString().equals(song)) {
//                                Toast.makeText(PlaylistsActivity.this, song + " is already in this playlist", Toast.LENGTH_SHORT).show();
//                                UserDetails.hasSharedSong = true;
//                            } else {
//                                UserDetails.hasSharedSong = false;
//                            }
//                        }
//                        if (!UserDetails.hasSharedSong) {
//                            addSongToShared(playlist, song, friendsIDs.get(i), oldPlaylist);
//
//                        }
//                    } else addSongToShared(playlist, song, friendsIDs.get(i), oldPlaylist);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
//

    private void addToPlaylist(final String playlist, final String song, final String oldPlaylist) {
        mysongs.clear();
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase songRef = ref.child("PlaylistSongs").child(ID).child(playlist);
        songRef.push().setValue(song);
        Toast.makeText(PlaylistsActivity.this, song + " was added to your playlist", Toast.LENGTH_SHORT).show();

        DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
        songref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(ID)) {
                    for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                        String key = snapshot.getKey();
                        String mysong = dataSnapshot.child(ID).child(key).getValue().toString();
                        mysongs.add(mysong);
                    }
                }
                checkSongInMySongs(playlist, song, oldPlaylist);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void checkSongInMySongs(String playlist, String song, String oldPlaylist) {
        if(!mysongs.contains(song)){
            Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
            Firebase playRef = ref.child("MySongs").child(ID);
            playRef.push().setValue(song);


            if(UserDetails.oldIntent.equals("Followers")){
                Intent backMainTest = new Intent(this, PlaylistSongs.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", PlaylistSongs.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }else if(UserDetails.oldIntent.equals("MySongs")){
                Intent backMainTest = new Intent(this, Songs.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", Songs.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }
            else if(UserDetails.oldIntent.equals("Downloads")){
                Intent backMainTest = new Intent(this, Downloads.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", Downloads.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }
            else if(UserDetails.oldIntent.equals("Favourites")){
                Intent backMainTest = new Intent(this, Favourites.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", Favourites.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }
            else if(UserDetails.oldIntent.equals("Recents")){
                Intent backMainTest = new Intent(this, LibraryActivity.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", LibraryActivity.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }
        } else {
            if(UserDetails.oldIntent.equals("Followers")){
                Intent backMainTest = new Intent(this, PlaylistSongs.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", PlaylistSongs.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }else if(UserDetails.oldIntent.equals("MySongs")){
                Intent backMainTest = new Intent(this, Songs.class);
                if(mediaPlayer.isPlaying()) {
                    backMainTest.putExtra("Song", Songs.track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
        } else {
                if(UserDetails.oldIntent.equals("Followers")){
                    Intent backMainTest = new Intent(this, PlaylistSongs.class);
                    if(mediaPlayer.isPlaying()) {
                        backMainTest.putExtra("Song", PlaylistSongs.track_title.getText().toString());
                    }
                    backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                    startActivity(backMainTest);
                    finish();
                }else if(UserDetails.oldIntent.equals("MySongs")){
                    Intent backMainTest = new Intent(this, Songs.class);
                    if(mediaPlayer.isPlaying()) {
                        backMainTest.putExtra("Song", Songs.track_title.getText().toString());
                    }
                    backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                    startActivity(backMainTest);
                    finish();
                }
                else if(UserDetails.oldIntent.equals("Downloads")){
                    Intent backMainTest = new Intent(this, Downloads.class);
                    if(mediaPlayer.isPlaying()) {
                        backMainTest.putExtra("Song", Downloads.track_title.getText().toString());
                     }
                    backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                    startActivity(backMainTest);
                    finish();
                }
                else if(UserDetails.oldIntent.equals("Favourites")){
                    Intent backMainTest = new Intent(this, Favourites.class);
                    if(mediaPlayer.isPlaying()) {
                        backMainTest.putExtra("Song", Favourites.track_title.getText().toString());
                    }
                    backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                    startActivity(backMainTest);
                    finish();
                }
                else if(UserDetails.oldIntent.equals("Recents")){
                    Intent backMainTest = new Intent(this, LibraryActivity.class);
                    if(mediaPlayer.isPlaying()) {
                        backMainTest.putExtra("Song", LibraryActivity.track_title.getText().toString());
                    }
                    backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                    startActivity(backMainTest);
                    finish();
                }
            }
        }

    }


    private void addToMySongs(String playlist, String song, String oldPlaylist) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("MySongs").child(ID);
        playRef.push().setValue(song);
        Toast.makeText(PlaylistsActivity.this, song + " was added to your playlist", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PlaylistsActivity.this, PlaylistSongs.class);
        intent.putExtra("Uniqid", "FromPlaylistsActivity");
        intent.putExtra("Name", oldPlaylist);
        startActivity(intent);
    }

    private void addSong(String playlist, final String song) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase songRef = ref.child("PlaylistSongs").child(ID).child(playlist);
        songRef.push().setValue(song);

        DatabaseReference songref = FirebaseDatabase.getInstance().getReference().child("MySongs");
        songref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(ID)){
                    for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                        String key = snapshot.getKey();

                        if (!dataSnapshot.child(ID).child(key).getValue().equals(song)) {
                            Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                            Firebase playRef = ref.child("MySongs").child(ID);
                            playRef.push().setValue(song);
                        }
                    }
                } else{
                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                    Firebase playRef = ref.child("MySongs").child(ID);
                    playRef.push().setValue(song);                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Toast.makeText(PlaylistsActivity.this, song + " was added to your playlist", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PlaylistsActivity.this, RealTimeActivity.class);
        intent.putExtra("Uniqid", "FromPlaylistsActivity");
        intent.putExtra("Name", playlist);
        startActivity(intent);
    }



    private void checkHasSong(final String song, final String playlist, final String oldPlaylist) {

        UserDetails.hasSong = false;

        //add id statement-if sharedplaylists arraylist has playlist go to fb without id else do this:
        //also create dofferent add to playlist without id
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(ID) && dataSnapshot.child(ID).hasChild(playlist)) {
                        for (DataSnapshot snapshot : dataSnapshot.child(ID).child(playlist).getChildren()) {
                            String key = snapshot.getKey().toString();
                            if (dataSnapshot.child(ID).child(playlist).child(key).getValue().toString().equals(song)) {
                                Toast.makeText(PlaylistsActivity.this, song + " is already in this playlist", Toast.LENGTH_SHORT).show();
                                UserDetails.hasSong = true;
                            } else {
                                UserDetails.hasSong = false;
                            }
                        }
                        if (!UserDetails.hasSong) {
                            addToPlaylist(playlist, song, oldPlaylist);

                        }
                    } else addToPlaylist(playlist,song, oldPlaylist);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.playlists, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(PlaylistsActivity.this);
            View mView = getLayoutInflater().inflate(R.layout.newplaylist, null);
            final EditText newname = (EditText) mView.findViewById(R.id.editText);
            Button cancel = (Button) mView.findViewById(R.id.cancel);
            Button create = (Button) mView.findViewById(R.id.create);


//                mBuilder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();

            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String name = playlistName.getText().toString().trim();

                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                    Firebase playRef = ref.child("Playlists").child(ID);
                    playRef.push().setValue(name);

                    Firebase sref = new Firebase("https://tunein-633e5.firebaseio.com/");
                    Firebase splayRef = sref.child("PlaylistSongs").child(ID).child(name);
                    splayRef.push().setValue(song);
                    newPlaylist.setVisibility(View.GONE);
                    fab.setVisibility(View.VISIBLE);
                    Toast.makeText(PlaylistsActivity.this, song + " was added to your new playlist", Toast.LENGTH_SHORT).show();
                    hideSoftKeyboard(PlaylistsActivity.this);

                    if(UserDetails.oldIntent.equals("Followers")){
                        Intent backMainTest = new Intent(PlaylistsActivity.this, PlaylistSongs.class);
                        if(mediaPlayer.isPlaying()) {
                            backMainTest.putExtra("Song", PlaylistSongs.track_title.getText().toString());
                        }
                        backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                        startActivity(backMainTest);
                        finish();
                    }else if(UserDetails.oldIntent.equals("MySongs")){
                        Intent backMainTest = new Intent(PlaylistsActivity.this, Songs.class);
                        if(mediaPlayer.isPlaying()) {
                            backMainTest.putExtra("Song", Songs.track_title.getText().toString());
                        }
                        backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                        startActivity(backMainTest);
                        finish();
                    }
                    else if(UserDetails.oldIntent.equals("Downloads")){
                        Intent backMainTest = new Intent(PlaylistsActivity.this, Downloads.class);
                        if(mediaPlayer.isPlaying()) {
                            backMainTest.putExtra("Song", Downloads.track_title.getText().toString());
                        }
                        backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                        startActivity(backMainTest);
                        finish();
                    }
                    else if(UserDetails.oldIntent.equals("Favourites")){
                        Intent backMainTest = new Intent(PlaylistsActivity.this, Favourites.class);
                        if(mediaPlayer.isPlaying()) {
                            backMainTest.putExtra("Song", Favourites.track_title.getText().toString());
                        }
                        backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                        startActivity(backMainTest);
                        finish();
                    }
                    else if(UserDetails.oldIntent.equals("Recents")){
                        Intent backMainTest = new Intent(PlaylistsActivity.this, LibraryActivity.class);
                        if(mediaPlayer.isPlaying()) {
                            backMainTest.putExtra("Song", LibraryActivity.track_title.getText().toString());
                        }
                        backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                        startActivity(backMainTest);
                        finish();
                    }
                    dialog.dismiss();


                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    newPlaylist.setVisibility(View.GONE);
                    hideSoftKeyboard(PlaylistsActivity.this);
                    fab.setVisibility(View.VISIBLE);
                    dialog.dismiss();

                }

            });

//            newPlaylist.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
//            playlistName.setText("");
        }
        else onBackPressed();



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //Toast.makeText(PlaylistsActivity.this, "back to " + UserDetails.oldIntent, Toast.LENGTH_SHORT).show();

        if(UserDetails.oldIntent.equals("Followers")){
            Intent backMainTest = new Intent(this, PlaylistSongs.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", LibraryActivity.track_title.getText().toString());
            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }else if(UserDetails.oldIntent.equals("MySongs")){
            Intent backMainTest = new Intent(this, Songs.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", LibraryActivity.track_title.getText().toString());
            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("Downloads")){
            Intent backMainTest = new Intent(this, Downloads.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", LibraryActivity.track_title.getText().toString());
            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("Favourites")){
            Intent backMainTest = new Intent(this, Favourites.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", LibraryActivity.track_title.getText().toString());
            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else if(UserDetails.oldIntent.equals("Recents")){
            Intent backMainTest = new Intent(this, LibraryActivity.class);
            if(mediaPlayer.isPlaying()) {
                backMainTest.putExtra("Song", LibraryActivity.track_title.getText().toString());
            }
            backMainTest.putExtra("Name", UserDetails.oldPlaylist);
            startActivity(backMainTest);
            finish();
        }
        else {
            Intent backMainTest = new Intent(this, RealTimeActivity.class);
            startActivity(backMainTest);
            finish();
        }


    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                new View(this).getWindowToken(), 0);
    }

    public String getBarTitle(){
        return getSupportActionBar().getTitle().toString();
    }


}
