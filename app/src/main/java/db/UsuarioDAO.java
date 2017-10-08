package db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Ubustus on 08/10/2017.
 */

@Dao
public interface UsuarioDAO {
    @Query("SELECT * FROM user")
    List<Usuario> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<Usuario> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Usuario findByName(String first, String last);

    @Insert
    void insertAll(Usuario... users);

    @Delete
    void delete(Usuario user);
}
