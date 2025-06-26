package com.example.trabalhofinal.TabelasDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.Tripulacoes;

import java.util.List;

@Dao
public interface TripulacaoDao {
    @Query("SELECT * FROM tripulacoes")
    List<Tripulacoes> getALL();

    @Query("SELECT * FROM tripulacoes WHERE idtripulacao = :id")
    Tripulacoes buscaTripulacao(int id);

    @Query("SELECT COUNT(*) FROM tripulacoes")
    int quantosTripulacao();
    @Insert
    void insertAll(Tripulacoes... tripulacoes);

    @Update
    void upgrade(Tripulacoes tripulacoes);

    @Delete
    void delete(Tripulacoes tripulacoes);
}
