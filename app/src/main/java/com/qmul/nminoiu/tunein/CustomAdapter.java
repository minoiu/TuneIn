package com.qmul.nminoiu.tunein;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nminoiu on 15/08/2017.
 */

    public class CustomAdapter extends BaseAdapter {

        private Context mContext;
        List<RowItem> rowItems;

        public CustomAdapter(Context context, List<RowItem> items) {
            mContext = context;
            this.rowItems = items;

        }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }



        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row, null);
                holder = new ViewHolder();
                holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
                holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            RowItem rowItem = (RowItem) getItem(position);

            holder.txtTitle.setText(rowItem.getTitle());
            holder.imageView.setImageResource(rowItem.getImageId());

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

