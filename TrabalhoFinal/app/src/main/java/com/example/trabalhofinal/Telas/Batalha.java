package com.example.trabalhofinal.Telas;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import java.util.Random;

public class Batalha    extends Fragment {
    private BatalhaBinding binding;

    private AppDataBase db;

    private int vidaInimigo = 0;
    private int estaminaInimigo = 0;

    private int vidaJogador = 0;
    private int estaminaJogador = 0;
    private int energiaJogador = 0;
    private Inimigos inimigos;
    private Jogador jogador;

    Random random = new Random();
    final int[] qntsDesvios = {random.nextInt(3)};
    final int[] acumuloDesvios = {0};
    final boolean[] vezdequem = {true};

    int ataquesTurnos = 0;


    int custo;
    int hp_jogador;
    int forca_jogador;
    int estamina_jogador;
    int agilidade_jogador;
    int defesa_jogador;
    int intuicao_jogador;
    int dano_jogador;

    int hp_inimigo;
    int forca_inimigo;
    int estamina_inimigo;
    int agilidade_inimigo;
    int defesa_inimigo;
    int intuicao_inimigo;
    int dano_inimigo;

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

        db = AppDataBase.getDataBase(getContext());

        Bundle arg = getArguments();
        if(arg != null) {
            inimigos = nivelaInimigo(arg.getInt("nivelIni"), arg.getInt("idInimi"));
            jogador = db.personagensDao().buscaPer(arg.getInt("idPerso"));
            vidaInimigo = inimigos.getHp();
            estaminaInimigo = inimigos.getEstamina();

            int resID = requireContext().getResources().getIdentifier(inimigos.getFotocombate(), "drawable", getContext().getPackageName());
            binding.imagemInimigo.setImageResource(resID);

            vidaJogador = jogador.getHp();
            estaminaJogador = jogador.getEstamina();
            energiaJogador = jogador.getEnergia();

            binding.nomeInimigo.setText(inimigos.getNome());
            binding.hakiInimigo.setText("Nivel: "+String.valueOf(arg.getInt("nivelIni"))+"\nARM: "+String.valueOf(inimigos.getHakiarm())+"\nOBS: "+String.valueOf(inimigos.getHakiobs()));
            binding.nomeJogador.setText(jogador.getNome());
            binding.energia.setText("Energia: "+String.valueOf(energiaJogador)+" / "+String.valueOf(jogador.getEnergia()));

            binding.vidaInimigo.setText("Vida Inimigo: \n"+String.valueOf(vidaInimigo)+" / "+String.valueOf(inimigos.getHp()));
            binding.vidaJogador.setText("Vida Jogador: \n"+String.valueOf(vidaJogador)+" / "+String.valueOf(jogador.getHp()));

            if(jogador.getHakirei() == 0){
                binding.hakiBnt.setVisibility(View.INVISIBLE);
            }
            binding.armaBnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(vezdequem[0]){
                        TrunoJogador(arg,jogador.getForca());
                    }else {
                        androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                        dlg.setMessage("Esta na vez do inimigo");
                        dlg.show();
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

                    if(jogador.getQntataquesdesbloqueados() >= 1) {
                        ataque1.setVisibility(View.VISIBLE);
                        descricao1.setVisibility(View.VISIBLE);
                        ataque1.setText(ataqueAkumaNoMi.getNomeDoAtaque()[0]);
                        descricao1.setText(ataqueAkumaNoMi.getDescricao()[0]);
                        ataque1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ataques.dismiss();
                                if(jogador.getEnergia() >= ataqueAkumaNoMi.getCusto()[0]) {
                                    AtaqueAkuma(ataqueAkumaNoMi, 0, ataqueAkumaNoMi.getTipoDeAtaque()[0], arg);
                                }else{
                                    Toast.makeText(requireContext(), "Jogador sem Energia", Toast.LENGTH_LONG).show();
                                }                            }
                        });
                    }if(jogador.getQntataquesdesbloqueados() >= 2) {
                        ataque2.setVisibility(View.VISIBLE);
                        descricao2.setVisibility(View.VISIBLE);
                        ataque2.setText(ataqueAkumaNoMi.getNomeDoAtaque()[1]);
                        descricao2.setText(ataqueAkumaNoMi.getDescricao()[1]);
                        ataque2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ataques.dismiss();
                                if(jogador.getEnergia() >= ataqueAkumaNoMi.getCusto()[1]) {
                                    AtaqueAkuma(ataqueAkumaNoMi, 1, ataqueAkumaNoMi.getTipoDeAtaque()[1], arg);
                                }else{
                                    Toast.makeText(requireContext(), "Jogador sem Energia", Toast.LENGTH_LONG).show();
                                }                            }
                        });
                    }if(jogador.getQntataquesdesbloqueados() >= 3) {
                        ataque3.setVisibility(View.VISIBLE);
                        descricao3.setVisibility(View.VISIBLE);
                        ataque3.setText(ataqueAkumaNoMi.getNomeDoAtaque()[2]);
                        descricao3.setText(ataqueAkumaNoMi.getDescricao()[2]);
                        ataque3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(jogador.getEnergia() >= ataqueAkumaNoMi.getCusto()[2]) {
                                    AtaqueAkuma(ataqueAkumaNoMi, 2, ataqueAkumaNoMi.getTipoDeAtaque()[2], arg);
                                }else{
                                    Toast.makeText(requireContext(), "Jogador sem Energia", Toast.LENGTH_LONG).show();
                                }                            }
                        });
                    }if(jogador.getQntataquesdesbloqueados() >= 4) {
                        ataque4.setVisibility(View.VISIBLE);
                        descricao4.setVisibility(View.VISIBLE);
                        ataque4.setText(ataqueAkumaNoMi.getNomeDoAtaque()[3]);
                        descricao4.setText(ataqueAkumaNoMi.getDescricao()[3]);
                        ataque4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ataques.dismiss();
                                if(jogador.getEnergia() >= ataqueAkumaNoMi.getCusto()[3]) {
                                    AtaqueAkuma(ataqueAkumaNoMi, 3, ataqueAkumaNoMi.getTipoDeAtaque()[3], arg);
                                }else{
                                    Toast.makeText(requireContext(), "Jogador sem Energia", Toast.LENGTH_LONG).show();
                                }                            }
                        });
                    }if(jogador.getQntataquesdesbloqueados() >= 5) {
                        ataque5.setVisibility(View.VISIBLE);
                        descricao5.setVisibility(View.VISIBLE);
                        ataque5.setText(ataqueAkumaNoMi.getNomeDoAtaque()[4]);
                        descricao5.setText(ataqueAkumaNoMi.getDescricao()[4]);
                        ataque5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ataques.dismiss();
                                if(jogador.getEnergia() >= ataqueAkumaNoMi.getCusto()[4]) {
                                    AtaqueAkuma(ataqueAkumaNoMi, 4, ataqueAkumaNoMi.getTipoDeAtaque()[4], arg);
                                }else{
                                    Toast.makeText(requireContext(), "Jogador sem Energia", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                    ataques.show();
                }
            });
        }
    }


    public void TrunoJogador(Bundle arg,int ValorBrutoDano){
        int dano = CalculaDano(inimigos.getDefesa() / 100, ValorBrutoDano);
        if (validaChance(inimigos.getIntuicao())) {
            ataquesTurnos ++;
            acumuloDesvios[0] = acumuloDesvios[0] + 1;
            TextView novo = new TextView(getContext());
            novo.setText(inimigos.getNome() + " desviou do seu ataque");
            binding.informacoes.addView(novo);
        } else {
            ataquesTurnos ++;
            qntsDesvios[0] = random.nextInt(3);
            TextView novo = new TextView(getContext());
            novo.setText(jogador.getNome() + " atacou com a " + jogador.getArmas());
            binding.informacoes.addView(novo);
            vidaInimigo = vidaInimigo - dano;
            if(VerficaVida(vidaInimigo)){
                binding.vidaInimigo.setText("Vida Inimigo: \n" + String.valueOf(0) + " / " + String.valueOf(inimigos.getHp()));
                Nivelamento(jogador,arg);
                androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                dlg.setMessage("Vitoria \n Retornar para as informações do personagem para a destribuição dos pontos");
                dlg.show();
                Bundle bundle = new Bundle();
                bundle.putInt("idU",arg.getInt("idU"));
                bundle.putInt("idPerso",arg.getInt("idPerso"));
                binding.armaBnt.setVisibility(View.INVISIBLE);
                binding.hakiBnt.setVisibility(View.INVISIBLE);
                binding.akumaBnt.setVisibility(View.INVISIBLE);
                return;

                /*NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.batalha2, true)
                        .build();
                NavHostFragment.findNavController(Batalha.this)
                        .navigate(R.id.action_batalha2_to_infoPersonagenUser,bundle,navOptions);//*/
            }else {
                binding.vidaInimigo.setText("Vida Inimigo: \n" + String.valueOf(vidaInimigo) + " / " + String.valueOf(inimigos.getHp()));
            }
        }
        if(validaChance(jogador.getAgilidade()) && ataquesTurnos < 2){
            ataquesTurnos++;
            androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
            dlg.setMessage("O jogador pode atacar novamente");
            dlg.show();
        }else{
            ataquesTurnos = 0;
            vezdequem[0] = false;
            TurnoInimigo(arg);
        }
    }

    public void TurnoInimigo(Bundle arg){
        int dano = CalculaDano((jogador.getDefesa()) / 100, inimigos.getForca());
        if (validaChance(jogador.getIntuicao())) {
            acumuloDesvios[0] = acumuloDesvios[0] + 1;
            TextView novo = new TextView(getContext());
            novo.setText(jogador.getNome() + " desviou do "+inimigos.getNome());
            binding.informacoes.addView(novo);
            ataquesTurnos ++;
        }else {
            ataquesTurnos ++;
            acumuloDesvios[0] = 0;
            TextView novo = new TextView(getContext());
            novo.setText(inimigos.getNome() + " atacou com a " + inimigos.getArmas());
            binding.informacoes.addView(novo);
            vidaJogador = vidaJogador - dano;
            if(VerficaVida(vidaJogador)) {
                binding.vidaJogador.setText("Vida Jogador: \n" + String.valueOf(0) + " / " + String.valueOf(jogador.getHp()));
                androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                dlg.setMessage("Derrota \n Retornar para as informações do personagem para a destribuição dos pontos");
                dlg.show();
                Bundle bundle = new Bundle();
                bundle.putInt("idU",arg.getInt("idU"));
                bundle.putInt("idPerso",arg.getInt("idPerso"));
                binding.armaBnt.setVisibility(View.INVISIBLE);
                binding.hakiBnt.setVisibility(View.INVISIBLE);
                binding.akumaBnt.setVisibility(View.INVISIBLE);
                return;

                /*NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.batalha2, true)
                        .build();
                NavHostFragment.findNavController(Batalha.this)
                        .navigate(R.id.action_batalha2_to_infoPersonagenUser,bundle,navOptions);//*/
            }else {
                binding.vidaJogador.setText("Vida Jogador: \n" + String.valueOf(vidaJogador) + " / " + String.valueOf(jogador.getHp()));
            }
        }
        if(validaChance(inimigos.getAgilidade()) && ataquesTurnos < 2){
            ataquesTurnos ++;
            Log.d("ataquesTurnos",String.valueOf(ataquesTurnos));
            androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
            dlg.setMessage("O Inimigo pode atacara novamente");
            dlg.show();
            TurnoInimigo(arg);
        }else {
            ataquesTurnos = 0;
            vezdequem[0] = true;
        }
    }

    //Vai ser para ver se tem chance de atacar novamente ou de desviar do ataque
    public boolean validaChance(int num){
        Random random = new Random();
        int chance = random.nextInt(101);
        if(chance < num) {
            Log.d("chance",String.valueOf(chance));
            Log.d("num",String.valueOf(num));
            return true;
        }
        return false;
    }

    //Quanto de dano a arma vaidar
    public int CalculaDano(int defesa,int forca){
        int danoTotal = 0;
        int danoRetirado = forca*defesa;
        danoTotal = forca-danoRetirado;
        return danoTotal;
    }

    public boolean VerficaVida(int vida){
        if(vida <= 0) return true;
        return false;
    }

    public void AtaqueAkuma(AtaqueAkumaNoMi ataqueAkumaNoMi,int qualataque, String tipoAtaque, Bundle arg){
        custo = ataqueAkumaNoMi.getCusto()[qualataque];
        hp_jogador = ataqueAkumaNoMi.getHpjogador()[qualataque];
        forca_jogador = ataqueAkumaNoMi.getForcajogador()[qualataque];
        estamina_jogador = ataqueAkumaNoMi.getEstaminajogador()[qualataque];
        agilidade_jogador = ataqueAkumaNoMi.getAgilidadejogador()[qualataque];
        defesa_jogador = ataqueAkumaNoMi.getDefesajogador()[qualataque];
        intuicao_jogador = ataqueAkumaNoMi.getIntuicaojogador()[qualataque];
        dano_jogador = ataqueAkumaNoMi.getDanojogador()[qualataque];

        hp_inimigo = ataqueAkumaNoMi.getHpinimigo()[qualataque];
        forca_inimigo = ataqueAkumaNoMi.getForcainimigo()[qualataque];
        estamina_inimigo = ataqueAkumaNoMi.getEstaminainimigo()[qualataque];
        agilidade_inimigo = ataqueAkumaNoMi.getAgilidadeinimigo()[qualataque];
        defesa_inimigo = ataqueAkumaNoMi.getDefesainimigo()[qualataque];
        intuicao_inimigo = ataqueAkumaNoMi.getIntuicaoinimigo()[qualataque];
        dano_inimigo = ataqueAkumaNoMi.getDanoinimigo()[qualataque];

        int custo = ataqueAkumaNoMi.getCusto()[qualataque];
        jogador.setEnergia(jogador.getEnergia()-custo);
        binding.energia.setText("Energia: "+String.valueOf(jogador.getEnergia())+" / "+String.valueOf(energiaJogador));

        switch (tipoAtaque){
            case"ATAQUE SIMPLES":
                TrunoJogador(arg,jogador.getEstamina());
                break;
            case"ATAQUE FORTE":
                TrunoJogador(arg,jogador.getEstamina()*2);
                break;
            case"ATAQUE FORTE 2":
                TrunoJogador(arg,jogador.getEstamina()*3);
                break;
            case"ESPECIAL":
                TrunoJogador(arg,jogador.getEstamina()*4);
                break;
            default:
                jogador.setHp(jogador.getHp()+(hp_jogador/100));
                jogador.setForca(jogador.getForca() + (forca_jogador / 100));
                jogador.setEstamina(jogador.getEstamina() + (estamina_jogador / 100));
                jogador.setAgilidade(jogador.getAgilidade() + (agilidade_jogador / 100));
                jogador.setDefesa(jogador.getDefesa() + (defesa_jogador / 100));
                jogador.setIntuicao(jogador.getIntuicao() + (intuicao_jogador / 100));

                inimigos.setHp(inimigos.getHp()+(hp_inimigo/100));
                inimigos.setForca(inimigos.getForca() + (forca_inimigo / 100));
                inimigos.setEstamina(inimigos.getEstamina() + (estamina_inimigo / 100));
                inimigos.setAgilidade(inimigos.getAgilidade() + (agilidade_inimigo / 100));
                inimigos.setDefesa(inimigos.getDefesa() + (defesa_inimigo / 100));
                inimigos.setIntuicao(inimigos.getIntuicao() + (intuicao_inimigo / 100));

                TurnoInimigo(arg);
                break;
        }
    }

    //Nivela o inimigo para aconpanhar o level do jogador
    public Inimigos nivelaInimigo(int nivelAtual, int idInim){
        Inimigos inimigos = db.inimigosDao().buscaInimigos(idInim);
        int hakiarm = 25;
        int hakiobs = 16;
        Random random = new Random();
        //Força, estamina, agilidade e HP
        inimigos.setAgilidade(inimigos.getAgilidade()+nivelAtual);
        if(inimigos.getAgilidade() > 75) inimigos.setAgilidade(75);
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
        } else if (nivelAtual <= 19) {
            inimigos.setHakiarm(random.nextInt(2));
        }else if (nivelAtual <= 29) {
            inimigos.setHakiarm(random.nextInt(3));
        }else if (nivelAtual <= 69) {
            inimigos.setHakiarm(random.nextInt(4));
        }else if (nivelAtual <= 79) {
            inimigos.setHakiarm(random.nextInt(3)+1);
        }else if (nivelAtual <= 89) {
            inimigos.setHakiarm(random.nextInt(2)+2);
        }else {
            inimigos.setHakiarm(3);
        }
        inimigos.setDefesa(inimigos.getDefesa()+(inimigos.getHakiarm()*hakiarm));

        if(inimigos.getEstamina() <= 14){
            inimigos.setEnergia(5);
        } else if (inimigos.getEstamina() <= 24) {
            inimigos.setEnergia(8);
        }else if (inimigos.getEstamina() <= 34) {
            inimigos.setEnergia(11);
        }else if (inimigos.getEstamina() <= 44) {
            inimigos.setEnergia(14);
        }else if (inimigos.getEstamina() <= 54) {
            inimigos.setEnergia(17);
        }else if (inimigos.getEstamina() <= 64) {
            inimigos.setEnergia(20);
        }else if (inimigos.getEstamina() <= 74) {
            inimigos.setEnergia(23);
        }else if (inimigos.getEstamina() <= 84) {
            inimigos.setEnergia(26);
        }else if (inimigos.getEstamina() <= 94) {
            inimigos.setEnergia(29);
        }else{
            inimigos.setEnergia(32);
        }

        return inimigos;
    }

    public void Nivelamento(Jogador jogador, Bundle arg){
        int idini = arg.getInt("idInimi");
        int hakiobs = 16;
        int hakiarm = 25;
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

        if(jogador.getEstamina() <= 14){
            jogador.setEnergia(5);
        } else if (jogador.getEstamina() <= 24) {
            jogador.setEnergia(8);
        }else if (jogador.getEstamina() <= 34) {
            jogador.setEnergia(11);
        }else if (jogador.getEstamina() <= 44) {
            jogador.setEnergia(14);
        }else if (jogador.getEstamina() <= 54) {
            jogador.setEnergia(17);
        }else if (jogador.getEstamina() <= 64) {
            jogador.setEnergia(20);
        }else if (jogador.getEstamina() <= 74) {
            jogador.setEnergia(23);
        }else if (jogador.getEstamina() <= 84) {
            jogador.setEnergia(26);
        }else if (jogador.getEstamina() <= 94) {
            jogador.setEnergia(29);
        }else{
            jogador.setEnergia(32);
        }

        if(jogador.getVitorias5() == 5 && !jogador.getAssociacao().equals("Marinha")){
            //Player vence uma Davy Back Fight e pode trocar aleatoriamente de tripulação
            int qualTripulacao = new Random().nextInt(db.tripulacaoDao().quantosTripulacao())+1;
            jogador.setIdTripula(qualTripulacao);
            List<Integer> tripulacaoList = usuario.getTripulacao();
            if(!procuraRepeticao(tripulacaoList,qualTripulacao)) tripulacaoList.add(qualTripulacao);
            usuario.setTripulacao(tripulacaoList);
            jogador.setVitorias5(0);
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
                            jogador.setHakiobs(jogador.getHakiobs() + 1);
                            jogador.setIntuicao(jogador.getIntuicao()+(jogador.getHakiobs()*hakiobs));
                        } else {
                            jafoi = true;
                        }
                        break;
                    case "Haki do Armamento":
                        if (jogador.getHakiarm() < 3) {
                            jogador.setHakiarm(jogador.getHakiarm() + 1);
                            jogador.setDefesa(jogador.getDefesa()+(jogador.getHakiarm()*hakiarm));
                        } else {
                            jafoi = true;;
                        }
                        break;
                    case "Haki do rei":
                        if (jogador.getHakirei() < 3) {
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
        if (jogador.getVitorias25() == 25 && nivelAtual <= 100) {
            switch (jogador.getTitulo()){
                //Pirata
                case "Bandido":
                    jogador.setTitulo("Pirata");
                    jogador.setRecompensa("B$ 30.000.000");
                    jogador.setOrigem("Grand Line");
                    jogador.setHp(jogador.getHp()+72);
                    break;
                case "Pirata":
                    jogador.setTitulo("Super Nova");
                    jogador.setRecompensa("B$ 250.000.000");
                    jogador.setOrigem("New World");
                    jogador.setHp(jogador.getHp()+72);
                    break;
                case "Super Nova":
                    jogador.setTitulo("Shichibukai");
                    jogador.setRecompensa("B$ 1.500.000.000");
                    jogador.setOrigem("New World (Pós-Dressrosa)");
                    jogador.setHp(jogador.getHp()+72);
                    break;
                case "Shichibukai":
                    jogador.setTitulo("Yonko");
                    jogador.setRecompensa("B$ 3.000.000.000");
                    jogador.setOrigem("New World (Pós-Wano)");
                    jogador.setHp(jogador.getHp()+72);
                    break;
                    //Marinha
                case "Aprendiz de Marinheiro":
                    jogador.setTitulo("Sargento-Mor");
                    jogador.setRecompensa("★★");
                    jogador.setOrigem("Grand Line");
                    jogador.setHp(jogador.getHp()+72);
                    break;
                case "Sargento-Mor":
                    jogador.setTitulo("Capitão da Marinha");
                    jogador.setRecompensa("★★★★★");
                    jogador.setOrigem("New World");
                    jogador.setHp(jogador.getHp()+72);
                    break;
                case "Capitão da Marinha":
                    jogador.setTitulo("Vice-Almirante");
                    jogador.setRecompensa("♛♛♛");
                    jogador.setOrigem("New World (Pós-Dressrosa)");
                    jogador.setHp(jogador.getHp()+72);
                    break;
                case "Vice-Almirante":
                    jogador.setTitulo("Almirante");
                    jogador.setRecompensa("♛♛♛♛♛");
                    jogador.setOrigem("New World (Pós-Wano)");
                    jogador.setHp(jogador.getHp()+72);
                    break;
                default:
                    Log.d("Batalha Swithc","Erro no swithc");
                    break;
            }
            jogador.setVitorias25(0);
        }
        usuario.setIdUser(arg.getInt("idU"));
        db.usuarioDao().upgrade(usuario);

        jogador.setIdpersonagens(arg.getInt("idPerso"));
        db.personagensDao().upgrade(jogador);
    }

    public boolean procuraRepeticao(List<Integer> array, int targetNumber) {
        for (int number : array) {
            if (number == targetNumber) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}