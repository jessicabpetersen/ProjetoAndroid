package com.example.biblioteca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;


public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="biblioteca.db";

    public static final String TABLE_REGISTO="registo";
    public static final String TABLE_ADICIONAR="adicionar";



    public static final String COL_ID="ID";
    public static final String COL_NOME="Nome";
    public static final String COL_EMAIL="Email";
    public static final String COL_PASSWORD="Password";
    public static final String COL_CPASSWORD="ConfirmarPassword";


    public static final String COL_TITULO="Titulo";
    public static final String COL_AUTOR="Autor";
    public static final String COL_EDITORA="Editora";
    public static final String COL_ANO="Ano";
    public static final String COL_IMAGEM="Imagem";
    public static final String COL_QT="Qtotal";
    public static final String COL_QD="Qdisponivel";
    public static final String COL_LOCALIZACAO="Localizacao";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_REGISTO + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Nome TEXT,Email TEXT,Password TEXT,ConfirmarPassword TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_ADICIONAR + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Titulo TEXT,Autor TEXT,Editora TEXT,Ano TEXT, Imagem BLOB,Qtotal TEXT,Qdisponivel TEXT,Localizacao TEXT )");
    }

    public void insertDataBase(Livro livro){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO " + TABLE_ADICIONAR + " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, livro.getTitulo());
        statement.bindString(2, livro.getAutor());
        statement.bindString(3, livro.getEditora());
        statement.bindString(4, livro.getAno());
        statement.bindBlob(5, livro.getImagem());
        statement.bindString(6, String.valueOf(livro.getQuantidadeTotal()));
        statement.bindString(7, String.valueOf(livro.getQuantidadeDisponivel()));
        statement.bindString(8, livro.getLocalizacao());

        statement.executeInsert();
    }

    public void onUpdateBook(Livro livro){
        //METODO 1
//        SQLiteDatabase database = getWritableDatabase();
//        String sql = "UPDATE " + TABLE_ADICIONAR +
//                " SET "+
//                COL_ANO+" = '"+livro.getAno()+"', "+
//                COL_AUTOR+" = '"+livro.getAutor()+"', "+
//                COL_EDITORA+" = '"+livro.getEditora()+"', "+
//                COL_IMAGEM+" = '"+livro.getImagem()+"', "+
//                COL_LOCALIZACAO+" = '"+livro.getLocalizacao()+"', "+
//                COL_TITULO+" = '"+livro.getTitulo()+"', "+
//                COL_QT+" = '"+livro.getQuantidadeTotal()+"', "+
//                COL_QD+" = '"+livro.getQuantidadeDisponivel()+"' " +
//                "WHERE "+
//                COL_TITULO +" = '"+livro.getTitulo()+"' AND "+
//                COL_AUTOR+" = '"+livro.getAutor()+"'";
//        database.execSQL(sql);

        //METODO 2


            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_ID, livro.getId());
            contentValues.put(COL_TITULO, livro.getTitulo());
            contentValues.put(COL_AUTOR, livro.getAutor());
            contentValues.put(COL_EDITORA, livro.getEditora());
            contentValues.put(COL_ANO, livro.getAno());
            contentValues.put(COL_IMAGEM, livro.getImagem());
            contentValues.put(COL_QT, livro.getQuantidadeTotal());
            contentValues.put(COL_QD, livro.getQuantidadeDisponivel());
            contentValues.put(COL_LOCALIZACAO, livro.getLocalizacao());
//        String where = COL_TITULO +" = ? and "+COL_AUTOR+" = ?";
//        String args[] ={livro.getTitulo(), livro.getAutor()};
            database.update(TABLE_ADICIONAR, contentValues, "ID = ?", new String[]{String.valueOf(livro.getId())});

        //METODO 3
//        String sql = "UPDATE " + TABLE_ADICIONAR + " " +
//                "SET "+
//                COL_ANO+" = ?, "+
//                COL_AUTOR+" = ?, "+
//                COL_EDITORA+" = ?, "+
//                COL_IMAGEM+" = ?, "+
//                COL_LOCALIZACAO+" = ?, "+
//                COL_TITULO+" = ?, "+
//                COL_QT+" = ?, "+
//                COL_QD+" = ? " +
//                "WHERE "+
//                COL_TITULO +"= ? AND "+
//                COL_AUTOR+" = ?";;
//
//        SQLiteStatement statement = database.compileStatement(sql);
//        statement.clearBindings();
//
//        statement.bindString(1, livro.getAno());
//        statement.bindString(2, livro.getAutor());
//        statement.bindString(3,livro.getEditora());
//        statement.bindBlob(4, livro.getImagem());
//        statement.bindString(5, livro.getLocalizacao());
//        statement.bindString(6, livro.getTitulo());
//        statement.bindString(7, String.valueOf(livro.getQuantidadeTotal()));
//        statement.bindString(8, String.valueOf(livro.getQuantidadeDisponivel()));
//        statement.bindString(9, livro.getTitulo());
//        statement.bindString(10, livro.getAutor());
//        statement.execute();
//        database.close();
    }

    public Cursor getBook(String sql){
        SQLiteDatabase database =  getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADICIONAR);
        onCreate(db);
    }

    public void onDeleteBook(String sql){
        SQLiteDatabase database = getWritableDatabase();
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.executeUpdateDelete();

    }
}

