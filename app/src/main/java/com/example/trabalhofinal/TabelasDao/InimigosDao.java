package com.example.trabalhofinal.TabelasDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.Inimigos;

import java.util.List;

@Dao
public interface InimigosDao {
    @Query("SELECT * FROM inimigos")
    List<Inimigos> getALL();
    @Insert
    void insertAll(Inimigos... inimigos);

    @Query("SELECT COUNT(*) FROM inimigos")
    int quantosInimigos();

    @Query("SELECT * FROM inimigos WHERE idinimigos = :id")
    Inimigos buscaInimigos(int id);

    @Query("SELECT fotoperfio FROM inimigos WHERE nome = :nome")
    String buscaInimigos(String nome);

    @Query("SELECT * FROM inimigos WHERE estagio = :estagio AND vezesbatalha != 0")
    List<Inimigos> geraInimigosPorEstagios(int estagio);

    @Query("UPDATE Inimigos SET vezesbatalha = CASE WHEN vezesbatalha > 0 THEN vezesbatalha - 1 ELSE 0 END WHERE idinimigos = :id")
    void reduzirVez(int id);

    @Update
    void upgrade(Inimigos inimigos);

    @Delete
    void delete(Inimigos inimigos);
}
