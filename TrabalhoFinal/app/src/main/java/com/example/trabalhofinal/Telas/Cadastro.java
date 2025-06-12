package com.example.trabalhofinal.Telas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.TabelasDao.AppDataBase;
import com.example.trabalhofinal.Tabelas.Usuario;
import com.example.trabalhofinal.databinding.CadastroBinding;

import org.mindrot.jbcrypt.BCrypt;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class Cadastro extends Fragment {

    private CadastroBinding binding;

    private static final int CAMERA_PERMISSION_CODE=1;
    ActivityResultLauncher<Uri> takePictureLauncher;
    Uri imageUri;

    private Bitmap fotoBitmap;

    private ImageView imageView;
    private Button buttonCamera;

    private AppDataBase db;
    Usuario usuario;
    private int idnome = -1;
    private int idemail = -1;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CadastroBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = AppDataBase.getDataBase(getContext());

        imageView=binding.fotoUser;
        buttonCamera=binding.tiraFoto;

        imageUri=createUri();
        registerPictureLauncher();

        buttonCamera.setOnClickListener(v->{
            checkPermissionAndOpenCamera();
        });

        Bundle arg = getArguments();
        if(arg != null){
            requireActivity().setTitle("Atualiza Usuario");
            binding.email.setVisibility(View.INVISIBLE);
            binding.nome.setVisibility(View.INVISIBLE);
            binding.senha.setVisibility(View.INVISIBLE);
            binding.senhaNovamente.setVisibility(View.INVISIBLE);
            binding.salvar.setVisibility(View.INVISIBLE);
            binding.senhaVisivel.setVisibility(View.INVISIBLE);
            binding.fotoUser.setVisibility(View.INVISIBLE);
            binding.tiraFoto.setVisibility(View.INVISIBLE);
        }else{
            binding.busca.setVisibility(View.INVISIBLE);
            binding.pesquisa.setVisibility(View.INVISIBLE);
        }

        binding.pesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nomeBusca = binding.busca.getText().toString();
                    if(!nomeBusca.isEmpty()){
                        if(db.usuarioDao().buscaNome(nomeBusca) || db.usuarioDao().buscaEmai(nomeBusca)){
                            idnome = db.usuarioDao().buscaNomeID(nomeBusca);
                            idemail = db.usuarioDao().buscaEmaiID(nomeBusca);
                            if( idnome > 0){
                                usuario = db.usuarioDao().buscaUsuario(idnome);
                            }else if (idemail > 0){
                                usuario = db.usuarioDao().buscaUsuario(idemail);
                            }
                            if(usuario != null){
                                binding.senha.setVisibility(View.VISIBLE);
                                binding.senhaNovamente.setVisibility(View.VISIBLE);
                                binding.salvar.setVisibility(View.VISIBLE);
                                binding.senhaVisivel.setVisibility(View.VISIBLE);
                            }
                        }else {
                            Toast.makeText(requireContext(), "Usuario nao existe", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(requireContext(), "Nome invalido", Toast.LENGTH_LONG).show();
                    }
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        binding.salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String nome = binding.nome.getText().toString();
                    String email = binding.email.getText().toString();
                    String senha = binding.senha.getText().toString();
                    String senhanovamente = binding.senhaNovamente.getText().toString();
                    if(!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty() && !senhanovamente.isEmpty() && fotoBitmap != null){
                        if((!db.usuarioDao().buscaNome(nome)) || arg != null) {
                            if ((isEmaiValido(email) && !db.usuarioDao().buscaEmai(email)) || arg != null) {
                                if (senha.equals(senhanovamente)) {
                                    //Foto
                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    fotoBitmap.compress(Bitmap.CompressFormat.JPEG,50,stream);
                                    //Criptaçao da Senha
                                    String senhaHash = hashPassword(senha);

                                    //Cria Usuario
                                    Usuario usuario = new Usuario(nome, senhaHash, email,stream.toByteArray());
                                    NavOptions navOptions = new NavOptions.Builder()
                                            .setPopUpTo(R.id.cadastro, true) // remove cadastroFragment da pilha
                                            .build();
                                    if(arg != null){
                                        if( idnome > 0){
                                            usuario.setIdUser(idnome);
                                        }else if (idemail > 0){
                                            usuario.setIdUser(idemail);
                                        }
                                        Toast.makeText(requireContext(), "Usuario atualizado", Toast.LENGTH_LONG).show();
                                        db.usuarioDao().upgrade(usuario);
                                    }else {
                                        Toast.makeText(requireContext(), "Usuario cadastrado", Toast.LENGTH_LONG).show();
                                        db.usuarioDao().insertAll(usuario);
                                        binding.nome.setText("");
                                        binding.email.setText("");
                                        binding.senha.setText("");
                                        binding.senhaNovamente.setText("");
                                        binding.fotoUser.setImageResource(0);
                                        fotoBitmap = null;

                                        NavHostFragment.findNavController(Cadastro.this)
                                                .navigate(R.id.action_cadastro_to_inicio, null, navOptions);
                                    }
                                } else {
                                    Toast.makeText(requireContext(), "As senhas não são iguais", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(requireContext(), "Email invalido", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(requireContext(), "Nome de Usuario ja existente", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(requireContext(), "Prencha todos os campos até a foto", Toast.LENGTH_LONG).show();
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
                    binding.senha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    binding.senhaNovamente.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    binding.senha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    binding.senhaNovamente.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    public boolean isEmaiValido(String emai){
        return emai != null && Patterns.EMAIL_ADDRESS.matcher(emai).matches();
    }

    private void checkPermissionAndOpenCamera() {
        if(ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
        }else{
            Toast.makeText(requireContext(),"Check permission Granted",
                    Toast.LENGTH_SHORT).show();
            takePictureLauncher.launch(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(requireContext(),"Request permission Granted",
                    Toast.LENGTH_SHORT).show();
            takePictureLauncher.launch(imageUri);
        }else{
            Toast.makeText(requireContext(),"Request permission Denied",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Uri createUri(){
        File imageFile=new File(getActivity().getFilesDir(),
                "camera_photo.jpg");
        return FileProvider.getUriForFile(getActivity(),
                "com.example.appcamera24.fileprovider",
                imageFile);
    }

    private void registerPictureLauncher() {
        takePictureLauncher=registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean o) {
                        try{
                            if(o){
                                imageView.setImageURI(null);
                                imageView.setImageURI(imageUri);

                                fotoBitmap = MediaStore.Images.Media.getBitmap(
                                        requireContext().getContentResolver(),
                                        imageUri);
                            }
                        }catch(Exception exception){
                            exception.getStackTrace();
                        }
                    }
                }
        );
    }

    public static String hashPassword(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}