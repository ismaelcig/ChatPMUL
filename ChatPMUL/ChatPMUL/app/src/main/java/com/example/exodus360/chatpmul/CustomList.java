package com.example.exodus360.chatpmul;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Usuario on 26/03/2017.
 */

public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final Integer[] img;//Icono
    private final String[] nombre;
    private final String[] descr;


    public CustomList(Activity context, Integer[] img, String[] nombre, String[] descr) {
        super(context, R.layout.list_element, nombre);
        this.context = context;
        this.img = img;
        this.nombre=nombre;
        this.descr = descr;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_element, null, true);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView txtNombre = (TextView) rowView.findViewById(R.id.textUser);
        TextView txtDescr = (TextView) rowView.findViewById(R.id.textMsg);

        imageView.setImageResource(img[position]);
        txtNombre.setText(nombre[position]);
        txtDescr.setText(descr[position]);

        return rowView;
    }
}
