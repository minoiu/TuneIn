package com.qmul.nminoiu.tunein;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.List;

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

/**
 * Created by nicoleta on 15/09/2017.
 */
public class CustomAdapter extends BaseAdapter {

    List<RowItem> rowItems;
    private Context mContext;
        private RelativeLayout buttons;

    /**
     * Instantiates a new Custom adapter.
     *
     * @param context the context
     * @param items   the items
     */
    public CustomAdapter(Context context, List<RowItem> items) {
            mContext = context;
            this.rowItems = items;
        }

        //return convertView with rows containing text and icons
        public View getView(final int position, View convertView, final ViewGroup parent) {

            ViewHolder holder = null;
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row, null);
                holder = new ViewHolder();
                holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
                holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
                UserDetails.menuIcons.add(holder.imageView);
                convertView.setTag(holder);
            }
            else {
                holder = (CustomAdapter.ViewHolder) convertView.getTag();
            }

            final RowItem rowItem = (RowItem) getItem(position);
            holder.txtTitle.setText(rowItem.getTitle());
            holder.imageView.setImageResource(rowItem.getImageId());

            //handling clicks on converView and starting new intent
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, PlaylistSongs.class);
                    intent.putExtra("Name", rowItem.getTitle().toString());
                    if(mediaPlayer.isPlaying()) {
                        intent.putExtra("Song", MyPlaylists.track_title.getText().toString());
                    }
                    mContext.startActivity(intent);
                }
            });

            return convertView;
        }

    //return no of rows
    @Override
    public int getCount() {
        return rowItems.size();
    }

    //return row from position
    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    //return id from position
    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

}

