package com.example.biblioteca;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.Collections;
import java.util.Comparator;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivroHolder> {
    private Context context;
    private ArrayList<Livro> livros;
    private BookEventListener listener;

    public BookEventListener getListener() {
        return listener;
    }

    public void setListener(BookEventListener listener) {
        this.listener = listener;
    }

    public LivroAdapter(Context context, ArrayList<Livro> livros){
        this.context = context;
        this.livros = livros;
        if(this.livros.size() > 0){
            Collections.sort(this.livros, new Comparator<Livro>() {
                @Override
                public int compare(Livro o1, Livro o2) {
                    return o1.getTitulo().compareTo(o2.getTitulo());
                }
            });
        }
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
            holder.autor.setText(livro.getAutor());
            holder.editora.setText(livro.getEditora());
            holder.ano.setText(livro.getAno());
            byte[] imagem = livro.getImagem();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);

            holder.imagem.setImageBitmap(bitmap);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onBookClick(livro);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }


    class LivroHolder extends RecyclerView.ViewHolder{
        TextView titulo, autor, editora, ano;
        ImageView imagem;
        public LivroHolder(View itemView){
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_book_list);
            autor = itemView.findViewById(R.id.autor_book_list);
            editora = itemView.findViewById(R.id.editora_book_list);
            ano = itemView.findViewById(R.id.ano_book_list);
            imagem = itemView.findViewById(R.id.imagem_book_list);
        }
    }
}
