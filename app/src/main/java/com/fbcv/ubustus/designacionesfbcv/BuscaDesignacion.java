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

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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

    private TextView tvPrevisto, tvLastUpdate, tvNumPartidos, dCodigo, dCategoria,dEstado,dEncuentro,dDia;
    int npartidos;
    String lastupdate ,distancia="1", tiempo="1";
    String dni, passWeb, rol, origen;
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

        tvLastUpdate = (TextView) findViewById(R.id.txt_semanaX);
        tvPrevisto = (TextView) findViewById(R.id.txt_previsto);
        tvNumPartidos = (TextView) findViewById(R.id.txt_numPartidos);
        getBtn = (Button) findViewById(R.id.getBtn);

        lv1 =(ListView) findViewById(R.id.lista_semanal);
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

                dCodigo = (TextView) vistaDetalle.findViewById(R.id.txt_detalle_codigo);
                dCategoria = (TextView) vistaDetalle.findViewById(R.id.txt_detalle_categoria);
                dEstado = (TextView) vistaDetalle.findViewById(R.id.txt_detalle_estado);
                dEncuentro = (TextView) vistaDetalle.findViewById(R.id.txt_detalle_encuentro);
                dDia = (TextView) vistaDetalle.findViewById(R.id.txt_detalle_dia);

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
                try {
                    dateLastWeek = formatoSQL.parse(lastupdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                List<Competicion> competiciones = db.competicionDAO().getAll();


                for (int i = 0; i < npartidos;i++){

                    Element fechaLabel = doc.getElementById("designacionesDataGrid_fechaLabel_"+i);
                    String fecha = fechaLabel.html().trim().replaceAll("<b>Día:</b>", "").replaceAll("<br><b>Hora:</b>", "").replaceAll("\\(([^)]+)\\)", "");
                    Element partidoLabel = doc.getElementById("designacionesDataGrid_partidoLabel_"+i);
                    String encuentro = partidoLabel.html().trim();
                    String codigo;
                    String categoria;
                    Element localidadLabel = doc.getElementById("designacionesDataGrid_campoLabel_"+i);
                    String localidad = localidadLabel.html().trim();
                    Element estadoLabel = doc.getElementById("designacionesDataGrid_aceptadaLabel_"+i);
                    String estado = estadoLabel.html().trim();
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
                        if (categoria.contains("Nivel 2")) {
                            cat = cat + "2";
                        }else if(categoria.contains("Nivel 1")){
                            cat = cat + "1";
                        }
                    }

                    if (categoria.contains("IR")){
                        cat = cat + "IR";
                        if (categoria.contains("Nivel 3")){
                            cat = cat + "3";
                        }
                        else if (categoria.contains("Nivel 2")){
                            cat = cat + "2";
                        }
                        else if (categoria.contains("Nivel 1")){
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
                    total = total + cuota;

                    Gson gson = new Gson();
                    JSONObject json = new JSONObject(readUrl("..."));


                    String urlGoogleAPI = "https://maps.googleapis.com/maps/api/distancematrix/xml?language=es&origins="+origen+"&destinations="+localidad+"&avoid=tolls";


                    Connection.Response response = Jsoup.connect(urlLogin)
                            .method(Connection.Method.GET)
                            .execute();

                    Document distanceMatrix = resp.parse();





                    Log.d("CATEGOR","Cate: "+cat);
                    Log.d("CUOTA","Cuota: "+cuota);





                    try {
                        //cuota = AccesoDatos.getCuotaCategoria(ds.getConnection(), cat, user.getRol());
				        /*System.out.println("cuotaARB: "+ cuota);*/
                    } catch (Exception e) {e.printStackTrace();}
                    //puesto por defecto (hay que recoger este valor con jsoup)
                    /*Log.d("TAG","C"+codigo);
                    Log.d("TAG","E"+encuentro);
                    Log.d("TAG","D"+date.toString());*/
                    Log.d("CATEGOR","Cate: "+cat);
                   /* Log.d("TAG","L"+localidad);
                    Log.d("TAG","C"+cuota);
                    Log.d("TAG","C"+distancia);
                    Log.d("TAG","C"+tiempo);
                    Log.d("TAG","C"+desplazamiento);
                    Log.d("TAG","C"+total);
                    Log.d("TAG","C"+estado);*/

                    Partido p = new Partido(codigo, encuentro, datePartido, categoria, localidad, cuota, distancia, tiempo, desplazamiento, total, estado );
                    al.add(p);

                }

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






}

