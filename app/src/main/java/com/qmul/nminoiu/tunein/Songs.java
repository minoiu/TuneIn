package com.qmul.nminoiu.tunein;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

/**
 * The type Songs.
 */
public class Songs extends AppCompatActivity {

    private ListView songsList;
    /**
     * The Songs.
     */
    ArrayList<String> songs = new ArrayList<>();
    private ArrayAdapter<String> sadapter;
    private ArrayAdapter<String> songssadapter;
    private String me;
    private ArrayList<String> recents;



    private DatabaseReference db;

    private LinearLayout searchLayout;
    private MaterialSearchView searchView;
    private DatabaseReference db1;
    private String value;
    private String sender;
    private String ID;
    private FirebaseAuth firebaseAuth;
    private AdapterAllSongs adapter;
    private AdapterAllSongs searchadapter;
    private String song;
    /**
     * The constant track_title.
     */
    public static TextView track_title;
    private LinearLayout play_toolbar;
    private Button btn;
    private String url;

    private List<RowItem> rowItems;
    private ImageView img;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MusicPlayerActivity.songs.clear();
        MusicPlayerActivity.urls.clear();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Songs.this, Conversations.class);
                if(mediaPlayer.isPlaying()){
                    String song = track_title.getText().toString();
                    i.putExtra("Song", song);
                }

                startActivity(i);
            }
        });

        final FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Songs.this, RealTimeActivity.class);
                if(mediaPlayer.isPlaying()){
                    String song = track_title.getText().toString();
                    i.putExtra("Song", song);
                }
                startActivity(i);
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();
        rowItems = new ArrayList<RowItem>();
        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        play_toolbar.setClickable(true);
        btn = (Button) findViewById(R.id.button);
        track_title = (TextView) findViewById(R.id.track_title);
        recents = new ArrayList<>();

        adapter = new AdapterAllSongs(this, rowItems);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String curUser = currentuser.getUid().toString();
        songsList = (ListView) searchLayout.findViewById(R.id.songsListView);
        songsList.bringToFront();
        songsList.setVisibility(View.VISIBLE);
        songsList.setClickable(true);

        final CoordinatorLayout.LayoutParams paramsFab = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        final CoordinatorLayout.LayoutParams paramsFab1 = (CoordinatorLayout.LayoutParams) fab1.getLayoutParams();


        songssadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs);
        songsList.setAdapter(adapter);

        //Toast.makeText(Songs.this, "id is " + ID , Toast.LENGTH_SHORT).show();


        Intent i = getIntent();
        if(i.hasExtra("Song")){
            String title = i.getStringExtra("Song");
//            track_title.setText(title);
        }
        if(mediaPlayer.isPlaying()){
            play_toolbar.setVisibility(View.VISIBLE);
            //track_title.setText(UserDetails.playingSongName);
            paramsFab.setMargins(53, 0, 0, 160); //bottom margin is 25 here (change it as u wish)
            fab.setLayoutParams(paramsFab);
            paramsFab1.setMargins(0, 0, 53, 160);
            fab1.setLayoutParams(paramsFab1);
        } else play_toolbar.setVisibility(View.GONE);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Songs");
        ID = firebaseAuth.getCurrentUser().getUid();
        sender = firebaseAuth.getCurrentUser().getEmail();

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

        songsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                Firebase refsong = new Firebase("https://tunein-633e5.firebaseio.com/CurrentSong/" + ID);
                Map<String, Object> uinfo = new HashMap<>();
                uinfo.put("Song", song);
                refsong.updateChildren(uinfo);
                getLink(song);


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



//        searchView = (MaterialSearchView) findViewById(R.id.search_view);
//        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//
//            @Override
//            public void onSearchViewShown() {
//                //searchLayout.setVisibility(View.VISIBLE);
//                //searchView.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//                songsList.setAdapter(adapter);
//            }
//        });

//        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                if (newText.toLowerCase() != null && !newText.toLowerCase().isEmpty()) {
//                    List<String> slistFound = new ArrayList<String>();
//                    for (String songToFind : songs) {
//                        if (songToFind.toLowerCase().contains(newText.toLowerCase()))
//                            slistFound.add(songToFind);
//                    }
//                    ArrayAdapter sadapter = new ArrayAdapter(Songs.this, android.R.layout.simple_list_item_1, slistFound);
//                    songsList.setAdapter(sadapter);
//                }
////
//                 else {
//                    ArrayAdapter sadapter = new ArrayAdapter(Songs.this, android.R.layout.simple_list_item_1, songs);
//                    songsList.setAdapter(sadapter);
//                }
//                return true;
//            }
//        });



        db = FirebaseDatabase.getInstance().getReference().child("MySongs").child(ID);

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Toast.makeText(Songs.this, "in on child id is " + ID, Toast.LENGTH_SHORT).show();

//                if (dataSnapshot.hasChild(ID)) {
                    String song = dataSnapshot.getValue(String.class);
                   // Toast.makeText(Songs.this, "song is " + song , Toast.LENGTH_SHORT).show();

                getUrl(song);
                MusicPlayerActivity.songs.add(song);


                songs.add(song);

                    RowItem item = new RowItem(R.drawable.options, song);

                    rowItems.add(item);
                    adapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String song = dataSnapshot.getValue(String.class);
                songs.remove(song);
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


        songsList.setAdapter(adapter);

        play_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(Songs.this, MusicPlayerActivity.class);
                intent_info.putExtra("Uniqid", "FromSongs");
                if (mediaPlayer.isPlaying()) {
                    intent_info.putExtra("Song", track_title.getText().toString());
                    //UserDetails.playingSongName = track_title.getText().toString();
                }
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            }
        });
        }

    private void getLink(final String song) {
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
                    //  Toast.makeText(RealTimeActivity.this, UserDetails.song + " is the url", Toast.LENGTH_SHORT).show();
                    //getTimeFromFirebase();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
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

        if (!recents.contains(song)) {
            Firebase likedRef = new Firebase("https://tunein-633e5.firebaseio.com/").child("RecentlyPlayed").child(ID);
            likedRef.push().setValue(song);
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_item, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
//        return true;
//    }

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

        Intent intent = new Intent(this, LibraryActivity.class);
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

        final List<String> myFollowers = new ArrayList<>();
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
                //Toast.makeText(RealTimeActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return UserDetails.fullname;
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
     * Open player page.
     *
     * @param v the v
     */
    public void openPlayerPage(View v) {
        Intent i = new Intent(Songs.this, MusicPlayerActivity.class);
        startActivity(i);
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
