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
 * Created by nicoleta on 17/12/2017.
 */
public class ConversationsAdapter extends BaseAdapter{

    private Context mContext;
    List<DoubleRow> drowItems;
    private RelativeLayout buttons;

    /**
     * Instantiates a new Conversations adapter.
     *
     * @param context the context
     * @param items   the items
     */
    public ConversationsAdapter(Context context, List<DoubleRow> items) {
        mContext = context;
        this.drowItems = items;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView subTitle;
        TextView message;
    }

    //handling clicks in conversations list
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ConversationsAdapter.ViewHolder holder = null;
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.doublerow, null);
            holder = new ConversationsAdapter.ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.from);
            holder.subTitle = (TextView) convertView.findViewById(R.id.time);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            UserDetails.menuIcons.add(holder.imageView);
            convertView.setTag(holder);
        }
        else {
            holder = (ConversationsAdapter.ViewHolder) convertView.getTag();
        }

        final DoubleRow rowItem = (DoubleRow) getItem(position);
        holder.txtTitle.setText(rowItem.getTitle());
        holder.subTitle.setText(rowItem.getSubtitle());
        holder.message.setText(rowItem.getMessage());
        holder.imageView.setImageResource(rowItem.getImageId());

        //handling click on friend name in conversation list
        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Chat.class);
                intent.putExtra("Uniqid", "FromConversations");
                intent.putExtra("Song", Conversations.track_title.getText().toString());
                intent.putExtra("Friend", rowItem.getTitle().toString());
                mContext.startActivity(intent);

            }
        });

        //handling click on the time in conversations list
        holder.subTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Chat.class);
                intent.putExtra("Uniqid", "FromConversations");
                intent.putExtra("Song", Conversations.track_title.getText().toString());
                intent.putExtra("Friend", rowItem.getTitle().toString());
                mContext.startActivity(intent);

            }
        });

        //handling click on the message in conversations list
        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Chat.class);
                intent.putExtra("Uniqid", "FromConversations");
                intent.putExtra("Song", Conversations.track_title.getText().toString());
                intent.putExtra("Friend", rowItem.getTitle().toString());
                mContext.startActivity(intent);

            }
        });

        //handling click on the icon in conversations list
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Chat.class);
                intent.putExtra("Uniqid", "FromConversations");
                intent.putExtra("Song", Conversations.track_title.getText().toString());
                intent.putExtra("Friend", rowItem.getTitle().toString());
                mContext.startActivity(intent);

            }
        });

        //click on convert view and starting intent Chat
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Chat.class);
                intent.putExtra("Uniqid", "FromConversations");
                intent.putExtra("Song", Conversations.track_title.getText().toString());
                intent.putExtra("Friend", rowItem.getTitle().toString());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    //return no of rows
    @Override
    public int getCount() {
        return drowItems.size();
    }

    //return row
    @Override
    public Object getItem(int position) {
        return drowItems.get(position);
    }

    //return item id form position
    @Override
    public long getItemId(int position) {
        return drowItems.indexOf(getItem(position));
    }
}

