package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.Utils.EncodingImg;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentDetalle extends Fragment {
    private int idProducto;
    private IAPIService iapiService;
    private List<Producto> productos;
    private ImageView imagen;
    private TextView nombre;
    private TextView ingredientes;
    private TextView calorias;
    private TextView precio;

    public interface IOnAttachListenerDetalle{
        int ipProducto();
    }


    public FragmentDetalle(){
        super(R.layout.fragmnet_detalle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imagen = view.findViewById(R.id.imgDetalle);
        nombre = view.findViewById(R.id.detellaProducto);
        ingredientes = view.findViewById(R.id.ingredientesDetalle);
        calorias = view.findViewById(R.id.calorias);
        precio = view.findViewById(R.id.precioDetalle);
        iapiService = RestClient.getInstance(requireContext());
        productos = new ArrayList<>();

        iapiService.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                assert response.body() != null;
                productos.addAll(response.body());
                for (int i = 0; i <productos.size() ; i++) {
                    if (productos.get(i).getId() == idProducto){
                        imagen.setImageBitmap(EncodingImg.decode(productos.get(i).getUrl_imagen()));
                        nombre.setText(productos.get(i).getNombre());
                        ingredientes.setText(productos.get(i).getIngredientes());
                        calorias.setText(productos.get(i).getCalorias());
                        precio.setText(productos.get(i).getPrecio());
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
        idProducto = iOnAttachListenerDetalle.ipProducto();
    }
}
