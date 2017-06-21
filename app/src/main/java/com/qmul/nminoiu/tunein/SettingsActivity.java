package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.WidgetContainer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import android.view.View.OnClickListener;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


    public class SettingsActivity extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener {

        MaterialSearchView searchView;

        LinearLayout searchLayout;

        ArrayList ulistSource;

        private TextView email;
        private FirebaseAuth firebaseAuth;
        private String username = "";
        private static final String TAG = "MainActivity";
        private ArrayList<String> songs = new ArrayList<>();
        private ArrayList<String> users = new ArrayList<>();
        private DatabaseReference db;
        private DatabaseReference db1;
        private ArrayAdapter<String> sadapter;
        private ArrayAdapter<String> uadapter;
        private ListView slistView;
        private ListView ulistView;
        private Toolbar play_toolbar;
        private TextView track_title;
        private MediaPlayer mediaPlayer;
        private StorageReference storage;
        private boolean playPause;
        private boolean intialStage = true;
        private Button btn;
        private boolean play;
        private boolean pause;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.app_bar_settings);

            //retrieve fullnames
            //final List<User> users = new ArrayList<User>();
//            ImageView play = (ImageView) findViewById(R.id.play);
//            ImageView pause = (ImageView) findViewById(R.id.pause);

//            mp = new MediaPlayer();
//            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            fetchAudioUrlFromFirebase();
            final String url = "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/Tracks%2FMotoramaGhost.mp3?alt=media&token=98a5ad87-82d9-431c-88ef-59a9e659cde6";

            btn = (Button) findViewById(R.id.button);
            mediaPlayer = new MediaPlayer();
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //btn.setOnClickListener(this);
//            mediaPlayer.start();

            db = FirebaseDatabase.getInstance().getReference().child("Users");
            db1 = FirebaseDatabase.getInstance().getReference().child("Songs");

            ulistView = (ListView) findViewById(R.id.plistView);
            uadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
            ulistView.setAdapter(uadapter);

            slistView = (ListView) findViewById(R.id.slistView);
            sadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs);
            slistView.setAdapter(sadapter);

            play_toolbar = (Toolbar) findViewById(R.id.play_toolbar);

            slistView = (ListView) findViewById(R.id.slistView);
            sadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs);

            slistView.setClickable(true);


            slistView.setAdapter(sadapter);

//            btn.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    final String url = "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/Tracks%2FMotoramaGhost.mp3?alt=media&token=98a5ad87-82d9-431c-88ef-59a9e659cde6";
//                    final MediaPlayer mediaPlayer = new MediaPlayer();
////                    Boolean playOn = false;
////
//                    if (mediaPlayer.isPlaying()) {
//                        mediaPlayer.pause();
//                        //Button btn = (Button) this.findViewById(R.id.button);
//                        btn.setBackgroundResource(R.drawable.ic_media_play);
//                        //playOn = false;
//
//
//                    } else {
////
//                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
////                        try {
////                            mediaPlayer.setDataSource(url);
//                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                        try {
//                            mediaPlayer.prepare();
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
//                        mediaPlayer.start();
////                        //Button btn = (Button) this.findViewById(R.id.button);
////                        btn.setBackgroundResource(R.drawable.ic_media_pause);
//                    }
//                }
//            });


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


            //trying for songs

            db1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String value = dataSnapshot.getValue(String.class);
                    songs.add(value);
                    sadapter.notifyDataSetChanged();
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

            db.child("Songs").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    for (DataSnapshot child : children) {
//                        User user = child.getValue("fullname");

//                            ulistSource.add(user);
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            db1.child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                    for (DataSnapshot child : children) {
//                        User user = child.getValue("fullname");

//                            ulistSource.add(user);
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Now Playing");
            toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));


            ulistView = (ListView) findViewById(R.id.plistView);
            uadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
            ulistView.setAdapter(uadapter);

            slistView = (ListView) findViewById(R.id.slistView);
            sadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, songs);


            //add click listeners for songs listview
            slistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String song = ((TextView) view).getText().toString();
                    play_toolbar.setVisibility(View.VISIBLE);
                    //Toast.makeText(SettingsActivity.this, song, Toast.LENGTH_LONG).show();
                    track_title = (TextView) findViewById(R.id.track_title);
                    track_title.setText(song);

                    //final MediaPlayer mediaPlayer = new MediaPlayer();

                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.setDataSource(url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    btn.setBackgroundResource(R.drawable.ic_media_pause);
                    mediaPlayer.start();

                }
            });


            slistView.setAdapter(sadapter);


            ///trying mediaplayer


