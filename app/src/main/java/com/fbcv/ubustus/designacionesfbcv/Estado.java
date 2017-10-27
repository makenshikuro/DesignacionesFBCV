package com.fbcv.ubustus.designacionesfbcv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import persistence.database.AppDatabase;
import persistence.entity.Partido;

public class Estado extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado);
       // Log.d("TAG INI", "onCreate() Restoring previous state");
        db = AppDatabase.getAppDatabase(this);
        //DBInitializer.populateAsync(db);
        //Log.d("TAG FINISH", "Insertado");

        List<Partido> categorias =  db.partidoDAO().getAll();
        Log.i("TAG Deuda", "Deuda: "+categorias.size());
        for (Partido p : categorias){
            Log.i("TAG Codigo", "Codigo: "+p.getCodigo());
        }



        //Log.i("TAG Deuda", "Deuda: "+categorias.size());




    }


}
