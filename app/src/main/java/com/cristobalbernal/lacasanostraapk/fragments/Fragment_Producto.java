package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.adaptadores.AdaptadorProducto;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Producto extends Fragment {
    public interface IOnAttachListener{
        Producto getProductoSeleccionado();
    }
    public Fragment_Producto(){
        super(R.layout.lista);
    }
    private Producto producto;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rvLista);
        IAPIService apiService = RestClient.getInstance();
        List<Producto> productos = new ArrayList<>();
        apiService.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                assert response.body() != null;
                productos.addAll(response.body());
                AdaptadorProducto adaptadorProducto = new AdaptadorProducto(productos);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adaptadorProducto);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IOnAttachListener iOnAttachListener = (IOnAttachListener) context;
        producto = iOnAttachListener.getProductoSeleccionado();
    }
}