//            ulistView = (ListView)findViewById(R.id.plistView);
//            ArrayAdapter<User> uadapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,users);
//            ulistView.setAdapter(uadapter);

            final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View hView = navigationView.getHeaderView(0);
            TextView nav_user = (TextView) hView.findViewById(R.id.emailProfile);
            nav_user.setText(getIntent().getExtras().getString("Email"));
            toolbar.setTitle("Profile");

            searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
            searchView = (MaterialSearchView) findViewById(R.id.search_view);
            searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

                @Override
                public void onSearchViewShown() {
                    searchLayout.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.INVISIBLE);
                    play_toolbar.setVisibility(View.GONE);
                    //play_toolbar.setVisibility(View.INVISIBLE);


                }

                @Override
                public void onSearchViewClosed() {


                    //If closed Search View , lstView will return default

//


//                    ulistView = (ListView)findViewById(R.id.plistView);
//
//                    ArrayAdapter<User> uadapter = new ArrayAdapter(SettingsActivity.this,android.R.layout.simple_list_item_1,ulistSource);
//
//                    ulistView.setAdapter(uadapter);


                    searchLayout.setVisibility(View.INVISIBLE);
                    fab.setVisibility(View.VISIBLE);


                }

            });


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


                        ArrayAdapter uadapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_list_item_1, ulistFound);

                        ulistView.setAdapter(uadapter);


                        List<String> slistFound = new ArrayList<String>();

                        for (String item : songs) {

                            if (item.toLowerCase().contains(newText.toLowerCase()))

                                slistFound.add(item);

                        }


                        ArrayAdapter sadapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_list_item_1, slistFound);

                        slistView.setAdapter(sadapter);
                    } else {

                        //if search text is null

                        //return default

                        ArrayAdapter uadapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_list_item_1, users);

                        ulistView.setAdapter(uadapter);
//
                        ArrayAdapter sadapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_list_item_1, songs);

                        slistView.setAdapter(sadapter);
//
                    }

                    return true;

                }

            });

        }

        @Override

        public boolean onCreateOptionsMenu(Menu menu) {


            getMenuInflater().inflate(R.menu.menu_item, menu);

            MenuItem item = menu.findItem(R.id.action_search);

            searchView.setMenuItem(item);

            return true;

        }


        @Override
        public void onBackPressed() {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                email = (TextView) findViewById(R.id.emailProfile);
                email.setText(getIntent().getExtras().getString("Email"));
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle actifon bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement

            return super.onOptionsItemSelected(item);
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_friends) {
                // Handle the camera action
            } else if (id == R.id.nav_Playlists) {
                Intent nextActivity = new Intent(this, AudioPlayer.class);
                startActivity(nextActivity);

            } else if (id == R.id.nav_delete) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                // Get auth credentials from the user for re-authentication. The example below shows
                // email and password credentials but there are multiple possible providers,
                // such as GoogleAuthProvider or FacebookAuthProvider.
                AuthCredential credential = EmailAuthProvider
                        .getCredential("user@example.com", "password1234");

                // Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User account deleted.");


                                                }
                                            }
                                        });

                            }
                        });
                Intent nextActivity = new Intent(this, RegisterActivity.class);
                startActivity(nextActivity);

            } else if (id == R.id.nav_logout) {
                FirebaseAuth.getInstance().signOut();
                Intent nextActivity = new Intent(this, LoginActivity.class);
                startActivity(nextActivity);
            } else if (id == R.id.nav_Visibility) {

            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }


        public static void hideSoftKeyboard(Activity activity) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }

        public void playMusic(View v){
            Integer length =mediaPlayer.getCurrentPosition();;
            final String url = "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/Tracks%2FMotoramaGhost.mp3?alt=media&token=98a5ad87-82d9-431c-88ef-59a9e659cde6";
            //final MediaPlayer mediaPlayer = new MediaPlayer();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentPosition();
            Button btn = (Button) this.findViewById(R.id.button);
            btn.setBackgroundResource(R.drawable.ic_media_play);
        } else {
            //stopPlaying();
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        try {
                            mediaPlayer.setDataSource(url);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.seekTo(length);
                        mediaPlayer.start();
                        Button btn = (Button) this.findViewById(R.id.button);

                        btn.setBackgroundResource(R.drawable.ic_media_pause);
        }

    }


        // btn.setOnClickListener(new OnClickListener() {


