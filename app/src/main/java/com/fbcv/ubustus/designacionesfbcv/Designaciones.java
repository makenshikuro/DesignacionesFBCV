package com.fbcv.ubustus.designacionesfbcv;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import persistence.database.AppDatabase;
import persistence.entity.Partido;

public class Designaciones extends AppCompatActivity {

    ListView listView;
    private AppDatabase db;
    ListViewAdapter listAdapter;
    private Context ctx =this;
    List<Partido> partidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designaciones);

        db = AppDatabase.getAppDatabase(ctx);


        partidos = db.partidoDAO().getAll();


        listView =(ListView) findViewById(R.id.lista_semanal);
        listAdapter = new ListViewAdapter(this, R.layout.list_view_items, partidos);
        listView.setAdapter(listAdapter);


        listAdapter.notifyDataSetChanged();



    }
}
