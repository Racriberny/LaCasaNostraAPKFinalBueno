package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.adaptadores.AdaptadorListaUsuarios;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Reservas;
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Lista_Reservas extends Fragment {

    public interface IOnUsuarioListener{
        Usuario getUser();
    }

    private Usuario usuario;

    public Fragment_Lista_Reservas(){
        super(R.layout.lista_lista);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rvListaReservas);
        IAPIService iapiService = RestClient.getInstance();
        List<Reservas> reservas = new ArrayList<>();
        List<Reservas> usuarioReservas = new ArrayList<>();

        iapiService.getReservas().enqueue(new Callback<List<Reservas>>() {
            @Override
            public void onResponse(Call<List<Reservas>> call, Response<List<Reservas>> response) {
                if (response.isSuccessful()){
                    assert response.body() !=null;
                    reservas.addAll(response.body());

                    for (Reservas reservas1:reservas){
                        if (reservas1.getUsuarioId() == usuario.getId()){
                            usuarioReservas.add(reservas1);
                        }
                    }
                    AdaptadorListaUsuarios adaptadorListaUsuarios = new AdaptadorListaUsuarios(usuarioReservas);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adaptadorListaUsuarios);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Reservas>> call, @NonNull Throwable t) {
                Log.d("Error_Reserva", t.getMessage());
            }
        });



    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IOnUsuarioListener iOnUsuarioListener = (IOnUsuarioListener) context;
        usuario = iOnUsuarioListener.getUser();
    }
}
