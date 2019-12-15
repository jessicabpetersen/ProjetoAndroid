package com.example.projeto;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LivroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLivro(Livro livro);

    @Delete
    void deleteLivro(Livro livro);

    @Update
    void updateLivro(Livro livro);

    @Query("Select * FROM livro")
    List<Livro> getLivro();

    @Query("SELECT * FROM livro WHERE id = :id")
    Livro getLivroById(int id);

    @Query("DELETE FROM livro where id = :id")
    void deleteLivroById(int id);

}
