package com.example.trabalhofinal.Telas;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.Tabelas.Jogador;
import com.example.trabalhofinal.Tabelas.Usuario;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.databinding.CriaPersonagenBinding;

import java.util.List;
import java.util.Random;

public class CriaJogador extends Fragment {
    private CriaPersonagenBinding binding;

    private AppDataBase db;
    private String akuma;
    private int fruta1 = 0 , fruta2 = 0, fruta3 = 0;
    private int idakuma = 0;
    private Akumas akumas;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CriaPersonagenBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());

        ArrayAdapter adpter = ArrayAdapter.createFromResource(getContext(), R.array.arma, android.R.layout.simple_spinner_item);
        binding.arma.setAdapter(adpter);

        adpter = ArrayAdapter.createFromResource(getContext(), R.array.origens, android.R.layout.simple_spinner_item);
        binding.mar.setAdapter(adpter);

        adpter = ArrayAdapter.createFromResource(getContext(), R.array.racas, android.R.layout.simple_spinner_item);
        binding.raca.setAdapter(adpter);

        if(fruta1 == 0 && fruta2 == 0 && fruta3 == 0) {
            Random random = new Random();
            fruta1 = random.nextInt(68) + 1;
            do {
                fruta2 = random.nextInt(68) + 1;
            } while (fruta2 == fruta1);
            do {
                fruta3 = random.nextInt(68) + 1;
            } while (fruta3 == fruta1 || fruta3 == fruta2);
        }

        if(fruta1 != 0 && fruta2 != 0 && fruta3 != 0){
            Akumas akumas1 = db.akumaDao().buscaAkuma(fruta1);
            Akumas akumas2 = db.akumaDao().buscaAkuma(fruta2);
            Akumas akumas3 = db.akumaDao().buscaAkuma(fruta3);

            if (!akumas1.getFotoakuma().equals("Nao Fotografada")) {
                int resID = requireContext().getResources().getIdentifier(akumas1.getFotoakuma(), "drawable", getContext().getPackageName());
                binding.fruta1.setImageResource(resID);
            }
            if (!akumas2.getFotoakuma().equals("Nao Fotografada")) {
                int resID = requireContext().getResources().getIdentifier(akumas2.getFotoakuma(), "drawable", getContext().getPackageName());
                binding.fruta2.setImageResource(resID);
            }
            if (!akumas3.getFotoakuma().equals("Nao Fotografada")) {
                int resID = requireContext().getResources().getIdentifier(akumas3.getFotoakuma(), "drawable", getContext().getPackageName());
                binding.fruta3.setImageResource(resID);
            }
        }
        ajustarCorTexto(binding.nome);

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nomePersonagem = binding.nome.getText().toString().trim();
                    String marOrigem = binding.mar.getSelectedItem().toString();
                    String racaSelecionada = binding.raca.getSelectedItem().toString();
                    String armaSelecionada = binding.arma.getSelectedItem().toString();

                    String generoSelecionado;
                    if (binding.sxMaculino.isChecked()) {
                        generoSelecionado = "Masculino";
                    } else if (binding.sxFeminino.isChecked()) {
                        generoSelecionado = "Feminino";
                    } else {
                        generoSelecionado = "";
                    }
                    String recompensa;
                    String titulo;
                    String associacaoSelecionada;
                    String tipo;
                    if (binding.pirata.isChecked()) {
                        associacaoSelecionada = "Pirata";
                        tipo = "P";
                        titulo = "Bandido";
                        recompensa = "B$ 0";
                    } else if (binding.marinha.isChecked()) {
                        associacaoSelecionada = "Marinha";
                        tipo = "M";
                        titulo = "Aprendiz de Marinheiro";
                        recompensa = "★";
                    } else {
                        tipo =" ";
                        associacaoSelecionada = "Nenhuma";
                        titulo = "Sem Título";
                        recompensa = "nada";
                    }
                    if(!nomePersonagem.isEmpty() && !generoSelecionado.isEmpty() && !associacaoSelecionada.isEmpty()) {
                        if(!db.jogadorDao().checaNome(nomePersonagem)) {
                            int level = 0;
                            int intuicao = 2;
                            int defesa = 0;
                            int agilidade = 0;
                            int estamina = 15;
                            int forca = 15;
                            int hp = 50;
                            int energia = 5;

                            int hakirei = 0;
                            int hakiobs = 0;
                            int hakiarm = 0;

                            Jogador jogador = new Jogador(nomePersonagem, level, armaSelecionada, hp, forca, estamina, agilidade, defesa, intuicao, energia, idakuma, associacaoSelecionada, tipo, titulo, marOrigem, recompensa, generoSelecionado, racaSelecionada, hakirei, hakiobs, hakiarm, 1);
                            jogador.setImagenPersona("Deffalt");
                            db.jogadorDao().insertAll(jogador);
                            Toast.makeText(requireContext(), "Personagen Adicionado", Toast.LENGTH_SHORT).show();

                            Bundle arg = getArguments();
                            if (arg != null) {
                                int idU = arg.getInt("idU");
                                Usuario usuario = db.usuarioDao().buscaUsuario(idU);
                                List<Integer> personaList = usuario.getPersonagens();
                                List<Integer> akumaList = usuario.getAkumanomis();
                                if (idakuma != 0 && !procuraRepeticao(akumaList, idakuma))
                                    akumaList.add(idakuma);
                                personaList.add(db.jogadorDao().buscaID(nomePersonagem));
                                usuario.setAkumanomis(akumaList);
                                usuario.setPersonagens(personaList);
                                usuario.setIdUser(idU);
                                db.usuarioDao().upgrade(usuario);

                                NavOptions navOptions = new NavOptions.Builder()
                                        .setPopUpTo(R.id.criaPersonagen2, true)
                                        .build();

                                Bundle bundle = new Bundle();
                                bundle.putInt("idU", arg.getInt("idU"));

                                NavHostFragment.findNavController(CriaJogador.this)
                                        .navigate(R.id.action_criaPersonagen2_self, bundle, navOptions);
                            } else {
                                Toast.makeText(requireContext(), "ARG nao gerado", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(requireContext(), "Nome de jogador já existente", Toast.LENGTH_SHORT).show();
                        }
                        resetFormFields();
                    }else {
                        Toast.makeText(requireContext(), "Preencha todos os Campos", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        binding.akuma.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.fruta1.setVisibility(View.VISIBLE);
                binding.fruta2.setVisibility(View.VISIBLE);
                binding.fruta3.setVisibility(View.VISIBLE);

                binding.cFruta1.setVisibility(View.VISIBLE);
                binding.cFruta2.setVisibility(View.VISIBLE);
                binding.cFruta3.setVisibility(View.VISIBLE);
            } else {
                binding.fruta1.setVisibility(View.GONE);
                binding.fruta2.setVisibility(View.GONE);
                binding.fruta3.setVisibility(View.GONE);

                binding.cFruta1.setVisibility(View.GONE);
                binding.cFruta2.setVisibility(View.GONE);
                binding.cFruta3.setVisibility(View.GONE);
            }
        });

        binding.sxMaculino.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.sxFeminino.setChecked(false);
            }
        });
        binding.sxFeminino.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.sxMaculino.setChecked(false);
            }
        });

        binding.cFruta1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                idakuma = fruta1;
                binding.cFruta2.setChecked(false);
                binding.cFruta3.setChecked(false);
            }
        });

        binding.cFruta2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                idakuma = fruta2;
                binding.cFruta1.setChecked(false);
                binding.cFruta3.setChecked(false);
            }
        });

        binding.cFruta3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                idakuma = fruta3;
                binding.cFruta2.setChecked(false);
                binding.cFruta1.setChecked(false);
            }
        });

        binding.pirata.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.marinha.setChecked(false);
            }
        });
        binding.marinha.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.pirata.setChecked(false);
            }
        });

        Bundle bundle = new Bundle();
        binding.fruta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fruta1 != 0) {
                    bundle.putInt("f", fruta1);
                    NavHostFragment.findNavController(CriaJogador.this)
                            .navigate(R.id.action_criaPersonagen2_to_caracAkuma, bundle);
                }else {
                    Toast.makeText(requireContext(), "Gere as Akumas No Mi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.fruta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fruta1 != 0) {
                    bundle.putInt("f", fruta2);
                    NavHostFragment.findNavController(CriaJogador.this)
                            .navigate(R.id.action_criaPersonagen2_to_caracAkuma, bundle);
                }else {
                    Toast.makeText(requireContext(), "Gere as Akumas No Mi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.fruta3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fruta1 != 0) {
                    bundle.putInt("f", fruta3);
                    NavHostFragment.findNavController(CriaJogador.this)
                            .navigate(R.id.action_criaPersonagen2_to_caracAkuma, bundle);
                }else {
                    Toast.makeText(requireContext(), "Gere as Akumas No Mi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean procuraRepeticao(List<Integer> array, int targetNumber) {
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

    private void resetFormFields() {
        // EditText
        binding.nome.setText("");

        // Spinners
        binding.mar.setSelection(0);
        binding.raca.setSelection(0);
        binding.arma.setSelection(0);

        // CheckBoxes de Gênero
        binding.sxMaculino.setChecked(false);
        binding.sxFeminino.setChecked(false);

        // CheckBoxes de Associação
        binding.pirata.setChecked(false);
        binding.marinha.setChecked(false);

        // CheckBox Akuma No Mi
        binding.akuma.setChecked(false);
        binding.fruta1.setVisibility(View.GONE);
        binding.fruta2.setVisibility(View.GONE);
        binding.fruta3.setVisibility(View.GONE);
        binding.fruta1.setImageDrawable(null);
        binding.fruta2.setImageDrawable(null);
        binding.fruta3.setImageDrawable(null);
        binding.cFruta1.setChecked(false);
        binding.cFruta2.setChecked(false);
        binding.cFruta3.setChecked(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
