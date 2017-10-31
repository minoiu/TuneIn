package com.qmul.nminoiu.tunein;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;
import static com.qmul.nminoiu.tunein.R.layout.playlist;

/**
 * Created by nicoleta on 11/10/2017.
 */

public class FollowersActivity extends AppCompatActivity {
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    // ProgressDialog pd;
    ListView usersList;
    ArrayList<String> users = new ArrayList<>();
    private AdapterFollowers uadapter;
    private DatabaseReference db;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private DatabaseReference mDatabase2;
    private DatabaseReference mDatabase3;
    private LinearLayout play_toolbar;
    private List<RowItem> rowItems;





    private LinearLayout searchLayout;




    private DatabaseReference db1;
    private String value;
    private String sender;
    private String ID;


    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        rowItems = new ArrayList<RowItem>();

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String curUser = currentuser.getUid().toString();

        Intent i = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Followers");
        ID = firebaseAuth.getCurrentUser().getUid();
        sender = firebaseAuth.getCurrentUser().getEmail();

        DatabaseReference mDb = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

        mDb.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.myname = dataSnapshot.getValue().toString();
                DatabaseReference fdb = FirebaseDatabase.getInstance().getReference().child("FollowersNames").child(ID);
                fdb.addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        String value = dataSnapshot.getValue(String.class);
                        users.add(value);
                        RowItem item = new RowItem(R.drawable.options, value);

