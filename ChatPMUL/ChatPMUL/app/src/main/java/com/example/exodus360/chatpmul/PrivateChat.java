package com.example.exodus360.chatpmul;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PrivateChat extends AppCompatActivity {

    PrivateChatList adapterPm;
    static ArrayList<String> pms = new ArrayList<String>();

    ListView listViewPm;

    static boolean active = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);
        active = true;
        listViewPm = (ListView)findViewById(R.id.listViewPm);
        adapterPm = new PrivateChatList(PrivateChat.this, pms);
        listViewPm = (ListView)findViewById(R.id.listView);
        listViewPm.setAdapter(adapterPm);
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

}
