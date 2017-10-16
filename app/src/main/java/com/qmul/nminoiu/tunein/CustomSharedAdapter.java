package com.qmul.nminoiu.tunein;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.os.Build.ID;
import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;
import static com.qmul.nminoiu.tunein.R.id.track_title;

/**
 * Created by nicoleta on 16/10/2017.
 */

public class CustomSharedAdapter extends BaseAdapter {

    private Context mContext;
    List<RowItem> rowItems;
    private RelativeLayout buttons;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String ID = firebaseAuth.getCurrentUser().getUid();



    public CustomSharedAdapter(Context context, List<RowItem> items) {
        mContext = context;
        this.rowItems = items;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {

        CustomSharedAdapter.ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
            holder = new CustomSharedAdapter.ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            UserDetails.menuIcons.add(holder.imageView);

            convertView.setTag(holder);
        }
        else {
            holder = (CustomSharedAdapter.ViewHolder) convertView.getTag();
        }

        final RowItem rowItem = (RowItem) getItem(position);

        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());

        MyPlaylists mp = new MyPlaylists();


        // mp.showMenu(holder.imageView);

        //buttons=(RelativeLayout) convertView.findViewById(R.id.buttons);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSharingFriend(rowItem.getTitle().toString());

            }
        });

        return convertView;
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

    private void getSharingFriend(final String playlist) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("PlaylistsInvites");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(ID).exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.child(ID).getChildren()) {
                        String friend = snapshot.getKey();

                        for (DataSnapshot snap : dataSnapshot.child(ID).child(friend).getChildren()) {
                            String key = snap.getKey();

                            String playlistSh = dataSnapshot.child(ID).child(friend).child(key).getValue().toString();
                            if (playlistSh.equals(playlist)) {
                                Toast.makeText(mContext, "the friend " + friend, Toast.LENGTH_LONG).show();

                                UserDetails.friend = friend;

                                Intent intent = new Intent(mContext, SharedPlaylistSongs.class);
                                intent.putExtra("Name", playlist);
                                intent.putExtra("Friend", UserDetails.friend);
//                                if(mediaPlayer.isPlaying()) {
//                                    intent.putExtra("Song", track_title.getText().toString());
//                                }
                                mContext.startActivity(intent);
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



//                        if(dataSnapshot.child(friend).child(key).getValue().toString().equals(playlist)){
//                            Intent intent = new Intent(mContext, SharedPlaylistSongs.class);
//                            intent.putExtra("Name", playlist);
//                            intent.putExtra("Friend", snapshot.getKey());
////                            if(mediaPlayer.isPlaying()) {
////                                intent.putExtra("Song", track_title.getText().toString());
////                            }
//                            mContext.startActivity(intent);




}

