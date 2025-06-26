package com.example.trabalhofinal.Telas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trabalhofinal.Tabelas.Tripulacoes;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.CarcTripulacaoBinding;

public class CaracTripulacao extends Fragment {
    private CarcTripulacaoBinding binding;

    private AppDataBase db;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CarcTripulacaoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());

        Bundle arg = getArguments();
        if(arg != null){
            Tripulacoes tripulacoes = db.tripulacaoDao().buscaTripulacao(arg.getInt("t"));

            binding.nome.setText(tripulacoes.getNome());
            binding.capitao.setText("Capitão: "+tripulacoes.getCapitao());
            binding.integrantes.setText("Integrantes: \n"+tripulacoes.integrantes);
            int resID = requireContext().getResources().getIdentifier(tripulacoes.getFoto(), "drawable", getContext().getPackageName());
            binding.fotoFruta.setImageResource(resID);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
