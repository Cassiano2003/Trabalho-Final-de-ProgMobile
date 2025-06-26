package com.example.trabalhofinal.TabelasDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trabalhofinal.Tabelas.Akumas;

import java.util.List;

@Dao
public interface AkumaDao {

    @Query("SELECT * FROM akumas")
    List<Akumas> getALL();

    @Query("SELECT * FROM akumas WHERE idakuma = :id")
    Akumas buscaAkuma(int id);

    @Query("SELECT idakuma FROM akumas WHERE nome = :nomeakuma")
    int buscaAkuma(String nomeakuma);

    @Insert
    void insertAll(Akumas... akumas);
    @Query("SELECT COUNT(*) FROM akumas ")
    int quantosAkumastotal();

    @Query("SELECT COUNT(*) FROM akumas WHERE descricao!='---' ")
    int quantosAkumas();
    @Update
    void upgrade(Akumas akumas);

    @Delete
    void delete(Akumas akumas);
}
