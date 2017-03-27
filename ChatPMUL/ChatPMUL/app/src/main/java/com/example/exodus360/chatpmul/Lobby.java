package com.example.exodus360.chatpmul;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

import static com.example.exodus360.chatpmul.R.id.activity_lobby;
import static com.example.exodus360.chatpmul.R.id.listView;

public class Lobby extends AppCompatActivity {

    LobbyList adapter;
    ArrayList<Integer> imgs = new ArrayList<Integer>();
    ArrayList<String> nombres = new ArrayList<String>();
    static Socket sk;
    static BufferedReader input;
    static PrintWriter output;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
//            imgs.add(R.drawable.offline);
//            nombres.add("Perro");
//            SetAdapter();
            new BeepAsyncTask().execute();
            timerHandler.postDelayed(timerRunnable,1000);
        }
    };

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

//        imgs.add(R.drawable.online);
//        imgs.add(R.drawable.online);
//        imgs.add(R.drawable.online);
//
//        nombres.add("Buho");
//        nombres.add("Buho");
//        nombres.add("Buho");
        sk = Login.GetSocket();
        output = Login.GetPrintWriter();
        input = Login.GetBufferedReader();
        SetAdapter();
        timerHandler.postDelayed(timerRunnable,0);
    }

    private void SetAdapter(){
        listView = (ListView)findViewById(R.id.listView);
        adapter = new LobbyList(Lobby.this, imgs, nombres);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public class BeepAsyncTask extends AsyncTask<String, String, Long> {

        @Override
        protected Long doInBackground(String... params) {
            String data = "#BEEP#" + getIntent().getStringExtra("email") + "#0#";
            System.out.println(data);
            try {
                output.println(data);
                data = input.readLine();
                publishProgress(data);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return (long)0;
        }

        protected void onProgressUpdate(String... string) {
            //%USERS%email1%True1%email2%False2%...
            //%MSG%email1%Msg1%email2%Msg2%...
            //%PRIV%emailSender1%priv1%emailSender2%priv2%...
            System.out.println(string[0]);
        }
    }
}
