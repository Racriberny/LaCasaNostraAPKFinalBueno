package com.cristobalbernal.lacasanostraapk.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.Utils.EncodingImg;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Producto;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Modificar_Producto extends Fragment {

    private IAPIService iapiService;

    private ActivityResultLauncher<PickVisualMediaRequest> pickImage;
    private String base64;
    private List<Tipo> tipoList;
    private List<Producto> productoList;


    public Fragment_Modificar_Producto(){
        super(R.layout.fragment_modificar_productos);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tipoList = new ArrayList<>();
        productoList = new ArrayList<>();
        iapiService = RestClient.getInstance(requireContext());
        ImageView imageView = view.findViewById(R.id.imagenModificar);
        pickImage = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                try {
                    base64 = EncodingImg.getBase64FromUri(requireContext(),uri);
                    imageView.setImageBitmap(EncodingImg.decode(base64));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage.launch(new PickVisualMediaRequest());
            }
        });


        iapiService.getTipo().enqueue(new Callback<List<Tipo>>() {
            @Override
            public void onResponse(Call<List<Tipo>> call, Response<List<Tipo>> response) {
                assert response.body() != null;
                tipoList.addAll(response.body());
                iapiService.getProductos().enqueue(new Callback<List<Producto>>() {
                    @Override
                    public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                        assert response.body() != null;
                        productoList.addAll(response.body());
                        String [] tipos = new String[tipoList.size()];
                        String [] productos = new String[productoList.size()];
                        for (int i = 0; i <tipos.length ; i++) {
                            tipos[i] = tipoList.get(i).getNombre();
                        }
                        for (int i = 0; i <productos.length ; i++) {
                            productos[i] = productoList.get(i).getNombre();
                        }
                        Spinner spinnerTipo = view.findViewById(R.id.my_spinner_tipos);
                        spinnerTipo.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_item_geekipedia,tipos));
                        Spinner spinnerProductos = view.findViewById(R.id.my_spinner_producto);
                        spinnerProductos.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_item_geekipedia,productos));
                        EditText nombre = view.findViewById(R.id.namemodificada);
                        EditText precio = view.findViewById(R.id.pricemodificado);
                        EditText calorias = view.findViewById(R.id.caloriasmodificada);
                        EditText ingredientes = view.findViewById(R.id.ingredienteModificadas);
                        Button modificarProducto = view.findViewById(R.id.modificarProducto);
                        final String[] productoSeleccionado = new String[1];
                        spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                // Obtener el producto seleccionado
                                Producto productoSeleccionado = productoList.get(position);

                                // Actualizar los campos de la vista con los datos del producto seleccionado
                                nombre.setText(productoSeleccionado.getNombre());
                                precio.setText(productoSeleccionado.getPrecio());
                                calorias.setText(productoSeleccionado.getCalorias());
                                ingredientes.setText(productoSeleccionado.getIngredientes());
                                imageView.setImageBitmap(EncodingImg.decode(productoSeleccionado.getUrl_imagen()));
                                base64 = productoSeleccionado.getUrl_imagen();
                                // Buscar el tipo correspondiente al producto seleccionado
                                Tipo tipoSeleccionado = null;
                                for (Tipo tipo : tipoList) {
                                    if (tipo.getId() == productoSeleccionado.getTipoIdtipo()) {
                                        tipoSeleccionado = tipo;
                                        break;
                                    }
                                }

                                // Establecer el tipo correspondiente como la opción seleccionada en el Spinner de tipo
                                if (tipoSeleccionado != null) {
                                    int index = tipoList.indexOf(tipoSeleccionado);
                                    spinnerTipo.setSelection(index);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        modificarProducto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String productoSeleccionado = spinnerProductos.getSelectedItem().toString();
                                String name = nombre.getText().toString();
                                String price = precio.getText().toString();
                                String calories = calorias.getText().toString();
                                String ingrediente = ingredientes.getText().toString();
                                String tipoSeleccionado = spinnerTipo.getSelectedItem().toString();

                                if (name.isEmpty()){
                                    nombre.setError("Debes de escribir un nombre.");
                                    nombre.requestFocus();
                                    return;
                                }
                                if (price.isEmpty()){
                                    precio.setError("Debes de escribir un precio.");
                                    precio.requestFocus();
                                    return;
                                }
                                if (calories.isEmpty()){
                                    calorias.setError("Debes de escribir calorias.");
                                    calorias.requestFocus();
                                    return;
                                }
                                if (ingrediente.isEmpty()){
                                    ingredientes.setError("Debes de escribir los ingredientes.");
                                    ingredientes.requestFocus();
                                    return;
                                }

                                Tipo tipo1 = null;
                                for (Tipo tipoTipo : tipoList) {
                                    if (tipoSeleccionado.equals(tipoTipo.getNombre())) {
                                        tipo1 = tipoTipo;
                                    }
                                }
                                Producto producto = null;
                                for (Producto producto1: productoList){
                                    if (productoSeleccionado.equals(producto1.getNombre())){
                                        producto = producto1;
                                    }
                                }
                                if (base64 == null){
                                    Toast.makeText(getContext(), "Debes de seleccionar una imagen", Toast.LENGTH_SHORT).show();
                                }else {
                                    assert producto != null;
                                    assert tipo1 != null;
                                    iapiService.modificarProducto(producto.getId(),new Producto(name,price,ingrediente,calories,tipo1.getId(),base64)).enqueue(new Callback<Producto>() {
                                        @Override
                                        public void onResponse(Call<Producto> call, Response<Producto> response) {
                                            Log.i("Modificando","Modificando producto...");
                                            Toast.makeText(getContext(), R.string.producto, Toast.LENGTH_SHORT).show();
                                            FragmentManager manager = getParentFragmentManager();
                                            manager.beginTransaction()
                                                    .setReorderingAllowed(true)
                                                    .addToBackStack(null)
                                                    .replace(R.id.content_frame,Fragment_Home.class,null)
                                                    .commit();
                                        }

                                        @Override
                                        public void onFailure(Call<Producto> call, Throwable t) {

                                        }
                                    });

                                }

                            }
                        });




                    }

                    @Override
                    public void onFailure(Call<List<Producto>> call, Throwable t) {

                    }
                });





            }

            @Override
            public void onFailure(Call<List<Tipo>> call, Throwable t) {

            }
        });




    }
}
