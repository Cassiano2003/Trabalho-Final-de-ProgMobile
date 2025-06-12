package com.example.trabalhofinal.TabelasDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.Personagens;

import java.util.List;

@Dao
public interface PersonagensDao {

    @Query("SELECT * FROM personagens")
    List<Personagens> getALL();

    @Query("SELECT * FROM personagens WHERE idpersonagens = :id")
    Personagens buscaPer(int id);


    @Query("SELECT idpersonagens FROM personagens WHERE nome = :nome")
    int buscaID(String nome);

    @Insert
    void insertAll(Personagens... personagens);

    @Update
    void upgrade(Personagens personagens);

    @Delete
    void delete(Personagens personagens);
}
