package com.example.trabalhofinal;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.AtaqueAkumaNoMi;
import com.example.trabalhofinal.Tabelas.Inimigos;
import com.example.trabalhofinal.Tabelas.Tripulacoes;
import com.example.trabalhofinal.TabelasDao.AppDataBase;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.trabalhofinal.databinding.ActivityMainBinding;

import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private AppDataBase db;

    public MediaPlayer mediaPlayer;

    public int musicaPausada = 0;
    public boolean musicaPrincipal = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = AppDataBase.getDataBase(this);
        CriaBanco();
        tocarMusicaAleatoria();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void CriaBanco(){
        //Ataques Akuma No MI
        if(db.ataqueAkumasDao().quantosAtaquesAkumaNoMi() == 0){
            try {
                AssetManager assetManager = getAssets();
                InputStream is = assetManager.open("ataquesakuma.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String conteudo = new String(buffer, "UTF-8");
                JSONObject json = new JSONObject(conteudo);


                JSONArray NOME_DO_ATAQUE = json.getJSONArray("NOME DO ATAQUE");
                JSONArray DESCRICAO = json.getJSONArray("DESCRIÇÃO");
                JSONArray TIPO_DE_ATAQUE = json.getJSONArray("TIPO DE ATAQUE");
                JSONArray CUSTO = json.getJSONArray("CUSTO");
                JSONArray HP_JOGADOR = json.getJSONArray("HP JOGADOR");
                JSONArray FORCA_JOGADOR= json.getJSONArray("FORÇA JOGADOR");
                JSONArray ESTAMINA_JOGADOR = json.getJSONArray("ESTAMINA JOGADOR");
                JSONArray AGILIDADE_JOGADOR = json.getJSONArray("AGILIDADE JOGADOR");
                JSONArray DEFESA_JOGADOR = json.getJSONArray("DEFESA JOGADOR");
                JSONArray INTUICAO_JOGADOR = json.getJSONArray("INTUIÇÃO JOGADOR");
                JSONArray DANO_JOGADOR = json.getJSONArray("DANO JOGADOR");
                JSONArray HP_INIMIGO = json.getJSONArray("HP INIMIGO");
                JSONArray FORCA_INIMIGO = json.getJSONArray("FORÇA INIMIGO");
                JSONArray ESTAMINA_INIMIGO = json.getJSONArray("ESTAMINA INIMIGO");
                JSONArray AGILIDADE_INIMIGO = json.getJSONArray("AGILIDADE INIMIGO");
                JSONArray DEFESA_INIMIGO = json.getJSONArray("DEFESA INIMIGO");
                JSONArray INTUICAO_INIMIGO = json.getJSONArray("INTUIÇÃO INIMIGO");
                //JSONArray DANO_INIMIGO = json.getJSONArray("DANO INIMIGO");


                int[] quantosAtaques = {5,3,3,5,4,3,5,4,5,3,4,5,4,4,4,4,5,3,3,3,4,4,2,1,3,4,3,4,3,1,5,4,4,1,1,2,5,5,2,2,5,3,4,1,4,3,2,2,3,4,2,1,4,1,3,1,4,5,5,5,5,2,4,4,2,2,5,5,2,2,1,2,3,2,4,3,3,1,2,1,4,4,1,1,3,4,3,1,3};

                int iten = 0;
                for(int i=0; i < quantosAtaques.length;i++){
                    int qntAtaques = quantosAtaques[i];
                    String[] nome_do_ataque = new String[qntAtaques];
                    String[] descricao = new String[qntAtaques];
                    String[] tipo_de_ataque = new String[qntAtaques];

                    int[] custo = new int[qntAtaques];
                    int[] hp_jogador = new int[qntAtaques];
                    int[] forca_jogador = new int[qntAtaques];
                    int[] estamina_jogador = new int[qntAtaques];
                    int[] agilidade_jogador = new int[qntAtaques];
                    int[] defesa_jogador = new int[qntAtaques];
                    int[] intuicao_jogador = new int[qntAtaques];
                    int[] dano_jogador = new int[qntAtaques];

                    int[] hp_inimigo = new int[qntAtaques];
                    int[] forca_inimigo = new int[qntAtaques];
                    int[] estamina_inimigo = new int[qntAtaques];
                    int[] agilidade_inimigo = new int[qntAtaques];
                    int[] defesa_inimigo = new int[qntAtaques];
                    int[] intuicao_inimigo = new int[qntAtaques];
                    int[] dano_inimigo = new int[qntAtaques];

                    for(int posicao=0;posicao < qntAtaques;posicao++){
                        Log.d("j",String.valueOf(posicao));
                        nome_do_ataque[posicao] = NOME_DO_ATAQUE.getString(iten).trim();
                        descricao[posicao] = DESCRICAO.getString(iten).trim();
                        tipo_de_ataque[posicao] = TIPO_DE_ATAQUE.getString(iten).trim();

                        custo[posicao] = CUSTO.getInt(iten);
                        hp_jogador[posicao] = HP_JOGADOR.getInt(iten);
                        forca_jogador[posicao] = FORCA_JOGADOR.getInt(iten);
                        estamina_jogador[posicao] = ESTAMINA_JOGADOR.getInt(iten);
                        agilidade_jogador[posicao] = AGILIDADE_JOGADOR.getInt(iten);
                        defesa_jogador[posicao] = DEFESA_JOGADOR.getInt(iten);
                        intuicao_jogador[posicao] = INTUICAO_JOGADOR.getInt(iten);
                        dano_jogador[posicao] = DANO_JOGADOR.getInt(iten);

                        hp_inimigo[posicao] = HP_INIMIGO.getInt(iten);
                        forca_inimigo[posicao] = FORCA_INIMIGO.getInt(iten);
                        estamina_inimigo[posicao] = ESTAMINA_INIMIGO.getInt(iten);
                        agilidade_inimigo[posicao] = AGILIDADE_INIMIGO.getInt(iten);
                        defesa_inimigo[posicao] = DEFESA_INIMIGO.getInt(iten);
                        intuicao_inimigo[posicao] = INTUICAO_INIMIGO.getInt(iten);
                        dano_inimigo[posicao] = 0;
                        iten++;
                    }
                    AtaqueAkumaNoMi ataqueAkumaNoMi = new AtaqueAkumaNoMi(qntAtaques,nome_do_ataque,descricao,tipo_de_ataque,custo,hp_jogador,forca_jogador,estamina_jogador,agilidade_jogador,defesa_jogador,intuicao_jogador,
                            dano_jogador,hp_inimigo,forca_inimigo,estamina_inimigo,agilidade_inimigo,defesa_inimigo,intuicao_inimigo,dano_inimigo);
                    db.ataqueAkumasDao().insertAll(ataqueAkumaNoMi);
                }
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //Cacteristicas AKUMAS NO MI
        if (db.akumaDao().quantosAkumas() == 0) {
            try {
                AssetManager assetManager = getAssets();
                InputStream is = assetManager.open("akumas.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String conteudo = new String(buffer, "UTF-8");
                JSONObject json = new JSONObject(conteudo);

                JSONArray NOME = json.getJSONArray("NOME");
                JSONArray TIPO = json.getJSONArray("TIPO");
                JSONArray USUÁRIOS = json.getJSONArray("USUÁRIOS");
                JSONArray DESCRIÇÃO = json.getJSONArray("DESCRIÇÃO");
                JSONArray NOME_TRADUZIDO = json.getJSONArray("NOME TRADUZIDO");
                JSONArray FOTO = json.getJSONArray("FOTO");

                List<AtaqueAkumaNoMi> ataqueAkumaNoMiList = db.ataqueAkumasDao().getALL();

                int qnt = NOME.length();
                for (int i = 0; i < qnt; i++) {
                    Akumas akumas = new Akumas(
                            NOME.getString(i).trim(),
                            TIPO.getString(i).trim(),
                            USUÁRIOS.getString(i).trim(),
                            DESCRIÇÃO.getString(i).trim(),
                            NOME_TRADUZIDO.getString(i).trim(),
                            FOTO.getString(i).trim()
                    );
                    akumas.setIdataques(i + 1);
                    db.akumaDao().insertAll(akumas);
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //Caracteristicas TRIPULACAO
        if (db.tripulacaoDao().quantosTripulacao() == 0) {
            try {
                AssetManager assetManager = getAssets();
                InputStream is = assetManager.open("tripulacao.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String conteudo = new String(buffer, "UTF-8");
                JSONObject json = new JSONObject(conteudo);

                JSONArray NOME = json.getJSONArray("NOME");
                JSONArray CAPITÃO = json.getJSONArray("CAPITÃO");
                JSONArray INTEGRANTES = json.getJSONArray("INTEGRANTES");
                JSONArray BANDEIRA = json.getJSONArray("BANDEIRA");

                int qnt = NOME.length();
                for (int i = 0; i < qnt; i++) {
                    Tripulacoes tripulacoes = new Tripulacoes(
                            NOME.getString(i).trim(),
                            CAPITÃO.getString(i).trim(),
                            INTEGRANTES.getString(i).trim(),
                            BANDEIRA.getString(i).trim()
                    );
                    db.tripulacaoDao().insertAll(tripulacoes);
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        if (db.inimigosDao().quantosInimigos() == 0) {
            //Caracteristicas dos Inimigos
            try {
                AssetManager assetManager = getAssets();
                InputStream is = assetManager.open("inimigos.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String conteudo = new String(buffer, "UTF-8");
                JSONObject json = new JSONObject(conteudo);

                JSONArray NOMES = json.getJSONArray("NOMES");
                JSONArray APELIDO_NOME_POPULAR = json.getJSONArray("APELIDO/NOME POPULAR");
                JSONArray ARMAS = json.getJSONArray("ARMAS");
                JSONArray HP = json.getJSONArray("HP");
                JSONArray FORÇA = json.getJSONArray("FORÇA");
                JSONArray ESTAMINA = json.getJSONArray("ESTAMINA");
                JSONArray AGILIDADE = json.getJSONArray("AGILIDADE");
                JSONArray DEFESA = json.getJSONArray("DEFESA");
                JSONArray INTUIÇÃO = json.getJSONArray("INTUIÇÃO");
                JSONArray AKUMA_NO_MI = json.getJSONArray("AKUMA NO MI");
                JSONArray ASSOCIAÇÃO = json.getJSONArray("ASSOCIAÇÃO");
                JSONArray TRIPULAÇÃO_ORGANIZAÇÃO = json.getJSONArray("TRIPULAÇÃO/ORGANIZAÇÃO");
                JSONArray RECOMPENSA = json.getJSONArray("RECOMPENSA");
                JSONArray TÍTULO = json.getJSONArray("TÍTULO");
                JSONArray ORIGEM = json.getJSONArray("ORIGEM");
                JSONArray SEXO = json.getJSONArray("SEXO");
                JSONArray RAÇA = json.getJSONArray("RAÇA");
                JSONArray FOTO_PERFIO = json.getJSONArray("FOTO PERFIO");
                JSONArray FOTO_COMBATE = json.getJSONArray("FOTO COMBATE");
                JSONArray FOTO_CATALAGO = json.getJSONArray("FOTO CATALAGO");


                int energia = 5;
                int[] estagios = {
                        5, 4, 4, 4, 4, 4, 4, 4, 4, 4,
                        1, 3, 3, 5, 1, 1, 1, 1, 1, 1,
                        1, 1, 4, 1, 1, 5, 3, 3, 1, 1,
                        1, 1, 2, 4, 2, 2, 2, 5, 5, 5,
                        5, 5, 1, 1, 1, 1, 5, 2, 4, 4,
                        2, 2, 2, 2, 2, 1, 2, 2, 2, 2,
                        2, 4, 5, 4, 5, 5, 3, 3, 3, 3,
                        4, 3, 3, 3, 4, 3, 3, 2, 2, 2,
                        5, 5, 5, 5, 5, 5, 4, 4, 4, 5,
                        4, 4, 1, 1, 1, 1, 1, 1, 1, 1,
                        3, 3, 3, 3, 3, 3, 3, 5, 3, 3,
                        3, 3, 4, 4, 3, 3, 3, 3, 3, 3,
                        5, 3, 3, 4, 4, 3, 4, 4, 4, 4,
                        4, 4, 4, 4, 4, 4, 4, 5, 4, 4,
                        4, 5, 4, 4, 4, 4, 5, 4, 4, 5,
                        3,2,4
                };

                String[] tipo = {
                        "P", "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                        "M", "M",
                        "P", "P", "P",
                        "M",
                        "N", "N",
                        "M",
                        "P", "P", "P", "P", "P",
                        "M", "M", "M",
                        "P", "P", "P",
                        "N",
                        "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                        "N",
                        "P", "P", "P", "P",
                        "N",
                        "M", "M", "M", "M", "M", "M", "M", "M",
                        "P", "P", "P", "P", "P",
                        "R",
                        "M", "M",
                        "P", "P", "P", "P", "P", "P", "P", "P", "P",
                        "M",
                        "P", "P", "P", "P",
                        "R",
                        "M",
                        "P", "P", "P", "P", "P", "P", "P", "P",
                        "M", "M",
                        "P",
                        "R",
                        "P", "P", "P", "P", "P",
                        "N",
                        "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                        "N",
                        "P", "P", "P",
                        "N", "N",
                        "P",
                        "M",
                        "P", "P", "P", "P",
                        "M",
                        "N", "N", "N", "N", "N",
                        "P",
                        "N",
                        "P", "P", "P", "P",
                        "N", "N", "N", "N", "N",
                        "P", "P", "P",
                        "N",
                        "P",
                        "N", "N", "N", "N",
                        "M",
                        "P",
                        "N",
                        "M", "M","P", "P"
                };

                int hakiobs = 0;
                int hakiarm = 0;

                int qnt = NOMES.length();
                for (int i = 0; i < qnt; i++) {
                    Inimigos inimigos = new Inimigos(NOMES.getString(i).trim(),APELIDO_NOME_POPULAR.getString(i).trim(),ARMAS.getString(i).trim(),HP.getInt(i),FORÇA.getInt(i),ESTAMINA.getInt(i),AGILIDADE.getInt(i)
                    ,DEFESA.getInt(i),INTUIÇÃO.getInt(i),energia,AKUMA_NO_MI.getString(i).trim(),ASSOCIAÇÃO.getString(i).trim(),TRIPULAÇÃO_ORGANIZAÇÃO.getString(i).trim(),RECOMPENSA.getString(i).trim()
                    ,estagios[i],tipo[i],TÍTULO.getString(i).trim(),ORIGEM.getString(i).trim(),SEXO.getString(i).trim(),RAÇA.getString(i).trim(),hakiobs,hakiarm,FOTO_PERFIO.getString(i).trim(),FOTO_CATALAGO.getString(i).trim(),FOTO_COMBATE.getString(i).trim());
                    if (!AKUMA_NO_MI.getString(i).trim().equals("NÃO TEM") && !AKUMA_NO_MI.getString(i).trim().equals("Desconhecida")) {
                        inimigos.setIdakuma(db.akumaDao().buscaAkuma(AKUMA_NO_MI.getString(i).trim()));
                    }
                    db.inimigosDao().insertAll(inimigos);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void tocarMusicaAleatoria(){
        int escolhe_musica = new Random().nextInt(10);
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        switch (escolhe_musica){
            case 0:
                mediaPlayer = MediaPlayer.create(this,R.raw.fan_letter);
                mediaPlayer.start();
                break;
            case 1:
                mediaPlayer = MediaPlayer.create(this,R.raw.grand_line1);
                mediaPlayer.start();
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(this,R.raw.more_adventure);
                mediaPlayer.start();
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(this,R.raw.pace1);
                mediaPlayer.start();
                break;
            case 4:
                mediaPlayer = MediaPlayer.create(this,R.raw.pace2);
                mediaPlayer.start();
                break;
            case 5:
                mediaPlayer = MediaPlayer.create(this,R.raw.pace3);
                mediaPlayer.start();
                break;
            case 6:
                mediaPlayer = MediaPlayer.create(this,R.raw.town1);
                mediaPlayer.start();
                break;
            case 7:
                mediaPlayer = MediaPlayer.create(this,R.raw.town2);
                mediaPlayer.start();
                break;
            case 8:
                mediaPlayer = MediaPlayer.create(this,R.raw.town3);
                mediaPlayer.start();
                break;
            case 9:
                mediaPlayer = MediaPlayer.create(this,R.raw.wano);
                mediaPlayer.start();
                break;
        }
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(mp -> tocarMusicaAleatoria()); // chama recursivamente
            mediaPlayer.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            musicaPausada = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(musicaPausada != 0){
            mediaPlayer.seekTo(musicaPausada);
            mediaPlayer.start();
        }
    }
}