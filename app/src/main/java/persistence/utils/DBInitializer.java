package persistence.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import persistence.database.AppDatabase;
import persistence.entity.Competicion;

/**
 * Created by Ubustus on 26/10/2017.
 */

public class DBInitializer {

    private static final String TAG = DBInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }



    private static void populateWithTestData(AppDatabase db) {

        Log.d(DBInitializer.TAG, "Rows HOLA ENTRO: ");

        Competicion competicion00 = new Competicion("BenjaminAyto","Benjamin Ayto",5.0f,5.0f);
        Competicion competicion01 = new Competicion("AlevinAyto","Alevin Ayto",5.0f,5.0f);
        Competicion competicion02 = new Competicion("InfAyto","Infantil Ayto",5.0f,5.0f);
        //Competicion competicion02 = new Competicion("InfMascAyto","Infantil Masc. Ayto",0.0f,0.0f);
        //Competicion competicion031 = new Competicion("InfFemAyto","Infantil Fem. Ayto",0.0f,0.0f);
        Competicion competicion04 = new Competicion("CadMascAyto","Cadete Masc. Ayto",5.0f,5.0f);
        Competicion competicion05 = new Competicion("CadFemAyto","Cadete Fem. Ayto",5.0f,5.0f);
        Competicion competicion1 = new Competicion("AlevinIR2","Alevin IR Nivel 2",5.0f,5.0f);
        Competicion competicion2 = new Competicion("AlevinIR1","Alevin IR Nivel 1",5.0f,5.0f);
        Competicion competicion3 = new Competicion("BenjaminIR3","Benjamin IR Nivel 3",5.0f,5.0f);
        Competicion competicion4 = new Competicion("BenjaminIR2","Benjamin IR Nivel 2",5.0f,5.0f);
        Competicion competicion5 = new Competicion("BenjaminIR1","Benjamin IR Nivel 1",5.0f,5.0f);
        Competicion competicion6 = new Competicion("InfMascIR3","Infantil IR Masc. Nivel 3",5.0f,5.0f);
        Competicion competicion7 = new Competicion("InfMascIR2","Infantil IR Masc. Nivel 2",5.0f,5.0f);
        Competicion competicion8 = new Competicion("InfMascIR1","Infantil IR Masc. Nivel 1",5.0f,5.0f);
        Competicion competicion9 = new Competicion("InfFemIR3","Infantil IR Fem. Nivel 3",5.0f,5.0f);
        Competicion competicion10 = new Competicion("InfFemIR2","Infantil IR Fem. Nivel 2",5.0f,5.0f);
        Competicion competicion11 = new Competicion("InfFemIR1","Infantil IR Fem. Nivel 1",5.0f,5.0f);
        Competicion competicion16 = new Competicion("CadMascIR3","Cadete IR Masc. Nivel 3",5.0f,5.0f);
        Competicion competicion17 = new Competicion("CadMascIR2","Cadete IR Masc. Nivel 2",5.0f,5.0f);
        Competicion competicion18 = new Competicion("CadMascIR1","Cadete IR Masc. Nivel 1",5.0f,5.0f);
        Competicion competicion19 = new Competicion("CadFemIR3","Cadete IR Fem. Nivel 3",5.0f,5.0f);
        Competicion competicion20 = new Competicion("CadFemIR2","Cadete IR Fem. Nivel 2",5.0f,5.0f);
        Competicion competicion21 = new Competicion("CadFemIR1","Cadete IR Fem. Nivel 1",5.0f,5.0f);
        Competicion competicion221 = new Competicion("JrFem1","Jr Femenino Nivel 1",19.0f,11.6f);
        Competicion competicion222 = new Competicion("JrFem2","Jr Femenino Nivel 2",15.5f,11.6f);
        Competicion competicion223 = new Competicion("JrFemCtoAut","Jr Femenino Cto Autonómico",19.0f,11.6f);
        Competicion competicion224 = new Competicion("JrFemCtoPref","Jr Femenino Cto Preferente",17.5f,11.6f);
        Competicion competicion22 = new Competicion("JrFemCtoZonal","Jr Femenino Cto 1ra Zonal",15.5f,11.6f);
        Competicion competicion24 = new Competicion("SrFemCtoPref","Sr Femenino Cto Preferente",19.5f,11.6f);
        Competicion competicion25 = new Competicion("SrFemCtoAut","Sr Femenino Cto Autonómico",21.5f,11.6f);
        Competicion competicion26 = new Competicion("SrFemAut","Sr Femenino Autonómico",19.5f,11.6f);
        Competicion competicion261 = new Competicion("SrFemPref","Sr Femenino Preferente",19.5f,11.6f);
        Competicion competicion27 = new Competicion("SrFemNac","Sr Femenino Nacional",42.25f,14.75f);
        Competicion competicion28 = new Competicion("SrFemLF2","Sr Lliga Femenina 2",0.0f,26.0f);
        Competicion competicion29 = new Competicion("SrFemLF1","Sr Lliga Femenina",0.0f,40.0f);
        Competicion competicion30 = new Competicion("JrMasc1","Jr Masculino 1ra Zonal",16.0f,11.6f);
        Competicion competicion31 = new Competicion("JrMascPref","Jr Masculino Preferente",18.0f,11.6f);
        Competicion competicion32 = new Competicion("JrMascAut","Jr Masculino Autonómico",22.0f,11.6f);
        Competicion competicion34 = new Competicion("SrMascZonal2","Sr Masculino 2da Zonal",19.5f,11.6f);
        Competicion competicion35 = new Competicion("SrMascZonal1","Sr Masculino 1ra Zonal",21.5f,11.6f);
        Competicion competicion36 = new Competicion("SrMascPref","Sr Masculino Preferente",25.5f,13.6f);
        Competicion competicion37 = new Competicion("SrMascAut","Sr Masculino Autonómico",42.25f,14.75f);
        Competicion competicion38 = new Competicion("SrMascNac","Sr Masculino Nacional",72.0f,18.0f);
        Competicion competicion39 = new Competicion("SrMascEBA","Sr Masculino Liga EBA",0.0f,24.0f);
        Competicion competicion40 = new Competicion("SrMascPlata","Sr Masculino LEB Plata",0.0f,40.0f);
        Competicion competicion41 = new Competicion("SrMascOro","Sr Masculino LEB Oro",0.0f,45.0f);
        Competicion competicion42 = new Competicion("SrMascACB","Sr Masculino Liga ACB",0.0f,64.0f);

        db.competicionDAO().deleteAll();

        List<Competicion> com = new ArrayList<Competicion>();
        com.add(competicion00);
        com.add(competicion01);
        com.add(competicion02);

        com.add(competicion04);
        com.add(competicion05);
        //com.add(competicion031);
        com.add(competicion1);
        com.add(competicion2);
        com.add(competicion3);
        com.add(competicion4);
        com.add(competicion5);
        com.add(competicion6);
        com.add(competicion7);
        com.add(competicion8);
        com.add(competicion9);
        com.add(competicion10);
        com.add(competicion11);
        com.add(competicion16);
        com.add(competicion17);
        com.add(competicion18);
        com.add(competicion19);
        com.add(competicion20);
        com.add(competicion21);
        com.add(competicion22);
        com.add(competicion221);
        com.add(competicion222);
        com.add(competicion223);
        com.add(competicion224);
        com.add(competicion261);
        com.add(competicion24);
        com.add(competicion25);
        com.add(competicion26);
        com.add(competicion27);
        com.add(competicion28);
        com.add(competicion29);
        com.add(competicion30);
        com.add(competicion31);
        com.add(competicion32);
        com.add(competicion34);
        com.add(competicion35);
        com.add(competicion36);
        com.add(competicion37);
        com.add(competicion38);
        com.add(competicion39);
        com.add(competicion40);
        com.add(competicion41);
        com.add(competicion42);

        db.competicionDAO().insertAll(com);

        //Calendar calendar = Calendar.getInstance();

        //Date today = calendar.getTime();
        //Categoria cat = new Categoria("1887565", "Amistoso", "Alaquas-X", today, "Alaquàs,València", "30", "15", 10, 20 );

        //Categoria cat = new Categoria("Zonal", 40, 60);
        //db.categoriaDAO().insertCategoria(cat);

        //List<Categoria> userList = db.categoriaDAO().getAll();
        Log.d(DBInitializer.TAG, "Rows Count: " + db.competicionDAO().getAll().size());
        //Log.d(DBInitializer.TAG, "Rows Count: "+cat.getNombre() );
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
