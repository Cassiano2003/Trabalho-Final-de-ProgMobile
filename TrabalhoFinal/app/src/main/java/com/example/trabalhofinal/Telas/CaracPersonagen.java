package com.example.trabalhofinal.Telas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.CarcPersonagensBinding;

public class CaracPersonagen  extends Fragment {
    private CarcPersonagensBinding binding;

    private AppDataBase db;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CarcPersonagensBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}