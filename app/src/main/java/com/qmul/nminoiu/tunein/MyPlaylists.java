package com.qmul.nminoiu.tunein;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyPlaylists extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private String playlistname;
    private ListView playlists;
    private List<String> playlistsList;
    private ArrayAdapter<String> playlistsadapter;
    private String ID;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference playlistsRef;
    private String playlist;
    private LinearLayout newPlaylist;
    private EditText playlistName;
    private Button create;
    private Button cancel;
    private CustomAdapter adapter;
    private ImageView icon;
    public RelativeLayout buttons;


    public static final String[] titles = new String[] { "Strawberry",
            "Banana", "Orange", "Mixed" };

    public static final Integer[] images = { R.drawable.options,
            R.drawable.options, R.drawable.options, R.drawable.options };

    ListView listView;
    List<RowItem> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplaylists);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Playlists");


        rowItems = new ArrayList<RowItem>();
        adapter = new CustomAdapter(this, rowItems);
        buttons = (RelativeLayout) findViewById(R.id.buttons);

        newPlaylist = (LinearLayout) findViewById(R.id.createPlaylist);
        newPlaylist.bringToFront();
        playlistName = (EditText) findViewById(R.id.editText);
        create = (Button) findViewById(R.id.create);
        cancel = (Button) findViewById(R.id.cancel);
        buttons = (RelativeLayout) findViewById(R.id.buttons);
        icon = (ImageView)  findViewById(R.id.icon);


        firebaseAuth = FirebaseAuth.getInstance();
        ID = firebaseAuth.getCurrentUser().getUid();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyPlaylists.this, Users.class);
                startActivity(i);
            }
        });

        playlists = (ListView) findViewById(R.id.playlistsList);
        playlistsList = new ArrayList<>();
        playlistsadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlistsList);

        playlistsRef = FirebaseDatabase.getInstance().getReference().child("Playlists").child(ID);
        playlistsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String playlist = dataSnapshot.getValue(String.class);
                //Toast.makeText(PlaylistsActivity.this, recentSongs + " recent songs ", Toast.LENGTH_SHORT).show();

                playlistsList.add(playlist);
                UserDetails.myPlaylists.add(playlist);
                RowItem item = new RowItem(R.drawable.ic_nextblack, playlist);

                rowItems.add(item);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        playlists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RowItem rowItem = (RowItem) parent.getItemAtPosition(position);
                String item = rowItem.getTitle();

                Toast.makeText(MyPlaylists.this, "click click" + item, Toast.LENGTH_LONG).show();
            }
        });

//        ClickListener(new View.OnClickListener() {
//                                         @Override
//                                         public void onClick(View v) {
//                                             Toast.makeText(MyPlaylists.this, "click click", Toast.LENGTH_LONG).show();
//                                             UserDetails.menuIcons.get(1).setOnClickListener(new View.OnClickListener() {
//                                                 @Override
//                                                 public void onClick(View v) {
//                                                     buttons.setVisibility(View.VISIBLE);
//
//                                                 }
//                                             });
//
//                                         }
//                                     });



//                                                 Toast toast = Toast.makeText(getApplicationContext(),
//                                                         "Item " +  ": " + UserDetails.myPlaylists.get(position),
//                                                         Toast.LENGTH_SHORT);
//                                                 toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
//                                                 toast.show();
//                                             }
//                                                 switch (view.getId()) {
//                                                     case R.id.icon:
//                                                         //Do it when myimage is clicked
//                                                         Toast.makeText( MyPlaylists.this, "Icon CLICKED!", Toast.LENGTH_SHORT).show();
//                                                         break;
//                                                     case R.id.title:
//                                                         //Do something when other item is clicked
//                                                         Toast.makeText( MyPlaylists.this, "title CLICKED!", Toast.LENGTH_SHORT).show();;
//                                                 }


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = playlistName.getText().toString().trim();

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase playRef = ref.child("Playlists").child(ID);
                playRef.push().setValue(name);
                Toast.makeText(MyPlaylists.this, "Playlist Created Successfully ", Toast.LENGTH_LONG).show();
                newPlaylist.setVisibility(View.GONE);

            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPlaylist.setVisibility(View.GONE);

            }
        });

        playlists.setClickable(true);
        playlists.setAdapter(adapter);
        playlists.setOnItemClickListener(this);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.playlists, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            newPlaylist.setVisibility(View.VISIBLE);
            playlistName.setText("");
        }
        else onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent backMainTest = new Intent(this, SettingsActivity.class);
        startActivity(backMainTest);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       // String item = parent.getItem(position).toString();

        //Toast.makeText(MyPlaylists.this, "click click" + item, Toast.LENGTH_LONG).show();

        //buttons.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {
        if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
            if(menu.getClass().getSimpleName().equals("MenuBuilder")){
                try{
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                }
                catch(NoSuchMethodException e){
                }
                catch(Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    public void showMenu(ImageView img){
       img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // buttons.setVisibility(View.VISIBLE);
            }
        });
    }

}
