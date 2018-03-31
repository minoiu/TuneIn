package com.qmul.nminoiu.tunein;

import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
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
import java.util.List;
import java.util.Scanner;

import static com.qmul.nminoiu.tunein.UserDetails.song;

/**
 * Created by nicoleta on 16/10/2017.
 */
public class AdapterShared extends BaseAdapter {

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
        private LinearLayout searchLayout;

    /**
     * Instantiates a new Adapter shared.
     *
     * @param context the context
     * @param items   the items
     */
    public AdapterShared(Context context, List<RowItem> items) {
            mContext = context;
            this.rowItems = items;
        }

        private class ViewHolder {
            ImageView imageView;
            TextView txtTitle;
        }

        //return no of rown
        @Override
        public int getCount() {
            return rowItems.size();
        }

        //return row from position
        @Override
        public Object getItem(int position) {
            return rowItems.get(position);
        }

        //return index from position
        @Override
        public long getItemId(int position) {
            return rowItems.indexOf(getItem(position));
        }

        //handling click on shared playlists
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {

            com.qmul.nminoiu.tunein.AdapterShared.ViewHolder holder = null;
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row, null);
                holder = new com.qmul.nminoiu.tunein.AdapterShared.ViewHolder();
                holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
                holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
                convertView.setTag(holder);
            } else {
                holder = (com.qmul.nminoiu.tunein.AdapterShared.ViewHolder) convertView.getTag();
            }

            final RowItem rowItem = (RowItem) getItem(position);
            holder.txtTitle.setText(rowItem.getTitle());
            holder.imageView.setImageResource(rowItem.getImageId());
            UserDetails.dwn = false;
            UserDetails.liked = false;
            searchLayout = (LinearLayout) convertView.findViewById(R.id.searchLayout);
            sender = firebaseAuth.getCurrentUser().getEmail();

            //click on shared icon
            try {
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String playlist = ((PlaylistSongs) mContext).getBarTitle();
                            FirebaseAuth fb;
                            fb = FirebaseAuth.getInstance();
                            String ID;
                            ID = fb.getCurrentUser().getUid();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Fullname").child(ID).child("Name");
                            mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    UserDetails.fullname = dataSnapshot.getValue().toString();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }

                            });

                        //retrieve friend id from Firebase
                        final String friend = rowItem.getTitle();
                        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("ID").child(friend).child("Id");

                        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                            @Override
                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                UserDetails.username = dataSnapshot.getValue().toString();
                                DatabaseReference delSharedPlRef = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites").child(UserDetails.username);
                                delSharedPlRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            String key = snapshot.getKey();

                                            if(key.equals(UserDetails.fullname)){
                                                for(DataSnapshot s : dataSnapshot.child(UserDetails.fullname).getChildren()){
                                                    String play = s.getValue().toString();
                                                    if(play.equals(playlist)){
                                                        String key1 = s.getKey().toString();
                                                        dataSnapshot.child(UserDetails.fullname).child(key1).getRef().removeValue();
                                                    }
                                                }
                                            }
                                        }
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

                        //retrieve shared playlist from Firebase
                        DatabaseReference shared = FirebaseDatabase.getInstance().getReference().child("SharedPlaylists").child(ID).child(friend);
                        shared.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String key = snapshot.getKey();
                                    if(dataSnapshot.child(key).getValue().toString().equals(playlist))
                                        dataSnapshot.child(key).getRef().removeValue();
                                        Toast.makeText(mContext.getApplicationContext(), "Your playlist is no longer shared with " + friend, Toast.LENGTH_SHORT).show();
                                        rowItems.remove(rowItem);
                                        notifyDataSetChanged();
                                        }
                                    }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });

            } catch(Exception e)

            {
                e.printStackTrace();
            }
            return convertView;
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



