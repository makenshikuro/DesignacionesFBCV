package com.fbcv.ubustus.designacionesfbcv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class Registro extends Activity implements View.OnClickListener{

    EditText licencia, dni, correo, passWeb, origen;
    Spinner categoria, rol;
    Button btn_registrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        licencia = (EditText) findViewById(R.id.regLicencia);
        origen = (EditText) findViewById(R.id.regOrigen);
        dni = (EditText) findViewById(R.id.regDni);
        correo = (EditText) findViewById(R.id.regEmail);
        passWeb = (EditText) findViewById(R.id.regPassWeb);

        categoria = (Spinner) findViewById(R.id.regSpinnerCategoria);
        rol = (Spinner) findViewById(R.id.regSpinnerRol);


        Resources res = getResources();
        String[] categorias = res.getStringArray(R.array.categoria_arrays);
        String[] roles = res.getStringArray(R.array.rol_arrays);
        String categoria_prompt = res.getString(R.string.categoria_prompt);
        String rol_prompt = res.getString(R.string.rol_prompt);

        ArrayAdapter<CharSequence> adapterCat = ArrayAdapter.createFromResource(this,
                R.array.categoria_arrays, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterRol = ArrayAdapter.createFromResource(this,
                R.array.rol_arrays, android.R.layout.simple_spinner_item);

        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterRol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoria.setAdapter(adapterCat);
        categoria.setPrompt(categoria_prompt);
        categoria.setSelection(1);

        rol.setPrompt(rol_prompt);
        rol.setAdapter(adapterRol);
        rol.setSelection(1);

        btn_registrar = (Button) findViewById(R.id.btn_registroSubmit);

        btn_registrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


            SharedPreferences prefs = getSharedPreferences("FBCVPref", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("licencia", licencia.getText().toString());
            editor.putString("dni", dni.getText().toString());
            editor.putString("email", correo.getText().toString());
            editor.putString("origen", origen.getText().toString());
            editor.putString("passWeb", passWeb.getText().toString());
            editor.putString("categoria", categoria.getSelectedItem().toString());
            editor.putString("rol", rol.getSelectedItem().toString());

            editor.commit();

            Intent intentMain = new Intent (Registro.this, MainActivity.class);
            Registro.this.startActivity(intentMain);


        }



}




