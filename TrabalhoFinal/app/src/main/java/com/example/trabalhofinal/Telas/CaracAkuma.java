package com.example.trabalhofinal.Telas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.AtaqueAkumaNoMi;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.TabelasDao.AtaqueAkumasDao;
import com.example.trabalhofinal.databinding.CarcAkumaBinding;

public class CaracAkuma   extends Fragment {
    private CarcAkumaBinding binding;

    private AppDataBase db;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CarcAkumaBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());

        Bundle arg = getArguments();
        if(arg != null){
            Akumas akumas = db.akumaDao().buscaAkuma(arg.getInt("f"));

            binding.nome.setText(akumas.getNome());
            binding.traducao.setText(akumas.getNome_t());
            binding.tipo.setText("Tipo: "+akumas.getTipo());
            binding.descricao.setText("Descrição: \n"+akumas.getDescricao());
            binding.usuarios.setText("Usuários: "+akumas.getUsuarios());

            if(!akumas.getFotoakuma().equals("Nao Fotografada")) {
                int resID = requireContext().getResources().getIdentifier(akumas.getFotoakuma(), "drawable", getContext().getPackageName());
                binding.fotoFruta.setImageResource(resID);
            }
            AtaqueAkumaNoMi ataqueAkumaNoMi = db.ataqueAkumasDao().buscaAtaqueAkumaNoMi(akumas.getIdataques());
            binding.ataques.setVisibility(View.VISIBLE);
            int qntAtaques = ataqueAkumaNoMi.getQntataques();
            String[] nome_do_ataque = ataqueAkumaNoMi.getNomeDoAtaque();
            String[] descricao = ataqueAkumaNoMi.getDescricao();
            String[] tipo_de_ataque = ataqueAkumaNoMi.getTipoDeAtaque();

            int[] custo = ataqueAkumaNoMi.getCusto();
            int[] hp_jogador = ataqueAkumaNoMi.getHpjogador();
            int[] forca_jogador = ataqueAkumaNoMi.getForcajogador();
            int[] estamina_jogador = ataqueAkumaNoMi.getEstaminajogador();
            int[] agilidade_jogador = ataqueAkumaNoMi.getAgilidadejogador();
            int[] defesa_jogador = ataqueAkumaNoMi.getDefesajogador();
            int[] intuicao_jogador = ataqueAkumaNoMi.getIntuicaojogador();
            int[] dano_jogador = ataqueAkumaNoMi.getDanojogador();

            int[] hp_inimigo = ataqueAkumaNoMi.getHpinimigo();
            int[] forca_inimigo = ataqueAkumaNoMi.getForcainimigo();
            int[] estamina_inimigo = ataqueAkumaNoMi.getEstaminainimigo();
            int[] agilidade_inimigo = ataqueAkumaNoMi.getAgilidadeinimigo();
            int[] defesa_inimigo = ataqueAkumaNoMi.getDefesainimigo();
            int[] intuicao_inimigo = ataqueAkumaNoMi.getIntuicaoinimigo();
            int[] dano_inimigo = ataqueAkumaNoMi.getDanoinimigo();

            for(int i=0; i < qntAtaques; i++){
                TextView novo = new TextView(requireContext());
                novo.setText(nome_do_ataque[i]+" \n"+descricao[i]+" \n"+tipo_de_ataque[i]+" \n"+String.valueOf(custo[i])+" \n"+
                        String.valueOf(hp_jogador[i])+" \n"+String.valueOf(forca_jogador[i])+" \n"+String.valueOf(estamina_jogador[i])
                        +" \n"+String.valueOf(agilidade_jogador[i])+" \n"+String.valueOf(defesa_jogador[i])+" \n"+String.valueOf(intuicao_jogador[i])
                        +" \n"+String.valueOf(dano_jogador[i])+" \n"+
                        String.valueOf(hp_inimigo[i])+" \n"+String.valueOf(forca_inimigo[i])+" \n"+String.valueOf(estamina_inimigo[i])
                        +" \n"+String.valueOf(agilidade_inimigo[i])+" \n"+String.valueOf(defesa_inimigo[i])+" \n"+String.valueOf(intuicao_inimigo[i])
                        +" \n"+String.valueOf(dano_inimigo[i]));
                novo.setTextSize(15);
                binding.ataquesakuma.addView(novo);
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}