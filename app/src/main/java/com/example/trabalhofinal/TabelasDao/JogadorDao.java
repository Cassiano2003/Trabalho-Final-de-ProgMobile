package com.example.trabalhofinal.TabelasDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trabalhofinal.Tabelas.Jogador;

import java.util.List;

@Dao
public interface JogadorDao {

    @Query("SELECT * FROM jogador")
    List<Jogador> getALL();

    @Query("SELECT * FROM Jogador WHERE idpersonagens = :id")
    Jogador buscaPer(int id);


    @Query("SELECT idpersonagens FROM Jogador WHERE nome = :nome")
    int buscaID(String nome);

    @Query("SELECT EXISTS(SELECT 1 FROM jogador WHERE nome = :nome)")
    boolean checaNome(String nome);

    @Insert
    void insertAll(Jogador... personagens);

    @Update
    void upgrade(Jogador jogador);

    @Delete
    void delete(Jogador jogador);
}
