package com.example.trabalhofinal.TabelasDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trabalhofinal.Tabelas.AtaqueAkumaNoMi;

import java.util.List;

@Dao
public interface AtaqueAkumasDao {
    @Query("SELECT * FROM ataquesakuma")
    List<AtaqueAkumaNoMi> getALL();
    @Query("SELECT * FROM ataquesakuma WHERE idataque = :id")
    AtaqueAkumaNoMi buscaAtaqueAkumaNoMi(int id);

    @Insert
    void insertAll(AtaqueAkumaNoMi... ataqueAkumaNoMi);
    @Query("SELECT COUNT(*) FROM ataquesakuma")
    int quantosAtaquesAkumaNoMi();

    @Update
    void upgrade(AtaqueAkumaNoMi ataqueAkumaNoMi);
    @Delete
    void delete(AtaqueAkumaNoMi ataqueAkumaNoMi);
}
