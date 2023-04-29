package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDetalle extends Fragment {
    private Tipo tipo;
    private int idProducto;
    private IAPIService iapiService;
    private TextView textView9;
    private TextView textView8;
    private List<Producto> productos;

    public interface IOnAttachListenerDetalle{
        Tipo getTipoSelecionadoo();
        int ipProducto();
    }


    public FragmentDetalle(){
        super(R.layout.pruebadetalle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iapiService = RestClient.getInstance();
        productos = new ArrayList<>();
        textView9 = view.findViewById(R.id.textView9);
        textView8 = view.findViewById(R.id.textView8);
        textView9.setText(String.valueOf(idProducto));

        iapiService.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                assert response.body() != null;
                productos.addAll(response.body());
                for (int i = 0; i <productos.size() ; i++) {
                    if (productos.get(i).getId() == idProducto){
                        Producto producto = new Producto(productos.get(i).getId(),productos.get(i).getNombre(),productos.get(i).getPrecio(),productos.get(i).getIngredientes()
                        ,productos.get(i).getCalorias(),productos.get(i).getTipoIdtipo(),productos.get(i).getUrl_imagen());
                        //Solo falta el adaptador!!!!
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {

            }
        });



    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IOnAttachListenerDetalle iOnAttachListenerDetalle = (IOnAttachListenerDetalle) context;
        tipo = iOnAttachListenerDetalle.getTipoSelecionadoo();
        idProducto = iOnAttachListenerDetalle.ipProducto();
    }
}
