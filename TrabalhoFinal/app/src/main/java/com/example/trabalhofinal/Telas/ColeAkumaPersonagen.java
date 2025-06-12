package com.example.trabalhofinal.Telas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.ColeAkumaPersonaBinding;

public class ColeAkumaPersonagen  extends Fragment {
    private ColeAkumaPersonaBinding binding;

    private AppDataBase db;
    private String qual;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ColeAkumaPersonaBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());
        Bundle arg = getArguments();

        //Criar os vetores apartir de qual escolha
        if(arg != null){
            qual = arg.getString("g");
            switch (qual){
                case "Akuma No Mi":
                    break;
                case "Tribulação":
                    break;
                case "Personagens":
                    break;
            }

            binding.grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("valor", i);

                    switch (qual){
                        case "Akuma No Mi":
                            NavHostFragment.findNavController(ColeAkumaPersonagen.this)
                                    .navigate(R.id.action_coleAkumaPersonagen_to_caracAkuma, bundle);
                            break;
                        case "Tribulação":
                            NavHostFragment.findNavController(ColeAkumaPersonagen.this)
                                    .navigate(R.id.action_coleAkumaPersonagen_to_caracBando, bundle);
                            break;
                        case "Personagens":
                            NavHostFragment.findNavController(ColeAkumaPersonagen.this)
                                    .navigate(R.id.action_coleAkumaPersonagen_to_caracPersonagen, bundle);
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
