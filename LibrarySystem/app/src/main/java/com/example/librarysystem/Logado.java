package com.example.librarysystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Logado extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Livro> listaLivros;
    private LivroAdapter adapter;
    private FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logado);

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        onLoadBooks();
    }

    private void onLoadBooks(){

    }
}
