package com.cristobalbernal.lacasanostraapk.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cristobalbernal.lacasanostraapk.MainActivity;
import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.Utils.EncodingImg;
import com.cristobalbernal.lacasanostraapk.Utils.HashGenerator;
import com.cristobalbernal.lacasanostraapk.Utils.Lib;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Mi_Perfil extends Fragment {
    private IAPIService iapiService;
    private List<Usuario> usuarios;
    private String user;
    private EditText name;
    private EditText correo;
    private EditText passwordActual;
    private EditText passwordNew;
    private EditText confirmarPasswordNew;
    private ImageView imageView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String contraseña;
    private int id;

    private ActivityResultLauncher<PickVisualMediaRequest> pickImage;
    private String base64;


    public Fragment_Mi_Perfil() {
        super(R.layout.fragment_perfil);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.name_textview);
        name.setEnabled(false);
        correo = view.findViewById(R.id.email_textview);
        correo.setEnabled(false);
        passwordActual = view.findViewById(R.id.passwordActual);
        passwordActual.setEnabled(false);
        passwordNew = view.findViewById(R.id.passwordNew);
        passwordNew.setEnabled(false);
        imageView = view.findViewById(R.id.imagenPerfil);
        imageView.setEnabled(false);
        confirmarPasswordNew = view.findViewById(R.id.confirmarContraseña);
        confirmarPasswordNew.setEnabled(false);
        Button edit = view.findViewById(R.id.editar);

        Button guardar = view.findViewById(R.id.save);
        guardar.setVisibility(View.GONE);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        user = sharedPreferences.getString("nombreDeUsuario","");
        iapiService = RestClient.getInstance();
        usuarios = new ArrayList<>();

        iapiService.getUsuario().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                assert response.body() != null;
                usuarios.addAll(response.body());
                for (int i = 0; i <usuarios.size() ; i++) {
                    if (user.equals(usuarios.get(i).getCorreoElectronico())){
                        String nombre = usuarios.get(i).getNombre();
                        String apellidos = usuarios.get(i).getApellidos();
                        contraseña = usuarios.get(i).getContrasena();
                        name.setText(nombre +" " + apellidos);
                        correo.setText(user);
                        imageView.setImageBitmap(EncodingImg.decode(usuarios.get(i).getImagen()));
                        base64 = usuarios.get(i).getImagen();
                        id = usuarios.get(i).getId();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setEnabled(true);
                correo.setEnabled(true);
                passwordActual.setEnabled(true);
                passwordNew.setEnabled(true);
                imageView.setEnabled(true);
                confirmarPasswordNew.setEnabled(true);
                edit.setVisibility(View.GONE);
                guardar.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),R.string.editar_perefil,Toast.LENGTH_SHORT).show();
            }
        });

        pickImage = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                try {
                    base64 = EncodingImg.getBase64FromUri(requireContext(),uri);
                    imageView.setImageBitmap(EncodingImg.decode(base64));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage.launch(new PickVisualMediaRequest());
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setVisibility(View.VISIBLE);
                guardar.setVisibility(View.GONE);
                String nombre = name.getText().toString();
                String email = correo.getText().toString();
                String actual = null;
                try {
                    actual = HashGenerator.getSHAString(passwordActual.getText().toString());
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                String nueva = null;
                try {
                    nueva = HashGenerator.getSHAString(passwordNew.getText().toString());
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
                String nuevaConfirmacion = null;
                try {
                    nuevaConfirmacion = HashGenerator.getSHAString(confirmarPasswordNew.getText().toString());
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }


                if (nombre.isEmpty()){
                    name.setError("Es necesario escribir en este campo.");
                    name.requestFocus();
                    return;
                }
                if (email.isEmpty()){
                    correo.setError("Es necesario escribir en este campo.");
                    correo.requestFocus();
                    return;
                }
                if (actual.isEmpty()){
                    passwordActual.setError("Es necesario escribir en este campo.");
                    passwordActual.requestFocus();
                    return;
                }
                if (nueva.isEmpty()){
                    passwordNew.setError("Es necesario escribir en este campo.");
                    passwordNew.requestFocus();
                    return;
                }
                if (nuevaConfirmacion.isEmpty()){
                    confirmarPasswordNew.setError("Es necesario escribir en esta campo.");
                    confirmarPasswordNew.requestFocus();
                    return;
                }

                String[] partes = nombre.split(" ");

                if (partes.length == 1){
                    Toast.makeText(getContext(),R.string.nombre_apellido,Toast.LENGTH_SHORT).show();
                    return;
                }
                String name_nam = partes[0];
                String apellido = partes[1];

                if (!nueva.equals(nuevaConfirmacion)){
                    confirmarPasswordNew.setError("Las contraseñas deben de coincidir.");
                    confirmarPasswordNew.requestFocus();
                    return;
                }
                if (actual.equals(contraseña)){
                    Call<Usuario> booleanCallSi = iapiService.modificarUser(id,new Usuario(name_nam,apellido,email, nueva,base64));
                    booleanCallSi.enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            Log.i("Bien",response.toString());
                            editor.putString("nombreDeUsuario", email);
                            editor.apply();
                            MainActivity.cambiarHeaderNavigationView(requireContext());
                            Toast.makeText(getContext(),R.string.cambiado,Toast.LENGTH_SHORT).show();
                            FragmentManager manager = getParentFragmentManager();
                            manager.beginTransaction()
                                    .setReorderingAllowed(true)
                                    .addToBackStack(null)
                                    .replace(R.id.content_frame, Fragment_Home.class, null)
                                    .commit();
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            Log.i("Mal",t.toString());
                        }
                    });
                }else {
                    Toast.makeText(getContext(),R.string.contraseñaNo,Toast.LENGTH_SHORT).show();
                    name.setEnabled(false);
                    correo.setEnabled(false);
                    passwordActual.setEnabled(false);
                    passwordNew.setEnabled(false);
                    imageView.setEnabled(false);
                    confirmarPasswordNew.setEnabled(false);
                }
            }
        });
    }
}
