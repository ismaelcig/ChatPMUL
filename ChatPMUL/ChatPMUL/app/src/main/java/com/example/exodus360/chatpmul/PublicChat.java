package com.example.exodus360.chatpmul;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PublicChat extends AppCompatActivity {

    PublicChatList adapterPc;
    ListView listViewPc;
    EditText textbox;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            SetAdapter();
            timerHandler.postDelayed(timerRunnable,1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_chat);
        textbox = (EditText)findViewById(R.id.textBox);
        SetAdapter();
        timerHandler.postDelayed(timerRunnable,0);
    }

    private void SetAdapter(){
        listViewPc = (ListView)findViewById(R.id.listViewPc);
        adapterPc = new PublicChatList(PublicChat.this, Lobby.messages, Lobby.emails);
        listViewPc = (ListView)findViewById(R.id.listViewPc);
        listViewPc.setAdapter(adapterPc);
    }

    public void bt_send(View view){
        new PublicAsyncTask().execute();
    }

    public class PublicAsyncTask extends AsyncTask<String, String, Long> {

        @Override
        protected Long doInBackground(String... params) {
            String data = TrySend();
            if (data != null){
                publishProgress(data);
            }
            return (long)0;
        }

        protected void onProgressUpdate(String... string) {
        }
    }

    private String TrySend(){
        try {
            Lobby.output.println("#SEND#" + Lobby.email + "##" + textbox.getText().toString() + "#");
            return Lobby.input.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