                        rowItems.add(item);
                        uadapter.notifyDataSetChanged();
                    }


                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        users.remove(dataSnapshot.getValue().toString());
                        uadapter.notifyDataSetChanged();
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
                public void onCancelled (DatabaseError databaseError){

                }
            });

        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

        mDatabase2.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                //Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        mDatabase3 = FirebaseDatabase.getInstance().getReference().child("Users").child(curUser);

        mDatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                UserDetails.username = value;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

                usersList = (ListView) findViewById(R.id.ulistView);
                uadapter = new AdapterFollowers(this, rowItems);
                usersList.setAdapter(uadapter);

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

                mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserDetails.fullname = dataSnapshot.getValue().toString();
                        //Toast.makeText(SettingsActivity.this, "Fullname" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        //searchView.bringToFront();//

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String user = ((TextView) view).getText().toString();
                        UserDetails.chatWith = user;
                        Intent i = getIntent();
                        if (i != null) {
                            if (i.hasExtra("Uniqid")) {
                                String uniqid = i.getStringExtra("Uniqid");

                                if (uniqid.equals("FromSongAdapter")) {
                                   // Toast.makeText(FollowersActivity.this, "from songsadapter ", Toast.LENGTH_SHORT).show();
                                    String song = i.getStringExtra("Song");
                                    Intent intent = new Intent(FollowersActivity.this, Chat.class);
                                    intent.putExtra("Uniqid", "FromFollowersShare");
                                    intent.putExtra("Name", user);
                                    intent.putExtra("Song", song);
                                    startActivity(intent);
                                } else if (uniqid.equals("FSAdapter")) {
                                    String songToJoin = i.getStringExtra("Song");
                                    String playlist = i.getStringExtra("Name");
                                    getReceiver(songToJoin, user, playlist);
                                    //Toast.makeText(FollowersActivity.this, "from sadapterfor notification " + songToJoin + user, Toast.LENGTH_SHORT).show();
                                } else if (uniqid.equals("AdapterAllSongs")) {
                                    // Toast.makeText(FollowersActivity.this, "from songsadapter ", Toast.LENGTH_SHORT).show();
                                    String songToJoin = i.getStringExtra("Song");
                                    getReceiver(songToJoin, user, "");
                                }
                            }
                        }

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
        Intent i = getIntent();
        if (i != null) {
            if (UserDetails.oldIntent.equals("Followers")) {
                Intent backMainTest = new Intent(this, PlaylistSongs.class);
                if (mediaPlayer.isPlaying()) {
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            } else if (UserDetails.oldIntent.equals("MySongs")) {
                Intent backMainTest = new Intent(this, Songs.class);
                if (mediaPlayer.isPlaying()) {
                    //backMainTest.putExtra("Song", track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            } else if (UserDetails.oldIntent.equals("Downloads")) {
                Intent backMainTest = new Intent(this, Downloads.class);
                if (mediaPlayer.isPlaying()) {
                   // backMainTest.putExtra("Song", track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            } else if (UserDetails.oldIntent.equals("Favourites")) {
                Intent backMainTest = new Intent(this, Favourites.class);
                if (mediaPlayer.isPlaying()) {
                   // backMainTest.putExtra("Song", track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            } else if (UserDetails.oldIntent.equals("Recents")) {
                Intent backMainTest = new Intent(this, LibraryActivity.class);
                if (mediaPlayer.isPlaying()) {
                   // backMainTest.putExtra("Song", track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            } else if (UserDetails.oldIntent.equals("FromSettingsMenu")) {
                Intent backMainTest = new Intent(this, SettingsActivity.class);
                if (mediaPlayer.isPlaying()) {
                    // backMainTest.putExtra("Song", track_title.getText().toString());
                }
                backMainTest.putExtra("Name", UserDetails.oldPlaylist);
                startActivity(backMainTest);
                finish();
            }


        }
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




//    @Override
//    public boolean onMenuOpened(int featureId, Menu menu) {
//        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
//            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
//                try {
//                    Method m = menu.getClass().getDeclaredMethod(
//                            "setOptionalIconsVisible", Boolean.TYPE);
//                    m.setAccessible(true);
//                    m.invoke(menu, true);
//                } catch (NoSuchMethodException e) {
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//        return super.onMenuOpened(featureId, menu);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_item, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
//        return true;
//    }

    private void getReceiver(final String song, final String user, final String playlist) {
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(user).child("Email");
        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.receiver = dataSnapshot.getValue().toString();
                sendListenWithNotification(song, user);
                Toast.makeText(FollowersActivity.this, "The invitation to " + user + " was sent.", Toast.LENGTH_SHORT).show();

                Intent goBack = new Intent(FollowersActivity.this, Chat.class);
                goBack.putExtra("Uniqid", "FromFollowersListeWith");
                goBack.putExtra("FriendName", user);
                goBack.putExtra("Song", song);
                goBack.putExtra("Name", playlist);
                startActivity(goBack);

                //Toast.makeText(RequestActivity.this, receiver, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getReceiver1(final String song, final String user) {
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(user).child("Email");
        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.receiver = dataSnapshot.getValue().toString();
                sendListenWithNotification(song, user);
                Toast.makeText(FollowersActivity.this, "The invitation to " + user + " was sent.", Toast.LENGTH_SHORT).show();

                Intent goBack = new Intent(FollowersActivity.this, Chat.class);
                goBack.putExtra("Uniqid", "FromFollowersListeWith");
                goBack.putExtra("FriendName", user);
                goBack.putExtra("Song", song);
                startActivity(goBack);

                //Toast.makeText(RequestActivity.this, receiver, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void sendListenWithNotification(final String songToJoin, String user) {

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    int SDK_INT = android.os.Build.VERSION.SDK_INT;
                    if (SDK_INT > 8) {
                        //notificationBuilder.setSmallIcon(R.drawable.ic_aphla_logo);

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        String send_email;

                        //This is a Simple Logic to Send Notification different Device Programmatically....
                        if (SettingsActivity.loggedEmail.equals(sender)) {
                            send_email = UserDetails.receiver;

                        } else {
                            send_email = sender;
                        }

                        try {
                            String jsonResponse;

                            URL url = new URL("https://onesignal.com/api/v1/notifications");
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setUseCaches(false);
                            con.setDoOutput(true);
                            con.setDoInput(true);

                            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                            con.setRequestProperty("Authorization", "Basic NmMxZDRiNjAtMzY5Ni00NDRhLThhZGEtODRkNmIzZTEzOWVm");
                            con.setRequestMethod("POST");

//                        String strJsonBody = "{'contents': {'en': 'The notification message or body'}," +
//                                "'app_id': ['99ce9cc9-d20d-4e6b-ba9b-de2e95d3ec00']'}" ;
                            //"'headings': {'en': 'Notification Title'}, " +
                            //"'big_picture': 'http://i.imgur.com/DKw1J2F.gif'}";

                            String strJsonBody = "{"
                                    + "\"app_id\": \"99ce9cc9-d20d-4e6b-ba9b-de2e95d3ec00\","

                                    + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                    + "\"data\": {\"foo\": \"bar\"},"
                                    + "\"contents\": {\"en\": \"" + UserDetails.fullname + " invited you to listen to '" + songToJoin + "' together!\"},"
                                    + "\"buttons\":[{\"id\": \"listenwith\", \"text\": \"Join\"}]"
                                    //+ "\"small_picture\": {\"@android:drawable/buttonorg.png\"}"
                                    + "}";

                            System.out.println("strJsonBody:\n" + strJsonBody);

                            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                            con.setFixedLengthStreamingMode(sendBytes.length);

                            OutputStream outputStream = con.getOutputStream();
                            outputStream.write(sendBytes);

                            int httpResponse = con.getResponseCode();
                            System.out.println("httpResponse: " + httpResponse);

                            if (httpResponse >= HttpURLConnection.HTTP_OK
                                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";

                                scanner.close();
                            } else {
                                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                                scanner.close();
                            }

                            System.out.println("jsonResponse:\n" + jsonResponse);

                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                }
            });
    }

}







