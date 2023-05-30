package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cristobalbernal.lacasanostraapk.MainActivity;
import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Setting extends Fragment {
    private TextView perfil;
    private TextView admin;
    private TextView cerrar_session;
    private String user;
    private IAPIService iapiService;
    private SharedPreferences sharedPreferences;
    private List<Usuario> usuarios;


    public Fragment_Setting(){
        super(R.layout.fragment_setting);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usuarios = new ArrayList<>();
        iapiService = RestClient.getInstance(requireContext());
        getUser();
        perfil = view.findViewById(R.id.tv_settings_option);
        admin =view.findViewById(R.id.admin);
        cerrar_session = view.findViewById(R.id.cerrar);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("nombreDeUsuario","");

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getParentFragmentManager();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Mi_Perfil.class, null)
                        .commit();
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i <usuarios.size() ; i++) {
                    if (user.equals(usuarios.get(i).getCorreoElectronico())){
                        if (usuarios.get(i).getAdmin() == 1){
                            FragmentManager manager = getParentFragmentManager();
                            manager.beginTransaction()
                                    .setReorderingAllowed(true)
                                    .addToBackStack(null)
                                    .replace(R.id.content_frame, Fragment_Admin.class, null)
                                    .commit();
                        }else {
                            Toast.makeText(getContext(),R.string.noadmin,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        cerrar_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.cambiarAlNormal(getContext());
                sharedPreferences.edit().remove("nombreDeUsuario").apply();
                FragmentManager manager = getParentFragmentManager();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Acceder.class, null)
                        .commit();
            }
        });
    }

    public void getUser(){
        iapiService.getUsuario().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                assert response.body() != null;
                usuarios.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });


    }
}
