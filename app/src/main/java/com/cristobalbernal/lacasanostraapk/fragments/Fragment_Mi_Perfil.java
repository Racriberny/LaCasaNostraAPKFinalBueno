package com.cristobalbernal.lacasanostraapk.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

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
    private EditText password;
    private SharedPreferences sharedPreferences;


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
        password = view.findViewById(R.id.password);
        password.setEnabled(false);
        Button edit = view.findViewById(R.id.editar);
        Button guardar = view.findViewById(R.id.guardar);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
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
                        name.setText(nombre +" " + apellidos);
                        correo.setText(user);
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
                password.setEnabled(true);
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
