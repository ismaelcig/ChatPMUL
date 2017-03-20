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

    Socket sk;
    BufferedReader entrada;
    PrintWriter salida;
    //private Context context = this;

    /**
     * Puerto
     * */
    //private static final int SERVERPORT = 2000;
    /**
     * HOST
     * */
    //private static final String ADDRESS = "127.0.0.1";

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
        username = (EditText)findViewById(R.id.editTextUsername);//******************************************No se envía
        pass = (EditText)findViewById(R.id.editTextPassword);
        pass1 = (EditText)findViewById(R.id.editTextPassword1);
        buttonSignUp = (Button)findViewById(R.id.buttonSignup);

        /*buttonSignUp.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //Comprueba que ningún campo queda vacío
                        if(email.getText().toString().length()>0 && username.getText().toString().length()>0 && pass.getText().toString().length()>0 && pass1.getText().toString().length()>0){
                            //Comprueba que ambas contraseñas coinciden
                            if (pass.getText() == pass1.getText()){
                                //Envía datos al servidor para intentar registrarse
                                MyATaskCliente myATaskYW = new MyATaskCliente();
                                myATaskYW.execute("#SIGNUP#"+email.getText().toString()+"#"+pass.getText().toString()+"#");
                            }
                            else{
                                Toast.makeText(context, "Both passwords must match", Toast.LENGTH_LONG).show();
                                pass.setText("");
                                pass1.setText("");
                            }
                        }else{
                            Toast.makeText(context, "All fields must be filled in", Toast.LENGTH_LONG).show();
                        }

                    }
                });*/


    }

    public void buttonRegister_Click(View view){

                //Comprueba que ningún campo queda vacío
                if(email.getText().toString().length()>0 && username.getText().toString().length()>0 && pass.getText().toString().length()>0 && pass1.getText().toString().length()>0){
                    //Comprueba que ambas contraseñas coinciden
                    if (pass.getText().toString().equals(pass1.getText().toString())){
                        Toast.makeText(this, "First connection to the server", Toast.LENGTH_LONG).show();
                        Log.d("","Sirvo para escribir");
                        System.out.println("Boo?");
                        //TODO1: SET THIS TO AN ASYNC TASK
                        ConectarCliente();
                        //Envía datos al servidor para intentar registrarse
                        //MyATaskCliente myATaskYW = new MyATaskCliente();
                        //myATaskYW.execute("#SIGNUP#"+email.getText().toString()+"#"+pass.getText().toString()+"#");
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

    private void ConectarCliente() {
        //Log.d("","Estoy Aqui");
        String ip = "10.0.2.2";
        int puerto = 2000;
        Log.d(""," Socket " + ip + " " + puerto);
        try {
            sk = new Socket(ip, puerto);
            entrada = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            salida = new PrintWriter(new OutputStreamWriter(sk.getOutputStream()), true);

            Log.d("","conectando ");
            Log.d("", "recibiendo ... ");
            Log.d("", entrada.readLine());

        } catch (Exception e) {
            Log.d("","error: " + e.toString());
        }
    }




//    /**
//     * Clase para interactuar con el servidor
//     * */
//    class MyATaskCliente extends AsyncTask<String,Void,String> {
//
//        /**
//         * Ventana que bloqueara la pantalla del movil hasta recibir respuesta del servidor
//         * */
//        ProgressDialog progressDialog;
//
//        /**
//         * muestra una ventana emergente
//         * */
//        @Override
//        protected void onPreExecute()
//        {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.setTitle("Connecting to server");
//            progressDialog.setMessage("Please wait...");
//            progressDialog.show();
//        }
//
//        /**
//         * Se conecta al servidor y trata resultado
//         * */
//        @Override
//        protected String doInBackground(String... values){
//
//            try {
//                //Se conecta al servidor
//                InetAddress serverAddr = InetAddress.getByName(ADDRESS);
//                Log.i("I/TCP Client", "Connecting...");
//                Socket socket = new Socket(serverAddr, SERVERPORT);
//                Log.i("I/TCP Client", "Connected to server");
//
//                //envia peticion de cliente
//                Log.i("I/TCP Client", "Send data to server");
//                PrintStream output = new PrintStream(socket.getOutputStream());
//                String request = values[0];
//                output.println(request);
//
//                //recibe respuesta del servidor y formatea a String
//                Log.i("I/TCP Client", "Received data to server");
//                InputStream stream = socket.getInputStream();
//                byte[] lenBytes = new byte[256];
//                stream.read(lenBytes,0,256);
//                String received = new String(lenBytes,"UTF-8").trim();
//                Log.i("I/TCP Client", "Received " + received);
//                Log.i("I/TCP Client", "");
//
//                //cierra conexion
//                socket.close();
//                return received;
//            }catch (UnknownHostException ex) {
//                Log.e("E/TCP Client", "" + ex.getMessage());
//                return ex.getMessage();
//            } catch (IOException ex) {
//                Log.e("E/TCP Client", "" + ex.getMessage());
//                return ex.getMessage();
//            }
//        }
//
//        /**
//         * Oculta ventana emergente y muestra resultado en pantalla
//         * */
//        @Override
//        protected void onPostExecute(String value){
//            progressDialog.dismiss();
//            if (value.equals("#OK#")){
//                //Registrado correctamente
//                Toast.makeText(context, "Now you can login", Toast.LENGTH_LONG).show();
//                Back();
//            }
//            else if (value.equals("#NOK#")){
//                //El email ya está en uso
//                Toast.makeText(context, "This email is already on use", Toast.LENGTH_LONG).show();
//                email.setText("");
//                email.requestFocus();
//            }
//            //editText2.setText(value);
//        }
    }
