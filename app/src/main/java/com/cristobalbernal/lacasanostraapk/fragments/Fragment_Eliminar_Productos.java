package com.cristobalbernal.lacasanostraapk.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.Utils.EncodingImg;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Eliminar_Productos extends Fragment {
    private IAPIService iapiService;
    private List<Producto> productoList;

    public Fragment_Eliminar_Productos(){
        super(R.layout.fragment_eliminar_productos);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iapiService = RestClient.getInstance();
        productoList = new ArrayList<>();
        iapiService.getProductos().enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    productoList.addAll(response.body());
                    String [] productos = new String[productoList.size()];
                    for (int i = 0; i <productos.length ; i++) {
                        productos[i] = productoList.get(i).getNombre();
                    }
                    Spinner spinnerProductos = view.findViewById(R.id.spinnerEliminar);
                    spinnerProductos.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_item_geekipedia,productos));
                    Button btEliminar = view.findViewById(R.id.btELimnar);
                    ImageView imageView = view.findViewById(R.id.imEliminarProducto);
                    spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Producto productoSeleccionado = productoList.get(position);
                            imageView.setImageBitmap(EncodingImg.decode(productoSeleccionado.getUrl_imagen()));
                            btEliminar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("¿Deseas eliminar el producto??")
                                                    .setMessage(productoSeleccionado.getNombre())
                                                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    iapiService.deleteProducto(productoSeleccionado.getId()).enqueue(new Callback<Boolean>() {
                                                                        @Override
                                                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                                            Toast.makeText(getContext(),"Producto eliminado",Toast.LENGTH_SHORT).show();
                                                                            FragmentManager manager = getParentFragmentManager();
                                                                            manager.beginTransaction()
                                                                                    .setReorderingAllowed(true)
                                                                                    .addToBackStack(null)
                                                                                    .replace(R.id.content_frame,Fragment_Home.class,null)
                                                                                    .commit();
                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<Boolean> call, Throwable t) {
                                                                            Log.d("Error",t.getMessage());
                                                                        }
                                                                    });
                                                                }
                                                            })
                                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Toast.makeText(getContext(),"Has cancelado",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            });





                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });





                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {

            }
        });



    }
}
