package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.adaptadores.AdaptadorTipoProducto;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.interfaces.IProductoSeleccionado;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Tipo_Producto  extends Fragment {

    public interface IOnAttachListener{
        Tipo getTipoSelecionado();
    }

    private Tipo tipo;
    private TextView titulo;
    private IProductoSeleccionado iProductoSeleccionado;

    public Fragment_Tipo_Producto() {
        super(R.layout.lista);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvLista = view.findViewById(R.id.rvLista);
        IAPIService iapiService= RestClient.getInstance(requireContext());
        List<Producto> productos = new ArrayList<>();
        List<Producto> tiposProducto = new ArrayList<>();
        titulo = view.findViewById(R.id.tvTitulo);


        iapiService.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()){
                    assert response.body() !=null;
                    productos.addAll(response.body());
                    titulo.setText(tipo.getNombre());
                    for (Producto producto: productos){
                        if (producto.getTipoIdtipo() == tipo.getId()){
                            tiposProducto.add(producto);

                        }
                    }
                    AdaptadorTipoProducto adaptadorTipoProducto =  new AdaptadorTipoProducto(tiposProducto,iProductoSeleccionado);
                    rvLista.setHasFixedSize(true);
                    rvLista.setAdapter(adaptadorTipoProducto);
                    rvLista.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Producto>> call, Throwable t) {
                Toast.makeText(requireActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IOnAttachListener iOnAttachListener = (IOnAttachListener) context;
        tipo = iOnAttachListener.getTipoSelecionado();
        iProductoSeleccionado = (IProductoSeleccionado) context;
    }

}
