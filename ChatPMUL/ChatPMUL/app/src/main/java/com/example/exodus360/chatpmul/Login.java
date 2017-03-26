package com.example.exodus360.chatpmul;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;

    String data;

    Socket sk;
    BufferedReader input;
    PrintWriter output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.et_email);
        password = (EditText)findViewById(R.id.et_password);
    }

    public void buttonSignup_Click(View view){
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

    public void buttonLogin_Click(View view){
        new LoginAsyncTask().execute();
    }

    private Boolean ConnectClient() {
        String ip = "10.0.2.2";
        int port = 2000;
        try {
            sk = new Socket(ip, port);
            input = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())), true);
            String aux = input.readLine();
            return true;
        } catch (Exception e) {
            Log.d("","error: " + e.toString());
            return false;
        }

    }

    private String TryLogin(){
        try {
            output.println("#LOGIN#" + email.getText() + "#" + password.getText() + "#");
            return input.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public class LoginAsyncTask extends AsyncTask<String, String, Long> {

        @Override
        protected Long doInBackground(String... params) {
            if (ConnectClient()){
                String data = TryLogin();
                publishProgress(data);
            }
            return (long)0;
        }

        protected void onProgressUpdate(String... string) {
            String [] subdata = string[0].split("#");
            if(subdata[1].equals("OK")){
                //TODO 1 WE HAVE TO CREATE THE NEXT WINDOW (LOBBY) SO WE CAN REFERENCE IT
                //The "Signup.class" in the next line has to be replace with the reference to the activity we desire to load
                Intent intent = new Intent(getApplicationContext(), Lobby.class);
                Bundle b = new Bundle();
                b.putString("email",email.getText().toString());
                intent.putExtras(b);
                startActivity(intent);

                Toast.makeText(getBaseContext(), "Login successful", Toast.LENGTH_LONG).show();
            }else if(subdata[1].equals("NOK")){
                if (subdata[2].equals("E2")){
                    Toast.makeText(getBaseContext(), "Incorrect password", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(), "Email not registered", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
