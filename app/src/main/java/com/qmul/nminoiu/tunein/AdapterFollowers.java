package com.qmul.nminoiu.tunein;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.qmul.nminoiu.tunein.UserDetails.song;
import static com.qmul.nminoiu.tunein.UserDetails.username;

/**
 * Created by nicoleta on 26/10/2017.
 */
public class AdapterFollowers extends BaseAdapter {

    private Context mContext;
    List<RowItem> rowItems;
    private RelativeLayout buttons;
    private FirebaseStorage mStorage;
    public File storagePath;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String ID = firebaseAuth.getCurrentUser().getUid();
    private Menu menu;
    private DatabaseReference dwnSongRef;
    private DatabaseReference lovedSongsRef;
    private DatabaseReference delSongRef;
    private String sender;
    private ArrayList<String> dwnList;
    private ArrayList<String> followersList;
    private DatabaseReference mDatabase1;
    private LinearLayout searchLayout;


    /**
     * Instantiates a new Adapter followers.
     *
     * @param context the context
     * @param items   the items
     */
    public AdapterFollowers(Context context, List<RowItem> items) {
        mContext = context;
        this.rowItems = items;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    //return no of rows
    @Override
    public int getCount() {
        return rowItems.size();
    }

    //return item from position
    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    //return index of item
    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    //handling followers menu options
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        AdapterFollowers.ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
            holder = new AdapterFollowers.ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        } else {
            holder = (AdapterFollowers.ViewHolder) convertView.getTag();
        }

        final RowItem rowItem = (RowItem) getItem(position);

        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());
        followersList = new ArrayList<String>();
        UserDetails.dwn = false;
        UserDetails.liked = false;
        searchLayout = (LinearLayout) convertView.findViewById(R.id.searchLayout);
        sender = firebaseAuth.getCurrentUser().getEmail();

        try {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {
                        case R.id.icon:

                            PopupMenu popup = new PopupMenu(mContext.getApplicationContext(), v);
                            popup.getMenuInflater().inflate(R.menu.menu_followers,
                                    popup.getMenu());
//
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {

                                        //click on follow back
                                        case R.id.followback:

                                            final String friendTofollow = rowItem.getTitle();
                                            DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(friendTofollow).child("Email");
                                            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Following").child(ID);

                                            mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    UserDetails.receiver = dataSnapshot.getValue().toString();
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                            mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.hasChild(friendTofollow)){
                                                        Toast.makeText(mContext.getApplicationContext(), "Already following " + friendTofollow, Toast.LENGTH_SHORT).show();
                                                    }
                                                    else{
                                                        final Map<String, Object> map = new HashMap<String, Object>();
                                                        map.put("Date", getDate());
                                                        mDatabase.child(friendTofollow).updateChildren(map);
                                                        Toast.makeText(mContext.getApplicationContext(), "You are now following " + friendTofollow, Toast.LENGTH_SHORT).show();
                                                        addToFollowers(friendTofollow);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {
                                                }
                                            });

                                            break;

                                        default:
                                            break;
                                    }

                                    return true;
                                }

                            });

                            break;

                        default:
                            break;
                    }


                }
            });

        }catch(Exception e)

        {
            e.printStackTrace();
        }

        //handling click on row text
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String user = rowItem.getTitle().toString();
                UserDetails.chatWith = user;
                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");
                dbF.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
                        UserDetails.friendID = friendID;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //get intent followers to identify previous activity
                Intent i = ((FollowersActivity) mContext).getIntent();
                if (i != null) {
                    if (i.hasExtra("Uniqid")) {
                        String uniqid = i.getStringExtra("Uniqid");
                        if (uniqid.equals("FromSongAdapter")) {
                            String song = i.getStringExtra("Song");
                            Intent intent = new Intent(mContext, Chat.class);
                            intent.putExtra("Uniqid", "FromFollowersShare");
                            intent.putExtra("Friend", user);
                            intent.putExtra("Song", song);
                            mContext.startActivity(intent);
                        } else if (uniqid.equals("FSAdapter")) {
                            String songToJoin = i.getStringExtra("Song");
                            String playlist = i.getStringExtra("Name");
                            getReceiver(songToJoin, user, playlist);
                        } else if (uniqid.equals("AdapterAllSongs")) {
                            String songToJoin = i.getStringExtra("Song");
                            getReceiver(songToJoin, user, "");
                        } else if (uniqid.equals("FromConversations")) {
                            String myname = i.getStringExtra("Myname");
                            Intent intent = new Intent(mContext, Chat.class);
                            intent.putExtra("Name", user);
                            intent.putExtra("Uniqid", "FromUsers");
                            UserDetails.oldIntent = "FromUsers";
                            intent.putExtra("Friend", user);
                            mContext.startActivity(intent);
                        } else if(uniqid.equals("FromSettingsMenu")){
                            final Intent intent = new Intent(mContext, ProfileActivity.class);
                            intent.putExtra("FriendName", user);
                            intent.putExtra("FriendId", UserDetails.friendID);
                            mContext.startActivity(intent);
                        }

                    }

                }

            }

        });

        return convertView;
    }

    //get receiver to send notification
    private void getReceiver(final String song, final String user, final String playlist) {
        mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(user).child("Email");
        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.receiver = dataSnapshot.getValue().toString();
                sendListenWithNotification(song, user);
                Toast.makeText(mContext, "The invitation to " + user + " was sent.", Toast.LENGTH_SHORT).show();

                Intent goBack = new Intent(mContext, Chat.class);
                goBack.putExtra("Uniqid", "FromFollowersListeWith");
                goBack.putExtra("FriendName", user);
                goBack.putExtra("Song", song);
                goBack.putExtra("Name", playlist);
                mContext.startActivity(goBack);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //add to followers in Firebase
    private void addToFollowers(final String username) {

        final DatabaseReference followersdbN = FirebaseDatabase.getInstance().getReference().child("FollowersNames");
        final DatabaseReference followersdb = FirebaseDatabase.getInstance().getReference().child("Followers").child(username);

        followersdb.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String me = FirebaseAuth.getInstance().getCurrentUser().getUid();
                final Map<String, Object> map = new HashMap<String, Object>();
                map.put("Date", getDate());
                followersdb.child(me).updateChildren(map);
                Toast.makeText(mContext.getApplicationContext(), "You are now a follower for " + username, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        followersdbN.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ID").child(username).child("Id");

                mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserDetails.fullname = dataSnapshot.getValue().toString();

                        Toast.makeText(mContext.getApplicationContext(), "Friend ID" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
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

    /**
     * Get date string.
     *
     * @return the string
     */
    public String getDate(){
        Date date = new Date();
        Date newDate = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String stringdate = dt.format(newDate);
        return stringdate;
    }

    //send following notification
    private void sendNotification(final String myname)
    {
        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Emails").child(username).child("Email");
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Following").child(ID);

        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.receiver = dataSnapshot.getValue().toString();
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

                    if (RealTimeActivity.loggedEmail.equals(sender)) {
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
    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        return true;
    }

    //send listed with invitation notification
    private void sendListenWithNotification(final String songToJoin, String user) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;

                    if (RealTimeActivity.loggedEmail.equals(sender)) {
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
                                + "\"contents\": {\"en\": \"" + UserDetails.fullname + " invited you to listen to '" + songToJoin + "' together!\"},"
                                + "\"buttons\":[{\"id\": \"listenwith\", \"text\": \"Join\"}]"
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

