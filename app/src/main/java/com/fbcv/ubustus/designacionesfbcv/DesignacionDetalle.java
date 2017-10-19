package com.fbcv.ubustus.designacionesfbcv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class DesignacionDetalle extends AppCompatActivity {
    TextView tvTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designacion_detalle);
        tvTEXT = (TextView) findViewById(R.id.txt_text);

        ArrayList<Partido> myList = (ArrayList<Partido>) getIntent().getSerializableExtra("hola");



        Bundle b = getIntent().getExtras();
        int value = -1; // or other values
        if(b != null)
            value = b.getInt("key");


        myList.get(value).getCodigo();



        tvTEXT.setText("TEXT "+myList.get(value).getCodigo());


    }




}
