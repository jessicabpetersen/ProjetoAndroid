package com.example.librarysystem;

import android.graphics.drawable.Icon;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "livros")
public class Livro {

    @PrimaryKey(autoGenerate =  false)
    private String titulo;
    @PrimaryKey(autoGenerate = false)
    private String autor;

    @ColumnInfo(name = "editora")
    private String editora;

    @ColumnInfo(name = "ano")
    private String ano;

    @ColumnInfo(name = "localizacao")
    private String localizacao;

    @ColumnInfo(name = "quantidadeTotal")
    private int quantidadeTotal;

    @ColumnInfo(name = "quantidadeDisponivel")
    private int quantidadeDisponivel;

    @ColumnInfo(name = "imagem")
    private Icon imagem;

    @Ignore
    private boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Icon getImagem() {
        return imagem;
    }

    public void setImagem(Icon imagem) {
        this.imagem = imagem;
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

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(int quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
}
