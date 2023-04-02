package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;

public class Fragment_Mi_Perfil extends Fragment {

    public interface IOnUsuarioPerfil{
        Usuario getUser();
    }
    private Usuario usuario;

    public Fragment_Mi_Perfil() {
        super(R.layout.fragment_perfil);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IOnUsuarioPerfil iOnUsuarioPerfil = (IOnUsuarioPerfil) context;
        usuario = iOnUsuarioPerfil.getUser();
    }
}
