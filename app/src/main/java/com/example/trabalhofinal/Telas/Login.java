package com.example.trabalhofinal.Telas;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.Tabelas.Usuario;
import com.example.trabalhofinal.databinding.LoginBinding;

import org.mindrot.jbcrypt.BCrypt;

public class Login extends Fragment {
    private LoginBinding binding;
    private AppDataBase db;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = LoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());

        binding.entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nomeLogin = binding.nomeLogin.getText().toString();
                    String senha = binding.senhaLogin.getText().toString();
                    if(!nomeLogin.isEmpty() && !senha.isEmpty()){
                        if(db.usuarioDao().buscaNome(nomeLogin) || db.usuarioDao().buscaEmai(nomeLogin)){
                            int idnome = db.usuarioDao().buscaNomeID(nomeLogin);
                            int idemail = db.usuarioDao().buscaEmaiID(nomeLogin);
                            Usuario usuario = null;
                            if( idnome > 0){
                                usuario = db.usuarioDao().buscaUsuario(idnome);
                            }else if (idemail > 0){
                                usuario = db.usuarioDao().buscaUsuario(idemail);
                            }
                            if(checkPassword(senha,usuario.getSenha())){
                                Bundle bundle = new Bundle();
                                bundle.putInt("id",usuario.getIdUser());
                                NavOptions navOptions = new NavOptions.Builder()
                                        .setPopUpTo(R.id.login, true)
                                        .build();

                                NavHostFragment.findNavController(Login.this)
                                        .navigate(R.id.action_login_to_configUsuario,bundle,navOptions);
                            }else {
                                Toast.makeText(requireContext(), "Senha Incorreta", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(requireContext(), "Usuário não encontrado", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(requireContext(), "Nome e Senha Incorretos", Toast.LENGTH_LONG).show();
                    }
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        binding.senhaVisivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.senhaVisivel.isChecked()){
                    binding.senhaLogin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    binding.senhaLogin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        binding.trocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("torcarSenha",true);
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.login, true) // remove cadastroFragment da pilha
                        .build();
                NavHostFragment.findNavController(Login.this)
                        .navigate(R.id.action_login_to_cadastro,bundle,navOptions);
            }
        });


    }

    public static boolean checkPassword(String senha, String comferirsenha) {
        return BCrypt.checkpw(senha, comferirsenha);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
