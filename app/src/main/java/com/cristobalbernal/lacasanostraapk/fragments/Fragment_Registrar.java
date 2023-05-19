package com.cristobalbernal.lacasanostraapk.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.android.material.textfield.TextInputEditText;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Registrar extends Fragment {
    private IAPIService iapiService;
    private TextInputEditText name, surname,correo,contrasena;
    private Button button;
    public Fragment_Registrar(){super(R.layout.fragment_registrar);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iapiService = RestClient.getInstance();
        name = view.findViewById(R.id.etNombreRegister);
        surname = view.findViewById(R.id.etApellidosRegister);
        correo = view.findViewById(R.id.etEmailRegister);
        contrasena = view.findViewById(R.id.etContrasenyaRegister);
        button = view.findViewById(R.id.btnRegister);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registerUser();
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void registerUser() throws NoSuchAlgorithmException {
        String nombre = Objects.requireNonNull(name.getText()).toString();
        String apellidos = Objects.requireNonNull(surname.getText()).toString();
        String corre_eletronico = Objects.requireNonNull(correo.getText()).toString();
        String password = Objects.requireNonNull(HashGenerator.getSHAString(String.valueOf(contrasena.getText())));

        if (nombre.isEmpty()){
            name.setError("Es necesario escribir el nombre!!!");
            name.requestFocus();
            return;
        }
        if (apellidos.isEmpty()){
            surname.setError("Es necesario escribir los apellidos!!");
            surname.requestFocus();
            return;
        }
        if (corre_eletronico.isEmpty()){
            correo.setError("Es necesario escribir un correo electonico!!");
            correo.requestFocus();
            return;
        }
        if (password.isEmpty()){
            contrasena.setError("Tienes que escribir una contraseña!!");
            contrasena.requestFocus();
            return;
        }

        Call<Boolean> booleanCall = iapiService.addUsuario(new Usuario(nombre,apellidos,corre_eletronico,password));

        booleanCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if (Boolean.TRUE.equals(response.body())){
                    Toast.makeText(getContext(), R.string.registrar, Toast.LENGTH_SHORT).show();
                    FragmentManager manager = getParentFragmentManager();
                    manager.beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .replace(R.id.content_frame, Fragment_Home.class, null)
                            .commit();
                }

            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
