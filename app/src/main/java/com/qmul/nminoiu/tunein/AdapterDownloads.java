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

/**
 * Created by nicoleta on 15/10/2017.
 */
public class AdapterDownloads extends BaseAdapter {

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
     * Instantiates a new Adapter downloads.
     *
     * @param context the context
     * @param items   the items
     */
    public AdapterDownloads(Context context, List<RowItem> items) {
        mContext = context;
        this.rowItems = items;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    //return number of rows
    @Override
    public int getCount() {
        return rowItems.size();
    }

    //return position of row
    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    //return index of row
    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    //handling downloads menu clicks
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        AdapterDownloads.ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
            holder = new AdapterDownloads.ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        } else {
            holder = (AdapterDownloads.ViewHolder) convertView.getTag();
        }

        final RowItem rowItem = (RowItem) getItem(position);
        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());
        UserDetails.dwn = false;
        UserDetails.liked = false;
        searchLayout = (LinearLayout) convertView.findViewById(R.id.searchLayout);
        sender = firebaseAuth.getCurrentUser().getEmail();
        dwnList = new ArrayList<String>();
        likedList = new ArrayList<String>();

        try {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {
                        case R.id.icon:

                            PopupMenu popup = new PopupMenu(mContext.getApplicationContext(), v);
                            popup.getMenuInflater().inflate(R.menu.dwnoptions,
                                    popup.getMenu());
//
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {

                                        //click on listen with
                                        case R.id.listenwith:

                                            Intent intent = new Intent(mContext, FollowersActivity.class);
                                            intent.putExtra("Uniqid","FSAdapter");
                                            intent.putExtra("Song", rowItem.getTitle());
                                            UserDetails.oldIntent="Downloads";

                                            intent.putExtra("Playlist", "");
                                            mContext.startActivity(intent);

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
                                            i.putExtra("Name", "");
                                            UserDetails.oldIntent="Downloads";
                                            i.putExtra("Song", rowItem.getTitle());
                                            mContext.startActivity(i);

                                            break;

                                        //click on add song to playlist
                                        case R.id.addto:

                                            String songToAdd = rowItem.getTitle();
                                            Intent newIntent = new Intent(mContext, PlaylistsActivity.class);
                                            newIntent.putExtra("Uniqid","AdapterAllSongs");
                                            newIntent.putExtra("Song", songToAdd);
                                            UserDetails.oldIntent="Downloads";
                                            newIntent.putExtra("Name", "");
                                            mContext.startActivity(newIntent);
                                            mContext.startActivity(newIntent);
                                            break;

                                        //click on delete song
                                        case R.id.delete:

                                            final String songToDelete = rowItem.getTitle();
                                            delSongRef = FirebaseDatabase.getInstance().getReference().child("DownloadedSongs").child(ID);
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

    //add song to favourites in Firebase
    private void addToFavourites(String songName) {
        Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
        Firebase playRef = ref.child("LovedSongs").child(ID);
        playRef.push().setValue(songName);
        Toast.makeText(mContext.getApplicationContext(), songName + " was added to your favourites", Toast.LENGTH_SHORT).show();
    }

    //add song to liked list
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

    //check if song is already in liked
    private void checkSongInLiked(String song) {
        if(likedList.contains(song)){
            Toast.makeText(mContext, song + " is already in you favourites", Toast.LENGTH_SHORT).show();
        } else {
            addToFavourites(song);
        }
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

