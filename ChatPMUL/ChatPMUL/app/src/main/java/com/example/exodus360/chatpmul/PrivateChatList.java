package com.example.exodus360.chatpmul;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by exodus360 on 27/03/2017.
 */

public class PrivateChatList extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> msg;


    public PrivateChatList(Activity context, ArrayList<String> msg) {
        super(context, R.layout.chatbubble, msg);
        this.context = context;
        this.msg=msg;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.chatbubble, null, true);
        TextView txtmsg = (TextView) rowView.findViewById(R.id.message_text);

        txtmsg.setText(msg.get(position));

        return rowView;
    }
}
