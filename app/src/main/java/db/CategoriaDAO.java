package db;


import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

public interface CategoriaDAO {
    @Query("SELECT * FROM categoria")
    List<Categoria> getAll();

    @Query("SELECT * FROM categoria WHERE uid IN (:userIds)")
    List<Categoria> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Categoria findByName(String first, String last);

    @Insert
    void insertAll(Categoria... users);

    @Delete
    void delete(Categoria categoria);
}
