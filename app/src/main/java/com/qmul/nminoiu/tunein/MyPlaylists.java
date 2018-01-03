package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
    private LinearLayout newPlaylist;
    private EditText playlistName;
    private Button create;
    private Button cancel;
    private CustomAdapter adapter;
    private CustomSharedAdapter adapter1;

    private ImageView icon;
    public RelativeLayout buttons;
    private FloatingActionButton fab;
    private FloatingActionButton fab1;

    private TextView track_title;
    private LinearLayout play_toolbar;
    private LinearLayout laypl;
    private Button btn;
    private String me;
    private String url;
    private RelativeLayout sharedWithMeLayout;



    public static final String[] titles = new String[] { "Strawberry",
            "Banana", "Orange", "Mixed" };

    public static final Integer[] images = { R.drawable.options,
            R.drawable.options, R.drawable.options, R.drawable.options };

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

        newPlaylist = (LinearLayout) findViewById(R.id.createPlaylist);
        newPlaylist.bringToFront();
        playlistName = (EditText) findViewById(R.id.editText);
        create = (Button) findViewById(R.id.create);
        cancel = (Button) findViewById(R.id.cancel);
        buttons = (RelativeLayout) findViewById(R.id.buttons);
        icon = (ImageView)  findViewById(R.id.icon);
        create.setEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPlaylists.this, Users.class);
                if(mediaPlayer.isPlaying()){
                    UserDetails.playingSongName = track_title.getText().toString();
                }
                startActivity(i);
            }
        });

        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPlaylists.this, SettingsActivity.class);
                if(mediaPlayer.isPlaying()){
                    UserDetails.playingSongName = track_title.getText().toString();
                }
                startActivity(i);
            }
        });


        final CoordinatorLayout.LayoutParams paramsFab = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        CoordinatorLayout.LayoutParams paramsFab1 = (CoordinatorLayout.LayoutParams) fab1.getLayoutParams();


        Intent i = getIntent();
        if(i.hasExtra("Song")){
            String title = i.getStringExtra("Song");
            UserDetails.songname = title;
            //Toast.makeText(this, "title is "+title, Toast.LENGTH_SHORT).show();
            track_title.setText(title);
        }
        if(mediaPlayer.isPlaying()){
            play_toolbar.setVisibility(View.VISIBLE);
           // track_title.setText(UserDetails.playingSongName);
            paramsFab.setMargins(53, 0, 0, 160);
            fab.setLayoutParams(paramsFab);
            paramsFab1.setMargins(0, 0, 53, 160);
            fab1.setLayoutParams(paramsFab1);
        } else play_toolbar.setVisibility(View.GONE);

