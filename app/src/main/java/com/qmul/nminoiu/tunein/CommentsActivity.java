package com.qmul.nminoiu.tunein;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nminoiu on 05/07/2017.
 */
public class CommentsActivity extends AppCompatActivity {
    private PopupWindow popWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * On show popup.
     *
     * @param v the v
     */
// call this method when required to show popup
    public void onShowPopup(View v){

        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // inflate the custom popup layout
        final View inflatedView = layoutInflater.inflate(R.layout.popup_layout, null,false);
        // find the ListView in the popup layout
        ListView listView = (ListView)inflatedView.findViewById(R.id.commentsListView);

        // get device size
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        int mDeviceHeight = size.y;


        // fill the data to the list items
        setSimpleList(listView);


        // set height depends on the device size
        popWindow = new PopupWindow(inflatedView, size.x - 50,size.y - 400, true );
        // set a background drawable with rounders corners
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_comments));
        // make it focusable to show the keyboard to enter in `EditText`
        popWindow.setFocusable(true);
        // make it outside touchable to dismiss the popup window
        popWindow.setOutsideTouchable(true);

        // show the popup at bottom of the screen and set some margin at bottom ie,
        popWindow.showAtLocation(v, Gravity.BOTTOM, 0,100);
    }


    /**
     * Set simple list.
     *
     * @param listView the list view
     */
    void setSimpleList(ListView listView){

        ArrayList<String> contactsList = new ArrayList<String>();

        for (int index = 0; index < 10; index++) {
            contactsList.add("I am @ index " + index + " today " + Calendar.getInstance().getTime().toString());
        }

        listView.setAdapter(new ArrayAdapter<String>(CommentsActivity.this, R.layout.comments_list_item, android.R.id.text1,contactsList));
    }

}

