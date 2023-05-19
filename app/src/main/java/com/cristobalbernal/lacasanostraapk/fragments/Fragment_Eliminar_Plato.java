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
import com.cristobalbernal.lacasanostraapk.modelos.Tipo;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Eliminar_Plato extends Fragment {
    private IAPIService iapiService;
    private List<Tipo> tipoList;
    public Fragment_Eliminar_Plato(){
        super(R.layout.fragment_eliminar_plato);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iapiService = RestClient.getInstance();
        tipoList = new ArrayList<>();
        iapiService.getTipo().enqueue(new Callback<List<Tipo>>() {
            @Override
            public void onResponse(Call<List<Tipo>> call, Response<List<Tipo>> response) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    tipoList.addAll(response.body());
                    String [] tipo = new String[tipoList.size()];
                    for (int i = 0; i <tipo.length ; i++) {
                        tipo[i] = tipoList.get(i).getNombre();
                    }
                    Spinner spinnerProductos = view.findViewById(R.id.spinnerEliminarPlato);
                    spinnerProductos.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_item_geekipedia,tipo));
                    Button btEliminar = view.findViewById(R.id.btELimnarPlato);
                    ImageView imageView = view.findViewById(R.id.imEliminarPlato);
                    spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Tipo tipoSeleccionado = tipoList.get(position);
                            imageView.setImageBitmap(EncodingImg.decode(tipoSeleccionado.getImagen()));
                            btEliminar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle(R.string.eliminar_tipo)
                                            .setMessage("Tipo "+ tipoSeleccionado.getNombre())
                                            .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    iapiService.deleteTipo(tipoSeleccionado.getId()).enqueue(new Callback<Boolean>() {
                                                        @Override
                                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                            Toast.makeText(getContext(),R.string.eliminado_tipo,Toast.LENGTH_SHORT).show();
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
                                            .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Toast.makeText(getContext(),R.string.hascancelar,Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<List<Tipo>> call, Throwable t) {

            }
        });



    }
}
