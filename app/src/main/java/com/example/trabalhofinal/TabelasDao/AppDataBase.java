package com.example.trabalhofinal.TabelasDao;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.AtaqueAkumaNoMi;
import com.example.trabalhofinal.Tabelas.Inimigos;
import com.example.trabalhofinal.Tabelas.Jogador;
import com.example.trabalhofinal.Tabelas.Tripulacoes;
import com.example.trabalhofinal.Tabelas.Usuario;


@Database(entities = {Usuario.class, Akumas.class,AtaqueAkumaNoMi.class, Tripulacoes.class, Inimigos.class, Jogador.class},version = 5)//Almentar as vesoes se colocar mais classes
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase INSTANCE;

    public static AppDataBase getDataBase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"OPDataBase").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract UsuarioDao usuarioDao();
    public abstract JogadorDao jogadorDao();
    public abstract InimigosDao inimigosDao();
    public abstract AkumaDao akumaDao();
    public abstract TripulacaoDao tripulacaoDao();
    public abstract AtaqueAkumasDao ataqueAkumasDao();
}