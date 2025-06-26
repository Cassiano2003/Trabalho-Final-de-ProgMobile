package com.example.trabalhofinal.Telas;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.MainActivity;
import com.example.trabalhofinal.R;
import com.example.trabalhofinal.Tabelas.Akumas;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.Tabelas.Usuario;
import com.example.trabalhofinal.databinding.ConfigUsuarioBinding;

import java.util.List;

public class ConfigUsuario extends Fragment {
    private ConfigUsuarioBinding binding;

    private AppDataBase db;
    Usuario user;

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

        binding.spinnerMusica.setSelection(0);

        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        mostrarDialogoDeConfirmacao();
                    }
                }
        );

        // Configura a seta de voltar da Toolbar (caso esteja usando uma)
        requireActivity().getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            AppCompatActivity activity = (AppCompatActivity) requireActivity();
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setHasOptionsMenu(true);
        });

        db = AppDataBase.getDataBase(getContext());

        Bundle arg = getArguments();
        if(arg != null){
            int idU = arg.getInt("id");
            user = db.usuarioDao().buscaUsuario(idU);
            binding.nome.setText(user.getNome());
            Bitmap fotoBitmap = BitmapFactory.decodeByteArray(user.getFoto(), 0, user.getFoto().length);
            binding.foto.setImageBitmap(fotoBitmap);

            int total = 0;
            int total_desbloqueado = 0;

            total += db.akumaDao().quantosAkumas();
            total += db.inimigosDao().quantosInimigos();
            total += db.tripulacaoDao().quantosTripulacao();

            total_desbloqueado += user.getInimigos().size();
            total_desbloqueado += user.getAkumanomis().size();
            total_desbloqueado += user.getTripulacao().size();

            double progresso = (double) total_desbloqueado / total * 100.0;

            binding.progreso.setText(String.format("Progresso: %.1f%%", progresso));

            List<Akumas> akumasList = db.akumaDao().getALL();
            for(int i=0; i< akumasList.size();i++){
                Log.d("id ataque "+String.valueOf(i+1),String.valueOf(akumasList.get(i).getIdataques()));
            }

        }

        ArrayAdapter adpter = ArrayAdapter.createFromResource(getContext(),R.array.musica, android.R.layout.simple_spinner_item);
        binding.spinnerMusica.setAdapter(adpter);
        binding.spinnerMusica.setSelection(0);
        binding.spinnerMusica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!binding.spinnerMusica.getSelectedItem().toString().equals("----")) {
                    ((MainActivity) getActivity()).mediaPlayer.release();
                    switch (position) {
                        case 1:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.ado1newgenesis);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        case 2:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.ado2iminvincible);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        case 3:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.ado3backlight);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        case 4:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.ado4fleetinglullaby);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        case 5:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.ado5totmusica);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        case 6:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.ado6sekainotsuzuki);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        case 7:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.ado7wherethewindblows);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        case 8:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.binksake);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        case 9:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.choppersong);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        case 10:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.nekomamushisong);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        case 11:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.newworld);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        case 12:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.queenconcert);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        case 13:
                            ((MainActivity) getActivity()).mediaPlayer = MediaPlayer.create(requireContext(), R.raw.sogekingtema);
                            ((MainActivity) getActivity()).mediaPlayer.start();
                            break;
                        default:
                            ((MainActivity) getActivity()).tocarMusicaAleatoria();
                            break;
                    }

                    ((MainActivity) getActivity()).mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            ((MainActivity) getActivity()).tocarMusicaAleatoria();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.seusPersonagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.spinnerMusica.setSelection(0);
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
                binding.spinnerMusica.setSelection(0);
                Bundle bundle = new Bundle();
                bundle.putString("g","Akuma No Mi");
                int idU = arg.getInt("id");
                bundle.putInt("idU",idU);

                NavHostFragment.findNavController(ConfigUsuario.this)
                        .navigate(R.id.action_configUsuario_to_coleAkumaPersonagen,bundle);
            }
        });

        binding.colBandeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.spinnerMusica.setSelection(0);
                Bundle bundle = new Bundle();
                bundle.putString("g","Tribulação");
                int idU = arg.getInt("id");
                bundle.putInt("idU",idU);

                NavHostFragment.findNavController(ConfigUsuario.this)
                        .navigate(R.id.action_configUsuario_to_coleAkumaPersonagen,bundle);
            }
        });

        binding.colPersonagens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.spinnerMusica.setSelection(0);
                Bundle bundle = new Bundle();
                bundle.putString("g","Inimigos");
                int idU = arg.getInt("id");
                bundle.putInt("idU",idU);

                NavHostFragment.findNavController(ConfigUsuario.this)
                        .navigate(R.id.action_configUsuario_to_coleAkumaPersonagen,bundle);
            }
        });

        binding.comoJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.spinnerMusica.setSelection(0);
                NavHostFragment.findNavController(ConfigUsuario.this)
                        .navigate(R.id.action_configUsuario_to_introducao);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Indicar que este fragmento tem opções de menu para a ActionBar/Toolbar
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.configUsuario, true) // remove cadastroFragment da pilha
                .build();

        if (item.getItemId() == android.R.id.home) {
            mostrarDialogoDeConfirmacao();
            return true;
        }
        if (id == R.id.Excluir) { // Exemplo de um item de menu
            AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
            dlg.setMessage("Deseja mesmo excluir essa conta?").setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.usuarioDao().delete(user);
                    NavHostFragment.findNavController(ConfigUsuario.this)
                            .navigate(R.id.action_configUsuario_to_inicio,navOptions);
                }
            });
            dlg.setNeutralButton("cancelar",null);
            dlg.show();
            return true;
        } else if (id == R.id.atualiza) {
            Bundle bundle = new Bundle();
            bundle.putInt("idU", user.getIdUser());
            NavHostFragment.findNavController(ConfigUsuario.this)
                    .navigate(R.id.action_configUsuario_to_cadastro,bundle,navOptions);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu); // Infla o menu aqui
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void mostrarDialogoDeConfirmacao() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Sair")
                .setMessage("Deseja mesmo sair desta tela?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    binding.spinnerMusica.setSelection(0);
                    requireActivity().getSupportFragmentManager().popBackStack();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.spinnerMusica.setSelection(0);
        binding = null;
    }
}
