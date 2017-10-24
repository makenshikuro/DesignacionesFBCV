package db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface PartidoDAO {
    @Query("SELECT * FROM user")
    List<Partido> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<Partido> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Partido findByName(String first, String last);

    @Insert
    void insertAll(Partido... partido);

    @Delete
    void delete(Partido partido);
}
