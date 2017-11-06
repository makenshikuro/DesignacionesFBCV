package com.fbcv.ubustus.designacionesfbcv;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import persistence.database.AppDatabase;
import persistence.entity.Competicion;
import persistence.entity.Partido;
import persistence.utils.DBInitializer;

public class BuscaDesignacion extends AppCompatActivity {

    private AppDatabase db;

    private Button getBtn;
    private Context ctx;
    public static float eurosxkm = 0.16f;

    private TextView tvPrevisto, tvLastUpdate, tvNumPartidos, dCodigo, dCategoria,dEstado,dEncuentro,dDia;
    int npartidos;
    String lastupdate ;
    String dni, passWeb, rol, origen;
    int tiempo = 0, distancia = 0;
    float total = 0, desplazamiento=0;
    ListView lv1;
    ArrayList<Partido> al = new ArrayList<Partido>();
    ListViewAdapter listAdapter;

    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
    DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    DateFormat formatoSQL = new SimpleDateFormat("yyyy-MM-dd");
    Date datePartido = new Date();
    Date dateLastWeek = new Date();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_busca_designacion);

        tvLastUpdate = findViewById(R.id.txt_semanaX);
        tvPrevisto = findViewById(R.id.txt_previsto);
        tvNumPartidos = findViewById(R.id.txt_numPartidos);
        getBtn = findViewById(R.id.getBtn);

        lv1 = findViewById(R.id.lista_semanal);
        listAdapter = new ListViewAdapter(this, R.layout.list_view_items, al);
        lv1.setAdapter(listAdapter);

        listAdapter.notifyDataSetChanged();
        new Designacion().execute();

        /* Recoger valores del archivo de preferencias */
        SharedPreferences prefs = getSharedPreferences("FBCVPref", Context.MODE_PRIVATE);
        dni = prefs.getString("dni", "no");
        passWeb = prefs.getString("passWeb", "no");
        rol = prefs.getString("rol", "no");
        origen = prefs.getString("origen", "no");

        /* Recojemos referencia de la BBDD */
        db = AppDatabase.getAppDatabase(ctx);

        /* Lanzamos Clasecon los valores por Defecto */
        DBInitializer.populateAsync(db);

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog dialog = new AlertDialog.Builder(ctx)
                        .setMessage("¿Desea Guardar la Designación?")
                        .setTitle("Confirmación")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Log.d("TAG","E"+formatoSQL.format(date));
                                //Log.d("TAG", "Date: "+dateLastWeek);
                                //Log.d("TAG", "Partidos: "+partidosSemana.size());
                                db = AppDatabase.getAppDatabase(ctx);

                                /* Buscamos los partidos de la semana actual */
                                List<Partido> partidosSemana = db.partidoDAO().getPartidosSemana(dateLastWeek);
                                // Borramos los partidos de la semana actual
                                db.partidoDAO().deleteAll(partidosSemana);
                                // Insertamos los partidos de la semana
                                db.partidoDAO().insertAll(al);

                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Guardado ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {}
                        })
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {

                            @Override
                            public void onCancel(DialogInterface dialog) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Cancelado ", Toast.LENGTH_SHORT).show();
                            }
                        }).create();
                dialog.show();
            }
        });

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, final int position, long id) {

                LayoutInflater inflater = LayoutInflater.from(BuscaDesignacion.this);
                final View vistaDetalle = inflater.inflate(R.layout.dialog_info_detalle, null);

                dCodigo = vistaDetalle.findViewById(R.id.txt_detalle_codigo);
                dCategoria = vistaDetalle.findViewById(R.id.txt_detalle_categoria);
                dEstado = vistaDetalle.findViewById(R.id.txt_detalle_estado);
                dEncuentro = vistaDetalle.findViewById(R.id.txt_detalle_encuentro);
                dDia = vistaDetalle.findViewById(R.id.txt_detalle_dia);

                final AlertDialog dialog = new AlertDialog.Builder(ctx)
                        .setMessage("Información")
                        .setView(vistaDetalle)
                        .setTitle("hola")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {

                            @Override
                            public void onCancel(DialogInterface dialog) {
                                dialog.dismiss();

                            }
                        }).create();

                dCategoria.setText(al.get(position).getCategoria());
                dCodigo.setText(al.get(position).getCodigo());
                dEncuentro.setText(al.get(position).getEncuentro());
                //dEstado.setText(al.get(position).getEstado());
                dDia.setText(al.get(position).getFecha().toString());

                dialog.show();
                Toast.makeText(getApplicationContext(), "presiono " + position, Toast.LENGTH_SHORT).show();
            }
        });

        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView adapterView, View view, final int position, long l) {
                Intent intent = new Intent(BuscaDesignacion.this, DesignacionDetalle.class);
                intent.putExtra("hola",al);
                Bundle b = new Bundle();
                b.putInt("key", position); //Your id

                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "presiono LARGO " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private class Designacion extends AsyncTask<Void, Void, Void> {

        String urlLogin = "https://intrafeb.feb.es/OficinaWebArbitro/";

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Connection.Response resp = Jsoup.connect(urlLogin)
                        .method(Connection.Method.GET)
                        .execute();

                Document loginPage = resp.parse();
                Element eventValidation = loginPage.select("input[name=__EVENTVALIDATION]").first();
                Element viewState = loginPage.select("input[name=__VIEWSTATE]").first();

                resp = Jsoup.connect(urlLogin)
                        .data("__VIEWSTATE", viewState.attr("value"))
                        .data("__EVENTVALIDATION", eventValidation.attr("value"))
                        .data("usuarioTextBox", dni)
                        .data("passwordTextBox", passWeb)
                        .data("autenticarLinkButton", "")
                        .data("ambitoDropDownList","2")
                        .method(Connection.Method.POST)
                        .execute();

                Document doc = Jsoup.connect("https://intrafeb.feb.es/OficinaWebArbitro/Paginas/Designaciones.aspx")
                        .cookies(resp.cookies())
                        .get();

                Elements rows = doc.select("#designacionesDataGrid tr");
                npartidos = rows.size()-2;

                Elements update = doc.select("#fechaPubTextBox");
                lastupdate = update.attr("value");
                //lastupdate = lastupdate.replaceAll("\\.","-");
                //Log.d("Fecha","Date: "+lastupdate);
                try {
                    dateLastWeek = format.parse(lastupdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                List<Competicion> competiciones = db.competicionDAO().getAll();


                for (int i = 0; i < npartidos;i++){

                    Element fechaLabel = doc.getElementById("designacionesDataGrid_fechaLabel_"+i);
                    String fecha = fechaLabel.html().replaceAll("<b>Día:</b>", "").replaceAll("<br><b>Hora:</b>", "").replaceAll("\\(([^)]+)\\)", "");
                    Element partidoLabel = doc.getElementById("designacionesDataGrid_partidoLabel_"+i);
                    String encuentro = partidoLabel.html().replace(" ","");
                    String codigo;
                    String categoria;
                    Element localidadLabel = doc.getElementById("designacionesDataGrid_campoLabel_"+i);
                    String localidad = localidadLabel.html().replace(" ","");
                    Element estadoLabel = doc.getElementById("designacionesDataGrid_aceptadaLabel_"+i);
                    String estado = estadoLabel.html().replace(" ","");
                    String[] lines = encuentro.split("<b>");
                    encuentro = lines[1].replaceAll("</b><br>", "").replaceAll("\\(([^)]+)\\)", "");
                    //System.out.println("encuentro"+i+" : "+ encuentro);
                    codigo = lines[1].replaceAll("</b><br>", "");
                    categoria = lines[3].replaceAll("Cat.:</b>", "").replaceAll("<br>", "");

                    lines = localidad.split("<b>");
                    localidad = lines[3].replaceAll("Localidad:</b>", "").replaceAll("<br>", "");

                    Matcher Mcod = Pattern.compile("\\(([^)]+)\\)").matcher(codigo);
                    while(Mcod.find()) {codigo = Mcod.group(1);}




                    try {datePartido = formatter.parse(fecha);} catch (ParseException e) {e.printStackTrace();}

                    String cat="";

                    if (categoria.contains("Ben")){
                        cat = "Benjamin";
                    }else if (categoria.contains("Ale")){
                        cat = "Alevin";
                    }else if (categoria.contains("Inf")){
                        cat = "Inf";
                    }else if (categoria.contains("Cad")){
                        cat = "Cad";
                    }else if (categoria.contains("Jr")){
                        cat = "Jr";
                    }else if (categoria.contains("Sr")){
                        cat = "Sr";
                    }

                    if (categoria.contains("Masc")){
                        cat = cat + "Masc";
                    }else if (categoria.contains("Fem")){
                        cat = cat + "Fem";
                        if (categoria.contains("Nivel 2")||categoria.contains("Nivel2")) {
                            cat = cat + "2";
                        }else if(categoria.contains("Nivel 1")||categoria.contains("Nivel1")){
                            cat = cat + "1";
                        }
                    }

                    if (categoria.contains("IR")){
                        cat = cat + "IR";
                        if (categoria.contains("Nivel 3")||categoria.contains("Nivel3")){
                            cat = cat + "3";
                        }
                        else if (categoria.contains("Nivel 2")||categoria.contains("Nivel2")){
                            cat = cat + "2";
                        }
                        else if (categoria.contains("Nivel 1")||categoria.contains("Nivel1")){
                            cat = cat + "1";
                        }
                    }else if (categoria.contains("Ayto")||categoria.contains("Cons")){
                        cat = cat + "Ayto";
                    }

                    if (categoria.contains("Zonal")){
                        if (categoria.contains("2ª")){
                            cat = cat + "2";
                        }else if(categoria.contains("1ª")){
                            cat = cat + "1";
                        }else{
                            cat = cat + "Zonal";
                        }

                    }else if (categoria.contains("Pref")){
                        cat = cat + "Pref";
                    }else if (categoria.contains("Aut")){
                        cat = cat + "Aut";
                    }else if (categoria.contains("Divi")){
                        cat = cat + "Nac";
                    }else if (categoria.contains("EBA")){
                        cat = cat + "EBA";
                    }else if (categoria.contains("PLATA")){
                        cat = cat + "Plata";
                    }else if (categoria.contains("L.F.-2")){
                        cat = cat + "LF2";
                    }else if (categoria.contains("Conso")){
                        cat = cat + "Consorcio";
                    }

                    float cuota = 0;

                    /* Extraemos la cuota del partido segun el Rol */
                    for (Competicion c : competiciones){
                        if (c.getId().equals(cat)){
                            if(rol.equals("ARB")){
                                cuota = c.getCuotaARB();
                            }else{
                                cuota = c.getCuotaOM();
                            }
                            categoria = c.getNombre();
                        }
                    }


                    String urlGoogleAPI = "https://maps.googleapis.com/maps/api/distancematrix/json?language=es&origins="+origen+"&destinations="+localidad+"&avoid=tolls";

                    try{
                        JSONObject json = new JSONObject(readUrl(urlGoogleAPI));
                        //String title = (String) json.get("rows");
                        JSONArray matrixArray = json.getJSONArray("rows");
                        if (json != null && json.get("status").equals("OK")) {
                                for (int indice = 0; indice < matrixArray.length(); indice++) {
                                    JSONArray elementArray = matrixArray.getJSONObject(indice).getJSONArray("elements");
                                    for (int indice2 = 0; indice2 < elementArray.length(); indice2++) {
                                        JSONObject distanceObj = elementArray.getJSONObject(indice2).getJSONObject("distance");
                                        String distance = distanceObj.getString("value");
                                        distancia = Integer.valueOf(distance)/1000;
                                        JSONObject durationObj = elementArray.getJSONObject(indice2).getJSONObject("duration");
                                        String duration = durationObj.getString("value");
                                        tiempo = Integer.valueOf(duration)/60;
                                        Log.d("JSON", ""+distancia);
                                        Log.d("JSON2", ""+tiempo);
                                    }
                                }
                        }
                    }catch (Exception e){e.printStackTrace();}

                    desplazamiento = eurosxkm * distancia;
                    if (desplazamiento * 2 < 4.0f){
                        desplazamiento = 4.0f;
                    }


                    total = total + cuota + desplazamiento;

                    Log.d("CATEGOR","Cate: "+cat);
                    Log.d("CUOTA","Cuota: "+cuota);
                    Log.d("DES","Desp: "+desplazamiento);


                    //puesto por defecto (hay que recoger este valor con jsoup)

                    Partido p = new Partido(codigo, encuentro, datePartido, categoria, localidad, cuota, distancia, tiempo, desplazamiento, total, estado );
                    al.add(p);

                }
                Log.d("TOTAL","Total: "+total);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listAdapter.notifyDataSetChanged();
            tvNumPartidos.setText("Partidos: "+npartidos);
            tvLastUpdate.setText("Semana: "+lastupdate);
            tvPrevisto.setText("Previsto: "+total+"€");
        }
    }


    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }






}

