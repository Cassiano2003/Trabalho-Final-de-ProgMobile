package com.example.trabalhofinal.Telas;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Inimigos;
import com.example.trabalhofinal.Tabelas.Personagens;
import com.example.trabalhofinal.Tabelas.Usuario;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
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
    private Personagens personagens;

    Random random = new Random();
    final int[] qntsDesvios = {random.nextInt(3)};
    final int[] acumuloDesvios = {0};
    final boolean[] vezdequem = {true};

    int ataquesTurnos = 0;

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
            personagens = db.personagensDao().buscaPer(arg.getInt("idPerso"));
            vidaInimigo = inimigos.getHp();
            estaminaInimigo = inimigos.getEstamina();

            vidaJogador = personagens.getHp();
            estaminaJogador = personagens.getEstamina();
            energiaJogador = personagens.getEnergia();

            binding.nomeInimigo.setText(inimigos.getNome());
            binding.hakiInimigo.setText("Nivel: "+String.valueOf(arg.getInt("nivelIni"))+"\nARM: "+String.valueOf(inimigos.getHakiarm())+"\nOBS: "+String.valueOf(inimigos.getHakiobs()));
            binding.nomeJogador.setText(personagens.getNome());
            binding.energia.setText("Energia: "+String.valueOf(energiaJogador)+" / "+String.valueOf(personagens.getEnergia()));

            binding.vidaInimigo.setText("Vida Inimigo: \n"+String.valueOf(vidaInimigo)+" / "+String.valueOf(inimigos.getHp()));
            binding.vidaJogador.setText("Vida Jogador: \n"+String.valueOf(vidaJogador)+" / "+String.valueOf(personagens.getHp()));

            if(personagens.getHakirei() == 0){
                binding.hakiBnt.setVisibility(View.INVISIBLE);
            }
            binding.armaBnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(vezdequem[0]){
                        TrunoJogador(arg);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View botoes = inflater.inflate(R.layout.combate_botoes, null);
                    builder.setView(botoes);

                    final AlertDialog ataques = builder.create();

                    ataques.show();
                }
            });
        }
    }


    public void TrunoJogador(Bundle arg){
        int dano = CalculaDano(inimigos.getDefesa() / 100, personagens.getForca());
        if (validaChance(inimigos.getIntuicao())) {
            acumuloDesvios[0] = acumuloDesvios[0] + 1;
            TextView novo = new TextView(getContext());
            novo.setText(inimigos.getNome() + " desviou do seu ataque");
            binding.informacoes.addView(novo);
            vezdequem[0] = false;
            TurnoInimigo(arg);
        } else {
            ataquesTurnos ++;
            qntsDesvios[0] = random.nextInt(3);
            TextView novo = new TextView(getContext());
            novo.setText(personagens.getNome() + " atacou com a " + personagens.getArmas());
            binding.informacoes.addView(novo);
            vidaInimigo = vidaInimigo - dano;
            if(VerficaVida(vidaInimigo)){
                binding.vidaInimigo.setText("Vida Inimigo: \n" + String.valueOf(0) + " / " + String.valueOf(inimigos.getHp()));
                Nivelamento(personagens,arg);
                androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                dlg.setMessage("Vitoria \n Retornar para as informações do personagem para a destribuição dos pontos");
                dlg.show();
                Bundle bundle = new Bundle();
                bundle.putInt("idU",arg.getInt("idU"));
                bundle.putInt("idPerso",arg.getInt("idPerso"));
                binding.armaBnt.setVisibility(View.INVISIBLE);
                binding.hakiBnt.setVisibility(View.INVISIBLE);
                binding.akumaBnt.setVisibility(View.INVISIBLE);

                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.batalha2, true)
                        .build();
                NavHostFragment.findNavController(Batalha.this)
                        .navigate(R.id.action_batalha2_to_infoPersonagenUser,bundle,navOptions);//*/
            }else {
                binding.vidaInimigo.setText("Vida Inimigo: \n" + String.valueOf(vidaInimigo) + " / " + String.valueOf(inimigos.getHp()));
                vezdequem[0] = false;
                TurnoInimigo(arg);
            }
        }
        if(validaChance(personagens.getAgilidade()) && ataquesTurnos < 2){
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
        int dano = CalculaDano((personagens.getDefesa()) / 100, inimigos.getForca());
        if (validaChance(personagens.getIntuicao())) {
            acumuloDesvios[0] = acumuloDesvios[0] + 1;
            TextView novo = new TextView(getContext());
            novo.setText(personagens.getNome() + " desviou do "+inimigos.getNome());
            binding.informacoes.addView(novo);
            vezdequem[0] = true;
        }else {
            ataquesTurnos ++;
            acumuloDesvios[0] = 0;
            TextView novo = new TextView(getContext());
            novo.setText(inimigos.getNome() + " atacou com a " + inimigos.getArmas());
            binding.informacoes.addView(novo);
            vidaJogador = vidaJogador - dano;
            if(VerficaVida(vidaJogador)) {
                binding.vidaJogador.setText("Vida Jogador: \n" + String.valueOf(0) + " / " + String.valueOf(personagens.getHp()));
                androidx.appcompat.app.AlertDialog.Builder dlg = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                dlg.setMessage("Derrota \n Retornar para as informações do personagem para a destribuição dos pontos");
                dlg.show();
                Bundle bundle = new Bundle();
                bundle.putInt("idU",arg.getInt("idU"));
                bundle.putInt("idPerso",arg.getInt("idPerso"));
                binding.armaBnt.setVisibility(View.INVISIBLE);
                binding.hakiBnt.setVisibility(View.INVISIBLE);
                binding.akumaBnt.setVisibility(View.INVISIBLE);

                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.batalha2, true)
                        .build();
                NavHostFragment.findNavController(Batalha.this)
                        .navigate(R.id.action_batalha2_to_infoPersonagenUser,bundle,navOptions);//*/
            }else {
                binding.vidaJogador.setText("Vida Jogador: \n" + String.valueOf(vidaJogador) + " / " + String.valueOf(personagens.getHp()));
                vezdequem[0] = true;
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

    //Nivela o inimigo para aconpanhar o level do jogador
    public Inimigos nivelaInimigo(int nivelAtual, int idInim){
        Inimigos inimigos = db.inimigosDao().buscaInimigos(idInim);
        int hakiarm = 25;
        int hakiobs = 16;
        Random random = new Random();
        //Força, estamina, agilidade e HP
        inimigos.setAgilidade(inimigos.getAgilidade()+nivelAtual);
        if(inimigos.getAgilidade() > 75) inimigos.setAgilidade(75);
        inimigos.setForca(inimigos.getForca()+nivelAtual);
        inimigos.setEstamina(inimigos.getEstamina()+nivelAtual);
        inimigos.setHp(inimigos.getHp()+nivelAtual);

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

    public void Nivelamento(Personagens personagens,Bundle arg){
        int idini = arg.getInt("idInimi");
        int hakiobs = 16;
        int hakiarm = 25;
        Usuario usuario = db.usuarioDao().buscaUsuario(arg.getInt("idU"));
        List<Integer> inimigoList = usuario.getInimigos();
        if(!procuraRepeticao(inimigoList,idini)) inimigoList.add(idini);
        usuario.setInimigos(inimigoList);


        int nivelAtual = personagens.getNivel() + 1;
        personagens.setNivel(nivelAtual);
        personagens.setVitorias5(personagens.getVitorias5()+1);
        personagens.setVitorias10(personagens.getVitorias10()+1);
        personagens.setVitorias25(personagens.getVitorias25()+1);
        if(personagens.getAgilidade() <= 75) {
            personagens.setPontos(personagens.getPontos()+4);
        }else{
            personagens.setAgilidade(75);
            personagens.setPontos(personagens.getPontos()+3);
        }

        if(personagens.getEstamina() <= 14){
            personagens.setEnergia(5);
        } else if (personagens.getEstamina() <= 24) {
            personagens.setEnergia(8);
        }else if (personagens.getEstamina() <= 34) {
            personagens.setEnergia(11);
        }else if (personagens.getEstamina() <= 44) {
            personagens.setEnergia(14);
        }else if (personagens.getEstamina() <= 54) {
            personagens.setEnergia(17);
        }else if (personagens.getEstamina() <= 64) {
            personagens.setEnergia(20);
        }else if (personagens.getEstamina() <= 74) {
            personagens.setEnergia(23);
        }else if (personagens.getEstamina() <= 84) {
            personagens.setEnergia(26);
        }else if (personagens.getEstamina() <= 94) {
            personagens.setEnergia(29);
        }else{
            personagens.setEnergia(32);
        }

        if(personagens.getVitorias5() == 5 && !personagens.getAssociacao().equals("Marinha")){
            //Player vence uma Davy Back Fight e pode trocar aleatoriamente de tripulação
            int qualTripulacao = new Random().nextInt(db.tripulacaoDao().quantosTripulacao())+1;
            personagens.setIdTripula(qualTripulacao);
            List<Integer> tripulacaoList = usuario.getTripulacao();
            if(!procuraRepeticao(tripulacaoList,qualTripulacao)) tripulacaoList.add(qualTripulacao);
            usuario.setTripulacao(tripulacaoList);
            personagens.setVitorias5(0);
        }
        if (personagens.getVitorias10() == 10 && personagens.getNivel() < 91){
            boolean jafoi = false;
            do {
                int qualHaki = new Random().nextInt(3);
                String[] haki = {"Haki da Observação", "Haki do Armamento", "Haki do rei"};
                String seuHaki = haki[qualHaki];
                switch (seuHaki) {
                    case "Haki da Observação":
                        if (personagens.getHakiobs() < 3) {
                            personagens.setHakiobs(personagens.getHakiobs() + 1);
                            personagens.setIntuicao(personagens.getIntuicao()+(personagens.getHakiobs()*hakiobs));
                        } else {
                            jafoi = true;
                        }
                        break;
                    case "Haki do Armamento":
                        if (personagens.getHakiarm() < 3) {
                            personagens.setHakiarm(personagens.getHakiarm() + 1);
                            personagens.setDefesa(personagens.getDefesa()+(personagens.getHakiarm()*hakiarm));
                        } else {
                            jafoi = true;;
                        }
                        break;
                    case "Haki do rei":
                        if (personagens.getHakirei() < 3) {
                            personagens.setHakirei(personagens.getHakirei() + 1);
                        } else {
                            jafoi = true;
                        }
                        break;
                    default:
                        jafoi = true;
                        break;
                }
            }while (jafoi);
            personagens.setVitorias10(0);

        }
        if (personagens.getVitorias25() == 25 && nivelAtual <= 100) {
            switch (personagens.getTitulo()){
                //Pirata
                case "Bandido":
                    personagens.setTitulo("Pirata");
                    personagens.setRecompensa("B$ 30.000.000");
                    personagens.setOrigem("Grand Line");
                    break;
                case "Pirata":
                    personagens.setTitulo("Super Nova");
                    personagens.setRecompensa("B$ 250.000.000");
                    personagens.setOrigem("New World");
                    break;
                case "Super Nova":
                    personagens.setTitulo("Shichibukai");
                    personagens.setRecompensa("B$ 1.500.000.000");
                    personagens.setOrigem("New World (Pós-Dressrosa)");
                    break;
                case "Shichibukai":
                    personagens.setTitulo("Yonko");
                    personagens.setRecompensa("B$ 3.000.000.000");
                    personagens.setOrigem("New World (Pós-Wano)");
                    break;
                    //Marinha
                case "Aprendiz de Marinheiro":
                    personagens.setTitulo("Sargento-Mor");
                    personagens.setRecompensa("★★");
                    personagens.setOrigem("Grand Line");
                    break;
                case "Sargento-Mor":
                    personagens.setTitulo("Capitão da Marinha");
                    personagens.setRecompensa("★★★★★");
                    personagens.setOrigem("New World");
                    break;
                case "Capitão da Marinha":
                    personagens.setTitulo("Vice-Almirante");
                    personagens.setRecompensa("♛♛♛");
                    personagens.setOrigem("New World (Pós-Dressrosa)");
                    break;
                case "Vice-Almirante":
                    personagens.setTitulo("Almirante");
                    personagens.setRecompensa("♛♛♛♛♛");
                    personagens.setOrigem("New World (Pós-Wano)");
                    break;
                default:
                    Log.d("Batalha Swithc","Erro no swithc");
                    break;
            }
            personagens.setVitorias25(0);
        }
        usuario.setIdUser(arg.getInt("idU"));
        db.usuarioDao().upgrade(usuario);

        personagens.setIdpersonagens(arg.getInt("idPerso"));
        db.personagensDao().upgrade(personagens);
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