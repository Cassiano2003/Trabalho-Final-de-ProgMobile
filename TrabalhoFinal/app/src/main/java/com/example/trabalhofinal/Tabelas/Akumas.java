package com.example.trabalhofinal.Tabelas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

@Entity(tableName = "akumas")
public class Akumas {
    @PrimaryKey(autoGenerate = true)
    public int idakuma;

    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name = "tipo")
    public String tipo;

    @ColumnInfo(name = "usuarios")
    public String usuarios;

    @ColumnInfo(name = "descricao")
    public String descricao;

    @ColumnInfo(name = "idataques")
    public int idataques;

    @ColumnInfo(name = "nome_t")
    public String nome_t;

    @ColumnInfo(name = "foto")
    public String fotoakuma;

    public Akumas(String nome, String tipo, String usuarios, String descricao, String nome_t, String fotoakuma) {
        this.nome = nome;
        this.tipo = tipo;
        this.usuarios = usuarios;
        this.descricao = descricao;
        this.nome_t = nome_t;
        this.fotoakuma = fotoakuma;
    }

    public int getIdataques() {
        return idataques;
    }

    public void setIdataques(int idataques) {
        this.idataques = idataques;
    }

    @Ignore
    public Akumas() {
    }

    @Ignore
    public Akumas(String nome, String tipo, String usuarios, String descricao, String nome_t) {
        this.nome = nome;
        this.tipo = tipo;
        this.usuarios = usuarios;
        this.descricao = descricao;
        this.nome_t = nome_t;
    }

    public int getIdakuma() {
        return idakuma;
    }

    public void setIdakuma(int idakuma) {
        this.idakuma = idakuma;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(String usuarios) {
        this.usuarios = usuarios;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome_t() {
        return nome_t;
    }

    public void setNome_t(String nome_t) {
        this.nome_t = nome_t;
    }

    public String getFotoakuma() {
        return fotoakuma;
    }

    public void setFotoakuma(String fotoakuma) {
        this.fotoakuma = fotoakuma;
    }

}
