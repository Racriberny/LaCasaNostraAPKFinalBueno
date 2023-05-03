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
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Modificar_Plato extends Fragment {
    private IAPIService apiService;

    private ActivityResultLauncher<PickVisualMediaRequest> pickImage;
    private String base64;
    public Fragment_Modificar_Plato(){
        super(R.layout.fragment_modificar_plato);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiService = RestClient.getInstance();
        List<Tipo> tipoList = new ArrayList<>();
        apiService.getTipo().enqueue(new Callback<List<Tipo>>() {
            @Override
            public void onResponse(Call<List<Tipo>> call, Response<List<Tipo>> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    tipoList.addAll(response.body());
                    String [] tipos = new String[tipoList.size()];
                    for (int i = 0; i <tipos.length ; i++) {
                        tipos[i] = tipoList.get(i).getNombre();
                    }
                    final int[] id = new int[1];
                    Spinner spinner = view.findViewById(R.id.sListaPlatos);
                    spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_item_geekipedia, tipos));
                    EditText nombre = view.findViewById(R.id.etValorNombre);
                    EditText descripcion = view.findViewById(R.id.etDescripcionModificada);
                    ImageView imagen = view.findViewById(R.id.imageViewModificar);
                    Button btModificar = view.findViewById(R.id.btnModificarPlato);
                    pickImage = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                        if (uri != null) {
                            try {
                                base64 = EncodingImg.getBase64FromUri(requireContext(),uri);
                                imagen.setImageBitmap(EncodingImg.decode(base64));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    imagen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pickImage.launch(new PickVisualMediaRequest());
                        }
                    });

                    btModificar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String tipo = spinner.getSelectedItem().toString();
                            String name = nombre.getText().toString();
                            String description = descripcion.getText().toString();
                            if (name.isEmpty()){
                                nombre.setError("No has indicado nuevo valor");
                                nombre.requestFocus();
                                return;
                            }
                            if (description.isEmpty()){
                                descripcion.setError("No has indicado nuevo valor");
                                descripcion.requestFocus();
                                return;
                            }


                            Tipo tipo1 = null;
                            for (Tipo categoria : tipoList) {
                                if (tipo.equals(categoria.getNombre())) {
                                    tipo1 = categoria;
                                }
                            }
                            assert tipo1 != null;
                            id[0] = tipo1.getId();
                            tipo1.setNombre(name);


                            if (base64 == null){
                                Toast.makeText(getContext(),"Tiene que seleccionar una image",Toast.LENGTH_SHORT).show();
                            }else {
                                Log.i("Modificando","Modificando categoria...");
                                apiService.modificarTipo(id,new Tipo(name,description,base64)).enqueue(new Callback<Tipo>() {
                                    @Override
                                    public void onResponse(Call<Tipo> call, Response<Tipo> response) {
                                        Log.i("Bien",response.toString());
                                        FragmentManager manager = getParentFragmentManager();
                                        manager.beginTransaction()
                                                .setReorderingAllowed(true)
                                                .addToBackStack(null)
                                                .replace(R.id.content_frame, Fragment_Home.class, null)
                                                .commit();
                                    }
                                    @Override
                                    public void onFailure(Call<Tipo> call, Throwable t) {

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
