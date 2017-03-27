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

public class PublicChatList extends ArrayAdapter<String> {
    private final Activity context;
    static ArrayList<String> messages = new ArrayList<String>();
    static ArrayList<String> emails = new ArrayList<String>();


    public PublicChatList(Activity context, ArrayList<String> messages, ArrayList<String> emails) {
        super(context, R.layout.public_chat_element, emails);
        this.context = context;
        this.messages = messages;
        this.emails = emails;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.public_chat_element, null, true);
        TextView txtEmail = (TextView) rowView.findViewById(R.id.textUser);
        TextView txtMsg = (TextView) rowView.findViewById(R.id.textMsg);

        txtEmail.setText(emails.get(position));
        txtMsg.setText(messages.get(position));

        return rowView;
    }
}
