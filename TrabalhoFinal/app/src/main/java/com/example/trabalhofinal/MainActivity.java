package com.example.trabalhofinal;

import android.os.Bundle;

import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.Inimigos;
import com.example.trabalhofinal.Tabelas.Tripulacoes;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.TabelasDao.TripulacaoDao;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

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
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
            String[] nome_inimigo = {
                    "Monkey D. Luffy", "Roronoa Zoro", "Nami", "Ussop", "Vinsmoke Sanji", "Tony Tony Chopper", "Nico Robin", "Frank", "Brook", "Jinbe",
                    "Alvida", "Koby", "Helmeppo", "Shanks", "Buggy", "Kuro", "Jango", "Gaimon", "Zeff", "Fullbody",
                    "Krieg", "Gin", "Dracule Mihawk", "Arlong", "Hatchan", "Monkey D. Garp", "Smoker", "Tashigi", "Nefertari Vivi", "Galdino",
                    "Wapol", "Dalton", "Portgas D. Ace", "Crocodile", "Daz Bonez", "Bentham", "Bellamy", "Marshall D. Teach", "Jesus Burgess", "Van Augur",
                    "Laffitte", "Doc Q", "Enel", "Foxy", "Porche", "Hamburg", "Kuzan", "Paulie", "Rob Lucci", "Kaku",
                    "Kalifa", "Jabra", "Blueno", "Kumadori", "Fukurou", "Spandam", "Gecko Moria", "Perona", "Absalom", "Oars",
                    "Shimotsuki Ryuma", "Bartholomew Kuma", "Borsalino", "Sentomaru", "Gol D. Roger", "Silvers Rayleigh", "Capone Bege", "Jewelry Bonney", "Basil Hawkins", "Scratchmen Apoo",
                    "Eustass Kid", "Killer", "Urouge", "X-Drake", "Trafalgar D. Water Law", "Bepo", "Penguin e Shachi", "Boa Hancock", "Emporio Ivankov", "Magellan",
                    "Shiryu", "Catarina Devon", "Sanjuan Wolf", "Vasco Shot", "Avalo Pizarro", "Edward Newgate", "Marco", "Izo", "Sengoku", "Sakazuki",
                    "Donquixote Doflamingo", "Sabo", "Demalo Black", "Manjaro", "Chocolat", "Mounblutain", "Drip", "Nora Gitsune", "Cocoa", "Turco",
                    "Caribou", "Hody Jones", "Dosun", "Zeo", "Daruma", "Ikaros Much", "Hyouzou", "Charlotte Linlin", "Ceaser Clown", "Vergo",
                    "Monet", "Baby 5", "Kin'emon", "Kurozumi Kanjuro", "Trebol", "Donquixote Rosinante", "Pica", "Señor Pink", "Bartolomeo", "Cavendish",
                    "Issho", "Rebecca", "Kyros", "Inuarashi", "Nekomamushi", "Carrot", "Jack", "Raizo", "Charlotte Perospero", "Charlotte Katakuri",
                    "Charlotte Oven", "Charlotte Cracker", "Vinsmoke Judge", "Vinsmoke Reiju", "Vinsmoke Ichiji", "Vinsmoke Niji", "Vinsmoke Yonji", "Kaido", "King", "Queen",
                    "Kurozumi Orochi", "Kozuki Oden", "Kikunojo", "Ashura Doji", "Kawamatsu", "Denjiro", "Aramaki", "Yamato", "Shinobu", "Stussy", "Prince Grus"
            };

            String[] apelido_inimigo = {
                    "Luffy do Chapéu de Palha", "Caçador de Piratas Zoro", "Gata Ladra", "Sogeking/God Ussop", "Sanji Perna Preta", "Amante de Algodão Doce",
                    "Criança Demônio", "Cyborg Franky", "Soul King", "Rei do Mar", "Alvida \"Clava de Ferro\"", "Herói", "Helmeppo", "Shanks o Ruivo",
                    "Buggy o Palhaço", "Klahadore, Kuro dos Cem Planos", "Jango o Vira-Casaca", "Gaimon", "Perna Vermelha", "Fullbody dos Punhos de Ferro",
                    "Don Krieg", "Homem-Bestial", "Olhos de Gavião", "Arlong", "Hachi", "Herói da Marinha", "Smoker o Caçador Branco", "Tashigi",
                    "Ex-Ms. Wednesday", "Mr. 3", "Wapol \"Prato-Fino\"", "Dalton", "Ace \"Punho de Fogo\"", "Sir Crocodile", "Mr. 1", "Mr. 2 Bon Clay",
                    "Bellamy a Hiena", "Barba Negra", "O Campeão", "O Super-Sônico", "Xerife Demônio", "Deus da Morte", "Deus Enel", "Silver Fox",
                    "Porche", "Four-Footed Dash Freaky", "Aokiji", "Paulie", "Arma do Massacre", "Vento da Montanha", "Kalifa", "Jabra", "Blueno",
                    "Kumadori", "Fukurou o Silencioso", "Spandam", "Gecko Moria", "Princesa Fantasma", "Absalom do Cemitério", "Oars o Puxador de Continentes",
                    "Espadachim Lendário", "PX-0", "Kizaru", "Sentomaru", "Gold Roger o Rei do Piratas", "Rei das Trevas", "Capone \"Gang\" Bege", "A Comilona",
                    "Mágico", "Rugido do Mar", "Eustass \"Captain\" Kid", "Kamazo o Retalhador", "Monge Louco", "Bandeira Vermelha", "Cirurgião da Morte",
                    "Bepo", "Penguin e Shachi", "Princesa Serpente", "Rei Okama", "Megallan", "Shiryu da Chuva", "Caçadora da Lua Crescente",
                    "Navio de Batalha Colossal", "Vasco Shot \"O Beberrão\"", "O Rei Corrupto", "Barba Branca", "Marco a Fênix", "Izo", "Sengoku o Buddha",
                    "Akainu", "Demônio Celestial", "Emperador do Fogo", "Fake Luffy", "Fake Zoro", "Fake Nami", "Fake Sogeking", "Fake Sanji", "Fake Chopper",
                    "Fake Robin", "Fake Franky", "Caribou do Cabeço Molhado", "Hody Jones", "Dosun", "Nobre do Distrito dos Homens-Peixe", "Daruma", "Ikaros Much",
                    "Hyouzou", "Big Mom", "Gangster Gastino", "Vergo \"Demônio do Bamboo\"", "Monet", "Baby 5", "Kin'emon Raposa de Fogo", "Kanjuro \"Chuva da Tarde\"",
                    "Trebol", "Corazon", "Pica", "Señor Pink", "Bartolomeu o Canibal", "Cavendish do Cavalo Branco", "Fujitora", "A Mulher Invencível",
                    "O Gladiador Invencível", "Duque Inuarashi", "Mestre Nekomamushi", "Carrot", "Jack da Seca", "Raizo da Névoa", "Perospero", "Katakuri",
                    "Oven", "Cracker dos Mil Braços", "Garuda", "Poison Pink", "Sparking Red", "Dengeki Blue", "Winch Green", "Kaido das Feras",
                    "King do Incêndio", "Queen da Praga", "Orochi", "Lorde Idiota", "O-Kiku", "Shutenmaru", "Gyukimaru", "Kyoshiro", "Ryokygyu",
                    "Princesa Oni", "Kunoichi Encantadora", "Rainha do Distrito do Prazer", "Prince Grus"
            };

            String[] arma_inimigo = {
                    "Soco", "Wado Ichimonji, Kitetsu III, Enma", "Clima-Tact", "Kuro Kabuto", "Chute", "Nenhuma", "Nenhuma", "Tiro Laser", "Espada",
                    "Arte Marcial", "Kanabo", "Soco", "Espada", "Espada", "Adaga/Faca", "Garras", "Lâmina Circular", "Nenhuma", "Chute", "Soco",
                    "Arma de Fogo", "Bastão", "Yoru", "Lança", "Espada", "Soco", "Nanashaku Jitte", "Meito Shigure", "Lâmina Circular", "Nenhuma",
                    "Nenhuma", "Bisento", "Nenhuma", "Nenhuma", "Nenhuma", "Chute", "Nenhuma", "Soco", "Arma de Fogo", "Bastão", "Foice",
                    "Nonosama Bo", "Nenhuma", "Bastão", "Kanabo", "Soco", "Chicote", "Arte Marcial", "Arte Marcial", "Arte Marcial", "Arte Marcial",
                    "Arte Marcial", "Arte Marcial", "Arte Marcial", "Espada", "Espada", "Nenhuma", "Arma de Fogo", "Soco", "Shusui", "Tiro Laser",
                    "Chute", "Machado", "Ace", "Espada", "Arma de Fogo", "Nenhuma", "Espada", "Bastão", "Soco", "Lâmina Giratória", "Kanabo",
                    "Espada", "Espada", "Arte Marcial", "Bastão", "Nenhuma", "Arte Marcial", "Nenhuma", "Espada", "Espada", "Nenhuma", "Arma de Fogo",
                    "Nenhuma", "Naginata", "Nenhuma", "Arma de Fogo", "Nenhuma", "Nenhuma", "Nenhuma", "Bastão", "Arma de Fogo", "Espada",
                    "Arma de Fogo", "Arma de Fogo", "Chute", "Garras", "Nenhuma", "Nenhuma", "Arma de Fogo", "Tridente", "Kanabo", "Chicote",
                    "Garras", "Lança", "Espada", "Napoleon", "Arma de Fogo", "Bastão", "Espada", "Arma de Fogo", "Sukesan e Kakusan", "Espada",
                    "Arma de Fogo", "Arma de Fogo", "Espada", "Soco", "Adaga/Faca", "Espada", "Espada", "Espada", "Espada", "Espada", "Arma de Fogo",
                    "Garras", "Foice", "Shuriken", "Bastão", "Tridente", "Naginata", "Espada", "Traje Germa", "Traje Germa", "Traje Germa", "Traje Germa",
                    "Traje Germa", "Hassaikai", "Espada", "Tiro Laser", "Arma de Fogo", "Ame no Habakiri e Enma", "Espada", "Espada", "Espada",
                    "Espada", "Espada", "Hassaikai", "Shuriken", "Arte Marcial", "Nenhuma"
            };

            String[] akumas_inimigo = {
                    "Gomu Gomu no Mi", "---", "---", "---", "---", "Hito Hito no Mi", "Hana Hana no Mi", "---", "Yomi Yomi no Mi", "---",
                    "Sube Sube no Mi", "---", "---", "---", "Bara Bara no Mi", "---", "---", "---", "---", "---",
                    "---", "---", "---", "---", "Moku Moku no Mi", "---", "---", "Doru Doru no Mi", "Baku Baku no Mi", "Ushi Ushi no Mi, Modelo: Bisão",
                    "Mera Mera no Mi", "Suna Suna no Mi", "Supa Supa no Mi", "Mane Mane no Mi", "Bane Bane no Mi", "Yami Yami no Mi, Gura Gura no Mi", "Riki Riki no Mi", "Wapu Wapu no Mi", "Desconhecida", "Shiku Shiku no Mi",
                    "Goro Goro no Mi", "Noro Noro no Mi", "---", "---", "Hie Hie no Mi", "---", "Neko Neko no Mi, Modelo: Leopardo", "Ushi Ushi no Mi, Modelo: Girafa", "Awa Awa no Mi", "Inu Inu no Mi, Modelo: Lobo",
                    "Doa Doa no Mi", "---", "---", "---", "Kage Kage no Mi", "Horo Horo no Mi", "Suke Suke no Mi", "---", "---", "Nikyu Nikyu no Mi",
                    "Pika Pika no Mi", "---", "---", "---", "Shiro Shiro no Mi", "Toshi Toshi no Mi", "Wara Wara no Mi", "Oto Oto no Mi", "Jiki Jiki no Mi", "---",
                    "Desconhecida", "Ryu Ryu no Mi, Modelo: Alossauro", "Ope Ope no Mi", "---", "---", "Mero Mero no Mi", "Horu Horu no Mi", "Doku Doku no Mi", "Suke Suke no Mi", "Inu Inu no Mi, Modelo: Kyubi no Kitsune",
                    "Deka Deka no Mi", "Gabu Gabu no Mi", "Shima Shima no Mi", "Gura Gura no Mi", "Tori Tori no Mi, Modelo: Fênix", "---", "Hito Hito no Mi, Modelo: Daibutsu", "Magu Magu no Mi", "Ito Ito no Mi", "Mera Mera no Mi",
                    "---", "---", "---", "---", "---", "---", "---", "Numa Numa no Mi", "---", "---",
                    "---", "---", "---", "---", "Soru Soru no Mi", "Gasu Gasu no Mi", "---", "Yuki Yuki no Mi", "Buki Buki no Mi", "Fuku Fuku no Mi",
                    "Fude Fude no Mi", "Beta Beta no Mi", "Nagi Nagi no Mi", "Ishi Ishi no Mi", "Sui Sui no Mi", "Bari Bari no Mi", "---", "Zushi Zushi no Mi", "---", "---",
                    "---", "---", "---", "Zou Zou no Mi, Modelo: Mamute", "Maki Maki no Mi", "Pero Pero no Mi", "Mochi Mochi no Mi", "Netsu Netsu no Mi", "Bisu Bisu no Mi", "---",
                    "---", "---", "---", "---", "Uo Uo no Mi, Modelo: Dragão", "Ryu Ryu no Mi, Modelo: Pteranodonte", "Ryu Ryu no Mi, Modelo: Braquiossauro", "Hebi Hebi no Mi, Modelo: Yamata no Orochi", "---", "---",
                    "---", "---", "---", "Mori Mori no Mi", "Inu Inu no Mi, Modelo: Okuchi no Makami", "Juku Juku no Mi", "Batto Batto no Mi", "Gunyo Gunyo no Mi"
            };

            String[] associacao_inimigo = {
                    "Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Pirata",
                    "Pirata", "Marinha", "Marinha", "Pirata", "Pirata", "Pirata", "Marinha", "Nenhuma", "Nenhuma", "Marinha",
                    "Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Marinha", "Marinha", "Marinha", "Pirata", "Pirata",
                    "Pirata", "Nenhuma", "Pirata", "Pirata", "Pirata", "Pirata", "Nenhuma", "Marinha", "Marinha", "Marinha",
                    "Marinha", "Marinha", "Marinha", "Marinha", "Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Revolucionário",
                    "Marinha", "Marinha", "Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Pirata",
                    "Pirata", "Marinha", "Pirata", "Pirata", "Pirata", "Pirata", "Revolucionário", "Marinha", "Pirata", "Pirata",
                    "Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Marinha", "Marinha", "Pirata", "Revolucionário", "Pirata",
                    "Pirata", "Pirata", "Pirata", "Pirata", "Nenhuma", "Pirata", "Pirata", "Pirata", "Pirata", "Pirata",
                    "Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Nenhuma", "Pirata", "Pirata", "Pirata", "Nenhuma",
                    "Nenhuma", "Pirata", "Marinha", "Pirata", "Pirata", "Pirata", "Pirata", "Marinha", "Nenhuma", "Nenhuma",
                    "Nenhuma", "Nenhuma", "Nenhuma", "Pirata", "Nenhuma", "Pirata", "Pirata", "Pirata", "Pirata", "Nenhuma",
                    "Nenhuma", "Nenhuma", "Nenhuma", "Nenhuma", "Pirata", "Pirata", "Pirata", "Nenhuma", "Pirata", "Nenhuma",
                    "Nenhuma", "Nenhuma", "Nenhuma", "Marinha", "Pirata", "Nenhuma", "Marinha", "Marinha"
            };

            String[] tripulacao_inimigo = {
                    "Straw Hat Pirates", "Straw Hat Pirates", "Straw Hat Pirates", "Straw Hat Pirates", "Straw Hat Pirates", "Straw Hat Pirates", "Straw Hat Pirates", "Straw Hat Pirates", "Straw Hat Pirates", "Straw Hat Pirates",
                    "Cross Guild", "SWORD", "SWORD", "Red Hair Pirates", "Cross Guild", "Black Cat Pirates", "Marinha", "Nenhuma", "Nenhuma", "Marinha",
                    "Krieg Pirates", "Krieg Pirates", "Cross Guild", "Arlong Pirates", "Arlong Pirates", "Marinha", "Base G-5, Unidade 1", "Base G-5, Unidade 1", "Straw Hat Pirates", "Cross Guild",
                    "Nenhuma", "Nenhuma", "Whitebeard Pirates", "Cross Guild", "Cross Guild", "Nenhuma", "Donquixote Pirates", "Blackbeard Pirates", "Blackbeard Pirates", "Blackbeard Pirates",
                    "Blackbeard Pirates", "Blackbeard Pirates", "Nenhuma", "Foxy Pirates", "Foxy Pirates", "Foxy Pirates", "Blackbeard Pirates", "Nenhuma", "CP0", "CP0",
                    "CP9", "CP9", "CP9", "CP9", "CP9", "CP9", "Thriller Bark Pirates", "Thriller Bark Pirates", "Thriller Bark Pirates", "Thriller Bark Pirates",
                    "Thriller Bark Pirates", "Exército Revolucionário", "Marinha", "Marinha", "Roger Pirates", "Roger Pirates", "Fire Tank Pirates", "Bonney Pirates", "Beast Pirates", "Beast Pirates",
                    "Kid Pirates", "Kid Pirates", "Fallen Monk Pirates", "Marinha", "Heart Pirates", "Heart Pirates", "Heart Pirates", "Kuja Pirates", "Exército Revolucionário", "Impel Down",
                    "Blackbeard Pirates", "Blackbeard Pirates", "Blackbeard Pirates", "Blackbeard Pirates", "Blackbeard Pirates", "Whitebeard Pirates", "Whitebeard Pirates", "Whitebeard Pirates", "QG da Marinha", "QG da Marinha",
                    "Donquixote Pirates", "Exército Revolucionário", "Fake Straw Hat Pirates", "Fake Straw Hat Pirates", "Fake Straw Hat Pirates", "Fake Straw Hat Pirates", "Fake Straw Hat Pirates", "Fake Straw Hat Pirates", "Fake Straw Hat Pirates", "Fake Straw Hat Pirates",
                    "Fake Straw Hat Pirates", "New Fish-Man Pirates", "New Fish-Man Pirates", "New Fish-Man Pirates", "New Fish-Man Pirates", "New Fish-Man Pirates", "New Fish-Man Pirates", "Big Mom Pirates", "NEO MADS", "Donquixote Pirates",
                    "Donquixote Pirates", "Straw Hat Grand Fleet", "Nenhuma", "Nenhuma", "Donquixote Pirates", "Marinha", "Donquixote Pirates", "Donquixote Pirates", "Barto Club", "Beautiful Pirates",
                    "Marinha", "Nenhuma", "Nenhuma", "Nenhuma", "Nenhuma", "Nenhuma", "Beast Pirates", "Nenhuma", "Big Mom Pirates", "Big Mom Pirates",
                    "Big Mom Pirates", "Big Mom Pirates", "Germa 66", "Germa 66", "Germa 66", "Germa 66", "Germa 66", "Beast Pirates", "Beast Pirates", "Beast Pirates",
                    "Nenhuma", "Roger Pirates", "Nenhuma", "Nenhuma", "Nenhuma", "Nenhuma", "Marinha", "Nenhuma", "Nenhuma", "CP0", "SWORD"
            };

            String[] reconpenca_inimigo = {
                    "B$ 3.000.000.000", "B$ 1.111.000.000", "B$ 366.000.000", "B$ 500.000.000", "B$ 1.032.000.000", "B$ 1.000", "B$ 930.000.000", "B$ 394.000.000", "B$ 383.000.000", "B$ 1.100.000.000",
                    "B$ 5.000.000", "★★★★★", "★", "B$ 4.048.900.000", "B$ 3.189.000.000", "B$ 16.000.000", "N/A", "N/A", "N/A", "N/A",
                    "B$ 17.000.000", "B$ 12.000.000", "B$ 3.590.000.000", "B$ 20.000.000", "B$ 8.000.000", "♛♛♛", "★★★★★", "★", "N/A", "B$ 24.000.000",
                    "N/A", "N/A", "B$ 550.000.000", "B$ 1.965.000.000", "B$ 75.000.000", "B$ 32.000.000", "B$ 195.000.000", "B$ 3.996.000.000", "B$ 20.000.000", "B$ 64.000.000",
                    "B$ 42.200.000", "B$ 72.000.000", "N/A", "B$ 24.000.000", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A",
                    "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "B$ 320.000.000", "N/A", "N/A", "N/A",
                    "N/A", "B$ 296.000.000", "♛♛♛", "★★★★★", "B$ 5.564.800.000", "Desconhecida", "B$ 350.000.000", "B$ 320.000.000", "B$ 320.000.000", "B$ 350.000.000",
                    "B$ 3.000.000.000", "B$ 200.000.000", "B$ 108.000.000", "B$ 222.000.000", "B$ 3.000.000.000", "B$ 1.500", "Desconhecida", "B$ 1.659.000.000", "B$ 100.000.000", "N/A",
                    "Desconhecida", "Desconhecida", "Desconhecida", "Desconhecida", "Desconhecida", "B$ 5.046.000.000", "B$ 1.374.000.000", "B$ 510.000.000", "♛♛♛♛♛", "♛♛♛♛♛",
                    "B$ 340.000.000", "Desconhecida", "B$ 26.000.000", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A",
                    "B$ 210.000.000", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "B$ 4.388.000.000", "B$ 300.000.000", "N/A",
                    "N/A", "N/A", "N/A", "N/A", "B$ 99.000.000", "N/A", "B$ 99.000.000", "B$ 58.000.000", "B$ 200.000.000", "B$ 330.000.000",
                    "♛♛♛", "N/A", "N/A", "N/A", "N/A", "N/A", "B$ 1.000.000.000", "N/A", "B$ 700.000.000", "B$ 1.057.000.000",
                    "B$ 300.000.000", "B$ 860.000.000", "N/A", "N/A", "N/A", "N/A", "N/A", "B$ 4.611.100.000", "B$ 1.390.000.000", "B$ 1.320.000.000",
                    "N/A", "Desconhecida", "N/A", "N/A", "N/A", "N/A", "♛♛♛", "N/A", "N/A", "N/A", "★★★★★"
            };

            String[] titulo_inimigo = {
                    "Capitão Pirata, Yonko, Super Nova", "Espadachim, Super Nova", "Navegadora", "Sniper", "Cozinheiro", "Médico/Pet", "Arqueóloga", "Carpinteiro", "Músico, Espadachim", "Timoneiro, Shichibukai",
                    "Capitã Pirata", "Capitão da Marinha", "Capitão Fragnata", "Capitão Pirata, Yonko", "Yonko", "Capitão Pirata", "Aprendiz de Marinheiro", "Civil", "Cozinheiro", "Tenente-Comandante",
                    "Capitão Pirata", "Imediato", "Espadachim, Shichibukai", "Capitão Pirata", "Vendedor de Takoyaki", "Vice-Almirante", "Vice-Almirante", "Capitã da Marinha", "Princesa de Alabasta", "Pirata",
                    "Rei do Reino Black Drum", "Rei do Reino Sakura", "Comandante da 2° Divisão", "Shichibukai", "Pirata", "Rainha da Terra Newkama", "Tintureiro", "Capitão Pirata, Yonko, Shichibukai", "Timoneiro, Capitão do Primeiro Navio", "Sniper, Capitão do Terceiro Navio",
                    "Navegador, Capitão do Quinto Navio", "Médico, Capitão do Nono Navio", "Deus de Skypiea", "Capitão Pirata", "Pirata", "Pirata", "Pirata, Ex-Almirante", "Vice-Presidente da Galley-la Company", "Agente da Cipher Pol, Carpinteiro", "Agente da Cipher Pol, Carpinteiro",
                    "Agente da Cipher Pol, Secretária", "Agente da Cipher Pol", "Agente da Cipher Pol, Barman", "Agente da Cipher Pol", "Agente da Cipher Pol", "Sub-Chefe da Cipher Pol", "Capitão Pirata, Shichibukai", "Comandante dos Zumbis Selvagens e Zumbis Supresa", "Repórter, Líder dos Zumbis Soldados e Zumbis Generais", "Zumbi Especial",
                    "General Zumbi", "Comandante do Exército Revolucionário", "Almirante", "Oficial da Marinha, Capitão da Unidade de Ciência da Marinha", "Rei dos Piratas", "Imediato, Mecânico", "Capitão Pirata, Mafioso, Super Nova", "Capitã Pirata, Princesa, Super Nova", "Shinuchi, Super Nova", "Músico, Informante, Super Nova",
                    "Capitão Pirata, Super Nova", "Super Nova", "Capitão Pirata, Super Nova", "Capitão da SWORD, Super Nova", "Capitão Pirata, Médico, Shichibukai", "Navegador", "Piratas", "Capitã Pirata, Imperatriz de Amazon Lily, Shichibukai", "Comandante G do Exército Revolucionário, Rainha", "Vice-Guardião de Impel Down",
                    "Capitão do Segundo Navio", "Capitã do Sexto Navio", "Capitão do Sétimo Navio", "Capitão do Oitavo Navio", "Capitão do Quarto Navio", "Capitão Pirata, Yonko", "Comandante da 1° Divisão, Médico", "Comandante da 16º Divisão, Samurai", "Inspetor General, Ex-Almirante da Frota", "Almirante da Frota",
                    "Capitão Pirata, Shichibukai, Rei de Dressrosa", "Segundo no Comando do Exército Revolucionário", "Capitão Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Pet", "Pirata", "Pirata",
                    "Capitão Pirata", "Capitão Pirata", "Pirata", "Pirata", "Pirata", "Pirata", "Mercenário, Pirata", "Capitã Pirata, Yonko, Rainha de Totto Land", "Cientista", "Pirata",
                    "Secretária, Espiã", "Pirata", "Samurai, Líder dos Nove Bainhas Vermelhas", "Samurai, Ator, Bainha Vermelha", "Oficial Executivo Pirata, Guarda-Costas", "Comandante da Marinha", "Oficial Executivo Pirata", "Oficial Pirata", "Capitão do Segundo Navio do Straw Hat Grand Fleet", "Capitão do Primeiro Navio do Straw Hat Grand Fleet",
                    "Almirante", "Princesa de Dressrosa", "Gladiador, Capitão da Guarda Real", "Samurai, Bainha Vermelha, Comandante do Mosqueteiros", "Guardião da Floresta da Baleia, Samurai, Bainha Verrmelha", "Mosqueteira", "All-Star, Capitão Pirata", "Ninja, Samurai, Bainha Vermelha", "Ministro do Doce", "General Doce, Ministro da Farinha",
                    "Ministro da Comida Dourada", "General Doce, Ministro do Biscoito", "Rei do Reino Germa", "Princesa, Mercenária", "Príncipe, Mercenário", "Príncipe, Mercenário", "Príncipe, Mercenário", "Capitão Pirata, Yonko", "All-Star", "All-Star, Cientista",
                    "Shogun do País de Wano", "Samurai, Daimyo", "Samurai, Bainha Vermelha", "Samurai, Bainha Vermelha, Chefe da Yakuza", "Samurai, Bainha Vermelha, Lutador de Sumo", "Samurai, Bainha Vermelha", "Almirante", "Pirata", "Kunoichi", "Agente da Cipher Pol, Imperatriz do Subterrânero", "Contra-Almirante"
            };

            String[] origem_inimigo = {
                    "East Blue", "East Blue", "East Blue", "East Blue", "North Blue", "Grand Line", "West Blue", "South Blue", "West Blue", "Grand Line",
                    "East Blue", "East Blue", "East Blue", "West Blue", "Grand Line", "East Blue", "East Blue", "East Blue", "East Blue", "North Blue",
                    "East Blue", "East Blue", "Grand Line", "East Blue", "East Blue", "East Blue", "Grand Line", "East Blue", "Grand Line", "South Blue",
                    "Grand Line", "Grand Line", "South Blue", "Grand Line", "West Blue", "East Blue", "Grand Line", "Grand Line", "Grand Line", "East Blue",
                    "West Blue", "North Blue", "Grand Line", "South Blue", "Grand Line", "Grand Line", "South Blue", "Grand Line", "Grand Line", "East Blue",
                    "Grand Line", "North Blue", "North Blue", "South Blue", "Grand Line", "Grand Line", "West Blue", "West Blue", "West Blue", "North Blue",
                    "Grand Line", "South Blue", "North Blue", "Grand Line", "East Blue", "Grand Line", "West Blue", "South Blue", "North Blue", "Grand Line",
                    "South Blue", "South Blue", "Grand Line", "North Blue", "North Blue", "Grand Line", "North Blue", "Grand Line", "Grand Line", "Grand Line",
                    "Grand Line", "South Blue", "West Blue", "South Blue", "North Blue", "Grand Line", "Grand Line", "Grand Line", "South Blue", "North Blue",
                    "Grand Line", "East Blue", "East Blue", "East Blue", "East Blue", "East Blue", "East Blue", "East Blue", "East Blue", "East Blue",
                    "North Blue", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "North Blue",
                    "North Blue", "North Blue", "Grand Line", "Grand Line", "North Blue", "Grand Line", "North Blue", "North Blue", "East Blue", "Grand Line",
                    "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line",
                    "Grand Line", "Grand Line", "Grand Line", "North Blue", "North Blue", "North Blue", "North Blue", "North Blue", "Grand Line", "Grand Line",
                    "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "Grand Line", "South Blue",
                    "Grand Line", "Grand Line", "Grand Line", "West Blue"
            };

            String[] genero_inimigo = {
                    "Masculino", "Masculino", "Feminino", "Masculino", "Masculino", "Masculino", "Feminino", "Masculino", "Masculino", "Masculino",
                    "Feminino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino",
                    "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Feminino", "Feminino", "Masculino",
                    "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Okama", "Masculino", "Masculino", "Masculino", "Masculino",
                    "Masculino", "Masculino", "Masculino", "Masculino", "Feminino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino",
                    "Feminino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Feminino", "Masculino", "Masculino",
                    "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Feminino",
                    "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Feminino",
                    "Okama", "Masculino", "Masculino", "Feminino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino",
                    "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Feminino", "Masculino", "Masculino", "Masculino",
                    "Feminino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Feminino",
                    "Masculino", "Masculino", "Feminino", "Feminino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino",
                    "Masculino", "Masculino", "Masculino", "Feminino", "Masculino", "Masculino", "Masculino", "Feminino", "Masculino", "Masculino",
                    "Masculino", "Masculino", "Masculino", "Masculino", "Masculino", "Feminino", "Masculino", "Masculino", "Masculino", "Masculino",
                    "Masculino", "Masculino", "Masculino", "Masculino", "Feminino", "Masculino", "Masculino", "Masculino", "Masculino", "Masculino",
                    "Feminino", "Feminino", "Masculino"
            };

            String[] raca_inimigo = {
                    "Humano", "Humano", "Humano", "Humano", "Humano", "Animal", "Humano", "Humano", "Humano", "Tritão",
                    "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano",
                    "Humano", "Humano", "Humano", "Tritão", "Tritão", "Humano", "Humano", "Humano", "Humano", "Humano",
                    "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano",
                    "Humano", "Humano", "Humano", "Shandia", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano",
                    "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Gigante",
                    "Humano", "Bucaneiro", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Long-Arm",
                    "Humano", "Humano", "Shandia", "Humano", "Humano", "Mink", "Humano", "Humano", "Humano", "Humano",
                    "Humano", "Humano", "Gigante", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano",
                    "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Animal", "Humano", "Humano",
                    "Humano", "Tritão", "Tritão", "Tritão", "Tritão", "Tritão", "Tritão", "Gigante", "Humano", "Humano",
                    "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano",
                    "Humano", "Humano", "Humano", "Humano", "Mink", "Mink", "Mink", "Tritão", "Humano", "Humano",
                    "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano", "Humano",
                    "Lunariano", "Humano", "Humano", "Humano", "Humano", "Humano", "Tritão", "Humano", "Humano", "Humano",
                    "Humano", "Clone", "Humano"
            };
            int[] estagios = {
                    5, 4, 4, 4, 4, 4, 4, 4, 4, 4,
                    1, 3, 3, 5, 1, 1, 1, 1, 1, 1,
                    1, 1, 4, 1, 1, 5, 3, 3, 1, 1,
                    1, 1, 2, 4, 2, 2, 2, 5, 5, 5,
                    5, 5, 1, 1, 1, 1, 5, 2, 4, 4,
                    2, 2, 2, 2, 2, 1, 2, 2, 2, 2,
                    2, 4, 5, 4, 5, 5, 3, 3, 3, 3,
                    4, 3, 3, 3, 4, 3, 3, 2, 5, 5,
                    5, 5, 5, 5, 4, 4, 4, 5, 4, 4,
                    1, 1, 1, 1, 1, 1, 1, 1, 3, 3,
                    3, 3, 3, 3, 3, 5, 3, 3, 3, 3,
                    4, 4, 3, 3, 3, 3, 3, 5, 3, 3,
                    4, 4, 3, 4, 4, 4, 4, 4, 4, 4,
                    4, 4, 4, 4, 4, 5, 4, 4, 4, 5,
                    4, 4, 4, 4, 5, 4, 4, 5, 3
            };

            String[] tipo = {
                    "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                    "P", "M", "M", "P", "P", "P", "M", "P", "P", "M",
                    "P", "P", "P", "P", "P", "M", "M", "M", "P", "P",
                    "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                    "P", "P", "P", "P", "M", "P", "P", "P", "M", "M",
                    "M", "M", "M", "M", "P", "M", "P", "P", "P", "P",
                    "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                    "P", "P", "P", "M", "P", "P", "P", "P", "P", "P",
                    "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                    "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                    "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                    "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                    "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                    "P", "P", "P", "P", "P", "P", "P", "P", "P", "P",
                    "P", "P", "P", "P", "P", "P", "P", "P", "P", "P"
            };

            int level = 0;
            int intuicao = 2;
            int defesa = 0;
            int agilidade = 0;
            int estamina = 5;
            int forca = 5;
            int hp = 20;
            int energia = 5;

            int hakirei = 0;
            int hakiobs = 0;
            int hakiarm = 0;

            int qnt = nome_inimigo.length;
            for (int i = 0; i < qnt; i++) {
                Inimigos inimigos = new Inimigos(nome_inimigo[i], apelido_inimigo[i], arma_inimigo[i], hp, forca, estamina, agilidade, defesa, intuicao, energia, akumas_inimigo[i], associacao_inimigo[i], tripulacao_inimigo[i], reconpenca_inimigo[i], estagios[i], tipo[i], titulo_inimigo[i], origem_inimigo[i], genero_inimigo[i], raca_inimigo[i], hakiobs, hakiarm, "sem foto", "sem foto");
                db.inimigosDao().insertAll(inimigos);
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