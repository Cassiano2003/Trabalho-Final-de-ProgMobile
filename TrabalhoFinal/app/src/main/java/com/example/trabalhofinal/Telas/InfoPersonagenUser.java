package com.example.trabalhofinal.Telas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.Personagens;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.InfoPlayerBinding;

public class InfoPersonagenUser extends Fragment {
    private InfoPlayerBinding binding;

    private AppDataBase db;

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

        Bundle arg = getArguments();
        if(arg != null){
            Personagens personagens = db.personagensDao().buscaPer(arg.getInt("idPerso"));

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

            binding.vida.setText("HP: "+String.valueOf(personagens.getHp()));
            binding.forca.setText("Força: "+String.valueOf(personagens.getForca()));
            binding.estamina.setText("Estamina: "+String.valueOf(personagens.getEstamina()));
            binding.agilidade.setText("Agilidade: "+String.valueOf(personagens.getAgilidade()));
            binding.defesa.setText("Defesa: "+String.valueOf(personagens.getDefesa()));
            binding.intuica.setText("Intuição: "+String.valueOf(personagens.getIntuicao()));

            if(personagens.getAkumaNoMi() != 0){
                Akumas akumas = db.akumaDao().buscaAkuma(personagens.getAkumaNoMi());
                binding.akuma.setText(akumas.getNome());
            }else {
                binding.akuma.setText("Nao tem Akuma No Mi");
                binding.ataques.setVisibility(View.INVISIBLE);
                binding.ataquesLinar.setVisibility(View.INVISIBLE);
            }
        }

        binding.batalha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("idU",arg.getInt("idU"));
                bundle.putInt("idPerso",arg.getInt("idPerso"));
                bundle.putInt("idInimi",0);

                NavHostFragment.findNavController(InfoPersonagenUser.this)
                        .navigate(R.id.action_infoPersonagenUser_to_batalha2,bundle);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
