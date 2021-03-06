package com.qmul.nminoiu.tunein;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

/**
 * The type Favourite playlists.
 */
public class FavouritePlaylists extends AppCompatActivity {

    private ListView playlistList;
    /**
     * The Songs.
     */
    ArrayList<String> songs = new ArrayList<>();
    private ArrayAdapter<String> sadapter;
    private ArrayAdapter<String> songssadapter;
    private String me;
    private View bar;
    private DatabaseReference db;
    private LinearLayout searchLayout;
    private MaterialSearchView searchView;
    private DatabaseReference db1;
    private String value;
    private String sender;
    private String ID;
    private FirebaseAuth firebaseAuth;
    private FavouritesCustomAdapter adapter;
    private AdapterFavourites searchadapter;
    private String song;
    private TextView track_title;
    private LinearLayout play_toolbar;
    private Button btn;
    private String url;
    private LinearLayout dwnLay;
    private RelativeLayout favPlaylistsLayout;
    private List<RowItem> rowItems;
    private ImageView img;
    private TextView name;
    private RelativeLayout dwnPlaylistsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_playlists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        favPlaylistsLayout = (RelativeLayout) findViewById(R.id.playlists);
        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();
        rowItems = new ArrayList<RowItem>();
        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        play_toolbar.setClickable(true);
        btn = (Button) findViewById(R.id.button);
        track_title = (TextView) findViewById(R.id.track_title);
        adapter = new FavouritesCustomAdapter(this, rowItems);
        img = (ImageView) findViewById(R.id.icon);
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String curUser = currentuser.getUid().toString();
        playlistList = (ListView) findViewById(R.id.favPlaylists);
        playlistList.bringToFront();
        playlistList.setVisibility(View.VISIBLE);
        playlistList.setClickable(true);
        playlistList.setAdapter(adapter);
        bar = (View) findViewById(R.id.bar);


        final CoordinatorLayout.LayoutParams paramsFab = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();

        Intent i = getIntent();
        if (i.hasExtra("Song")) {
            String title = i.getStringExtra("Song");
//            track_title.setText(title);
        }
        if (mediaPlayer.isPlaying()) {
            //track_title.setText(UserDetails.playingSongName);
            play_toolbar.setVisibility(View.VISIBLE);
            paramsFab.setMargins(53, 0, 0, 160); //bottom margin is 25 here (change it as u wish)
            fab.setLayoutParams(paramsFab);
        } else play_toolbar.setVisibility(View.GONE);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favourite Playlists");
        ID = firebaseAuth.getCurrentUser().getUid();
        sender = firebaseAuth.getCurrentUser().getEmail();
        playlistList.bringToFront();

        DatabaseReference songTitleRef = FirebaseDatabase.getInstance().getReference().child("CurrentSong");
        songTitleRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(ID).exists()){
                    String song = dataSnapshot.child(ID).child("Song").getValue().toString();
                    track_title.setText(song);
//                    Toast.makeText(LibraryActivity.this, "song is " + song, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        playlistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RowItem rowItem = (RowItem) parent.getItemAtPosition(position);
                String playlist = rowItem.getTitle();
                Intent intent = new Intent(FavouritePlaylists.this, PlaylistSongs.class);
                intent.putExtra("Name", playlist);
                intent.putExtra("Uniqid","favplaylists");
                if(mediaPlayer.isPlaying()) {
                    intent.putExtra("Song", track_title.getText().toString());
                    //UserDetails.playingSongName = track_title.getText().toString();
                    startActivity(intent);

                } else startActivity(intent);

                //Intent intent = new Intent(this, FavPlaylistSongs.class);
                    //    intent.putExtra("Song", track_title.getText().toString());
                    //intent.putExtra("Playlist", playlist);
                // startActivity(intent);

            }

        });

        db = FirebaseDatabase.getInstance().getReference().child("LovedPlaylists").child(ID);

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Toast.makeText(Favourites.this, "has child is " + dataSnapshot.hasChild(ID), Toast.LENGTH_SHORT).show();

                String song = dataSnapshot.getValue(String.class);
                //  Toast.makeText(Favourites.this, "in on song is " + song, Toast.LENGTH_SHORT).show();

                songs.add(song);

                RowItem item = new RowItem(R.drawable.ic_nextblack, song);

                rowItems.add(item);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String song = dataSnapshot.getValue(String.class);
                songs.remove(song);
                rowItems.remove(new RowItem(R.drawable.ic_nextblack, song));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        playlistList.setAdapter(adapter);

        play_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(FavouritePlaylists.this, MusicPlayerActivity.class);
                intent_info.putExtra("Uniqid", "FromLibrary");
                if (mediaPlayer.isPlaying()) {
                    intent_info.putExtra("Song", track_title.getText().toString());
                    //UserDetails.playingSongName = track_title.getText().toString();

                }
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            }
        });
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

            Intent intent = new Intent(this, Favourites.class);
            if(mediaPlayer.isPlaying()) {
                intent.putExtra("Song", track_title.getText().toString());
                //UserDetails.playingSongName = track_title.getText().toString();

            }
            startActivity(intent);
