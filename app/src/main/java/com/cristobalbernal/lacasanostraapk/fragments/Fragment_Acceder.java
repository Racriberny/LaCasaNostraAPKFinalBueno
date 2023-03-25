package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.Utils.HashGenerator;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;
import com.google.android.material.textfield.TextInputEditText;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Acceder extends Fragment {
    private TextInputEditText correo;
    private TextInputEditText contrasena;
    private IAPIService iapiService;
    public Fragment_Acceder(){
        super(R.layout.fragment_acceder);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iapiService = RestClient.getInstance();
        correo = view.findViewById(R.id.etEmailLogin);
        contrasena = view.findViewById(R.id.etContrasenyaLogin);
        Button login = view.findViewById(R.id.btnLogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getLogin();
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        view.findViewById(R.id.tvRegistrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getLogin() throws NoSuchAlgorithmException {
        String correoElectronico = Objects.requireNonNull(correo.getText()).toString();
        String password = Objects.requireNonNull(HashGenerator.getSHAString(String.valueOf(contrasena.getText())));

        if (correoElectronico.isEmpty()){
            correo.setError("Se requiere un correo electronico");
            correo.requestFocus();
            return;
        }else if (password.isEmpty()){
            contrasena.setError("Se requiere una contraseña");
            contrasena.requestFocus();
            return;
        }
        Call<Boolean> booleanCall = iapiService.logUsuario(new Usuario(correoElectronico,password));

        booleanCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if (Boolean.TRUE.equals(response.body())) {
                    FragmentManager manager = getParentFragmentManager();
                    manager.beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .replace(R.id.content_frame, Fragment_Home.class, null)
                            .commit();
                    Toast.makeText(getContext(), "Has inicion sesion", Toast.LENGTH_SHORT).show();
                } else if (Boolean.FALSE.equals(response.body())) {
                    Toast.makeText(getContext(), "No has inicion sesion", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    }
}
