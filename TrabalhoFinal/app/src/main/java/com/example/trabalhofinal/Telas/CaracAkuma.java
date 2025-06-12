package com.example.trabalhofinal.Telas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
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
            binding.tipo.setText(akumas.getTipo());
            binding.descricao.setText(akumas.getDescricao());
            binding.usuarios.setText(akumas.getUsuarios());

            if(!akumas.getFotoakuma().equals("Nao Fotografada")) {
                int resID = requireContext().getResources().getIdentifier(akumas.getFotoakuma(), "drawable", getContext().getPackageName());
                binding.fotoFruta.setImageResource(resID);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}