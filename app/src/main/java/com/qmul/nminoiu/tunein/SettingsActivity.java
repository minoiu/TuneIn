package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

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



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            setContentView(R.layout.app_bar_settings);

            //retrieve fullnames
            //final List<User> users = new ArrayList<User>();

            db = FirebaseDatabase.getInstance().getReference().child("Users");
            db1 = FirebaseDatabase.getInstance().getReference().child("Songs");

            ulistView = (ListView)findViewById(R.id.plistView);
            uadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,users);
            ulistView.setAdapter(uadapter);

            slistView = (ListView)findViewById(R.id.slistView);
            sadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,songs);
            slistView.setAdapter(sadapter);

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

                    for(DataSnapshot child : children){
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

                    for(DataSnapshot child : children){
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


            ulistView = (ListView)findViewById(R.id.plistView);
            uadapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,users);
            ulistView.setAdapter(uadapter);

            slistView = (ListView)findViewById(R.id.slistView);
            sadapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,songs);
            slistView.setAdapter(sadapter);

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
            View hView =  navigationView.getHeaderView(0);
            TextView nav_user = (TextView)hView.findViewById(R.id.emailProfile);
            nav_user.setText(getIntent().getExtras().getString("Email"));
            toolbar.setTitle("Profile");

            searchLayout = (LinearLayout)findViewById(R.id.searchLayout);
            searchView = (MaterialSearchView)findViewById(R.id.search_view);
            searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

                @Override
                public void onSearchViewShown() {
                    searchLayout.setVisibility(View.VISIBLE);
                    fab.setVisibility(View.INVISIBLE);


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

                    if(newText.toLowerCase() != null && !newText.toLowerCase().isEmpty()){

                        List<String> ulistFound = new ArrayList<String>();

                        for(String item:users){


                            if(item.toLowerCase().contains(newText.toLowerCase()))

                                ulistFound.add(item);

                        }



                        ArrayAdapter uadapter = new ArrayAdapter(SettingsActivity.this,android.R.layout.simple_list_item_1,ulistFound);

                        ulistView.setAdapter(uadapter);


                        List<String> slistFound = new ArrayList<String>();

                        for(String item:songs){

                            if(item.toLowerCase().contains(newText.toLowerCase()))

                                slistFound.add(item);

                        }



                        ArrayAdapter sadapter = new ArrayAdapter(SettingsActivity.this,android.R.layout.simple_list_item_1,slistFound);

                        slistView.setAdapter(sadapter);
                    }

                    else{

                        //if search text is null

                        //return default

                        ArrayAdapter uadapter = new ArrayAdapter(SettingsActivity.this,android.R.layout.simple_list_item_1,users);

                        ulistView.setAdapter(uadapter);
//
                        ArrayAdapter sadapter = new ArrayAdapter(SettingsActivity.this,android.R.layout.simple_list_item_1,songs);

                        slistView.setAdapter(sadapter);
//
                    }

                    return true;

                }

            });

        }

        @Override

        public boolean onCreateOptionsMenu(Menu menu) {


            getMenuInflater().inflate(R.menu.menu_item,menu);

            MenuItem item = menu.findItem(R.id.action_search);

            searchView.setMenuItem(item);

            return true;

        }

        //nav_header_settings


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

        //trying media player




    }


