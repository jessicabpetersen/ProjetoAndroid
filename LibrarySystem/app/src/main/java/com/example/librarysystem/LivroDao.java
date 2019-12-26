package com.example.librarysystem;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;

@Dao
public interface LivroDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void insertLivro(Livro livro);

    @Delete
    void deleteLivro(Livro... livro);

    @Update
    void updateLivro(Livro livro);

    @Query("SELECT * FROM livros")
    ArrayList<Livro> getLivros();

}
