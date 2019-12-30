package com.example.biblioteca;
import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class AdicionarLivro extends AppCompatActivity {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private Button bt_guardar, bt_escolher;
    private EditText adTitulo,adAutor,adEditora,adAno, adQtotal,adQdisponivel,adLocalizacao, adImagem;
    private ImageView imageView;
//    private byte[] adImagem;
    private final int REQUEST_CODE_GALLERY = 999;

    public AdicionarLivro() {
        // Required empty public constructor
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
                startActivity(new Intent(AdicionarLivro.this, Pesquisa.class));
                return true;
            case R.id.menuTwo:
                startActivity(new Intent(AdicionarLivro.this,MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_livro);

        openHelper = new Database(this);

        bt_guardar = findViewById(R.id.guardar);
        bt_escolher = findViewById(R.id.bt_imagem);
        adTitulo = findViewById(R.id.editView_titulo);
        adAutor = findViewById(R.id.editView_autor);
        adEditora = findViewById(R.id.editView_editora);
        adAno = findViewById(R.id.editView_ano);
        adImagem = findViewById(R.id.editView_imagem);
        adQtotal = findViewById(R.id.editView_quantidadet);
        adQdisponivel = findViewById(R.id.editView_quantidaded);
        adLocalizacao = findViewById(R.id.editView_localizacao);
        imageView = findViewById(R.id.imagem_capa_book_adicionar);

        bt_escolher.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        AdicionarLivro.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        bt_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                String fTitulo = adTitulo.getText().toString().trim();
                String fAutor = adAutor.getText().toString().trim();
                String fEditora = adEditora.getText().toString().trim();
                String fAno = adAno.getText().toString().trim();
                byte[] fImagem = imageViewToByte();
                String fQtotal = adQtotal.getText().toString().trim();
                String fQdisponivel = adQdisponivel.getText().toString().trim();
                String fLocalizacao = adLocalizacao.getText().toString().trim();
                String faImagem = adImagem.getText().toString().trim();

                if (fTitulo.isEmpty() || fAutor.isEmpty() ||fEditora.isEmpty() ||fAno.isEmpty() || faImagem.isEmpty() || fQtotal.isEmpty()|| fQdisponivel.isEmpty() || fLocalizacao.isEmpty()) {
                    Toast.makeText(AdicionarLivro.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    insertData(fTitulo,fAutor,fEditora,fAno,fImagem,fQtotal,fQdisponivel,fLocalizacao);
                    Toast.makeText(AdicionarLivro.this, "Livro adicionado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdicionarLivro.this, ListaLivros.class));
                }
            }
        });

    }

    private byte[] imageViewToByte() {
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public void insertData(String fTitulo,String fAutor,String fEditora,String fAno,byte[] fImagem, String fQtotal,String fQdisponivel,String fLocalizacao){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.COL_TITULO,fTitulo);
        contentValues.put(Database.COL_AUTOR,fAutor);
        contentValues.put(Database.COL_EDITORA,fEditora);
        contentValues.put(Database.COL_ANO,fAno);
        contentValues.put(Database.COL_IMAGEM,fImagem);
        contentValues.put(Database.COL_QT,fQtotal);
        contentValues.put(Database.COL_QD,fQdisponivel);
        contentValues.put(Database.COL_LOCALIZACAO,fLocalizacao);

        db.insert(Database.TABLE_ADICIONAR,null,contentValues);
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
                adImagem.setText(uri.getPathSegments().get(5)+".png");
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
