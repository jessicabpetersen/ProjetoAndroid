package com.example.projeto;

import android.content.Context;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LivrosAdapter extends RecyclerView.Adapter<LivrosAdapter.LivroHolder> {
    private Context context;
    private ArrayList<Livro> lista_livros;
    private LivroEventListener listener;
    private boolean multiCheckMode = false;

    public LivrosAdapter(Context context, ArrayList<Livro> lista){
        this.context = context;
        this.lista_livros = lista;
    }

    @NonNull
    @Override
    public LivroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview,parent, false);
        return new LivroHolder(v);
    }
    private Livro getLivro(int position){
        return lista_livros.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull LivroHolder holder, int position) {
        final Livro livro = getLivro(position);
        if(livro != null){
            holder.livroTitulo.setText(livro.getTitulo());
            holder.livroAutor.setText(livro.getAutor());
            holder.livroAno.setText(livro.getAno());
            holder.livroEdicao.setText(livro.getEdicao());
            holder.livroQuantidade.setText(livro.getQuantidade());
            holder.livroId.setText(livro.getId());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onLivroClick(livro);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLivroLongClick(livro);
                    return false;
                }
            });

            if (multiCheckMode) {
                holder.checkBox.setVisibility(View.VISIBLE); // show checkBox if multiMode on
                holder.checkBox.setChecked(livro.isChecked());
            } else holder.checkBox.setVisibility(View.GONE);
        }
    }

    public List<Livro> getCheckedLivros(){
        List<Livro> checkedLivros = new ArrayList<>();
        for(Livro livro : this.lista_livros){
            if(livro.isChecked())
                checkedLivros.add(livro);
        }
        return checkedLivros;
    }
    @Override
    public int getItemCount() {
        return lista_livros.size();
    }

    class LivroHolder extends RecyclerView.ViewHolder{
        TextView livroTitulo, livroAutor, livroQuantidade, livroEdicao, livroAno, livroId;
        CheckBox checkBox;

        public LivroHolder(View itemView){
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            livroTitulo = itemView.findViewById(R.id.book_title);
        }
    }

    public void setListener(LivroEventListener listener) {
        this.listener = listener;
    }

    public void setMultiCheckMode(boolean multiCheckMode) {
        this.multiCheckMode = multiCheckMode;
        if (!multiCheckMode)
            for (Livro livro: this.lista_livros) {
                livro.setChecked(false);
            }
        notifyDataSetChanged();
    }

}
