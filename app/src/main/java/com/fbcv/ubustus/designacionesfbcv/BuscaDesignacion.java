package com.fbcv.ubustus.designacionesfbcv;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuscaDesignacion extends AppCompatActivity {

    private Button getBtn;
    private ArrayAdapter<String> adaptador;
    private TextView result, lastUpdate, num_partidos;
    int npartidos;
    String lastupdate;
    private ListView lv1;
    ArrayList<Partido> al = new ArrayList<Partido>();

    ArrayList<String> listItems=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_designacion);


        result = (TextView) findViewById(R.id.result);

        adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listItems);
        lv1=(ListView)findViewById(R.id.lista_semanal);
        lv1.setAdapter(adaptador);




        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Title().execute();
            }
        });
    }

    // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {

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

                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
                    DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                    Date date = new Date();
                    try {date = formatter.parse(fecha);} catch (ParseException e) {e.printStackTrace();}

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
                    Partido p = new Partido(codigo, encuentro, date, cat, localidad, cuota, estado);
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

            for(Partido p : al){
                listItems.add(p.getCodigo());
            }
            adaptador.notifyDataSetChanged();

            result.setText("Hecho");
        }
    }

}

