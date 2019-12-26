package com.example.librarysystem;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Livro.class, version = 1, exportSchema = false)
public abstract class LivroDB extends RoomDatabase {

    public abstract LivroDao livroDao();

    public static final String DATABASE_NAME = "livroDb";

    public static LivroDB instance;

    public static LivroDB getInstance(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context, LivroDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }
}
