package com.example.exodus360.chatpmul;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    static String email;

    static ArrayList<Integer> imgs = new ArrayList<Integer>();
    static ArrayList<String> names = new ArrayList<String>();

    static ArrayList<String> messages = new ArrayList<String>();
    static ArrayList<String> emails = new ArrayList<String>();

    static int index = 0;

    static Socket sk;
    static BufferedReader input;
    static PrintWriter output;

    static boolean active = false;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
//            imgs.add(R.drawable.offline);
//            names.add("Perro");
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
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
//        imgs.add(R.drawable.online);
//        imgs.add(R.drawable.online);
//        imgs.add(R.drawable.online);
//
//        names.add("Buho");
//        names.add("Buho");
//        names.add("Buho");
        email = getIntent().getStringExtra("email");
        active = true;
        sk = Login.GetSocket();
        output = Login.GetPrintWriter();
        input = Login.GetBufferedReader();
        SetAdapter();
        timerHandler.postDelayed(timerRunnable,0);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Toast.makeText(getBaseContext(), "You Clicked at " + names.get(+position), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getBaseContext(), PrivateChat.class);
//                startActivity(intent);
//            }
//        });
    }

    private void SetAdapter(){
        listView = (ListView)findViewById(R.id.listView);
        adapter = new LobbyList(Lobby.this, imgs, names);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

//    private void SetPmAdapter(){
//        listViewPm = (ListView)findViewById(R.id.listViewPm);
//        adapterPm = new PrivateChatList(PrivateChat.this, pms);
//    }

    public class BeepAsyncTask extends AsyncTask<String, String, Long> {

        @Override
        protected Long doInBackground(String... params) {
            String data = "#BEEP#" + getIntent().getStringExtra("email") + "#" + index +"#";
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

            //With this I get the 3 inner commands that i have to process
            String [] subcmd = string[0].split("#");

            //***************************************************************
            ////%USERS%email1%True1%email2%False2%...
            String [] subdata = subcmd[2].split("%");
            names.clear();
            imgs.clear();
            for (int i = 0; i < (subdata.length - 3) * 0.5; i++ ) {
                if (!getIntent().getStringExtra("email").equals(subdata[(i*2)+2])){
                    names.add(subdata[(i*2)+2]);
                    if (subdata[(i*2)+3].equals("True")){
                        imgs.add(R.drawable.online);
                    }else{
                        imgs.add(R.drawable.offline);
                    }
                }
            }
            SetAdapter();
            //***************************************************************

            //***************************************************************
            //%MSG%email1%Msg1%email2%Msg2%...
            subdata = subcmd[3].split("%");
            emails.clear();
            messages.clear();
            for (int i = 0; i < (subdata.length - 3) * 0.5; i++ ) {
                emails.add(subdata[(i*2)+2]);
                messages.add(subdata[(i*2)+3]);
            }
            if (PrivateChat.active){
                //actualizar el adapter de private chat
            }
            //***************************************************************

        }

    }

    public void bt_openPublic(View view){
        Intent intent = new Intent(this, PublicChat.class);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }
}
