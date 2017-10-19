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
import java.util.List;
import java.util.Scanner;

import static com.qmul.nminoiu.tunein.UserDetails.song;
import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;


/**
 * Created by nicoleta on 16/10/2017.
 */

public class AdapterSharedWithMe extends BaseAdapter {

    private Context mContext;
    List<RowItem> rowItems;
    private RelativeLayout buttons;
    private FirebaseStorage mStorage;
    public File storagePath;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private Menu menu;
    private DatabaseReference dwnSongRef;
    private DatabaseReference lovedSongsRef;
    private DatabaseReference delSongRef;
    private String sender;
    private String ID = firebaseAuth.getCurrentUser().getUid();
    private ArrayList<String> dwnList;
    private ArrayList<String> likedList;



    private LinearLayout searchLayout;


    public AdapterSharedWithMe(Context context, List<RowItem> items) {
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

        AdapterSharedWithMe.ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
            holder = new AdapterSharedWithMe.ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        } else {
            holder = (AdapterSharedWithMe.ViewHolder) convertView.getTag();
        }

        final RowItem rowItem = (RowItem) getItem(position);

        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());

        MyPlaylists mp = new MyPlaylists();
        UserDetails.dwn = false;
        UserDetails.liked = false;
        dwnList = new ArrayList<String>();
        likedList = new ArrayList<String>();
        searchLayout = (LinearLayout) convertView.findViewById(R.id.searchLayout);
        sender = firebaseAuth.getCurrentUser().getEmail();
        final String playlist = ((SharedPlaylistSongs) mContext).getBarTitle();



        // mp.showMenu(holder.imageView);

        //buttons=(RelativeLayout) convertView.findViewById(R.id.buttons);
        try {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    switch (v.getId()) {
                        case R.id.icon:

                            PopupMenu popup = new PopupMenu(mContext.getApplicationContext(), v);
                            popup.getMenuInflater().inflate(R.menu.sharedplaylistsongs,
                                    popup.getMenu());

                            final Menu popupMenu = popup.getMenu();
//
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {
                                        case R.id.listenwith:

                                            Intent intent = new Intent(mContext, FollowersActivity.class);
                                            intent.putExtra("Uniqid","FSAdapter");
                                            intent.putExtra("Song", rowItem.getTitle());
                                            intent.putExtra("Name", playlist);
                                            mContext.startActivity(intent);

                                            //Or Some other code you want to put here.. This is just an example.
                                          //  Toast.makeText(mContext.getApplicationContext(), " Listen clicked " + " : " + rowItem.getTitle(), Toast.LENGTH_LONG).show();

                                            break;

                                        case R.id.down:

                                            ID = firebaseAuth.getCurrentUser().getUid();
                                            String song = rowItem.getTitle();
                                            addDwnToList(song);

//                                            if(UserDetails.dwn){
//                                                Toast.makeText(mContext.getApplicationContext(), song + " is already downloaded", Toast.LENGTH_LONG).show();
//                                            } else {
//                                                addToDownloads(song);
//                                                download(song);
//                                                Toast.makeText(mContext.getApplicationContext(), "Downloading... ", Toast.LENGTH_SHORT).show();
//                                            }

                                            break;

                                        case R.id.like:

                                            String songName = rowItem.getTitle();
                                            addToLikedList(songName);

                                            break;

                                        case R.id.share:

                                            Intent i = new Intent(mContext, FollowersActivity.class);
                                            i.putExtra("Uniqid","FromSongAdapter");
                                            i.putExtra("Name", playlist);
                                            i.putExtra("Song", rowItem.getTitle());
                                            mContext.startActivity(i);

                                            break;

                                        case R.id.addto:
                                            String playlistName = ((SharedPlaylistSongs) mContext).getBarTitle();
                                            String songToAdd = rowItem.getTitle();
                                            Intent newIntent = new Intent(mContext.getApplicationContext(), PlaylistsActivity.class);
                                            newIntent.putExtra("Uniqid","FSAdapter");
                                            newIntent.putExtra("Song", songToAdd);
                                            newIntent.putExtra("Name", playlistName);
                                            mContext.startActivity(newIntent);
                                            break;

                                        case R.id.delete:

                                            final String songToDelete = rowItem.getTitle();
                                            PlaylistSongs ps = new PlaylistSongs();

                                            String playlist = ((PlaylistSongs) mContext).getBarTitle();
                                           // Toast.makeText(mContext.getApplicationContext(), "playlist " + ": " + playlist, Toast.LENGTH_LONG).show();


                                            delSongRef = FirebaseDatabase.getInstance().getReference().child("PlaylistSongs").child(ID).child(playlist);
                                            delSongRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        String key = snapshot.getKey();

                                                        if (dataSnapshot.child(key).getValue().equals(songToDelete)) {
                                                            delSongRef.child(key).getRef().setValue(null);
                                                            notifyDataSetChanged();
                                                            rowItems.remove(rowItem);
                                                        }
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


        return convertView;
    }

        private void addToLikedList(final String songName) {
            likedList.clear();
            final DatabaseReference dwnSongRef2 = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);
            dwnSongRef2.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey().toString();
                        String likedsong = dataSnapshot.child(key).getValue().toString();
                        likedList.add(likedsong);
                    }
                    checkSongInLiked(songName);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    private void checkSongInLiked(String song) {
        if(likedList.contains(song)){
            Toast.makeText(mContext, song + " is already in you favourites", Toast.LENGTH_SHORT).show();
        } else {
            addToFavourites(song);
        }
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
                        Toast.makeText(mContext.getApplicationContext(), songName + " is already in your favourites.", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(mContext.getApplicationContext(), song + " is already downloaded", Toast.LENGTH_SHORT).show();
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


}
