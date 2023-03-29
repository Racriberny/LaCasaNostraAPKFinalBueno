package com.cristobalbernal.lacasanostraapk.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cristobalbernal.lacasanostraapk.R;

import java.util.Calendar;

public class Fragment_Reserva extends Fragment implements View.OnClickListener {
    Button bfecha,bhora,guardar;
    EditText efecha,ehora,cantidad;
    private  int dia,mes,ano,hora,minutos;
    public Fragment_Reserva(){
        super(R.layout.fragment_reserva);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bfecha= view.findViewById(R.id.bfecha);
        bhora= view.findViewById(R.id.bhora);
        efecha= view.findViewById(R.id.efecha);
        ehora= view.findViewById(R.id.ehora);
        cantidad = view.findViewById(R.id.cantidad);
        guardar = view.findViewById(R.id.guardar);
        bfecha.setOnClickListener(this);
        bhora.setOnClickListener(this);
        guardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==bfecha){
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    efecha.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
                }
            }
                    ,dia,mes,ano);
            datePickerDialog.show();
        }
        if (v==bhora){
            Calendar c= Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    ehora.setText(hourOfDay+":"+minute);
                }
            },hora,minutos,true);
            timePickerDialog.show();
        }
        String canti= cantidad.getText().toString();
        if (v == guardar){
            registrar(efecha.getText().toString(),ehora.getText().toString(),canti);
        }
    }

    private void registrar(String fecha, String hora, String canti) {
        System.out.println(fecha);
        System.out.println(hora);
        System.out.println(canti);
    }
}
