package com.cristobalbernal.lacasanostraapk.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.Utils.HashGenerator;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

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
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String contraseña;
    private int id;


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
        Button edit = view.findViewById(R.id.editar);
        Button guardar = view.findViewById(R.id.save);
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
                        id = usuarios.get(i).getId();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });
        System.out.println(usuarios);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setEnabled(true);
                correo.setEnabled(true);
                passwordActual.setEnabled(true);
                passwordNew.setEnabled(true);
                Toast.makeText(getContext(),R.string.editar_perefil,Toast.LENGTH_SHORT).show();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                String[] partes = nombre.split(" ");

                if (partes.length == 1){
                    Toast.makeText(getContext(),R.string.nombre_apellido,Toast.LENGTH_SHORT).show();
                    return;
                }
                String name_nam = partes[0];
                String apellido = partes[1];


                if (actual.equals(contraseña)){
                    Call<Usuario> booleanCall = iapiService.modificarUser(id,new Usuario(name_nam,apellido,email,nueva));

                    booleanCall.enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            Log.i("Bien",response.toString());
                            editor.putString("nombreDeUsuario", email);
                            editor.apply();
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
                }
            }
        });
    }
}
