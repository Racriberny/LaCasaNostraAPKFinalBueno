package com.cristobalbernal.lacasanostraapk.Utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lib {
    public static String devolver(String fechaPasa){
        @SuppressLint("SimpleDateFormat") DateFormat formatoOriginal = new SimpleDateFormat("yyyy/MM/dd");
        @SuppressLint("SimpleDateFormat") DateFormat formatoNuevo = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date fecha = formatoOriginal.parse(fechaPasa);
            return formatoNuevo.format(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
