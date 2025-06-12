package com.example.trabalhofinal.Telas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Personagens;
import com.example.trabalhofinal.Tabelas.Usuario;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.ListPersonagensBinding;

import java.util.ArrayList;
import java.util.List;

public class ListPersonagens extends Fragment {
    private ListPersonagensBinding binding;

    private AppDataBase db;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ListPersonagensBinding.inflate(inflater, container, false);
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
            List<Personagens> personagens = new ArrayList<>();
            for (int i = 0; i < listaPers.size(); i++) {
                personagens.add(db.personagensDao().buscaPer(listaPers.get(i)));
            }
            ArrayAdapter<Personagens> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_activated_1, personagens);
            binding.lista.setAdapter(adapter);

            binding.novo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idU", idU);

                    NavHostFragment.findNavController(ListPersonagens.this)
                            .navigate(R.id.action_listPersonagens_to_criaPersonagen2, bundle);
                }
            });

            binding.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Personagens p = personagens.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt("idPerso", p.getIdpersonagens());
                    bundle.putInt("idU", idU);

                    NavHostFragment.findNavController(ListPersonagens.this)
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
                            idnome = db.personagensDao().buscaID(bus);
                            if (idnome > 0) {
                                Personagens personagens = db.personagensDao().buscaPer(idnome);
                                if (procuraPersonagen(usuario.getPersonagens(),personagens.getIdpersonagens())) {
                                    List<Personagens> p = new ArrayList<>();
                                    p.add(personagens);
                                    ArrayAdapter<Personagens> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_activated_1, p);
                                    binding.lista.setAdapter(adapter);
                                } else {
                                    Toast.makeText(requireContext(), "Personagen nao encontrado", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(requireContext(), "Personagen nao encontrado", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(requireContext(), "Entrada Invalida", Toast.LENGTH_LONG).show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
