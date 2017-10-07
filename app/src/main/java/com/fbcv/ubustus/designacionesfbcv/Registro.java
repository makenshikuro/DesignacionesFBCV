package com.fbcv.ubustus.designacionesfbcv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Registro extends AppCompatActivity implements View.OnClickListener {

    EditText et_nombre;
    Button btn_registrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        et_nombre = (EditText) findViewById(R.id.tv_regNombre);
        btn_registrar = (Button) findViewById(R.id.btn_registroSubmit);

        btn_registrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
