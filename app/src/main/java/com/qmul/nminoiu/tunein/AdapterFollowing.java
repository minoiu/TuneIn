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


    /**
     * Instantiates a new Adapter following.
     *
     * @param context the context
     * @param items   the items
     */
    public AdapterFollowing(Context context, List<RowItem> items) {
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

    //handling clicks on following menu items
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

        try {
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {
                        case R.id.icon:

                            PopupMenu popup = new PopupMenu(mContext.getApplicationContext(), v);
                            popup.getMenuInflater().inflate(R.menu.following_menu, popup.getMenu());
                            popup.show();
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {

                                    switch (item.getItemId()) {

                                        //click on unfollow friend
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

        //click on row item
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String user = rowItem.getTitle().toString();
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

                final Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("FriendName", user);
                intent.putExtra("FriendId", UserDetails.friendID);
                mContext.startActivity(intent);
            }

        });

        return convertView;
    }

    //remove friend from followers Firebase node
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

    //get friend id
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

    //get fullname
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

    //delete from Firebase followersNames
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

