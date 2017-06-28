package com.qmul.nminoiu.tunein;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Users extends AppCompatActivity {
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    // ProgressDialog pd;
    ListView usersList;
    ArrayList<String> users = new ArrayList<>();
    private ArrayAdapter<String> uadapter;
    private DatabaseReference db;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        firebaseAuth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance().getReference().child("Users");

        usersList = (ListView) findViewById(R.id.usersList);
        uadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        usersList.setAdapter(uadapter);

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                users.add(value);
                uadapter.notifyDataSetChanged();
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

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String user = ((TextView) view).getText().toString();
                UserDetails.chatWith = user;
                FirebaseUser currentuser = firebaseAuth.getCurrentUser();

                Firebase ref = new Firebase("https://tunein-633e5.firebaseio.com/");
                Firebase userRef = ref.child("Users").child(currentuser.getUid());
                //Map<String,Object> uinfo = new HashMap<String, Object>();
                String curUser = currentuser.getEmail().toString();




                UserDetails.username = curUser;


                Toast.makeText(Users.this, UserDetails.username, Toast.LENGTH_LONG).show();


                startActivity(new Intent(Users.this, Chat.class));
            }
        });
    }
}



