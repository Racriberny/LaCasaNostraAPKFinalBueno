package com.cristobalbernal.lacasanostraapk.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    private SharedPreferences sharedPreferences;


    public Fragment_Mi_Perfil() {
        super(R.layout.fragment_perfil);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name = view.findViewById(R.id.name_textview);
        TextView correo = view.findViewById(R.id.email_textview);
        Button button = view.findViewById(R.id.cerrrarSesion);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("nombreDeUsuario","");
        System.out.println(user);
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getParentFragmentManager();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Acceder.class, null)
                        .commit();

            }
        });
    }
}
