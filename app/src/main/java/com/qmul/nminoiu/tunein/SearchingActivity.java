package com.qmul.nminoiu.tunein;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchingActivity extends AppCompatActivity {
    MaterialSearchView searchView;

    ListView lstView;

    String[] lstSource = {

            "Harry",

            "Ron",

            "Hermione",

            "Snape",

            "Malfoy",

            "One",

            "Two",

            "Three",

            "Four",

            "Five",

            "Six",

            "Seven",

            "Eight",

            "Nine",

            "Ten"
    };



    @Override

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Now Playing");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        lstView = (ListView)findViewById(R.id.lstView);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lstSource);
        lstView.setAdapter(adapter);

        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

            @Override
            public void onSearchViewShown() {
                lstView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSearchViewClosed() {



                //If closed Search View , lstView will return default

                lstView = (ListView)findViewById(R.id.lstView);

                ArrayAdapter adapter = new ArrayAdapter(SearchingActivity.this,android.R.layout.simple_list_item_1,lstSource);

                lstView.setAdapter(adapter);

                lstView.setVisibility(View.INVISIBLE);

            }

        });



        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

            @Override

            public boolean onQueryTextSubmit(String query) {

                return false;

            }



            @Override

            public boolean onQueryTextChange(String newText) {

                if(newText != null && !newText.isEmpty()){

                    List<String> lstFound = new ArrayList<String>();

                    for(String item:lstSource){

                        if(item.contains(newText))

                            lstFound.add(item);

                    }



                    ArrayAdapter adapter = new ArrayAdapter(SearchingActivity.this,android.R.layout.simple_list_item_1,lstFound);

                    lstView.setAdapter(adapter);

                }

                else{

                    //if search text is null

                    //return default

                    ArrayAdapter adapter = new ArrayAdapter(SearchingActivity.this,android.R.layout.simple_list_item_1,lstSource);

                    lstView.setAdapter(adapter);

                }

                return true;

            }

        });

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_item,menu);

        MenuItem item = menu.findItem(R.id.action_search);

        searchView.setMenuItem(item);

        return true;

    }
}