//
//        Intent i = getIntent();
//        if (i != null) {
//            if (i.hasExtra("Uniqid")) {
//                String uniqid = i.getStringExtra("Uniqid");

//                if (uniqid.equals("FromSongAdapter")) {
//                    // Toast.makeText(FollowersActivity.this, "from songsadapter ", Toast.LENGTH_SHORT).show();
//                    String song = i.getStringExtra("Song");
//                    String playlist = i.getStringExtra("Name");
//                    Intent intent = new Intent(Songs.this, Chat.class);
//                    intent.putExtra("Uniqid", "FromFollowers");
//                    intent.putExtra("Song", song);
//                    intent.putExtra("Name", playlist);
//                    startActivity(intent);
//                } else if (uniqid.equals("FSAdapter")) {
//                    String songToJoin = i.getStringExtra("Song");
//                    String playlist = i.getStringExtra("Name");
//                    Intent intent = new Intent(Songs.this, PlaylistSongs.class);
//                    intent.putExtra("Uniqid", "FromFollowers");
//                    intent.putExtra("Name", playlist);
//                    startActivity(intent);
//
//                }
        }
        //  }
        // finish();
//    }

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
                //Toast.makeText(RealTimeActivity.this, UserDetails.myFollowers.size() + " is the size", Toast.LENGTH_LONG).show();

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Open player page.
     *
     * @param v the v
     */
    public void openPlayerPage(View v) {
        Intent i = new Intent(FavouritePlaylists.this, MusicPlayerActivity.class);
        startActivity(i);
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
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                //Toast.makeText(RealTimeActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
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
                //Toast.makeText(RealTimeActivity.this, UserDetails.myname + " is finally my fullname", Toast.LENGTH_SHORT).show();

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

        //MusicPlayerActivity.songProgressBar.setProgress(0);
        //MusicPlayerActivity.songProgressBar.setMax(100);
//            new MusicPlayerActivity().updateProgressBar();
    }

    /**
     * Erase from firebase.
     */
    public void eraseFromFirebase() {
        final RealTimeActivity sa = new RealTimeActivity();
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Homepage");
        mDatabase1.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String v;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            v = snapshot.getKey();
                            //Toast.makeText(RealTimeActivity.this, "in erase" + dataSnapshot.child(snapshot.child(v).getKey().toString()).getKey().toString(), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(RealTimeActivity.this, "v" + v, Toast.LENGTH_SHORT).show();
                            //getFulname();
                            if (dataSnapshot.child(v).hasChild(sa.getMyFullname(ID))) {
                                // Toast.makeText(RealTimeActivity.this, "in if" + snapshot.getValue(), Toast.LENGTH_SHORT).show();

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

}