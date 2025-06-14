package com.example.trabalhofinal;

import android.content.res.AssetManager;
import android.os.Bundle;

import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.Inimigos;
import com.example.trabalhofinal.Tabelas.Tripulacoes;
import com.example.trabalhofinal.TabelasDao.AppDataBase;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.trabalhofinal.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;

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
                JSONArray foto = json.getJSONArray("foto");


                int qnt = NOME.length();
                for (int i = 0; i < qnt; i++) {
                    Akumas akumas = new Akumas(NOME.getString(i),TIPO.getString(i),USUÁRIOS.getString(i),DESCRIÇÃO.getString(i),NOME_TRADUZIDO.getString(i),foto.getString(i));
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
                    Tripulacoes tripulacoes = new Tripulacoes(NOME.getString(i),CAPITÃO.getString(i),INTEGRANTES.getString(i),BANDEIRA.getString(i));
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