package com.cristobalbernal.lacasanostraapk.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cristobalbernal.lacasanostraapk.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Fragment_Promociones extends Fragment{


    public Fragment_Promociones(){
        super(R.layout.fragment_promociones);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int cantidad = 5;
        ImageView imageView = view.findViewById(R.id.qr);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String corre = sharedPreferences.getString("nombreDeUsuario", "");
        String decuent = corre + " " + cantidad;
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = null;
        try {
            bitmap = barcodeEncoder.encodeBitmap(decuent, BarcodeFormat.QR_CODE,750,750);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        imageView.setImageBitmap(bitmap);

    }
}
