package com.cristobalbernal.lacasanostraapk.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Modificar_Plato extends Fragment {
    private IAPIService apiService;
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
                    Spinner spinner = view.findViewById(R.id.sListaPlatos);
                    spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_item_geekipedia, tipos));
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String tipo = spinner.getSelectedItem().toString();
                            Tipo tipo1 = null;
                            for (Tipo categoria : tipoList) {
                                if (tipo.equals(categoria.getNombre())) {
                                    tipo1 = categoria;
                                }
                            }
                            assert tipo1 != null;
                            Toast.makeText(getContext(),String.valueOf(tipo1.getId()),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

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
