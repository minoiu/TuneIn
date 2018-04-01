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

import android.widget.PopupMenu;

/**
 * Created by nicoleta on 21/10/2017.
 */
public class SongsAdapter extends BaseAdapter {

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


    /**
     * Instantiates a new Songs adapter.
     *
     * @param context the context
     * @param items   the items
     */
    public SongsAdapter(Context context, List<RowItem> items) {
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

    //return id from position
    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    //handle click on song option menu
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        SongsAdapter.ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
            holder = new SongsAdapter.ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        } else {
            holder = (SongsAdapter.ViewHolder) convertView.getTag();
        }

        final RowItem rowItem = (RowItem) getItem(position);

        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());
        UserDetails.dwn = false;
        UserDetails.liked = false;
        dwnList = new ArrayList<String>();
        likedList = new ArrayList<String>();
        searchLayout = (LinearLayout) convertView.findViewById(R.id.searchLayout);
        sender = firebaseAuth.getCurrentUser().getEmail();
        final String playlist = ((PlaylistSongs) mContext).getBarTitle();

        try {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.icon:

                            PopupMenu popup = new PopupMenu(mContext.getApplicationContext(), v);
                            popup.getMenuInflater().inflate(R.menu.songoptions, popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    //click on listen with
                                    switch (item.getItemId()) {
                                        case R.id.listenwith:

                                            Intent intent = new Intent(mContext, FollowersActivity.class);
                                            intent.putExtra("Uniqid","FSAdapter");
                                            intent.putExtra("Song", rowItem.getTitle());
                                            UserDetails.oldIntent="Followers";
                                            UserDetails.oldPlaylist = playlist;
                                            intent.putExtra("Name", playlist);
                                            mContext.startActivity(intent);
                                            break;

                                            //click on download
                                        case R.id.down:

                                            ID = firebaseAuth.getCurrentUser().getUid();
                                            String song = rowItem.getTitle();
                                            addDwnToList(song);

                                            break;

                                        //click on like song
                                        case R.id.like:

                                            String songName = rowItem.getTitle();
                                            addToLikedList(songName);

                                            break;

                                        //click on share song
                                        case R.id.share:

                                            Intent i = new Intent(mContext, FollowersActivity.class);
                                            i.putExtra("Uniqid","FromSongAdapter");
                                            i.putExtra("Name", playlist);
                                            UserDetails.oldIntent="Followers";
                                            UserDetails.oldPlaylist = playlist;
                                            i.putExtra("Song", rowItem.getTitle());
                                            mContext.startActivity(i);

                                            break;

                                        //click on add to playlisy
                                        case R.id.addto:
                                            String playlistName = ((PlaylistSongs) mContext).getBarTitle();
                                            String songToAdd = rowItem.getTitle();
                                            Intent newIntent = new Intent(mContext, PlaylistsActivity.class);
                                            newIntent.putExtra("Uniqid","FSAdapter");
                                            newIntent.putExtra("Song", songToAdd);
                                            UserDetails.oldPlaylist = playlist;
                                            UserDetails.oldIntent="Followers";
                                            newIntent.putExtra("OldPlaylist", playlistName);
                                            newIntent.putExtra("Song", PlaylistSongs.track_title.getText().toString());
                                            mContext.startActivity(newIntent);
                                            break;

                                        //click on delete song
                                        case R.id.delete:

                                            final String songToDelete = rowItem.getTitle();
                                            String playlist = ((PlaylistSongs) mContext).getBarTitle();
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

    //add song to likes
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

    //check if song is in liked
    private void checkSongInLiked(String song) {
        if(likedList.contains(song)){
            Toast.makeText(mContext, song + " is already in you favourites", Toast.LENGTH_SHORT).show();
        } else {
            addToFavourites(song);
        }
    }

    //add song to downloads
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

    //check if song in downloads
    private void checkSongInDwn(String song) {
        if(dwnList.contains(song)){
            Toast.makeText(mContext, song + " is already downloaded", Toast.LENGTH_SHORT).show();
        } else {
            addToDownloads(song);
            download(song);
        }
    }

    //add song to favourites
    private void addToFavourites(String songName) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("LovedSongs").child(ID);
        playRef.push().setValue(songName);
        Toast.makeText(mContext.getApplicationContext(), songName + " was added to your favourites", Toast.LENGTH_SHORT).show();
    }

    //download song
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
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }

    //add to downloads
    private void addToDownloads(String song) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("DownloadedSongs").child(ID);
        playRef.push().setValue(song);
    }
}
