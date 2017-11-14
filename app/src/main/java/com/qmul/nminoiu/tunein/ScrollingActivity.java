package com.qmul.nminoiu.tunein;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.qmul.nminoiu.tunein.UserDetails.username;

/**
 * Created by nicoledumitrascu on 30/06/2017.
 */

public class ScrollingActivity extends AppCompatActivity {

    public static String fullname;
    private DatabaseReference mDatabase;
    private DatabaseReference followersdb;
    private DatabaseReference followersdbN;

    private DatabaseReference mDatabase1;
    private String sender;
    private FirebaseAuth firebaseAuth;
    private String ID;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        final String username = getIntent().getExtras().getString("User");

        final TextView name = (TextView) findViewById(R.id.name);
        name.setText(username);
        firebaseAuth = FirebaseAuth.getInstance();
        final String user = firebaseAuth.getCurrentUser().getUid();
        sender = firebaseAuth.getCurrentUser().getEmail();
        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();


        //user.setFullname(UserDetails.chatWith);

        fullname = UserDetails.chatWith;

        FloatingActionButton follow = (FloatingActionButton) findViewById(R.id.fab);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ScrollingActivity.this, "fullname: " + fullname, Toast.LENGTH_SHORT).show();


                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(name.getText().toString()).child("Email");
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Following").child(user);

                mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserDetails.receiver = dataSnapshot.getValue().toString();
                        //Toast.makeText(RequestActivity.this, receiver, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(username)){
                            Toast.makeText(ScrollingActivity.this, "Already following " + username, Toast.LENGTH_SHORT).show();
                        }
                        else{
                            final Map<String, Object> map = new HashMap<String, Object>();
                            map.put("Date", getDate());
                            mDatabase.child(username).updateChildren(map);
                            Toast.makeText(ScrollingActivity.this, "You are now following " + username, Toast.LENGTH_SHORT).show();
                            addToFollowers(username);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addToFollowers(final String username) {

        followersdb = FirebaseDatabase.getInstance().getReference().child("Followers").child(username);
        followersdb.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String me = FirebaseAuth.getInstance().getCurrentUser().getUid();
                final Map<String, Object> map = new HashMap<String, Object>();
                map.put("Date", getDate());
                followersdb.child(me).updateChildren(map);
                Toast.makeText(ScrollingActivity.this, "You are now a follower for " + username, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        followersdbN = FirebaseDatabase.getInstance().getReference().child("FollowersNames");
        followersdbN.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ID").child(username).child("Id");

                mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserDetails.fullname = dataSnapshot.getValue().toString();
                        Toast.makeText(ScrollingActivity.this, "Friend ID" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

                DatabaseReference mfullname = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

                mfullname.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String myFullname = dataSnapshot.getValue().toString();
                        UserDetails.myname = myFullname;
                        sendNotification(myFullname);
                        followersdbN.child(UserDetails.fullname).push().setValue(myFullname);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed(){
        Intent backMainTest = new Intent(this,SettingsActivity.class);
        startActivity(backMainTest);
        finish();
    }

    private void sendNotification(final String myname)
    {
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(username).child("Email");
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Following").child(ID);

        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.receiver = dataSnapshot.getValue().toString();
                //Toast.makeText(RequestActivity.this, receiver, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
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

                        String strJsonBody = "{"
                                + "\"app_id\": \"99ce9cc9-d20d-4e6b-ba9b-de2e95d3ec00\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \"" + myname +" is now following you\"}"
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

    public String getDate(){
        Date date = new Date();
        Date newDate = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String stringdate = dt.format(newDate);
        return stringdate;
    }


}