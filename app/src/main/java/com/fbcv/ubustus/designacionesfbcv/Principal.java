package com.fbcv.ubustus.designacionesfbcv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {

    Button buscaDesignacion, designaciones, estado, configuracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        buscaDesignacion = (Button) findViewById(R.id.btn_buscaDesignacion);
        designaciones = (Button) findViewById(R.id.btn_designaciones);
        estado = (Button) findViewById(R.id.btn_estado);
        configuracion = (Button) findViewById(R.id.btn_configuracion);

        buscaDesignacion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent (Principal.this, BuscaDesignacion.class);
                Principal.this.startActivity(intent);
            }
        });

        designaciones.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent (Principal.this, Designaciones.class);
                Principal.this.startActivity(intent);
            }
        });

        estado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent (Principal.this, Estado.class);
                Principal.this.startActivity(intent);
            }
        });

        configuracion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent (Principal.this, Configuracion.class);
                Principal.this.startActivity(intent);
            }
        });

    }


}
