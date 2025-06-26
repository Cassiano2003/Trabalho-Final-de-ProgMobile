package com.example.trabalhofinal.Telas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.MainActivity;
import com.example.trabalhofinal.R;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.InicioBinding;

import java.io.File;

public class Inicio extends Fragment {

    private InicioBinding binding;
    private AppDataBase db;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = InicioBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());
        int qntUsuarios = db.usuarioDao().quantosUsuarios();

        binding.cadastro.setOnClickListener(v ->
                NavHostFragment.findNavController(Inicio.this)
                        .navigate(R.id.action_inicio_to_cadastro)
        );

        if(qntUsuarios > 0){
            binding.login.setVisibility(View.VISIBLE);
        }

        binding.login.setOnClickListener(v ->
                NavHostFragment.findNavController(Inicio.this)
                .navigate(R.id.action_inicio_to_login)
        );

        if(((MainActivity) getActivity()).musicaPausada != 0){
            ((MainActivity) getActivity()).musicaPausada = 0;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}