package com.example.trabalhofinal.Tabelas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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

    @ColumnInfo(name = "ataques")
    public int ataques;

    @ColumnInfo(name = "nome_t")
    public String nome_t;

    @ColumnInfo(name = "foto")
    public byte[] fotoakuma;

    public Akumas(String nome, String tipo, String usuarios, String descricao, String nome_t, byte[] fotoakuma) {
        this.nome = nome;
        this.tipo = tipo;
        this.usuarios = usuarios;
        this.descricao = descricao;
        this.nome_t = nome_t;
        this.fotoakuma = fotoakuma;
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

    public byte[] getFotoakuma() {
        return fotoakuma;
    }

    public void setFotoakuma(byte[] fotoakuma) {
        this.fotoakuma = fotoakuma;
    }

}
