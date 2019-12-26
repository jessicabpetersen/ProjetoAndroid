package com.example.librarysystem;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroHolder> {
    private Context context;
    private List<Livro> livros;

    public LivroAdapter(Context context, List<Livro> livros){
        this.context = context;
        this.livros = livros;
    }

    @NonNull
    @Override
    public LivroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.book_layout, parent, false);
        return new LivroHolder(v);
    }

    public Livro getLivro(int position){
        return livros.get(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull LivroHolder holder, int position) {
        final Livro livro = getLivro(position);
        if(livro != null){
            holder.titulo.setText(livro.getTitulo());
            holder.imagem.setImageIcon(livro.getImagem());
            holder.autor.setText(livro.getAutor());
            holder.ano.setText(livro.getAno());
        }
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }


    class LivroHolder extends RecyclerView.ViewHolder{
        TextView titulo, autor, ano;
        ImageView imagem;
        public LivroHolder(View itemView){
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_book_list);
            autor = itemView.findViewById(R.id.autor_book_list);
            ano = itemView.findViewById(R.id.ano_book_list);
            imagem = itemView.findViewById(R.id.imagem_book_list);
        }
    }
}
