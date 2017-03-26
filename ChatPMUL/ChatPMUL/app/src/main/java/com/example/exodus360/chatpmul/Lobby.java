package com.example.exodus360.chatpmul;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import static com.example.exodus360.chatpmul.R.id.activity_lobby;
import static com.example.exodus360.chatpmul.R.id.listView;

public class Lobby extends AppCompatActivity {

    private Integer[] imgs={
            R.drawable.online,
            R.drawable.offline,
            R.drawable.offline,
            R.drawable.offline,
            R.drawable.offline,
            R.drawable.online};
    private String[] nombres={"BUHO","COLIBRI","CUERVO","FLAMENCO","KIWI","LORO"};

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        listView = (ListView)findViewById(R.id.listView);
        LobbyList adapter = new LobbyList(Lobby.this, imgs, nombres);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });*/
    }
}
