package com.example.biblioteca;


import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.biblioteca.Database.COL_AUTOR;
import static com.example.biblioteca.Database.COL_TITULO;
import static com.example.biblioteca.Database.TABLE_ADICIONAR;


public class EditarLivro extends AppCompatActivity {
    Livro livro;
    private final int REQUEST_CODE_GALLERY = 999;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private Button bt_guardar, bt_imagem;
    private EditText adTitulo,adAutor,adEditora,adAno,adImagem, adQtotal,adQdisponivel,adLocalizacao;
    private ImageView imageView;
    private Database dao;
    private String tituloAntigo, autorAntigo;

    public EditarLivro() {
        // Required empty public constructor
        livro = new Livro();
        dao = new Database(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuOne:
                startActivity(new Intent(EditarLivro.this, Pesquisa.class));
                return true;
            case R.id.menuTwo:
                startActivity(new Intent(EditarLivro.this,MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_livro);

        openHelper = new Database(this);

        bt_guardar = findViewById(R.id.guardar);
        bt_imagem = findViewById(R.id.bt_imagem);
        adTitulo = findViewById(R.id.editView_titulo);
        adTitulo.setText(getIntent().getStringExtra("titulo"));
        adAutor = findViewById(R.id.editView_autor);
        adAutor.setText(getIntent().getStringExtra("autor"));
        adEditora = findViewById(R.id.editView_editora);
        adAno = findViewById(R.id.editView_ano);
        adImagem = findViewById(R.id.editView_imagem);
        adQtotal = findViewById(R.id.editView_quantidadet);
        adQdisponivel = findViewById(R.id.editView_quantidaded);
        adLocalizacao = findViewById(R.id.editView_localizacao);
        imageView = findViewById(R.id.imagemView_editar);

        Cursor cursor = dao.getBook("Select * from " + TABLE_ADICIONAR+" where "+COL_TITULO+" = '"+adTitulo.getText().toString()+"' and "+ COL_AUTOR+" = '"+ adAutor.getText().toString()+"'");
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                livro.setId(cursor.getInt(0));
                livro.setTitulo(cursor.getString(1));
                livro.setAutor(cursor.getString(2));
                livro.setEditora(cursor.getString(3));
                livro.setAno(cursor.getString(4));
                livro.setImagem(cursor.getBlob(5));
                livro.setQuantidadeTotal(cursor.getInt(6));
                livro.setQuantidadeDisponivel(cursor.getInt(7));
                livro.setLocalizacao(cursor.getString(8));
                adEditora.setText(livro.getEditora());
                adQtotal.setText(String.valueOf(livro.getQuantidadeTotal()));
                adAno.setText(livro.getAno());
                adTitulo.setText(livro.getTitulo());
                adAutor.setText(livro.getAutor());
                adQdisponivel.setText(String.valueOf(livro.getQuantidadeDisponivel()));
                adLocalizacao.setText(livro.getLocalizacao());
                tituloAntigo = livro.getTitulo();
                autorAntigo = livro.getAutor();
            }while (cursor.moveToNext());
        }

        bt_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db = openHelper.getWritableDatabase();
                livro.setTitulo(adTitulo.getText().toString().trim());
                livro.setAutor(adAutor.getText().toString().trim());
                livro.setEditora(adEditora.getText().toString().trim());
                livro.setAno(adAno.getText().toString().trim());
                String faImagem = adImagem.getText().toString().trim();
                livro.setQuantidadeTotal(Integer.parseInt(adQtotal.getText().toString().trim()));
                livro.setQuantidadeDisponivel(Integer.parseInt(adQdisponivel.getText().toString().trim()));
                livro.setLocalizacao(adLocalizacao.getText().toString().trim());
                if (!faImagem.isEmpty())
                    livro.setImagem(imageViewToByte());
                if (livro.getTitulo().isEmpty() || livro.getAutor().isEmpty() ||livro.getEditora().isEmpty() ||
                        livro.getAno().isEmpty() ||  livro.getQuantidadeTotal() <1||
                        livro.getQuantidadeDisponivel()< 1 || livro.getLocalizacao().isEmpty()) {
                    Toast.makeText(EditarLivro.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    dao.onDeleteBook("Delete from "+ TABLE_ADICIONAR + " where "+COL_AUTOR +" = '"+autorAntigo+"' and "+ COL_TITULO+" = '"+ tituloAntigo+"'");
                    dao.insertDataBase(livro);
                    Toast.makeText(EditarLivro.this, "Livro editado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditarLivro.this, ListaLivros.class));
                }
            }
        });

        bt_imagem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        EditarLivro.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });


    }

    private byte[] imageViewToByte() {
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_GALLERY);

            }else{
                Toast.makeText(getApplicationContext(), "Você não tem permissão para acessar a galeria", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode  == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                adImagem.setText(uri.getPathSegments().get(uri.getPathSegments().size()-1)+".png");
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
