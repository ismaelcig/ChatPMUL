package com.example.exodus360.chatpmul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
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
        ConnectClient();
    }

    private void ConnectClient() {
        String ip = "10.0.2.2";
        int port = 2000;
        try {
            sk = new Socket(ip, port);
            input = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            output = new PrintWriter(new OutputStreamWriter(sk.getOutputStream()), true);
            String aux = input.readLine();
            Toast.makeText(this, aux, Toast.LENGTH_LONG).show();
            Toast.makeText(this, aux, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Log.d("","error: " + e.toString());
        }
    }

    private void TryLogin(){
        try {
            output.write("#LOGIN#" + email.getText() + "#" + password.getText() + "#");
            data = input.readLine();

            //For debbuging purposes
            System.out.println(data);

            String [] subdata = data.split("#");

            if(subdata[1].equals("OK")){
                //TODO 1 WE HAVE TO CREATE THE NEXT WINDOW (LOBBY) SO WE CAN REFERENCE IT
                //The "Signup.class" in the next line has to be replace with the reference to the activity we desire to load
//                Intent intent = new Intent(this, Signup.class);
//                Bundle b = new Bundle();
//                b.putString("email",email.getText().toString());
//                intent.putExtras(b);
//                startActivity(intent);
                Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show();
            }else if(subdata[1].equals("NOK")){
                if (subdata[2].equals("E2")){
                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "User not registered", Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
