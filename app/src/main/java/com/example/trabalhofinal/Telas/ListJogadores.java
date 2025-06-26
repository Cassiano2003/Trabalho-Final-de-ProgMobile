package com.example.trabalhofinal.Telas;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Jogador;
import com.example.trabalhofinal.Tabelas.Usuario;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.ListJogadoresBinding;

import java.util.ArrayList;
import java.util.List;

public class ListJogadores extends Fragment {
    private ListJogadoresBinding binding;

    private AppDataBase db;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ListJogadoresBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());

        Bundle arg = getArguments();
        if (arg != null) {
            int idU = arg.getInt("idU");
            Usuario usuario = db.usuarioDao().buscaUsuario(idU);
            Log.d("Usuario",usuario.toString());
            List<Integer> listaPers = usuario.getPersonagens();
            List<Jogador> personagens = new ArrayList<>();
            for (int i = 0; i < listaPers.size(); i++) {
                personagens.add(db.jogadorDao().buscaPer(listaPers.get(i)));
            }
            ArrayAdapter<Jogador> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_activated_1, personagens);
            binding.lista.setAdapter(adapter);

            binding.novo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idU", idU);

                    NavHostFragment.findNavController(ListJogadores.this)
                            .navigate(R.id.action_listPersonagens_to_criaPersonagen2, bundle);
                }
            });

            ajustarCorTexto(binding.campoBusca);

            binding.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Jogador p = personagens.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idPerso", p.getIdpersonagens());
                    bundle.putInt("idU", idU);

                    NavHostFragment.findNavController(ListJogadores.this)
                            .navigate(R.id.action_listPersonagens_to_infoPersonagenUser, bundle);
                }
            });

            binding.busca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String bus = binding.campoBusca.getText().toString();
                        int idnome = -1;
                        if (!bus.isEmpty()) {
                            idnome = db.jogadorDao().buscaID(bus);
                            if (idnome > 0) {
                                Jogador jogador = db.jogadorDao().buscaPer(idnome);
                                if (procuraPersonagen(usuario.getPersonagens(), jogador.getIdpersonagens())) {
                                    List<Jogador> p = new ArrayList<>();
                                    p.add(jogador);
                                    ArrayAdapter<Jogador> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_activated_1, p);
                                    binding.lista.setAdapter(adapter);
                                } else {
                                    Toast.makeText(requireContext(), "Personagem não encontrado", Toast.LENGTH_LONG).show();
                                    binding.lista.setAdapter(adapter);
                                }
                            } else {
                                Toast.makeText(requireContext(), "Personagem não encontrado", Toast.LENGTH_LONG).show();
                                binding.lista.setAdapter(adapter);
                            }
                        } else {
                            Toast.makeText(requireContext(), "Entrada Inválida", Toast.LENGTH_LONG).show();
                            binding.lista.setAdapter(adapter);
                        }
                    } catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    public boolean procuraPersonagen(List<Integer> array, int targetNumber) {
        for (int number : array) {
            if (number == targetNumber) {
                return true;
            }
        }
        return false;
    }

    public void ajustarCorTexto(TextView textView) {
        int nightModeFlags = textView.getContext()
                .getResources()
                .getConfiguration()
                .uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                // Tema escuro → fundo provavelmente escuro → texto claro
                textView.setTextColor(Color.WHITE);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
            default:
                // Tema claro → fundo claro → texto escuro
                textView.setTextColor(Color.BLACK);
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
