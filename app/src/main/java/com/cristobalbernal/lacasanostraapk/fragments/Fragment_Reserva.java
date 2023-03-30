package com.cristobalbernal.lacasanostraapk.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cristobalbernal.lacasanostraapk.R;
import com.cristobalbernal.lacasanostraapk.Utils.Lib;
import com.cristobalbernal.lacasanostraapk.interfaces.IAPIService;
import com.cristobalbernal.lacasanostraapk.modelos.Reservas;
import com.cristobalbernal.lacasanostraapk.modelos.Usuario;
import com.cristobalbernal.lacasanostraapk.rest.RestClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Reserva extends Fragment implements View.OnClickListener {
    private Usuario usuarioActivo;
    public interface IOnActivoUser{
        Usuario usuario();
    }
    private List<Reservas> reservas;

    private Button bfecha,bhora,guardar;
    private EditText efecha,ehora,cantidad;
    private  int dia,mes,ano,hora,minutos,segundos;
    private IAPIService iapiService;
    public Fragment_Reserva(){
        super(R.layout.fragment_reserva);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iapiService = RestClient.getInstance();
        reservas = new ArrayList<>();
        bfecha= view.findViewById(R.id.bfecha);
        bhora= view.findViewById(R.id.bhora);
        efecha= view.findViewById(R.id.efecha);
        ehora= view.findViewById(R.id.ehora);
        cantidad = view.findViewById(R.id.cantidad);
        guardar = view.findViewById(R.id.guardar);
        bfecha.setOnClickListener(this);
        bhora.setOnClickListener(this);
        guardar.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onClick(View v) {
        if(v==bfecha){
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    // Crea una instancia de la clase Calendar y configúrala con la fecha seleccionada por el usuario
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth);

                    // Usa la clase SimpleDateFormat para formatear la fecha en el formato "yyyy-MM-dd"
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(calendar.getTime());
                    efecha.setText(formattedDate);
                    System.out.println(efecha.getText().toString());
                    // Usa la fecha formateada según sea necesario

                }
            }, ano, mes, dia);
            datePickerDialog.show();
        }
        if (v==bhora){
            Calendar c= Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);
            segundos = 00;

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    ehora.setText((hourOfDay + 1) +":"+minute + ":" + segundos);
                }
            },hora,minutos,true);
            timePickerDialog.show();
        }
        String canti= cantidad.getText().toString();
        if (v == guardar){
            registrar(efecha.getText().toString(),ehora.getText().toString(),canti,usuarioActivo);
        }
    }

    private void registrar(String fecha, String hora, String canti, Usuario usuario) {
        Call<Boolean> booleanCall = iapiService.addReserva(new Reservas(canti,fecha,String.valueOf(usuario.getId()),hora));

        booleanCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (Boolean.TRUE.equals(response.body())){
                    Toast.makeText(getContext(), "Has realizado una reserva en La Casa Nostra a nombre de: "
                            +usuario.getNombre() , Toast.LENGTH_SHORT).show();
                    FragmentManager manager = getParentFragmentManager();
                    manager.beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .replace(R.id.content_frame, Fragment_Home.class, null)
                            .commit();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IOnActivoUser iOnActivoUser = (IOnActivoUser) context;
        iOnActivoUser.usuario();
        usuarioActivo = iOnActivoUser.usuario();
    }

    /*
    Mostrar reservas;
    iapiService.getReservas().enqueue(new Callback<List<Reservas>>() {
                @Override
                public void onResponse(Call<List<Reservas>> call, Response<List<Reservas>> response) {
                    if(response.isSuccessful()) {
                        assert response.body() != null;
                        reservas.addAll(response.body());
                        Log.i("Reservas",reservas.toString());
                    }
                }

                @Override
                public void onFailure(Call<List<Reservas>> call, Throwable t) {

                }
            });
     */
}
