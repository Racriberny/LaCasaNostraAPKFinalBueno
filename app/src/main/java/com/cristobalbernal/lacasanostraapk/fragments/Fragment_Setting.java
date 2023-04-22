package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cristobalbernal.lacasanostraapk.R;

public class Fragment_Setting extends Fragment {
    private TextView perfil;
    private TextView cerrar_session;
    private SharedPreferences sharedPreferences;


    public Fragment_Setting(){
        super(R.layout.fragment_setting);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        perfil = view.findViewById(R.id.tv_settings_option);
        cerrar_session = view.findViewById(R.id.cerrar);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
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

        cerrar_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
