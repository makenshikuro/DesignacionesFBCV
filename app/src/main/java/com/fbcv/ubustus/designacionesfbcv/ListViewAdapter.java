package com.fbcv.ubustus.designacionesfbcv;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<Partido> {

    // Declare Variables

    ArrayList<Partido> alp;
    LayoutInflater inflater;


    public ListViewAdapter(Context context,int textViewResourceId, ArrayList<Partido> al) {
        super(context,textViewResourceId, al);
        this.alp = al;

    }

    @Override
    public int getCount() {
        return alp.size();
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtFecha;
        TextView txtHora;
        TextView txtEncuentro;
        TextView txtCategoria;
        TextView txtEstado;


        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.list_view_items, null);
        txtFecha = (TextView) v.findViewById(R.id.txtFecha);
        txtHora = (TextView) v.findViewById(R.id.txtHora);
        txtEncuentro = (TextView) v.findViewById(R.id.txtEncuentro);
        txtCategoria = (TextView) v.findViewById(R.id.txtCategoria);
        txtEstado = (TextView) v.findViewById(R.id.txtEstado);


        txtFecha.setText(alp.get(position).getFecha().toString());
        txtHora.setText(alp.get(position).getFecha().toString());
        txtEncuentro.setText(alp.get(position).getEncuentro());
        txtCategoria.setText(alp.get(position).getCategoria());
        txtEstado.setText(alp.get(position).getEstado());

        return v;

    }
}
