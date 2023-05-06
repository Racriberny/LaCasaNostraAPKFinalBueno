package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.adaptadores.AdaptadorTipo;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.interfaces.ITipoComida;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.modelos.Vista;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Carta extends Fragment {

    private List<Tipo> tipos;
    private List<Vista> vistaList;

    private IAPIService iapiService;
    private TextView titulo;
    public Fragment_Carta(){
        super(R.layout.lista);
    }

    private ITipoComida listener;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iapiService = RestClient.getInstance();
        RecyclerView recyclerView = view.findViewById(R.id.rvLista);
        tipos = new ArrayList<>();
        vistaList = new ArrayList<>();
        titulo = view.findViewById(R.id.tvTitulo);
        titulo.setText(R.string.carta);


        iapiService.getTipo().enqueue(new Callback<List<Tipo>>() {
            @Override
            public void onResponse(Call<List<Tipo>> call, Response<List<Tipo>> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    tipos.addAll(response.body());
                    for (int i = 0; i <tipos.size() ; i++) {
                        System.out.println(tipos.get(i).getNombre());
                    }
                    AdaptadorTipo adaptadorTipo = new AdaptadorTipo(tipos, listener);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adaptadorTipo);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Tipo>> call, @NonNull Throwable t) {
                Toast.makeText(requireActivity().getApplicationContext(), "No se han podido obtener las categorias", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ITipoComida) context;
    }
}
