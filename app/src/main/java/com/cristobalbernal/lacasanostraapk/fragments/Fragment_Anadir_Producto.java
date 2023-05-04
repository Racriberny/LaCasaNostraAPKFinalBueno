package com.cristobalbernal.lacasanostraapk.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Anadir_Producto extends Fragment {
    private IAPIService iapiService;
    private ActivityResultLauncher<PickVisualMediaRequest> pickImage;
    private String base64;


    public Fragment_Anadir_Producto(){
        super(R.layout.fragment_anadir_producto);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iapiService = RestClient.getInstance();
        List<Tipo> tipoList = new ArrayList<>();
        ImageView imageView = view.findViewById(R.id.imgProducto);

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
                if (response.isSuccessful()){
                    tipoList.addAll(response.body());
                    String[] tipo = new String[tipoList.size()];
                    for (int i = 0; i <tipo.length ; i++) {
                        tipo[i] = tipoList.get(i).getNombre();
                    }
                    Spinner spinner = view.findViewById(R.id.spinnerProductos);
                    spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_item_geekipedia,tipo));
                    EditText nombre = view.findViewById(R.id.name_producto);
                    EditText precio = view.findViewById(R.id.price_producto);
                    EditText ingrediente = view.findViewById(R.id.ingrediente_productos);
                    EditText calorias = view.findViewById(R.id.calories_producto);
                    Button añadir = view.findViewById(R.id.anadirProducto);


                    añadir.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tipo = spinner.getSelectedItem().toString();
                            String name = nombre.getText().toString();
                            String price = precio.getText().toString();
                            String ingredientes = ingrediente.getText().toString();
                            String calories = calorias.getText().toString();

                            if (name.isEmpty()){
                                nombre.setError("Debes de introducir el nombre.");
                                nombre.requestFocus();
                                return;
                            }
                            if (price.isEmpty()){
                                precio.setError("Debes de introducir el precio.");
                                precio.requestFocus();
                                return;
                            }
                            if (ingredientes.isEmpty()){
                                ingrediente.setError("Debes de introducir los ingredientes.");
                                ingrediente.requestFocus();
                                return;
                            }
                            if (calories.isEmpty()){
                                calorias.setError("Debes de escribir las calorias.");
                                calorias.requestFocus();
                                return;
                            }
                            Tipo tipo1 = null;
                            for (Tipo tipoTipo : tipoList) {
                                if (tipo.equals(tipoTipo.getNombre())) {
                                    tipo1 = tipoTipo;
                                }
                            }

                            if (base64 == null){
                                Toast.makeText(getContext(),"Tiene que seleccionar una image",Toast.LENGTH_SHORT).show();
                            }else {
                                Log.i("Añadiendo","Añadiendo producto...");
                                assert tipo1 !=null;

                                iapiService.addProducto(new Producto(name,price,ingredientes,calories,tipo1.getId(),base64)).enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        Log.i("Bien",response.toString());
                                        Toast.makeText(getContext(),"Producto añadido correctamente",Toast.LENGTH_SHORT).show();
                                        FragmentManager manager = getParentFragmentManager();
                                        manager.beginTransaction()
                                                .setReorderingAllowed(true)
                                                .addToBackStack(null)
                                                .replace(R.id.content_frame,Fragment_Home.class,null)
                                                .commit();
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {

                                    }
                                });

                            }


                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Tipo>> call, Throwable t) {

            }
        });



    }
}
