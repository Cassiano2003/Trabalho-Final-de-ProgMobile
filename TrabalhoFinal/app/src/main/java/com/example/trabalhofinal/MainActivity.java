package com.example.trabalhofinal;

import android.content.res.AssetManager;
import android.os.Bundle;

import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.Inimigos;
import com.example.trabalhofinal.Tabelas.Tripulacoes;
import com.example.trabalhofinal.TabelasDao.AppDataBase;


import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.trabalhofinal.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private AppDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = AppDataBase.getDataBase(this);

        //Cacteristicas AKUMAS NO MI
        if (db.akumaDao().quantosAkumas() == 0) {
            try {
                InputStream is = this.getAssets().open("akumas.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, StandardCharsets.UTF_8);

                // Parsear JSON com Gson
                Gson gson = new Gson();
                Type akumasListType = new TypeToken<List<Akumas>>(){}.getType();
                List<Akumas> akumasList = gson.fromJson(json, akumasListType);

                // Criar arrays
                int sizeList = akumasList.size();
                String[] nomes_akumas = new String[sizeList];
                String[] tipo_akumas = new String[sizeList];
                String[] usuarios_akumas = new String[sizeList];
                String[] descricao_akumas = new String[sizeList];
                String[] nome_traduzido_akumas = new String[sizeList];
                String[] fotos_akumas = new String[sizeList];

                for (int i = 0; i < sizeList; i++) {
                    Akumas akuma = akumasList.get(i);
                    nomes_akumas[i] = akuma.getNome().replace("\n", "");
                    tipo_akumas[i] = akuma.getTipo();
                    usuarios_akumas[i] = akuma.getUsuarios();
                    descricao_akumas[i] = akuma.getDescricao();
                    nome_traduzido_akumas[i] = akuma.getNome_t().replace("\n", "");
                    fotos_akumas[i] = akuma.getFotoakuma();
                }

                int qnt = nomes_akumas.length;
                for (int i = 0; i < qnt; i++) {
                    Akumas akumas = new Akumas(nomes_akumas[i], tipo_akumas[i], usuarios_akumas[i], descricao_akumas[i], nome_traduzido_akumas[i], fotos_akumas[i]);
                    db.akumaDao().insertAll(akumas);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //Caracteristicas TRIPULACAO
        if (db.tripulacaoDao().quantosTripulacao() == 0) {
            try {
                InputStream is = this.getAssets().open("tripulacao.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String json = new String(buffer, StandardCharsets.UTF_8);

                // Parsear JSON com Gson
                Gson gson = new Gson();
                Type tripulacaoListType = new TypeToken<List<Tripulacoes>>(){}.getType();
                List<Tripulacoes> tripulacaoList = gson.fromJson(json, tripulacaoListType);



                // Criar arrays
                int sizeList = tripulacaoList.size();
                String[] nome_tripulacao = new String[sizeList];
                String[] capitao_tripulacao = new String[sizeList];
                String[] integrantes_tripulacao = new String[sizeList];
                String[] bandeira_tripulacao = new String[sizeList];

                for (int i = 0; i < sizeList; i++) {
                    Tripulacoes tripulacoes = tripulacaoList.get(i);
                    nome_tripulacao[i] = tripulacoes.getNome();
                    capitao_tripulacao[i] = tripulacoes.getCapitao();
                    integrantes_tripulacao[i] = tripulacoes.getIntegrantes();
                    bandeira_tripulacao[i] = tripulacoes.getFoto();
                }

                int qnt = nome_tripulacao.length;
                for (int i = 0; i < qnt; i++) {
                    Tripulacoes tripulacoes = new Tripulacoes(nome_tripulacao[i], capitao_tripulacao[i], integrantes_tripulacao[i], bandeira_tripulacao[i]);
                    db.tripulacaoDao().insertAll(tripulacoes);
                }

            } catch (IOException e) {
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
                        3
                };

                String[] tipo = {
                        "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                        "P", "M", "M", "P", "P", "P", "M", "P", "P", "M",
                        "P", "P", "P", "P", "P", "M", "M", "M", "P", "P",
                        "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                        "P", "P", "P", "P", "P", "P", "P", "P", "M", "M",
                        "M", "M", "M", "M", "M", "M", "P", "P", "P", "P",
                        "P", "P", "M", "M", "P", "P", "P", "P", "P", "P",
                        "P", "P", "P", "M", "P", "P", "P", "P", "M", "P",
                        "P", "P", "P", "P", "P", "P", "P", "M", "M", "P",
                        "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                        "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                        "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                        "M", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                        "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                        "P", "P", "P", "P", "P", "P", "M", "P", "P", "M",
                        "M"
                };

                int level = 0;

                int hakirei = 0;
                int hakiobs = 0;
                int hakiarm = 0;

                int qnt = NOMES.length();
                for (int i = 0; i < qnt; i++) {
                    Inimigos inimigos = new Inimigos(NOMES.getString(i),APELIDO_NOME_POPULAR.getString(i),ARMAS.getString(i),HP.getInt(i),FORÇA.getInt(i),ESTAMINA.getInt(i),AGILIDADE.getInt(i),DEFESA.getInt(i),INTUIÇÃO.getInt(i),energia,AKUMA_NO_MI.getString(i),ASSOCIAÇÃO.getString(i),TRIPULAÇÃO_ORGANIZAÇÃO.getString(i),RECOMPENSA.getString(i),estagios[i],tipo[i],TÍTULO.getString(i),ORIGEM.getString(i),SEXO.getString(i),RAÇA.getString(i), hakiobs, hakiarm, "sem foto", "sem foto");
                    db.inimigosDao().insertAll(inimigos);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}