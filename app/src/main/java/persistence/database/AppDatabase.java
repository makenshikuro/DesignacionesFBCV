package persistence.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import persistence.dao.CategoriaDAO;
import persistence.dao.CompeticionDAO;
import persistence.dao.PartidoDAO;
import persistence.entity.Categoria;
import persistence.entity.Competicion;
import persistence.entity.Partido;
import persistence.utils.Converters;


@Database(entities = {Categoria.class, Partido.class, Competicion.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    public abstract CategoriaDAO categoriaDAO();
    public abstract PartidoDAO partidoDAO();
    public abstract CompeticionDAO competicionDAO();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "fbcv-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()

                            .build();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
