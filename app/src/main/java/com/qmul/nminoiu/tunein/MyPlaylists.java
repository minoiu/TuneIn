package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

/**
 * created by nicoleta on 28/11/2017
 */
public class MyPlaylists extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private String playlistname;
    private ListView playlists;
    private ListView sharedPlListview;
    private List<String> playlistsList;
    private ArrayAdapter<String> playlistsadapter;
    private ArrayList<String> sharedPlaylists = new ArrayList<>();
    private String ID;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference playlistsRef;
    private String playlist;
    private CustomAdapter adapter;
    private CustomSharedAdapter adapter1;
    private ImageView icon;
    public RelativeLayout buttons;
    private FloatingActionButton fab;
    private FloatingActionButton fab1;
    public static TextView track_title;
    private LinearLayout play_toolbar;
    private LinearLayout laypl;
    private Button btn;
    private String me;
    private String url;
    private RelativeLayout sharedWithMeLayout;
    public static final String[] titles = new String[] { "Strawberry",
            "Banana", "Orange", "Mixed" };
    public static final Integer[] images = { R.drawable.options, R.drawable.options, R.drawable.options, R.drawable.options };
    ListView listView;
    List<RowItem> rowItems;
    List<RowItem> rowItems1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplaylists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Playlists");
        laypl = (LinearLayout) findViewById((R.id.myplaylistsLayout));
        playlists = (ListView) laypl.findViewById(R.id.playlistsList);
        sharedPlListview = (ListView) laypl.findViewById(R.id.listSharedWithMe);
        play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
        play_toolbar.setClickable(true);
        btn = (Button) findViewById(R.id.button);
        track_title = (TextView) findViewById(R.id.track_title);
        rowItems = new ArrayList<RowItem>();
        rowItems1 = new ArrayList<RowItem>();
        adapter = new CustomAdapter(this, rowItems);
        adapter1 = new CustomSharedAdapter(this, rowItems1);
        buttons = (RelativeLayout) findViewById(R.id.buttons);
        sharedWithMeLayout = (RelativeLayout) findViewById(R.id.sharedWithMeLayout);
        buttons = (RelativeLayout) findViewById(R.id.buttons);
        icon = (ImageView)  findViewById(R.id.icon);
        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();
        adapter1.notifyDataSetChanged();
        sharedPlaylists.clear();
        playlistsList = new ArrayList<>();
        playlists.setClickable(true);
        playlists.bringToFront();
        playlistsList.clear();
        playlistsadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlistsList);
        rowItems1 = new ArrayList<RowItem>();
        adapter1 = new CustomSharedAdapter(this, rowItems1);

        //handle click on messages floating button
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPlaylists.this, Conversations.class);
                if(mediaPlayer.isPlaying()){
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
                Intent i = new Intent(MyPlaylists.this, RealTimeActivity.class);
                if(mediaPlayer.isPlaying()){
                    String song = track_title.getText().toString();
                    i.putExtra("Song", song);
                }
                startActivity(i);
            }
        });

        //set new margins for floating buttons
        final CoordinatorLayout.LayoutParams paramsFab = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        CoordinatorLayout.LayoutParams paramsFab1 = (CoordinatorLayout.LayoutParams) fab1.getLayoutParams();

        //get extra from intent
        Intent i = getIntent();
        if(i.hasExtra("Song")){
            String title = i.getStringExtra("Song");
            UserDetails.songname = title;
        }

        //set player bar visible if media player is playing
        if(mediaPlayer.isPlaying()){
            play_toolbar.setVisibility(View.VISIBLE);
            paramsFab.setMargins(53, 0, 0, 160);
            fab.setLayoutParams(paramsFab);
            paramsFab1.setMargins(0, 0, 53, 160);
            fab1.setLayoutParams(paramsFab1);
        } else play_toolbar.setVisibility(View.GONE);

        //get current song form Firebase
        DatabaseReference songTitleRef = FirebaseDatabase.getInstance().getReference().child("CurrentSong");
        songTitleRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(ID).exists()){
                    String song = dataSnapshot.child(ID).child("Song").getValue().toString();
                    track_title.setText(song);
                    Toast.makeText(MyPlaylists.this, "song is " + song, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //handle click on play bar
        play_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(MyPlaylists.this, MusicPlayerActivity.class);
                intent_info.putExtra("Uniqid", "FromMyPlaylists");
                if (mediaPlayer.isPlaying()) {
                    intent_info.putExtra("Song", track_title.getText().toString());
                }
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            }
        });

        //retrieve shared playlists from Firebase
        DatabaseReference sharedWithMe = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites");
        sharedWithMe.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sharedPlaylists.clear();
                if(dataSnapshot.child(ID).exists()){
                    sharedWithMeLayout.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()){
                        String friend = snapshot.getKey();
                        for (DataSnapshot snap : dataSnapshot.child(ID).child(friend).getChildren()) {
                            String key = snap.getKey();
                            String playlist = dataSnapshot.child(ID).child(friend).child(key).getValue().toString();
                            sharedPlaylists.add(playlist);
                            UserDetails.myPlaylists.add(playlist);
                            RowItem item = new RowItem(R.drawable.ic_nextblack, playlist);
                            rowItems1.add(item);
                            adapter1.notifyDataSetChanged();
                        }
                    }
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //retreieve playlists from Firebase
        playlistsRef = FirebaseDatabase.getInstance().getReference().child("Playlists").child(ID);
        playlistsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String playlist = dataSnapshot.getValue(String.class);
                playlistsList.add(playlist);
                UserDetails.myPlaylists.add(playlist);
                RowItem item = new RowItem(R.drawable.ic_nextblack, playlist);
                rowItems.add(item);
                adapter.notifyDataSetChanged();
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

        //handle click on shared playlists tab
        sharedPlListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RowItem rowItem = (RowItem) parent.getItemAtPosition(position);
                final String playlist = rowItem.getTitle();
                if(UserDetails.oldIntent.equals("PLSongs")) {
                    Intent intent = new Intent(MyPlaylists.this, SharedPlaylistSongs.class);
                    intent.putExtra("Name", UserDetails.oldPlaylist);
                    intent.putExtra("Friend", UserDetails.friend);
                    if (mediaPlayer.isPlaying()) {
                        intent.putExtra("Song", track_title.getText().toString());
                    }
                    startActivity(intent);
                }
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites").child(ID);
                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String friend = snapshot.getKey();
                            for (DataSnapshot snap : dataSnapshot.child(friend).getChildren()) {
                                String key = snap.getKey();
                                if(dataSnapshot.child(friend).child(key).getValue().toString().equals(playlist)){
                                    UserDetails.friend = friend;
                                    Toast.makeText(MyPlaylists.this, "friend who shared is " + UserDetails.friend, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MyPlaylists.this, SharedPlaylistSongs.class);
                                    intent.putExtra("Name", UserDetails.oldPlaylist);
                                    intent.putExtra("Friend", UserDetails.friend);
                                    if(mediaPlayer.isPlaying()) {
                                        intent.putExtra("Song", track_title.getText().toString());
                                    }
                                    startActivity(intent);
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

            }
        });

        //handle click on playlists
        playlists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RowItem rowItem = (RowItem) parent.getItemAtPosition(position);
                final String playlist = rowItem.getTitle();
                if(UserDetails.oldIntent.equals("PLSongs")) {
                    Intent intent = new Intent(MyPlaylists.this, PlaylistSongs.class);
                    intent.putExtra("Name", playlist);
                    if (mediaPlayer.isPlaying()) {
                        intent.putExtra("Song", track_title.getText().toString());
                    }
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MyPlaylists.this, PlaylistSongs.class);
                    intent.putExtra("Name", playlist);
                    if (mediaPlayer.isPlaying()) {
                        intent.putExtra("Song", track_title.getText().toString());
                    }
                    startActivity(intent);
                }
            }
        });

        if (adapter1 != null) {
            adapter1.notifyDataSetChanged();
        }

        //set adapter to playlists and make it clickable
        sharedPlListview.setClickable(true);
        sharedPlListview.setAdapter(adapter1);
        sharedPlListview.setOnItemClickListener(this);
        playlists.setClickable(true);
        playlists.setAdapter(adapter);
        playlists.setOnItemClickListener(this);
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.playlists, menu);
        return true;
    }

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MyPlaylists.this);
            View mView = getLayoutInflater().inflate(R.layout.newplaylist, null);
            final EditText newname = (EditText) mView.findViewById(R.id.editText);
            Button cancel = (Button) mView.findViewById(R.id.cancel);
            Button create = (Button) mView.findViewById(R.id.create);
            mBuilder.setView(mView);
            final AlertDialog dialog = mBuilder.create();
            dialog.show();
            fab.setVisibility(View.GONE);

            //handle click on create
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = newname.getText().toString().trim();
                    if(name.equals("")){
                        Toast.makeText(MyPlaylists.this, "Please enter a name", Toast.LENGTH_LONG).show();
                    } else {
                        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                        Firebase playRef = ref.child("Playlists").child(ID);
                        playRef.push().setValue(name);
                        Toast.makeText(MyPlaylists.this, "Playlist Created Successfully ", Toast.LENGTH_LONG).show();
                        fab.setVisibility(View.VISIBLE);
                        dialog.hide();
                        hideSoftKeyboard(MyPlaylists.this);
                    }
                }
            });

            //handle click on cancel
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fab.setVisibility(View.VISIBLE);
                    hideSoftKeyboard(MyPlaylists.this);
                    dialog.hide();
                }
            });
        }
        else onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    //handle click on  back button and open previous activity
    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, LibraryActivity.class);
        if(mediaPlayer.isPlaying()) {
            backMainTest.putExtra("Song", track_title.getText().toString());
        }
        startActivity(backMainTest);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    //handle on menu open
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
            uinfo.put("Song", mysong);
            ref4.child(UserDetails.fullname).updateChildren(uinfo);
        }
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
        final RealTimeActivity sa = new RealTimeActivity();
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Homepage");
        mDatabase1.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String v;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
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

    /**
     * Open player page.
     *
     * @param v the v
     */
    public void openPlayerPage(View v) {
        Intent i = new Intent(MyPlaylists.this, MusicPlayerActivity.class);
        startActivity(i);
    }
}
