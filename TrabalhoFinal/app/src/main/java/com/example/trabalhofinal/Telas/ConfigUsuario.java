package com.example.trabalhofinal.Telas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.Tabelas.Usuario;
import com.example.trabalhofinal.databinding.ConfigUsuarioBinding;

public class ConfigUsuario extends Fragment {
    private ConfigUsuarioBinding binding;

    private AppDataBase db;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ConfigUsuarioBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());

        Bundle arg = getArguments();
        if(arg != null){
            int idU = arg.getInt("id");
            Usuario user = db.usuarioDao().buscaUsuario(idU);
            binding.nome.setText(user.getNome());
            Bitmap fotoBitmap = BitmapFactory.decodeByteArray(user.getFoto(), 0, user.getFoto().length);
            binding.foto.setImageBitmap(fotoBitmap);
        }

        binding.seusPersonagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idU = arg.getInt("id");
                Bundle bundle = new Bundle();
                bundle.putInt("idU",idU);

                NavHostFragment.findNavController(ConfigUsuario.this)
                        .navigate(R.id.action_configUsuario_to_listPersonagens,bundle);
            }
        });
        binding.colAkumas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("g","Akuma No Mi");

                NavHostFragment.findNavController(ConfigUsuario.this)
                        .navigate(R.id.action_configUsuario_to_coleAkumaPersonagen,bundle);
            }
        });

        binding.colBandeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("g","Tribulação");

                NavHostFragment.findNavController(ConfigUsuario.this)
                        .navigate(R.id.action_configUsuario_to_coleAkumaPersonagen,bundle);
            }
        });

        binding.colPersonagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("g","Personagens");

                NavHostFragment.findNavController(ConfigUsuario.this)
                        .navigate(R.id.action_configUsuario_to_coleAkumaPersonagen,bundle);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
