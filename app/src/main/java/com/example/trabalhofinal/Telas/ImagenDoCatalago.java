package com.example.trabalhofinal.Telas;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.Inimigos;
import com.example.trabalhofinal.Tabelas.Tripulacoes;
import com.example.trabalhofinal.Tabelas.Usuario;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.TabelasDao.adaptador;
import com.example.trabalhofinal.databinding.ImagenDoCatalagoBinding;

import java.util.List;

public class ImagenDoCatalago extends Fragment {
    private ImagenDoCatalagoBinding binding;

    private AppDataBase db;
    private String qual;
    int[] gridImagens;
    int[] idgeral;
    private int qnt_adicionadas = 0;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ImagenDoCatalagoBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());
        Bundle arg = getArguments();

        //Criar os vetores apartir de qual escolha
        if(arg != null){
            qual = arg.getString("g");
            Usuario usuario = db.usuarioDao().buscaUsuario(arg.getInt("idU"));
            switch (qual){
                case "Akuma No Mi":
                    binding.getRoot().setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.catalogo_akuma));
                    binding.titulo.setTextColor(Color.WHITE);
                    binding.quantidade.setTextColor(Color.WHITE);
                    List<Integer> akuma = usuario.getAkumanomis();
                    int qnt_akumas = db.akumaDao().quantosAkumas();
                    gridImagens = new int[qnt_akumas];
                    idgeral = new int[qnt_akumas];
                    qnt_adicionadas = 0;
                    if(!akuma.isEmpty()) {
                        for (int i = 0; i < akuma.size(); i++) {
                            idgeral[i] = akuma.get(i);
                            Akumas akumas = db.akumaDao().buscaAkuma(akuma.get(i));
                            int resID = requireContext().getResources().getIdentifier(akumas.getFotoakuma(), "drawable", getContext().getPackageName());
                            gridImagens[i] = resID;
                            qnt_adicionadas ++;
                        }
                    }
                    binding.titulo.setText("Akumas No Mi");
                    binding.quantidade.setText(String.valueOf(qnt_adicionadas)+" / "+String.valueOf(qnt_akumas));
                    break;
                case "Tribulação":
                    binding.getRoot().setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.catalogo_tripulacao));
                    List<Integer> tripulacoesList = usuario.getTripulacao();
                    int qnt_tripu = db.tripulacaoDao().quantosTripulacao();
                    gridImagens = new int[qnt_tripu];
                    idgeral = new int[qnt_tripu];
                    qnt_adicionadas = 0;
                    if(!tripulacoesList.isEmpty()) {
                        for (int i = 0; i < tripulacoesList.size(); i++) {
                            Tripulacoes tripulacoes = db.tripulacaoDao().buscaTripulacao(tripulacoesList.get(i));
                            idgeral[i] = tripulacoes.getIdtripulacao();
                            int resID = requireContext().getResources().getIdentifier(tripulacoes.getFoto(), "drawable", getContext().getPackageName());
                            gridImagens[i] = resID;
                            qnt_adicionadas++;
                        }
                    }
                    binding.titulo.setText("Tripulaçoes");
                    binding.quantidade.setText(String.valueOf(qnt_adicionadas)+" / "+String.valueOf(qnt_tripu));
                    break;
                case "Inimigos":
                    binding.titulo.setTextColor(Color.WHITE);
                    binding.quantidade.setTextColor(Color.WHITE);
                    binding.getRoot().setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.catalogo_inimigos));
                    List<Integer> inimigosList = usuario.getInimigos();
                    Log.d("inimigos",inimigosList.toString());
                    int qnt_ini = db.inimigosDao().quantosInimigos();
                    gridImagens = new int[qnt_ini];
                    idgeral = new int[qnt_ini];
                    qnt_adicionadas = 0;
                    if(!inimigosList.isEmpty()) {
                        for (int i = 0; i < inimigosList.size(); i++) {
                            Inimigos inimigos = db.inimigosDao().buscaInimigos(inimigosList.get(i));
                            idgeral[i] = inimigos.getIdinimigos();
                            int resID = requireContext().getResources().getIdentifier(inimigos.getFotoperfio(), "drawable", getContext().getPackageName());
                            //int teste = R.drawable.roronoazoroportrait;
                            gridImagens[i] = resID;
                            qnt_adicionadas++;
                        }
                    }
                    binding.titulo.setText("Inimigos");
                    binding.quantidade.setText(String.valueOf(qnt_adicionadas)+" / "+String.valueOf(qnt_ini));
                    break;
            }
            if(qnt_adicionadas != 0) {
                binding.grid1.setAdapter(new adaptador(getContext(), gridImagens));
            }

            binding.grid1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("valor", i);

                    switch (qual){
                        case "Akuma No Mi":
                            if(gridImagens[i] != 0 && idgeral[i] != 0) {
                                bundle.putInt("f", idgeral[i]);
                                NavHostFragment.findNavController(ImagenDoCatalago.this)
                                        .navigate(R.id.action_coleAkumaPersonagen_to_caracAkuma, bundle);
                            }
                            break;
                        case "Tribulação":
                            if(gridImagens[i] != 0 && idgeral[i] != 0) {
                                bundle.putInt("t", idgeral[i]);
                                NavHostFragment.findNavController(ImagenDoCatalago.this)
                                        .navigate(R.id.action_coleAkumaPersonagen_to_caracBando, bundle);
                            }
                            break;
                        case "Inimigos":
                            if(gridImagens[i] != 0 && idgeral[i] != 0) {
                                bundle.putInt("i", idgeral[i]);
                                NavHostFragment.findNavController(ImagenDoCatalago.this)
                                        .navigate(R.id.action_coleAkumaPersonagen_to_caracPersonagen, bundle);
                            }
                            break;
                    }
                }
            });
        }
    }

    public boolean procuraRepeticao(int[] array, int targetNumber) {
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
