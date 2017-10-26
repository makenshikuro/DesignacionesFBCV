package persistence.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import persistence.database.AppDatabase;
import persistence.entity.Categoria;

/**
 * Created by Ubustus on 26/10/2017.
 */

public class DBInitializer {

    private static final String TAG = DBInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }



    private static void populateWithTestData(AppDatabase db) {

        Log.d(DBInitializer.TAG, "Rows HOLA ENTRO: ");
        Calendar calendar = Calendar.getInstance();

        Date today = calendar.getTime();
        //Categoria cat = new Categoria("1887565", "Amistoso", "Alaquas-X", today, "Alaquàs,València", "30", "15", 10, 20 );

        Categoria cat = new Categoria("Zonal", 40, 60);
        db.categoriaDAO().insertCategoria(cat);

        List<Categoria> userList = db.categoriaDAO().getAll();
        //Log.d(DBInitializer.TAG, "Rows Count: " + userList.size());
        Log.d(DBInitializer.TAG, "Rows Count: "+cat.getNombre() );
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
