package com.example.trabalhofinal.Telas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.Inimigos;
import com.example.trabalhofinal.Tabelas.Personagens;
import com.example.trabalhofinal.Tabelas.Tripulacoes;
import com.example.trabalhofinal.Tabelas.Usuario;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.InfoPlayerBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InfoPersonagenUser extends Fragment {
    private InfoPlayerBinding binding;

    private AppDataBase db;

    private int pontosMAX;
    private int pontosMIN = 0;
    private Personagens personagens;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = InfoPlayerBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());

        int estagio_persangem = 0;
        Inimigos inimigos = db.inimigosDao().buscaInimigos(1);
        int nivelInimigo = 0;
        Random random = new Random();
        int qnt_ini = 0;
        List<Inimigos> inimigosE1 = db.inimigosDao().geraInimigosPorEstagios(1);
        List<Inimigos> inimigosE2 = db.inimigosDao().geraInimigosPorEstagios(2);
        List<Inimigos> inimigosE3 = db.inimigosDao().geraInimigosPorEstagios(3);
        List<Inimigos> inimigosE4 = db.inimigosDao().geraInimigosPorEstagios(4);
        List<Inimigos> inimigosE5 = db.inimigosDao().geraInimigosPorEstagios(5);

        List<String> nomesInimigos = new ArrayList<>();
        nomesInimigos.add("Deffalt");


        Bundle arg = getArguments();
        if(arg != null){
            personagens = db.personagensDao().buscaPer(arg.getInt("idPerso"));
            Usuario usuario = db.usuarioDao().buscaUsuario(arg.getInt("idU"));
            for(int i=0; i< usuario.getInimigos().size();i++){
                Inimigos inimigos1 = db.inimigosDao().buscaInimigos(usuario.getInimigos().get(i));
                nomesInimigos.add(inimigos1.getNome());
            }
            ArrayAdapter adpter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,nomesInimigos);
            binding.nomePersonagens.setAdapter(adpter);

            nivelInimigo = personagens.getNivel();
            List<Inimigos> inimigosFinal = new ArrayList<>();

            if (personagens.getNivel() >= 0){
                estagio_persangem = 1;
                inimigosFinal.addAll(inimigosE1);
            }if(personagens.getNivel() >= 25){
                estagio_persangem = 2;
                inimigosFinal.addAll(inimigosE2);
            }if (personagens.getNivel() >= 50){
                estagio_persangem = 3;
                inimigosFinal.addAll(inimigosE3);
            }if (personagens.getNivel() >= 75){
                estagio_persangem = 4;
                inimigosFinal.addAll(inimigosE4);
            }if (personagens.getNivel() >= 100){
                estagio_persangem = 5;
                inimigosFinal.addAll(inimigosE5);
            }
            qnt_ini = inimigosFinal.size();
            int levelMIN = 0;
            int levelMAX = 0;

            if(personagens.getNivel() < 26){
                levelMIN = personagens.getNivel()-10;
                levelMAX = personagens.getNivel()+10;
            }else if(personagens.getNivel() < 51){
                levelMIN = personagens.getNivel()-15;
                levelMAX = personagens.getNivel()+15;
            }else if(personagens.getNivel() < 76){
                levelMIN = personagens.getNivel()-20;
                levelMAX = personagens.getNivel()+20;
            }else{
                levelMIN = personagens.getNivel()-25;
                levelMAX = personagens.getNivel()+25;
            }

            do {
                int inimigoAleatorio = random.nextInt(qnt_ini);
                inimigos = inimigosFinal.get(inimigoAleatorio);
            }while (inimigos.getTipo().equals(personagens.getTipo()));

            do {
                nivelInimigo = random.nextInt(levelMAX - levelMIN + 1)+levelMIN;
            }while (nivelInimigo < 0);

            binding.nome.setText(personagens.getNome());
            binding.tituloPer.setText(personagens.getTitulo());
            binding.arma.setText(personagens.getArmas());
            binding.level.setText("LV "+ String.valueOf(personagens.getNivel()));
            binding.raca.setText(personagens.getRaca());
            binding.genero.setText(personagens.getSexo());
            binding.associacao.setText(personagens.getAssociacao());
            binding.recompenca.setText(personagens.getRecompensa());
            binding.regiao.setText(personagens.getOrigem());

            binding.rei.setText("Rei \n"+String.valueOf(personagens.getHakirei()));
            binding.observacao.setText("Observação \n"+String.valueOf(personagens.getHakiobs()));
            binding.armamento.setText("Armamento \n"+String.valueOf(personagens.getHakiarm()));
            AtualizaStatus();

            if(personagens.getIdTripula() != 0){
                Tripulacoes tripulacoes = db.tripulacaoDao().buscaTripulacao(personagens.getIdTripula());
                binding.qualTripulacao.setVisibility(View.VISIBLE);
                int resID = requireContext().getResources().getIdentifier(tripulacoes.getFoto(), "drawable", getContext().getPackageName());
                binding.qualTripulacao.setImageResource(resID);
            }

            if(personagens.getAkumaNoMi() != 0){
                Akumas akumas = db.akumaDao().buscaAkuma(personagens.getAkumaNoMi());
                binding.akuma.setText(akumas.getNome());
            }else {
                binding.akuma.setText("Nao tem Akuma No Mi");
                binding.ataques.setVisibility(View.INVISIBLE);
                binding.ataquesLinar.setVisibility(View.INVISIBLE);
            }

            Log.d("verifica",inimigos.toString()+" "+String.valueOf(nivelInimigo));
            Log.d("estagio",String.valueOf(estagio_persangem));
            Log.d("estagio Inimigo",String.valueOf(estagio_persangem));
        }

        Inimigos finalInimigos = inimigos;
        int finalNivelInimigo = nivelInimigo;
        binding.batalha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personagens.setImagenPersona(binding.nomePersonagens.getSelectedItem().toString());
                personagens.setIdpersonagens(arg.getInt("idPerso"));
                db.personagensDao().upgrade(personagens);

                Bundle bundle = new Bundle();
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.infoPersonagenUser, true)
                        .build();

                bundle.putInt("idU",arg.getInt("idU"));
                bundle.putInt("idPerso",arg.getInt("idPerso"));
                bundle.putInt("idInimi", finalInimigos.getIdinimigos());
                bundle.putInt("nivelIni", finalNivelInimigo);

                NavHostFragment.findNavController(InfoPersonagenUser.this)
                        .navigate(R.id.action_infoPersonagenUser_to_batalha2,bundle);
            }
        });
    }

    public void ComtrolePontos(){
        binding.btnAgilidadeMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personagens.getPontos() != 0) {
                    personagens.setAgilidade(personagens.getAgilidade() + 1);
                    pontosMAX--;
                    pontosMIN++;
                    personagens.setPontos(pontosMAX);
                    AtualizaStatus();
                }
            }
        });

        binding.btnAgilidadeMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personagens.getPontos() != 0) {
                    personagens.setAgilidade(personagens.getAgilidade() - 1);
                    pontosMAX++;
                    pontosMIN--;
                    personagens.setPontos(pontosMAX);
                    AtualizaStatus();
                }
            }
        });

        binding.btnVidaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personagens.getPontos() != 0) {
                    personagens.setHp(personagens.getHp() + 1);
                    pontosMAX--;
                    pontosMIN++;
                    personagens.setPontos(pontosMAX);
                    AtualizaStatus();
                }
            }
        });

        binding.btnVidaMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personagens.getPontos() != 0) {
                    personagens.setHp(personagens.getHp() - 1);
                    pontosMAX++;
                    pontosMIN--;
                    personagens.setPontos(pontosMAX);
                    AtualizaStatus();
                }
            }
        });

        binding.btnForcaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personagens.getPontos() != 0) {
                    personagens.setForca(personagens.getForca() + 1);
                    pontosMAX--;
                    pontosMIN++;
                    personagens.setPontos(pontosMAX);
                    AtualizaStatus();
                }
            }
        });

        binding.btnForcaMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personagens.getPontos() != 0) {
                    personagens.setForca(personagens.getForca() - 1);
                    pontosMAX++;
                    pontosMIN--;
                    personagens.setPontos(pontosMAX);
                    AtualizaStatus();
                }
            }
        });

        binding.btnEstaminaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personagens.getPontos() != 0) {
                    personagens.setEstamina(personagens.getEstamina() + 1);
                    pontosMAX--;
                    pontosMIN++;
                    personagens.setPontos(pontosMAX);
                    AtualizaStatus();
                }
            }
        });

        binding.btnEstaminaMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personagens.getPontos() != 0) {
                    personagens.setEstamina(personagens.getEstamina() - 1);
                    pontosMAX++;
                    pontosMIN--;
                    personagens.setPontos(pontosMAX);
                    AtualizaStatus();
                }
            }
        });
    }

    public void AtualizaStatus(){
        binding.vida.setText("HP: "+String.valueOf(personagens.getHp()));
        binding.forca.setText("Força: "+String.valueOf(personagens.getForca()));
        binding.estamina.setText("Estamina: "+String.valueOf(personagens.getEstamina()));
        binding.agilidade.setText("Agilidade: "+String.valueOf(personagens.getAgilidade())+"%");
        binding.defesa.setText("Defesa: "+String.valueOf(personagens.getDefesa())+"%");
        binding.intuica.setText("Intuição: "+String.valueOf(personagens.getIntuicao())+"%");
        binding.pontos.setText("Pontos: "+String.valueOf(personagens.getPontos()));

        pontosMAX = personagens.getPontos();
        if(pontosMAX != 0){
            binding.btnEstaminaMais.setVisibility(View.VISIBLE);
            binding.btnEstaminaMenos.setVisibility(View.VISIBLE);

            binding.btnVidaMais.setVisibility(View.VISIBLE);
            binding.btnVidaMenos.setVisibility(View.VISIBLE);

            binding.btnForcaMais.setVisibility(View.VISIBLE);
            binding.btnForcaMenos.setVisibility(View.VISIBLE);

            if(personagens.getAgilidade() < 75) {
                binding.btnAgilidadeMais.setVisibility(View.VISIBLE);
                binding.btnAgilidadeMenos.setVisibility(View.VISIBLE);
            }
            ComtrolePontos();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
