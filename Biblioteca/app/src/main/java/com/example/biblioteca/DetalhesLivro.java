package com.example.biblioteca;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.biblioteca.Database.COL_AUTOR;
import static com.example.biblioteca.Database.COL_TITULO;
import static com.example.biblioteca.Database.TABLE_ADICIONAR;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalhesLivro extends AppCompatActivity {
    private Livro livro;
    private TextView titulo, autor, editora,ano,qntdTotal, qntdDisponivel,localizacao;
    private ImageView imagem;
    private Database dao;
    private Button btEditar, btExcluir;


    public DetalhesLivro() {
        // Required empty public constructor
        livro = new Livro();
        dao = new Database(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_livro);


        titulo = findViewById(R.id.textView_titulo);
        titulo.setText(getIntent().getStringExtra("titulo"));
        autor = findViewById(R.id.textView_nome_autor);
        autor.setText(getIntent().getStringExtra("autor"));
        editora = findViewById(R.id.textView_nome_editora);
        editora.setText(getIntent().getStringExtra("editora"));
        ano = findViewById(R.id.textView_nome_ano);
        ano.setText(getIntent().getStringExtra("ano"));
        qntdTotal = findViewById(R.id.detalhe_quantidade_total);

        qntdDisponivel = findViewById(R.id.detalhe_quantidade_disponivel);
        localizacao = findViewById(R.id.detalhe_localizacao);
        imagem = findViewById(R.id.img_livro);
        btEditar = findViewById(R.id.detalhe_editar);
        btEditar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onBtnEditar();
            }
        });
        btExcluir = findViewById(R.id.eliminar);
        btExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnExcluir();
            }
        });

        Cursor cursor = dao.getBook("Select * from " + TABLE_ADICIONAR+" where "+COL_TITULO+" = '"+titulo.getText().toString()+"' and "+ COL_AUTOR+" = '"+ autor.getText().toString()+"'");
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
                qntdDisponivel.setText(String.valueOf(livro.getQuantidadeDisponivel()));
                qntdTotal.setText(String.valueOf(livro.getQuantidadeTotal()));
                localizacao.setText(livro.getLocalizacao());
                byte[] imagema = livro.getImagem();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagema, 0, imagema.length);
                imagem.setImageBitmap(bitmap);

            }while (cursor.moveToNext());
        }
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
                startActivity(new Intent(DetalhesLivro.this, Pesquisa.class));
                return true;
            case R.id.menuTwo:
                startActivity(new Intent(DetalhesLivro.this,MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBtnEditar(){
        Intent editarLivro = new Intent(this, EditarLivro.class);
        editarLivro.putExtra("titulo", livro.getTitulo());
        editarLivro.putExtra("autor", livro.getAutor());
        startActivity(editarLivro);

    }

    public void onBtnExcluir(){
        dao.onDeleteBook("Delete from "+ TABLE_ADICIONAR + " where "+COL_AUTOR +" = '"+livro.getAutor()+"' and "+ COL_TITULO+" = '"+ livro.getTitulo()+"'");
        Intent intent = new Intent(this, ListaLivros.class);
        startActivity(intent);
    }
}
