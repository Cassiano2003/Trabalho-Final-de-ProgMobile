package com.example.trabalhofinal.Telas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.trabalhofinal.MainActivity;
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

    int musicaPausada = 0;
    private boolean musicaFrente = true;
    private int pontosMAX;
    private int pontosMIN_vida = 0;
    private int pontosMIN_forca = 0;
    private int pontosMIN_estamina = 0;
    private int pontosMIN_agilidade = 0;
    private Jogador jogador;
    int levelMIN = 0;
    int levelMAX = 0;

    public boolean continuaMusica = true;
    List<Inimigos> inimigosFinal = new ArrayList<>();

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
        tocarMusicaAleatoria();

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
        nomesInimigos.add("Default");


        Bundle arg = getArguments();
        if(arg != null){
            jogador = db.jogadorDao().buscaPer(arg.getInt("idPerso"));
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
            }else {
                binding.nomePersonagens.setSelection(0);
            }


            nivelInimigo = jogador.getNivel();

            if (jogador.getNivel() >= 0){
                binding.getRoot().setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.tela_jogador_0));
                estagio_persangem = 1;
                inimigosFinal.addAll(inimigosE1);
            }if(jogador.getNivel() >= 25){
                binding.getRoot().setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.tela_jogador_25));
                estagio_persangem = 2;
                inimigosFinal.addAll(inimigosE2);
            }if (jogador.getNivel() >= 50){
                binding.getRoot().setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.tela_jogador_50));
                estagio_persangem = 3;
                inimigosFinal.addAll(inimigosE3);
            }if (jogador.getNivel() >= 75){
                binding.getRoot().setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.tela_jogador_75));
                estagio_persangem = 4;
                inimigosFinal.addAll(inimigosE4);
            }if (jogador.getNivel() >= 100){
                binding.getRoot().setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.tela_jogador_100));
                estagio_persangem = 5;
                inimigosFinal.addAll(inimigosE5);
            }
            qnt_ini = inimigosFinal.size();

            if(jogador.getNivel() < 25){
                levelMIN = jogador.getNivel()-10;
                levelMAX = jogador.getNivel()+10;
            }else if(jogador.getNivel() < 50){
                levelMIN = jogador.getNivel()-15;
                levelMAX = jogador.getNivel()+15;
            }else if(jogador.getNivel() < 75){
                levelMIN = jogador.getNivel()-20;
                levelMAX = jogador.getNivel()+20;
            }else if(jogador.getNivel() < 100){
                levelMIN = jogador.getNivel()-25;
                levelMAX = jogador.getNivel()+25;
            }else{
                levelMIN = jogador.getNivel()-30;
                levelMAX = jogador.getNivel()+30;
            }

            binding.nome.setText(jogador.getNome());
            binding.tituloPer.setText(jogador.getTitulo());
            binding.arma.setText("Arma escolhida: "+jogador.getArmas());
            binding.level.setText("LV "+ String.valueOf(jogador.getNivel()));
            binding.raca.setText("Raça: "+jogador.getRaca());
            binding.genero.setText(jogador.getSexo());
            binding.associacao.setText(jogador.getAssociacao());
            binding.recompenca.setText("Recompensa:\n"+jogador.getRecompensa());
            binding.regiao.setText("Região Atual:\n"+jogador.getOrigem());

            binding.rei.setText("Haki do\nRei \n"+String.valueOf(jogador.getHakirei()));
            binding.observacao.setText("Haki da\nObservação \n"+String.valueOf(jogador.getHakiobs()));
            binding.armamento.setText("Haki do\nArmamento \n"+String.valueOf(jogador.getHakiarm()));
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
                    Typeface fonte = ResourcesCompat.getFont(requireContext(), R.font.one_piece_font);
                    novo.setTypeface(fonte);
                    novo.setTextColor(Color.BLACK);
                    novo.setBackgroundColor(Color.parseColor("#33FFFFFF"));
                    novo.setTextSize(30);
                    novo.setText(ataqueAkumaNoMi.getNomeDoAtaque()[i]+" \nTipo: "+ataqueAkumaNoMi.getTipoDeAtaque()[i]
                            +" \n"+ataqueAkumaNoMi.getDescricao()[i]);
                    binding.ataquesLinar.addView(novo);
                }
                binding.akuma.setText("Akuma no Mi escolhida: "+akumas.getNome());
            }else {
                binding.akuma.setText("Akuma no Mi escolhida: Nenhuma");
                binding.ataques.setVisibility(View.INVISIBLE);
                binding.ataquesLinar.setVisibility(View.INVISIBLE);
            }

            Log.d("verifica",inimigos.toString()+" "+String.valueOf(nivelInimigo));
            Log.d("estagio",String.valueOf(estagio_persangem));
            Log.d("estagio Inimigo",String.valueOf(estagio_persangem));
        }

        int finalQnt_ini = qnt_ini;
        binding.batalha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<Inimigos, Integer> dados = GeraInimigos(inimigosFinal, finalQnt_ini);
                Bundle bundle = new Bundle();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View caragamento = inflater.inflate(R.layout.tela_caregamento, null);
                builder.setView(caragamento);

                final AlertDialog tela_caregamento = builder.create();

                ImageView gif = caragamento.findViewById(R.id.gifcaregamento);
                TextView inf_inimigo = caragamento.findViewById(R.id.informacoes_inimigo);
                Button ir = caragamento.findViewById(R.id.ir);
                inf_inimigo.setText(dados.first.getNome()+"  \nNivel: "+String.valueOf(dados.second));
                Glide.with(requireContext()).load(R.drawable.going_merry).into(gif);
                tela_caregamento.show();

                bundle.putInt("idU",arg.getInt("idU"));
                bundle.putInt("idPerso",arg.getInt("idPerso"));
                bundle.putInt("idInimi", dados.first.getIdinimigos());
                bundle.putInt("nivelIni", dados.second);

                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    inf_inimigo.setVisibility(View.VISIBLE);
                    ir.setVisibility(View.VISIBLE);
                }, (random.nextInt(5)+1)*1000);

                ir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        musicaFrente = false;
                        tela_caregamento.dismiss();
                        NavHostFragment.findNavController(InfoJogador.this)
                                .navigate(R.id.action_infoPersonagenUser_to_batalha2,bundle);
                    }
                });
            }
        });

        binding.nomePersonagens.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nome = binding.nomePersonagens.getSelectedItem().toString();
                if(!nome.equals("Default")){
                    String fotoperfio = db.inimigosDao().buscaInimigos(nomesInimigos.get(position));
                    Log.d("Fotoperfio","A foto do "+fotoperfio+" Nome do cidadao "+nomesInimigos.get(position));
                    if (fotoperfio != null) {
                        int resID = requireContext().getResources().getIdentifier(fotoperfio, "drawable", requireContext().getPackageName());
                        if (resID != 0) { // Verifica se o recurso existe
                            binding.foto.setImageResource(resID);
                            jogador.setImagenPersona(nome);
                            jogador.setIdpersonagens(arg.getInt("idPerso"));
                            db.jogadorDao().upgrade(jogador);
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
                    int resID = requireContext().getResources().getIdentifier("padrao", "drawable", requireContext().getPackageName());
                    if (resID != 0) { // Verifica se o recurso existe
                        binding.foto.setImageResource(resID);
                        jogador.setImagenPersona(nome);
                        jogador.setIdpersonagens(arg.getInt("idPerso"));
                        db.jogadorDao().upgrade(jogador);
                    }
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
                    pontosMIN_agilidade++;
                    jogador.setPontos(pontosMAX);
                    binding.btnAgilidadeMenos.setVisibility(View.VISIBLE);
                    AtualizaStatus(arg);
                }
            }
        });

        binding.btnAgilidadeMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMIN_agilidade != 0) {
                    jogador.setAgilidade(jogador.getAgilidade() - 1);
                    pontosMAX++;
                    pontosMIN_agilidade--;
                    jogador.setPontos(pontosMAX);
                    AtualizaStatus(arg);
                }else {
                    binding.btnAgilidadeMenos.setVisibility(View.GONE);
                }
            }
        });

        binding.btnVidaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMAX != 0) {
                    jogador.setHp(jogador.getHp() + 1);
                    pontosMAX--;
                    pontosMIN_vida++;
                    jogador.setPontos(pontosMAX);
                    binding.btnVidaMenos.setVisibility(View.VISIBLE);
                    AtualizaStatus(arg);
                }
            }
        });

        binding.btnVidaMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMIN_vida != 0) {
                    jogador.setHp(jogador.getHp() - 1);
                    pontosMAX++;
                    pontosMIN_vida--;
                    jogador.setPontos(pontosMAX);
                    AtualizaStatus(arg);
                }else {
                    binding.btnVidaMenos.setVisibility(View.GONE);
                }
            }
        });

        binding.btnForcaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMAX != 0) {
                    jogador.setForca(jogador.getForca() + 1);
                    pontosMAX--;
                    pontosMIN_forca++;
                    jogador.setPontos(pontosMAX);
                    binding.btnForcaMenos.setVisibility(View.VISIBLE);
                    AtualizaStatus(arg);
                }
            }
        });

        binding.btnForcaMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMIN_forca != 0) {
                    jogador.setForca(jogador.getForca() - 1);
                    pontosMAX++;
                    pontosMIN_forca--;
                    jogador.setPontos(pontosMAX);
                    AtualizaStatus(arg);
                }else {
                    binding.btnForcaMenos.setVisibility(View.GONE);
                }
            }
        });

        binding.btnEstaminaMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMAX != 0) {
                    jogador.setEstamina(jogador.getEstamina() + 1);
                    pontosMAX--;
                    pontosMIN_estamina++;
                    jogador.setPontos(pontosMAX);
                    binding.btnEstaminaMenos.setVisibility(View.VISIBLE);
                    AtualizaStatus(arg);
                }
            }
        });

        binding.btnEstaminaMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pontosMIN_estamina != 0) {
                    jogador.setEstamina(jogador.getEstamina() - 1);
                    pontosMAX++;
                    pontosMIN_estamina--;
                    jogador.setPontos(pontosMAX);
                    AtualizaStatus(arg);
                }else {
                    binding.btnEstaminaMenos.setVisibility(View.GONE);
                }
            }
        });
    }


    public void DesbloqueiaAtaques(Akumas akumas){
        AtaqueAkumaNoMi ataqueAkumaNoMi = db.ataqueAkumasDao().buscaAtaqueAkumaNoMi(akumas.getIdataques());
        if(jogador.getQntataquesdesbloqueados() != ataqueAkumaNoMi.getQntataques()) {
            switch (ataqueAkumaNoMi.getQntataques()) {
                case 2:
                    if (jogador.getNivel() == 50 && jogador.getQntataquesdesbloqueados() == 1)
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    break;
                case 3:
                    if (jogador.getNivel() == 50 && jogador.getQntataquesdesbloqueados() == 2) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    } else if (jogador.getNivel() == 25 && jogador.getQntataquesdesbloqueados() == 1) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }
                    break;
                case 4:
                    if (jogador.getNivel() == 75 && jogador.getQntataquesdesbloqueados() == 3) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    } else if (jogador.getNivel() == 50 && jogador.getQntataquesdesbloqueados() == 2) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }else if (jogador.getNivel() == 25 && jogador.getQntataquesdesbloqueados() == 1) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }
                    break;
                case 5:
                    if (jogador.getNivel() == 100 && jogador.getQntataquesdesbloqueados() == 4) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    } else if (jogador.getNivel() == 75 && jogador.getQntataquesdesbloqueados() == 3) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }else if (jogador.getNivel() == 50 && jogador.getQntataquesdesbloqueados() == 2) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }else if (jogador.getNivel() == 25 && jogador.getQntataquesdesbloqueados() == 1) {
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
        jogador.setIdpersonagens(arg.getInt("idPerso"));
        db.jogadorDao().upgrade(jogador);

        pontosMAX = jogador.getPontos();
        if(pontosMAX != 0){
            binding.btnEstaminaMais.setVisibility(View.VISIBLE);

            binding.btnVidaMais.setVisibility(View.VISIBLE);

            binding.btnForcaMais.setVisibility(View.VISIBLE);

            if(jogador.getAgilidade() == 50) {
                binding.btnAgilidadeMais.setVisibility(View.GONE);
                binding.btnAgilidadeMenos.setVisibility(View.GONE);
            }else {
                binding.btnAgilidadeMais.setVisibility(View.VISIBLE);
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

    public Pair<Inimigos, Integer> GeraInimigos(List<Inimigos> inimigosFinal, int qnt_ini){
        Random random = new Random();
        Inimigos inimigos;
        do {
            int inimigoAleatorio = random.nextInt(qnt_ini);
            inimigos = inimigosFinal.get(inimigoAleatorio);
        }while (inimigos.getTipo().equals(jogador.getTipo()));

         int nivelInimigo;
        do {
            nivelInimigo = random.nextInt(levelMAX - levelMIN + 1)+levelMIN;
        }while (nivelInimigo < 0);

        return  new Pair<>(inimigos,nivelInimigo);
    }


    public void tocarMusicaAleatoria(){
        int escolhe_musica = new Random().nextInt(9);
        if (((MainActivity) getActivity()).musicaPrincipal) {
            ((MainActivity) getActivity()).musicaPrincipal = false;
            ((MainActivity) getActivity()).mediaPlayer.release();
            switch (escolhe_musica){
                case 0:
                    ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(),R.raw.angry1);
                    ((MainActivity) getActivity()).mediaPlayer.start();
                    break;
                case 1:
                    ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(),R.raw.angry2);
                    ((MainActivity) getActivity()).mediaPlayer.start();
                    break;
                case 2:
                    ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(),R.raw.drums);
                    ((MainActivity) getActivity()).mediaPlayer.start();
                    break;
                case 3:
                    ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(),R.raw.grand_line2);
                    ((MainActivity) getActivity()).mediaPlayer.start();
                    break;
                case 4:
                    ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(),R.raw.karakuri);
                    ((MainActivity) getActivity()).mediaPlayer.start();
                    break;
                case 5:
                    ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(),R.raw.marvelous);
                    ((MainActivity) getActivity()).mediaPlayer.start();
                    break;
                case 6:
                    ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(),R.raw.overtaken);
                    ((MainActivity) getActivity()).mediaPlayer.start();
                    break;
                case 7:
                    ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(),R.raw.serious);
                    ((MainActivity) getActivity()).mediaPlayer.start();
                    break;
                case 8:
                    ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(),R.raw.very_strongest);
                    ((MainActivity) getActivity()).mediaPlayer.start();
                    break;
            }
        }
        if (((MainActivity) getActivity()).mediaPlayer != null) {
            ((MainActivity) getActivity()).mediaPlayer.setOnCompletionListener(mp -> {
                ((MainActivity) getActivity()).musicaPrincipal = true;
                tocarMusicaAleatoria();
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Indicar que este fragmento tem opções de menu para a ActionBar/Toolbar
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Excluir) { // Exemplo de um item de menu
            androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
            dlg.setMessage("Deseja mesmo excluir essa conta?").setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idPerso", getArguments().getInt("idPerso"));
                    bundle.putInt("idU", getArguments().getInt("idU"));
                    Usuario usuario = db.usuarioDao().buscaUsuario(getArguments().getInt("idU"));
                    List<Integer> jogadores = usuario.getPersonagens();
                    jogadores.removeIf(n -> n == jogador.getIdpersonagens());
                    usuario.setPersonagens(jogadores);
                    usuario.setIdUser(getArguments().getInt("idU"));
                    db.usuarioDao().upgrade(usuario);
                    db.jogadorDao().delete(jogador);
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
            dlg.setNeutralButton("Não",null);
            dlg.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_infojogador, menu); // Infla o menu aqui
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(musicaFrente){
            ((MainActivity) getActivity()) .musicaPrincipal = true;
            ((MainActivity) getActivity()) .tocarMusicaAleatoria();
        }else {
            musicaFrente = true;
        }
        binding = null;
    }
}
