package com.cristobalbernal.lacasanostraapk.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
    private Button bfecha,bhora,guardar,listaReservas;
    private EditText efecha,ehora;
    private Spinner cantidad;
    private String numeroSeleccionado;
    private  String seleccion;
    private  int dia,mes,ano,hora,minutos,segundos;
    private IAPIService iapiService;

    private List<Usuario> usuarios;
    private String user;
    private SharedPreferences sharedPreferences;
    private int id;
    public Fragment_Reserva(){
        super(R.layout.fragment_reserva);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iapiService = RestClient.getInstance();
        usuarios = new ArrayList<>();
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("nombreDeUsuario","");
        bfecha= view.findViewById(R.id.bfecha);
        listaReservas= view.findViewById(R.id.listaReservas);
        bhora= view.findViewById(R.id.bhora);
        efecha= view.findViewById(R.id.efecha);
        ehora= view.findViewById(R.id.ehora);
        cantidad = view.findViewById(R.id.cantidad);
        guardar = view.findViewById(R.id.guardar);
        bfecha.setOnClickListener(this);
        bhora.setOnClickListener(this);
        guardar.setOnClickListener(this);
        listaReservas.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        List<String> numeros =new ArrayList<>();
        for (int i = 1; i <=15 ; i++) {
            numeros.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapador = new ArrayAdapter<>(getContext(), R.layout.spinner_item_geekipedia,numeros);
        cantidad.setAdapter(adapador);

        iapiService.getUsuario().enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                assert response.body() != null;
                usuarios.addAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });



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
                    ehora.setText(hourOfDay +":"+minute + ":" + segundos);
                }
            },hora,minutos,true);
            timePickerDialog.show();
        }


        cantidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numeroSeleccionado = (String) parent.getItemAtPosition(position);
                seleccion = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for (int i = 0; i <usuarios.size() ; i++) {
            if (usuarios.get(i).getCorreoElectronico().equals(user)){
                id = usuarios.get(i).getId();
            }
        }

        if (v == guardar){
            int comensales = (cantidad.getSelectedItemPosition() +1);
            registrar(efecha.getText().toString(),ehora.getText().toString(), String.valueOf(comensales),id);
        }
        if (v == listaReservas){
            FragmentManager manager = getParentFragmentManager();
            manager.beginTransaction()
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .replace(R.id.content_frame, Fragment_Lista_Reservas.class, null)
                    .commit();
        }
    }

    private void registrar(String fecha, String hora, String canti, int id) {
        Call<Boolean> booleanCall = iapiService.addReserva(new Reservas(canti,fecha,id,hora));

        booleanCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                if (Boolean.TRUE.equals(response.body())){
                    Toast.makeText(getContext(),R.string.reservaFinal , Toast.LENGTH_SHORT).show();
                    FragmentManager manager = getParentFragmentManager();
                    manager.beginTransaction()
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .replace(R.id.content_frame, Fragment_Home.class, null)
                            .commit();
                }else if (Boolean.FALSE.equals(response.body())){
                    System.out.println("Error");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("Error_Reserva", t.getMessage());
            }
        });
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
