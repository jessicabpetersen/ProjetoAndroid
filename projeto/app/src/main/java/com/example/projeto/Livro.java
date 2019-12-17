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
    @ColumnInfo(name = "quantidadeTotal")
    private int quantidadeTotal;
    @ColumnInfo(name = "quantidadeDisponivel")
    private int quantidadeDisponivel;
    @ColumnInfo(name = "localizacao")
    private String localizacao;

    @Ignore
    private boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Ignore
    public Livro(){}

    public Livro(String titulo, String autor, String ano, int edicao, int quantidadeDisponivel, int quantidadeTotal, String localizacao){
        this.titulo = titulo;
        this.ano = ano;
        this.autor = autor;
        this.edicao = edicao;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.quantidadeTotal = quantidadeTotal;
        this.localizacao = localizacao;
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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
