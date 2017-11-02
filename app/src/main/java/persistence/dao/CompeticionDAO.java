package persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import persistence.entity.Competicion;


@Dao
public interface CompeticionDAO {
    @Query("SELECT * FROM competicion")
    List<Competicion> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCompeticion(Competicion competicion);
    @Delete
    void delete(Competicion competicion);
}
