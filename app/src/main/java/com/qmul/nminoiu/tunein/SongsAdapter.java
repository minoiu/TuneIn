package com.qmul.nminoiu.tunein;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.qmul.nminoiu.tunein.CustomAdapter;
import com.qmul.nminoiu.tunein.MyPlaylists;
import com.qmul.nminoiu.tunein.PlaylistSongs;
import com.qmul.nminoiu.tunein.R;
import com.qmul.nminoiu.tunein.RowItem;
import com.qmul.nminoiu.tunein.UserDetails;

import java.io.File;
import java.io.IOException;
import java.util.List;
import android.widget.PopupMenu;

import static com.qmul.nminoiu.tunein.UserDetails.song;

/**
 * Created by nminoiu on 21/08/2017.
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
    private LinearLayout searchLayout;


    public SongsAdapter(Context context, List<RowItem> items) {
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

        MyPlaylists mp = new MyPlaylists();
        searchLayout = (LinearLayout) convertView.findViewById(R.id.searchLayout);




        // mp.showMenu(holder.imageView);

        //buttons=(RelativeLayout) convertView.findViewById(R.id.buttons);
        try {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    switch (v.getId()) {
                        case R.id.icon:

                            PopupMenu popup = new PopupMenu(mContext.getApplicationContext(), v);
                            popup.getMenuInflater().inflate(R.menu.songoptions,
                                    popup.getMenu());

                            final Menu popupMenu = popup.getMenu();
//
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {
                                        case R.id.listenwith:

                                            //Or Some other code you want to put here.. This is just an example.
                                            Toast.makeText(mContext.getApplicationContext(), " Listen clicked " + " : " + rowItem.getTitle(), Toast.LENGTH_LONG).show();

                                            break;

                                        case R.id.down:

                                            ID = firebaseAuth.getCurrentUser().getUid();
                                            String song = rowItem.getTitle();
                                            checkDownloaded(song);

                                            if(UserDetails.dwn){
                                                Toast.makeText(mContext.getApplicationContext(), song + " is already downloaded", Toast.LENGTH_LONG).show();
                                            } else {
                                                addToDownloads(song);
                                                download(song);
                                                Toast.makeText(mContext.getApplicationContext(), "Downloading... ", Toast.LENGTH_SHORT).show();
                                            }

                                            break;

                                        case R.id.like:

                                            String songName = rowItem.getTitle();
                                            checkLiked(songName);

                                            if(UserDetails.liked){
                                                Toast.makeText(mContext.getApplicationContext(), songName + " is already in your favourites", Toast.LENGTH_LONG).show();
                                            } else {
                                                addToFavourites(songName);
                                            }

                                            break;

                                        case R.id.share:

                                            Intent intent = new Intent(mContext, Users.class);
                                            intent.putExtra("Uniqid","FromSongsAdapter");
                                            intent.putExtra("Song", rowItem.getTitle());
                                            mContext.startActivity(intent);

                                            break;

                                        case R.id.addto:
                                            String songToAdd = rowItem.getTitle();
                                            Intent i = new Intent(mContext.getApplicationContext(), PlaylistsActivity.class);
                                            i.putExtra("Song", songToAdd);
                                            mContext.startActivity(i);
                                            break;

                                        case R.id.delete:

                                            Toast.makeText(mContext.getApplicationContext(), "listen with " + " : " + rowItem.getTitle(), Toast.LENGTH_LONG).show();

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

    private void addToFavourites(String songName) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("LovedSongs").child(ID);
        playRef.push().setValue(songName);
        Toast.makeText(mContext.getApplicationContext(), songName + " was added to your favourites", Toast.LENGTH_SHORT).show();
    }

    private void checkLiked(final String songName) {
        lovedSongsRef = FirebaseDatabase.getInstance().getReference().child("LovedSongs").child(ID);
        lovedSongsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String lovedsong = dataSnapshot.getValue(String.class);

                if (lovedsong.equals(songName)) {
                    UserDetails.liked = true;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String lovedSong = dataSnapshot.getValue(String.class);

                if (lovedSong.equals(songName)) {
                    UserDetails.liked = false;
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void checkDownloaded(final String song) {
        dwnSongRef = FirebaseDatabase.getInstance().getReference().child("DownloadedSongs").child(ID);
        dwnSongRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String dwnsong = dataSnapshot.getValue(String.class);

                if (dwnsong.equals(song)) {
                    UserDetails.dwn = true;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String dwnsong = dataSnapshot.getValue(String.class);

                if (dwnsong.equals(song)) {
                    UserDetails.dwn = false;
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

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


}
