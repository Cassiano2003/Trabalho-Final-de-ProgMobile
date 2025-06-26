package com.example.trabalhofinal.Tabelas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

@Entity(tableName = "tripulacoes")
public class Tripulacoes {
    @PrimaryKey(autoGenerate = true)
    public int idtripulacao;

    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name = "capitao")
    public String capitao;

    @ColumnInfo(name = "integrantes")
    public String integrantes;

    @ColumnInfo(name = "foto")
    public String foto;

    public Tripulacoes(String nome, String capitao, String integrantes, String foto) {
        this.nome = nome;
        this.capitao = capitao;
        this.integrantes = integrantes;
        this.foto = foto;
    }


    public int getIdtripulacao() {
        return idtripulacao;
    }

    public void setIdtripulacao(int idtripulacao) {
        this.idtripulacao = idtripulacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCapitao() {
        return capitao;
    }

    public void setCapitao(String capitao) {
        this.capitao = capitao;
    }

    public String getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(String integrantes) {
        this.integrantes = integrantes;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Tripulacoes{" +
                "idtripulacao=" + idtripulacao +
                ", nome='" + nome + '\'' +
                ", capitao='" + capitao + '\'' +
                ", integrantes='" + integrantes + '\'' +
                ", foto=" + foto +
                '}';
    }
}
