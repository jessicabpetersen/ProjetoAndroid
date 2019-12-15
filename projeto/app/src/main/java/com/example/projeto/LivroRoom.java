package com.example.projeto;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Livro.class, version = 1)
public abstract class LivroRoom extends RoomDatabase {
    public abstract LivroDao livroDao();

    public static final String DATABASE_NOME = "livrosBD";

    private static LivroRoom instance;

    public synchronized static LivroRoom getInstance(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context, LivroRoom.class, DATABASE_NOME)
                    .allowMainThreadQueries()
                    .build();

        return instance;
    }
}
