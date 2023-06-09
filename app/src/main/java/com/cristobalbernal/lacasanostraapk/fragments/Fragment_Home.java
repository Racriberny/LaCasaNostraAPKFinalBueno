package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cristobalbernal.lacasanostraapk.MainActivity;
import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.Utils.EncodingImg;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;
import com.cristobalbernal.lacasanostraapk.notificacion.DialogoSeleccion;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Home extends Fragment {
    private IAPIService iapiService;
    private List<Usuario> usuarios;

    private SharedPreferences sharedPreferences;
    private String user;
    private Button admin;
    public Fragment_Home(){
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iapiService = RestClient.getInstance(requireContext());
        usuarios = new ArrayList<>();


        Button carta = view.findViewById(R.id.btCartaHome);
        Button paginaWeb= view.findViewById(R.id.btPaginaWeb);
        Button idioma= view.findViewById(R.id.btCambioIdioma);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("nombreDeUsuario","");
        //getProductos();
        //getTipos();
        carta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getParentFragmentManager();
                manager.beginTransaction()
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .replace(R.id.content_frame, Fragment_Carta.class, null)
                        .commit();
            }
        });
        paginaWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPaginaWeb("https://lacasanostra.000webhostapp.com/localhost_9000/home.html");
            }
        });
        idioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoSeleccion dialogoSeleccion = new DialogoSeleccion();
                dialogoSeleccion.show(getParentFragmentManager(), "error_dialog_select");
            }
        });
    }

    /*
    private void getProductos(){
        iapiService.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(@NonNull Call<List<Producto>> call, @NonNull Response<List<Producto>> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    for(Producto producto: response.body()) {
                        Log.i("Productos", producto.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Producto>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    */

    private void abrirPaginaWeb(String url){
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(webIntent);
    }
}
