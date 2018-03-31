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
    /**
     * The Row items.
     */
    List<RowItem> rowItems;
        private RelativeLayout buttons;
        private FirebaseStorage mStorage;
    /**
     * The Storage path.
     */
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
            /**
             * The Image view.
             */
            ImageView imageView;
            /**
             * The Txt title.
             */
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

            MyPlaylists mp = new MyPlaylists();
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


                        final String friend = rowItem.getTitle();
                        DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference().child("ID").child(friend).child("Id");

                        mDatabase1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                            @Override
                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                // UserDetails.fullname = dataSnapshot.getValue().toString();
                                UserDetails.username = dataSnapshot.getValue().toString();
                                //Toast.makeText(mContext, "friend id is " + UserDetails.username, Toast.LENGTH_SHORT).show();
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
                                                        //Toast.makeText(mContext, "last key is "+ key1, Toast.LENGTH_SHORT).show();

                                                        dataSnapshot.child(UserDetails.fullname).child(key1).getRef().removeValue();
                                                    }
                                                }

                                            }
//
// String playlistSh = dataSnapshot.child(ID).child(friend).child(key).getValue().toString();
//                        if (playlistSh.equals(oldPlaylist)) {
//                            dataSnapshot.child(ID).child(friend).child(key).getRef().removeValue();
//
//                        }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                //
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
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

            }catch(Exception e)

            {

                e.printStackTrace();
            }


            return convertView;
        }

    private void getID(String friend) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ID").child(friend).child("Id");

        mDatabase.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
               // UserDetails.fullname = dataSnapshot.getValue().toString();
                UserDetails.username = dataSnapshot.getValue().toString();

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

    /**
     * Update list.
     *
     * @param newlist the newlist
     */
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



