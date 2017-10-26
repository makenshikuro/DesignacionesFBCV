package persistence.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import persistence.entity.Categoria;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
@Dao
public interface CategoriaDAO {
    @Query("SELECT * FROM categoria")
    List<Categoria> getAll();

    @Insert
    void insertAll(Categoria... categorias);

    @Insert(onConflict = IGNORE)
    void insertCategoria(Categoria categoria);


    @Delete
    void delete(Categoria categoria);

    @Query("DELETE FROM categoria")
    void deleteAll();
}
