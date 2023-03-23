package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        apiService.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(@NonNull Call<List<Producto>> call, @NonNull Response<List<Producto>> response) {
                assert response.body() != null;
                AdaptadorProducto adaptadorProducto = new AdaptadorProducto(producto);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adaptadorProducto);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            }

            @Override
            public void onFailure(@NonNull Call<List<Producto>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "No se han podido obtener las productos!!", Toast.LENGTH_LONG).show();
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