//        if(i.hasExtra("Uniqid")){
//            String uniqid = i.getStringExtra("Uniqid");
//            if(uniqid.equals("FromPlaylistDelete")){
//                playlistsList = new ArrayList<>();
//                playlistsList.clear();
//                playlists.setClickable(true);
//                playlists.bringToFront();
//                playlistsadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlistsList);
//                rowItems1 = new ArrayList<RowItem>();
//                adapter1 = new CustomSharedAdapter(this, rowItems1);
//
//
//                final ArrayList<String> sharedPlaylists = new ArrayList<>();
//                DatabaseReference sharedWithMe = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites");
//
//                sharedWithMe.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        if(dataSnapshot.child(ID).exists()){
//                            //Toast.makeText(MyPlaylists.this, "i'm in", Toast.LENGTH_SHORT).show();
//
//                            //set visible
//                            sharedWithMeLayout.setVisibility(View.VISIBLE);
//
//                            for(DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()){
//                                String friend = snapshot.getKey();
//
//                                for (DataSnapshot snap : dataSnapshot.child(ID).child(friend).getChildren()) {
//                                    String key = snap.getKey();
//
//                                    String playlist = dataSnapshot.child(ID).child(friend).child(key).getValue().toString();
//
//                                    sharedPlaylists.add(playlist);
//                                    UserDetails.myPlaylists.add(playlist);
//                                    RowItem item = new RowItem(R.drawable.ic_nextblack, playlist);
//
//                                    rowItems1.add(item);
//                                    adapter1.notifyDataSetChanged();
//
//                                }
//                            }
//                        }
//                        adapter1.notifyDataSetChanged();
//
//                        // if(dataSnapshot.hasChildren())
////                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
////                    String friend = snapshot.getKey();
////                    sharedFriends.add(friend);
////                    RowItem item = new RowItem(R.drawable.xclose, friend);
////
////                    rowItems1.add(item);
////                    adapterShared.notifyDataSetChanged();
//
//
////                    for (DataSnapshot snap : dataSnapshot.child(friend).getChildren()) {
////                        Toast.makeText(PlaylistSongs.this, "friend is " + friend, Toast.LENGTH_SHORT).show();
////                        String key = snap.getKey();
////                        if (dataSnapshot.child(friend).child(key).getValue().equals(toolbar.getTitle().toString())) {
////                            Toast.makeText(PlaylistSongs.this, "test", Toast.LENGTH_SHORT).show();
////
////                            sharedOwnership.setVisibility(View.VISIBLE);
////                            showSharedImg.setBackgroundResource(R.drawable.blackdown);
////                            bar.setVisibility(View.VISIBLE);
////
//
////                        }
////
////
////                    }
////
////                }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//
//            }
//        }


        //sharedPlaylists.clear();
        //adapter.notifyDataSetChanged();
        adapter1.notifyDataSetChanged();

        sharedPlaylists.clear();

        playlistsList = new ArrayList<>();
        playlists.setClickable(true);
        playlists.bringToFront();
        playlistsList.clear();
        playlistsadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlistsList);
        rowItems1 = new ArrayList<RowItem>();
        adapter1 = new CustomSharedAdapter(this, rowItems1);

        play_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_info = new Intent(MyPlaylists.this, AndroidBuildingMusicPlayerActivity.class);
                intent_info.putExtra("Uniqid", "FromMyPlaylists");
                if (mediaPlayer.isPlaying()) {
                    intent_info.putExtra("Song", track_title.getText().toString());
                    UserDetails.playingSongName = track_title.getText().toString();
                }
                startActivity(intent_info);
                overridePendingTransition(R.anim.slide_up_info, R.anim.no_change);
            }
        });

        DatabaseReference sharedWithMe = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites");

        sharedWithMe.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sharedPlaylists.clear();

                if(dataSnapshot.child(ID).exists()){
                    //Toast.makeText(MyPlaylists.this, "i'm in", Toast.LENGTH_SHORT).show();

                    //set visible
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

                // if(dataSnapshot.hasChildren())
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    String friend = snapshot.getKey();
//                    sharedFriends.add(friend);
//                    RowItem item = new RowItem(R.drawable.xclose, friend);
//
//                    rowItems1.add(item);
//                    adapterShared.notifyDataSetChanged();


//                    for (DataSnapshot snap : dataSnapshot.child(friend).getChildren()) {
//                        Toast.makeText(PlaylistSongs.this, "friend is " + friend, Toast.LENGTH_SHORT).show();
//                        String key = snap.getKey();
//                        if (dataSnapshot.child(friend).child(key).getValue().equals(toolbar.getTitle().toString())) {
//                            Toast.makeText(PlaylistSongs.this, "test", Toast.LENGTH_SHORT).show();
//
//                            sharedOwnership.setVisibility(View.VISIBLE);
//                            showSharedImg.setBackgroundResource(R.drawable.blackdown);
//                            bar.setVisibility(View.VISIBLE);
//

//                        }
//
//
//                    }
//
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        playlistsRef = FirebaseDatabase.getInstance().getReference().child("Playlists").child(ID);
        playlistsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlist = dataSnapshot.getValue(String.class);
                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

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

//        ClickListener(new View.OnClickListener() {
//                                         @Override
//                                         public void onClick(View v) {
//                                             Toast.makeText(MyPlaylists.this, "click click", Toast.LENGTH_LONG).show();
//                                             UserDetails.menuIcons.get(1).setOnClickListener(new View.OnClickListener() {
//                                                 @Override
//                                                 public void onClick(View v) {
//                                                     buttons.setVisibility(View.VISIBLE);
//
//                                                 }
//                                             });
//
//                                         }
//                                     });



//                                                 Toast toast = Toast.makeText(getApplicationContext(),
//                                                         "Item " +  ": " + UserDetails.myPlaylists.get(position),
//                                                         Toast.LENGTH_SHORT);
//                                                 toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
//                                                 toast.show();
//                                             }
//                                                 switch (view.getId()) {
//                                                     case R.id.icon:
//                                                         //Do it when myimage is clicked
//                                                         Toast.makeText( MyPlaylists.this, "Icon CLICKED!", Toast.LENGTH_SHORT).show();
//                                                         break;
//                                                     case R.id.title:
//                                                         //Do something when other item is clicked
//                                                         Toast.makeText( MyPlaylists.this, "title CLICKED!", Toast.LENGTH_SHORT).show();;
//                                                 }


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = playlistName.getText().toString().trim();
                if(name.equals("")){
                    Toast.makeText(MyPlaylists.this, "Please enter a name", Toast.LENGTH_LONG).show();


                } else {
                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                    Firebase playRef = ref.child("Playlists").child(ID);
                    playRef.push().setValue(name);
                    Toast.makeText(MyPlaylists.this, "Playlist Created Successfully ", Toast.LENGTH_LONG).show();
                    newPlaylist.setVisibility(View.GONE);
                    fab.setVisibility(View.VISIBLE);

                    hideSoftKeyboard(MyPlaylists.this);
                }
            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPlaylist.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
                hideSoftKeyboard(MyPlaylists.this);
            }
        });

        if (adapter1 != null) {
            adapter1.notifyDataSetChanged();
        }

        sharedPlListview.setClickable(true);
        sharedPlListview.setAdapter(adapter1);
        sharedPlListview.setOnItemClickListener(this);

        playlists.setClickable(true);
        playlists.setAdapter(adapter);
        playlists.setOnItemClickListener(this);
    }

    private void getSharingFriend(final String playlist) {

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
                                Toast.makeText(MyPlaylists.this, "friend who shared is " + UserDetails.friend, Toast.LENGTH_LONG).show();


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
            newPlaylist.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            playlistName.setText("");
        }
        else onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    private void checkEntry() {
        if(playlistName.getText().toString().trim().equals("")){
            create.setEnabled(false);
        } else create.setEnabled(true);
    }

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
       // String item = parent.getItem(position).toString();

        //Toast.makeText(MyPlaylists.this, "click click" + item, Toast.LENGTH_LONG).show();

        //buttons.setVisibility(View.VISIBLE);
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

//    @Override
//    public void onResume() {
//        super.onResume();
//        sharedPlaylists = new ArrayList<String>();
//    }

    private void deleteFromInvitations(final String oldPlaylist) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                                for(int i=0; i<= sharedPlaylists.size()-1; i++){
                                    if(sharedPlaylists.get(i).equals(oldPlaylist)){
                                        sharedPlaylists.remove(i);
                                        adapter1.notifyDataSetChanged();

                                    }
                                }
                                adapter1.notifyDataSetChanged();
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

    public void openPlayerPage(View v) {
        Intent i = new Intent(MyPlaylists.this, AndroidBuildingMusicPlayerActivity.class);
        startActivity(i);
    }


}
