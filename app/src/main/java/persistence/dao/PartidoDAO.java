package persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import persistence.entity.Partido;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;


@Dao
public interface PartidoDAO {
    @Query("SELECT * FROM partido")
    List<Partido> getAll();

    @Insert
    void insertAll(List<Partido> partido);

    @Insert(onConflict = IGNORE)
    void insertPartido(Partido partido);

    @Delete
    void delete(Partido partido);
}
