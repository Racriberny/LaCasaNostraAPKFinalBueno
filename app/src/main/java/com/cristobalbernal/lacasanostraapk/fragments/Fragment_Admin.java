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


    public Fragment_Admin() {
        super(R.layout.fragment_admin);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager manager = requireActivity().getSupportFragmentManager();
        Button btAnadirPlato = view.findViewById(R.id.btAdminPlato);
        Button btModificarPlato = view.findViewById(R.id.btModificarPlatoAdmin);
        Button btAdminProducto = view.findViewById(R.id.btAdminProducto);
        Button btModificarProducto = view.findViewById(R.id.btModificarProducto);
        Button btEliminarProductos = view.findViewById(R.id.btEliminarProductos);
        Button btEliminarPlato = view.findViewById(R.id.btEliminarPlato);

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
        btAdminProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame,Fragment_Anadir_Producto.class,null)
                        .commit();
            }
        });
        btModificarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame,Fragment_Modificar_Producto.class,null)
                        .commit();
            }
        });
        btEliminarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame,Fragment_Eliminar_Productos.class,null)
                        .commit();
            }
        });
        btEliminarPlato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame,Fragment_Eliminar_Plato.class,null)
                        .commit();
            }
        });



    }
}
