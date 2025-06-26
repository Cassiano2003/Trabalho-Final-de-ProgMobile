package com.example.trabalhofinal.Telas;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.AtaqueAkumaNoMi;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.TabelasDao.AtaqueAkumasDao;
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
            binding.tipo.setText("Tipo: "+akumas.getTipo());
            binding.descricao.setText("Descrição: \n"+akumas.getDescricao());
            binding.usuarios.setText("Usuários: "+akumas.getUsuarios());

            if(!akumas.getFotoakuma().equals("Nao Fotografada")) {
                int resID = requireContext().getResources().getIdentifier(akumas.getFotoakuma(), "drawable", getContext().getPackageName());
                binding.fotoFruta.setImageResource(resID);
            }
            AtaqueAkumaNoMi ataqueAkumaNoMi = db.ataqueAkumasDao().buscaAtaqueAkumaNoMi(akumas.getIdataques());
            binding.ataques.setVisibility(View.VISIBLE);
            int qntAtaques = ataqueAkumaNoMi.getQntataques();
            String[] nome_do_ataque = ataqueAkumaNoMi.getNomeDoAtaque();
            String[] descricao = ataqueAkumaNoMi.getDescricao();
            String[] tipo_de_ataque = ataqueAkumaNoMi.getTipoDeAtaque();

            int[] custo = ataqueAkumaNoMi.getCusto();
            int nivel;
                
            for(int i=0; i < qntAtaques; i++){
                TextView novo = new TextView(requireContext());
                Typeface fonte = ResourcesCompat.getFont(requireContext(), R.font.one_piece_font);
                novo.setTypeface(fonte);
                novo.setTextColor(Color.WHITE);
                novo.setTextSize(30);
                View divisao = new View(requireContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,10);
                params.setMargins(0,16,0,16);
                divisao.setLayoutParams(params);
                divisao.setBackgroundColor(Color.RED);
                
                nivel = 25 * i;
                if (qntAtaques == 2 && i == 1){
                    nivel = 50;
                }
                //vc acha q teria como separar o novo em mais de um "setText"? pq assim teria como formatar individualmente cada seção da akuma no mi
                novo.setText(nome_do_ataque[i]+"  (Nível: "+nivel+")\nTipo: "+tipo_de_ataque[i]+"         Custo: "+String.valueOf(custo[i])+" de Energia\n"+descricao[i]);
                novo.setTextSize(15);
                binding.ataquesakuma.addView(novo);
                binding.ataquesakuma.addView(divisao);
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
