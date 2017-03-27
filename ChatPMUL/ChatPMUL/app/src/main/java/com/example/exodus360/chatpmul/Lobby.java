package com.example.exodus360.chatpmul;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.exodus360.chatpmul.R.id.activity_lobby;
import static com.example.exodus360.chatpmul.R.id.listView;

public class Lobby extends AppCompatActivity {

    LobbyList adapter;
    ArrayList<Integer> imgs = new ArrayList<Integer>();
    ArrayList<String> nombres = new ArrayList<String>();

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            imgs.add(R.drawable.offline);
            nombres.add("Perro");
            SetAdapter();
            timerHandler.postDelayed(timerRunnable,1000);
        }
    };

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        imgs.add(R.drawable.online);
        imgs.add(R.drawable.online);
        imgs.add(R.drawable.online);

        nombres.add("Buho");
        nombres.add("Buho");
        nombres.add("Buho");
        SetAdapter();
        timerHandler.postDelayed(timerRunnable,0);
    }

    private void SetAdapter(){
        listView = (ListView)findViewById(R.id.listView);
        adapter = new LobbyList(Lobby.this, imgs, nombres);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
