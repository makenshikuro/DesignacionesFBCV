package com.fbcv.ubustus.designacionesfbcv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DesignacionDetalle extends AppCompatActivity {
    TextView tvTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvTEXT = (TextView) findViewById(R.id.txt_text);
        setContentView(R.layout.activity_designacion_detalle);
        /*Intent i = getIntent();
        int position = i.getIntExtra("position", 0);*/

        //tvTEXT.setText("TEXT");


    }




}
