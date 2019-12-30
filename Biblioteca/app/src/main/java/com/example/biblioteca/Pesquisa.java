package com.example.biblioteca;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.Menu;



public class Pesquisa extends AppCompatActivity {

    private Button pesquisar;
    private TextView textView_pesquisa, notificacao;
    private EditText editView_p;
    private Database dao;

    public Pesquisa() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisa);
        dao = new Database(this);

        pesquisar = findViewById(R.id.pesquisar);
        textView_pesquisa = findViewById(R.id.textView_pesquisa);
        editView_p = findViewById(R.id.editView_p);
        notificacao = findViewById(R.id.notificacao_pesquisar);


        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Livro livro =  new Livro();
                try {
                    Cursor cursor = dao.getBook("Select * From "+Database.TABLE_ADICIONAR+ " WHERE "+
                            Database.COL_TITULO+" = '"+editView_p.getText().toString()+"' or "+
                            Database.COL_AUTOR+" = '"+editView_p.getText().toString()+"'");
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        livro.setId(cursor.getInt(0));
                        livro.setTitulo(cursor.getString(1));
                        livro.setAutor(cursor.getString(2));
                        livro.setEditora(cursor.getString(3));
                        livro.setAno(cursor.getString(4));
                        livro.setImagem(cursor.getBlob(5));
                        livro.setQuantidadeTotal(cursor.getInt(6));
                        livro.setQuantidadeDisponivel(cursor.getInt(7));
                        livro.setLocalizacao(cursor.getString(8));
                        openDetalhe(livro);
                    }else{
                        editView_p.setText("");
                        notificacao.setText("Livro não encontrado");

                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void openDetalhe(Livro livro){
            Intent detalheLivro = new Intent(this, DetalhesLivro.class);
            detalheLivro.putExtra("titulo", livro.getTitulo().toString());
            detalheLivro.putExtra("autor", livro.getAutor().toString());
            detalheLivro.putExtra("ano", livro.getAno().toString());
            detalheLivro.putExtra("editora", livro.getEditora().toString());
            startActivity(detalheLivro);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuTwo:
                startActivity(new Intent(Pesquisa.this,MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
