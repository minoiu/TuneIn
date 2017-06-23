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
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.WidgetContainer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
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
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import android.view.View.OnClickListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SettingsActivity extends AppCompatActivity
            implements NavigationView.OnNavigationItemSelectedListener {

        MaterialSearchView searchView;
        LinearLayout searchLayout;
        private TextView email;
        private FirebaseAuth firebaseAuth;
        private String username = "";
        private static final String TAG = "MainActivity";
        private ArrayList<String> songs = new ArrayList<>();
        private ArrayList<String> users = new ArrayList<>();
        private DatabaseReference db;
        private DatabaseReference db1;
        private DatabaseReference db2;
        private ArrayAdapter<String> sadapter;
        private ArrayAdapter<String> uadapter;
        private ListView slistView;
        private ListView ulistView;
        private LinearLayout play_toolbar;
        private TextView track_title;
        private MediaPlayer mediaPlayer;
        private StorageReference storage;
        private boolean playPause;
        private boolean intialStage = true;
        private Button btn;
        private boolean play;
        private boolean pause;
        private String url;
        private Handler mResultHandler;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.app_bar_settings);

            //media player
            btn = (Button) findViewById(R.id.button);
            play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
            mediaPlayer = new MediaPlayer();


            //getting users and songs from database
            db = FirebaseDatabase.getInstance().getReference().child("Users");
            db1 = FirebaseDatabase.getInstance().getReference().child("Songs");
            db2 = FirebaseDatabase.getInstance().getReference().child("URL");

            ulistView = (ListView) findViewById(R.id.plistView);
            uadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
            ulistView.setAdapter(uadapter);

            slistView = (ListView) findViewById(R.id.slistView);
            sadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs);
            slistView.setAdapter(sadapter);

            slistView.setClickable(true);

            //event listener for users
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

            //event listener for songs
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

            db.child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child : children) {
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            db1.child("Songs").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for (DataSnapshot child : children) {
                       // Toast.makeText(SettingsActivity.this, dataSnapshot.toString().trim(), Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            //on create
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);

            //homepage toolbar
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Now Playing");
            toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

            //loading users in plistView
            ulistView = (ListView) findViewById(R.id.plistView);
            uadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
            ulistView.setAdapter(uadapter);

            //loading songs in slistView
            slistView = (ListView) findViewById(R.id.slistView);
            sadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, songs);

            //add click listeners for songs listview
            //showing clicked song in play_toolbar
            slistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String song = ((TextView) view).getText().toString();
                    play_toolbar.setVisibility(View.VISIBLE);
                    play_toolbar.requestLayout();
                    play_toolbar.bringToFront();

                    track_title = (TextView) findViewById(R.id.track_title);
                    track_title.setText(song);

                    Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                    Firebase songRef = ref.child("Test").child(song);

                    songRef.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
                        @Override
                        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {

                            for (com.firebase.client.DataSnapshot dsp : dataSnapshot.getChildren()) {
                                btn.setBackgroundResource(R.drawable.ic_media_pause);
                                play_toolbar = (LinearLayout) findViewById(R.id.play_toolbar);
                                play_toolbar.bringToFront();
                                play_toolbar.setVisibility(View.VISIBLE);
                                play_toolbar.requestLayout();
                                play_toolbar.invalidate();
                                url = String.valueOf(dsp.getValue());
                                startMusic(url);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                }
            });
            slistView.setAdapter(sadapter);

            // TODO: 21/06/2017 chat button and function
            final ImageButton fab = (ImageButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            //sliding menu settings
            //// TODO: 21/06/2017 add extra settings
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            //showing user's email on sliding drawer
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View hView = navigationView.getHeaderView(0);
            TextView nav_user = (TextView) hView.findViewById(R.id.emailProfile);
            nav_user.setText(getIntent().getExtras().getString("Email"));
            toolbar.setTitle("Profile");

            //search layout and functions
            searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
            searchView = (MaterialSearchView) findViewById(R.id.search_view);
            searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

                @Override
                public void onSearchViewShown() {
                    searchLayout.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.INVISIBLE);
                    play_toolbar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onSearchViewClosed() {
                    searchLayout.setVisibility(View.INVISIBLE);
                    fab.setVisibility(View.VISIBLE);
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
                        ArrayAdapter uadapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_list_item_1, users);
                        ulistView.setAdapter(uadapter);
                        ArrayAdapter sadapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_list_item_1, songs);
                        slistView.setAdapter(sadapter);
                    }
                    return true;
                }
            });
        }

        //showing searching layout
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_item, menu);
            MenuItem item = menu.findItem(R.id.action_search);
            searchView.setMenuItem(item);
            return true;
        }

        //when goign back from search layout
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

        //posible place to get the ids for the clicked song
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle actifon bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            return super.onOptionsItemSelected(item);
        }

        //sliding menu functions
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

        // TODO: 21/06/2017 posible hide keyboard method
        public static void hideSoftKeyboard(Activity activity) {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }

        public void startMusic(String link) {
            play_toolbar.setVisibility(View.VISIBLE);
            play_toolbar.bringToFront();

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
        }

        public void stopMusic(Integer length) {
            play_toolbar.setVisibility(View.VISIBLE);
            play_toolbar.bringToFront();
            length = mediaPlayer.getCurrentPosition();
            Button btn = (Button) this.findViewById(R.id.button);
            btn.setBackgroundResource(R.drawable.ic_media_play);
            mediaPlayer.pause();
        }


        //play song method with signle button background handling
        public void playPauseMusic(View v) {

            play_toolbar.setVisibility(View.VISIBLE);
            play_toolbar.bringToFront();

            Integer length = mediaPlayer.getCurrentPosition();
            if (mediaPlayer.isPlaying()) {
                length = mediaPlayer.getCurrentPosition();
                mediaPlayer.pause();
                Button btn = (Button) this.findViewById(R.id.button);
                btn.setBackgroundResource(R.drawable.ic_media_play);
            } else {
                mediaPlayer.seekTo(length);
                playFromPause(length,url);
                Button btn = (Button) this.findViewById(R.id.button);
                btn.setBackgroundResource(R.drawable.ic_media_pause);
            }
        }

        public void playFromPause(Integer time, String link){
            play_toolbar.setVisibility(View.VISIBLE);
            play_toolbar.bringToFront();
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
            mediaPlayer.seekTo(time);
            mediaPlayer.start();
            Button btn = (Button) this.findViewById(R.id.button);
            btn.setBackgroundResource(R.drawable.ic_media_pause);
        }
    }

