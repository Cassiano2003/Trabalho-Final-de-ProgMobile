package com.example.trabalhofinal.Telas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.AtaqueAkumaNoMi;
import com.example.trabalhofinal.Tabelas.Inimigos;
import com.example.trabalhofinal.Tabelas.Jogador;
import com.example.trabalhofinal.Tabelas.Tripulacoes;
import com.example.trabalhofinal.Tabelas.Usuario;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.InfoJogadorBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InfoJogador extends Fragment {
    private InfoJogadorBinding binding;

    private AppDataBase db;

    private int pontosMAX;
    private int pontosMIN = 0;
    private Jogador jogador;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = InfoJogadorBinding.inflate(inflater, container, false);
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
            jogador = db.personagensDao().buscaPer(arg.getInt("idPerso"));
            Usuario usuario = db.usuarioDao().buscaUsuario(arg.getInt("idU"));
            for(int i=0; i< usuario.getInimigos().size();i++){
                Inimigos inimigos1 = db.inimigosDao().buscaInimigos(usuario.getInimigos().get(i));
                nomesInimigos.add(inimigos1.getNome());
            }
            ArrayAdapter adpter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,nomesInimigos);
            binding.nomePersonagens.setAdapter(adpter);

            String nome = jogador.getImagenPersona();
            int posicao = EncontraNome(nomesInimigos,nome);
            if(posicao != -1 && posicao != 0){
                binding.nomePersonagens.setSelection(posicao);
            }


            nivelInimigo = jogador.getNivel();
            List<Inimigos> inimigosFinal = new ArrayList<>();

            if (jogador.getNivel() >= 0){
                estagio_persangem = 1;
                inimigosFinal.addAll(inimigosE1);
            }if(jogador.getNivel() >= 25){
                estagio_persangem = 2;
                inimigosFinal.addAll(inimigosE2);
            }if (jogador.getNivel() >= 50){
                estagio_persangem = 3;
                inimigosFinal.addAll(inimigosE3);
            }if (jogador.getNivel() >= 75){
                estagio_persangem = 4;
                inimigosFinal.addAll(inimigosE4);
            }if (jogador.getNivel() >= 100){
                estagio_persangem = 5;
                inimigosFinal.addAll(inimigosE5);
            }
            qnt_ini = inimigosFinal.size();
            int levelMIN = 0;
            int levelMAX = 0;

            if(jogador.getNivel() < 26){
                levelMIN = jogador.getNivel()-10;
                levelMAX = jogador.getNivel()+10;
            }else if(jogador.getNivel() < 51){
                levelMIN = jogador.getNivel()-15;
                levelMAX = jogador.getNivel()+15;
            }else if(jogador.getNivel() < 76){
                levelMIN = jogador.getNivel()-20;
                levelMAX = jogador.getNivel()+20;
            }else{
                levelMIN = jogador.getNivel()-25;
                levelMAX = jogador.getNivel()+25;
            }

            do {
                int inimigoAleatorio = random.nextInt(qnt_ini);
                inimigos = inimigosFinal.get(inimigoAleatorio);
            }while (inimigos.getTipo().equals(jogador.getTipo()));

            do {
                nivelInimigo = random.nextInt(levelMAX - levelMIN + 1)+levelMIN;
            }while (nivelInimigo < 0);

            binding.nome.setText(jogador.getNome());
            binding.tituloPer.setText(jogador.getTitulo());
            binding.arma.setText(jogador.getArmas());
            binding.level.setText("LV "+ String.valueOf(jogador.getNivel()));
            binding.raca.setText(jogador.getRaca());
            binding.genero.setText(jogador.getSexo());
            binding.associacao.setText(jogador.getAssociacao());
            binding.recompenca.setText(jogador.getRecompensa());
            binding.regiao.setText(jogador.getOrigem());

            binding.rei.setText("Rei \n"+String.valueOf(jogador.getHakirei()));
            binding.observacao.setText("Observação \n"+String.valueOf(jogador.getHakiobs()));
            binding.armamento.setText("Armamento \n"+String.valueOf(jogador.getHakiarm()));
            AtualizaStatus(arg);

            if(jogador.getIdTripula() != 0){
                Tripulacoes tripulacoes = db.tripulacaoDao().buscaTripulacao(jogador.getIdTripula());
                binding.qualTripulacao.setVisibility(View.VISIBLE);
                int resID = requireContext().getResources().getIdentifier(tripulacoes.getFoto(), "drawable", getContext().getPackageName());
                binding.qualTripulacao.setImageResource(resID);
            }

            if(jogador.getAkumaNoMi() != 0){
                Akumas akumas = db.akumaDao().buscaAkuma(jogador.getAkumaNoMi());
                AtaqueAkumaNoMi ataqueAkumaNoMi = db.ataqueAkumasDao().buscaAtaqueAkumaNoMi(akumas.getIdataques());
                DesbloqueiaAtaques(akumas);
                for (int i=0;i < jogador.getQntataquesdesbloqueados();i++){
                    TextView novo = new TextView(requireContext());
                    novo.setText(ataqueAkumaNoMi.getNomeDoAtaque()[i]+" \n"+ataqueAkumaNoMi.getTipoDeAtaque()[i]
                            +" \n"+ataqueAkumaNoMi.getDescricao()[i]);
                    binding.ataquesLinar.addView(novo);
                }
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
                Bundle bundle = new Bundle();
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.infoPersonagenUser, true)
                        .build();

                bundle.putInt("idU",arg.getInt("idU"));
                bundle.putInt("idPerso",arg.getInt("idPerso"));
                bundle.putInt("idInimi", finalInimigos.getIdinimigos());
                bundle.putInt("nivelIni", finalNivelInimigo);

                NavHostFragment.findNavController(InfoJogador.this)
                        .navigate(R.id.action_infoPersonagenUser_to_batalha2,bundle);
            }
        });

        binding.nomePersonagens.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nome = binding.nomePersonagens.getSelectedItem().toString();
                if(!nome.equals("Deffalt")){
                    String fotoperfio = db.inimigosDao().buscaInimigos(nomesInimigos.get(position));
                    Log.d("Fotoperfio","A foto do "+fotoperfio+" Nome do cidadao "+nomesInimigos.get(position));
                    if (fotoperfio != null) {
                        int resID = requireContext().getResources().getIdentifier(fotoperfio, "drawable", requireContext().getPackageName());
                        if (resID != 0) { // Verifica se o recurso existe
                            binding.foto.setImageResource(resID);
                            jogador.setImagenPersona(nome);
                            jogador.setIdpersonagens(arg.getInt("idPerso"));
                            db.personagensDao().upgrade(jogador);
                            Log.d("Foto colocada","A foto do "+fotoperfio+" foi colocada");
                        } else {
                            Log.e("InfoJogador", "Recurso drawable não encontrado: " + fotoperfio);
                            //binding.foto.setImageResource(R.drawable.imagem_padrao);
                        }
                    } else {
                        Log.e("InfoJogador", "fotoperfio é null");
                        //binding.foto.setImageResource(R.drawable.imagem_padrao);
                    }
                }else{
                    binding.foto.setImageResource(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void ComtrolePontos(Bundle arg){
        binding.btnAgilidadeMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMAX != 0) {
                    jogador.setAgilidade(jogador.getAgilidade() + 1);
                    pontosMAX--;
                    pontosMIN++;
                    jogador.setPontos(pontosMAX);
                    AtualizaStatus(arg);
                }
            }
        });

        binding.btnAgilidadeMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMIN != 0) {
                    jogador.setAgilidade(jogador.getAgilidade() - 1);
                    pontosMAX++;
                    pontosMIN--;
                    jogador.setPontos(pontosMAX);
                    AtualizaStatus(arg);
                }
            }
        });

        binding.btnVidaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMAX != 0) {
                    jogador.setHp(jogador.getHp() + 1);
                    pontosMAX--;
                    pontosMIN++;
                    jogador.setPontos(pontosMAX);
                    AtualizaStatus(arg);
                }
            }
        });

        binding.btnVidaMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMIN != 0) {
                    jogador.setHp(jogador.getHp() - 1);
                    pontosMAX++;
                    pontosMIN--;
                    jogador.setPontos(pontosMAX);
                    AtualizaStatus(arg);
                }
            }
        });

        binding.btnForcaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMAX != 0) {
                    jogador.setForca(jogador.getForca() + 1);
                    pontosMAX--;
                    pontosMIN++;
                    jogador.setPontos(pontosMAX);
                    AtualizaStatus(arg);
                }
            }
        });

        binding.btnForcaMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMIN != 0) {
                    jogador.setForca(jogador.getForca() - 1);
                    pontosMAX++;
                    pontosMIN--;
                    jogador.setPontos(pontosMAX);
                    AtualizaStatus(arg);
                }
            }
        });

        binding.btnEstaminaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMAX != 0) {
                    jogador.setEstamina(jogador.getEstamina() + 1);
                    pontosMAX--;
                    pontosMIN++;
                    jogador.setPontos(pontosMAX);
                    AtualizaStatus(arg);
                }
            }
        });

        binding.btnEstaminaMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMIN != 0) {
                    jogador.setEstamina(jogador.getEstamina() - 1);
                    pontosMAX++;
                    pontosMIN--;
                    jogador.setPontos(pontosMAX);
                    AtualizaStatus(arg);
                }
            }
        });

        jogador.setIdpersonagens(arg.getInt("idPerso"));
        db.personagensDao().upgrade(jogador);
    }


    public void DesbloqueiaAtaques(Akumas akumas){
        AtaqueAkumaNoMi ataqueAkumaNoMi = db.ataqueAkumasDao().buscaAtaqueAkumaNoMi(akumas.getIdataques());
        if(jogador.getQntataquesdesbloqueados() != ataqueAkumaNoMi.getQntataques()) {
            switch (ataqueAkumaNoMi.getQntataques()) {
                case 2:
                    if (jogador.getNivel() >= 50)
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    break;
                case 3:
                    if (jogador.getNivel() >= 50) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    } else if (jogador.getNivel() >= 25) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }
                    break;
                case 4:
                    if (jogador.getNivel() >= 75) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    } else if (jogador.getNivel() >= 50) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }else if (jogador.getNivel() >= 25) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }
                    break;
                case 5:
                    if (jogador.getNivel() >= 100) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    } else if (jogador.getNivel() >= 75) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }else if (jogador.getNivel() >= 50) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }else if (jogador.getNivel() >= 25) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }
                    break;
            }
        }
    }

    public void AtualizaStatus(Bundle arg){
        binding.vida.setText("HP: "+String.valueOf(jogador.getHp()));
        binding.forca.setText("Força: "+String.valueOf(jogador.getForca()));
        binding.estamina.setText("Estamina: "+String.valueOf(jogador.getEstamina()));
        binding.agilidade.setText("Agilidade: "+String.valueOf(jogador.getAgilidade())+"%");
        binding.defesa.setText("Defesa: "+String.valueOf(jogador.getDefesa())+"%");
        binding.intuica.setText("Intuição: "+String.valueOf(jogador.getIntuicao())+"%");
        binding.pontos.setText("Pontos: "+String.valueOf(jogador.getPontos()));

        pontosMAX = jogador.getPontos();
        if(pontosMAX != 0){
            binding.btnEstaminaMais.setVisibility(View.VISIBLE);
            binding.btnEstaminaMenos.setVisibility(View.VISIBLE);

            binding.btnVidaMais.setVisibility(View.VISIBLE);
            binding.btnVidaMenos.setVisibility(View.VISIBLE);

            binding.btnForcaMais.setVisibility(View.VISIBLE);
            binding.btnForcaMenos.setVisibility(View.VISIBLE);

            if(jogador.getAgilidade() < 50) {
                binding.btnAgilidadeMais.setVisibility(View.VISIBLE);
                binding.btnAgilidadeMenos.setVisibility(View.VISIBLE);
            }
            ComtrolePontos(arg);
        }
    }

    public int EncontraNome(List<String> nomes,String busca){
        int i =0;
        for(String nome : nomes){
            if(nome.equals(busca)) return i;
            i++;
        }
        return -1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
