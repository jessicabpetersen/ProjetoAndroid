package com.example.projeto;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "livro")
public class Livro {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "titulo")
    private String titulo;
    @ColumnInfo(name = "autor")
    private String autor;
    @ColumnInfo(name = "ano")
    private String ano;
    @ColumnInfo(name = "edicao")
    private int edicao;
    @ColumnInfo(name = "quantidade")
    private int quantidade;

    @Ignore
    public Livro(){}

    public Livro(String titulo, String autor, String ano, int edicao, int quantidade){
        this.titulo = titulo;
        this.ano = ano;
        this.autor = autor;
        this.edicao = edicao;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
