package com.example.trabalhofinal.Telas;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trabalhofinal.MainActivity;
import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Inimigos;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.CarcInimigosBinding;

import java.util.Random;

public class CaracInimigo extends Fragment {
    private CarcInimigosBinding binding;

    private AppDataBase db;
    private MediaPlayer media;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CarcInimigosBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());

        Bundle arg = getArguments();
        if(arg != null){
            int idIni = arg.getInt("i");
            Inimigos inimigos = db.inimigosDao().buscaInimigos(idIni);
            binding.nome.setText(inimigos.getNome());
            binding.apelido.setText("Apelido: "+inimigos.getApelidoNomePopular());
            binding.tituloDe.setText("Título: "+inimigos.getTitulo());
            binding.genero.setText("Gênero: "+inimigos.getSexo());
            binding.raca.setText("Raça: "+inimigos.getRaca());
            binding.akuma.setText("Akuma No Mi: "+inimigos.getAkumaNoMi());
            binding.arma.setText("Arma: "+inimigos.getArmas());
            binding.mar.setText("Origem: "+inimigos.getOrigem());
            binding.associaao.setText("Associação: "+inimigos.getAssociacao());
            binding.tripulacao.setText("Tripulação/Organização: "+inimigos.getTripulacaoOrganizacao());
            binding.recompenca.setText("Recompensa: "+inimigos.getRecompensa());

            if(!inimigos.getFotocatalogo().equals("sem foto")){
                int resID = requireContext().getResources().getIdentifier(inimigos.getFotocatalogo(), "drawable", getContext().getPackageName());
                binding.fotoPersonagen.setImageResource(resID);
            }else{
                int teste = R.drawable.roronoazoroanimeposttimeskip;
                binding.fotoPersonagen.setImageResource(teste);
            }
            int[] soundResources = getSoundResources(inimigos.getNome());
            if (soundResources != null && soundResources.length > 0) {
                ((MainActivity) getActivity()).mediaPlayer.pause();
                int selectedSound = soundResources[new Random().nextInt(soundResources.length)];
                MediaPlayer media = MediaPlayer.create(requireContext(), selectedSound);
                media.start();
                media.setOnCompletionListener(mp -> {
                    mp.release();
                    ((MainActivity) getActivity()).mediaPlayer.start();
                });
            }
        }
    }

    private int[] getSoundResources(String characterName) {
        switch (characterName) {
            case "Scratchmen Apoo":
                return new int[]{R.raw.apoo_laugh};
            case "Arlong":
                return new int[]{R.raw.arlong_laugh};
            case "Bartolomeo":
                return new int[]{R.raw.bartolomeo_scream, R.raw.bartolomeo_tema};
            case "Bellamy":
                return new int[]{R.raw.bellamy_laugh};
            case "Bentham":
                return new int[]{R.raw.bentham_laugh};
            case "Marshall D. Teach":
                return new int[]{R.raw.blackbeard_laugh};
            case "Brook":
                return new int[]{R.raw.brook_laugh, R.raw.brook_tema};
            case "Buggy":
                return new int[]{R.raw.buggy_tema};
            case "Jesus Burgess":
                return new int[]{R.raw.burgess_laugh};
            case "Ceaser Clown":
                return new int[]{R.raw.caesar_laugh};
            case "Catarina Devon":
                return new int[]{R.raw.catarina_laugh};
            case "Tony Tony Chopper":
                return new int[]{R.raw.choppersong, R.raw.chopper_tema, R.raw.chopper_tsundere};
            case "Crocodile":
                return new int[]{R.raw.crocodile_laugh};
            case "Donquixote Doflamingo":
                return new int[]{R.raw.doflamingo_laugh};
            case "Enel":
                return new int[]{R.raw.enel_laugh};
            case "Foxy":
                return new int[]{R.raw.choppersong, R.raw.chopper_tema, R.raw.chopper_tsundere};
            case "Franky":
                return new int[]{R.raw.franky_supeeer, R.raw.franky_tema};
            case "Fukurou":
                return new int[]{R.raw.fukurou_laugh};
            case "Monkey D. Garp":
                return new int[]{R.raw.garp_laugh};
            case "Hogback":
                return new int[]{R.raw.hogback_laugh};
            case "Jinbe":
                return new int[]{R.raw.jinbe_laugh, R.raw.jinbe_tema};
            case "Kaido":
                return new int[]{R.raw.kaido_laugh};
            case "Kikunojo":
                return new int[]{R.raw.kanjuro_laugh};
            case "Kawamatsu":
                return new int[]{R.raw.kawamatsu_laugh};
            case "Eustass Kid":
                return new int[]{R.raw.kid_tema};
            case "Killer":
                return new int[]{R.raw.killer_laugh};
            case "Trafalgar D. Water Law":
                return new int[]{R.raw.law_tema};
            case "Charlotte Linlin":
                return new int[]{R.raw.linlin_laugh};
            case "Monkey D. Luffy":
                return new int[]{R.raw.luffy_laugh, R.raw.luffy_tema, R.raw.luffy_tema_gear5, R.raw.luffy_tema_gear5, R.raw.luffy_tema_wano};
            case "Gecko Moria":
                return new int[]{R.raw.moria_laugh};
            case "Nami":
                return new int[]{R.raw.nami_tema};
            case "Nekomamushi":
                return new int[]{R.raw.nekomamushi_laugh};
            case "Perona":
                return new int[]{R.raw.perona_laugh};
            case "Charlotte Perospero":
                return new int[]{R.raw.perospero_laugh};
            case "Pica":
                return new int[]{R.raw.pica_laugh};
            case "Silvers Rayleigh":
                return new int[]{R.raw.rayleigh_laugh};
            case "Nico Robin":
                return new int[]{R.raw.robin_laugh, R.raw.robin_tema};
            case "Gol D. Roger":
                return new int[]{R.raw.roger_laugh};
            case "Vinsmoke Sanji":
                return new int[]{R.raw.sanji_tema,R.raw.sanji_nami,R.raw.sanji_robin};
            case "Shanks":
                return new int[]{R.raw.shanks_laugh};
            case "Trebol":
                return new int[]{R.raw.trebol_laugh};
            case "Usopp":
                return new int[]{R.raw.usopp_tema, R.raw.sogeking};
            case "Vasco Shot":
                return new int[]{R.raw.vasco_laugh};
            case "Nefertari Vivi":
                return new int[]{R.raw.vivi_tema};
            case "Wapol":
                return new int[]{R.raw.wapol_laugh};
            case "Edward Newgate":
                return new int[]{R.raw.whitebeard_laugh};
            case "Yamato":
                return new int[]{R.raw.yamato_tema};
            case "Roronoa Zoro":
                return new int[]{R.raw.zoro_tarzan, R.raw.zoro_tema, R.raw.zoro_tema_wano};
            default:
                return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(media != null){
            media.release();
            ((MainActivity) getActivity()).mediaPlayer.start();
        }
        binding = null;
    }
}
