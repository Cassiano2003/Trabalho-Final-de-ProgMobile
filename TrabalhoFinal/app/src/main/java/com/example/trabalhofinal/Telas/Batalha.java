package com.example.trabalhofinal.Telas;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.BatalhaBinding;

public class Batalha    extends Fragment {
    private BatalhaBinding binding;

    private AppDataBase db;

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

        binding.akumaBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View botoes = inflater.inflate(R.layout.combate_botoes,null);
                builder.setView(botoes);

                final AlertDialog ataques = builder.create();

                ataques.show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}