//        public void playSong(View v) {
//            final String url = "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/Tracks%2FMotoramaGhost.mp3?alt=media&token=98a5ad87-82d9-431c-88ef-59a9e659cde6";
//            final MediaPlayer mediaPlayer = new MediaPlayer();
//            Boolean playOn = false;
//            stopPlaying();
//
//                if (mediaPlayer.isPlaying() && playOn) {
//                    mediaPlayer.pause();
//                    Button btn = (Button) this.findViewById(R.id.button);
//                    btn.setBackgroundResource(R.drawable.ic_media_play);
//                    playOn = false;
//
//
//                } else {
//
//                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                        try {
//                            mediaPlayer.setDataSource(url);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            mediaPlayer.prepare();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        mediaPlayer.start();
//                        Button btn = (Button) this.findViewById(R.id.button);
//
//                        btn.setBackgroundResource(R.drawable.ic_media_pause);
//                        playOn = true;
//
//                        //playSong(v);
//                    }
//                }
//
//                btn.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.pause();
//                } else {
//                    mediaPlayer.start();
//                }
//            }
//        });


        //return v;
        //nicolemiplaySong(v);


        private void stopPlaying() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
//
//        public void playSong(View v) {
//            boolean play = true;
//            boolean pause = false;
//
//            final Button btn = (Button) this.findViewById(R.id.button);
//            btn.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    final String url = "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/Tracks%2FMotoramaGhost.mp3?alt=media&token=98a5ad87-82d9-431c-88ef-59a9e659cde6";
//                    final MediaPlayer mediaPlayer = new MediaPlayer();
//                    Boolean playOn = false;
//
//                    if (mediaPlayer.isPlaying()) {
//                        mediaPlayer.pause();
//                        //Button btn = (Button) this.findViewById(R.id.button);
//                        btn.setBackgroundResource(R.drawable.ic_media_play);
//                        //playOn = false;
//
//
//                    } else {
//
//                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                        try {
//                            mediaPlayer.setDataSource(url);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            mediaPlayer.prepare();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        mediaPlayer.start();
//                        //Button btn = (Button) this.findViewById(R.id.button);
//                        btn.setBackgroundResource(R.drawable.ic_media_pause);
//                    }
//                }
//            });
//

//   }


