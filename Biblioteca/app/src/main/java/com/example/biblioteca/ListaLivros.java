package com.example.biblioteca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.biblioteca.Database.COL_TITULO;
import static com.example.biblioteca.Database.TABLE_ADICIONAR;

public class ListaLivros extends AppCompatActivity implements BookEventListener {

    private RecyclerView recyclerView;
    private ArrayList<Livro> lista;
    private LivroAdapter adapter;
    private Database dao;
    private FloatingActionButton floatingActionButton;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuOne:
                startActivity(new Intent(ListaLivros.this, Pesquisa.class));
                return true;
            case R.id.menuTwo:
                startActivity(new Intent(ListaLivros.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_livros);
        dao = new Database(this);

        recyclerView = findViewById(R.id.lista_livros);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingactionbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddBook();
            }
        });
    }

    private void onAddBook() {
        startActivity(new Intent(ListaLivros.this,AdicionarLivro.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        onLoadBooks();
    }

    private void onLoadBooks(){

        this.lista = new ArrayList<>();
        try {
            Cursor cursor = dao.getBook("Select * from " + TABLE_ADICIONAR+" order by +"+COL_TITULO + " asc");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Livro livro = new Livro();
                    livro.setId(cursor.getInt(0));
                    livro.setTitulo(cursor.getString(1));
                    livro.setAutor(cursor.getString(2));
                    livro.setEditora(cursor.getString(3));
                    livro.setAno(cursor.getString(4));
                    livro.setImagem(cursor.getBlob(5));
                    livro.setQuantidadeTotal(cursor.getInt(6));
                    livro.setQuantidadeDisponivel(cursor.getInt(7));
                    livro.setLocalizacao(cursor.getString(8));
                    this.lista.add(livro);
                } while (cursor.moveToNext());
            }
        }catch (NullPointerException e){

        }
        this.adapter = new LivroAdapter(this, this.lista);
        this.adapter.setListener(this);
        this.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBookClick(Livro livro) {
        Intent detalheLivro = new Intent(this, DetalhesLivro.class);
        detalheLivro.putExtra("titulo", livro.getTitulo().toString());
        detalheLivro.putExtra("autor", livro.getAutor().toString());
        detalheLivro.putExtra("ano", livro.getAno().toString());
        detalheLivro.putExtra("editora", livro.getEditora().toString());
        startActivity(detalheLivro);
    }

}
