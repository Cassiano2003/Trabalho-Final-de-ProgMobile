package com.example.trabalhofinal.Tabelas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;
import java.util.List;

@Entity(tableName = "usuario")
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int idUser;

    @ColumnInfo(name = "nome")
    public String nome;
    @ColumnInfo(name = "senha")
    public String senha;
    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "foto")
    public byte [] foto;
    @ColumnInfo(name = "akuma")
    public List<Integer> akumanomis;

    @ColumnInfo(name = "tripulacao")
    public List<Integer> tripulacao;

    @ColumnInfo(name = "inimigos")
    public List<Integer> inimigos;

    @ColumnInfo(name = "personagens")
    public List<Integer> personagens;


    public Usuario(String nome, String senha, String email, byte[] foto) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.foto = foto;
    }

    public List<Integer> getTripulacao() {
        return tripulacao;
    }

    public void setTripulacao(List<Integer> tripulacao) {
        this.tripulacao = tripulacao;
    }

    public List<Integer> getAkumanomis() {
        return akumanomis;
    }

    public void setAkumanomis(List<Integer> akumanomis) {
        this.akumanomis = akumanomis;
    }

    public List<Integer> getInimigos() {
        return inimigos;
    }

    public void setInimigos(List<Integer> inimigos) {
        this.inimigos = inimigos;
    }

    public List<Integer> getPersonagens() {
        return personagens;
    }

    public void setPersonagens(List<Integer> personagens) {
        this.personagens = personagens;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUser=" + idUser +
                ", nome='" + nome + '\'' +
                ", akumanomis=" + akumanomis +
                ", inimigos=" + inimigos +
                ", personagens=" + personagens +
                '}';
    }
}