//        public void pSong(View v) {
//            play = true;
//            pause = false;
//
//            final Button btn = (Button) this.findViewById(R.id.button);
//            btn.setOnClickListener(new View.OnClickListener() {
//
//
//                @Override
//                public void onClick(View v) {
//
//                }
//
//                final String url = "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/Tracks%2FMotoramaGhost.mp3?alt=media&token=98a5ad87-82d9-431c-88ef-59a9e659cde6";
//                    final MediaPlayer mediaPlayer = new MediaPlayer();
//                    Boolean playOn = false;
//
//                    if (play) {
//                        play = false;
//                        pause = true;
//                        btn.setBackgroundResource(R.drawable.ic_media_play);
//
//
//                        //mediaPlayer.pause();
//                        //Button btn = (Button) this.findViewById(R.id.button);
//                        //playOn = false;
//
//                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                        try {
//                            mediaPlayer.setDataSource(url);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        try {
//                            mediaPlayer.prepare();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        btn.setClickable(true);
//                        mediaPlayer.start();
//
//                        //Button btn = (Button) this.findViewById(R.id.button);
//                    }
//                    if(pause){
//                        play = true;
//                        pause = false;
//
//                        btn.setBackgroundResource(R.drawable.ic_media_pause);
//                        mediaPlayer.pause();
//                        btn.setClickable(true);
//
//                    }
//                }
//            });
//
//        }

    }



//        View.OnClickListener pausePlay = new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                // TODO Auto-generated method stub
//
//                if (!playPause) {
//                    btn.setBackgroundResource(R.drawable.ic_media_pause);
//                    if (intialStage)
//                        new Player().execute("gs://tunein-633e5.appspot.com/Tracks/The Soul's Release - Catching Fireflies.mp3");
//                    else {
//                        if (!mediaPlayer.isPlaying())
//                            mediaPlayer.start();
//                    }
//                    playPause = true;
//                } else {
//                    btn.setBackgroundResource(R.drawable.ic_media_play);
//                    if (mediaPlayer.isPlaying())
//                        mediaPlayer.pause();
//                    playPause = false;
//                }
//            }
//        };







//
//            try {
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                mediaPlayer.setDataSource(url);
//                mediaPlayer.prepare();
//                mediaPlayer.start();
////                if (mediaPlayer.isPlaying()) {
////                    mediaPlayer.pause();
////                    btn.setBackgroundResource(R.drawable.ic_media_play);
////                } else {
////                    btn.setBackgroundResource(R.drawable.ic_media_pause);
////                }
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//        }
//
//        public void musicPause(View playPause) throws IOException {
//
//            final String url = "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/Tracks%2FMotoramaGhost.mp3?alt=media&token=98a5ad87-82d9-431c-88ef-59a9e659cde6";
//            final MediaPlayer mediaPlayer = new MediaPlayer();
//
//            }
////
//            try {
////                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
////                mediaPlayer.setDataSource(url);
////                mediaPlayer.prepare();
//                mediaPlayer.pause();
////                if (mediaPlayer.isPlaying()) {
////                    mediaPlayer.pause();
////                    btn.setBackgroundResource(R.drawable.ic_media_play);
////                } else {
////                    btn.setBackgroundResource(R.drawable.ic_media_pause);
////                }
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//        }


//        public void tryButton(View v) throws IOException {
//            final String url = "https://firebasestorage.googleapis.com/v0/b/tunein-633e5.appspot.com/o/Tracks%2FMotoramaGhost.mp3?alt=media&token=98a5ad87-82d9-431c-88ef-59a9e659cde6";
//            final MediaPlayer mediaPlayer = new MediaPlayer();
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.setDataSource(url);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.pause();
//                btn.setBackgroundResource(R.drawable.ic_media_play);
//            } else {
//                btn.setBackgroundResource(R.drawable.ic_media_pause);
//            }
//        }


//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//
//                public void onPrepared(MediaPlayer mediaPlayer){
//                    mediaPlayer.start();
//                }
//            });
//
//
//            if (mediaPlayer.isPlaying()) {
//                mediaPlayer.pause();
//                btn.setBackgroundResource(R.drawable.ic_media_play);
//            } else {
//                btn.setBackgroundResource(R.drawable.ic_media_pause);
//            }
//
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.setDataSource(url);
//            mediaPlayer.prepareAsync();
//
//
//
//
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_radio);
//        }








