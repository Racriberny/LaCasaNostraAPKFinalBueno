package com.cristobalbernal.lacasanostraapk.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;

public class Fragment_Mi_Perfil extends Fragment {

    public interface IOnUsuarioPerfil{
        Usuario getUserPefil();
    }
    private Usuario usuario;

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
        String nombre = usuario.getNombre();
        String apellidos = usuario.getApellidos();
        String email = usuario.getCorreoElectronico();
        name.setText(nombre +" " + apellidos);
        correo.setText(email);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IOnUsuarioPerfil iOnUsuarioPerfil = (IOnUsuarioPerfil) context;
        usuario = iOnUsuarioPerfil.getUserPefil();
    }
}
