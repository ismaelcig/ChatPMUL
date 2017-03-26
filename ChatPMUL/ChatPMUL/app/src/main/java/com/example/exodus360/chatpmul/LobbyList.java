package com.example.exodus360.chatpmul;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class LobbyList extends ArrayAdapter<String> {
    private final Activity context;
    private final Integer[] img;//Icono
    private final String[] nombre;


    public LobbyList(Activity context, Integer[] img, String[] nombre) {
        super(context, R.layout.lobby_element, nombre);
        this.context = context;
        this.img = img;
        this.nombre=nombre;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.lobby_element, null, true);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView txtNombre = (TextView) rowView.findViewById(R.id.textUser);

        imageView.setImageResource(img[position]);
        txtNombre.setText(nombre[position]);

        return rowView;
    }

}
