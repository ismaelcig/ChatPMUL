package com.example.exodus360.chatpmul;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Signup extends AppCompatActivity {
    private  Button buttonSignUp;
    private EditText email;
    private  EditText username;
    private  EditText pass;
    private  EditText pass1;

    String data;
    Socket sk;
    BufferedReader input;
    PrintWriter output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
        }

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        email = (EditText)findViewById(R.id.editTextEmail);
        username = (EditText)findViewById(R.id.editTextUsername);
        pass = (EditText)findViewById(R.id.editTextPassword);
        pass1 = (EditText)findViewById(R.id.editTextPassword1);
        buttonSignUp = (Button)findViewById(R.id.buttonSignup);
    }

    public void buttonRegister_Click(View view){

                //Checks that no field is empty
                if(email.getText().toString().length()>0 && username.getText().toString().length()>0 && pass.getText().toString().length()>0 && pass1.getText().toString().length()>0){
                    //Validates that both passwords match
                    if (pass.getText().toString().equals(pass1.getText().toString())){
                        System.out.println("Boo?");
                        ConnectClient();
                        TrySignUp();
                    }
                    else{
                        Toast.makeText(this, "Both passwords must match", Toast.LENGTH_LONG).show();
                        pass.setText("");
                        pass1.setText("");
                    }
                }else{
                    Toast.makeText(this, "All fields must be filled in", Toast.LENGTH_LONG).show();
                }
    }
    public void buttonBack_Click(View view){
        Back();
    }

    void Back(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void ConnectClient() {
        String ip = "10.0.2.2";
        int puerto = 2000;
        Log.d(""," Socket " + ip + " " + puerto);
        try {
            sk = new Socket(ip, puerto);
            input = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            output = new PrintWriter(new OutputStreamWriter(sk.getOutputStream()), true);
            String aux = input.readLine();
            //For debugging purposes
            System.out.println(aux);
        } catch (Exception e) {
            Log.d("","error: " + e.toString());
        }
    }

    private void TrySignUp(){
        try {
            data = "#SIGNUP#" + email.getText().toString() + "#" + pass.getText().toString() + "#";
            output.write(data);
            data = input.readLine();
            String [] subdata = data.split("#");

            //For debugging purposes
            System.out.println(data);
            System.out.println(subdata);

            if (subdata[1].equals("OK")){
                Toast.makeText(this, "Signup successful", Toast.LENGTH_LONG).show();
                Back();
            }else{
                Toast.makeText(this, "User already registered", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }
