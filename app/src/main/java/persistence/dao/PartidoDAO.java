package persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import persistence.entity.Partido;



@Dao
public interface PartidoDAO {
    @Query("SELECT * FROM partido")
    List<Partido> getAll();

    @Query("SELECT * FROM partido WHERE fecha>=:date")
    List<Partido> getPartidosSemana(Date date);

    @Insert
    void insertAll(List<Partido> partido);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPartido(Partido partido);

    @Delete
    void delete(Partido partido);

    @Delete
    void deleteAll(List<Partido> partido);


}
