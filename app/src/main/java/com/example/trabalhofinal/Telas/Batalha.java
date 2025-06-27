package com.example.trabalhofinal.Telas;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.trabalhofinal.MainActivity;
import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.AtaqueAkumaNoMi;
import com.example.trabalhofinal.Tabelas.Inimigos;
import com.example.trabalhofinal.Tabelas.Jogador;
import com.example.trabalhofinal.Tabelas.Usuario;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.TabelasDao.AtaqueAkumasDao;
import com.example.trabalhofinal.databinding.BatalhaBinding;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Batalha    extends Fragment {
    private BatalhaBinding binding;

    private AppDataBase db;
    private Random random = new Random();

    private int vidaInimigo = 0;
    private int forcaInimigo = 0;
    private int estaminaInimigo = 0;
    private int agilidadeInimigo = 0;
    private int defesaInimigo = 0;
    private int intuicaoInimigo = 0;



    private int vidaJogador = 0;
    private int energiaJogador = 0;

    private int forcajogador = 0;
    private int estaminajogador = 0;
    private int agilidadejogador = 0;
    private int defesajogador = 0;
    private int intuicaojogador = 0;

    private int frutaAntiga = 0;
    private Inimigos inimigos;
    private Jogador jogador;

    private boolean ataquecerteiro = true;

    final boolean[] vezdequem = {true};

    private boolean observacao = false;

    private int ataquesTurnos = 0;

    private int buffTurnos = 0;

    // Variáveis do jogador
    private double hp_jogador, forca_jogador, estamina_jogador, agilidade_jogador, defesa_jogador, intuicao_jogador;
    private int dano_jogador;

    // Variáveis do inimigo
    private double hp_inimigo, forca_inimigo, estamina_inimigo, agilidade_inimigo, defesa_inimigo, intuicao_inimigo;

    // Outros
    private int custo;

    private int nivelAtual;

    private int resucitou = 0;

    private int time = 0;

    private int palha = 0;
    private Typeface fonte;
    private int quantosataques;
    private boolean ataquerei;
    private String[] armaBranca = {
            "Wado Ichimonji",
            "Kitetsu II",
            "Kitetsu III",
            "Meito Shigure",
            "Shusui",
            "Soto Muso",
            "Sukesan e Kakusan",
            "Ame no Habakiri",
            "Enma",
            "Espada",
            "Machado",
            "Garras",
            "Lamina Giratória",
            "Adaga/Faca",
            "Shuriken",
            "Lamina Circular",
            "Lança",
            "Tridente",
            "Halberg",
            "Bisento",
            "Naginata",
            "Guandao",
            "Foice",
            "Yoru",
            "Ace",
            "Gancho",
            "Lamina",
            "Chifres"
    };
    private String[] armaDeFogo = {
            "Arma de Fogo",
            "Arco"
    };
    private String[] laser = {
            "Tiro Laser",
            "Traje Germa"
    };
    private String[] desarmado = {
            "Chicote",
            "Soco",
            "Chute",
            "Cabeçada",
            "Luva de Cera",
            "Luva de Boxe"
    };


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = BatalhaBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isAdded()) {
                    mostrarDialogoDeConfirmacao();
                }
            }
        });
        requireActivity().getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            AppCompatActivity activity = (AppCompatActivity) requireActivity();
            Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            setHasOptionsMenu(true);
        });

        db = AppDataBase.getDataBase(getContext());
        fonte = ResourcesCompat.getFont(requireContext(), R.font.one_piece_font);

        Bundle arg = getArguments();
        if(arg != null) {
            binding.hakiBnt.setVisibility(View.GONE);
            binding.armaBnt.setVisibility(View.GONE);
            binding.akumaBnt.setVisibility(View.GONE);
            nivelAtual = arg.getInt("nivelIni");
            jogador = db.jogadorDao().buscaPer(arg.getInt("idPerso"));
            inimigos = nivelaInimigo(arg.getInt("idInimi"));

            forcajogador = jogador.getForca();
            estaminajogador = jogador.getEstamina();
            agilidadejogador = jogador.getAgilidade();
            defesajogador = jogador.getDefesa();
            intuicaojogador = jogador.getIntuicao();

            forcaInimigo = inimigos.getForca();
            estaminaInimigo = inimigos.getEstamina();
            agilidadeInimigo = inimigos.getAgilidade();
            defesaInimigo = inimigos.getDefesa();
            intuicaoInimigo = inimigos.getIntuicao();

            binding.akumaBnt.setText(db.akumaDao().buscaAkuma(jogador.getAkumaNoMi()).getNome());
            binding.armaBnt.setText(jogador.getArmas());
            vidaInimigo = inimigos.getHp();

            int resID = requireContext().getResources().getIdentifier(inimigos.getFotocombate(), "drawable", getContext().getPackageName());
            binding.imagemInimigo.setImageResource(resID);

            vidaJogador = jogador.getHp();
            energiaJogador = jogador.getEnergia();

            binding.nomeInimigo.setText(inimigos.getNome());
            binding.hakiInimigo.setText("Nivel: "+String.valueOf(arg.getInt("nivelIni"))+"\nARM: "+String.valueOf(inimigos.getHakiarm())+"\nOBS: "+String.valueOf(inimigos.getHakiobs()));
            binding.nomeJogador.setText(jogador.getNome());
            binding.energia.setText("Energia: "+String.valueOf(energiaJogador)+" / "+String.valueOf(jogador.getEnergia()));

            binding.vidaInimigo.setText("Vida Inimigo: \n"+String.valueOf(vidaInimigo)+" / "+String.valueOf(inimigos.getHp()));
            binding.vidaJogador.setText("Vida Jogador: \n"+String.valueOf(vidaJogador)+" / "+String.valueOf(jogador.getHp()));

            binding.vidaJogadorBar.setMax(jogador.getHp());
            binding.vidaJogadorBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
            binding.vidaJogadorBar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#555555")));
            ObjectAnimator animator = ObjectAnimator.ofInt(binding.vidaJogadorBar, "progress", vidaJogador, vidaJogador);
            animator.setDuration(500); // meio segundo
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();


            binding.vidaInimigoBar.setMax(inimigos.getHp());
            binding.vidaInimigoBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
            binding.vidaInimigoBar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#555555")));
            animator = ObjectAnimator.ofInt(binding.vidaInimigoBar, "progress", vidaInimigo, vidaInimigo);
            animator.setDuration(500); // meio segundo
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();

            binding.ennergiaBar.setIndeterminate(false);
            binding.ennergiaBar.setMax(jogador.getEnergia());
            binding.ennergiaBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#26FF00")));
            binding.ennergiaBar.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#555555")));
            animator = ObjectAnimator.ofInt(binding.ennergiaBar, "progress", jogador.getEnergia(), jogador.getEnergia());
            animator.setDuration(500); // meio segundo
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();

            if(jogador.getHakirei() != 0 && jogador.getTurnohakirei() != 0){
                jogador.setTurnohakirei(jogador.getTurnohakirei() - 1);
            }


            if(jogador.getHakiarm() > 0) {
                MediaPlayer media = MediaPlayer.create(requireContext(), R.raw.armament_haki);
                ((MainActivity) getActivity()).mediaPlayer.setVolume(0.25f, 0.25f);
                media.start();
                media.setOnCompletionListener(mp -> {
                    mp.release();
                    ((MainActivity) getActivity()).mediaPlayer.setVolume(1, 1);
                    decideAtaque(arg);
                });
            }else{
                decideAtaque(arg);
            }



            binding.armaBnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.hakiBnt.setVisibility(View.GONE);
                    binding.armaBnt.setVisibility(View.GONE);
                    binding.akumaBnt.setVisibility(View.GONE);
                    if(vezdequem[0]) {
                        ((MainActivity) getActivity()).mediaPlayer.setVolume(0.25f, 0.25f);
                        MediaPlayer media = MediaPlayer.create(requireContext(), R.raw.bonk);
                        if (buscaPalavra(armaBranca, jogador.getArmas())) {
                            if (random.nextInt(2) == 0) {
                                media = MediaPlayer.create(requireContext(), R.raw.espada1);
                            } else {
                                media = MediaPlayer.create(requireContext(), R.raw.espada2);
                            }
                        } else if (buscaPalavra(armaDeFogo, jogador.getArmas())) {
                            media = MediaPlayer.create(requireContext(), R.raw.arma);
                        } else if (buscaPalavra(laser, jogador.getArmas())) {
                            media = MediaPlayer.create(requireContext(), R.raw.laser);
                        } else if (buscaPalavra(desarmado, jogador.getArmas())) {
                            if (random.nextInt(2) == 0) {
                                media = MediaPlayer.create(requireContext(), R.raw.soco1);
                            } else {
                                media = MediaPlayer.create(requireContext(), R.raw.soco2);
                            }
                        } else if (jogador.getArmas().equals("Kuro Kabuto")) {
                            media = MediaPlayer.create(requireContext(), R.raw.estilingue);
                        }
                        media.start();
                        media.setOnCompletionListener(mp -> {
                            ((MainActivity) getActivity()).mediaPlayer.setVolume(1, 1);
                            TurnoJogador(arg, forcajogador, jogador.getArmas());
                            mp.release();
                        });
                    }
                }
            });

            binding.akumaBnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Akumas akumas = db.akumaDao().buscaAkuma(jogador.getAkumaNoMi());
                    AtaqueAkumaNoMi ataqueAkumaNoMi = db.ataqueAkumasDao().buscaAtaqueAkumaNoMi(akumas.getIdataques());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View botoes = inflater.inflate(R.layout.combate_botoes, null);
                    builder.setView(botoes);

                    final AlertDialog ataques = builder.create();

                    Button ataque1 = botoes.findViewById(R.id.ataque1);
                    Button ataque2 = botoes.findViewById(R.id.ataque2);
                    Button ataque3 = botoes.findViewById(R.id.ataque3);
                    Button ataque4 = botoes.findViewById(R.id.ataque4);
                    Button ataque5 = botoes.findViewById(R.id.ataque5);

                    TextView descricao1 = botoes.findViewById(R.id.descricao_ataque1);
                    TextView descricao2 = botoes.findViewById(R.id.descricao_ataque2);
                    TextView descricao3 = botoes.findViewById(R.id.descricao_ataque3);
                    TextView descricao4 = botoes.findViewById(R.id.descricao_ataque4);
                    TextView descricao5 = botoes.findViewById(R.id.descricao_ataque5);
                    MediaPlayer media = MediaPlayer.create(requireContext(),R.raw.akuma);
                    if(jogador.getQntataquesdesbloqueados() >= 1) {
                        ataque1.setVisibility(View.VISIBLE);
                        descricao1.setVisibility(View.VISIBLE);
                        ataque1.setText(ataqueAkumaNoMi.getNomeDoAtaque()[0]);
                        descricao1.setText(ataqueAkumaNoMi.getTipoDeAtaque()[0] + " \n"+"Custo: "+ataqueAkumaNoMi.getCusto()[0]);
                        ataque1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(jogador.getEnergia() >= ataqueAkumaNoMi.getCusto()[0]) {
                                    ((MainActivity) getActivity()) .mediaPlayer.setVolume(0.25f,0.25f);
                                    media.start();
                                    binding.hakiBnt.setVisibility(View.GONE);
                                    binding.armaBnt.setVisibility(View.GONE);
                                    binding.akumaBnt.setVisibility(View.GONE);
                                    media.setOnCompletionListener(mp -> {
                                        ((MainActivity) getActivity()) .mediaPlayer.setVolume(1,1);
                                        AtaqueAkuma(ataqueAkumaNoMi, 0, ataqueAkumaNoMi.getTipoDeAtaque()[0], arg,true);
                                        mp.release();
                                    });
                                }else{
                                    Toast.makeText(requireContext(), "Jogador sem Energia", Toast.LENGTH_LONG).show();
                                }
                                ataques.dismiss();
                            }
                        });
                    }if(jogador.getQntataquesdesbloqueados() >= 2) {
                        ataque2.setVisibility(View.VISIBLE);
                        descricao2.setVisibility(View.VISIBLE);
                        ataque2.setText(ataqueAkumaNoMi.getNomeDoAtaque()[1]);
                        descricao2.setText(ataqueAkumaNoMi.getTipoDeAtaque()[1] + " \n"+"Custo: "+ataqueAkumaNoMi.getCusto()[1]);
                        ataque2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(jogador.getEnergia() >= ataqueAkumaNoMi.getCusto()[1]) {
                                    ((MainActivity) getActivity()) .mediaPlayer.setVolume(0.25f,0.25f);
                                    media.start();
                                    binding.hakiBnt.setVisibility(View.GONE);
                                    binding.armaBnt.setVisibility(View.GONE);
                                    binding.akumaBnt.setVisibility(View.GONE);
                                    media.setOnCompletionListener(mp -> {
                                        ((MainActivity) getActivity()) .mediaPlayer.setVolume(1,1);
                                        AtaqueAkuma(ataqueAkumaNoMi, 1, ataqueAkumaNoMi.getTipoDeAtaque()[1], arg,true);
                                        mp.release();
                                    });
                                }else{
                                    Toast.makeText(requireContext(), "Jogador sem Energia", Toast.LENGTH_LONG).show();
                                }
                                ataques.dismiss();
                            }
                        });
                    }if(jogador.getQntataquesdesbloqueados() >= 3) {
                        ataque3.setVisibility(View.VISIBLE);
                        descricao3.setVisibility(View.VISIBLE);
                        ataque3.setText(ataqueAkumaNoMi.getNomeDoAtaque()[2]);
                        descricao3.setText(ataqueAkumaNoMi.getTipoDeAtaque()[2] + " \n"+"Custo: "+ataqueAkumaNoMi.getCusto()[2]);
                        ataque3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(jogador.getEnergia() >= ataqueAkumaNoMi.getCusto()[2]) {
                                    ((MainActivity) getActivity()) .mediaPlayer.setVolume(0.25f,0.25f);
                                    media.start();
                                    binding.hakiBnt.setVisibility(View.GONE);
                                    binding.armaBnt.setVisibility(View.GONE);
                                    binding.akumaBnt.setVisibility(View.GONE);
                                    media.setOnCompletionListener(mp -> {
                                        ((MainActivity) getActivity()) .mediaPlayer.setVolume(1,1);
                                        AtaqueAkuma(ataqueAkumaNoMi, 2, ataqueAkumaNoMi.getTipoDeAtaque()[2], arg,true);
                                        mp.release();
                                    });
                                }else{
                                    Toast.makeText(requireContext(), "Jogador sem Energia", Toast.LENGTH_LONG).show();
                                }
                                ataques.dismiss();
                            }
                        });
                    }if(jogador.getQntataquesdesbloqueados() >= 4) {
                        ataque4.setVisibility(View.VISIBLE);
                        descricao4.setVisibility(View.VISIBLE);
                        ataque4.setText(ataqueAkumaNoMi.getNomeDoAtaque()[3]);
                        descricao4.setText(ataqueAkumaNoMi.getTipoDeAtaque()[3] + " \n"+"Custo: "+ataqueAkumaNoMi.getCusto()[3]);
                        ataque4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(jogador.getEnergia() >= ataqueAkumaNoMi.getCusto()[3]) {
                                    ((MainActivity) getActivity()) .mediaPlayer.setVolume(0.25f,0.25f);
                                    media.start();
                                    binding.hakiBnt.setVisibility(View.GONE);
                                    binding.armaBnt.setVisibility(View.GONE);
                                    binding.akumaBnt.setVisibility(View.GONE);
                                    media.setOnCompletionListener(mp -> {
                                        ((MainActivity) getActivity()) .mediaPlayer.setVolume(1,1);
                                        AtaqueAkuma(ataqueAkumaNoMi, 3, ataqueAkumaNoMi.getTipoDeAtaque()[3], arg,true);
                                        mp.release();
                                    });
                                }else{
                                    Toast.makeText(requireContext(), "Jogador sem Energia", Toast.LENGTH_LONG).show();
                                }
                                ataques.dismiss();
                            }
                        });
                    }if(jogador.getQntataquesdesbloqueados() >= 5) {
                        ataque5.setVisibility(View.VISIBLE);
                        descricao5.setVisibility(View.VISIBLE);
                        ataque5.setText(ataqueAkumaNoMi.getNomeDoAtaque()[4]);
                        descricao5.setText(ataqueAkumaNoMi.getTipoDeAtaque()[4] + " \n"+"Custo: "+ataqueAkumaNoMi.getCusto()[4]);
                        ataque5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(jogador.getEnergia() >= ataqueAkumaNoMi.getCusto()[4]) {
                                    ((MainActivity) getActivity()) .mediaPlayer.setVolume(0.25f,0.25f);
                                    media.start();
                                    binding.hakiBnt.setVisibility(View.GONE);
                                    binding.armaBnt.setVisibility(View.GONE);
                                    binding.akumaBnt.setVisibility(View.GONE);
                                    ((MainActivity) getActivity()) .mediaPlayer.setVolume(1,1);
                                    media.setOnCompletionListener(mp -> {
                                        AtaqueAkuma(ataqueAkumaNoMi, 4, ataqueAkumaNoMi.getTipoDeAtaque()[4], arg,true);
                                        mp.release();
                                    });
                                }else{
                                    Toast.makeText(requireContext(), "Jogador sem Energia", Toast.LENGTH_LONG).show();
                                }
                                ataques.dismiss();
                            }
                        });
                    }

                    ataques.show();
                }
            });

            binding.hakiBnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.hakiBnt.setVisibility(View.GONE);
                    binding.armaBnt.setVisibility(View.GONE);
                    binding.akumaBnt.setVisibility(View.GONE);
                    ((MainActivity) getActivity()).mediaPlayer.setVolume(0.25f, 0.25f);
                    MediaPlayer media = MediaPlayer.create(requireContext(), R.raw.haki_rei);
                    int hakiRei = jogador.getHakirei();
                    int hakiArm = inimigos.getHakiarm();
                    double fator = 0;

                    media.start();

                    switch (hakiRei) {
                        case 1:
                            if (hakiArm == 0 && validaChance(75)) fator = 0.20;
                            else if (hakiArm == 1 && validaChance(50)) fator = 0.10;
                            break;
                        case 2:
                            if (hakiArm == 0) fator = 0.30;
                            else if (hakiArm == 1 && validaChance(75)) fator = 0.20;
                            else if (hakiArm == 2 && validaChance(50)) fator = 0.10;
                            break;
                        case 3:
                            if (hakiArm == 0) {
                                ObjectAnimator animator = ObjectAnimator.ofInt(binding.vidaInimigoBar, "progress", vidaInimigo, 0);
                                animator.setDuration(500);
                                animator.setInterpolator(new DecelerateInterpolator());
                                animator.start();
                                vidaInimigo = 0;
                                binding.titulo.setText("Combate");
                                binding.vidaInimigo.setText("Vida Inimigo: \n" + vidaInimigo + " / " + inimigos.getHp());

                                new AlertDialog.Builder(getContext())
                                    .setMessage("VITÓRIA!!\nPontos de Experiência foram adquiridos.\nRetorne ao perfil do seu personagem para distribuir esses pontos.")
                                    .setPositiveButton("OK", (dialog, which) -> {
                                        dialog.dismiss();
                                        Nivelamento(jogador, arg);
                                    }).show();

                                binding.armaBnt.setVisibility(View.GONE);
                                binding.hakiBnt.setVisibility(View.GONE);
                                binding.akumaBnt.setVisibility(View.GONE);

                                break;
                            } else if (hakiArm == 1) fator = 0.30;
                            else if (hakiArm == 2 && validaChance(75)) fator = 0.20;
                            else if (hakiArm == 3 && validaChance(50)) fator = 0.10;
                            break;
                    }

                    if (fator > 0) {
                        forcaInimigo = ((int)(forcaInimigo - forcaInimigo * fator));
                        estaminaInimigo = ((int)(estaminaInimigo - estaminaInimigo * fator));

                        exibeMensagem(inimigos.getNome() + " teve sua força diminuída em " + (int)(fator * 100) + "%");
                    } else if (hakiRei != 3 || hakiArm != 0) {
                        exibeMensagem(jogador.getNome() + " nao conseguiu diminuir a força do inimigo");
                    }

                    media.setOnCompletionListener(mp -> {
                        mp.release();
                        ((MainActivity) getActivity()).mediaPlayer.setVolume(1, 1);
                        jogador.setTurnohakirei(5);
                        jogador.setIdpersonagens(arg.getInt("idPerso"));
                        db.jogadorDao().upgrade(jogador);
                        binding.hakiBnt.setVisibility(View.GONE);
                        GeraAtaqueInimigo(arg);
                    });
                }
            });

            jogador.setIdpersonagens(arg.getInt("idPerso"));
            db.jogadorDao().upgrade(jogador);
        }
    }


    public void TurnoJogador(Bundle arg,int ValorBrutoDano,String nomeataque){
        int dano = CalculaDano(defesaInimigo, ValorBrutoDano);
        MediaPlayer media = MediaPlayer.create(requireContext(),R.raw.observation_haki);
        Buff();
        if(palha == 0) {
            if (validaChance(intuicaoInimigo) && ataquecerteiro) {
                ((MainActivity) getActivity()) .mediaPlayer.setVolume(0.25f,0.25f);
                media.start();
                observacao = true;
                ataquesTurnos++;
                exibeMensagem(inimigos.getNome() + " desviou do seu ataque");
            } else {
                ataquesTurnos++;
                exibeMensagem(jogador.getNome() + " atacou com " + nomeataque + " / Dano:" + String.valueOf(dano));
                if (VerficaVida(vidaInimigo - dano,inimigos.getIdakuma(),true)) {
                    ObjectAnimator animator = ObjectAnimator.ofInt(binding.vidaInimigoBar, "progress", vidaInimigo, 0);
                    animator.setDuration(500); // meio segundo
                    animator.setInterpolator(new DecelerateInterpolator());
                    animator.start();
                    vidaInimigo = 0;
                    binding.titulo.setText("Combate");
                    binding.vidaInimigo.setText("Vida Inimigo: \n" + String.valueOf(vidaInimigo) + " / " + String.valueOf(inimigos.getHp()));
                    androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                    dlg.setMessage("VITÓRIA!!\nPontos de Experiência foram adquiridos.\nRetorne ao perfil do seu personagem para distribuir esses pontos.");
                    dlg.setPositiveButton("OK", (dialog, which) -> {
                        dialog.dismiss(); // Fecha o diálogo
                        Nivelamento(jogador, arg);
                    });
                    dlg.show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("idU", arg.getInt("idU"));
                    bundle.putInt("idPerso", arg.getInt("idPerso"));
                    binding.armaBnt.setVisibility(View.GONE);
                    binding.hakiBnt.setVisibility(View.GONE);
                    binding.akumaBnt.setVisibility(View.GONE);
                    return;
                } else {
                    ObjectAnimator animator = ObjectAnimator.ofInt(binding.vidaInimigoBar, "progress", vidaInimigo, vidaInimigo - dano);
                    animator.setDuration(500); // meio segundo
                    animator.setInterpolator(new DecelerateInterpolator());
                    animator.start();
                    vidaInimigo = vidaInimigo - dano;
                    binding.vidaInimigo.setText("Vida Inimigo: \n" + String.valueOf(vidaInimigo) + " / " + String.valueOf(inimigos.getHp()));
                }
            }
        }else if(palha == -1) {
            dano = CalculaDano(defesajogador,ValorBrutoDano);
            palha = 0;
            ataquesTurnos++;
            exibeMensagem(jogador.getNome() + " atacou com " + nomeataque + " / Dano:" + String.valueOf(dano));
            if (VerficaVida(vidaJogador - dano,jogador.getAkumaNoMi(),false)) {
                ObjectAnimator animator = ObjectAnimator.ofInt(binding.vidaJogadorBar, "progress", vidaJogador, 0);
                animator.setDuration(500); // meio segundo
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
                vidaJogador = 0;
                binding.titulo.setText("Combate");
                binding.vidaJogador.setText("Vida Jogador: \n" + String.valueOf(vidaJogador) + " / " + String.valueOf(jogador.getHp()));
                androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                dlg.setMessage("DERROTA... \nRetorne ao perfil do seu personagem e tente novamente.");
                dlg.setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss(); // Fecha o diálogo
                });
                dlg.show();
                Bundle bundle = new Bundle();
                bundle.putInt("idU", arg.getInt("idU"));
                bundle.putInt("idPerso", arg.getInt("idPerso"));
                binding.armaBnt.setVisibility(View.GONE);
                binding.hakiBnt.setVisibility(View.GONE);
                binding.akumaBnt.setVisibility(View.GONE);
                return;
            } else {
                ObjectAnimator animator = ObjectAnimator.ofInt(binding.vidaJogadorBar, "progress", vidaJogador, vidaJogador - dano);
                animator.setDuration(500); // meio segundo
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
                vidaJogador = vidaJogador - dano;
                binding.vidaJogador.setText("Vida Jogador: \n" + String.valueOf(vidaJogador) + " / " + String.valueOf(jogador.getHp()));
            }
        }
        if(observacao) {
            observacao = false;
            media.setOnCompletionListener(mp -> {
                ((MainActivity) getActivity()) .mediaPlayer.setVolume(1,1);
                mp.release();
                if (validaChance(jogador.getAgilidade()) && ataquesTurnos < 2) {
                    liberaBotoes();
                    ataquesTurnos++;
                    binding.titulo.setText("Vez " + jogador.getNome());
                    androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                    dlg.setMessage(jogador.getNome() + " foi mais agil que " + inimigos.getNome() + " e conseguiu mais uma oportunidade de ataque.");
                    dlg.show();
                } else {
                    binding.hakiBnt.setVisibility(View.GONE);
                    binding.armaBnt.setVisibility(View.GONE);
                    binding.akumaBnt.setVisibility(View.GONE);
                    ataquesTurnos = 0;
                    vezdequem[0] = false;
                    binding.titulo.setText("Vez " + inimigos.getNome());
                    GeraAtaqueInimigo(arg);
                }
            });
        }else{
            if (validaChance(jogador.getAgilidade()) && ataquesTurnos < 2) {
                liberaBotoes();
                ataquesTurnos++;
                binding.titulo.setText("Vez " + jogador.getNome());
                androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                dlg.setMessage(jogador.getNome() + " foi mais agil que " + inimigos.getNome() + " e conseguiu mais uma oportunidade de ataque.");
                dlg.show();
            } else {
                binding.hakiBnt.setVisibility(View.GONE);
                binding.armaBnt.setVisibility(View.GONE);
                binding.akumaBnt.setVisibility(View.GONE);
                ataquesTurnos = 0;
                vezdequem[0] = false;
                binding.titulo.setText("Vez " + inimigos.getNome());
                GeraAtaqueInimigo(arg);
            }
        }
    }

    public void TurnoInimigo(Bundle arg,int ValorBrutoDano,String nomeataque){
        int dano = CalculaDano(defesajogador, ValorBrutoDano);
        MediaPlayer media = MediaPlayer.create(requireContext(),R.raw.observation_haki);
        Buff();
        if(palha == 0) {
            if (validaChance(intuicaojogador) && ataquecerteiro) {
                ((MainActivity) getActivity()) .mediaPlayer.setVolume(0.25f,0.25f);
                media.start();
                observacao = true;
                exibeMensagem(jogador.getNome() + " desviou do ataque de " + inimigos.getNome());
                ataquesTurnos++;
            } else {
                ataquesTurnos++;
                exibeMensagem(inimigos.getNome() + " atacou com " + nomeataque + " / Dano:" + String.valueOf(dano));
                if (VerficaVida(vidaJogador - dano,jogador.getAkumaNoMi(),false)) {
                    ObjectAnimator animator = ObjectAnimator.ofInt(binding.vidaJogadorBar, "progress", vidaJogador, 0);
                    animator.setDuration(500); // meio segundo
                    animator.setInterpolator(new DecelerateInterpolator());
                    animator.start();
                    vidaJogador = 0;
                    binding.titulo.setText("Combate");
                    binding.vidaJogador.setText("Vida Jogador: \n" + String.valueOf(vidaJogador) + " / " + String.valueOf(jogador.getHp()));
                    androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                    dlg.setMessage("DERROTA... \nRetorne ao perfil do seu personagem e tente novamente.");
                    dlg.setPositiveButton("OK", (dialog, which) -> {
                        dialog.dismiss(); // Fecha o diálogo
                    });
                    dlg.show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("idU", arg.getInt("idU"));
                    bundle.putInt("idPerso", arg.getInt("idPerso"));
                    binding.armaBnt.setVisibility(View.GONE);
                    binding.hakiBnt.setVisibility(View.GONE);
                    binding.akumaBnt.setVisibility(View.GONE);
                    return;
                } else {
                    ObjectAnimator animator = ObjectAnimator.ofInt(binding.vidaJogadorBar, "progress", vidaJogador, vidaJogador - dano);
                    animator.setDuration(500); // meio segundo
                    animator.setInterpolator(new DecelerateInterpolator());
                    animator.start();
                    vidaJogador = vidaJogador - dano;
                    binding.vidaJogador.setText("Vida Jogador: \n" + String.valueOf(vidaJogador) + " / " + String.valueOf(jogador.getHp()));
                }
            }
        }else if(palha == 1){
            dano = CalculaDano(defesaInimigo,ValorBrutoDano);
            palha = 0;
            ataquesTurnos++;
            exibeMensagem(inimigos.getNome() + " atacou com " + nomeataque + " / Dano:" + String.valueOf(dano));
            if (VerficaVida(vidaInimigo - dano,inimigos.getIdakuma(),true)) {
                ObjectAnimator animator = ObjectAnimator.ofInt(binding.vidaInimigoBar, "progress", vidaInimigo, 0);
                animator.setDuration(500); // meio segundo
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
                vidaInimigo = 0;
                binding.titulo.setText("Combate");
                binding.vidaInimigo.setText("Vida Inimigo: \n" + String.valueOf(vidaInimigo) + " / " + String.valueOf(inimigos.getHp()));
                androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                dlg.setMessage("VITÓRIA!!\nPontos de Experiência foram adquiridos.\nRetorne ao perfil do seu personagem para distribuir esses pontos.");
                dlg.setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss(); // Fecha o diálogo
                    Nivelamento(jogador, arg);
                });
                dlg.show();
                Bundle bundle = new Bundle();
                bundle.putInt("idU", arg.getInt("idU"));
                bundle.putInt("idPerso", arg.getInt("idPerso"));
                binding.armaBnt.setVisibility(View.GONE);
                binding.hakiBnt.setVisibility(View.GONE);
                binding.akumaBnt.setVisibility(View.GONE);
                return;
            } else {
                ObjectAnimator animator = ObjectAnimator.ofInt(binding.vidaInimigoBar, "progress", vidaInimigo, vidaInimigo - dano);
                animator.setDuration(500); // meio segundo
                animator.setInterpolator(new DecelerateInterpolator());
                animator.start();
                vidaInimigo = vidaInimigo - dano;
                binding.vidaInimigo.setText("Vida Inimigo: \n" + String.valueOf(vidaInimigo) + " / " + String.valueOf(inimigos.getHp()));
            }
        }
        if(observacao) {
            observacao = false;
            media.setOnCompletionListener(mp -> {
                ((MainActivity) getActivity()) .mediaPlayer.setVolume(1,1);
                mp.release();
                if (validaChance(inimigos.getAgilidade()) && ataquesTurnos < 2) {
                    ataquesTurnos++;
                    binding.titulo.setText("Vez " + inimigos.getNome());
                    GeraAtaqueInimigo(arg);
                } else {
                    liberaBotoes();
                    binding.titulo.setText("Vez " + jogador.getNome());
                    vezdequem[0] = true;
                }
            });
        }else {
            if (validaChance(inimigos.getAgilidade()) && ataquesTurnos < 2) {
                ataquesTurnos++;
                binding.titulo.setText("Vez " + inimigos.getNome());
                GeraAtaqueInimigo(arg);
            } else {
                liberaBotoes();
                binding.titulo.setText("Vez " + jogador.getNome());
                vezdequem[0] = true;
            }
        }
    }


    private int aplicaBonus(int base, double bonus) {
        return (int) (base + (base * bonus));
    }

    public void AtaqueAkuma(AtaqueAkumaNoMi ataqueAkumaNoMi, int qualataque, String tipoAtaque, Bundle arg, boolean quem_e) {
    // Atribui valores dos vetores (conversão de int para double percentual)
    custo = ataqueAkumaNoMi.getCusto()[qualataque];

    hp_jogador = ataqueAkumaNoMi.getHpjogador()[qualataque] / 100.0;
    forca_jogador = ataqueAkumaNoMi.getForcajogador()[qualataque] / 100.0;
    estamina_jogador = ataqueAkumaNoMi.getEstaminajogador()[qualataque] / 100.0;
    agilidade_jogador = ataqueAkumaNoMi.getAgilidadejogador()[qualataque] / 100.0;
    defesa_jogador = ataqueAkumaNoMi.getDefesajogador()[qualataque] / 100.0;
    intuicao_jogador = ataqueAkumaNoMi.getIntuicaojogador()[qualataque] / 100.0;
    dano_jogador = ataqueAkumaNoMi.getDanojogador()[qualataque];

    hp_inimigo = ataqueAkumaNoMi.getHpinimigo()[qualataque] / 100.0;
    forca_inimigo = ataqueAkumaNoMi.getForcainimigo()[qualataque] / 100.0;
    estamina_inimigo = ataqueAkumaNoMi.getEstaminainimigo()[qualataque] / 100.0;
    agilidade_inimigo = ataqueAkumaNoMi.getAgilidadeinimigo()[qualataque] / 100.0;
    defesa_inimigo = ataqueAkumaNoMi.getDefesainimigo()[qualataque] / 100.0;
    intuicao_inimigo = ataqueAkumaNoMi.getIntuicaoinimigo()[qualataque] / 100.0;

    // Aplica custo de energia
    if (quem_e) {
        ObjectAnimator animator = ObjectAnimator.ofInt(binding.ennergiaBar, "progress", jogador.getEnergia(), jogador.getEnergia() - custo);
        animator.setDuration(100);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        jogador.setEnergia(jogador.getEnergia() - custo);
        binding.energia.setText("Energia: " + jogador.getEnergia() + " / " + energiaJogador);
    } else {
        inimigos.setEnergia(inimigos.getEnergia() - custo);
    }

    // Verifica tipo de ataque
    switch (tipoAtaque) {
        case "ATAQUE SIMPLES":
            if (quem_e)
                TurnoJogador(arg, energiaJogador, ataqueAkumaNoMi.getNomeDoAtaque()[qualataque]);
            else
                TurnoInimigo(arg, estaminaInimigo, ataqueAkumaNoMi.getNomeDoAtaque()[qualataque]);
            break;

        case "ATAQUE FORTE":
            if (quem_e)
                TurnoJogador(arg, energiaJogador* 2, ataqueAkumaNoMi.getNomeDoAtaque()[qualataque]);
            else
                TurnoInimigo(arg, estaminaInimigo * 2, ataqueAkumaNoMi.getNomeDoAtaque()[qualataque]);
            break;

        case "ATAQUE FORTE 2":
            if (quem_e)
                TurnoJogador(arg, energiaJogador * 3, ataqueAkumaNoMi.getNomeDoAtaque()[qualataque]);
            else
                TurnoInimigo(arg, estaminaInimigo * 3, ataqueAkumaNoMi.getNomeDoAtaque()[qualataque]);
            break;

        case "ESPECIAL":
            if (dano_jogador == -1) {
                palha = quem_e ? 1 : -1;
                if (quem_e)
                    TurnoJogador(arg, 0, ataqueAkumaNoMi.getNomeDoAtaque()[qualataque]);
                else
                    TurnoInimigo(arg, 0, ataqueAkumaNoMi.getNomeDoAtaque()[qualataque]);
            } else {
                if (quem_e)
                    TurnoJogador(arg, energiaJogador* 4, ataqueAkumaNoMi.getNomeDoAtaque()[qualataque]);
                else
                    TurnoInimigo(arg, estaminaInimigo * 4, ataqueAkumaNoMi.getNomeDoAtaque()[qualataque]);
            }
            break;

        default:
            Log.d("buff para", inimigos.getNome());
            buffTurnos++;

            if (dano_jogador == 3) {
                // Troca de fruta
                if (quem_e) {
                    frutaAntiga = jogador.getAkumaNoMi();
                    jogador.setAkumaNoMi(random.nextInt(db.akumaDao().quantosAkumas()) + 1);
                    binding.akumaBnt.setText(db.akumaDao().buscaAkuma(jogador.getAkumaNoMi()).getNome());
                    Usuario usuario = db.usuarioDao().buscaUsuario(arg.getInt("idU"));
                    List<Integer> akumas = usuario.getAkumanomis();
                    if (!procuraRepeticao(akumas, jogador.getAkumaNoMi()))
                        akumas.add(jogador.getAkumaNoMi());
                    usuario.setAkumanomis(akumas);
                    usuario.setIdUser(arg.getInt("idU"));
                    db.usuarioDao().upgrade(usuario);
                    DesbloqueiaAtaques(db.akumaDao().buscaAkuma(jogador.getAkumaNoMi()));
                } else {
                    inimigos.setIdakuma(random.nextInt(db.akumaDao().quantosAkumastotal()) + 1);
                }
            } else if (dano_jogador == 2) {
                ataquecerteiro = false;
            } else if (forca_jogador == 2.0) {
                // Super buff jogador
                vidaJogador = (aplicaBonus(vidaJogador, hp_jogador));
                forcajogador = (forcajogador * 2);
                estaminajogador = (aplicaBonus(estaminajogador, estamina_jogador));
                agilidadejogador = (aplicaBonus(agilidadejogador, agilidade_jogador));
                defesajogador = (aplicaBonus(defesajogador, defesa_jogador));
                intuicaojogador = (aplicaBonus(intuicaojogador, intuicao_jogador));

                vidaInimigo = (aplicaBonus(vidaInimigo, hp_inimigo));
                forcaInimigo = (aplicaBonus(forcaInimigo, forca_inimigo));
                estaminaInimigo = (aplicaBonus(estaminaInimigo, estamina_inimigo));
                agilidadeInimigo = (aplicaBonus(agilidadeInimigo, agilidade_inimigo));
                defesaInimigo = (aplicaBonus(defesaInimigo, defesa_inimigo));
                intuicaoInimigo = (aplicaBonus(intuicaoInimigo, intuicao_inimigo));
            } else {
                // Buff padrão
                vidaJogador = (aplicaBonus(vidaJogador, hp_jogador));
                forcajogador = (aplicaBonus(forcajogador, forca_jogador));
                estaminajogador = (aplicaBonus(estaminajogador, estamina_jogador));
                agilidadejogador = (aplicaBonus(agilidadejogador, agilidade_jogador));
                defesajogador = (aplicaBonus(defesajogador, defesa_jogador));
                intuicaojogador = (aplicaBonus(intuicaojogador, intuicao_jogador));

                vidaInimigo = (aplicaBonus(vidaInimigo, hp_inimigo));
                forcaInimigo = (aplicaBonus(forcaInimigo, forca_inimigo));
                estaminaInimigo = (aplicaBonus(estaminaInimigo, estamina_inimigo));
                agilidadeInimigo = (aplicaBonus(agilidadeInimigo, agilidade_inimigo));
                defesaInimigo = (aplicaBonus(defesaInimigo, defesa_inimigo));
                intuicaoInimigo = (aplicaBonus(intuicaoInimigo, intuicao_inimigo));
            }

            // Feedback visual e lógica de turno
            TextView novo = new TextView(getContext());
            novo.setTextColor(Color.BLACK);
            novo.setTypeface(fonte);
            novo.setTextSize(20);
            novo.setText((quem_e ? jogador.getNome() : inimigos.getNome()) + " usou o buff " + ataqueAkumaNoMi.getNomeDoAtaque()[qualataque]);
            binding.informacoes.addView(novo);

            ObjectAnimator animator = ObjectAnimator.ofInt(binding.vidaInimigoBar, "progress", inimigos.getHp(), vidaInimigo);
            animator.setDuration(500); // meio segundo
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
            binding.vidaInimigo.setText("Vida Inimigo: \n" + String.valueOf(vidaInimigo) + " / " + String.valueOf(inimigos.getHp()));
            animator = ObjectAnimator.ofInt(binding.vidaJogadorBar, "progress", jogador.getHp(), vidaJogador);
            animator.setDuration(500); // meio segundo
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
            binding.vidaJogador.setText("Vida Inimigo: \n" + String.valueOf(vidaJogador) + " / " + String.valueOf(jogador.getHp()));

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (quem_e) {
                    if (VerficaVida(vidaInimigo,inimigos.getIdakuma(),true)) {
                        ObjectAnimator animator1 = ObjectAnimator.ofInt(binding.vidaInimigoBar, "progress", vidaInimigo, 0);
                        animator1.setDuration(500); // meio segundo
                        animator1.setInterpolator(new DecelerateInterpolator());
                        animator1.start();
                        vidaInimigo = 0;
                        binding.titulo.setText("Combate");
                        binding.vidaInimigo.setText("Vida Inimigo: \n" + String.valueOf(vidaInimigo) + " / " + String.valueOf(inimigos.getHp()));
                        androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                        dlg.setMessage("VITÓRIA!!\nPontos de Experiência foram adquiridos.\nRetorne ao perfil do seu personagem para distribuir esses pontos.");
                        dlg.setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss(); // Fecha o diálogo
                            Nivelamento(jogador, arg);
                        });
                        dlg.show();
                        Bundle bundle = new Bundle();
                        bundle.putInt("idU", arg.getInt("idU"));
                        bundle.putInt("idPerso", arg.getInt("idPerso"));
                        binding.armaBnt.setVisibility(View.GONE);
                        binding.hakiBnt.setVisibility(View.GONE);
                        binding.akumaBnt.setVisibility(View.GONE);
                        return;
                    }
                    binding.titulo.setText("Vez " + inimigos.getNome());
                    GeraAtaqueInimigo(arg);
                } else {
                    if (VerficaVida(vidaJogador,jogador.getAkumaNoMi(),false)) {
                        ObjectAnimator animator2 = ObjectAnimator.ofInt(binding.vidaJogadorBar, "progress", vidaJogador, 0);
                        animator2.setDuration(500); // meio segundo
                        animator2.setInterpolator(new DecelerateInterpolator());
                        animator2.start();
                        vidaJogador = 0;
                        binding.titulo.setText("Combate");
                        binding.vidaJogador.setText("Vida Jogador: \n" + String.valueOf(vidaJogador) + " / " + String.valueOf(jogador.getHp()));
                        androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                        dlg.setMessage("DERROTA... \nRetorne ao perfil do seu personagem e tente novamente.");
                        dlg.setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss(); // Fecha o diálogo
                        });
                        dlg.show();
                        Bundle bundle = new Bundle();
                        bundle.putInt("idU", arg.getInt("idU"));
                        bundle.putInt("idPerso", arg.getInt("idPerso"));
                        binding.armaBnt.setVisibility(View.GONE);
                        binding.hakiBnt.setVisibility(View.GONE);
                        binding.akumaBnt.setVisibility(View.GONE);
                        return;
                    }
                    binding.titulo.setText("Vez " + jogador.getNome());
                    vezdequem[0] = true;
                    if (jogador.getHakirei() != 0 && jogador.getTurnohakirei() == 0) {
                        binding.hakiBnt.setVisibility(View.VISIBLE);
                    }
                    binding.armaBnt.setVisibility(View.VISIBLE);
                    binding.akumaBnt.setVisibility(View.VISIBLE);
                }
            }, time);
            break;
    }
}


    private int calculaAtaquesDisponiveis(int total) {
        return Math.min(total, (nivelAtual / 25) + 1);
    }
    private void executaAtaqueHaki(Bundle arg) {
        ((MainActivity) getActivity()) .mediaPlayer.setVolume(0.25f,0.25f);
        MediaPlayer media = MediaPlayer.create(requireContext(),R.raw.haki_rei);
        switch (inimigos.getHakirei()) {
            case 1:
            case 2:
            case 3:
                media.start();

                double fator = 0.0;
                boolean chanceAtingida = false;

                int hakiArmJogador = jogador.getHakiarm();

                if (inimigos.getHakirei() == 1) {
                    if (hakiArmJogador == 0 && validaChance(75)) {
                        fator = 0.20;
                        chanceAtingida = true;
                    } else if (hakiArmJogador == 1 && validaChance(50)) {
                        fator = 0.10;
                        chanceAtingida = true;
                    }
                } else if (inimigos.getHakirei() == 2) {
                    if (hakiArmJogador == 0) {
                        fator = 0.30;
                        chanceAtingida = true;
                    } else if (hakiArmJogador == 1 && validaChance(75)) {
                        fator = 0.20;
                        chanceAtingida = true;
                    } else if (hakiArmJogador == 2 && validaChance(50)) {
                        fator = 0.10;
                        chanceAtingida = true;
                    }
                } else if (inimigos.getHakirei() == 3) {
                    if (hakiArmJogador == 0) {
                        // Jogador perde toda a vida
                        ObjectAnimator animator = ObjectAnimator.ofInt(binding.vidaJogadorBar, "progress", vidaJogador, 0);
                        animator.setDuration(500);
                        animator.setInterpolator(new DecelerateInterpolator());
                        animator.start();

                        vidaJogador = 0;
                        binding.titulo.setText("Combate");
                        binding.vidaJogador.setText("Vida jogador: \n" + vidaJogador + " / " + jogador.getHp());

                        new AlertDialog.Builder(getContext())
                                .setMessage("DERROTA... \nRetorne ao perfil do seu personagem e tente novamente.")
                                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                                .show();

                        binding.armaBnt.setVisibility(View.GONE);
                        binding.hakiBnt.setVisibility(View.GONE);
                        binding.akumaBnt.setVisibility(View.GONE);
                    } else if (hakiArmJogador == 1) {
                        fator = 0.30;
                        chanceAtingida = true;
                    } else if (hakiArmJogador == 2 && validaChance(75)) {
                        fator = 0.20;
                        chanceAtingida = true;
                    } else if (hakiArmJogador == 3 && validaChance(50)) {
                        fator = 0.10;
                        chanceAtingida = true;
                    }
                }

                if (chanceAtingida && fator > 0) {
                    forcajogador = ((int) (forcajogador - forcajogador * fator));
                    estaminajogador = ((int) (estaminajogador - estaminajogador * fator));
                    exibeMensagem(jogador.getNome() + " teve sua força e estamina reduzidas em " + (int)(fator * 100) + "%");
                } else if (inimigos.getHakirei() != 3 || hakiArmJogador != 0) {
                    exibeMensagem(inimigos.getNome() + " não conseguiu diminuir a força do inimigo");
                }

                media.setOnCompletionListener(mp -> {
                    mp.release();
                    ((MainActivity) getActivity()).mediaPlayer.setVolume(1, 1);
                    if (jogador.getHp() > 0) {
                        liberaBotoes();
                        ataquerei = false;
                        vezdequem[0] = true;
                    }
                });
                break;

            default:
                executaAtaqueAkuma(arg);
        }
    }

    private void executaAtaqueAkuma(Bundle arg) {
        Akumas akuma = db.akumaDao().buscaAkuma(inimigos.getIdakuma());
        AtaqueAkumaNoMi ataques = db.ataqueAkumasDao().buscaAtaqueAkumaNoMi(akuma.getIdataques());
        int totalataques = ataques.getQntataques();
        quantosataques = calculaAtaquesDisponiveis(totalataques);

        int randomAtaque = 0;
        String tipo ="";
        int num = 0;
        do {
            randomAtaque = random.nextInt(quantosataques);
            tipo = ataques.getTipoDeAtaque()[randomAtaque];
            num ++;
        }while (ataques.getCusto()[randomAtaque] > inimigos.getEnergia() && num < 10);

        MediaPlayer media = MediaPlayer.create(requireContext(), R.raw.akuma);
        media.start();
        ((MainActivity) getActivity()).mediaPlayer.setVolume(0.25f, 0.25f);

        String finalTipo = tipo;
        media.setOnCompletionListener(mp -> {
            mp.release();
            ((MainActivity) getActivity()).mediaPlayer.setVolume(1f, 1f);
            AtaqueAkuma(ataques, quantosataques, finalTipo, arg, false);
        });
    }


    public void GeraAtaqueInimigo(Bundle arg) {
        binding.hakiBnt.setVisibility(View.GONE);
        binding.armaBnt.setVisibility(View.GONE);
        binding.akumaBnt.setVisibility(View.GONE);

        int tipoAtaque = random.nextInt(3);

        if (tipoAtaque == 0 || inimigos.getAkumaNoMi().equals("NÃO TEM") ||
                inimigos.getAkumaNoMi().equals("Desconhecida") ||
                inimigos.getAkumaNoMi().equals("Mane Mane no Mi") ||
                inimigos.getAkumaNoMi().equals("Fuku Fuku no Mi")) {
            ataqueFisicoInimigo(arg);
        } else if (tipoAtaque == 1) {
            executaAtaqueAkuma(arg);
        } else if (tipoAtaque == 2 && ataquerei) {
            executaAtaqueHaki(arg);
        } else {
            GeraAtaqueInimigo(arg);
        }
    }

    private int retirabuff(int novo,int antigo, double bonus) {
        return (int) (novo - (antigo * bonus));
    }

    public void Buff() {
        if (buffTurnos != 4) {
            buffTurnos++;
        } else {
            ataquecerteiro = true;

            // Aplica o debuff reverso
            boolean buffDeForca = dano_jogador == 2 || forca_jogador >= 2.0;
            boolean buffDeEstamina = estamina_jogador >= 2.0;

            if (buffDeForca) {
                forcajogador = (forcajogador / 2);
                estaminajogador = (retirabuff(estaminajogador,jogador.getEstamina(), estamina_jogador));
            } else if (buffDeEstamina) {
                forcajogador = (retirabuff(forcajogador,jogador.getForca(), forca_jogador));
                estaminajogador = (estaminajogador / 2);
            } else {
                forcajogador = (retirabuff(forcajogador,jogador.getForca(), forca_jogador));
                estaminajogador = (retirabuff(estaminajogador,jogador.getEstamina(), estamina_jogador));
            }
            agilidadejogador = (retirabuff(agilidadejogador,jogador.getAgilidade(), agilidade_jogador));
            defesajogador = (retirabuff(defesajogador,jogador.getDefesa(), defesa_jogador));
            intuicaojogador = (retirabuff(intuicaojogador,jogador.getIntuicao(), intuicao_jogador));

            forcaInimigo = (retirabuff(forcaInimigo,inimigos.getForca(), forca_inimigo));
            estaminaInimigo = (retirabuff(estaminaInimigo,inimigos.getEstamina(), estamina_inimigo));
            agilidadeInimigo = (retirabuff(agilidadeInimigo,inimigos.getAgilidade(), agilidade_inimigo));
            defesaInimigo = (retirabuff(defesaInimigo,inimigos.getDefesa(), defesa_inimigo));
            intuicaoInimigo = (retirabuff(intuicaoInimigo,inimigos.getIntuicao(), intuicao_inimigo));
        }
}

    public void ataqueFisicoInimigo(Bundle arg){
        ((MainActivity) getActivity()) .mediaPlayer.setVolume(0.25f,0.25f);
        MediaPlayer media = MediaPlayer.create(requireContext(),R.raw.bonk);
        if(buscaPalavra(armaBranca,inimigos.getArmas())){
            if(random.nextInt(2) == 0) {
                media = MediaPlayer.create(requireContext(), R.raw.espada1);
            }else{
                media = MediaPlayer.create(requireContext(), R.raw.espada2);
            }
        }else if(buscaPalavra(armaDeFogo,inimigos.getArmas())){
            media = MediaPlayer.create(requireContext(),R.raw.arma);
        }else if(buscaPalavra(laser,inimigos.getArmas())){
            media = MediaPlayer.create(requireContext(),R.raw.laser);
        }else if(buscaPalavra(desarmado,inimigos.getArmas())){
            if(random.nextInt(2) == 0) {
                media = MediaPlayer.create(requireContext(), R.raw.soco1);
            }else {
                media = MediaPlayer.create(requireContext(), R.raw.soco2);
            }
        }else if (inimigos.getArmas().equals("Kuro Kabuto")){
            media = MediaPlayer.create(requireContext(), R.raw.estilingue);
        }
        media.start();
        media.setOnCompletionListener(mp -> {
            ((MainActivity) getActivity()) .mediaPlayer.setVolume(1,1);
            TurnoInimigo(arg,forcaInimigo,inimigos.getArmas());
            mp.release();
        });
    }

    public boolean procuraRepeticao(List<Integer> array, int targetNumber) {
        for (int number : array) {
            if (number == targetNumber) {
                return true;
            }
        }
        return false;
    }

    public void DesbloqueiaAtaques(Akumas akumas){
        AtaqueAkumaNoMi ataqueAkumaNoMi = db.ataqueAkumasDao().buscaAtaqueAkumaNoMi(akumas.getIdataques());
        if(jogador.getQntataquesdesbloqueados() != ataqueAkumaNoMi.getQntataques()) {
            switch (ataqueAkumaNoMi.getQntataques()) {
                case 2:
                    if (jogador.getNivel() == 50)
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    break;
                case 3:
                    if (jogador.getNivel() == 50) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    } else if (jogador.getNivel() == 25) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }
                    break;
                case 4:
                    if (jogador.getNivel() == 75) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    } else if (jogador.getNivel() == 50) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }else if (jogador.getNivel() == 25) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }
                    break;
                case 5:
                    if (jogador.getNivel() == 100) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    } else if (jogador.getNivel() == 75) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }else if (jogador.getNivel() == 50) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }else if (jogador.getNivel() == 25) {
                        jogador.setQntataquesdesbloqueados(jogador.getQntataquesdesbloqueados() + 1);
                    }
                    break;
            }
        }
    }
    //Vai ser para ver se tem chance de atacar novamente ou de desviar do ataque
    public boolean validaChance(int num){
        Random random = new Random();
        int chance = random.nextInt(101);
        if(chance < num) {
            return true;
        }
        return false;
    }

   public int CalculaDano(int defesa, int forca) {
    double def = defesa / 100.0; // agora def = 0.75
    double danoRetirado = forca * def;
    double danoTotal = forca - danoRetirado;
    return (int) danoTotal;
}

    public boolean VerficaVida(int vida, int idakuma, boolean quem_e){
        if(vida <= 0) {
            if(idakuma != 0) {
                Akumas akumas = db.akumaDao().buscaAkuma(idakuma);
                if (resucitou != 1 && akumas.getNome().equals("Yomi Yomi no Mi")) {
                    if (quem_e) {
                        vidaInimigo = inimigos.getHp();
                    } else {
                        vidaJogador = jogador.getHp();
                    }
                    resucitou++;
                    return false;
                } else if (resucitou != 2 && akumas.getNome().equals("Tama Tama no Mi")) {
                    if (quem_e) {
                        vidaInimigo = inimigos.getHp();
                    } else {
                        vidaJogador = jogador.getHp();
                    }
                    resucitou++;
                    return false;
                } else {
                    resucitou = 0;
                    return true;
                }
            }else{
                return true;
            }
        }
        return false;
    }

    public void decideAtaque(Bundle arg){
        if (inimigos == null || jogador == null || binding == null) return;

        binding.hakiBnt.setVisibility(View.GONE);
        binding.armaBnt.setVisibility(View.GONE);
        binding.akumaBnt.setVisibility(View.GONE);

        boolean inimigoComeca = false;

        if (inimigos.getAgilidade() > jogador.getAgilidade()) {
            inimigoComeca = true;
        } else if (inimigos.getAgilidade() == jogador.getAgilidade()) {
            inimigoComeca = random.nextInt(2) == 0;
        }

        if (inimigoComeca) {
            binding.titulo.setText("Vez " + inimigos.getNome());
            vezdequem[0] = false;
            GeraAtaqueInimigo(arg);
        } else {
            binding.titulo.setText("Vez " + jogador.getNome());
            vezdequem[0] = true;
            liberaBotoes();
        }
    }

    public void liberaBotoes(){
        if(jogador.getHakirei() != 0 && jogador.getTurnohakirei() == 0){
            binding.hakiBnt.setVisibility(View.VISIBLE);
        }
        binding.armaBnt.setVisibility(View.VISIBLE);
        binding.akumaBnt.setVisibility(View.VISIBLE);
    }

    private void exibeMensagem(String texto) {
        TextView novo = new TextView(getContext());
        novo.setTextColor(Color.BLACK);
        novo.setTypeface(fonte);
        novo.setTextSize(20);
        novo.setText(texto);
        binding.informacoes.addView(novo);
    }

    public Inimigos nivelaInimigo(int idInim){
        Inimigos inimigos = db.inimigosDao().buscaInimigos(idInim);
        int hakiarm = 25;
        int hakiobs = 16;
        Random random = new Random();
        if(nivelAtual > 50) {
            inimigos.setAgilidade(inimigos.getAgilidade()+50);
        }else{
            inimigos.setAgilidade(inimigos.getAgilidade()+nivelAtual);
        }
        inimigos.setForca(inimigos.getForca()+(nivelAtual*2));
        inimigos.setEstamina(inimigos.getEstamina()+(nivelAtual*2));
        inimigos.setHp(inimigos.getHp()+(nivelAtual*3));

        if(nivelAtual >= 90){
            inimigos.setHakiobs(3);
        } else if (nivelAtual >= 60) {
            inimigos.setHakiobs(2);
        }else if (nivelAtual >= 30) {
            inimigos.setHakiobs(1);
        }
        inimigos.setIntuicao(inimigos.getIntuicao()+(inimigos.getHakiobs()*hakiobs));

        if(nivelAtual <= 9){
            inimigos.setHakiarm(0);
            inimigos.setHakirei(0);
        } else if (nivelAtual <= 19) {
            inimigos.setHakiarm(random.nextInt(2));
            inimigos.setHakirei(random.nextInt(2));
        }else if (nivelAtual <= 29) {
            inimigos.setHakiarm(random.nextInt(3));
            inimigos.setHakirei(random.nextInt(3));
        }else if (nivelAtual <= 69) {
            inimigos.setHakiarm(random.nextInt(4));
            inimigos.setHakirei(random.nextInt(4));
        }else if (nivelAtual <= 79) {
            inimigos.setHakiarm(random.nextInt(3)+1);
            inimigos.setHakirei(random.nextInt(3)+1);
        }else if (nivelAtual <= 89) {
            inimigos.setHakiarm(random.nextInt(2)+2);
            inimigos.setHakirei(random.nextInt(2)+2);
        }else {
            inimigos.setHakiarm(3);
            inimigos.setHakirei(3);
        }
        inimigos.setDefesa(inimigos.getDefesa()+(inimigos.getHakiarm()*hakiarm));

        if(inimigos.getEstamina() <= 30){
            inimigos.setEnergia(5);
        } else if (inimigos.getEstamina() <= 45) {
            inimigos.setEnergia(8);
        }else if (inimigos.getEstamina() <= 60) {
            inimigos.setEnergia(11);
        }else if (inimigos.getEstamina() <= 75) {
            inimigos.setEnergia(14);
        }else if (inimigos.getEstamina() <= 90) {
            inimigos.setEnergia(17);
        }else if (inimigos.getEstamina() <= 105) {
            inimigos.setEnergia(20);
        }else if (inimigos.getEstamina() <= 120) {
            inimigos.setEnergia(23);
        }else if (inimigos.getEstamina() <= 135) {
            inimigos.setEnergia(26);
        }else if (inimigos.getEstamina() <= 150) {
            inimigos.setEnergia(29);
        }else{
            inimigos.setEnergia(32);
        }

        if(jogador.getNivel() >= 25 && (jogador.getEstamina() >= 165 || jogador.getForca() >= 165)){
            inimigos.setHp(inimigos.getHp()+165);
        }else if(jogador.getNivel() >= 50 && (jogador.getEstamina() >= 315 || jogador.getForca() >= 315)){
            inimigos.setHp(inimigos.getHp()+480);
        }else if(jogador.getNivel() >= 75 && (jogador.getEstamina() >= 465 || jogador.getForca() >= 465)){
            inimigos.setHp(inimigos.getHp()+945);
        }else if((jogador.getEstamina() >= 615 || jogador.getForca() >= 615)){
            inimigos.setHp(inimigos.getHp()+1560);
        }

        return inimigos;
    }

    public void Nivelamento(Jogador jogador, Bundle arg){
        int idini = arg.getInt("idInimi");
        if(frutaAntiga > 0) jogador.setAkumaNoMi(frutaAntiga);
        int hakiobs = 16;
        int hakiarm = 25;

        boolean haobs = false;
        boolean haarm = false;
        boolean harei = false;
        boolean evo = false;

        Usuario usuario = db.usuarioDao().buscaUsuario(arg.getInt("idU"));
        List<Integer> inimigoList = usuario.getInimigos();
        if(!procuraRepeticao(inimigoList,idini)) inimigoList.add(idini);
        usuario.setInimigos(inimigoList);


        int nivelAtual = jogador.getNivel() + 1;
        jogador.setNivel(nivelAtual);
        jogador.setVitorias5(jogador.getVitorias5()+1);
        jogador.setVitorias10(jogador.getVitorias10()+1);
        jogador.setVitorias25(jogador.getVitorias25()+1);
        if(jogador.getAgilidade() < 50) {
            jogador.setPontos(jogador.getPontos()+8);
        }else{
            jogador.setAgilidade(50);
            jogador.setPontos(jogador.getPontos()+8);
        }

        if(jogador.getEstamina() <= 30){
            jogador.setEnergia(5);
        } else if (jogador.getEstamina() <= 45) {
            jogador.setEnergia(8);
        }else if (jogador.getEstamina() <= 60) {
            jogador.setEnergia(11);
        }else if (jogador.getEstamina() <= 75) {
            jogador.setEnergia(14);
        }else if (jogador.getEstamina() <= 90) {
            jogador.setEnergia(17);
        }else if (jogador.getEstamina() <= 105) {
            jogador.setEnergia(20);
        }else if (jogador.getEstamina() <= 120) {
            jogador.setEnergia(23);
        }else if (jogador.getEstamina() <= 135) {
            jogador.setEnergia(26);
        }else if (jogador.getEstamina() <= 150) {
            jogador.setEnergia(29);
        }else{
            jogador.setEnergia(32);
        }
        //EU SÓ TRANSFERI A OPÇÃO DE ENTRAR PARA UMA TRIPULAÇÃO PIRATA QUANDO VC É DA MARINHA, PRA APARECER A CADA 25 NÍVEIS, INVÉS DE 5
        //(eu acredito q se vc é da marinha, os convites para entrar em uma tripulação deveriam ser mais raros doq quando vc ja é pirata)
        if(jogador.getVitorias5() == 5){
            jogador.setVitorias5(0);
            if(jogador.getTipo().equals("P")) {
                androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                dlg.setMessage("Um convite para participar de uma nova tripulação pirata foi recebido.\nDeseja aceitar?");
                dlg.setPositiveButton("Aceitar", (dialog, which) -> {
                    int qualTripulacao = new Random().nextInt(db.tripulacaoDao().quantosTripulacao())+1;
                    jogador.setIdTripula(qualTripulacao);
                    List<Integer> tripulacaoList = usuario.getTripulacao();
                    if(!procuraRepeticao(tripulacaoList,qualTripulacao)) tripulacaoList.add(qualTripulacao);
                    usuario.setTripulacao(tripulacaoList);
                    //Esse "dialog.dismiss()" tava dentro do if (jogador = marinha), ent eu n sei ao certo como vc iria fechar o dialogo caso o jogador fosse pirata, por isso eu deixei ele aqui fora
                    //se n era pra fazer isso ou se vc ja tem uma forma de arruma isso ent desculpa, é só apagar essa linha de baixo q vai voltar ao q tava
                    dialog.dismiss(); // Fecha o diálogo
                    usuario.setIdUser(arg.getInt("idU"));
                    db.usuarioDao().upgrade(usuario);
                    jogador.setIdpersonagens(arg.getInt("idPerso"));
                    db.jogadorDao().upgrade(jogador);
                });
                dlg.setNegativeButton("Recusar",null);

                dlg.show();
            }
        }
        if (jogador.getVitorias10() == 10 && jogador.getNivel() < 91){
            boolean jafoi = false;
            do {
                int qualHaki = new Random().nextInt(3);
                String[] haki = {"Haki da Observação", "Haki do Armamento", "Haki do rei"};
                String seuHaki = haki[qualHaki];
                switch (seuHaki) {
                    case "Haki da Observação":
                        if (jogador.getHakiobs() < 3) {
                            haobs = true;
                            jafoi = false;
                            jogador.setHakiobs(jogador.getHakiobs() + 1);
                            jogador.setIntuicao(2+(jogador.getHakiobs()*hakiobs));
                        } else {
                            jafoi = true;
                        }
                        break;
                    case "Haki do Armamento":
                        if (jogador.getHakiarm() < 3) {
                            haarm = true;
                            jafoi = false;
                            jogador.setHakiarm(jogador.getHakiarm() + 1);
                            jogador.setDefesa(0+(jogador.getHakiarm()*hakiarm));
                        } else {
                            jafoi = true;;
                        }
                        break;
                    case "Haki do rei":
                        if (jogador.getHakirei() < 3) {
                            harei = true;
                            jafoi = false;
                            jogador.setHakirei(jogador.getHakirei() + 1);
                        } else {
                            jafoi = true;
                        }
                        break;
                    default:
                        jafoi = true;
                        break;
                }
            }while (jafoi);
            jogador.setVitorias10(0);

        }
        if (jogador.getVitorias25() == 25) {
            jogador.setVitorias25(0);
            if(jogador.getTipo().equals("M")) {
                androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                dlg.setMessage("Um grupo de piratas tentou convencer você a entrar na tripulação deles.\nDeseja trair a Marinha e se tornar um pirata?\nOBS: É um caminho sem volta caso aceite.");
                dlg.setPositiveButton("Aceitar", (dialog, which) -> {
                    int qualTripulacao = new Random().nextInt(db.tripulacaoDao().quantosTripulacao())+1;
                    jogador.setIdTripula(qualTripulacao);
                    List<Integer> tripulacaoList = usuario.getTripulacao();
                    if(!procuraRepeticao(tripulacaoList,qualTripulacao)) tripulacaoList.add(qualTripulacao);
                    usuario.setTripulacao(tripulacaoList);

                    jogador.setTipo("P");
                    jogador.setAssociacao("Pirata");
                    switch (jogador.getTitulo()) {
                        //Pirata
                        case "Aprendiz de Marinheiro":
                            jogador.setTitulo("Bandido");
                            jogador.setRecompensa("B$ 0");
                            break;
                        case "Sargento-Mor":
                            jogador.setTitulo("Pirata");
                            jogador.setRecompensa("B$ 30.000.000");
                            jogador.setOrigem("Grand Line");
                            break;
                        case "Capitão da Marinha":
                            jogador.setTitulo("Super Nova");
                            jogador.setRecompensa("B$ 250.000.000");
                            jogador.setOrigem("New World");
                            break;
                        case "Vice-Almirante":
                            jogador.setTitulo("Shichibukai");
                            jogador.setRecompensa("B$ 1.500.000.000");
                            jogador.setOrigem("New World (Pós-Dressrosa)");
                            break;
                        case "Almirante":
                            jogador.setTitulo("Yonko");
                            jogador.setRecompensa("B$ 3.000.000.000");
                            jogador.setOrigem("New World (Pós-Wano)");
                            break;
                    }
                    dialog.dismiss(); // Fecha o diálogo

                    usuario.setIdUser(arg.getInt("idU"));
                    db.usuarioDao().upgrade(usuario);
                    jogador.setIdpersonagens(arg.getInt("idPerso"));
                    db.jogadorDao().upgrade(jogador);
                });
                dlg.setNegativeButton("Recusar",null);

                dlg.show();
            }
            if (nivelAtual <= 100) { //coloquei aqui, pq assim, o jogador ainda pode receber pedido de tripulação msm depois do nivel 100
                switch (jogador.getTitulo()){
                    //Pirata
                    case "Bandido":
                        jogador.setTitulo("Pirata");
                        jogador.setRecompensa("B$ 30.000.000");
                        jogador.setOrigem("Grand Line");
                        jogador.setHp(jogador.getHp()+72);
                        evo = true;
                        break;
                    case "Pirata":
                        jogador.setTitulo("Super Nova");
                        jogador.setRecompensa("B$ 250.000.000");
                        jogador.setOrigem("New World");
                        jogador.setHp(jogador.getHp()+72);
                        evo = true;
                        break;
                    case "Super Nova":
                        jogador.setTitulo("Shichibukai");
                        jogador.setRecompensa("B$ 1.500.000.000");
                        jogador.setOrigem("New World (Pós-Dressrosa)");
                        jogador.setHp(jogador.getHp()+72);
                        evo = true;
                        break;
                    case "Shichibukai":
                        jogador.setTitulo("Yonko");
                        jogador.setRecompensa("B$ 3.000.000.000");
                        jogador.setOrigem("New World (Pós-Wano)");
                        jogador.setHp(jogador.getHp()+72);
                        evo = true;
                        break;
                    //Marinha
                    case "Aprendiz de Marinheiro":
                        jogador.setTitulo("Sargento-Mor");
                        jogador.setRecompensa("★★");
                        jogador.setOrigem("Grand Line");
                        jogador.setHp(jogador.getHp()+72);
                        evo = true;
                        break;
                    case "Sargento-Mor":
                        jogador.setTitulo("Capitão da Marinha");
                        jogador.setRecompensa("★★★★★");
                        jogador.setOrigem("New World");
                        jogador.setHp(jogador.getHp()+72);
                        evo = true;
                        break;
                    case "Capitão da Marinha":
                        jogador.setTitulo("Vice-Almirante");
                        jogador.setRecompensa("♛♛♛");
                        jogador.setOrigem("New World (Pós-Dressrosa)");
                        jogador.setHp(jogador.getHp()+72);
                        evo = true;
                        break;
                    case "Vice-Almirante":
                        jogador.setTitulo("Almirante");
                        jogador.setRecompensa("♛♛♛♛♛");
                        jogador.setOrigem("New World (Pós-Wano)");
                        jogador.setHp(jogador.getHp()+72);
                        evo = true;
                        break;
                    default:
                        Log.d("Batalha Swithc","Erro no swithc");
                        break;
                }
            }
        }

        androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        dlg.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss(); // Fecha o diálogo
        });
        String mensagem = "";
        if(haobs){
            mensagem += ("Você sente seus reflexos se tornarem mais apurados...\nUm Haki acaba de ser adquirido ou aprimorado!\n"+
                    "Haki da Observação: "+String.valueOf(jogador.getHakiobs())+".\n");
        } else if (haarm) {
            mensagem += ("Você sente seu corpo se tornar mais resistente...\nUm haki acaba de ser adquirido ou aprimorado!\n"+
                    "Haki do Armamento: "+String.valueOf(jogador.getHakiarm())+".\n");
        } else if (harei) {
            mensagem += ("Você sente suas convicções se tornarem mais poderosas...\nUm haki acaba de ser adquirido ou aprimorado!\n"+
                    "Haki do Rei: "+String.valueOf(jogador.getHakirei())+".\n");
        }
        if(evo){
            mensagem += ("UM NOVO ESTÁGIO FOI DESBLOQUEADO!!\nPrepare-se para enfrentar inimigos ainda mais fortes.\n"+"Fase atual: "+jogador.getOrigem()+".\n"
                    +"Além disso, sua recompensa também foi aumentada para "+jogador.getRecompensa());
        }
        if(!mensagem.equals("")){
            dlg.setMessage(mensagem);
            dlg.show();
        }
        usuario.setIdUser(arg.getInt("idU"));
        db.usuarioDao().upgrade(usuario);

        jogador.setIdpersonagens(arg.getInt("idPerso"));
        db.jogadorDao().upgrade(jogador);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Indicar que este fragmento tem opções de menu para a ActionBar/Toolbar
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mostrarDialogoDeConfirmacao();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void mostrarDialogoDeConfirmacao() {
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Sair")
                    .setMessage("Deseja mesmo sair do Combate?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        // Sai do fragmento (volta para o anterior)
                        requireActivity().getSupportFragmentManager().popBackStack();
                    })
                    .setNegativeButton("Não", null)
                    .show();
    }

    public boolean buscaPalavra(String []conjunto, String palavra){
        for(String pala : conjunto){
            if(pala.equals(palavra)) return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
