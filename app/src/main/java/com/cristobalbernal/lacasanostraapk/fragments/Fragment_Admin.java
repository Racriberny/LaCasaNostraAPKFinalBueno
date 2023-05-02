package com.cristobalbernal.lacasanostraapk.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cristobalbernal.lacasanostraapk.R;

public class Fragment_Admin extends Fragment {
    private Button btAnadirPlato;
    private Button btModificarPlato;


    public Fragment_Admin() {
        super(R.layout.fragment_admin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager manager = requireActivity().getSupportFragmentManager();
        btAnadirPlato = view.findViewById(R.id.btAdminPlato);
        btModificarPlato = view.findViewById(R.id.btModificarPlatoAdmin);


        btAnadirPlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Anadir_Plato.class, null)
                        .commit();
            }
        });
        btModificarPlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame,Fragment_Modificar_Plato.class,null)
                        .commit();
            }
        });



    }
}
