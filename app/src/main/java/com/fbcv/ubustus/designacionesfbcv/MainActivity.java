package com.fbcv.ubustus.designacionesfbcv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_registrar;
    EditText password;
    Button iniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences prefs = getSharedPreferences("FBCVPref", Context.MODE_PRIVATE);
        String licencia = prefs.getString("licencia", "no");

        Log.i("licencia:", licencia);

        if (licencia.equals("no")){
            setContentView(R.layout.activity_main_first_time);
            tv_registrar = (TextView) findViewById(R.id.tv_registrar);

            tv_registrar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intentReg = new Intent (MainActivity.this, Registro.class);
                    MainActivity.this.startActivity(intentReg);
                }
            });
        }
        else {
            setContentView(R.layout.activity_main);

            iniciar = (Button) findViewById(R.id.btn_iniciar);
            password = (EditText) findViewById(R.id.TV_password);


            iniciar.setOnClickListener(this);


        }

    }

    @Override
    public void onClick(View v) {
        SharedPreferences prefs = getSharedPreferences("FBCVPref", Context.MODE_PRIVATE);
        String passwordBD = prefs.getString("password", "no");


        if(password.getText().toString().equals(passwordBD)&&(!password.equals(""))&&(password.getText().toString().length()>0)) {
            Intent intentPrincipal = new Intent (MainActivity.this, Principal.class);
            MainActivity.this.startActivity(intentPrincipal);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setMessage("Error")
                    .setTitle(R.string.error_password);


            AlertDialog dialog = builder.create();

            dialog.show();

        }




    }
}
