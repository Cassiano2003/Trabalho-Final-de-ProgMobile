package com.example.trabalhofinal.TabelasDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.trabalhofinal.Tabelas.Inimigos;

import java.util.List;

@Dao
public interface InimigosDao {
    @Query("SELECT * FROM inimigos")
    List<Inimigos> getALL();
    @Insert
    void insertAll(Inimigos... inimigos);

    @Update
    void upgrade(Inimigos inimigos);

    @Delete
    void delete(Inimigos inimigos);
}
