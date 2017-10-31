package com.fbcv.ubustus.designacionesfbcv;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import persistence.entity.Partido;

public class BuscaDesignacion extends AppCompatActivity {

    private AppDatabase db;

    private Button getBtn;
    private Context ctx;

    private TextView tvPrevisto, tvLastUpdate, tvNumPartidos, dCodigo, dCategoria,dEstado,dEncuentro,dDia;
    int npartidos;
    String lastupdate ,distancia="1", tiempo="1";
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




        lv1 =(ListView) findViewById(R.id.lista_semanal);
        listAdapter = new ListViewAdapter(this, R.layout.list_view_items, al);
        lv1.setAdapter(listAdapter);


        listAdapter.notifyDataSetChanged();
        new Designacion().execute();



        getBtn = (Button) findViewById(R.id.getBtn);
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
                                db = AppDatabase.getAppDatabase(ctx);

                                List<Partido> partidosSemana = db.partidoDAO().getPartidosSemana(dateLastWeek);
                                Log.d("TAG", "Date: "+dateLastWeek);
                                Log.d("TAG", "Partidos: "+partidosSemana.size());

                                db.partidoDAO().deleteAll(partidosSemana);

                                //db.partidoDAO().insertAll(al);

                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Guardado ", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {

                            @Override
                            public void onCancel(DialogInterface dialog) {
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Cancelado ", Toast.LENGTH_SHORT).show();

                            }
                        }).create();

                dialog.show();




                //db.partidoDAO().insertPartido(al.get(0));

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


                //
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
                        .data("usuarioTextBox", "48592698T")
                        .data("passwordTextBox", "hitsug4y4")
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
                        cat = "Infantil";
                    }else if (categoria.contains("Cad")){
                        cat = "Cadete";
                    }else if (categoria.contains("Jr")){
                        cat = "Jr";
                    }else if (categoria.contains("Sr")){
                        cat = "Sr";
                    }

                    if (categoria.contains("Masc")){
                        cat = cat + " Masc";
                    }else if (categoria.contains("Fem")){
                        cat = cat + " Fem";
                    }

                    if (categoria.contains("IR")){
                        cat = cat + " IR";
                    }else if (categoria.contains("Ayto")||categoria.contains("Cons")){
                        cat = cat + " Ayto";
                    }

                    if (categoria.contains("Zonal")){
                        if (categoria.contains("2ª")){
                            cat = cat + " 2da Zonal";
                        }else if(categoria.contains("1ª")){
                            cat = cat + " 1ra Zonal";
                        }else{
                            cat = cat + " Zonal";
                        }

                    }else if (categoria.contains("Pref")){
                        cat = cat + " Preferente";
                    }else if (categoria.contains("Aut")){
                        cat = cat + " Autonomico";
                    }else if (categoria.contains("Divi")){
                        cat = cat + " Nacional";
                    }else if (categoria.contains("EBA")){
                        cat = cat + " EBA";
                    }else if (categoria.contains("PLATA")){
                        cat = cat + " LEB Plata";
                    }else if (categoria.contains("L.F.-2")){
                        cat = cat + " Liga Femenina 2";
                    }else if (categoria.contains("Conso")){
                        cat = cat + " Consorcio";
                    }

                    float cuota = 0;
                    try {
                        //cuota = AccesoDatos.getCuotaCategoria(ds.getConnection(), cat, user.getRol());
				        /*System.out.println("cuotaARB: "+ cuota);*/
                    } catch (Exception e) {e.printStackTrace();}
                    //puesto por defecto (hay que recoger este valor con jsoup)
                    /*Log.d("TAG","C"+codigo);
                    Log.d("TAG","E"+encuentro);
                    Log.d("TAG","D"+date.toString());
                    Log.d("TAG","C"+cat);
                    Log.d("TAG","L"+localidad);
                    Log.d("TAG","C"+cuota);
                    Log.d("TAG","C"+distancia);
                    Log.d("TAG","C"+tiempo);
                    Log.d("TAG","C"+desplazamiento);
                    Log.d("TAG","C"+total);
                    Log.d("TAG","C"+estado);*/

                    Partido p = new Partido(codigo, encuentro, datePartido, cat, localidad, cuota, distancia, tiempo, desplazamiento, total, estado );
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
            tvPrevisto.setText("Previsto: 4€");
        }
    }






}

