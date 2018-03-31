package com.qmul.nminoiu.tunein;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import static com.qmul.nminoiu.tunein.LoginActivity.mediaPlayer;

/**
 * Created by nicoleta on 20/10/2017.
 */
public class FavouritesCustomAdapter extends BaseAdapter {

    private Context mContext;
    /**
     * The Row items.
     */
    List<RowItem> rowItems;
    private RelativeLayout buttons;

    /**
     * Instantiates a new Favourites custom adapter.
     *
     * @param context the context
     * @param items   the items
     */
    public FavouritesCustomAdapter(Context context, List<RowItem> items) {
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

    public View getView(final int position, View convertView, final ViewGroup parent) {

        FavouritesCustomAdapter.ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
            holder = new FavouritesCustomAdapter.ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            UserDetails.menuIcons.add(holder.imageView);

            convertView.setTag(holder);
        }
        else {
            holder = (FavouritesCustomAdapter.ViewHolder) convertView.getTag();
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

                Intent intent = new Intent(mContext, PlaylistSongs.class);
                intent.putExtra("Name", rowItem.getTitle().toString());
                if(mediaPlayer.isPlaying()) {
                    intent.putExtra("Song", Favourites.track_title.getText().toString());
                }
                mContext.startActivity(intent);

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

}

