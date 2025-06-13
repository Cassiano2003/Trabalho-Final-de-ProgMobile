package com.example.trabalhofinal.Telas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Inimigos;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.CarcInimigosBinding;

public class CaracInimigo extends Fragment {
    private CarcInimigosBinding binding;

    private AppDataBase db;

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
            binding.genero.setText("Genero: "+inimigos.getSexo());
            binding.raca.setText("Raça: "+inimigos.getRaca());
            binding.akuma.setText("Usuario de Akuma No Mi: "+inimigos.getAkumaNoMi());
            binding.arma.setText("Arma: "+inimigos.getArmas());
            binding.mar.setText("Origem: "+inimigos.getOrigem());
            binding.associaao.setText("Associação: "+inimigos.getAssociacao());
            binding.tripulacao.setText("Tripulação/Organização: "+inimigos.getTripulacaoOrganizacao());
            binding.recompenca.setText("Recompensa: "+inimigos.getRecompensa());

            if(!inimigos.getFotointeira().equals("sem foto")){
                int resID = requireContext().getResources().getIdentifier(inimigos.getFotointeira(), "drawable", getContext().getPackageName());
                binding.fotoPersonagen.setImageResource(resID);
            }else{
                int teste = R.drawable.roronoazoroanimeposttimeskip;
                binding.fotoPersonagen.setImageResource(teste);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}