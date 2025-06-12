package com.example.trabalhofinal.TabelasDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.trabalhofinal.Tabelas.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Query("SELECT * FROM usuario")
    List<Usuario> getALL();

    @Query("SELECT EXISTS(SELECT 1 FROM usuario WHERE email = :emai)")
    boolean buscaEmai(String emai);
    @Query("SELECT EXISTS(SELECT 1 FROM usuario WHERE nome = :nome)")
    boolean buscaNome(String nome);

    @Query("SELECT idUser FROM usuario WHERE email = :emai")
    int buscaEmaiID(String emai);
    @Query("SELECT idUser FROM usuario WHERE nome = :nome")
    int buscaNomeID(String nome);

    @Query("SELECT * FROM usuario WHERE idUser = :id")
    Usuario buscaUsuario(int id);

    @Query("SELECT COUNT(*) FROM usuario")
    int quantosUsuarios();
    @Insert
    void insertAll(Usuario... usuarios);

    @Update
    void upgrade(Usuario usuario);

    @Delete
    void delete(Usuario usuario);
}
