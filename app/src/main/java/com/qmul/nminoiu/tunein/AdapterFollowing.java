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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.qmul.nminoiu.tunein.UserDetails.song;
import static com.qmul.nminoiu.tunein.UserDetails.username;

/**
 * Created by nicoleta on 26/10/2017.
 */

public class AdapterFollowing extends BaseAdapter {

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
    private ArrayList<String> likedList;


    private LinearLayout searchLayout;


    public AdapterFollowing(Context context, List<RowItem> items) {
        mContext = context;
        this.rowItems = items;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        AdapterFollowing.ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
            holder = new AdapterFollowing.ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        } else {
            holder = (AdapterFollowing.ViewHolder) convertView.getTag();
        }

        final RowItem rowItem = (RowItem) getItem(position);

        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());

        likedList = new ArrayList<String>();
        UserDetails.dwn = false;
        UserDetails.liked = false;
        searchLayout = (LinearLayout) convertView.findViewById(R.id.searchLayout);
        sender = firebaseAuth.getCurrentUser().getEmail();
        //final String playlist = ((Songs) mContext).getBarTitle();



        // mp.showMenu(holder.imageView);

        //buttons=(RelativeLayout) convertView.findViewById(R.id.buttons);
        try {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    switch (v.getId()) {
                        case R.id.icon:

                            PopupMenu popup = new PopupMenu(mContext.getApplicationContext(), v);
                            popup.getMenuInflater().inflate(R.menu.following_menu,
                                    popup.getMenu());

                            final Menu popupMenu = popup.getMenu();
//
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {
                                        case R.id.unfollow:

                                            final String friendToUnfollow = rowItem.getTitle();


                                            final DatabaseReference unfollowRef = FirebaseDatabase.getInstance().getReference().child("Following").child(ID);
                                            unfollowRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        String key = snapshot.getKey();
                                                        if (key.equals(friendToUnfollow)) {
                                                            Toast.makeText(mContext.getApplicationContext(), " following " + " : " + key, Toast.LENGTH_LONG).show();

                                                            unfollowRef.child(key).removeValue();
                                                            notifyDataSetChanged();
                                                            rowItems.remove(rowItem);
                                                            removeFromAllFollowing(friendToUnfollow);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }

                                                });

                                                //Or Some other code you want to put here.. This is just an example.

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

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String user = rowItem.getTitle().toString();

                DatabaseReference dbF = FirebaseDatabase.getInstance().getReference().child("ID").child(user).child("Id");

                dbF.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final String friendID = dataSnapshot.getValue().toString();
//                      Toast.makeText(PlaylistSongs.this, "my fullname " + UserDetails.fullname , Toast.LENGTH_SHORT).show();
                        UserDetails.friendID = friendID;

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                final Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                mContext.startActivity(intent);
            }

        });


        return convertView;
    }

    private void removeFromAllFollowing(final String friendToUnfollow) {

        final DatabaseReference followersdb = FirebaseDatabase.getInstance().getReference().child("Followers").child(friendToUnfollow);

        followersdb.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String me = FirebaseAuth.getInstance().getCurrentUser().getUid();
                followersdb.child(me).removeValue();
                Toast.makeText(mContext.getApplicationContext(), "You are no longer following " + friendToUnfollow, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        getFriendID(friendToUnfollow);
    }

    private void getFriendID(String friendName) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ID").child(friendName).child("Id");

        mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.fullname = dataSnapshot.getValue().toString();
                String FrID = dataSnapshot.getValue().toString();
                Toast.makeText(mContext.getApplicationContext(), "Friend ID" + UserDetails.fullname, Toast.LENGTH_SHORT).show();
                getMyFullname(FrID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private void getMyFullname(final String frID) {
        DatabaseReference mfullname = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");

        mfullname.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String myFullname = dataSnapshot.getValue().toString();
                deleteFromFNames(frID,myFullname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void deleteFromFNames(String frID, final String myFullname) {
        final DatabaseReference followersdbN = FirebaseDatabase.getInstance().getReference().child("FollowersNames").child(frID);

        followersdbN.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String key = snapshot.getKey();
                    if(dataSnapshot.child(key).getValue().toString().equals(myFullname)){
                        dataSnapshot.child(key).getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void addToFavourites(String songName) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("LovedSongs").child(ID);
        playRef.push().setValue(songName);
        Toast.makeText(mContext.getApplicationContext(), songName + " was added to your favourites", Toast.LENGTH_SHORT).show();
    }

    private void checkLiked(final String songName) {
        lovedSongsRef = FirebaseDatabase.getInstance().getReference().child("LovedSongs");
        lovedSongsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                    String key = snapshot.getKey().toString();
                    if (dataSnapshot.child(ID).child(key).getValue().toString().equals(songName)) {
                        Toast.makeText(mContext.getApplicationContext(), song + " is already in your favourites.", Toast.LENGTH_SHORT).show();
                        UserDetails.liked = true;
                    } else {
                        UserDetails.dwn = false;
                    }
                }
                if (!UserDetails.liked) {
                    addToFavourites(songName);
                    Toast.makeText(mContext.getApplicationContext(), "Downloading... ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addDwnToList(final String song) {
        dwnList.clear();
        final DatabaseReference dwnSongRef1 = FirebaseDatabase.getInstance().getReference().child("DownloadedSongs").child(ID);
        dwnSongRef1.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey().toString();
                    String dwnsong = dataSnapshot.child(key).getValue().toString();
                    dwnList.add(dwnsong);
                }
                checkSongInDwn(song);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void checkSongInDwn(String song) {
        if(dwnList.contains(song)){
            Toast.makeText(mContext, song + " is already downloaded", Toast.LENGTH_SHORT).show();
        } else {
            addToDownloads(song);
            download(song);
        }
    }


    private void checkDownloaded(final String song) {
        dwnSongRef = FirebaseDatabase.getInstance().getReference().child("DownloadedSongs");
        dwnSongRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(ID)) {
                    for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                        String key = snapshot.getKey().toString();
                        if (dataSnapshot.child(ID).child(key).getValue().toString().equals(song)) {
                            Toast.makeText(mContext.getApplicationContext(), song + " is already downloaded", Toast.LENGTH_LONG).show();
                            UserDetails.dwn = true;
                        } else {
                            UserDetails.dwn = false;
                        }
                    }
                    if (!UserDetails.dwn) {
                        addToDownloads(song);
                        download(song);
                        Toast.makeText(mContext.getApplicationContext(), "Downloading... ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    addToDownloads(song);
                    download(song);
                    Toast.makeText(mContext.getApplicationContext(), "Downloading... ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void download(String song) {
        mStorage = FirebaseStorage.getInstance();

        StorageReference storageReference = mStorage.getReferenceFromUrl("gs://tunein-633e5.appspot.com/bad boi muzik");
        StorageReference down = storageReference.child(song + ".mp3");

        storagePath = new File(mContext.getFilesDir(), "My_music");
        File localFile = new File(storagePath, song);
        try {
            localFile = File.createTempFile(song, ".mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        final File finalLocalFile = new File(storagePath, song);
        down.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                finalLocalFile.getAbsolutePath();
                Toast.makeText(mContext.getApplicationContext(), "Done downloading" , Toast.LENGTH_SHORT).show();
                //download.setVisibility(View.GONE);
                //downloadgreen.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }

    private void addToDownloads(String song) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("DownloadedSongs").child(ID);
        playRef.push().setValue(song);
    }

    public void updateList(List<RowItem> newlist) {
        rowItems.clear();
        rowItems.addAll(newlist);
        this.notifyDataSetChanged();
    }

    private void sendNotification()
    {

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
                                + "\"contents\": {\"en\": \"You have a new friend request!\"}"
                                + "\"button1\": {\"Accept\": \"Decline\"},"

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


}

